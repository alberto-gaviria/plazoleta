package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantSummaryResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantSummaryMapper;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;
import com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "API para gestión de restaurantes")
@Validated
public class RestaurantController {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IRestaurantSummaryMapper restaurantSummaryMapper;

    public RestaurantController(IRestaurantServicePort restaurantServicePort,
                                IRestaurantRequestMapper restaurantRequestMapper,
                                IRestaurantResponseMapper restaurantResponseMapper,
                                IRestaurantSummaryMapper restaurantSummaryMapper) {
        this.restaurantServicePort = restaurantServicePort;
        this.restaurantRequestMapper = restaurantRequestMapper;
        this.restaurantResponseMapper = restaurantResponseMapper;
        this.restaurantSummaryMapper = restaurantSummaryMapper;
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
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).ADMINISTRADOR)")
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody AddRestaurantRequest request,
            Authentication authentication) {

        Long adminId = Long.valueOf(authentication.getName());
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(request);
        restaurantServicePort.saveRestaurant(restaurant, adminId);

        RestaurantResponse response = restaurantResponseMapper.restaurantToResponse(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar restaurantes",
            description = "Permite a un cliente listar todos los restaurantes disponibles ordenados alfabéticamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token requerido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    @PreAuthorize("hasAuthority(T(com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants$Roles).CLIENTE)")
    public ResponseEntity<PageResponse<RestaurantSummaryResponse>> getAllRestaurants(
            @Parameter(description = "Número de página (inicia en 0)", example = "0")
            @RequestParam(defaultValue = HttpConstants.Pagination.DEFAULT_PAGE_VALUE)
            @Min(value = HttpConstants.Pagination.MIN_PAGE, message = "El número de página debe ser mayor o igual a " + HttpConstants.Pagination.MIN_PAGE)
            int page,

            @Parameter(description = "Cantidad de elementos por página", example = "10")
            @RequestParam(defaultValue = HttpConstants.Pagination.DEFAULT_SIZE_VALUE)
            @Min(value = HttpConstants.Pagination.MIN_SIZE, message = "El tamaño de página debe ser mayor a " + HttpConstants.Pagination.MIN_SIZE)
            @Max(value = HttpConstants.Pagination.MAX_SIZE, message = "El tamaño de página no puede ser mayor a " + HttpConstants.Pagination.MAX_SIZE)
            int size) {

        Page<RestaurantSummary> restaurantsPage = restaurantServicePort.getAllRestaurants(page, size);
        PageResponse<RestaurantSummaryResponse> response = restaurantSummaryMapper.toPageResponse(restaurantsPage);

        return ResponseEntity.ok(response);
    }
}
