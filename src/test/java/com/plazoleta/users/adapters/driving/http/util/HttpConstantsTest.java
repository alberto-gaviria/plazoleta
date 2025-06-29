package com.plazoleta.users.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void httpConstants_ShouldNotBeInstantiable() {
        // Given
        Constructor<HttpConstants> constructor;

        try {
            constructor = HttpConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // When & Then
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor privado no encontrado");
        }
    }

    @Test
    void usuarioValidationConstants_ShouldNotBeInstantiable() {
        // Given
        Constructor<HttpConstants.UsuarioValidation> constructor;

        try {
            constructor = HttpConstants.UsuarioValidation.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // When & Then
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor privado no encontrado");
        }
    }

    @Test
    void usuarioValidationConstants_ShouldHaveCorrectPatterns() {
        // Then
        assertEquals("^[0-9]+$", HttpConstants.UsuarioValidation.DOCUMENTO_PATTERN);
        assertEquals("^\\+?[0-9]{1,13}$", HttpConstants.UsuarioValidation.CELULAR_PATTERN);
    }

    @Test
    void usuarioValidationConstants_ShouldHaveCorrectMessages() {
        // Then
        assertEquals("El nombre es obligatorio", HttpConstants.UsuarioValidation.NOMBRE_REQUERIDO);
        assertEquals("El apellido es obligatorio", HttpConstants.UsuarioValidation.APELLIDO_REQUERIDO);
        assertEquals("El número de documento es obligatorio", HttpConstants.UsuarioValidation.DOCUMENTO_REQUERIDO);
        assertEquals("El documento de identidad debe ser únicamente numérico", HttpConstants.UsuarioValidation.DOCUMENTO_NUMERICO);
        assertEquals("El celular es obligatorio", HttpConstants.UsuarioValidation.CELULAR_REQUERIDO);
        assertEquals("El teléfono debe contener un máximo de 13 caracteres y puede contener el símbolo +", HttpConstants.UsuarioValidation.CELULAR_FORMATO);
        assertEquals("La fecha de nacimiento es obligatoria", HttpConstants.UsuarioValidation.FECHA_NACIMIENTO_REQUERIDA);
        assertEquals("El correo es obligatorio", HttpConstants.UsuarioValidation.CORREO_REQUERIDO);
        assertEquals("Debe tener estructura de email válida", HttpConstants.UsuarioValidation.CORREO_FORMATO);
        assertEquals("La clave es obligatoria", HttpConstants.UsuarioValidation.CLAVE_REQUERIDA);
    }
}