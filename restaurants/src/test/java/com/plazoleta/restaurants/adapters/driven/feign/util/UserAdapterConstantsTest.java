package com.plazoleta.restaurants.adapters.driven.feign.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class UserAdapterConstantsTest {

    @Test
    void shouldNotInstantiateUserAdapterConstants() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<UserAdapterConstants> constructor = UserAdapterConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void shouldNotInstantiateErrorMessages() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<UserAdapterConstants.ErrorMessages> constructor =
                    UserAdapterConstants.ErrorMessages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void shouldNotInstantiateRoles() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<UserAdapterConstants.Roles> constructor =
                    UserAdapterConstants.Roles.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void shouldHaveCorrectErrorMessageConstants() {
        // When & Then
        assertEquals("Usuario no encontrado con ID: ", UserAdapterConstants.ErrorMessages.USUARIO_NO_ENCONTRADO);
        assertEquals("Error obteniendo usuario con ID: ", UserAdapterConstants.ErrorMessages.ERROR_OBTENIENDO_USUARIO);
        assertEquals("Error validando rol del usuario con ID: ", UserAdapterConstants.ErrorMessages.ERROR_VALIDANDO_ROL);
        assertEquals("Error de comunicación con el servicio de usuarios: ", UserAdapterConstants.ErrorMessages.ERROR_COMUNICACION_USERS_SERVICE);
        assertEquals(" - ", UserAdapterConstants.ErrorMessages.MENSAJE_SEPARATOR);
    }

    @Test
    void shouldHaveCorrectRoleConstants() {
        // When & Then
        assertEquals("ADMINISTRADOR", UserAdapterConstants.Roles.ADMINISTRADOR);
        assertEquals("PROPIETARIO", UserAdapterConstants.Roles.PROPIETARIO);
        assertEquals("EMPLEADO", UserAdapterConstants.Roles.EMPLEADO);
        assertEquals("CLIENTE", UserAdapterConstants.Roles.CLIENTE);
    }

    @Test
    void shouldHaveAllRoleConstantsNotNull() {
        // When & Then
        assertNotNull(UserAdapterConstants.Roles.ADMINISTRADOR);
        assertNotNull(UserAdapterConstants.Roles.PROPIETARIO);
        assertNotNull(UserAdapterConstants.Roles.EMPLEADO);
        assertNotNull(UserAdapterConstants.Roles.CLIENTE);
    }

    @Test
    void shouldHaveAllErrorMessageConstantsNotNull() {
        // When & Then
        assertNotNull(UserAdapterConstants.ErrorMessages.USUARIO_NO_ENCONTRADO);
        assertNotNull(UserAdapterConstants.ErrorMessages.ERROR_OBTENIENDO_USUARIO);
        assertNotNull(UserAdapterConstants.ErrorMessages.ERROR_VALIDANDO_ROL);
        assertNotNull(UserAdapterConstants.ErrorMessages.ERROR_COMUNICACION_USERS_SERVICE);
        assertNotNull(UserAdapterConstants.ErrorMessages.MENSAJE_SEPARATOR);
    }
}