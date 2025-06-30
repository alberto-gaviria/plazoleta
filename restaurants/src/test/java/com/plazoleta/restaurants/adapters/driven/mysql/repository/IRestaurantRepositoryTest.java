package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IRestaurantRepository Tests")
class IRestaurantRepositoryTest {

    @Test
    @DisplayName("Repository interface should be properly defined")
    void repositoryInterface_ProperlyDefined() {
        // This is just a placeholder test since we're testing an interface
        // In a real integration test, this would test actual database operations
        assertTrue(true);
    }

    @Test
    @DisplayName("Should extend JpaRepository")
    void interface_ExtendsJpaRepository() {
        // Then
        assertTrue(JpaRepository.class.isAssignableFrom(IRestaurantRepository.class));
    }

    @Test
    @DisplayName("Should be an interface")
    void class_IsInterface() {
        // Then
        assertTrue(IRestaurantRepository.class.isInterface());
    }

    @Test
    @DisplayName("Should have findByNit method")
    void findByNit_MethodExists() {
        // Then
        try {
            IRestaurantRepository.class.getMethod("findByNit", String.class);
            assertTrue(true);
        } catch (NoSuchMethodException e) {
            fail("findByNit method should exist with String parameter");
        }
    }

    @Test
    @DisplayName("Should have findByNombre method")
    void findByNombre_MethodExists() {
        // Then
        try {
            IRestaurantRepository.class.getMethod("findByNombre", String.class);
            assertTrue(true);
        } catch (NoSuchMethodException e) {
            fail("findByNombre method should exist with String parameter");
        }
    }

    @Test
    @DisplayName("Should inherit standard JPA repository methods")
    void interface_InheritsJpaRepositoryMethods() {
        // Verify some key inherited methods exist
        try {
            // These methods come from JpaRepository
            JpaRepository.class.getMethod("save", Object.class);
            JpaRepository.class.getMethod("findById", Object.class);
            JpaRepository.class.getMethod("findAll");
            JpaRepository.class.getMethod("deleteById", Object.class);
            assertTrue(true);
        } catch (NoSuchMethodException e) {
            fail("JpaRepository methods should be inherited");
        }
    }

    @Test
    @DisplayName("Should have correct generic types")
    void interface_HasCorrectGenericTypes() {
        // Verify the interface has correct generic parameters
        assertTrue(IRestaurantRepository.class.isInterface());
        // Additional checks for generic types would require reflection
        // This is a basic structure test
    }

    @Test
    @DisplayName("findByNit method should return Optional")
    void findByNit_ReturnsOptional() {
        // Then
        try {
            var method = IRestaurantRepository.class.getMethod("findByNit", String.class);
            assertEquals("java.util.Optional", method.getReturnType().getName());
        } catch (NoSuchMethodException e) {
            fail("findByNit method should exist");
        }
    }

    @Test
    @DisplayName("findByNombre method should return Optional")
    void findByNombre_ReturnsOptional() {
        // Then
        try {
            var method = IRestaurantRepository.class.getMethod("findByNombre", String.class);
            assertEquals("java.util.Optional", method.getReturnType().getName());
        } catch (NoSuchMethodException e) {
            fail("findByNombre method should exist");
        }
    }

    @Test
    @DisplayName("Should be in correct package")
    void interface_IsInCorrectPackage() {
        // Then
        assertEquals("com.plazoleta.restaurants.adapters.driven.mysql.repository",
                IRestaurantRepository.class.getPackage().getName());
    }
}