package com.plazoleta.restaurants.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void testPrivateConstructorOfDomainConstants() throws Exception {
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void testPrivateConstructorOfRestaurant() throws Exception {
        Constructor<DomainConstants.Restaurant> constructor = DomainConstants.Restaurant.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void testPrivateConstructorOfDish() throws Exception {
        Constructor<DomainConstants.Dish> constructor = DomainConstants.Dish.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void testPrivateConstructorOfOrder() throws Exception {
        Constructor<DomainConstants.Order> constructor = DomainConstants.Order.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }
}
