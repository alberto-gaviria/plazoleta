package com.plazoleta.users.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

    class UserTest {

        @Test
        void testUserCreation() {
            // Given
            Long id = 1L;
            String nombre = "Juan";
            String apellido = "Pérez";
            String numeroDocumento = "12345678";
            String celular = "+573001234567";
            LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
            String correo = "juan@email.com";
            String clave = "password123";
            RoleType roleType = RoleType.PROPIETARIO; // Usando RoleType en lugar de Long

            // When
            User user = new User(id, nombre, apellido, numeroDocumento,
                    celular, fechaNacimiento, correo, clave, roleType);

            // Then
            assertEquals(id, user.getId());
            assertEquals(nombre, user.getNombre());
            assertEquals(apellido, user.getApellido());
            assertEquals(numeroDocumento, user.getNumeroDocumento());
            assertEquals(celular, user.getCelular());
            assertEquals(fechaNacimiento, user.getFechaNacimiento());
            assertEquals(correo, user.getCorreo());
            assertEquals(clave, user.getClave());
            assertEquals(roleType, user.getRoleType()); // Verificando roleType en lugar de idRol
        }

        @Test
        void testUserNoArgsConstructor() {
            // When
            User user = new User();

            // Then
            assertNull(user.getId());
            assertNull(user.getNombre());
            assertNull(user.getApellido());
            assertNull(user.getNumeroDocumento());
            assertNull(user.getCelular());
            assertNull(user.getFechaNacimiento());
            assertNull(user.getCorreo());
            assertNull(user.getClave());
            assertNull(user.getRoleType()); // Verificando roleType en lugar de idRol
        }

        @Test
        void testUserSettersAndGetters() {
            // Given
            User user = new User();

            // When
            user.setId(1L);
            user.setNombre("Juan");
            user.setApellido("Pérez");
            user.setNumeroDocumento("12345678");
            user.setCelular("+573001234567");
            user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
            user.setCorreo("juan@email.com");
            user.setClave("password123");
            user.setRoleType(RoleType.PROPIETARIO); // Usando setRoleType en lugar de setIdRol

            // Then
            assertEquals(1L, user.getId());
            assertEquals("Juan", user.getNombre());
            assertEquals("Pérez", user.getApellido());
            assertEquals("12345678", user.getNumeroDocumento());
            assertEquals("+573001234567", user.getCelular());
            assertEquals(LocalDate.of(1990, 1, 1), user.getFechaNacimiento());
            assertEquals("juan@email.com", user.getCorreo());
            assertEquals("password123", user.getClave());
            assertEquals(RoleType.PROPIETARIO, user.getRoleType()); // Verificando roleType
        }

        @Test
        void getRoleType_WhenValidRoleType_ShouldReturnCorrectRoleType() {
            // Given
            User user = new User();
            user.setRoleType(RoleType.PROPIETARIO);

            // When
            RoleType roleType = user.getRoleType();

            // Then
            assertEquals(RoleType.PROPIETARIO, roleType);
        }

        @Test
        void getRoleType_WhenRoleTypeIsNull_ShouldReturnNull() {
            // Given
            User user = new User();
            user.setRoleType(null);

            // When
            RoleType roleType = user.getRoleType();

            // Then
            assertNull(roleType);
        }

        @Test
        void setRoleType_WhenValidRoleType_ShouldSetCorrectRoleType() {
            // Given
            User user = new User();

            // When
            user.setRoleType(RoleType.PROPIETARIO);

            // Then
            assertEquals(RoleType.PROPIETARIO, user.getRoleType());
        }

        @Test
        void setRoleType_WhenNullRoleType_ShouldSetRoleTypeToNull() {
            // Given
            User user = new User();
            user.setRoleType(RoleType.PROPIETARIO); // Asignar primero un valor

            // When
            user.setRoleType(null);

            // Then
            assertNull(user.getRoleType());
        }

        @Test
        void setRoleType_WithDifferentRoles_ShouldSetCorrectRoles() {
            // Given
            User user = new User();

            // When & Then
            user.setRoleType(RoleType.ADMINISTRADOR);
            assertEquals(RoleType.ADMINISTRADOR, user.getRoleType());

            user.setRoleType(RoleType.EMPLEADO);
            assertEquals(RoleType.EMPLEADO, user.getRoleType());

            user.setRoleType(RoleType.CLIENTE);
            assertEquals(RoleType.CLIENTE, user.getRoleType());

            user.setRoleType(RoleType.PROPIETARIO);
            assertEquals(RoleType.PROPIETARIO, user.getRoleType());
        }

        @Test
        void testUserWithCompleteData() {
            // Given & When
            User user = new User(
                    100L,
                    "Ana",
                    "Martínez",
                    "98765432",
                    "+573009876543",
                    LocalDate.of(1985, 12, 15),
                    "ana.martinez@email.com",
                    "securePassword",
                    RoleType.ADMINISTRADOR
            );

            // Then
            assertEquals(100L, user.getId());
            assertEquals("Ana", user.getNombre());
            assertEquals("Martínez", user.getApellido());
            assertEquals("98765432", user.getNumeroDocumento());
            assertEquals("+573009876543", user.getCelular());
            assertEquals(LocalDate.of(1985, 12, 15), user.getFechaNacimiento());
            assertEquals("ana.martinez@email.com", user.getCorreo());
            assertEquals("securePassword", user.getClave());
            assertEquals(RoleType.ADMINISTRADOR, user.getRoleType());
        }

        @Test
        void testUserFieldModification() {
            // Given
            User user = new User();
            user.setNombre("Juan");
            user.setRoleType(RoleType.CLIENTE);

            // When
            user.setNombre("Carlos");
            user.setRoleType(RoleType.PROPIETARIO);

            // Then
            assertEquals("Carlos", user.getNombre());
            assertEquals(RoleType.PROPIETARIO, user.getRoleType());
        }

        @Test
        void testUserWithNullValues() {
            // Given
            User user = new User();

            // When
            user.setId(null);
            user.setNombre(null);
            user.setApellido(null);
            user.setNumeroDocumento(null);
            user.setCelular(null);
            user.setFechaNacimiento(null);
            user.setCorreo(null);
            user.setClave(null);
            user.setRoleType(null);

            // Then
            assertNull(user.getId());
            assertNull(user.getNombre());
            assertNull(user.getApellido());
            assertNull(user.getNumeroDocumento());
            assertNull(user.getCelular());
            assertNull(user.getFechaNacimiento());
            assertNull(user.getCorreo());
            assertNull(user.getClave());
            assertNull(user.getRoleType());
        }

        @Test
        void testUserWithEmptyStrings() {
            // Given
            User user = new User();

            // When
            user.setNombre("");
            user.setApellido("");
            user.setNumeroDocumento("");
            user.setCelular("");
            user.setCorreo("");
            user.setClave("");

            // Then
            assertEquals("", user.getNombre());
            assertEquals("", user.getApellido());
            assertEquals("", user.getNumeroDocumento());
            assertEquals("", user.getCelular());
            assertEquals("", user.getCorreo());
            assertEquals("", user.getClave());
        }

        @Test
        void testAllRoleTypes() {
            // Test cada rol se puede asignar y obtener correctamente
            User user = new User();

            // Test ADMINISTRADOR
            user.setRoleType(RoleType.ADMINISTRADOR);
            assertEquals(RoleType.ADMINISTRADOR, user.getRoleType());

            // Test PROPIETARIO
            user.setRoleType(RoleType.PROPIETARIO);
            assertEquals(RoleType.PROPIETARIO, user.getRoleType());

            // Test EMPLEADO
            user.setRoleType(RoleType.EMPLEADO);
            assertEquals(RoleType.EMPLEADO, user.getRoleType());

            // Test CLIENTE
            user.setRoleType(RoleType.CLIENTE);
            assertEquals(RoleType.CLIENTE, user.getRoleType());
        }
    }