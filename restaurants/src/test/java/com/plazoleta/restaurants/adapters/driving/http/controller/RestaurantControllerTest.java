package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController controller;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;

    @Mock
    private Authentication authentication;

    private AddRestaurantRequest addRestaurantRequest;
    private Restaurant restaurant;
    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setUp() {
        addRestaurantRequest = new AddRestaurantRequest();
        addRestaurantRequest.setNombre("Restaurante El Buen Sabor");
        addRestaurantRequest.setNit("900123456-7");
        addRestaurantRequest.setDireccion("Calle 123 #45-67");
        addRestaurantRequest.setTelefono("+573001234567");
        addRestaurantRequest.setUrlLogo("https://ejemplo.com/logo.png");
        addRestaurantRequest.setIdPropietario(2L);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNombre("Restaurante El Buen Sabor");
        restaurant.setNit("900123456-7");
        restaurant.setDireccion("Calle 123 #45-67");
        restaurant.setTelefono("+573001234567");
        restaurant.setUrlLogo("https://ejemplo.com/logo.png");
        restaurant.setIdPropietario(2L);

        restaurantResponse = new RestaurantResponse();
        restaurantResponse.setId(1L);
        restaurantResponse.setNombre("Restaurante El Buen Sabor");
        restaurantResponse.setNit("900123456-7");
        restaurantResponse.setDireccion("Calle 123 #45-67");
        restaurantResponse.setTelefono("+573001234567");
        restaurantResponse.setUrlLogo("https://ejemplo.com/logo.png");
    }

    @Test
    void createRestaurant_WhenValidRequest_ShouldReturnCreated() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), anyLong());
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(restaurantResponse, result.getBody());
        verify(restaurantRequestMapper).addRequestToRestaurant(any(AddRestaurantRequest.class));
        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class), anyLong());
        verify(restaurantResponseMapper).restaurantToResponse(any(Restaurant.class));
    }

    @Test
    void createRestaurant_WhenMapperReturnsRestaurant_ShouldCallServiceAndReturnCreated() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(addRestaurantRequest)).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(restaurant, 1L);
        when(restaurantResponseMapper.restaurantToResponse(restaurant)).thenReturn(restaurantResponse);

        // When
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(restaurantResponse, result.getBody());
        verify(restaurantRequestMapper).addRequestToRestaurant(addRestaurantRequest);
        verify(restaurantServicePort).saveRestaurant(restaurant, 1L);
        verify(restaurantResponseMapper).restaurantToResponse(restaurant);
    }

    @Test
    void createRestaurant_WhenServiceCalled_ShouldDelegateToCorrectService() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        verify(restaurantServicePort, times(1)).saveRestaurant(restaurant, 1L);
    }

    @Test
    void createRestaurant_WhenCalled_ShouldMapRequestCorrectly() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(addRestaurantRequest)).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), anyLong());
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        verify(restaurantRequestMapper, times(1)).addRequestToRestaurant(addRestaurantRequest);
    }

    @Test
    void createRestaurant_WhenServiceExecutesSuccessfully_ShouldReturnCreatedStatus() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), anyLong());
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        ResponseEntity<RestaurantResponse> response = controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restaurantResponse, response.getBody());
    }

    @Test
    void createRestaurant_WhenRequestHasAllFields_ShouldProcessSuccessfully() {
        // Given
        AddRestaurantRequest fullRequest = new AddRestaurantRequest();
        fullRequest.setNombre("Restaurante La Plaza");
        fullRequest.setNit("800987654-3");
        fullRequest.setDireccion("Avenida 456 #78-90");
        fullRequest.setTelefono("+573009876543");
        fullRequest.setUrlLogo("https://ejemplo.com/plaza-logo.png");
        fullRequest.setIdPropietario(3L);

        Restaurant mappedRestaurant = new Restaurant();
        mappedRestaurant.setId(2L);
        mappedRestaurant.setNombre("Restaurante La Plaza");

        RestaurantResponse fullResponse = new RestaurantResponse();
        fullResponse.setId(2L);
        fullResponse.setNombre("Restaurante La Plaza");

        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(fullRequest)).thenReturn(mappedRestaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(mappedRestaurant, 1L);
        when(restaurantResponseMapper.restaurantToResponse(mappedRestaurant)).thenReturn(fullResponse);

        // When
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(fullRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(fullResponse, result.getBody());
        verify(restaurantRequestMapper).addRequestToRestaurant(fullRequest);
        verify(restaurantServicePort).saveRestaurant(mappedRestaurant, 1L);
        verify(restaurantResponseMapper).restaurantToResponse(mappedRestaurant);
    }

    @Test
    void createRestaurant_WhenControllerMethodCalled_ShouldFollowExpectedFlow() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), anyLong());
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        // Verify the complete flow
        verify(authentication, times(1)).getName();
        verify(restaurantRequestMapper, times(1)).addRequestToRestaurant(addRestaurantRequest);
        verify(restaurantServicePort, times(1)).saveRestaurant(restaurant, 1L);
        verify(restaurantResponseMapper, times(1)).restaurantToResponse(restaurant);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(restaurantResponse, result.getBody());
    }

    @Test
    void createRestaurant_WhenDifferentRestaurantData_ShouldMapAndSaveCorrectly() {
        // Given
        AddRestaurantRequest customRequest = new AddRestaurantRequest();
        customRequest.setNombre("Comida Rápida Central");
        customRequest.setNit("700555444-1");
        customRequest.setDireccion("Centro Comercial 123");
        customRequest.setTelefono("+573001112233");
        customRequest.setUrlLogo("https://ejemplo.com/central-logo.png");
        customRequest.setIdPropietario(4L);

        Restaurant customRestaurant = new Restaurant();
        customRestaurant.setId(3L);
        customRestaurant.setNombre("Comida Rápida Central");

        RestaurantResponse customResponse = new RestaurantResponse();
        customResponse.setId(3L);
        customResponse.setNombre("Comida Rápida Central");

        when(authentication.getName()).thenReturn("2");
        when(restaurantRequestMapper.addRequestToRestaurant(customRequest)).thenReturn(customRestaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(customRestaurant, 2L);
        when(restaurantResponseMapper.restaurantToResponse(customRestaurant)).thenReturn(customResponse);

        // When
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(customRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(customResponse, result.getBody());
        verify(restaurantRequestMapper).addRequestToRestaurant(customRequest);
        verify(restaurantServicePort).saveRestaurant(customRestaurant, 2L);
        verify(restaurantResponseMapper).restaurantToResponse(customRestaurant);
    }

    @Test
    void createRestaurant_WhenMockingDependencies_ShouldInteractCorrectly() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        verify(authentication).getName();
        verify(restaurantRequestMapper).addRequestToRestaurant(addRestaurantRequest);
        verify(restaurantServicePort).saveRestaurant(restaurant, 1L);
        verify(restaurantResponseMapper).restaurantToResponse(restaurant);
        verifyNoMoreInteractions(restaurantRequestMapper, restaurantServicePort, restaurantResponseMapper);
    }

    @Test
    void createRestaurant_WhenValidExecution_ShouldReturnCorrectResponseEntity() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), anyLong());
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        ResponseEntity<RestaurantResponse> response = controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restaurantResponse, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void createRestaurant_WhenAuthenticationProvidesDifferentUserId_ShouldUseCorrectAdminId() {
        // Given
        when(authentication.getName()).thenReturn("5");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), eq(5L));
        when(restaurantResponseMapper.restaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        // When
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(restaurantServicePort).saveRestaurant(restaurant, 5L);
        verify(authentication, times(1)).getName();
    }

    @Test
    void createRestaurant_WhenResponseMapperCalled_ShouldReturnMappedResponse() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(restaurantRequestMapper.addRequestToRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(any(Restaurant.class), anyLong());
        when(restaurantResponseMapper.restaurantToResponse(restaurant)).thenReturn(restaurantResponse);

        // When
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(addRestaurantRequest, authentication);

        // Then
        assertEquals(restaurantResponse, result.getBody());
        verify(restaurantResponseMapper, times(1)).restaurantToResponse(restaurant);
    }
}