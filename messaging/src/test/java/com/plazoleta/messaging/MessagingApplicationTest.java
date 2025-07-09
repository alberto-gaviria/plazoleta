package com.plazoleta.messaging;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessagingApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto se cargue correctamente
    }

    @Test
    void mainMethod_ShouldStartApplicationWithoutErrors() {
        assertDoesNotThrow(() -> MessagingApplication.main(new String[]{}));
    }
}