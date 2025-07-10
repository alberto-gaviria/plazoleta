package com.plazoleta.traceability.adapters.driving.http.controller;

import com.plazoleta.traceability.adapters.driving.http.dto.request.RecordStatusChangeRequest;
import com.plazoleta.traceability.adapters.driving.http.mapper.IRecordStatusChangeRequestMapper;
import com.plazoleta.traceability.domain.api.ITraceabilityServicePort;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@Tag(name = "Trazabilidad Interna", description = "API interna para registrar cambios de estado")
@Validated
public class InternalTraceabilityController {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final IRecordStatusChangeRequestMapper statusChangeMapper;

    public InternalTraceabilityController(ITraceabilityServicePort traceabilityServicePort,
                                          IRecordStatusChangeRequestMapper statusChangeMapper) {
        this.traceabilityServicePort = traceabilityServicePort;
        this.statusChangeMapper = statusChangeMapper;
    }

    @Operation(summary = "Registrar cambio de estado del pedido",
            description = "Endpoint interno para registrar los cambios de estado de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cambio de estado registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/record-status-change")
    public ResponseEntity<Void> recordStatusChange(@Valid @RequestBody RecordStatusChangeRequest request) {

        traceabilityServicePort.recordOrderStatusChange(
                request.getOrderId(),
                request.getClientId(),
                request.getClientEmail(),
                request.getPreviousStatus(),
                request.getNewStatus(),
                request.getEmployeeId(),
                request.getEmployeeEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}