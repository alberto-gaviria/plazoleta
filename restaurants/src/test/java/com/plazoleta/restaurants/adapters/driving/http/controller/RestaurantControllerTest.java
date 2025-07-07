package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantSummaryResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IRestaurantSummaryMapper;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private IRestaurantSummaryMapper summaryMapper;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_ValidRequest_ShouldReturnCreated() {
        AddRestaurantRequest request = new AddRestaurantRequest();
        Restaurant restaurant = new Restaurant();
        Restaurant savedRestaurant = new Restaurant();
        RestaurantResponse expectedResponse = new RestaurantResponse();

        when(authentication.getName()).thenReturn("42");
        when(restaurantRequestMapper.addRequestToRestaurant(request)).thenReturn(restaurant);
        when(restaurantServicePort.saveRestaurant(restaurant, 42L)).thenReturn(savedRestaurant);
        when(restaurantResponseMapper.restaurantToResponse(savedRestaurant)).thenReturn(expectedResponse);

        ResponseEntity<RestaurantResponse> response = controller.createRestaurant(request, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(authentication).getName();
        verify(restaurantRequestMapper).addRequestToRestaurant(request);
        verify(restaurantServicePort).saveRestaurant(restaurant, 42L);
        verify(restaurantResponseMapper).restaurantToResponse(savedRestaurant);
    }

    @Test
    void getAllRestaurants_ValidPagination_ShouldReturnOk() {
        int page = 0;
        int size = 10;

        Page<RestaurantSummary> pageResult = new Page<>(Collections.emptyList(), 0, 10, 0);
        PageResponse<RestaurantSummaryResponse> mappedPage = new PageResponse<>();

        when(restaurantServicePort.getAllRestaurants(page, size)).thenReturn(pageResult);
        when(summaryMapper.toPageResponse(pageResult)).thenReturn(mappedPage);

        ResponseEntity<PageResponse<RestaurantSummaryResponse>> response =
                controller.getAllRestaurants(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mappedPage, response.getBody());

        verify(restaurantServicePort).getAllRestaurants(page, size);
        verify(summaryMapper).toPageResponse(pageResult);
    }
}
