package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.CreateOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderResponseMapper;
import com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(HttpConstants.Paths.PEDIDOS)
@Tag(name = "Pedidos", description = "API para gestión de pedidos")
@Validated
public class OrderController {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;

    public OrderController(IOrderServicePort orderServicePort,
                           IOrderRequestMapper orderRequestMapper,
                           IOrderResponseMapper orderResponseMapper) {
        this.orderServicePort = orderServicePort;
        this.orderRequestMapper = orderRequestMapper;
        this.orderResponseMapper = orderResponseMapper;
    }

    @Operation(summary = "Crear un nuevo pedido",
            description = "Permite a un cliente crear un nuevo pedido con platos de un mismo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo clientes pueden crear pedidos"),
            @ApiResponse(responseCode = "409", description = "El cliente ya tiene un pedido activo"),
            @ApiResponse(responseCode = "404", description = "Plato o restaurante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).CLIENTE)")
    public ResponseEntity<OrderResponse> createOrder(
            @Parameter(description = "Datos del pedido a crear", required = true)
            @Valid @RequestBody CreateOrderRequest request,
            Authentication authentication) {

        Long currentClientId = Long.valueOf(authentication.getName());
        Order order = orderRequestMapper.createRequestToOrder(request);
        Order savedOrder = orderServicePort.createOrder(order, currentClientId);

        OrderResponse response = orderResponseMapper.orderToResponse(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}