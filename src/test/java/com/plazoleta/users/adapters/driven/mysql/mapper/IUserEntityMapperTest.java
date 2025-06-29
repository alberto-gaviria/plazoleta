package com.plazoleta.users.adapters.driven.mysql.mapper;

import com.plazoleta.users.adapters.driven.mysql.entity.UserEntity;
import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUsuarioEntityMapper Tests")
class IUserEntityMapperTest {

    private IUserEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IUserEntityMapper.class);
    }

    @Test
    @DisplayName("Should map Usuario to UsuarioEntity correctly")
    void toEntity_ValidUsuario_MapsCorrectly() {
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
        user.setIdRol(2L);

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
        assertEquals(user.getIdRol(), result.getIdRol());
    }

    @Test
    @DisplayName("Should map UsuarioEntity to Usuario correctly")
    void toModel_ValidUsuarioEntity_MapsCorrectly() {
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
        entity.setIdRol(2L);

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
        assertEquals(entity.getIdRol(), result.getIdRol());
    }

    @Test
    @DisplayName("Should handle null Usuario")
    void toEntity_NullUsuario_ReturnsNull() {
        // When
        UserEntity result = mapper.toEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null UsuarioEntity")
    void toModel_NullUsuarioEntity_ReturnsNull() {
        // When
        User result = mapper.toModel(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle Usuario with null fields")
    void toEntity_UsuarioWithNullFields_MapsNulls() {
        // Given
        User user = new User();
        user.setId(null);
        user.setNombre(null);
        user.setApellido(null);

        // When
        UserEntity result = mapper.toEntity(user);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
    }

    @Test
    @DisplayName("Should handle UsuarioEntity with null fields")
    void toModel_EntityWithNullFields_MapsNulls() {
        // Given
        UserEntity entity = new UserEntity();
        entity.setId(null);
        entity.setNombre(null);
        entity.setApellido(null);

        // When
        User result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
    }
    @Test
    @DisplayName("Should map list of entities to models")
    void toModelList_ValidList_MapsCorrectly() {
        // Given
        UserEntity entity1 = new UserEntity(1L, "Juan", "Pérez", "12345678",
                "+573001234567", LocalDate.of(1990, 1, 1),
                "juan@test.com", "password123", 2L);
        UserEntity entity2 = new UserEntity(2L, "María", "García", "87654321",
                "+573009876543", LocalDate.of(1985, 5, 15),
                "maria@test.com", "password456", 2L);
        List<UserEntity> entities = Arrays.asList(entity1, entity2);

        // When
        List<User> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        assertEquals("María", result.get(1).getNombre());
    }

    @Test
    @DisplayName("Should handle null list")
    void toModelList_NullList_ReturnsNull() {
        // When
        List<User> result = mapper.toModelList(null);

        // Then
        assertNull(result);
    }
}