package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AssignEmployeeToOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.CreateOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.DeliverOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.MarkOrderReadyRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderResponseMapper;
import com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.util.paged.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
            description = "Permite a un cliente crear un nuevo pedido con platos de un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo clientes pueden crear pedidos"),
            @ApiResponse(responseCode = "409", description = "El cliente ya tiene un pedido activo"),
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

    @Operation(summary = "Listar pedidos por estado",
            description = "Permite a un empleado listar los pedidos de su restaurante, con filtro opcional por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo empleados pueden realizar esta acción"),
            @ApiResponse(responseCode = "404", description = "Empleado sin restaurante asignado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).EMPLEADO)")
    public ResponseEntity<PageResponse<OrderResponse>> getOrdersByStatus(
            @Parameter(description = "Estado del pedido para filtrar (opcional)", example = "PENDIENTE")
            @RequestParam(required = false) String estado,

            @Parameter(description = "Número de página (inicia en 0)", example = "0")
            @RequestParam(defaultValue = HttpConstants.Pagination.DEFAULT_PAGE_VALUE)
            @Min(value = HttpConstants.Pagination.MIN_PAGE, message = "El número de página debe ser mayor o igual a " + HttpConstants.Pagination.MIN_PAGE)
            int page,

            @Parameter(description = "Cantidad de elementos por página", example = "10")
            @RequestParam(defaultValue = HttpConstants.Pagination.DEFAULT_SIZE_VALUE)
            @Min(value = HttpConstants.Pagination.MIN_SIZE, message = "El tamaño de página debe ser mayor a " + HttpConstants.Pagination.MIN_SIZE)
            @Max(value = HttpConstants.Pagination.MAX_SIZE, message = "El tamaño de página no puede ser mayor a " + HttpConstants.Pagination.MAX_SIZE)
            int size,
            Authentication authentication) {

        Long currentEmployeeId = Long.valueOf(authentication.getName());

        OrderStatus orderStatus = null;
        if (estado != null && !estado.trim().isEmpty()) {
            try {
                orderStatus = OrderStatus.valueOf(estado.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(HttpConstants.Messages.INVALID_ORDER_STATUS + estado);
            }
        }

        Page<Order> ordersPage = orderServicePort.getOrdersByEmployeeAndStatus(
                currentEmployeeId, orderStatus, page, size);

        PageResponse<OrderResponse> response = orderResponseMapper.toPageResponse(ordersPage);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Asignarse a un pedido",
            description = "Permite a un empleado asignarse a un pedido pendiente de su restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado asignado exitosamente al pedido"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo empleados pueden realizar esta acción"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "409", description = "El pedido ya tiene un empleado asignado o no está en estado pendiente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping(HttpConstants.Paths.ASSIGN_EMPLOYEE)
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).EMPLEADO)")
    public ResponseEntity<OrderResponse> assignEmployeeToOrder(
            @Parameter(description = "Datos para asignar empleado al pedido", required = true)
            @Valid @RequestBody AssignEmployeeToOrderRequest request,
            Authentication authentication) {

        Long currentEmployeeId = Long.valueOf(authentication.getName());
        Order updatedOrder = orderServicePort.assignEmployeeToOrder(request.getIdPedido(), currentEmployeeId);

        OrderResponse response = orderResponseMapper.orderToResponse(updatedOrder);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Marcar pedido como listo",
            description = "Permite a un empleado marcar un pedido como listo y enviar notificación al cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido marcado como listo y notificación enviada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo empleados pueden realizar esta acción"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "409", description = "El pedido no está en estado de preparación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping(HttpConstants.Paths.MARK_ORDER_READY)
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).EMPLEADO)")
    public ResponseEntity<OrderResponse> markOrderAsReady(
            @Parameter(description = "Datos para marcar pedido como listo", required = true)
            @Valid @RequestBody MarkOrderReadyRequest request,
            Authentication authentication) {

        Long currentEmployeeId = Long.valueOf(authentication.getName());
        Order updatedOrder = orderServicePort.markOrderAsReady(request.getIdPedido(), currentEmployeeId);

        OrderResponse response = orderResponseMapper.orderToResponse(updatedOrder);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Entregar pedido",
            description = "Permite a un empleado marcar un pedido como entregado validando el PIN de seguridad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido entregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o PIN incorrecto"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo empleados pueden realizar esta acción"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "409", description = "El pedido no está en estado listo o ya fue entregado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping(HttpConstants.Paths.DELIVER_ORDER)
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).EMPLEADO)")
    public ResponseEntity<OrderResponse> deliverOrder(
            @Parameter(description = "Datos para entregar el pedido con PIN de seguridad", required = true)
            @Valid @RequestBody DeliverOrderRequest request,
            Authentication authentication) {

        Long currentEmployeeId = Long.valueOf(authentication.getName());
        Order deliveredOrder = orderServicePort.deliverOrder(
                request.getIdPedido(),
                request.getPinSeguridad(),
                currentEmployeeId
        );

        OrderResponse response = orderResponseMapper.orderToResponse(deliveredOrder);
        return ResponseEntity.ok(response);
    }
}