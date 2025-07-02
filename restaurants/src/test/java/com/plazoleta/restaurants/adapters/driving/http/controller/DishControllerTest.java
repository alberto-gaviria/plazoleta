package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishResponseMapper;
import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DishController.class)
class DishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDishServicePort dishServicePort;

    @MockBean
    private IDishRequestMapper dishRequestMapper;

    @MockBean
    private IDishResponseMapper dishResponseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private AddDishRequest validDishRequest;
    private Dish dish;
    private DishResponse dishResponse;
    private final Long validUserId = 1L;

    @BeforeEach
    void setUp() {
        validDishRequest = new AddDishRequest(
                "Pizza Margherita",
                BigDecimal.valueOf(25.50),
                "Pizza con salsa de tomate, mozzarella y albahaca",
                "https://example.com/pizza.jpg",
                2L,
                1L
        );

        dish = new Dish();
        dish.setId(1L);
        dish.setNombre("Pizza Margherita");
        dish.setPrecio(BigDecimal.valueOf(25.50));
        dish.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dish.setUrlImagen("https://example.com/pizza.jpg");
        dish.setIdCategoria(2L);
        dish.setIdRestaurante(1L);
        dish.setActivo(true);

        dishResponse = new DishResponse();
        dishResponse.setId(1L);
        dishResponse.setNombre("Pizza Margherita");
        dishResponse.setPrecio(BigDecimal.valueOf(25.50));
        dishResponse.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dishResponse.setUrlImagen("https://example.com/pizza.jpg");
        dishResponse.setIdCategoria(2L);
        dishResponse.setIdRestaurante(1L);
        dishResponse.setActivo(true);
    }

    @Test
    void createDish_WithValidData_ShouldReturnCreated() throws Exception {
        // Given
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class)))
                .thenReturn(dish);
        when(dishResponseMapper.dishToResponse(any(Dish.class)))
                .thenReturn(dishResponse);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), eq(validUserId));

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Pizza Margherita"))
                .andExpect(jsonPath("$.precio").value(25.50))
                .andExpect(jsonPath("$.descripcion").value("Pizza con salsa de tomate, mozzarella y albahaca"))
                .andExpect(jsonPath("$.urlImagen").value("https://example.com/pizza.jpg"))
                .andExpect(jsonPath("$.idCategoria").value(2L))
                .andExpect(jsonPath("$.idRestaurante").value(1L))
                .andExpect(jsonPath("$.activo").value(true));

        verify(dishRequestMapper).addRequestToDish(any(AddDishRequest.class));
        verify(dishServicePort).saveDish(any(Dish.class), eq(validUserId));
        verify(dishResponseMapper).dishToResponse(any(Dish.class));
    }

    @Test
    void createDish_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given - Request con múltiples campos inválidos
        AddDishRequest invalidRequest = new AddDishRequest(
                "", // nombre vacío
                BigDecimal.valueOf(-1), // precio negativo
                null, // descripción null
                null, // urlImagen null
                null, // idCategoria null
                null  // idRestaurante null
        );

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithNullName_ShouldReturnBadRequest() throws Exception {
        // Given
        validDishRequest.setNombre(null);

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithNullPrice_ShouldReturnBadRequest() throws Exception {
        // Given
        validDishRequest.setPrecio(null);

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithEmptyDescription_ShouldReturnBadRequest() throws Exception {
        // Given
        validDishRequest.setDescripcion("");

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithZeroPrice_ShouldReturnBadRequest() throws Exception {
        // Given
        validDishRequest.setPrecio(BigDecimal.ZERO);

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithNegativePrice_ShouldReturnBadRequest() throws Exception {
        // Given
        validDishRequest.setPrecio(BigDecimal.valueOf(-10.50));

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithMissingUserId_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithEmptyUserId_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithInvalidUserId_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", "invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithLongDescription_ShouldReturnBadRequest() throws Exception {
        // Given - Descripción de más de 500 caracteres
        String longDescription = "a".repeat(501);
        validDishRequest.setDescripcion(longDescription);

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithLongName_ShouldReturnBadRequest() throws Exception {
        // Given - Nombre de más de 100 caracteres
        String longName = "a".repeat(101);
        validDishRequest.setNombre(longName);

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithValidMinimumPrice_ShouldReturnCreated() throws Exception {
        // Given - Precio mínimo válido
        validDishRequest.setPrecio(BigDecimal.valueOf(0.01));
        dish.setPrecio(BigDecimal.valueOf(0.01));
        dishResponse.setPrecio(BigDecimal.valueOf(0.01));

        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class)))
                .thenReturn(dish);
        when(dishResponseMapper.dishToResponse(any(Dish.class)))
                .thenReturn(dishResponse);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), eq(validUserId));

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.precio").value(0.01));

        verify(dishServicePort).saveDish(any(Dish.class), eq(validUserId));
    }

    @Test
    void createDish_WithDifferentCategory_ShouldReturnCreated() throws Exception {
        // Given
        Long differentCategoryId = 5L;
        validDishRequest.setIdCategoria(differentCategoryId);
        dish.setIdCategoria(differentCategoryId);
        dishResponse.setIdCategoria(differentCategoryId);

        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class)))
                .thenReturn(dish);
        when(dishResponseMapper.dishToResponse(any(Dish.class)))
                .thenReturn(dishResponse);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), eq(validUserId));

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCategoria").value(differentCategoryId));

        verify(dishServicePort).saveDish(any(Dish.class), eq(validUserId));
    }

    @Test
    void createDish_WithDifferentRestaurant_ShouldReturnCreated() throws Exception {
        // Given
        Long differentRestaurantId = 10L;
        validDishRequest.setIdRestaurante(differentRestaurantId);
        dish.setIdRestaurante(differentRestaurantId);
        dishResponse.setIdRestaurante(differentRestaurantId);

        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class)))
                .thenReturn(dish);
        when(dishResponseMapper.dishToResponse(any(Dish.class)))
                .thenReturn(dishResponse);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), eq(validUserId));

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRestaurante").value(differentRestaurantId));

        verify(dishServicePort).saveDish(any(Dish.class), eq(validUserId));
    }

    @Test
    void createDish_ShouldPassCorrectUserIdToService() throws Exception {
        // Given
        Long specificUserId = 99L;
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class)))
                .thenReturn(dish);
        when(dishResponseMapper.dishToResponse(any(Dish.class)))
                .thenReturn(dishResponse);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), eq(specificUserId));

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", specificUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isCreated());

        verify(dishServicePort).saveDish(any(Dish.class), eq(specificUserId));
    }

    @Test
    void createDish_WithNullCategoryId_ShouldReturnBadRequest() throws Exception {
        // Given
        validDishRequest.setIdCategoria(null);

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }

    @Test
    void createDish_WithNullRestaurantId_ShouldReturnBadRequest() throws Exception {
        // Given
        validDishRequest.setIdRestaurante(null);

        // When & Then
        mockMvc.perform(post("/platos")
                        .header("X-User-Id", validUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDishRequest)))
                .andExpect(status().isBadRequest());

        verify(dishRequestMapper, never()).addRequestToDish(any());
        verify(dishServicePort, never()).saveDish(any(), any());
    }
}