package com.plazoleta.users.adapters.driving.http.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddUserRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validAddUsuarioRequest_ShouldPassValidation() {
        // Given
        AddUserRequest request = createValidRequest();

        // When
        Set<ConstraintViolation<AddUserRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void addUsuarioRequest_WithBlankNombre_ShouldFailValidation() {
        // Given
        AddUserRequest request = createValidRequest();
        request.setNombre("");

        // When
        Set<ConstraintViolation<AddUserRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
    }

    @Test
    void addUsuarioRequest_WithInvalidEmail_ShouldFailValidation() {
        // Given
        AddUserRequest request = createValidRequest();
        request.setCorreo("email-invalido");

        // When
        Set<ConstraintViolation<AddUserRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
    }

    private AddUserRequest createValidRequest() {
        AddUserRequest request = new AddUserRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setNumeroDocumento("12345678");
        request.setCelular("+573001234567");
        request.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        request.setCorreo("juan@email.com");
        request.setClave("password123");
        return request;
    }
}