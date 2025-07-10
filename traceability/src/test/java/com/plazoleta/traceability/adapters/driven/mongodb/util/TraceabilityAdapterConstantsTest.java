package com.plazoleta.traceability.adapters.driven.mongodb.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class TraceabilityAdapterConstantsTest {

    @Test
    void testPrivateConstructorOfOuterClass() throws Exception {
        Constructor<TraceabilityAdapterConstants> constructor =
                TraceabilityAdapterConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        TraceabilityAdapterConstants instance = constructor.newInstance();
        assertNotNull(instance);
    }

    @Test
    void testPrivateConstructorOfErrorMessages() throws Exception {
        Constructor<TraceabilityAdapterConstants.ErrorMessages> constructor =
                TraceabilityAdapterConstants.ErrorMessages.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        TraceabilityAdapterConstants.ErrorMessages instance = constructor.newInstance();
        assertNotNull(instance);
    }

    @Test
    void testPrivateConstructorOfFeignMessages() throws Exception {
        Constructor<TraceabilityAdapterConstants.FeignMessages> constructor =
                TraceabilityAdapterConstants.FeignMessages.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        TraceabilityAdapterConstants.FeignMessages instance = constructor.newInstance();
        assertNotNull(instance);
    }
}
