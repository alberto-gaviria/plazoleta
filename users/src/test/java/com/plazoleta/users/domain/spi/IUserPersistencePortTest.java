package com.plazoleta.users.domain.spi;

import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUserPersistencePort Tests")
class IUserPersistencePortTest {

    @Test
    @DisplayName("Should be an interface")
    void class_IsInterface() {
        // Then
        assertTrue(IUserPersistencePort.class.isInterface());
    }

    @Test
    @DisplayName("Should have saveUsuario method")
    void saveUsuario_MethodExists() {
        // Then
        try {
            IUserPersistencePort.class.getMethod("saveUsuario", User.class);
            assertTrue(true);
        } catch (NoSuchMethodException e) {
            fail("saveUsuario method should exist with User parameter");
        }
    }

    @Test
    @DisplayName("saveUsuario method should return void")
    void saveUsuario_ReturnsVoid() {
        // Then
        try {
            var method = IUserPersistencePort.class.getMethod("saveUsuario", User.class);
            assertEquals(void.class, method.getReturnType());
        } catch (NoSuchMethodException e) {
            fail("saveUsuario method should exist");
        }
    }

    @Test
    @DisplayName("Should have findById method")
    void findById_MethodExists() {
        // Then
        try {
            var method = IUserPersistencePort.class.getMethod("findById", Long.class);
            assertEquals(Optional.class, method.getReturnType());
        } catch (NoSuchMethodException e) {
            fail("findById method should exist with Long parameter");
        }
    }

    @Test
    @DisplayName("Should have findByCorreo method")
    void findByCorreo_MethodExists() {
        // Then
        try {
            var method = IUserPersistencePort.class.getMethod("findByCorreo", String.class);
            assertEquals(Optional.class, method.getReturnType());
        } catch (NoSuchMethodException e) {
            fail("findByCorreo method should exist with String parameter");
        }
    }

    @Test
    @DisplayName("Interface should be public")
    void interface_IsPublic() {
        // Then
        assertTrue(java.lang.reflect.Modifier.isPublic(IUserPersistencePort.class.getModifiers()));
    }

    @Test
    @DisplayName("Interface should not have implementation")
    void interface_HasNoImplementation() {
        // Verify it's an interface (has no concrete implementation)
        assertTrue(IUserPersistencePort.class.isInterface());
        assertEquals(0, IUserPersistencePort.class.getDeclaredFields().length);
    }

    @Test
    @DisplayName("Interface should be in correct package")
    void interface_IsInCorrectPackage() {
        // Then
        assertEquals("com.plazoleta.users.domain.spi", IUserPersistencePort.class.getPackage().getName());
    }

    @Test
    @DisplayName("Interface should have exactly three methods")
    void interface_HasThreeMethods() {
        // Then - La interfaz tiene 3 métodos: saveUsuario, findById, findByCorreo
        assertEquals(3, IUserPersistencePort.class.getDeclaredMethods().length);
    }

    @Test
    @DisplayName("Interface should not extend other interfaces")
    void interface_DoesNotExtendOthers() {
        // Then
        assertEquals(0, IUserPersistencePort.class.getInterfaces().length);
    }

    @Test
    @DisplayName("All methods should have correct signatures")
    void allMethods_HaveCorrectSignatures() {
        try {
            // saveUsuario method
            var saveMethod = IUserPersistencePort.class.getMethod("saveUsuario", User.class);
            assertEquals(void.class, saveMethod.getReturnType());
            assertEquals(1, saveMethod.getParameterCount());

            // findById method
            var findByIdMethod = IUserPersistencePort.class.getMethod("findById", Long.class);
            assertEquals(Optional.class, findByIdMethod.getReturnType());
            assertEquals(1, findByIdMethod.getParameterCount());

            // findByCorreo method
            var findByCorreoMethod = IUserPersistencePort.class.getMethod("findByCorreo", String.class);
            assertEquals(Optional.class, findByCorreoMethod.getReturnType());
            assertEquals(1, findByCorreoMethod.getParameterCount());

        } catch (NoSuchMethodException e) {
            fail("All expected methods should exist with correct signatures");
        }
    }

    @Test
    @DisplayName("Methods should not throw checked exceptions")
    void methods_DoNotThrowCheckedExceptions() {
        try {
            var saveMethod = IUserPersistencePort.class.getMethod("saveUsuario", User.class);
            assertEquals(0, saveMethod.getExceptionTypes().length);

            var findByIdMethod = IUserPersistencePort.class.getMethod("findById", Long.class);
            assertEquals(0, findByIdMethod.getExceptionTypes().length);

            var findByCorreoMethod = IUserPersistencePort.class.getMethod("findByCorreo", String.class);
            assertEquals(0, findByCorreoMethod.getExceptionTypes().length);

        } catch (NoSuchMethodException e) {
            fail("Methods should exist");
        }
    }
}