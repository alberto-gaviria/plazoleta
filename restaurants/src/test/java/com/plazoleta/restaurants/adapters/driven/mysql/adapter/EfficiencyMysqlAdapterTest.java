package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.users.client.IUserServiceClient;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.domain.model.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EfficiencyMysqlAdapterTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IUserServiceClient userServiceClient;

    private EfficiencyMysqlAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new EfficiencyMysqlAdapter(orderRepository, restaurantRepository, userServiceClient);
    }

    @Test
    void findOrdersEfficiencyByRestaurant_returnsMappedList() {
        Object[] row = new Object[] {
                1L,
                Timestamp.valueOf("2025-07-09 10:00:00"),
                Timestamp.valueOf("2025-07-09 10:45:00"),
                100L,
                "ENTREGADO"
        };
        List<Object[]> mockList = new ArrayList<>();
        mockList.add(row);

        when(orderRepository.findCompletedOrdersEfficiencyByRestaurant(1L)).thenReturn(mockList);
        when(userServiceClient.getUserEmail(100L)).thenReturn("empleado.100@plazoleta.com");

        List<OrderEfficiency> result = adapter.findOrdersEfficiencyByRestaurant(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(45L, result.get(0).getTiempoTotalMinutos());
        assertEquals("empleado.100@plazoleta.com", result.get(0).getEmpleadoEmail());
    }

    @Test
    void findEmployeesEfficiencyByRestaurant_returnsSortedAndRankedList() {
        Object[] row1 = new Object[] { 1L, 10, 30.0 };
        Object[] row2 = new Object[] { 2L, 8, 25.0 };
        List<Object[]> rows = Arrays.asList(row1, row2);

        when(orderRepository.findEmployeesEfficiencyByRestaurant(1L)).thenReturn(rows);

        List<EmployeeEfficiency> result = adapter.findEmployeesEfficiencyByRestaurant(1L);

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getEmpleadoId());
        assertEquals(1, result.get(0).getRanking());
        assertEquals(1L, result.get(1).getEmpleadoId());
        assertEquals(2, result.get(1).getRanking());
    }

    @Test
    void getRestaurantOwnerId_returnsOwnerId() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setIdPropietario(123L);

        doReturn(Optional.of(restaurantEntity)).when(restaurantRepository).findById(2L);

        Optional<Long> result = adapter.getRestaurantOwnerId(2L);

        assertTrue(result.isPresent());
        assertEquals(123L, result.get());
    }

    @Test
    void getUserInfo_successfulCall_returnsUserInfo() {
        when(userServiceClient.getUserEmail(5L)).thenReturn("empleado.5@plazoleta.com");

        Optional<UserInfo> result = adapter.getUserInfo(5L);

        assertTrue(result.isPresent());
        assertEquals("empleado.5@plazoleta.com", result.get().getEmail());
    }

    @Test
    void getUserInfo_fails_returnsDefaultUserInfo() {
        when(userServiceClient.getUserEmail(9L)).thenThrow(new RuntimeException());

        Optional<UserInfo> result = adapter.getUserInfo(9L);

        assertTrue(result.isPresent());
        assertTrue(result.get().getEmail().startsWith("empleado.9@plazoleta.com"));
    }

    @Test
    void getRestaurantName_returnsName() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setNombre("Pizza House");

        doReturn(Optional.of(restaurantEntity)).when(restaurantRepository).findById(1L);

        Optional<String> result = adapter.getRestaurantName(1L);

        assertTrue(result.isPresent());
        assertEquals("Pizza House", result.get());
    }
}