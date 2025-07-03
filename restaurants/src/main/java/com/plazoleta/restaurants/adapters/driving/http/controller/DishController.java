package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.ToggleDishStatusRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.UpdateDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishResponseMapper;
import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.model.Dish;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
@Tag(name = "Platos", description = "API para gestión de platos")
public class DishController {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;

    public DishController(IDishServicePort dishServicePort,
                          IDishRequestMapper dishRequestMapper,
                          IDishResponseMapper dishResponseMapper) {
        this.dishServicePort = dishServicePort;
        this.dishRequestMapper = dishRequestMapper;
        this.dishResponseMapper = dishResponseMapper;
    }

    @Operation(summary = "Crear un nuevo plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para crear platos en este restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<DishResponse> createDish(
            @Parameter(description = "Datos del plato a crear", required = true)
            @Valid @RequestBody AddDishRequest request,
            Authentication authentication) {

        Long currentUserId = Long.valueOf(authentication.getName());
        Dish dish = dishRequestMapper.addRequestToDish(request);
        dishServicePort.saveDish(dish, currentUserId);

        DishResponse response = dishResponseMapper.dishToResponse(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Modificar un plato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato modificado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para modificar este plato"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{dishId}")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<DishResponse> updateDish(
            @Parameter(description = "ID del plato a modificar", required = true)
            @PathVariable Long dishId,
            @Parameter(description = "Datos a actualizar del plato", required = true)
            @Valid @RequestBody UpdateDishRequest request,
            Authentication authentication) {

        Long currentUserId = Long.valueOf(authentication.getName());
        Dish updatedDish = dishServicePort.updateDish(dishId, request.getPrecio(), request.getDescripcion(), currentUserId);
        DishResponse response = dishResponseMapper.dishToResponse(updatedDish);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Habilitar/Deshabilitar un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del plato actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para modificar platos de este restaurante"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{dishId}/estado")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<DishResponse> toggleDishStatus(
            @Parameter(description = "ID del plato a habilitar/deshabilitar", required = true)
            @PathVariable Long dishId,
            @Parameter(description = "Nuevo estado del plato", required = true)
            @Valid @RequestBody ToggleDishStatusRequest request,
            Authentication authentication) {

        Long currentUserId = Long.valueOf(authentication.getName());
        Dish updatedDish = dishServicePort.toggleDishStatus(dishId, request.getActivo(), currentUserId);
        DishResponse response = dishResponseMapper.dishToResponse(updatedDish);
        return ResponseEntity.ok(response);
    }
}