package com.plazoleta.messaging.adapters.driving.http.controller;

import com.plazoleta.messaging.adapters.driving.http.dto.request.OrderReadyNotificationRequest;
import com.plazoleta.messaging.adapters.driving.http.dto.response.NotificationResponse;
import com.plazoleta.messaging.adapters.driving.http.mapper.INotificationResponseMapper;
import com.plazoleta.messaging.domain.api.INotificationServicePort;
import com.plazoleta.messaging.domain.model.SmsNotification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones SMS")
public class NotificationController {

    private final INotificationServicePort notificationServicePort;
    private final INotificationResponseMapper responseMapper;

    public NotificationController(INotificationServicePort notificationServicePort,
                                  INotificationResponseMapper responseMapper) {
        this.notificationServicePort = notificationServicePort;
        this.responseMapper = responseMapper;
    }

    @Operation(summary = "Enviar notificación de pedido listo")
    @PostMapping("/sms/order-ready")

    public ResponseEntity<NotificationResponse> sendOrderReadyNotification(
            @Valid @RequestBody OrderReadyNotificationRequest request) {

        SmsNotification notification = notificationServicePort.sendOrderReadyNotification(
                request.getOrderId(),
                request.getClientPhone(),
                request.getPin(),
                request.getRestaurantName()
        );

        NotificationResponse response = responseMapper.toResponse(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Obtener notificaciones por pedido")
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyAuthority('EMPLEADO', 'CLIENTE')")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByOrderId(@PathVariable String orderId) {
        List<SmsNotification> notifications = notificationServicePort.getNotificationsByOrderId(orderId);
        List<NotificationResponse> response = responseMapper.toResponseList(notifications);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener notificación por ID")
    @GetMapping("/{notificationId}")
    @PreAuthorize("hasAnyAuthority('EMPLEADO', 'CLIENTE')")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable String notificationId) {
        SmsNotification notification = notificationServicePort.getNotificationById(notificationId);
        NotificationResponse response = responseMapper.toResponse(notification);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Reintentar notificación fallida")
    @PostMapping("/{notificationId}/retry")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Void> retryFailedNotification(@PathVariable String notificationId) {
        notificationServicePort.retryFailedNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}