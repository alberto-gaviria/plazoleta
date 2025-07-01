package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
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
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "API para gestión de restaurantes")
public class RestaurantController {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;

    public RestaurantController(IRestaurantServicePort restaurantServicePort,
                                IRestaurantRequestMapper restaurantRequestMapper) {
        this.restaurantServicePort = restaurantServicePort;
        this.restaurantRequestMapper = restaurantRequestMapper;
    }

    @Operation(summary = "Crear un nuevo restaurante",
            description = "Permite a un administrador crear un nuevo restaurante en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - ID de administrador inválido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Usuario no tiene permisos de administrador"),
            @ApiResponse(responseCode = "409", description = "El restaurante ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Parameter(description = "ID del administrador que crea el restaurante", required = true)
            @RequestHeader("X-Admin-Id") @NotNull Long adminId,
            @Parameter(description = "Datos del restaurante a crear", required = true)
            @Valid @RequestBody AddRestaurantRequest request) {

        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(request);
        restaurantServicePort.saveRestaurant(restaurant, adminId);

        RestaurantResponse response = restaurantRequestMapper.restaurantToResponse(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}