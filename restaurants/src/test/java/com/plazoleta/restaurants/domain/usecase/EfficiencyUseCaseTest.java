package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import com.plazoleta.restaurants.domain.model.UserInfo;
import com.plazoleta.restaurants.domain.spi.IEfficiencyPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EfficiencyUseCaseTest {

    private IEfficiencyPersistencePort persistencePort;
    private EfficiencyUseCase efficiencyUseCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IEfficiencyPersistencePort.class);
        efficiencyUseCase = new EfficiencyUseCase(persistencePort);
    }

    @Test
    void getOrdersEfficiency_success() {
        Long restaurantId = 1L, ownerId = 100L;
        List<OrderEfficiency> orders = List.of(new OrderEfficiency());

        when(persistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(Optional.of(ownerId));
        when(persistencePort.findOrdersEfficiencyByRestaurant(restaurantId)).thenReturn(orders);

        List<OrderEfficiency> result = efficiencyUseCase.getOrdersEfficiency(restaurantId, ownerId);

        assertEquals(orders, result);
        verify(persistencePort).getRestaurantOwnerId(restaurantId);
        verify(persistencePort).findOrdersEfficiencyByRestaurant(restaurantId);
    }

    @Test
    void getOrdersEfficiency_nullRestaurantId_throwsException() {
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> efficiencyUseCase.getOrdersEfficiency(null, 1L)
        );
        assertEquals(DomainConstants.Efficiency.ERROR_RESTAURANT_ID_REQUERIDO, exception.getMessage());
    }

    @Test
    void getOrdersEfficiency_nullOwnerId_throwsException() {
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> efficiencyUseCase.getOrdersEfficiency(1L, null)
        );
        assertEquals(DomainConstants.Efficiency.ERROR_PROPIETARIO_ID_REQUERIDO, exception.getMessage());
    }

    @Test
    void getOrdersEfficiency_restaurantNotFound_throwsException() {
        when(persistencePort.getRestaurantOwnerId(1L)).thenReturn(Optional.empty());

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> efficiencyUseCase.getOrdersEfficiency(1L, 100L)
        );
        assertEquals(DomainConstants.Efficiency.ERROR_RESTAURANT_NO_ENCONTRADO, exception.getMessage());
    }

    @Test
    void getOrdersEfficiency_ownerNotAuthorized_throwsException() {
        when(persistencePort.getRestaurantOwnerId(1L)).thenReturn(Optional.of(200L));

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> efficiencyUseCase.getOrdersEfficiency(1L, 100L)
        );
        assertEquals(DomainConstants.Efficiency.ERROR_PROPIETARIO_NO_AUTORIZADO, exception.getMessage());
    }

    @Test
    void getEmployeesEfficiencyRanking_success_withUserInfoAndEmail() {
        Long restaurantId = 1L, ownerId = 100L;
        EmployeeEfficiency employee = new EmployeeEfficiency();
        employee.setEmpleadoId(10L);
        employee.setEmpleadoEmail("correo@example.com");

        UserInfo userInfo = new UserInfo();
        userInfo.setNombre("Juan");
        userInfo.setApellido("Pérez");
        userInfo.setEmail("otro@example.com");

        when(persistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(Optional.of(ownerId));
        when(persistencePort.findEmployeesEfficiencyByRestaurant(restaurantId)).thenReturn(List.of(employee));
        when(persistencePort.getUserInfo(10L)).thenReturn(Optional.of(userInfo));

        List<EmployeeEfficiency> result = efficiencyUseCase.getEmployeesEfficiencyRanking(restaurantId, ownerId);

        assertEquals(1, result.size());
        assertEquals("Juan Pérez", result.get(0).getEmpleadoNombre());
        assertEquals("correo@example.com", result.get(0).getEmpleadoEmail()); // no se cambia el email existente
    }

    @Test
    void getEmployeesEfficiencyRanking_emailEmpty_setsFromUserInfo() {
        Long restaurantId = 1L, ownerId = 100L;
        EmployeeEfficiency employee = new EmployeeEfficiency();
        employee.setEmpleadoId(10L);
        employee.setEmpleadoEmail("   "); // email vacío

        UserInfo userInfo = new UserInfo();
        userInfo.setNombre("Ana");
        userInfo.setApellido("Gómez");
        userInfo.setEmail("ana@example.com");

        when(persistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(Optional.of(ownerId));
        when(persistencePort.findEmployeesEfficiencyByRestaurant(restaurantId)).thenReturn(List.of(employee));
        when(persistencePort.getUserInfo(10L)).thenReturn(Optional.of(userInfo));

        List<EmployeeEfficiency> result = efficiencyUseCase.getEmployeesEfficiencyRanking(restaurantId, ownerId);

        assertEquals("Ana Gómez", result.get(0).getEmpleadoNombre());
        assertEquals("ana@example.com", result.get(0).getEmpleadoEmail());
    }

    @Test
    void getEmployeesEfficiencyRanking_userInfoNotFound_setsDefaultName() {
        Long restaurantId = 1L, ownerId = 100L;
        EmployeeEfficiency employee = new EmployeeEfficiency();
        employee.setEmpleadoId(99L);

        when(persistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(Optional.of(ownerId));
        when(persistencePort.findEmployeesEfficiencyByRestaurant(restaurantId)).thenReturn(List.of(employee));
        when(persistencePort.getUserInfo(99L)).thenReturn(Optional.empty());

        List<EmployeeEfficiency> result = efficiencyUseCase.getEmployeesEfficiencyRanking(restaurantId, ownerId);

        assertTrue(result.get(0).getEmpleadoNombre().startsWith(DomainConstants.Efficiency.DEFAULT_EMPLOYEE_NAME_PREFIX));
    }

    @Test
    void getEmployeesEfficiencyRanking_userInfoThrowsException_setsDefaultName() {
        Long restaurantId = 1L, ownerId = 100L;
        EmployeeEfficiency employee = new EmployeeEfficiency();
        employee.setEmpleadoId(55L);

        when(persistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(Optional.of(ownerId));
        when(persistencePort.findEmployeesEfficiencyByRestaurant(restaurantId)).thenReturn(List.of(employee));
        when(persistencePort.getUserInfo(55L)).thenThrow(new RuntimeException("Simulated error"));

        List<EmployeeEfficiency> result = efficiencyUseCase.getEmployeesEfficiencyRanking(restaurantId, ownerId);

        assertTrue(result.get(0).getEmpleadoNombre().startsWith(DomainConstants.Efficiency.DEFAULT_EMPLOYEE_NAME_PREFIX));
    }
}
