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
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishMysqlAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @Mock
    private IDishWithCategoryMapper dishWithCategoryMapper;

    private DishMysqlAdapter dishMysqlAdapter;

    private Dish dish;
    private DishEntity dishEntity;
    private RestaurantEntity restaurantEntity;
    private CategoryEntity categoryEntity;
    private Category category;
    private DishWithCategory dishWithCategory;

    @BeforeEach
    void setUp() {
        // Constructor actualizado con 6 parámetros
        dishMysqlAdapter = new DishMysqlAdapter(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper
        );

        dish = new Dish();
        dish.setId(1L);
        dish.setNombre("Pizza Margherita");
        dish.setPrecio(new BigDecimal("25.50"));
        dish.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dish.setUrlImagen("https://example.com/pizza.jpg");
        dish.setIdCategoria(2L);
        dish.setIdRestaurante(1L);
        dish.setActivo(true);

        dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setNombre("Pizza Margherita");
        dishEntity.setPrecio(new BigDecimal("25.50"));
        dishEntity.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dishEntity.setUrlImagen("https://example.com/pizza.jpg");
        dishEntity.setIdCategoria(2L);
        dishEntity.setIdRestaurante(1L);
        dishEntity.setActivo(true);

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setIdPropietario(1L);

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(2L);
        categoryEntity.setNombre("Pizzas");
        categoryEntity.setDescripcion("Pizzas artesanales");

        category = new Category(2L, "Pizzas", "Pizzas artesanales");

        dishWithCategory = new DishWithCategory(
                1L,
                "Pizza Margherita",
                new BigDecimal("25.50"),
                "Pizza con salsa de tomate, mozzarella y albahaca",
                "https://example.com/pizza.jpg",
                category,
                1L,
                true
        );
    }

    @Test
    void saveDish_ShouldMapAndSaveDishEntity() {
        // Given
        when(dishEntityMapper.toEntity(dish)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        // When
        dishMysqlAdapter.saveDish(dish);

        // Then
        verify(dishEntityMapper).toEntity(dish);
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void existsRestaurantById_WhenRestaurantExists_ShouldReturnTrue() {
        // Given
        Long restaurantId = 1L;
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);

        // When
        boolean result = dishMysqlAdapter.existsRestaurantById(restaurantId);

        // Then
        assertTrue(result);
        verify(restaurantRepository).existsById(restaurantId);
    }

    @Test
    void existsRestaurantById_WhenRestaurantDoesNotExist_ShouldReturnFalse() {
        // Given
        Long restaurantId = 1L;
        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        // When
        boolean result = dishMysqlAdapter.existsRestaurantById(restaurantId);

        // Then
        assertFalse(result);
        verify(restaurantRepository).existsById(restaurantId);
    }

    @Test
    void getRestaurantOwnerId_WhenRestaurantExists_ShouldReturnOwnerId() {
        // Given
        Long restaurantId = 1L;
        Long expectedOwnerId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));

        // When
        Long result = dishMysqlAdapter.getRestaurantOwnerId(restaurantId);

        // Then
        assertEquals(expectedOwnerId, result);
        verify(restaurantRepository).findById(restaurantId);
    }

    @Test
    void getRestaurantOwnerId_WhenRestaurantDoesNotExist_ShouldThrowElementNotFoundException() {
        // Given
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // When & Then
        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class,
                                                          () -> dishMysqlAdapter.getRestaurantOwnerId(restaurantId));

        assertEquals(AdapterConstants.ErrorMessages.RESTAURANT_NO_ENCONTRADO, exception.getMessage());
        verify(restaurantRepository).findById(restaurantId);
    }

    @Test
    void findDishById_WhenDishExists_ShouldReturnDish() {
        // Given
        Long dishId = 1L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));
        when(dishEntityMapper.toModel(dishEntity)).thenReturn(dish);

        // When
        Optional<Dish> result = dishMysqlAdapter.findDishById(dishId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(dish, result.get());
        verify(dishRepository).findById(dishId);
        verify(dishEntityMapper).toModel(dishEntity);
    }

    @Test
    void findDishById_WhenDishDoesNotExist_ShouldReturnEmpty() {
        // Given
        Long dishId = 1L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // When
        Optional<Dish> result = dishMysqlAdapter.findDishById(dishId);

        // Then
        assertFalse(result.isPresent());
        verify(dishRepository).findById(dishId);
        verifyNoInteractions(dishEntityMapper);
    }

    @Test
    void updateDish_ShouldMapAndSaveDishEntity() {
        // Given
        when(dishEntityMapper.toEntity(dish)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        // When
        dishMysqlAdapter.updateDish(dish);

        // Then
        verify(dishEntityMapper).toEntity(dish);
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void getDishRestaurantId_WhenDishExists_ShouldReturnRestaurantId() {
        // Given
        Long dishId = 1L;
        Long expectedRestaurantId = 1L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));

        // When
        Long result = dishMysqlAdapter.getDishRestaurantId(dishId);

        // Then
        assertEquals(expectedRestaurantId, result);
        verify(dishRepository).findById(dishId);
    }

    @Test
    void getDishRestaurantId_WhenDishDoesNotExist_ShouldThrowElementNotFoundException() {
        // Given
        Long dishId = 1L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // When & Then
        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class,
                                                          () -> dishMysqlAdapter.getDishRestaurantId(dishId));

        assertEquals(AdapterConstants.ErrorMessages.DISH_NO_ENCONTRADO, exception.getMessage());
        verify(dishRepository).findById(dishId);
    }

    // ==================== NUEVOS TESTS PARA HU10 ====================

    @Test
    void findDishesByRestaurant_WithCategoryFilter_ShouldReturnFilteredDishes() {
        // Given
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        org.springframework.data.domain.Page<DishEntity> springPage =
                new PageImpl<>(Arrays.asList(dishEntity), pageable, 1L);

        when(dishRepository.findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable))
                .thenReturn(springPage);
        when(categoryRepository.findById(2L))
                .thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toModel(categoryEntity))
                .thenReturn(category);
        when(dishWithCategoryMapper.toModel(dishEntity, category))
                .thenReturn(dishWithCategory);

        // When
        Page<DishWithCategory> result = dishMysqlAdapter.findDishesByRestaurant(restaurantId, categoryId, pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Pizza Margherita", result.getContent().get(0).getNombre());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(1L, result.getTotalElements());

        verify(dishRepository).findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);
        verify(categoryRepository).findById(2L);
        verify(categoryEntityMapper).toModel(categoryEntity);
        verify(dishWithCategoryMapper).toModel(dishEntity, category);
    }

    @Test
    void findDishesByRestaurant_WithoutCategoryFilter_ShouldReturnAllDishes() {
        // Given
        Long restaurantId = 1L;
        Long categoryId = null;
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        org.springframework.data.domain.Page<DishEntity> springPage =
                new PageImpl<>(Arrays.asList(dishEntity), pageable, 1L);

        when(dishRepository.findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable))
                .thenReturn(springPage);
        when(categoryRepository.findById(2L))
                .thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toModel(categoryEntity))
                .thenReturn(category);
        when(dishWithCategoryMapper.toModel(dishEntity, category))
                .thenReturn(dishWithCategory);

        // When
        Page<DishWithCategory> result = dishMysqlAdapter.findDishesByRestaurant(restaurantId, categoryId, pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(dishRepository).findActiveDishesByRestaurantAndCategory(restaurantId, null, pageable);
        verify(categoryRepository).findById(2L);
        verify(categoryEntityMapper).toModel(categoryEntity);
        verify(dishWithCategoryMapper).toModel(dishEntity, category);
    }

    @Test
    void findDishesByRestaurant_EmptyResult_ShouldReturnEmptyPage() {
        // Given
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        org.springframework.data.domain.Page<DishEntity> emptySpringPage =
                new PageImpl<>(Collections.emptyList(), pageable, 0L);

        when(dishRepository.findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable))
                .thenReturn(emptySpringPage);

        // When
        Page<DishWithCategory> result = dishMysqlAdapter.findDishesByRestaurant(restaurantId, categoryId, pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0L, result.getTotalElements());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());

        verify(dishRepository).findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);
        verifyNoInteractions(categoryRepository);
        verifyNoInteractions(categoryEntityMapper);
        verifyNoInteractions(dishWithCategoryMapper);
    }

    @Test
    void findDishesByRestaurant_CategoryNotFound_ShouldReturnDishWithDefaultCategory() {
        // Given
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        org.springframework.data.domain.Page<DishEntity> springPage =
                new PageImpl<>(Arrays.asList(dishEntity), pageable, 1L);

        Category defaultCategory = new Category(2L, "Categoría no encontrada", "");
        DishWithCategory dishWithDefaultCategory = new DishWithCategory(
                1L, "Pizza Margherita", new BigDecimal("25.50"),
                "Pizza con salsa de tomate, mozzarella y albahaca",
                "https://example.com/pizza.jpg", defaultCategory, 1L, true
        );

        when(dishRepository.findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable))
                .thenReturn(springPage);
        when(categoryRepository.findById(2L))
                .thenReturn(Optional.empty()); // Categoría no encontrada
        when(dishWithCategoryMapper.toModel(eq(dishEntity), any(Category.class)))
                .thenReturn(dishWithDefaultCategory);

        // When
        Page<DishWithCategory> result = dishMysqlAdapter.findDishesByRestaurant(restaurantId, categoryId, pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Pizza Margherita", result.getContent().get(0).getNombre());
        assertEquals("Categoría no encontrada", result.getContent().get(0).getCategoria().getNombre());

        verify(dishRepository).findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);
        verify(categoryRepository).findById(2L);
        verify(dishWithCategoryMapper).toModel(eq(dishEntity), any(Category.class));
        verifyNoInteractions(categoryEntityMapper);
    }

    @Test
    void findDishesByRestaurant_MultipleDishes_ShouldMapAllCorrectly() {
        // Given
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Segundo plato
        DishEntity dishEntity2 = new DishEntity();
        dishEntity2.setId(2L);
        dishEntity2.setNombre("Pizza Pepperoni");
        dishEntity2.setPrecio(new BigDecimal("28.00"));
        dishEntity2.setDescripcion("Pizza con pepperoni y queso");
        dishEntity2.setUrlImagen("https://example.com/pizza-pepperoni.jpg");
        dishEntity2.setIdCategoria(2L);
        dishEntity2.setIdRestaurante(1L);
        dishEntity2.setActivo(true);

        DishWithCategory dishWithCategory2 = new DishWithCategory(
                2L, "Pizza Pepperoni", new BigDecimal("28.00"),
                "Pizza con pepperoni y queso",
                "https://example.com/pizza-pepperoni.jpg", category, 1L, true
        );

        List<DishEntity> dishEntities = Arrays.asList(dishEntity, dishEntity2);
        org.springframework.data.domain.Page<DishEntity> multipleSpringPage =
                new PageImpl<>(dishEntities, pageable, 2L);

        when(dishRepository.findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable))
                .thenReturn(multipleSpringPage);
        when(categoryRepository.findById(2L))
                .thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toModel(categoryEntity))
                .thenReturn(category);
        when(dishWithCategoryMapper.toModel(dishEntity, category))
                .thenReturn(dishWithCategory);
        when(dishWithCategoryMapper.toModel(dishEntity2, category))
                .thenReturn(dishWithCategory2);

        // When
        Page<DishWithCategory> result = dishMysqlAdapter.findDishesByRestaurant(restaurantId, categoryId, pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Pizza Margherita", result.getContent().get(0).getNombre());
        assertEquals("Pizza Pepperoni", result.getContent().get(1).getNombre());
        assertEquals(2L, result.getTotalElements());

        verify(dishRepository).findActiveDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);
        verify(categoryRepository, times(2)).findById(2L);
        verify(categoryEntityMapper, times(2)).toModel(categoryEntity);
        verify(dishWithCategoryMapper).toModel(dishEntity, category);
        verify(dishWithCategoryMapper).toModel(dishEntity2, category);
    }
}