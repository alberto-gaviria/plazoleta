package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.spi.IUserValidationPort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserValidationPort userValidationPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort,
                             IUserValidationPort userValidationPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userValidationPort = userValidationPort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant, Long adminId) {
        validateRestaurant(restaurant);
        validateRestaurantBusinessRules(restaurant);
        validateAdministrador(adminId);
        validatePropietario(restaurant.getIdPropietario());

        restaurantPersistencePort.saveRestaurant(restaurant);
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

    private void validateAdministrador(Long adminId) {
        if (adminId == null) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_ADMIN_ID_REQUERIDO);
        }

        if (!userValidationPort.existsUserById(adminId)) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_ENCONTRADO);
        }

        if (!userValidationPort.hasRequiredRole(adminId, DomainConstants.Restaurant.ROL_ADMINISTRADOR)) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_VALIDO);
        }
    }

    private void validatePropietario(Long idPropietario) {
        if (!userValidationPort.existsUserById(idPropietario)) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO);
        }

        if (!userValidationPort.hasRequiredRole(idPropietario, DomainConstants.Restaurant.ROL_PROPIETARIO)) {
            throw new InvalidRestaurantException(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO);
        }
    }
}