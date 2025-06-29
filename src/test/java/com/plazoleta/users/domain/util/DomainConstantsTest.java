package com.plazoleta.users.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void domainConstants_ShouldNotBeInstantiable() {
        // Given
        Constructor<DomainConstants> constructor;

        try {
            constructor = DomainConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // When & Then
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor privado no encontrado");
        }
    }

    @Test
    void usuarioConstants_ShouldNotBeInstantiable() {
        // Given
        Constructor<DomainConstants.Usuario> constructor;

        try {
            constructor = DomainConstants.Usuario.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // When & Then
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor privado no encontrado");
        }
    }

    @Test
    void usuarioConstants_ShouldHaveCorrectValues() {
        // Then
        assertEquals(18, DomainConstants.Usuario.EDAD_MINIMA);
        assertEquals("El usuario no puede ser nulo", DomainConstants.Usuario.ERROR_USUARIO_NULO);
        assertEquals("El nombre es obligatorio", DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO);
        assertEquals("El apellido es obligatorio", DomainConstants.Usuario.ERROR_APELLIDO_REQUERIDO);
        assertEquals("El número de documento es obligatorio", DomainConstants.Usuario.ERROR_DOCUMENTO_REQUERIDO);
        assertEquals("El celular es obligatorio", DomainConstants.Usuario.ERROR_CELULAR_REQUERIDO);
        assertEquals("La fecha de nacimiento es obligatoria", DomainConstants.Usuario.ERROR_FECHA_NACIMIENTO_REQUERIDA);
        assertEquals("El correo es obligatorio", DomainConstants.Usuario.ERROR_CORREO_REQUERIDO);
        assertEquals("La clave es obligatoria", DomainConstants.Usuario.ERROR_CLAVE_REQUERIDA);
        assertEquals("El usuario debe ser mayor de edad", DomainConstants.Usuario.ERROR_MENOR_EDAD);
    }

    @Test
    void roleConstants_ShouldNotBeInstantiable() {
        // Given
        Constructor<DomainConstants.Role> constructor;

        try {
            constructor = DomainConstants.Role.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // When & Then
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor privado no encontrado");
        }
    }

    @Test
    void roleConstants_ShouldHaveCorrectRoleIds() {
        // Then
        assertEquals(1L, DomainConstants.Role.ADMINISTRADOR_ID);
        assertEquals(2L, DomainConstants.Role.PROPIETARIO_ID);
        assertEquals(3L, DomainConstants.Role.EMPLEADO_ID);
        assertEquals(4L, DomainConstants.Role.CLIENTE_ID);
    }

    @Test
    void roleConstants_ShouldHaveCorrectAuthorities() {
        // Then
        assertEquals("ROLE_ADMINISTRADOR", DomainConstants.Role.ADMINISTRADOR_AUTHORITY);
        assertEquals("ROLE_PROPIETARIO", DomainConstants.Role.PROPIETARIO_AUTHORITY);
        assertEquals("ROLE_EMPLEADO", DomainConstants.Role.EMPLEADO_AUTHORITY);
        assertEquals("ROLE_CLIENTE", DomainConstants.Role.CLIENTE_AUTHORITY);
    }

    @Test
    void roleConstants_ShouldHaveCorrectErrorMessage() {
        // Then
        assertEquals("Rol no válido con ID: ", DomainConstants.Role.ERROR_INVALID_ROLE_ID);
    }
}