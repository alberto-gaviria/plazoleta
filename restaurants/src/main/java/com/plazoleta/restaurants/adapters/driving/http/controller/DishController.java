package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddDishRequest;
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
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @ApiResponse(responseCode = "403", description = "No autorizado para crear platos en este restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<DishResponse> createDish(
            @Parameter(description = "ID del usuario que crea el plato", required = true)
            @RequestHeader("X-User-Id") @NotNull Long currentUserId,
            @Parameter(description = "Datos del plato a crear", required = true)
            @Valid @RequestBody AddDishRequest request) {

        Dish dish = dishRequestMapper.addRequestToDish(request);
        dishServicePort.saveDish(dish, currentUserId);

        DishResponse response = dishResponseMapper.dishToResponse(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}