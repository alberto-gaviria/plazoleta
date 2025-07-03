package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "API para gestión de restaurantes")
public class RestaurantController {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    public RestaurantController(IRestaurantServicePort restaurantServicePort,
                                IRestaurantRequestMapper restaurantRequestMapper,
                                IRestaurantResponseMapper restaurantResponseMapper) {
        this.restaurantServicePort = restaurantServicePort;
        this.restaurantRequestMapper = restaurantRequestMapper;
        this.restaurantResponseMapper = restaurantResponseMapper;
    }

    @Operation(summary = "Crear un nuevo restaurante",
            description = "Permite a un administrador crear un nuevo restaurante en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "403", description = "Prohibido - Usuario no tiene permisos de administrador"),
            @ApiResponse(responseCode = "409", description = "El restaurante ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody AddRestaurantRequest request,
            Authentication authentication) {

        Long adminId = Long.valueOf(authentication.getName());
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(request);
        restaurantServicePort.saveRestaurant(restaurant, adminId);

        RestaurantResponse response = restaurantResponseMapper.restaurantToResponse(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}