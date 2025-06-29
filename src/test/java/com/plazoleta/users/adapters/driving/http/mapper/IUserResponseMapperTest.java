package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUsuarioResponseMapper Tests")
class IUserResponseMapperTest {

    private IUserResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IUserResponseMapper.class);
    }

    @Test
    @DisplayName("Should map Usuario to UsuarioResponse correctly")
    void toUsuarioResponse_ValidUsuario_MapsCorrectly() {
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
        UserResponse result = mapper.toUsuarioResponse(user);

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getNombre(), result.getNombre());
        assertEquals(user.getApellido(), result.getApellido());
        assertEquals(user.getNumeroDocumento(), result.getNumeroDocumento());
        assertEquals(user.getCelular(), result.getCelular());
        assertEquals(user.getFechaNacimiento(), result.getFechaNacimiento());
        assertEquals(user.getCorreo(), result.getCorreo());
        assertEquals(user.getIdRol(), result.getIdRol());
    }

    @Test
    @DisplayName("Should handle null usuario")
    void toUsuarioResponse_NullUsuario_ReturnsNull() {
        // When
        UserResponse result = mapper.toUsuarioResponse(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle Usuario with null fields")
    void toUsuarioResponse_UsuarioWithNullFields_MapsNulls() {
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
        user.setIdRol(null);

        // When
        UserResponse result = mapper.toUsuarioResponse(user);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
        assertNull(result.getNumeroDocumento());
        assertNull(result.getCelular());
        assertNull(result.getFechaNacimiento());
        assertNull(result.getCorreo());
        assertNull(result.getIdRol());
    }

    @Test
    @DisplayName("Should exclude clave field from response")
    void toUsuarioResponse_ExcludesClave() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setClave("secretPassword");
        user.setCorreo("juan@test.com");

        // When
        UserResponse result = mapper.toUsuarioResponse(user);

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getNombre(), result.getNombre());
        assertEquals(user.getApellido(), result.getApellido());
        assertEquals(user.getCorreo(), result.getCorreo());
        // Clave should not be present in response (UsuarioResponse doesn't have clave field)
    }

    @Test
    @DisplayName("Should handle edge case values")
    void toUsuarioResponse_EdgeCaseValues_MapsCorrectly() {
        // Given
        User user = new User();
        user.setId(0L);
        user.setNombre("");
        user.setApellido("");
        user.setNumeroDocumento("");
        user.setCelular("");
        user.setFechaNacimiento(LocalDate.of(2000, 12, 31));
        user.setCorreo("");
        user.setIdRol(0L);

        // When
        UserResponse result = mapper.toUsuarioResponse(user);

        // Then
        assertNotNull(result);
        assertEquals(0L, result.getId());
        assertEquals("", result.getNombre());
        assertEquals("", result.getApellido());
        assertEquals("", result.getNumeroDocumento());
        assertEquals("", result.getCelular());
        assertEquals(LocalDate.of(2000, 12, 31), result.getFechaNacimiento());
        assertEquals("", result.getCorreo());
        assertEquals(0L, result.getIdRol());
    }
    @Test
    @DisplayName("Should map list of usuarios to response list")
    void toUsuarioResponseList_ValidList_MapsCorrectly() {
        // Given
        User user1 = new User();
        user1.setId(1L);
        user1.setNombre("Juan");
        user1.setCorreo("juan@test.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setNombre("María");
        user2.setCorreo("maria@test.com");

        List<User> users = Arrays.asList(user1, user2);

        // When
        List<UserResponse> result = mapper.toUsuarioResponseList(users);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        assertEquals("María", result.get(1).getNombre());
    }

    @Test
    @DisplayName("Should handle null list")
    void toUsuarioResponseList_NullList_ReturnsNull() {
        // When
        List<UserResponse> result = mapper.toUsuarioResponseList(null);

        // Then
        assertNull(result);
    }
}