package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.ToggleDishStatusRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.UpdateDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishResponseMapper;
import com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants;
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
@RequestMapping(HttpConstants.Paths.PLATOS)
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

    @Operation(summary = "Crear un nuevo plato",
            description = "Permite a un propietario crear un nuevo plato en su restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo propietarios pueden crear platos"),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).PROPIETARIO)")
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

    @Operation(summary = "Modificar un plato existente",
            description = "Permite a un propietario modificar el precio y descripción de un plato de su restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo el propietario del restaurante puede modificar platos"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(HttpConstants.Paths.DISH_BY_ID)
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).PROPIETARIO)")
    public ResponseEntity<DishResponse> updateDish(
            @Parameter(description = "ID del plato a modificar", required = true)
            @PathVariable Long dishId,
            @Parameter(description = "Datos a actualizar del plato", required = true)
            @Valid @RequestBody UpdateDishRequest request,
            Authentication authentication) {

        Long currentUserId = Long.valueOf(authentication.getName());
        Dish updated = dishServicePort.updateDish(dishId, request.getPrecio(), request.getDescripcion(), currentUserId);

        DishResponse response = dishResponseMapper.dishToResponse(updated);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Habilitar/Deshabilitar un plato",
            description = "Permite a un propietario cambiar el estado activo/inactivo de un plato de su restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del plato actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Solo el propietario del restaurante puede cambiar el estado de platos"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping(HttpConstants.Paths.DISH_STATUS)
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).PROPIETARIO)")
    public ResponseEntity<DishResponse> toggleDishStatus(
            @Parameter(description = "ID del plato a habilitar/deshabilitar", required = true)
            @PathVariable Long dishId,
            @Parameter(description = "Nuevo estado del plato", required = true)
            @Valid @RequestBody ToggleDishStatusRequest request,
            Authentication authentication) {

        Long currentUserId = Long.valueOf(authentication.getName());
        Dish toggled = dishServicePort.toggleDishStatus(dishId, request.getActivo(), currentUserId);

        DishResponse response = dishResponseMapper.dishToResponse(toggled);
        return ResponseEntity.ok(response);
    }
}