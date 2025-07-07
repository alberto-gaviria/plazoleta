package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import com.plazoleta.restaurants.domain.util.paged.Page;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidDishException;

import java.math.BigDecimal;
import java.util.Optional;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public Dish saveDish(Dish dish, Long currentUserId) {
        validateDish(dish);
        validateDishBusinessRules(dish);
        validateRestaurantExists(dish.getIdRestaurante());
        validateOwnership(dish.getIdRestaurante(), currentUserId);

        return dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish updateDish(Long dishId, BigDecimal precio, String descripcion, Long currentUserId) {
        validateUpdateParameters(dishId, precio, descripcion, currentUserId);

        Optional<Dish> dishOptional = dishPersistencePort.findDishById(dishId);
        if (dishOptional.isEmpty()) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_DISH_NO_ENCONTRADO);
        }

        Dish dish = dishOptional.get();
        Long restaurantId = dishPersistencePort.getDishRestaurantId(dishId);

        validateOwnership(restaurantId, currentUserId);
        validatePrecioPositivo(precio);

        dish.setPrecio(precio);
        dish.setDescripcion(descripcion);

        return dishPersistencePort.updateDish(dish);
    }

    @Override
    public Dish toggleDishStatus(Long dishId, Boolean activo, Long currentUserId) {
        validateToggleStatusParameters(dishId, activo, currentUserId);

        Optional<Dish> dishOptional = dishPersistencePort.findDishById(dishId);
        if (dishOptional.isEmpty()) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_DISH_NO_ENCONTRADO);
        }

        Dish dish = dishOptional.get();
        Long restaurantId = dishPersistencePort.getDishRestaurantId(dishId);

        validateOwnership(restaurantId, currentUserId);

        dish.setActivo(activo);

        return dishPersistencePort.updateDish(dish);
    }

    @Override
    public Page<DishWithCategory> getDishesByRestaurant(Long restaurantId, Long categoryId, int pageNumber, int pageSize) {
        validateRestaurantListingParameters(restaurantId, pageNumber, pageSize);
        validateRestaurantExists(restaurantId);

        return dishPersistencePort.findDishesByRestaurant(restaurantId, categoryId, pageNumber, pageSize);
    }

    private void validateRestaurantListingParameters(Long restaurantId, int pageNumber, int pageSize) {
        if (restaurantId == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO);
        }

        if (pageNumber < DomainConstants.Dish.MIN_PAGE_NUMBER) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_PAGE_NUMBER_INVALID);
        }

        if (pageSize < DomainConstants.Dish.MIN_PAGE_SIZE) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_PAGE_SIZE_INVALID);
        }

        if (pageSize > DomainConstants.Dish.MAX_PAGE_SIZE) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_PAGE_SIZE_TOO_LARGE);
        }
    }

    private void validateToggleStatusParameters(Long dishId, Boolean activo, Long currentUserId) {
        if (dishId == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_DISH_ID_REQUERIDO);
        }

        if (currentUserId == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_USUARIO_REQUERIDO);
        }

        if (activo == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_ESTADO_REQUERIDO);
        }
    }

    private void validateUpdateParameters(Long dishId, BigDecimal precio, String descripcion, Long currentUserId) {
        if (dishId == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_DISH_ID_REQUERIDO);
        }

        if (currentUserId == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_USUARIO_REQUERIDO);
        }

        if (precio == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO);
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA);
        }
    }

    private void validateDish(Dish dish) {
        if (dish == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_DISH_NULO);
        }

        if (dish.getNombre() == null || dish.getNombre().trim().isEmpty()) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO);
        }

        if (dish.getPrecio() == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO);
        }

        if (dish.getDescripcion() == null || dish.getDescripcion().trim().isEmpty()) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA);
        }

        if (dish.getUrlImagen() == null || dish.getUrlImagen().trim().isEmpty()) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA);
        }

        if (dish.getIdCategoria() == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_CATEGORIA_REQUERIDA);
        }

        if (dish.getIdRestaurante() == null) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO);
        }
    }

    private void validateDishBusinessRules(Dish dish) {
        validatePrecioPositivo(dish.getPrecio());
    }

    private void validatePrecioPositivo(BigDecimal precio) {
        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_PRECIO_POSITIVO);
        }
    }

    private void validateRestaurantExists(Long restaurantId) {
        if (!dishPersistencePort.existsRestaurantById(restaurantId)) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_RESTAURANTE_NO_ENCONTRADO);
        }
    }

    private void validateOwnership(Long restaurantId, Long currentUserId) {
        Long restaurantOwnerId = dishPersistencePort.getRestaurantOwnerId(restaurantId);
        if (!currentUserId.equals(restaurantOwnerId)) {
            throw new InvalidDishException(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO);
        }
    }
}