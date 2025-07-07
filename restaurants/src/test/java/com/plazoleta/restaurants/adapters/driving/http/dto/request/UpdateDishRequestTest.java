package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateDishRequestTest {

    private final Validator validator;

    public UpdateDishRequestFullTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    @DisplayName("1) Constructores, getters y setters")
    void testConstructorsAndAccessors() {
        BigDecimal precio = new BigDecimal("42.42");
        String desc = "Prueba";

        // all‑args constructor
        UpdateDishRequest fromAllArgs = new UpdateDishRequest(precio, desc);
        assertAll("all-args",
                () -> assertSame(precio, fromAllArgs.getPrecio()),
                () -> assertEquals(desc, fromAllArgs.getDescripcion())
        );

        // default + setters
        UpdateDishRequest fromDefault = new UpdateDishRequest();
        assertAll("default-inicial",
                () -> assertNull(fromDefault.getPrecio()),
                () -> assertNull(fromDefault.getDescripcion())
        );
        fromDefault.setPrecio(precio);
        fromDefault.setDescripcion(desc);
        assertAll("default-after-set",
                () -> assertEquals(new BigDecimal("42.42"), fromDefault.getPrecio()),
                () -> assertEquals("Prueba", fromDefault.getDescripcion())
        );
    }

    @Test
    @DisplayName("2) Validaciones de Bean Validation")
    void testBeanValidations() {


        UpdateDishRequest r1 = new UpdateDishRequest(null, "X");
        Set<ConstraintViolation<UpdateDishRequest>> v1 = validator.validate(r1);
        assertTrue(v1.stream().anyMatch(v -> v.getPropertyPath().toString().equals("precio")
                && v.getMessage().contains("obligatorio")));


        UpdateDishRequest r2 = new UpdateDishRequest(new BigDecimal("0.001"), "X");
        Set<ConstraintViolation<UpdateDishRequest>> v2 = validator.validate(r2);
        assertTrue(v2.stream().anyMatch(v -> v.getPropertyPath().toString().equals("precio")
                && v.getMessage().contains("mayor a 0")));

        UpdateDishRequest r3 = new UpdateDishRequest(new BigDecimal("12345678901.234"), "X");
        Set<ConstraintViolation<UpdateDishRequest>> v3 = validator.validate(r3);
        assertEquals(2, v3.size(), "Debe fallar por integer>10 y fraction>2");

        UpdateDishRequest r4 = new UpdateDishRequest(BigDecimal.ONE, null);
        UpdateDishRequest r5 = new UpdateDishRequest(BigDecimal.ONE, "   ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) sb.append("a");
        UpdateDishRequest r6 = new UpdateDishRequest(BigDecimal.ONE, sb.toString());

        assertFalse(validator.validate(r4).isEmpty(), "descripcion null debe fallar");
        assertFalse(validator.validate(r5).isEmpty(), "descripcion blank debe fallar");
        assertFalse(validator.validate(r6).isEmpty(), "descripcion >500 debe fallar");

        // caso válido
        UpdateDishRequest ok = new UpdateDishRequest(new BigDecimal("10.50"), "Valido");
        assertTrue(validator.validate(ok).isEmpty(), "Caso válido sin violaciones");
    }

    @Test
    @DisplayName("3) Verifica presencia de anotaciones con reflexión")
    void testAnnotationsPresence() throws NoSuchFieldException {
        Field precioField = UpdateDishRequest.class.getDeclaredField("precio");
        assertAll("precio-annotations",
                () -> assertNotNull(precioField.getAnnotation(NotNull.class)),
                () -> assertNotNull(precioField.getAnnotation(DecimalMin.class)),
                () -> assertNotNull(precioField.getAnnotation(Digits.class))
        );

        Field descField = UpdateDishRequest.class.getDeclaredField("descripcion");
        assertAll("descripcion-annotations",
                () -> assertNotNull(descField.getAnnotation(NotBlank.class)),
                () -> assertNotNull(descField.getAnnotation(Size.class))
        );
    }

    @Test
    @DisplayName("4) Serialización y deserialización JSON con Jackson")
    void testJsonSerialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();


        String jsonIn = "{\"precio\":5.75,\"descripcion\":\"Texto JSON\"}";
        UpdateDishRequest in = mapper.readValue(jsonIn, UpdateDishRequest.class);
        assertAll("from-json",
                () -> assertEquals(new BigDecimal("5.75"), in.getPrecio()),
                () -> assertEquals("Texto JSON", in.getDescripcion())
        );

        String jsonOut = mapper.writeValueAsString(in);
        assertTrue(jsonOut.contains("\"precio\":5.75"));
        assertTrue(jsonOut.contains("\"descripcion\":\"Texto JSON\""));


        UpdateDishRequest roundTrip = mapper.readValue(jsonOut, UpdateDishRequest.class);
        assertEquals(in.getPrecio(), roundTrip.getPrecio());
        assertEquals(in.getDescripcion(), roundTrip.getDescripcion());
    }
}
