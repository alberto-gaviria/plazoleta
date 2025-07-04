package com.plazoleta.users.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    // --- No-instantiability tests ---

    @Test
    void httpConstants_ShouldNotBeInstantiable() throws NoSuchMethodException {
        Constructor<HttpConstants> ctor = HttpConstants.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
    }

    @Test
    void rolesConstants_ShouldNotBeInstantiable() throws NoSuchMethodException {
        Constructor<HttpConstants.Roles> ctor =
                HttpConstants.Roles.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        assertThrows(InvocationTargetException.class, ctor::newInstance);
    }

    @Test
    void pathsConstants_ShouldNotBeInstantiable() throws NoSuchMethodException {
        Constructor<HttpConstants.Paths> ctor =
                HttpConstants.Paths.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        assertThrows(InvocationTargetException.class, ctor::newInstance);
    }

    @Test
    void messagesConstants_ShouldNotBeInstantiable() throws NoSuchMethodException {
        Constructor<HttpConstants.Messages> ctor =
                HttpConstants.Messages.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        assertThrows(InvocationTargetException.class, ctor::newInstance);
    }

    @Test
    void usuarioValidationConstants_ShouldNotBeInstantiable() throws NoSuchMethodException {
        Constructor<HttpConstants.UsuarioValidation> ctor =
                HttpConstants.UsuarioValidation.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        assertThrows(InvocationTargetException.class, ctor::newInstance);
    }

    // --- Value tests for each group ---

    @Test
    void rolesConstants_ShouldHaveCorrectValues() {
        assertEquals("ADMINISTRADOR", HttpConstants.Roles.ADMINISTRADOR);
        assertEquals("PROPIETARIO",  HttpConstants.Roles.PROPIETARIO);
    }

    @Test
    void pathsConstants_ShouldHaveCorrectValues() {
        assertEquals("/usuarios",     HttpConstants.Paths.USUARIOS);
        assertEquals("/propietario",  HttpConstants.Paths.SUB_PROPIETARIO);
        assertEquals("/cliente",      HttpConstants.Paths.SUB_CLIENTE);
        assertEquals("/empleado",     HttpConstants.Paths.SUB_EMPLEADO);
        assertEquals("/{id}",         HttpConstants.Paths.BY_ID);
        assertEquals("/me",           HttpConstants.Paths.ME);
        assertEquals("/auth",         HttpConstants.Paths.AUTH);
        assertEquals("/login",        HttpConstants.Paths.LOGIN);
    }

    @Test
    void messagesConstants_ShouldHaveCorrectValues() {
        // Success
        assertEquals("Propietario creado exitosamente",
                HttpConstants.Messages.CREATE_OWNER_SUCCESS);
        assertEquals("Cliente registrado exitosamente",
                HttpConstants.Messages.CREATE_CLIENT_SUCCESS);
        assertEquals("Empleado creado exitosamente",
                HttpConstants.Messages.CREATE_EMPLOYEE_SUCCESS);
        assertEquals("Autenticación exitosa",
                HttpConstants.Messages.AUTH_SUCCESS);
        assertEquals("Usuario encontrado",
                HttpConstants.Messages.USER_FOUND);

        // Errors
        assertEquals("Datos de entrada inválidos",
                HttpConstants.Messages.INVALID_INPUT);
        assertEquals("Error interno del servidor",
                HttpConstants.Messages.INTERNAL_ERROR);
        assertEquals("Solo administradores pueden crear propietarios",
                HttpConstants.Messages.FORBIDDEN_ONLY_ADMIN);
        assertEquals("Solo propietarios pueden crear empleados",
                HttpConstants.Messages.FORBIDDEN_ONLY_OWNER);
        assertEquals("Credenciales inválidas",
                HttpConstants.Messages.CREDENTIALS_INVALID);
        assertEquals("Usuario no encontrado",
                HttpConstants.Messages.USER_NOT_FOUND);
        assertEquals("No autenticado",
                HttpConstants.Messages.UNAUTHORIZED);
    }

    @Test
    void usuarioValidationConstants_ShouldHaveCorrectPatternsAndMessages() {
        // Patterns
        assertEquals("^[0-9]+$",
                HttpConstants.UsuarioValidation.DOCUMENTO_PATTERN);
        assertEquals("^\\+?[0-9]{1,13}$",
                HttpConstants.UsuarioValidation.CELULAR_PATTERN);

        // Messages
        assertEquals("El nombre es obligatorio",
                HttpConstants.UsuarioValidation.NOMBRE_REQUERIDO);
        assertEquals("El apellido es obligatorio",
                HttpConstants.UsuarioValidation.APELLIDO_REQUERIDO);
        assertEquals("El número de documento es obligatorio",
                HttpConstants.UsuarioValidation.DOCUMENTO_REQUERIDO);
        assertEquals("El documento de identidad debe ser únicamente numérico",
                HttpConstants.UsuarioValidation.DOCUMENTO_NUMERICO);
        assertEquals("El celular es obligatorio",
                HttpConstants.UsuarioValidation.CELULAR_REQUERIDO);
        assertEquals("El teléfono debe contener un máximo de 13 caracteres y puede contener el símbolo +",
                HttpConstants.UsuarioValidation.CELULAR_FORMATO);
        assertEquals("La fecha de nacimiento es obligatoria",
                HttpConstants.UsuarioValidation.FECHA_NACIMIENTO_REQUERIDA);
        assertEquals("El correo es obligatorio",
                HttpConstants.UsuarioValidation.CORREO_REQUERIDO);
        assertEquals("Debe tener estructura de email válida",
                HttpConstants.UsuarioValidation.CORREO_FORMATO);
        assertEquals("La clave es obligatoria",
                HttpConstants.UsuarioValidation.CLAVE_REQUERIDA);
    }
}
