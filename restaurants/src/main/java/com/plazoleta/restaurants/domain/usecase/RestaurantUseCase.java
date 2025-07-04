package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant, Long adminId) {
        validateRestaurant(restaurant);
        validateRestaurantBusinessRules(restaurant);

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public Page<RestaurantSummary> getAllRestaurants(int pageNumber, int pageSize) {
        validatePaginationParameters(pageNumber, pageSize);
        return restaurantPersistencePort.findAllRestaurantsSorted(pageNumber, pageSize);
    }

    private void validatePaginationParameters(int pageNumber, int pageSize) {
        if (pageNumber < DomainConstants.Restaurant.MIN_PAGE_NUMBER) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_PAGE_NUMBER_INVALID);
        }

        if (pageSize < DomainConstants.Restaurant.MIN_PAGE_SIZE) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_PAGE_SIZE_INVALID);
        }

        if (pageSize > DomainConstants.Restaurant.MAX_PAGE_SIZE) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_PAGE_SIZE_TOO_LARGE);
        }
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_RESTAURANT_NULO);
        }

        if (restaurant.getNombre() == null || restaurant.getNombre().trim().isEmpty()) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO);
        }

        if (restaurant.getNit() == null || restaurant.getNit().trim().isEmpty()) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO);
        }

        if (restaurant.getDireccion() == null || restaurant.getDireccion().trim().isEmpty()) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA);
        }

        if (restaurant.getTelefono() == null || restaurant.getTelefono().trim().isEmpty()) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO);
        }

        if (restaurant.getUrlLogo() == null || restaurant.getUrlLogo().trim().isEmpty()) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA);
        }

        if (restaurant.getIdPropietario() == null) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO);
        }
    }

    private void validateRestaurantBusinessRules(Restaurant restaurant) {
        validateNombreNotOnlyNumbers(restaurant.getNombre());
        validateNitFormat(restaurant.getNit());
        validateTelefonoFormat(restaurant.getTelefono());
    }

    private void validateNombreNotOnlyNumbers(String nombre) {
        if (nombre.matches(DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN)) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS);
        }
    }

    private void validateNitFormat(String nit) {
        if (!nit.matches(DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN)) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO);
        }
    }

    private void validateTelefonoFormat(String telefono) {
        if (!telefono.matches(DomainConstants.Restaurant.TELEFONO_PATTERN)) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO);
        }
    }
}