package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IEfficiencyServicePort;
import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import com.plazoleta.restaurants.domain.model.UserInfo;
import com.plazoleta.restaurants.domain.spi.IEfficiencyPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;

import java.util.List;
import java.util.Optional;

public class EfficiencyUseCase implements IEfficiencyServicePort {

    private final IEfficiencyPersistencePort efficiencyPersistencePort;

    public EfficiencyUseCase(IEfficiencyPersistencePort efficiencyPersistencePort) {
        this.efficiencyPersistencePort = efficiencyPersistencePort;
    }

    @Override
    public List<OrderEfficiency> getOrdersEfficiency(Long restaurantId, Long ownerId) {
        validateParameters(restaurantId, ownerId);
        validateOwnership(restaurantId, ownerId);
        return efficiencyPersistencePort.findOrdersEfficiencyByRestaurant(restaurantId);
    }

    @Override
    public List<EmployeeEfficiency> getEmployeesEfficiencyRanking(Long restaurantId, Long ownerId) {
        validateParameters(restaurantId, ownerId);
        validateOwnership(restaurantId, ownerId);

        List<EmployeeEfficiency> employees = efficiencyPersistencePort.findEmployeesEfficiencyByRestaurant(restaurantId);
        enrichEmployeesWithUserInfo(employees);
        return employees;
    }

    private void validateParameters(Long restaurantId, Long ownerId) {
        if (restaurantId == null) {
            throw new InvalidRestaurantException(DomainConstants.Efficiency.ERROR_RESTAURANT_ID_REQUERIDO);
        }
        if (ownerId == null) {
            throw new InvalidRestaurantException(DomainConstants.Efficiency.ERROR_PROPIETARIO_ID_REQUERIDO);
        }
    }

    private void validateOwnership(Long restaurantId, Long ownerId) {
        Optional<Long> restaurantOwnerId = efficiencyPersistencePort.getRestaurantOwnerId(restaurantId);

        if (restaurantOwnerId.isEmpty()) {
            throw new InvalidRestaurantException(DomainConstants.Efficiency.ERROR_RESTAURANT_NO_ENCONTRADO);
        }

        if (!ownerId.equals(restaurantOwnerId.get())) {
            throw new InvalidRestaurantException(DomainConstants.Efficiency.ERROR_PROPIETARIO_NO_AUTORIZADO);
        }
    }

    private void enrichEmployeesWithUserInfo(List<EmployeeEfficiency> employees) {
        for (EmployeeEfficiency employee : employees) {
            try {
                Optional<UserInfo> userInfo = efficiencyPersistencePort.getUserInfo(employee.getEmpleadoId());
                if (userInfo.isPresent()) {
                    employee.setEmpleadoNombre(userInfo.get().getNombreCompleto());
                    if (isEmailEmpty(employee.getEmpleadoEmail())) {
                        employee.setEmpleadoEmail(userInfo.get().getEmail());
                    }
                } else {
                    setDefaultEmployeeName(employee);
                }
            } catch (Exception e) {
                setDefaultEmployeeName(employee);
            }
        }
    }

    private boolean isEmailEmpty(String email) {
        return email == null || email.trim().isEmpty();
    }

    private void setDefaultEmployeeName(EmployeeEfficiency employee) {
        employee.setEmpleadoNombre(
                DomainConstants.Efficiency.DEFAULT_EMPLOYEE_NAME_PREFIX +
                        DomainConstants.Efficiency.NAME_SEPARATOR +
                        employee.getEmpleadoId()
        );
    }
}