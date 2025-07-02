package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantResponseMapper;
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
import static org.mockito.ArgumentMatchers.eq;
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

    @MockBean
    private IRestaurantResponseMapper restaurantResponseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private AddRestaurantRequest addRestaurantRequest;
    private Restaurant restaurant;
    private RestaurantResponse restaurantResponse;
    private final Long validAdminId = 1L;

    @BeforeEach
    void setUp() {
        addRestaurantRequest = new AddRestaurantRequest(
                "Restaurante Test",
                "123456789",
                "Calle 123 #45-67",
                "+573001234567",
                "https://restaurante.com/logo.png",
                2L // ID del propietario (diferente al admin)
        );

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNombre("Restaurante Test");
        restaurant.setNit("123456789");
        restaurant.setDireccion("Calle 123 #45-67");
        restaurant.setTelefono("+573001234567");
        restaurant.setUrlLogo("https://restaurante.com/logo.png");
        restaurant.setIdPropietario(2L);

        restaurantResponse = new RestaurantResponse();
        restaurantResponse.setId(1L);
        restaurantResponse.setNombre("Restaurante Test");
        restaurantResponse.setNit("123456789");
        restaurantResponse.setDireccion("Calle 123 #45-67");
        restaurantResponse.setTelefono("+573001234567");
        restaurantResponse.setUrlLogo("https://restaurante.com/logo.png");
        restaurantResponse.setIdPropietario(2L);
    }

    @Test
    void shouldCreateRestaurantSuccessfully() throws Exception {
        // Given
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class)))
                .thenReturn(restaurant);
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class)))
                .thenReturn(restaurantResponse);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Restaurante Test"))
                .andExpect(jsonPath("$.nit").value("123456789"))
                .andExpect(jsonPath("$.direccion").value("Calle 123 #45-67"))
                .andExpect(jsonPath("$.telefono").value("+573001234567"))
                .andExpect(jsonPath("$.urlLogo").value("https://restaurante.com/logo.png"))
                .andExpect(jsonPath("$.idPropietario").value(2L));

        verify(restaurantRequestMapper).addRequestToRestaurant(any(AddRestaurantRequest.class));
        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));
        verify(restaurantResponseMapper).restaurantToResponse(any(Restaurant.class));
    }

    @Test
    void shouldReturnBadRequestWhenAdminIdHeaderIsMissing() throws Exception {
        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenAdminIdIsNull() throws Exception {
        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenAdminIdIsInvalid() throws Exception {
        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", "invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenNombreIsBlank() throws Exception {
        // Given
        addRestaurantRequest.setNombre("");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenNitIsInvalid() throws Exception {
        // Given
        addRestaurantRequest.setNit("abc123");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenTelefonoIsInvalid() throws Exception {
        // Given
        addRestaurantRequest.setTelefono("123456789012345"); // Más de 13 caracteres

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenTelefonoHasInvalidFormat() throws Exception {
        // Given
        addRestaurantRequest.setTelefono("abc123def"); // Formato inválido

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenIdPropietarioIsNull() throws Exception {
        // Given
        addRestaurantRequest.setIdPropietario(null);

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenDireccionIsBlank() throws Exception {
        // Given
        addRestaurantRequest.setDireccion("");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenUrlLogoIsBlank() throws Exception {
        // Given
        addRestaurantRequest.setUrlLogo("");

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isBadRequest());

        verify(restaurantRequestMapper, never()).addRequestToRestaurant(any());
        verify(restaurantServicePort, never()).saveRestaurant(any(), any());
    }

    @Test
    void shouldAcceptValidRestaurantWithValidAdminId() throws Exception {
        // Given - Crear un request completamente válido
        AddRestaurantRequest validRequest = new AddRestaurantRequest(
                "Pizza Palace",
                "987654321",
                "Carrera 80 #30-40",
                "+573009876543",
                "https://pizzapalace.com/logo.png",
                3L
        );

        Restaurant validRestaurant = new Restaurant();
        validRestaurant.setNombre("Pizza Palace");
        validRestaurant.setNit("987654321");
        validRestaurant.setDireccion("Carrera 80 #30-40");
        validRestaurant.setTelefono("+573009876543");
        validRestaurant.setUrlLogo("https://pizzapalace.com/logo.png");
        validRestaurant.setIdPropietario(3L);

        RestaurantResponse validResponse = new RestaurantResponse();
        validResponse.setId(2L);
        validResponse.setNombre("Pizza Palace");
        validResponse.setNit("987654321");
        validResponse.setDireccion("Carrera 80 #30-40");
        validResponse.setTelefono("+573009876543");
        validResponse.setUrlLogo("https://pizzapalace.com/logo.png");
        validResponse.setIdPropietario(3L);

        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class)))
                .thenReturn(validRestaurant);
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class)))
                .thenReturn(validResponse);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pizza Palace"))
                .andExpect(jsonPath("$.nit").value("987654321"))
                .andExpect(jsonPath("$.idPropietario").value(3L));

        verify(restaurantRequestMapper).addRequestToRestaurant(any(AddRestaurantRequest.class));
        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));
        verify(restaurantResponseMapper).restaurantToResponse(any(Restaurant.class));
    }

    @Test
    void shouldPassCorrectAdminIdToService() throws Exception {
        // Given
        Long specificAdminId = 7L;
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class)))
                .thenReturn(restaurant);
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class)))
                .thenReturn(restaurantResponse);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(specificAdminId));

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", specificAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isCreated());

        // Verificar que se pasó el ID correcto del administrador
        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(specificAdminId));
    }

    @Test
    void shouldAcceptValidTelefonoWithPlus() throws Exception {
        // Given
        AddRestaurantRequest requestWithPlus = new AddRestaurantRequest(
                "Burger King",
                "555666777",
                "Avenida Principal #100-200",
                "+573001112233",
                "https://burgerking.com/logo.png",
                4L
        );

        Restaurant restaurantWithPlus = new Restaurant();
        restaurantWithPlus.setNombre("Burger King");
        restaurantWithPlus.setNit("555666777");
        restaurantWithPlus.setDireccion("Avenida Principal #100-200");
        restaurantWithPlus.setTelefono("+573001112233");
        restaurantWithPlus.setUrlLogo("https://burgerking.com/logo.png");
        restaurantWithPlus.setIdPropietario(4L);

        RestaurantResponse responseWithPlus = new RestaurantResponse();
        responseWithPlus.setNombre("Burger King");
        responseWithPlus.setTelefono("+573001112233");

        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class)))
                .thenReturn(restaurantWithPlus);
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class)))
                .thenReturn(responseWithPlus);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestWithPlus)))
                .andExpect(status().isCreated());

        verify(restaurantRequestMapper).addRequestToRestaurant(any(AddRestaurantRequest.class));
        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));
        verify(restaurantResponseMapper).restaurantToResponse(any(Restaurant.class));
    }

    @Test
    void shouldAcceptValidNitOnlyNumbers() throws Exception {
        // Given
        addRestaurantRequest.setNit("999888777666");

        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class)))
                .thenReturn(restaurant);
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class)))
                .thenReturn(restaurantResponse);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));

        // When & Then
        mockMvc.perform(post("/restaurantes")
                        .header("X-Admin-Id", validAdminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRestaurantRequest)))
                .andExpect(status().isCreated());

        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(validAdminId));
    }
}