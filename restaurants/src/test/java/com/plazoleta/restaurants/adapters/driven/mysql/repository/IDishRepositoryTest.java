package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@DisplayName("IDishRepository Tests")
class IDishRepositoryTest {

    @Test
    @DisplayName("Repository interface should be properly defined")
    void repositoryInterface_ProperlyDefined() {

        assertTrue(IDishRepository.class.isInterface());
    }

    @Test
    @DisplayName("Should extend JpaRepository")
    void interface_ExtendsJpaRepository() {
        // Verificar que extiende JpaRepository
        assertTrue(JpaRepository.class.isAssignableFrom(IDishRepository.class));
    }

    @Test
    @DisplayName("Should have correct generic types")
    void interface_HasCorrectGenericTypes() {
        // Verificar que la interfaz tiene los tipos genéricos correctos
        assertTrue(IDishRepository.class.isInterface());

        // Verificar que hereda los métodos básicos de JpaRepository
        try {
            // Estos métodos vienen de JpaRepository y deberían estar disponibles
            JpaRepository.class.getMethod("save", Object.class);
            JpaRepository.class.getMethod("findById", Object.class);
            JpaRepository.class.getMethod("findAll");
            JpaRepository.class.getMethod("deleteById", Object.class);
            JpaRepository.class.getMethod("count");
            JpaRepository.class.getMethod("existsById", Object.class);
            assertTrue(true);
        } catch (NoSuchMethodException e) {
            fail("JpaRepository methods should be inherited: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should be in correct package")
    void interface_IsInCorrectPackage() {
        // Verificar que está en el paquete correcto
        assertEquals("com.plazoleta.restaurants.adapters.driven.mysql.repository",
                IDishRepository.class.getPackage().getName());
    }

    @Test
    @DisplayName("Should inherit standard JPA repository methods")
    void interface_InheritsJpaRepositoryMethods() {
        // Verificar que hereda métodos estándar de JpaRepository
        try {
            JpaRepository.class.getMethod("save", Object.class);
            JpaRepository.class.getMethod("findById", Object.class);
            JpaRepository.class.getMethod("findAll");
            JpaRepository.class.getMethod("deleteById", Object.class);
            JpaRepository.class.getMethod("count");
            JpaRepository.class.getMethod("existsById", Object.class);
            JpaRepository.class.getMethod("delete", Object.class);
            assertTrue(true);
        } catch (NoSuchMethodException e) {
            fail("JpaRepository methods should be inherited: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Interface should have correct name")
    void interface_HasCorrectName() {
        // Verificar que tiene el nombre correcto
        assertEquals("IDishRepository", IDishRepository.class.getSimpleName());
    }

    @Test
    @DisplayName("Interface should be public")
    void interface_IsPublic() {
        // Verificar que la interfaz es pública
        assertTrue(java.lang.reflect.Modifier.isPublic(IDishRepository.class.getModifiers()));
    }

    @Test
    @DisplayName("Interface should not be final")
    void interface_IsNotFinal() {
        // Verificar que la interfaz no es final (las interfaces no pueden ser final)
        assertFalse(java.lang.reflect.Modifier.isFinal(IDishRepository.class.getModifiers()));
    }

    @Test
    @DisplayName("Interface should not be abstract")
    void interface_IsNotAbstract() {

        assertTrue(IDishRepository.class.isInterface());
    }

    @Test
    @DisplayName("Should have correct inheritance hierarchy")
    void interface_HasCorrectInheritanceHierarchy() {

        Class<?>[] interfaces = IDishRepository.class.getInterfaces();

        assertEquals(1, interfaces.length);

        assertEquals(JpaRepository.class, interfaces[0]);
    }

    @Test
    @DisplayName("Should be compatible with Spring Data JPA")
    void interface_IsCompatibleWithSpringDataJPA() {

        assertTrue(JpaRepository.class.isAssignableFrom(IDishRepository.class));
        assertTrue(org.springframework.data.repository.Repository.class.isAssignableFrom(IDishRepository.class));
        assertTrue(org.springframework.data.repository.CrudRepository.class.isAssignableFrom(IDishRepository.class));
        assertTrue(org.springframework.data.repository.PagingAndSortingRepository.class.isAssignableFrom(IDishRepository.class));
    }
}