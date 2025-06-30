package com.plazoleta.restaurants.adapters.driven.feign.adapter;

import com.plazoleta.restaurants.adapters.driven.feign.client.IUserFeignClient;
import com.plazoleta.restaurants.adapters.driven.feign.dto.response.UserResponse;
import com.plazoleta.restaurants.adapters.driven.feign.util.UserAdapterConstants;
import com.plazoleta.restaurants.domain.spi.IUserValidationPort;
import com.plazoleta.restaurants.adapters.driven.feign.exception.FeignUserException;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class UserValidationAdapter implements IUserValidationPort {

    private final IUserFeignClient userFeignClient;

    public UserValidationAdapter(IUserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public boolean existsUserById(Long userId) {
        try {
            UserResponse response = userFeignClient.getUserById(userId);
            return response != null && response.getId() != null;
        } catch (FeignException.NotFound e) {
            return false;
        } catch (FeignException e) {
            throw new FeignUserException(UserAdapterConstants.ErrorMessages.ERROR_OBTENIENDO_USUARIO + userId +
                    UserAdapterConstants.ErrorMessages.MENSAJE_SEPARATOR + e.getMessage());
        } catch (Exception e) {
            throw new FeignUserException(UserAdapterConstants.ErrorMessages.ERROR_COMUNICACION_USERS_SERVICE +
                    e.getMessage(), e);
        }
    }

    @Override
    public boolean hasRequiredRole(Long userId, String requiredRole) {
        try {
            UserResponse response = userFeignClient.getUserById(userId);
            return response != null && requiredRole.equals(response.getRolNombre());
        } catch (FeignException.NotFound e) {
            return false;
        } catch (FeignException e) {
            throw new FeignUserException(UserAdapterConstants.ErrorMessages.ERROR_VALIDANDO_ROL + userId +
                    UserAdapterConstants.ErrorMessages.MENSAJE_SEPARATOR + e.getMessage());
        } catch (Exception e) {
            throw new FeignUserException(UserAdapterConstants.ErrorMessages.ERROR_COMUNICACION_USERS_SERVICE +
                    e.getMessage(), e);
        }
    }
}