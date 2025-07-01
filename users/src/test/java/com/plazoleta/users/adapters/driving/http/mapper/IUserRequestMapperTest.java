package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IUserRequestMapper Tests")
class IUserRequestMapperTest {

    private IUserRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IUserRequestMapper.class);
    }

    @Test
    @DisplayName("Should map AddUserRequest to User correctly")
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
        assertNull(result.getId()); // Ignored by mapping
        assertEquals(request.getNombre(), result.getNombre());
        assertEquals(request.getApellido(), result.getApellido());
        assertEquals(request.getNumeroDocumento(), result.getNumeroDocumento());
        assertEquals(request.getCelular(), result.getCelular());
        assertEquals(request.getFechaNacimiento(), result.getFechaNacimiento());
        assertEquals(request.getCorreo(), result.getCorreo());
        assertEquals(request.getClave(), result.getClave());
        assertNull(result.getRoleType()); // Ignored by mapping - se asigna en UseCase
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
        assertNull(result.getId()); // Always null due to ignore mapping
        assertNull(result.getNombre());
        assertNull(result.getApellido());
        assertNull(result.getNumeroDocumento());
        assertNull(result.getCelular());
        assertNull(result.getFechaNacimiento());
        assertNull(result.getCorreo());
        assertNull(result.getClave());
        assertNull(result.getRoleType()); // Always null due to ignore mapping
    }

    @Test
    @DisplayName("Should always set id and roleType to null due to ignore mapping")
    void addRequestToUsuario_AlwaysSetsIgnoredFieldsToNull() {
        // Given
        AddUserRequest request = new AddUserRequest(
                "Juan", "Pérez", "12345678", "+573001234567",
                LocalDate.of(1990, 1, 1), "juan@test.com", "password123"
        );

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertNull(result.getId()); // Ignored field
        assertNull(result.getRoleType()); // Ignored field - se asigna en UseCase
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
        assertNull(result.getId()); // Ignored
        assertNull(result.getRoleType()); // Ignored
    }

    @Test
    @DisplayName("Should handle empty string values")
    void addRequestToUsuario_EmptyStrings_MapsEmptyStrings() {
        // Given
        AddUserRequest request = new AddUserRequest(
                "", "", "", "",
                LocalDate.of(1990, 1, 1), "", ""
        );

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertEquals("", result.getNombre());
        assertEquals("", result.getApellido());
        assertEquals("", result.getNumeroDocumento());
        assertEquals("", result.getCelular());
        assertEquals("", result.getCorreo());
        assertEquals("", result.getClave());
        assertNull(result.getId());
        assertNull(result.getRoleType());
    }

    @Test
    @DisplayName("Should preserve all mapped field values correctly")
    void addRequestToUsuario_CompleteMapping_PreservesAllValues() {
        // Given
        String expectedNombre = "María";
        String expectedApellido = "González";
        String expectedDocumento = "98765432";
        String expectedCelular = "+573009876543";
        LocalDate expectedFecha = LocalDate.of(1985, 5, 15);
        String expectedCorreo = "maria@example.com";
        String expectedClave = "securePassword123";

        AddUserRequest request = new AddUserRequest(
                expectedNombre, expectedApellido, expectedDocumento,
                expectedCelular, expectedFecha, expectedCorreo, expectedClave
        );

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertEquals(expectedNombre, result.getNombre());
        assertEquals(expectedApellido, result.getApellido());
        assertEquals(expectedDocumento, result.getNumeroDocumento());
        assertEquals(expectedCelular, result.getCelular());
        assertEquals(expectedFecha, result.getFechaNacimiento());
        assertEquals(expectedCorreo, result.getCorreo());
        assertEquals(expectedClave, result.getClave());

        // Campos ignorados siempre null
        assertNull(result.getId());
        assertNull(result.getRoleType());
    }

    @Test
    @DisplayName("Should handle special characters in string fields")
    void addRequestToUsuario_SpecialCharacters_MapsCorrectly() {
        // Given
        AddUserRequest request = new AddUserRequest(
                "José María", "Pérez-González", "12345678A",
                "+57 300 123 4567", LocalDate.of(1990, 1, 1),
                "jose.maria@email.com", "password@123!"
        );

        // When
        User result = mapper.addRequestToUsuario(request);

        // Then
        assertNotNull(result);
        assertEquals("José María", result.getNombre());
        assertEquals("Pérez-González", result.getApellido());
        assertEquals("12345678A", result.getNumeroDocumento());
        assertEquals("+57 300 123 4567", result.getCelular());
        assertEquals("jose.maria@email.com", result.getCorreo());
        assertEquals("password@123!", result.getClave());
        assertNull(result.getId());
        assertNull(result.getRoleType());
    }
}