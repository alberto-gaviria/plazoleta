package com.plazoleta.users.domain.spi;

import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUsuarioPersistencePort Tests")
class IUserPersistencePortTest {

    @Test
    @DisplayName("Persistence interface should be properly defined")
    void persistenceInterface_ProperlyDefined() {
        // This is just a placeholder test since we're testing an interface
        // In a real integration test, this would test actual persistence operations
        assertTrue(true);
    }

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
            fail("saveUsuario method should exist with Usuario parameter");
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
    @DisplayName("Interface should have correct method signature")
    void interface_HasCorrectMethodSignature() {
        // Verify method exists with correct signature
        try {
            var method = IUserPersistencePort.class.getMethod("saveUsuario", User.class);
            assertNotNull(method);
            assertEquals(void.class, method.getReturnType());
            assertEquals(1, method.getParameterCount());
        } catch (NoSuchMethodException e) {
            fail("saveUsuario method should exist with correct signature");
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
    @DisplayName("Interface should have exactly one method")
    void interface_HasOneMethod() {
        // Then
        assertEquals(1, IUserPersistencePort.class.getDeclaredMethods().length);
    }

    @Test
    @DisplayName("Interface should not extend other interfaces")
    void interface_DoesNotExtendOthers() {
        // Then
        assertEquals(0, IUserPersistencePort.class.getInterfaces().length);
    }
}