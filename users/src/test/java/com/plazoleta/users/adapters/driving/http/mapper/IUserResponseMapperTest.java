package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.model.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUserResponseMapper Tests")
class IUserResponseMapperTest {

    private IUserResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IUserResponseMapper.class);
    }

    @Test
    @DisplayName("Should map User to UserResponse correctly")
    void userToDto_ValidUser_MapsCorrectly() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan@test.com");
        user.setClave("password123");
        user.setRoleType(RoleType.PROPIETARIO); // Usando RoleType en lugar de setIdRol

        // When
        UserResponse result = mapper.userToDto(user); // Método correcto

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getNombre(), result.getNombre());
        assertEquals(user.getApellido(), result.getApellido());
        assertEquals(user.getNumeroDocumento(), result.getNumeroDocumento());
        assertEquals(user.getCelular(), result.getCelular());
        assertEquals(user.getFechaNacimiento(), result.getFechaNacimiento());
        assertEquals(user.getCorreo(), result.getCorreo());
        assertEquals(user.getRoleType().getId(), result.getIdRol()); // Conversión de RoleType a Long
        assertEquals(user.getRoleType().name(), result.getRolNombre()); // Conversión de RoleType a String
    }

    @Test
    @DisplayName("Should handle null user")
    void userToDto_NullUser_ReturnsNull() {
        // When
        UserResponse result = mapper.userToDto(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle User with null fields")
    void userToDto_UserWithNullFields_MapsNulls() {
        // Given
        User user = new User();
        user.setId(null);
        user.setNombre(null);
        user.setApellido(null);
        user.setNumeroDocumento(null);
        user.setCelular(null);
        user.setFechaNacimiento(null);
        user.setCorreo(null);
        user.setClave(null);
        user.setRoleType(null); // Usando setRoleType en lugar de setIdRol

        // When
        UserResponse result = mapper.userToDto(user);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
        assertNull(result.getNumeroDocumento());
        assertNull(result.getCelular());
        assertNull(result.getFechaNacimiento());
        assertNull(result.getCorreo());
        assertNull(result.getIdRol()); // Null porque roleType es null
        assertNull(result.getRolNombre()); // Null porque roleType es null
    }

    @Test
    @DisplayName("Should exclude clave field from response")
    void userToDto_ExcludesClave() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setClave("secretPassword");
        user.setCorreo("juan@test.com");
        user.setRoleType(RoleType.CLIENTE);

        // When
        UserResponse result = mapper.userToDto(user);

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getNombre(), result.getNombre());
        assertEquals(user.getApellido(), result.getApellido());
        assertEquals(user.getCorreo(), result.getCorreo());
        assertEquals(4L, result.getIdRol()); // CLIENTE tiene ID 4
        assertEquals("CLIENTE", result.getRolNombre());
        // Clave no se incluye en UserResponse automáticamente
    }

    @Test
    @DisplayName("Should handle edge case values")
    void userToDto_EdgeCaseValues_MapsCorrectly() {
        // Given
        User user = new User();
        user.setId(0L);
        user.setNombre("");
        user.setApellido("");
        user.setNumeroDocumento("");
        user.setCelular("");
        user.setFechaNacimiento(LocalDate.of(2000, 12, 31));
        user.setCorreo("");
        user.setRoleType(RoleType.ADMINISTRADOR); // ID 1

        // When
        UserResponse result = mapper.userToDto(user);

        // Then
        assertNotNull(result);
        assertEquals(0L, result.getId());
        assertEquals("", result.getNombre());
        assertEquals("", result.getApellido());
        assertEquals("", result.getNumeroDocumento());
        assertEquals("", result.getCelular());
        assertEquals(LocalDate.of(2000, 12, 31), result.getFechaNacimiento());
        assertEquals("", result.getCorreo());
        assertEquals(1L, result.getIdRol()); // ADMINISTRADOR tiene ID 1
        assertEquals("ADMINISTRADOR", result.getRolNombre());
    }

    @Test
    @DisplayName("Should map all role types correctly")
    void userToDto_AllRoleTypes_MapsCorrectly() {
        // Test ADMINISTRADOR
        User adminUser = new User();
        adminUser.setId(1L);
        adminUser.setNombre("Admin");
        adminUser.setRoleType(RoleType.ADMINISTRADOR);

        UserResponse adminResponse = mapper.userToDto(adminUser);
        assertEquals(1L, adminResponse.getIdRol());
        assertEquals("ADMINISTRADOR", adminResponse.getRolNombre());

        // Test PROPIETARIO
        User propietarioUser = new User();
        propietarioUser.setId(2L);
        propietarioUser.setNombre("Propietario");
        propietarioUser.setRoleType(RoleType.PROPIETARIO);

        UserResponse propietarioResponse = mapper.userToDto(propietarioUser);
        assertEquals(2L, propietarioResponse.getIdRol());
        assertEquals("PROPIETARIO", propietarioResponse.getRolNombre());

        // Test EMPLEADO
        User empleadoUser = new User();
        empleadoUser.setId(3L);
        empleadoUser.setNombre("Empleado");
        empleadoUser.setRoleType(RoleType.EMPLEADO);

        UserResponse empleadoResponse = mapper.userToDto(empleadoUser);
        assertEquals(3L, empleadoResponse.getIdRol());
        assertEquals("EMPLEADO", empleadoResponse.getRolNombre());

        // Test CLIENTE
        User clienteUser = new User();
        clienteUser.setId(4L);
        clienteUser.setNombre("Cliente");
        clienteUser.setRoleType(RoleType.CLIENTE);

        UserResponse clienteResponse = mapper.userToDto(clienteUser);
        assertEquals(4L, clienteResponse.getIdRol());
        assertEquals("CLIENTE", clienteResponse.getRolNombre());
    }

    @Test
    @DisplayName("Should test role conversion methods directly")
    void testRoleConversionMethods() {
        // Test roleTypeToId
        assertEquals(1L, mapper.roleTypeToId(RoleType.ADMINISTRADOR));
        assertEquals(2L, mapper.roleTypeToId(RoleType.PROPIETARIO));
        assertEquals(3L, mapper.roleTypeToId(RoleType.EMPLEADO));
        assertEquals(4L, mapper.roleTypeToId(RoleType.CLIENTE));
        assertNull(mapper.roleTypeToId(null));

        // Test roleTypeToName
        assertEquals("ADMINISTRADOR", mapper.roleTypeToName(RoleType.ADMINISTRADOR));
        assertEquals("PROPIETARIO", mapper.roleTypeToName(RoleType.PROPIETARIO));
        assertEquals("EMPLEADO", mapper.roleTypeToName(RoleType.EMPLEADO));
        assertEquals("CLIENTE", mapper.roleTypeToName(RoleType.CLIENTE));
        assertNull(mapper.roleTypeToName(null));
    }

    @Test
    @DisplayName("Should handle complete user mapping")
    void userToDto_CompleteUser_MapsAllFields() {
        // Given
        User user = new User();
        user.setId(100L);
        user.setNombre("Ana");
        user.setApellido("Martínez");
        user.setNumeroDocumento("98765432");
        user.setCelular("+573009876543");
        user.setFechaNacimiento(LocalDate.of(1985, 12, 15));
        user.setCorreo("ana.martinez@email.com");
        user.setClave("password123");
        user.setRoleType(RoleType.PROPIETARIO);

        // When
        UserResponse result = mapper.userToDto(user);

        // Then
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Ana", result.getNombre());
        assertEquals("Martínez", result.getApellido());
        assertEquals("98765432", result.getNumeroDocumento());
        assertEquals("+573009876543", result.getCelular());
        assertEquals(LocalDate.of(1985, 12, 15), result.getFechaNacimiento());
        assertEquals("ana.martinez@email.com", result.getCorreo());
        assertEquals(2L, result.getIdRol());
        assertEquals("PROPIETARIO", result.getRolNombre());
    }

    @Test
    @DisplayName("Should handle user with special characters")
    void userToDto_SpecialCharacters_MapsCorrectly() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setNombre("José María");
        user.setApellido("Pérez-González");
        user.setNumeroDocumento("12345678A");
        user.setCelular("+57 300 123 4567");
        user.setCorreo("jose.maria@email.com");
        user.setRoleType(RoleType.EMPLEADO);

        // When
        UserResponse result = mapper.userToDto(user);

        // Then
        assertNotNull(result);
        assertEquals("José María", result.getNombre());
        assertEquals("Pérez-González", result.getApellido());
        assertEquals("12345678A", result.getNumeroDocumento());
        assertEquals("+57 300 123 4567", result.getCelular());
        assertEquals("jose.maria@email.com", result.getCorreo());
        assertEquals(3L, result.getIdRol());
        assertEquals("EMPLEADO", result.getRolNombre());
    }
}