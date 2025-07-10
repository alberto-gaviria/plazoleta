package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.CategoryEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.ICategoryEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IDishWithCategoryMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.ICategoryRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.domain.model.Category;
import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IRestaurantRepository restaurantRepository;
    private final ICategoryRepository categoryRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IDishWithCategoryMapper dishWithCategoryMapper;

    public DishMysqlAdapter(IDishRepository dishRepository,
                            IRestaurantRepository restaurantRepository,
                            ICategoryRepository categoryRepository,
                            IDishEntityMapper dishEntityMapper,
                            ICategoryEntityMapper categoryEntityMapper,
                            IDishWithCategoryMapper dishWithCategoryMapper) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.dishEntityMapper = dishEntityMapper;
        this.categoryEntityMapper = categoryEntityMapper;
        this.dishWithCategoryMapper = dishWithCategoryMapper;
    }

    @Override
    public Dish saveDish(Dish dish) {
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);
        DishEntity savedEntity = dishRepository.save(dishEntity);
        return dishEntityMapper.toModel(savedEntity);
    }

    @Override
    public boolean existsRestaurantById(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }

    @Override
    public Long getRestaurantOwnerId(Long restaurantId) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            return restaurant.get().getIdPropietario();
        }
        throw new ElementNotFoundException(AdapterConstants.ErrorMessages.RESTAURANT_NO_ENCONTRADO);
    }

    @Override
    public Optional<Dish> findDishById(Long dishId) {
        Optional<DishEntity> dishEntity = dishRepository.findById(dishId);
        return dishEntity.map(dishEntityMapper::toModel);
    }

    @Override
    public Dish updateDish(Dish dish) {
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);
        DishEntity updatedEntity = dishRepository.save(dishEntity);
        return dishEntityMapper.toModel(updatedEntity);
    }

    @Override
    public Long getDishRestaurantId(Long dishId) {
        Optional<DishEntity> dishEntity = dishRepository.findById(dishId);
        if (dishEntity.isPresent()) {
            return dishEntity.get().getIdRestaurante();
        }
        throw new ElementNotFoundException(AdapterConstants.ErrorMessages.DISH_NO_ENCONTRADO);
    }

    @Override
    public Page<DishWithCategory> findDishesByRestaurant(Long restaurantId, Long categoryId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        org.springframework.data.domain.Page<DishEntity> springPage =
                dishRepository.findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);

        List<DishWithCategory> content = springPage.getContent()
                .stream()
                .map(this::convertToDishWithCategory)
                .toList();

        return new Page<>(
                content,
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements()
        );
    }

    private DishWithCategory convertToDishWithCategory(DishEntity dishEntity) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(dishEntity.getIdCategoria());
        Category category = categoryEntity
                .map(categoryEntityMapper::toModel)
                .orElse(new Category(dishEntity.getIdCategoria(), AdapterConstants.ErrorMessages.CATEGORY_NO_ENCONTRADA, ""));

        return dishWithCategoryMapper.toModel(dishEntity, category);
    }
}