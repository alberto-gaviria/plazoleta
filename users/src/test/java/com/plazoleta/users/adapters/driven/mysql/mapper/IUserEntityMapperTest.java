package com.plazoleta.users.adapters.driven.mysql.mapper;

import com.plazoleta.users.adapters.driven.mysql.entity.UserEntity;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.model.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUserEntityMapper Tests")
class IUserEntityMapperTest {

    private IUserEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IUserEntityMapper.class);
    }

    @Test
    @DisplayName("Should map User to UserEntity correctly")
    void toEntity_ValidUser_MapsCorrectly() {
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
        user.setRoleType(RoleType.PROPIETARIO); // Usando RoleType en lugar de idRol

        // When
        UserEntity result = mapper.toEntity(user);

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getNombre(), result.getNombre());
        assertEquals(user.getApellido(), result.getApellido());
        assertEquals(user.getNumeroDocumento(), result.getNumeroDocumento());
        assertEquals(user.getCelular(), result.getCelular());
        assertEquals(user.getFechaNacimiento(), result.getFechaNacimiento());
        assertEquals(user.getCorreo(), result.getCorreo());
        assertEquals(user.getClave(), result.getClave());
        assertEquals(user.getRoleType().getId(), result.getIdRol()); // Verificamos la conversión
    }

    @Test
    @DisplayName("Should map UserEntity to User correctly")
    void toModel_ValidUserEntity_MapsCorrectly() {
        // Given
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setNombre("Juan");
        entity.setApellido("Pérez");
        entity.setNumeroDocumento("12345678");
        entity.setCelular("+573001234567");
        entity.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        entity.setCorreo("juan@test.com");
        entity.setClave("password123");
        entity.setIdRol(2L); // ID del rol PROPIETARIO

        // When
        User result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getNombre(), result.getNombre());
        assertEquals(entity.getApellido(), result.getApellido());
        assertEquals(entity.getNumeroDocumento(), result.getNumeroDocumento());
        assertEquals(entity.getCelular(), result.getCelular());
        assertEquals(entity.getFechaNacimiento(), result.getFechaNacimiento());
        assertEquals(entity.getCorreo(), result.getCorreo());
        assertEquals(entity.getClave(), result.getClave());
        assertEquals(RoleType.PROPIETARIO, result.getRoleType()); // Verificamos la conversión
    }

    @Test
    @DisplayName("Should handle null User")
    void toEntity_NullUser_ReturnsNull() {
        // When
        UserEntity result = mapper.toEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null UserEntity")
    void toModel_NullUserEntity_ReturnsNull() {
        // When
        User result = mapper.toModel(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle User with null fields")
    void toEntity_UserWithNullFields_MapsNulls() {
        // Given
        User user = new User();
        user.setId(null);
        user.setNombre(null);
        user.setApellido(null);
        user.setRoleType(null);

        // When
        UserEntity result = mapper.toEntity(user);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
        assertNull(result.getIdRol());
    }

    @Test
    @DisplayName("Should handle UserEntity with null fields")
    void toModel_EntityWithNullFields_MapsNulls() {
        // Given
        UserEntity entity = new UserEntity();
        entity.setId(null);
        entity.setNombre(null);
        entity.setApellido(null);
        entity.setIdRol(null);

        // When
        User result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
        assertNull(result.getRoleType());
    }

    @Test
    @DisplayName("Should map list of entities to models")
    void toModelList_ValidList_MapsCorrectly() {
        // Given
        UserEntity entity1 = new UserEntity();
        entity1.setId(1L);
        entity1.setNombre("Juan");
        entity1.setApellido("Pérez");
        entity1.setNumeroDocumento("12345678");
        entity1.setCelular("+573001234567");
        entity1.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        entity1.setCorreo("juan@test.com");
        entity1.setClave("password123");
        entity1.setIdRol(2L);

        UserEntity entity2 = new UserEntity();
        entity2.setId(2L);
        entity2.setNombre("María");
        entity2.setApellido("García");
        entity2.setNumeroDocumento("87654321");
        entity2.setCelular("+573009876543");
        entity2.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        entity2.setCorreo("maria@test.com");
        entity2.setClave("password456");
        entity2.setIdRol(3L);

        List<UserEntity> entities = Arrays.asList(entity1, entity2);

        // When
        List<User> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        assertEquals("María", result.get(1).getNombre());
        assertEquals(RoleType.PROPIETARIO, result.get(0).getRoleType());
        assertEquals(RoleType.EMPLEADO, result.get(1).getRoleType());
    }

    @Test
    @DisplayName("Should handle null list")
    void toModelList_NullList_ReturnsNull() {
        // When
        List<User> result = mapper.toModelList(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle empty list")
    void toModelList_EmptyList_ReturnsEmptyList() {
        // Given
        List<UserEntity> emptyList = Arrays.asList();

        // When
        List<User> result = mapper.toModelList(emptyList);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should test role conversion methods directly")
    void testRoleConversionMethods() {
        // Test idToRoleType
        assertEquals(RoleType.ADMINISTRADOR, mapper.idToRoleType(1L));
        assertEquals(RoleType.PROPIETARIO, mapper.idToRoleType(2L));
        assertEquals(RoleType.EMPLEADO, mapper.idToRoleType(3L));
        assertEquals(RoleType.CLIENTE, mapper.idToRoleType(4L));
        assertNull(mapper.idToRoleType(null));

        // Test roleTypeToId
        assertEquals(1L, mapper.roleTypeToId(RoleType.ADMINISTRADOR));
        assertEquals(2L, mapper.roleTypeToId(RoleType.PROPIETARIO));
        assertEquals(3L, mapper.roleTypeToId(RoleType.EMPLEADO));
        assertEquals(4L, mapper.roleTypeToId(RoleType.CLIENTE));
        assertNull(mapper.roleTypeToId(null));
    }

    @Test
    @DisplayName("Should map all role types correctly")
    void testAllRoleTypeMapping() {
        // Test ADMINISTRADOR
        User adminUser = new User();
        adminUser.setRoleType(RoleType.ADMINISTRADOR);
        UserEntity adminEntity = mapper.toEntity(adminUser);
        assertEquals(1L, adminEntity.getIdRol());

        // Test PROPIETARIO
        User propietarioUser = new User();
        propietarioUser.setRoleType(RoleType.PROPIETARIO);
        UserEntity propietarioEntity = mapper.toEntity(propietarioUser);
        assertEquals(2L, propietarioEntity.getIdRol());

        // Test EMPLEADO
        User empleadoUser = new User();
        empleadoUser.setRoleType(RoleType.EMPLEADO);
        UserEntity empleadoEntity = mapper.toEntity(empleadoUser);
        assertEquals(3L, empleadoEntity.getIdRol());

        // Test CLIENTE
        User clienteUser = new User();
        clienteUser.setRoleType(RoleType.CLIENTE);
        UserEntity clienteEntity = mapper.toEntity(clienteUser);
        assertEquals(4L, clienteEntity.getIdRol());
    }
}