package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantEfficiencyResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IEfficiencyResponseMapper;
import com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants;
import com.plazoleta.restaurants.domain.api.IEfficiencyServicePort;
import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import com.plazoleta.restaurants.domain.spi.IEfficiencyPersistencePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eficiencia")
@Tag(name = "Eficiencia de Pedidos", description = "API para consultar la eficiencia de pedidos del restaurante")
@Validated
public class EfficiencyController {

    private final IEfficiencyServicePort efficiencyServicePort;
    private final IEfficiencyPersistencePort efficiencyPersistencePort;
    private final IEfficiencyResponseMapper efficiencyResponseMapper;

    public EfficiencyController(IEfficiencyServicePort efficiencyServicePort,
                                IEfficiencyPersistencePort efficiencyPersistencePort,
                                IEfficiencyResponseMapper efficiencyResponseMapper) {
        this.efficiencyServicePort = efficiencyServicePort;
        this.efficiencyPersistencePort = efficiencyPersistencePort;
        this.efficiencyResponseMapper = efficiencyResponseMapper;
    }

    @Operation(summary = "Consultar eficiencia del restaurante",
            description = "Permite a un propietario consultar la eficiencia de pedidos y empleados de su restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eficiencia del restaurante obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo propietarios pueden consultar eficiencia"),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/restaurante/{restaurantId}")
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).PROPIETARIO)")
    public ResponseEntity<RestaurantEfficiencyResponse> getRestaurantEfficiency(
            @Parameter(description = "ID del restaurante", required = true, example = "1")
            @PathVariable Long restaurantId,
            Authentication authentication) {

        Long currentOwnerId = Long.valueOf(authentication.getName());

        List<OrderEfficiency> ordersEfficiency = efficiencyServicePort.getOrdersEfficiency(restaurantId, currentOwnerId);

        List<EmployeeEfficiency> employeesRanking = efficiencyServicePort.getEmployeesEfficiencyRanking(restaurantId, currentOwnerId);

        String restaurantName = efficiencyPersistencePort.getRestaurantName(restaurantId)
                .orElse("Restaurante " + restaurantId);

        RestaurantEfficiencyResponse response = efficiencyResponseMapper.toRestaurantResponse(
                restaurantId, restaurantName, ordersEfficiency, employeesRanking);

        return ResponseEntity.ok(response);
    }
}