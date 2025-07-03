package com.plazoleta.users.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DomainConstantsTest {

    @Test
    void shouldThrowExceptionWhenInstantiatingDomainConstants() throws Exception {
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Clase de constantes");
    }

    @Test
    void shouldThrowExceptionWhenInstantiatingUsuarioConstants() throws Exception {
        Constructor<DomainConstants.Usuario> constructor = DomainConstants.Usuario.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Clase de constantes");
    }

    @Test
    void shouldThrowExceptionWhenInstantiatingRoleConstants() throws Exception {
        Constructor<DomainConstants.Role> constructor = DomainConstants.Role.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Clase de constantes");
    }

    @Test
    void shouldThrowExceptionWhenInstantiatingAuthenticationConstants() throws Exception {
        Constructor<DomainConstants.Authentication> constructor = DomainConstants.Authentication.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Clase de constantes");
    }

    // ALTERNATIVA: Si prefieres capturar y verificar la causa manualmente
    @Test
    void shouldThrowExceptionWhenInstantiatingDomainConstants_AlternativeApproach() throws Exception {
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            assertThatThrownBy(() -> { throw e.getCause(); })
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Clase de constantes");
        }
    }

    // OTRA ALTERNATIVA: Usando expectThrows de JUnit si prefieres
    @Test
    void shouldThrowExceptionWhenInstantiatingUsuarioConstants_JUnitApproach() throws Exception {
        Constructor<DomainConstants.Usuario> constructor = DomainConstants.Usuario.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = org.junit.jupiter.api.Assertions.assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        org.junit.jupiter.api.Assertions.assertInstanceOf(IllegalStateException.class, exception.getCause());
        org.junit.jupiter.api.Assertions.assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    // Tests adicionales para verificar que las constantes son accesibles
    @Test
    void usuarioConstants_ShouldBeAccessible() {
        // Verificar que las constantes son accesibles sin instanciar
        org.junit.jupiter.api.Assertions.assertEquals(18, DomainConstants.Usuario.EDAD_MINIMA);
        org.junit.jupiter.api.Assertions.assertEquals("El usuario no puede ser nulo", DomainConstants.Usuario.ERROR_USUARIO_NULO);
        org.junit.jupiter.api.Assertions.assertEquals("El nombre es obligatorio", DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO);
    }

    @Test
    void roleConstants_ShouldBeAccessible() {
        org.junit.jupiter.api.Assertions.assertEquals(1L, DomainConstants.Role.ADMINISTRADOR_ID);
        org.junit.jupiter.api.Assertions.assertEquals(2L, DomainConstants.Role.PROPIETARIO_ID);
        org.junit.jupiter.api.Assertions.assertEquals("ADMINISTRADOR", DomainConstants.Role.ADMINISTRADOR_AUTHORITY);
        org.junit.jupiter.api.Assertions.assertEquals("PROPIETARIO", DomainConstants.Role.PROPIETARIO_AUTHORITY);
    }

    @Test
    void authenticationConstants_ShouldBeAccessible() {
        org.junit.jupiter.api.Assertions.assertEquals("Credenciales inválidas", DomainConstants.Authentication.ERROR_INVALID_CREDENTIALS);
        org.junit.jupiter.api.Assertions.assertEquals("El email es obligatorio", DomainConstants.Authentication.ERROR_EMAIL_REQUIRED);
        org.junit.jupiter.api.Assertions.assertEquals("Token inválido", DomainConstants.Authentication.ERROR_TOKEN_INVALID);
    }

    @Test
    void allConstantClasses_ShouldBeFinal() {
        // Verificar que las clases son final (no se pueden extender)
        org.junit.jupiter.api.Assertions.assertTrue(
                java.lang.reflect.Modifier.isFinal(DomainConstants.class.getModifiers()),
                "DomainConstants should be final"
        );
        org.junit.jupiter.api.Assertions.assertTrue(
                java.lang.reflect.Modifier.isFinal(DomainConstants.Usuario.class.getModifiers()),
                "DomainConstants.Usuario should be final"
        );
        org.junit.jupiter.api.Assertions.assertTrue(
                java.lang.reflect.Modifier.isFinal(DomainConstants.Role.class.getModifiers()),
                "DomainConstants.Role should be final"
        );
        org.junit.jupiter.api.Assertions.assertTrue(
                java.lang.reflect.Modifier.isFinal(DomainConstants.Authentication.class.getModifiers()),
                "DomainConstants.Authentication should be final"
        );
    }
}