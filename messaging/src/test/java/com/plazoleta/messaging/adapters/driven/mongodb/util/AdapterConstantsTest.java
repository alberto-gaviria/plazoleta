package com.plazoleta.messaging.adapters.driven.mongodb.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class AdapterConstantsTest {

    @Test
    void constructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants> constructor = AdapterConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void constructor_ShouldBePrivate() {
        // Given
        Constructor<?> constructor = AdapterConstants.class.getDeclaredConstructors()[0];

        // Then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void databaseConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants.Database> constructor = AdapterConstants.Database.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void databaseConstructor_ShouldBePrivate() {
        // Given
        Constructor<?> constructor = AdapterConstants.Database.class.getDeclaredConstructors()[0];

        // Then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void retryConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants.Retry> constructor = AdapterConstants.Retry.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void retryConstructor_ShouldBePrivate() {
        // Given
        Constructor<?> constructor = AdapterConstants.Retry.class.getDeclaredConstructors()[0];

        // Then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void errorMessagesConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants.ErrorMessages> constructor = AdapterConstants.ErrorMessages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void errorMessagesConstructor_ShouldBePrivate() {
        // Given
        Constructor<?> constructor = AdapterConstants.ErrorMessages.class.getDeclaredConstructors()[0];

        // Then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void databaseConstants_ShouldHaveCorrectValues() {
        // Then
        assertEquals("sms_notifications", AdapterConstants.Database.SMS_NOTIFICATIONS_COLLECTION);
        assertEquals("order_id", AdapterConstants.Database.ORDER_ID_FIELD);
        assertEquals("client_phone", AdapterConstants.Database.CLIENT_PHONE_FIELD);
        assertEquals("status", AdapterConstants.Database.STATUS_FIELD);
        assertEquals("created_at", AdapterConstants.Database.CREATED_AT_FIELD);
        assertEquals("retry_attempts", AdapterConstants.Database.RETRY_ATTEMPTS_FIELD);
    }

    @Test
    void retryConstants_ShouldHaveCorrectValues() {
        // Then
        assertEquals(24, AdapterConstants.Retry.RETRY_HOURS_LIMIT);
        assertEquals(3, AdapterConstants.Retry.MAX_RETRY_ATTEMPTS);
    }

    @Test
    void errorMessagesConstants_ShouldHaveCorrectValues() {
        // Then
        assertEquals("No se encontró la notificación especificada", AdapterConstants.ErrorMessages.NOTIFICATION_NOT_FOUND);
        assertEquals("Error de conexión con la base de datos", AdapterConstants.ErrorMessages.DATABASE_CONNECTION_ERROR);
    }

    @Test
    void allConstants_ShouldBePublicStaticFinal() throws NoSuchFieldException {
        // Database constants
        assertTrue(Modifier.isPublic(AdapterConstants.Database.class.getField("SMS_NOTIFICATIONS_COLLECTION").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Database.class.getField("SMS_NOTIFICATIONS_COLLECTION").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Database.class.getField("SMS_NOTIFICATIONS_COLLECTION").getModifiers()));

        assertTrue(Modifier.isPublic(AdapterConstants.Database.class.getField("ORDER_ID_FIELD").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Database.class.getField("ORDER_ID_FIELD").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Database.class.getField("ORDER_ID_FIELD").getModifiers()));

        assertTrue(Modifier.isPublic(AdapterConstants.Database.class.getField("CLIENT_PHONE_FIELD").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Database.class.getField("CLIENT_PHONE_FIELD").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Database.class.getField("CLIENT_PHONE_FIELD").getModifiers()));

        assertTrue(Modifier.isPublic(AdapterConstants.Database.class.getField("STATUS_FIELD").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Database.class.getField("STATUS_FIELD").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Database.class.getField("STATUS_FIELD").getModifiers()));

        assertTrue(Modifier.isPublic(AdapterConstants.Database.class.getField("CREATED_AT_FIELD").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Database.class.getField("CREATED_AT_FIELD").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Database.class.getField("CREATED_AT_FIELD").getModifiers()));

        assertTrue(Modifier.isPublic(AdapterConstants.Database.class.getField("RETRY_ATTEMPTS_FIELD").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Database.class.getField("RETRY_ATTEMPTS_FIELD").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Database.class.getField("RETRY_ATTEMPTS_FIELD").getModifiers()));

        // Retry constants
        assertTrue(Modifier.isPublic(AdapterConstants.Retry.class.getField("RETRY_HOURS_LIMIT").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Retry.class.getField("RETRY_HOURS_LIMIT").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Retry.class.getField("RETRY_HOURS_LIMIT").getModifiers()));

        assertTrue(Modifier.isPublic(AdapterConstants.Retry.class.getField("MAX_RETRY_ATTEMPTS").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.Retry.class.getField("MAX_RETRY_ATTEMPTS").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Retry.class.getField("MAX_RETRY_ATTEMPTS").getModifiers()));

        // ErrorMessages constants
        assertTrue(Modifier.isPublic(AdapterConstants.ErrorMessages.class.getField("NOTIFICATION_NOT_FOUND").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.ErrorMessages.class.getField("NOTIFICATION_NOT_FOUND").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.ErrorMessages.class.getField("NOTIFICATION_NOT_FOUND").getModifiers()));

        assertTrue(Modifier.isPublic(AdapterConstants.ErrorMessages.class.getField("DATABASE_CONNECTION_ERROR").getModifiers()));
        assertTrue(Modifier.isStatic(AdapterConstants.ErrorMessages.class.getField("DATABASE_CONNECTION_ERROR").getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.ErrorMessages.class.getField("DATABASE_CONNECTION_ERROR").getModifiers()));
    }

    @Test
    void mainClass_ShouldBeFinal() {
        // Then
        assertTrue(Modifier.isFinal(AdapterConstants.class.getModifiers()));
    }

    @Test
    void innerClasses_ShouldBeStaticAndFinal() {
        // Then
        assertTrue(Modifier.isStatic(AdapterConstants.Database.class.getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Database.class.getModifiers()));

        assertTrue(Modifier.isStatic(AdapterConstants.Retry.class.getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.Retry.class.getModifiers()));

        assertTrue(Modifier.isStatic(AdapterConstants.ErrorMessages.class.getModifiers()));
        assertTrue(Modifier.isFinal(AdapterConstants.ErrorMessages.class.getModifiers()));
    }

    @Test
    void constants_ShouldNotBeNull() {
        // Database constants
        assertNotNull(AdapterConstants.Database.SMS_NOTIFICATIONS_COLLECTION);
        assertNotNull(AdapterConstants.Database.ORDER_ID_FIELD);
        assertNotNull(AdapterConstants.Database.CLIENT_PHONE_FIELD);
        assertNotNull(AdapterConstants.Database.STATUS_FIELD);
        assertNotNull(AdapterConstants.Database.CREATED_AT_FIELD);
        assertNotNull(AdapterConstants.Database.RETRY_ATTEMPTS_FIELD);

        // ErrorMessages constants
        assertNotNull(AdapterConstants.ErrorMessages.NOTIFICATION_NOT_FOUND);
        assertNotNull(AdapterConstants.ErrorMessages.DATABASE_CONNECTION_ERROR);
    }

    @Test
    void constants_ShouldNotBeEmpty() {
        // Database constants
        assertFalse(AdapterConstants.Database.SMS_NOTIFICATIONS_COLLECTION.isEmpty());
        assertFalse(AdapterConstants.Database.ORDER_ID_FIELD.isEmpty());
        assertFalse(AdapterConstants.Database.CLIENT_PHONE_FIELD.isEmpty());
        assertFalse(AdapterConstants.Database.STATUS_FIELD.isEmpty());
        assertFalse(AdapterConstants.Database.CREATED_AT_FIELD.isEmpty());
        assertFalse(AdapterConstants.Database.RETRY_ATTEMPTS_FIELD.isEmpty());

        // ErrorMessages constants
        assertFalse(AdapterConstants.ErrorMessages.NOTIFICATION_NOT_FOUND.isEmpty());
        assertFalse(AdapterConstants.ErrorMessages.DATABASE_CONNECTION_ERROR.isEmpty());
    }

    @Test
    void retryConstants_ShouldHavePositiveValues() {
        // Then
        assertTrue(AdapterConstants.Retry.RETRY_HOURS_LIMIT > 0);
        assertTrue(AdapterConstants.Retry.MAX_RETRY_ATTEMPTS > 0);
    }

    @Test
    void databaseFieldConstants_ShouldFollowSnakeCaseConvention() {
        // Then
        assertTrue(AdapterConstants.Database.ORDER_ID_FIELD.contains("_"));
        assertTrue(AdapterConstants.Database.CLIENT_PHONE_FIELD.contains("_"));
        assertTrue(AdapterConstants.Database.CREATED_AT_FIELD.contains("_"));
        assertTrue(AdapterConstants.Database.RETRY_ATTEMPTS_FIELD.contains("_"));

        // Should not contain uppercase letters (snake_case)
        assertEquals(AdapterConstants.Database.ORDER_ID_FIELD.toLowerCase(), AdapterConstants.Database.ORDER_ID_FIELD);
        assertEquals(AdapterConstants.Database.CLIENT_PHONE_FIELD.toLowerCase(), AdapterConstants.Database.CLIENT_PHONE_FIELD);
        assertEquals(AdapterConstants.Database.STATUS_FIELD.toLowerCase(), AdapterConstants.Database.STATUS_FIELD);
        assertEquals(AdapterConstants.Database.CREATED_AT_FIELD.toLowerCase(), AdapterConstants.Database.CREATED_AT_FIELD);
        assertEquals(AdapterConstants.Database.RETRY_ATTEMPTS_FIELD.toLowerCase(), AdapterConstants.Database.RETRY_ATTEMPTS_FIELD);
    }

    @Test
    void errorMessages_ShouldBeInSpanish() {
        // Then
        assertTrue(AdapterConstants.ErrorMessages.NOTIFICATION_NOT_FOUND.contains("No se encontró"));
        assertTrue(AdapterConstants.ErrorMessages.DATABASE_CONNECTION_ERROR.contains("Error de conexión"));
    }

    @Test
    void illegalStateException_ShouldHaveCorrectMessage() {
        // Given & When
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants> constructor = AdapterConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });

        // Then
        Throwable cause = exception.getCause();
        assertInstanceOf(IllegalStateException.class, cause);
        assertEquals("Clase de constantes", cause.getMessage());
    }

    @Test
    void innerClassesIllegalStateException_ShouldHaveCorrectMessage() {
        // Database class
        InvocationTargetException databaseException = assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants.Database> constructor = AdapterConstants.Database.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });

        Throwable databaseCause = databaseException.getCause();
        assertInstanceOf(IllegalStateException.class, databaseCause);
        assertEquals("Clase de constantes", databaseCause.getMessage());

        // Retry class
        InvocationTargetException retryException = assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants.Retry> constructor = AdapterConstants.Retry.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });

        Throwable retryCause = retryException.getCause();
        assertInstanceOf(IllegalStateException.class, retryCause);
        assertEquals("Clase de constantes", retryCause.getMessage());

        // ErrorMessages class
        InvocationTargetException errorException = assertThrows(InvocationTargetException.class, () -> {
            Constructor<AdapterConstants.ErrorMessages> constructor = AdapterConstants.ErrorMessages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });

        Throwable errorCause = errorException.getCause();
        assertInstanceOf(IllegalStateException.class, errorCause);
        assertEquals("Clase de constantes", errorCause.getMessage());
    }
}