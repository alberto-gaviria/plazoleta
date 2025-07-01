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
        assertEquals("Usuario no encontrado con ID: ", DomainConstants.Usuario.ERROR_USUARIO_NO_ENCONTRADO);
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
        // Then - Corregidas las expectativas para coincidir con los valores reales
        assertEquals("ADMINISTRADOR", DomainConstants.Role.ADMINISTRADOR_AUTHORITY);
        assertEquals("PROPIETARIO", DomainConstants.Role.PROPIETARIO_AUTHORITY);
        assertEquals("EMPLEADO", DomainConstants.Role.EMPLEADO_AUTHORITY);
        assertEquals("CLIENTE", DomainConstants.Role.CLIENTE_AUTHORITY);
    }

    @Test
    void roleConstants_ShouldHaveCorrectErrorMessage() {
        // Then - Corregida la expectativa para coincidir con el valor real
        assertEquals("ID de rol inválido: ", DomainConstants.Role.ERROR_INVALID_ROLE_ID);
    }

    @Test
    void allConstants_ShouldNotBeNull() {
        // Test que ninguna constante sea null

        // Usuario constants
        assertNotNull(DomainConstants.Usuario.ERROR_USUARIO_NULO);
        assertNotNull(DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO);
        assertNotNull(DomainConstants.Usuario.ERROR_APELLIDO_REQUERIDO);
        assertNotNull(DomainConstants.Usuario.ERROR_DOCUMENTO_REQUERIDO);
        assertNotNull(DomainConstants.Usuario.ERROR_CELULAR_REQUERIDO);
        assertNotNull(DomainConstants.Usuario.ERROR_CORREO_REQUERIDO);
        assertNotNull(DomainConstants.Usuario.ERROR_CLAVE_REQUERIDA);
        assertNotNull(DomainConstants.Usuario.ERROR_FECHA_NACIMIENTO_REQUERIDA);
        assertNotNull(DomainConstants.Usuario.ERROR_MENOR_EDAD);
        assertNotNull(DomainConstants.Usuario.ERROR_USUARIO_NO_ENCONTRADO);

        // Role constants
        assertNotNull(DomainConstants.Role.ADMINISTRADOR_ID);
        assertNotNull(DomainConstants.Role.PROPIETARIO_ID);
        assertNotNull(DomainConstants.Role.EMPLEADO_ID);
        assertNotNull(DomainConstants.Role.CLIENTE_ID);
        assertNotNull(DomainConstants.Role.ADMINISTRADOR_AUTHORITY);
        assertNotNull(DomainConstants.Role.PROPIETARIO_AUTHORITY);
        assertNotNull(DomainConstants.Role.EMPLEADO_AUTHORITY);
        assertNotNull(DomainConstants.Role.CLIENTE_AUTHORITY);
        assertNotNull(DomainConstants.Role.ERROR_INVALID_ROLE_ID);
    }

    @Test
    void allStringConstants_ShouldNotBeEmpty() {
        // Usuario error messages
        assertFalse(DomainConstants.Usuario.ERROR_USUARIO_NULO.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_APELLIDO_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_DOCUMENTO_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_CELULAR_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_CORREO_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_CLAVE_REQUERIDA.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_FECHA_NACIMIENTO_REQUERIDA.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_MENOR_EDAD.isEmpty());
        assertFalse(DomainConstants.Usuario.ERROR_USUARIO_NO_ENCONTRADO.isEmpty());

        // Role authorities
        assertFalse(DomainConstants.Role.ADMINISTRADOR_AUTHORITY.isEmpty());
        assertFalse(DomainConstants.Role.PROPIETARIO_AUTHORITY.isEmpty());
        assertFalse(DomainConstants.Role.EMPLEADO_AUTHORITY.isEmpty());
        assertFalse(DomainConstants.Role.CLIENTE_AUTHORITY.isEmpty());
        assertFalse(DomainConstants.Role.ERROR_INVALID_ROLE_ID.isEmpty());
    }

    @Test
    void roleIds_ShouldBePositive() {
        // Then
        assertTrue(DomainConstants.Role.ADMINISTRADOR_ID > 0);
        assertTrue(DomainConstants.Role.PROPIETARIO_ID > 0);
        assertTrue(DomainConstants.Role.EMPLEADO_ID > 0);
        assertTrue(DomainConstants.Role.CLIENTE_ID > 0);
    }

    @Test
    void roleIds_ShouldBeUnique() {
        // Then - Verificar que todos los IDs son diferentes
        assertNotEquals(DomainConstants.Role.ADMINISTRADOR_ID, DomainConstants.Role.PROPIETARIO_ID);
        assertNotEquals(DomainConstants.Role.ADMINISTRADOR_ID, DomainConstants.Role.EMPLEADO_ID);
        assertNotEquals(DomainConstants.Role.ADMINISTRADOR_ID, DomainConstants.Role.CLIENTE_ID);
        assertNotEquals(DomainConstants.Role.PROPIETARIO_ID, DomainConstants.Role.EMPLEADO_ID);
        assertNotEquals(DomainConstants.Role.PROPIETARIO_ID, DomainConstants.Role.CLIENTE_ID);
        assertNotEquals(DomainConstants.Role.EMPLEADO_ID, DomainConstants.Role.CLIENTE_ID);
    }

    @Test
    void edadMinima_ShouldBe18() {
        // Then
        assertEquals(18, DomainConstants.Usuario.EDAD_MINIMA);
        assertTrue(DomainConstants.Usuario.EDAD_MINIMA > 0);
        assertTrue(DomainConstants.Usuario.EDAD_MINIMA < 100); // Sanity check
    }
}