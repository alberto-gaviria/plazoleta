package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.UpdateDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IDishResponseMapper;
import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishControllerTest {

    @InjectMocks
    private DishController controller;

    @Mock
    private IDishServicePort dishServicePort;

    @Mock
    private IDishRequestMapper dishRequestMapper;

    @Mock
    private IDishResponseMapper dishResponseMapper;

    @Mock
    private Authentication authentication;

    private AddDishRequest addDishRequest;
    private UpdateDishRequest updateDishRequest;
    private Dish dish;
    private Dish updatedDish;
    private DishResponse dishResponse;

    @BeforeEach
    void setUp() {
        // Setup AddDishRequest
        addDishRequest = new AddDishRequest();
        addDishRequest.setNombre("Hamburguesa Clásica");
        addDishRequest.setDescripcion("Deliciosa hamburguesa con carne, lechuga y tomate");
        addDishRequest.setPrecio(new BigDecimal("15000"));
        addDishRequest.setIdRestaurante(1L);
        addDishRequest.setIdCategoria(1L);
        addDishRequest.setUrlImagen("https://ejemplo.com/hamburguesa.jpg");

        // Setup UpdateDishRequest
        updateDishRequest = new UpdateDishRequest();
        updateDishRequest.setPrecio(new BigDecimal("18000"));
        updateDishRequest.setDescripcion("Hamburguesa premium con ingredientes gourmet");

        // Setup Dish
        dish = new Dish();
        dish.setId(1L);
        dish.setNombre("Hamburguesa Clásica");
        dish.setDescripcion("Deliciosa hamburguesa con carne, lechuga y tomate");
        dish.setPrecio(new BigDecimal("15000"));
        dish.setIdRestaurante(1L);
        dish.setIdCategoria(1L);
        dish.setUrlImagen("https://ejemplo.com/hamburguesa.jpg");
        dish.setActivo(true);

        // Setup Updated Dish
        updatedDish = new Dish();
        updatedDish.setId(1L);
        updatedDish.setNombre("Hamburguesa Clásica");
        updatedDish.setDescripcion("Hamburguesa premium con ingredientes gourmet");
        updatedDish.setPrecio(new BigDecimal("18000"));
        updatedDish.setIdRestaurante(1L);
        updatedDish.setIdCategoria(1L);
        updatedDish.setUrlImagen("https://ejemplo.com/hamburguesa.jpg");
        updatedDish.setActivo(true);

        // Setup DishResponse
        dishResponse = new DishResponse();
        dishResponse.setId(1L);
        dishResponse.setNombre("Hamburguesa Clásica");
        dishResponse.setDescripcion("Deliciosa hamburguesa con carne, lechuga y tomate");
        dishResponse.setPrecio(new BigDecimal("15000"));
        dishResponse.setIdRestaurante(1L);
        dishResponse.setIdCategoria(1L);
        dishResponse.setUrlImagen("https://ejemplo.com/hamburguesa.jpg");
        dishResponse.setActivo(true);
    }

    // ==================== CREATE DISH TESTS ====================

    @Test
    void createDish_WhenValidRequest_ShouldReturnCreated() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class))).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), anyLong());
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.createDish(addDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(dishResponse, result.getBody());
        verify(dishRequestMapper).addRequestToDish(any(AddDishRequest.class));
        verify(dishServicePort).saveDish(any(Dish.class), anyLong());
        verify(dishResponseMapper).dishToResponse(any(Dish.class));
    }

    @Test
    void createDish_WhenMapperReturnsDish_ShouldCallServiceAndReturnCreated() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(addDishRequest)).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(dish, 1L);
        when(dishResponseMapper.dishToResponse(dish)).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.createDish(addDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(dishResponse, result.getBody());
        verify(dishRequestMapper).addRequestToDish(addDishRequest);
        verify(dishServicePort).saveDish(dish, 1L);
        verify(dishResponseMapper).dishToResponse(dish);
    }

    @Test
    void createDish_WhenServiceCalled_ShouldDelegateToCorrectService() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class))).thenReturn(dish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        controller.createDish(addDishRequest, authentication);

        // Then
        verify(dishServicePort, times(1)).saveDish(dish, 1L);
    }

    @Test
    void createDish_WhenCalled_ShouldMapRequestCorrectly() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(addDishRequest)).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), anyLong());
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        controller.createDish(addDishRequest, authentication);

        // Then
        verify(dishRequestMapper, times(1)).addRequestToDish(addDishRequest);
    }

    @Test
    void createDish_WhenServiceExecutesSuccessfully_ShouldReturnCreatedStatus() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class))).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), anyLong());
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> response = controller.createDish(addDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dishResponse, response.getBody());
    }

    @Test
    void createDish_WhenRequestHasAllFields_ShouldProcessSuccessfully() {
        // Given
        AddDishRequest fullRequest = new AddDishRequest();
        fullRequest.setNombre("Pizza Margherita");
        fullRequest.setDescripcion("Pizza tradicional italiana con mozzarella y albahaca");
        fullRequest.setPrecio(new BigDecimal("25000"));
        fullRequest.setIdRestaurante(2L);
        fullRequest.setIdCategoria(2L);
        fullRequest.setUrlImagen("https://ejemplo.com/pizza.jpg");

        Dish mappedDish = new Dish();
        mappedDish.setId(2L);
        mappedDish.setNombre("Pizza Margherita");

        DishResponse fullResponse = new DishResponse();
        fullResponse.setId(2L);
        fullResponse.setNombre("Pizza Margherita");

        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(fullRequest)).thenReturn(mappedDish);
        doNothing().when(dishServicePort).saveDish(mappedDish, 1L);
        when(dishResponseMapper.dishToResponse(mappedDish)).thenReturn(fullResponse);

        // When
        ResponseEntity<DishResponse> result = controller.createDish(fullRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(fullResponse, result.getBody());
        verify(dishRequestMapper).addRequestToDish(fullRequest);
        verify(dishServicePort).saveDish(mappedDish, 1L);
        verify(dishResponseMapper).dishToResponse(mappedDish);
    }

    @Test
    void createDish_WhenControllerMethodCalled_ShouldFollowExpectedFlow() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class))).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), anyLong());
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.createDish(addDishRequest, authentication);

        // Then
        // Verify the complete flow
        verify(authentication, times(1)).getName();
        verify(dishRequestMapper, times(1)).addRequestToDish(addDishRequest);
        verify(dishServicePort, times(1)).saveDish(dish, 1L);
        verify(dishResponseMapper, times(1)).dishToResponse(dish);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(dishResponse, result.getBody());
    }

    @Test
    void createDish_WhenDifferentDishData_ShouldMapAndSaveCorrectly() {
        // Given
        AddDishRequest customRequest = new AddDishRequest();
        customRequest.setNombre("Ensalada César");
        customRequest.setDescripcion("Ensalada fresca con pollo y aderezo césar");
        customRequest.setPrecio(new BigDecimal("12000"));
        customRequest.setIdRestaurante(3L);
        customRequest.setIdCategoria(3L);
        customRequest.setUrlImagen("https://ejemplo.com/ensalada.jpg");

        Dish customDish = new Dish();
        customDish.setId(3L);
        customDish.setNombre("Ensalada César");

        DishResponse customResponse = new DishResponse();
        customResponse.setId(3L);
        customResponse.setNombre("Ensalada César");

        when(authentication.getName()).thenReturn("2");
        when(dishRequestMapper.addRequestToDish(customRequest)).thenReturn(customDish);
        doNothing().when(dishServicePort).saveDish(customDish, 2L);
        when(dishResponseMapper.dishToResponse(customDish)).thenReturn(customResponse);

        // When
        ResponseEntity<DishResponse> result = controller.createDish(customRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(customResponse, result.getBody());
        verify(dishRequestMapper).addRequestToDish(customRequest);
        verify(dishServicePort).saveDish(customDish, 2L);
        verify(dishResponseMapper).dishToResponse(customDish);
    }

    @Test
    void createDish_WhenMockingDependencies_ShouldInteractCorrectly() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class))).thenReturn(dish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        controller.createDish(addDishRequest, authentication);

        // Then
        verify(authentication).getName();
        verify(dishRequestMapper).addRequestToDish(addDishRequest);
        verify(dishServicePort).saveDish(dish, 1L);
        verify(dishResponseMapper).dishToResponse(dish);
        verifyNoMoreInteractions(dishRequestMapper, dishServicePort, dishResponseMapper);
    }

    @Test
    void createDish_WhenValidExecution_ShouldReturnCorrectResponseEntity() {
        // Given
        when(authentication.getName()).thenReturn("1");
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class))).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), anyLong());
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> response = controller.createDish(addDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dishResponse, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void createDish_WhenAuthenticationProvidesDifferentUserId_ShouldUseCorrectUserId() {
        // Given
        when(authentication.getName()).thenReturn("5");
        when(dishRequestMapper.addRequestToDish(any(AddDishRequest.class))).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(any(Dish.class), eq(5L));
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.createDish(addDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(dishServicePort).saveDish(dish, 5L);
        verify(authentication, times(1)).getName();
    }

    // ==================== UPDATE DISH TESTS ====================

    @Test
    void updateDish_WhenValidRequest_ShouldReturnOk() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(eq(dishId), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(updatedDish)).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(dishResponse, result.getBody());
        verify(dishServicePort).updateDish(dishId, updateDishRequest.getPrecio(), updateDishRequest.getDescripcion(), 1L);
        verify(dishResponseMapper).dishToResponse(updatedDish);
    }

    @Test
    void updateDish_WhenServiceReturnsUpdatedDish_ShouldMapAndReturnOk() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(dishId, updateDishRequest.getPrecio(), updateDishRequest.getDescripcion(), 1L)).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(updatedDish)).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dishResponse, result.getBody());
        verify(dishServicePort).updateDish(dishId, updateDishRequest.getPrecio(), updateDishRequest.getDescripcion(), 1L);
        verify(dishResponseMapper).dishToResponse(updatedDish);
    }

    @Test
    void updateDish_WhenServiceCalled_ShouldDelegateToCorrectService() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        verify(dishServicePort, times(1)).updateDish(dishId, updateDishRequest.getPrecio(), updateDishRequest.getDescripcion(), 1L);
    }

    @Test
    void updateDish_WhenCalled_ShouldExtractCorrectParameters() {
        // Given
        Long dishId = 2L;
        UpdateDishRequest customRequest = new UpdateDishRequest();
        customRequest.setPrecio(new BigDecimal("20000"));
        customRequest.setDescripcion("Nueva descripción actualizada");

        when(authentication.getName()).thenReturn("3");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        controller.updateDish(dishId, customRequest, authentication);

        // Then
        verify(dishServicePort, times(1)).updateDish(dishId, customRequest.getPrecio(), customRequest.getDescripcion(), 3L);
    }

    @Test
    void updateDish_WhenServiceExecutesSuccessfully_ShouldReturnOkStatus() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> response = controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dishResponse, response.getBody());
    }

    @Test
    void updateDish_WhenRequestHasAllFields_ShouldProcessSuccessfully() {
        // Given
        Long dishId = 1L;
        UpdateDishRequest fullRequest = new UpdateDishRequest();
        fullRequest.setPrecio(new BigDecimal("30000"));
        fullRequest.setDescripcion("Descripción completamente actualizada con nuevos ingredientes");

        Dish fullUpdatedDish = new Dish();
        fullUpdatedDish.setId(dishId);
        fullUpdatedDish.setPrecio(fullRequest.getPrecio());
        fullUpdatedDish.setDescripcion(fullRequest.getDescripcion());

        DishResponse fullResponse = new DishResponse();
        fullResponse.setId(dishId);
        fullResponse.setPrecio(fullRequest.getPrecio());

        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(dishId, fullRequest.getPrecio(), fullRequest.getDescripcion(), 1L)).thenReturn(fullUpdatedDish);
        when(dishResponseMapper.dishToResponse(fullUpdatedDish)).thenReturn(fullResponse);

        // When
        ResponseEntity<DishResponse> result = controller.updateDish(dishId, fullRequest, authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(fullResponse, result.getBody());
        verify(dishServicePort).updateDish(dishId, fullRequest.getPrecio(), fullRequest.getDescripcion(), 1L);
        verify(dishResponseMapper).dishToResponse(fullUpdatedDish);
    }

    @Test
    void updateDish_WhenControllerMethodCalled_ShouldFollowExpectedFlow() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        // Verify the complete flow
        verify(authentication, times(1)).getName();
        verify(dishServicePort, times(1)).updateDish(dishId, updateDishRequest.getPrecio(), updateDishRequest.getDescripcion(), 1L);
        verify(dishResponseMapper, times(1)).dishToResponse(updatedDish);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dishResponse, result.getBody());
    }

    @Test
    void updateDish_WhenDifferentDishId_ShouldUpdateCorrectDish() {
        // Given
        Long customDishId = 5L;
        UpdateDishRequest customRequest = new UpdateDishRequest();
        customRequest.setPrecio(new BigDecimal("22000"));
        customRequest.setDescripcion("Actualización de plato especial");

        Dish customUpdatedDish = new Dish();
        customUpdatedDish.setId(customDishId);

        DishResponse customResponse = new DishResponse();
        customResponse.setId(customDishId);

        when(authentication.getName()).thenReturn("2");
        when(dishServicePort.updateDish(customDishId, customRequest.getPrecio(), customRequest.getDescripcion(), 2L)).thenReturn(customUpdatedDish);
        when(dishResponseMapper.dishToResponse(customUpdatedDish)).thenReturn(customResponse);

        // When
        ResponseEntity<DishResponse> result = controller.updateDish(customDishId, customRequest, authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(customResponse, result.getBody());
        verify(dishServicePort).updateDish(customDishId, customRequest.getPrecio(), customRequest.getDescripcion(), 2L);
        verify(dishResponseMapper).dishToResponse(customUpdatedDish);
    }

    @Test
    void updateDish_WhenMockingDependencies_ShouldInteractCorrectly() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        verify(authentication).getName();
        verify(dishServicePort).updateDish(dishId, updateDishRequest.getPrecio(), updateDishRequest.getDescripcion(), 1L);
        verify(dishResponseMapper).dishToResponse(updatedDish);
        verifyNoMoreInteractions(dishServicePort, dishResponseMapper);
    }

    @Test
    void updateDish_WhenValidExecution_ShouldReturnCorrectResponseEntity() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> response = controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dishResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void updateDish_WhenAuthenticationProvidesDifferentUserId_ShouldUseCorrectUserId() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("7");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), eq(7L))).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(any(Dish.class))).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(dishServicePort).updateDish(dishId, updateDishRequest.getPrecio(), updateDishRequest.getDescripcion(), 7L);
        verify(authentication, times(1)).getName();
    }

    @Test
    void updateDish_WhenResponseMapperCalled_ShouldReturnMappedResponse() {
        // Given
        Long dishId = 1L;
        when(authentication.getName()).thenReturn("1");
        when(dishServicePort.updateDish(anyLong(), any(BigDecimal.class), anyString(), anyLong())).thenReturn(updatedDish);
        when(dishResponseMapper.dishToResponse(updatedDish)).thenReturn(dishResponse);

        // When
        ResponseEntity<DishResponse> result = controller.updateDish(dishId, updateDishRequest, authentication);

        // Then
        assertEquals(dishResponse, result.getBody());
        verify(dishResponseMapper, times(1)).dishToResponse(updatedDish);
    }
}