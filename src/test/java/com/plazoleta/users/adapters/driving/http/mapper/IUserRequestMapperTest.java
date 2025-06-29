package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUsuarioRequestMapper Tests")
class IUserRequestMapperTest {

    private IUserRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IUserRequestMapper.class);
    }

    @Test
    @DisplayName("Should map AddUsuarioRequest to Usuario correctly")
    void addRequestToUsuario_ValidRequest_MapsCorrectly() {
        // Given
        AddUserRequest request = new AddUserRequest(
                "Juan",
                "Pérez",
                "12345678",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@test.com",
                "password123"
        );

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(request.getNombre(), result.getNombre());
        assertEquals(request.getApellido(), result.getApellido());
        assertEquals(request.getNumeroDocumento(), result.getNumeroDocumento());
        assertEquals(request.getCelular(), result.getCelular());
        assertEquals(request.getFechaNacimiento(), result.getFechaNacimiento());
        assertEquals(request.getCorreo(), result.getCorreo());
        assertEquals(request.getClave(), result.getClave());
        assertNull(result.getIdRol());
    }

    @Test
    @DisplayName("Should handle null request")
    void addRequestToUsuario_NullRequest_ReturnsNull() {
        // When
        User result = mapper.addRequestToUsuario(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle request with null fields")
    void addRequestToUsuario_NullFields_MapsNulls() {
        // Given
        AddUserRequest request = new AddUserRequest(null, null, null, null, null, null, null);

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
        assertNull(result.getNumeroDocumento());
        assertNull(result.getCelular());
        assertNull(result.getFechaNacimiento());
        assertNull(result.getCorreo());
        assertNull(result.getClave());
        assertNull(result.getIdRol());
    }

    @Test
    @DisplayName("Should always set idRol to null due to ignore mapping")
    void addRequestToUsuario_AlwaysSetsIdRolToNull() {
        // Given
        AddUserRequest request = new AddUserRequest(
                "Juan", "Pérez", "12345678", "+573001234567",
                LocalDate.of(1990, 1, 1), "juan@test.com", "password123"
        );

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertNull(result.getIdRol());
    }

    @Test
    @DisplayName("Should handle edge case date values")
    void addRequestToUsuario_EdgeCaseDates_MapsCorrectly() {
        // Given
        LocalDate edgeCaseDate = LocalDate.of(2000, 12, 31);
        AddUserRequest request = new AddUserRequest(
                "Test", "User", "87654321", "+573009876543",
                edgeCaseDate, "test@example.com", "testpass"
        );

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertEquals(edgeCaseDate, result.getFechaNacimiento());
        assertNull(result.getIdRol());
    }
}