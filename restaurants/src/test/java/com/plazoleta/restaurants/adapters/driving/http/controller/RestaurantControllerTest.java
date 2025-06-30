package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRestaurantServicePort restaurantServicePort;

    @MockBean
    private IRestaurantRequestMapper restaurantRequestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private AddRestaurantRequest addRestaurantRequest;
    private Restaurant restaurant;
    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setUp() {
        addRestaurantRequest = new AddRestaurantRequest(
                "Restaurante Test",
                "123456789",
                "Calle 123 #45-67",
                "+573001234567",
                "https://restaurante.com/logo.png",
                1L
        );

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNombre("Restaurante Test");
        restaurant.setNit("123456789");
        restaurant.setDireccion("Calle 123 #45-67");
        restaurant.setTelefono("+573001234567");
        restaurant.setUrlLogo("https://restaurante.com/logo.png");
        restaurant.setIdPropietario(1L);

        restaurantResponse = new RestaurantResponse();
        restaurantResponse.setId(1L);
        restaurantResponse.setNombre("Restaurante Test");
        restaurantResponse.setNit("123456789");
        restaurantResponse.setDireccion("Calle 123 #45-67");
        restaurantResponse.setTelefono("+573001234567");
        restaurantResponse.setUrlLogo("https://restaurante.com/logo.png");
        restaurantResponse.setIdPropietario(1L);
    }

    @Test
    void shouldCreateRestaurantSuccessfully() throws Exception {
        // Given
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class)))
                .thenReturn(restaurant);
        when(restaurantRequestMapper.restaurantToResponse(any(Restaurant.class)))
                .thenReturn(restaurantResponse);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class));

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Restaurante Test"))
                .andExpect(jsonPath("$.nit").value("123456789"))
                .andExpect(jsonPath("$.direccion").value("Calle 123 #45-67"))
                .andExpect(jsonPath("$.telefono").value("+573001234567"))
                .andExpect(jsonPath("$.urlLogo").value("https://restaurante.com/logo.png"))
                .andExpect(jsonPath("$.idPropietario").value(1L));

        verify(restaurantRequestMapper).addRequestToRestaurant(any(AddRestaurantRequest.class));
        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class));
        verify(restaurantRequestMapper).restaurantToResponse(any(Restaurant.class));
    }

    @Test
    void shouldReturnBadRequestWhenNombreIsBlank() throws Exception {
        // Given
        addRestaurantRequest.setNombre("");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldReturnBadRequestWhenNitIsInvalid() throws Exception {
        // Given
        addRestaurantRequest.setNit("abc123");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldReturnBadRequestWhenTelefonoIsInvalid() throws Exception {
        // Given
        addRestaurantRequest.setTelefono("123456789012345"); // Más de 13 caracteres

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldReturnBadRequestWhenTelefonoHasInvalidFormat() throws Exception {
        // Given
        addRestaurantRequest.setTelefono("abc123def"); // Formato inválido

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldReturnBadRequestWhenIdPropietarioIsNull() throws Exception {
        // Given
        addRestaurantRequest.setIdPropietario(null);

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldReturnBadRequestWhenDireccionIsBlank() throws Exception {
        // Given
        addRestaurantRequest.setDireccion("");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldReturnBadRequestWhenUrlLogoIsBlank() throws Exception {
        // Given
        addRestaurantRequest.setUrlLogo("");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any());
    }
}