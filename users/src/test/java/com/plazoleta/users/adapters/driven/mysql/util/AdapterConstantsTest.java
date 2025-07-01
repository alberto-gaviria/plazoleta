package com.plazoleta.users.adapters.driven.mysql.util;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.*;

class AdapterConstantsTest {

    @Test
    void adapterConstants_ShouldNotBeInstantiable() {
        // Given
        Constructor<AdapterConstants> constructor;

        try {
            constructor = AdapterConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // When & Then
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor privado no encontrado");
        }
    }

    @Test
    void errorMessagesConstants_ShouldNotBeInstantiable() {
        // Given
        Constructor<AdapterConstants.ErrorMessages> constructor;

        try {
            constructor = AdapterConstants.ErrorMessages.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // When & Then
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor privado no encontrado");
        }
    }

    @Test
    void errorMessagesConstants_ShouldHaveCorrectValues() {
        // Then
        assertEquals("Ya existe un usuario con ese correo electrónico",
                AdapterConstants.ErrorMessages.USUARIO_CORREO_DUPLICADO);
        assertEquals("Ya existe un usuario con ese número de documento",
                AdapterConstants.ErrorMessages.USUARIO_DOCUMENTO_DUPLICADO);
        assertEquals("No se encontró el usuario solicitado",
                AdapterConstants.ErrorMessages.USUARIO_NO_ENCONTRADO);
    }
}