package com.plazoleta.restaurants.adapters.driven.traceability.util;

public class TraceabilityAdapterConstants {

    private TraceabilityAdapterConstants() {

    }

    public static final String TRACEABILITY_SUCCESS =
            "Trazabilidad registrada exitosamente para el pedido: {}";
    public static final String TRACEABILITY_CLIENT_ERROR =
            "Error del cliente al registrar trazabilidad para el pedido: {}. Status: {}";
    public static final String TRACEABILITY_FEIGN_ERROR =
            "Error de comunicación con servicio de trazabilidad para el pedido: {}. Error: {}";
    public static final String TRACEABILITY_UNEXPECTED_ERROR =
            "Error inesperado al registrar trazabilidad para el pedido: {}. Error: {}";
}