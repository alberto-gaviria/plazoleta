package com.plazoleta.restaurants.adapters.driven.mysql.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class AdapterConstantsTest {

    @Test
    void testPrivateConstructorsThrowException() {
        assertIllegalStateException(AdapterConstants.class);
        assertIllegalStateException(AdapterConstants.DatabaseColumns.class);
        assertIllegalStateException(AdapterConstants.ErrorMessages.class);
        assertIllegalStateException(AdapterConstants.LogMessages.class);
        assertIllegalStateException(AdapterConstants.TemporaryData.class);
        assertIllegalStateException(AdapterConstants.ValidationMessages.class);
        assertIllegalStateException(AdapterConstants.OrderConstants.class);
        assertIllegalStateException(AdapterConstants.EfficiencyConstants.class);
        assertIllegalStateException(AdapterConstants.TimeFormatConstants.class);
        assertIllegalStateException(AdapterConstants.EfficiencyQueryConstants.class);
        assertIllegalStateException(AdapterConstants.EfficiencyValidationConstants.class);
    }

    private void assertIllegalStateException(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Expected IllegalStateException for class: " + clazz.getName());
        } catch (Exception e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof IllegalStateException,
                       "Expected IllegalStateException for class: " + clazz.getName() + ", but got: " + (cause != null ? cause.getClass().getName() : e.getClass().getName()));
        }
    }
}
