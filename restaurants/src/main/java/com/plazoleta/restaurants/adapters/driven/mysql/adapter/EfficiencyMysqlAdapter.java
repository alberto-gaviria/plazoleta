package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.adapters.driven.users.client.IUserServiceClient;
import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import com.plazoleta.restaurants.domain.model.UserInfo;
import com.plazoleta.restaurants.domain.spi.IEfficiencyPersistencePort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EfficiencyMysqlAdapter implements IEfficiencyPersistencePort {

    private final IOrderRepository orderRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IUserServiceClient userServiceClient;

    public EfficiencyMysqlAdapter(IOrderRepository orderRepository,
                                  IRestaurantRepository restaurantRepository,
                                  IUserServiceClient userServiceClient) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public List<OrderEfficiency> findOrdersEfficiencyByRestaurant(Long restaurantId) {
        List<Object[]> results = orderRepository.findCompletedOrdersEfficiencyByRestaurant(restaurantId);
        return results.stream()
                .map(this::mapToOrderEfficiency)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeEfficiency> findEmployeesEfficiencyByRestaurant(Long restaurantId) {
        List<Object[]> results = orderRepository.findEmployeesEfficiencyByRestaurant(restaurantId);

        List<EmployeeEfficiency> employees = results.stream()
                .map(this::mapToEmployeeEfficiency)
                .sorted(Comparator.comparing(EmployeeEfficiency::getTiempoPromedioMinutos))
                .collect(Collectors.toList());

        for (int i = AdapterConstants.EfficiencyConstants.RANKING_START_INDEX;
             i < employees.size();
             i++) {
            employees.get(i).setRanking(i + AdapterConstants.EfficiencyConstants.RANKING_INCREMENT);
        }

        return employees;
    }

    @Override
    public Optional<Long> getRestaurantOwnerId(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> restaurant.getIdPropietario());
    }

    @Override
    public Optional<UserInfo> getUserInfo(Long userId) {
        try {
            String userEmail = userServiceClient.getUserEmail(userId);
            String emailPrefix = userEmail.split(AdapterConstants.EfficiencyConstants.EMAIL_SEPARATOR)[
                    AdapterConstants.EfficiencyConstants.EMAIL_PREFIX_INDEX];

            return Optional.of(new UserInfo(
                    userId,
                    AdapterConstants.EfficiencyConstants.DEFAULT_EMPLOYEE_NAME,
                    emailPrefix,
                    userEmail
            ));
        } catch (Exception e) {
            return Optional.of(new UserInfo(
                    userId,
                    AdapterConstants.EfficiencyConstants.DEFAULT_EMPLOYEE_NAME,
                    String.valueOf(userId),
                    AdapterConstants.TemporaryData.DEFAULT_EMPLOYEE_EMAIL_PREFIX +
                            userId +
                            AdapterConstants.TemporaryData.DEFAULT_EMAIL_DOMAIN
            ));
        }
    }

    @Override
    public Optional<String> getRestaurantName(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> restaurant.getNombre());
    }

    private OrderEfficiency mapToOrderEfficiency(Object[] row) {
        Long orderId = ((Number) row[AdapterConstants.EfficiencyConstants.ORDER_ID_INDEX]).longValue();

        LocalDateTime fechaInicio = convertToLocalDateTime(
                row[AdapterConstants.EfficiencyConstants.START_DATE_INDEX]);
        LocalDateTime fechaFin = convertToLocalDateTime(
                row[AdapterConstants.EfficiencyConstants.END_DATE_INDEX]);

        Long empleadoId = row[AdapterConstants.EfficiencyConstants.EMPLOYEE_ID_INDEX] != null ?
                ((Number) row[AdapterConstants.EfficiencyConstants.EMPLOYEE_ID_INDEX]).longValue() : null;
        String estado = (String) row[AdapterConstants.EfficiencyConstants.STATUS_INDEX];

        Long tiempoTotalMinutos = AdapterConstants.EfficiencyConstants.DEFAULT_TIME_MINUTES;
        if (fechaInicio != null && fechaFin != null) {
            Duration duration = Duration.between(fechaInicio, fechaFin);
            tiempoTotalMinutos = duration.toMinutes();
        }

        String empleadoEmail = getEmployeeEmailSafely(empleadoId);

        return new OrderEfficiency(
                orderId,
                fechaInicio,
                fechaFin,
                tiempoTotalMinutos,
                empleadoId,
                empleadoEmail,
                estado
        );
    }

    private EmployeeEfficiency mapToEmployeeEfficiency(Object[] row) {
        Long empleadoId = ((Number) row[AdapterConstants.EfficiencyConstants.EMPLOYEE_STAT_ID_INDEX]).longValue();
        Integer totalPedidos = ((Number) row[AdapterConstants.EfficiencyConstants.TOTAL_ORDERS_INDEX]).intValue();
        Double tiempoPromedioMinutos = ((Number) row[AdapterConstants.EfficiencyConstants.AVG_TIME_INDEX]).doubleValue();

        return new EmployeeEfficiency(
                empleadoId,
                null,
                null,
                totalPedidos,
                tiempoPromedioMinutos
        );
    }

    private LocalDateTime convertToLocalDateTime(Object dateObject) {
        if (dateObject == null) {
            return null;
        }

        if (dateObject instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) dateObject).toLocalDateTime();
        } else if (dateObject instanceof LocalDateTime) {
            return (LocalDateTime) dateObject;
        }

        return null;
    }

    private String getEmployeeEmailSafely(Long empleadoId) {
        if (empleadoId == null) {
            return null;
        }

        try {
            return userServiceClient.getUserEmail(empleadoId);
        } catch (Exception e) {
            return AdapterConstants.TemporaryData.DEFAULT_EMPLOYEE_EMAIL_PREFIX +
                    empleadoId +
                    AdapterConstants.TemporaryData.DEFAULT_EMAIL_DOMAIN;
        }
    }
}