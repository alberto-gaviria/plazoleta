package com.plazoleta.traceability.adapters.driving.http.controller;

import com.plazoleta.traceability.adapters.driving.http.dto.response.GetOrderTraceabilityResponse;
import com.plazoleta.traceability.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.traceability.adapters.driving.http.mapper.ITraceabilityResponseMapper;
import com.plazoleta.traceability.adapters.driving.http.util.HttpConstants;
import com.plazoleta.traceability.domain.api.ITraceabilityServicePort;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.util.paged.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(HttpConstants.Paths.TRACEABILITY)
@Tag(name = "Trazabilidad", description = "API para consultar la trazabilidad de pedidos")
@Validated
public class TraceabilityController {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceabilityResponseMapper traceabilityResponseMapper;

    public TraceabilityController(ITraceabilityServicePort traceabilityServicePort,
                                  ITraceabilityResponseMapper traceabilityResponseMapper) {
        this.traceabilityServicePort = traceabilityServicePort;
        this.traceabilityResponseMapper = traceabilityResponseMapper;
    }

    @Operation(summary = "Consultar trazabilidad del pedido",
            description = "Permite a un cliente consultar el historial de cambios de estado de su pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trazabilidad del pedido obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo clientes pueden consultar trazabilidad"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado o no pertenece al cliente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(HttpConstants.Paths.ORDER_TRACEABILITY)
    @PreAuthorize("hasAuthority(T(com.plazoleta.traceability.adapters.driving.http.util.HttpConstants$Roles).CLIENTE)")
    public ResponseEntity<PageResponse<GetOrderTraceabilityResponse>> getOrderTraceability(
            @Parameter(description = "ID del pedido", required = true, example = "1")
            @PathVariable Long orderId,

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

        Long currentClientId = Long.valueOf(authentication.getName());
        Page<OrderTraceability> traceabilityPage = traceabilityServicePort.getOrderTraceability(
                orderId, currentClientId, page, size);

        PageResponse<GetOrderTraceabilityResponse> response = traceabilityResponseMapper.toPageResponse(traceabilityPage);
        return ResponseEntity.ok(response);
    }
}
