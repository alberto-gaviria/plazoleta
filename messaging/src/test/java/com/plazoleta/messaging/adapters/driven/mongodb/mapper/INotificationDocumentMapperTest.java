package com.plazoleta.messaging.adapters.driven.mongodb.mapper;

import com.plazoleta.messaging.adapters.driven.mongodb.document.SmsNotificationDocument;
import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class INotificationDocumentMapperTest {

    private final INotificationDocumentMapper mapper = Mappers.getMapper(INotificationDocumentMapper.class);

    @Test
    void testToDocument() {
        SmsNotification model = createSampleModel();

        SmsNotificationDocument document = mapper.toDocument(model);

        assertNotNull(document);
        assertEquals(model.getId(), document.getId());
        assertEquals(model.getOrderId(), document.getOrderId());
        assertEquals(model.getClientPhone(), document.getClientPhone());
        assertEquals(model.getMessage(), document.getMessage());
        assertEquals(model.getPin(), document.getPin());
        assertEquals(model.getRestaurantName(), document.getRestaurantName());
        assertEquals(model.getStatus(), document.getStatus());
        assertEquals(model.getCreatedAt(), document.getCreatedAt());
        assertEquals(model.getRetryAttempts(), document.getRetryAttempts());
        // updatedAt is ignored
        assertNull(document.getUpdatedAt());
    }

    @Test
    void testToModel() {
        SmsNotificationDocument document = createSampleDocument();

        SmsNotification model = mapper.toModel(document);

        assertNotNull(model);
        assertEquals(document.getId(), model.getId());
        assertEquals(document.getOrderId(), model.getOrderId());
        assertEquals(document.getClientPhone(), model.getClientPhone());
        assertEquals(document.getMessage(), model.getMessage());
        assertEquals(document.getPin(), model.getPin());
        assertEquals(document.getRestaurantName(), model.getRestaurantName());
        assertEquals(document.getStatus(), model.getStatus());
        assertEquals(document.getCreatedAt(), model.getCreatedAt());
        assertEquals(document.getRetryAttempts(), model.getRetryAttempts());
    }

    @Test
    void testToModelList() {
        SmsNotificationDocument document = createSampleDocument();

        List<SmsNotification> modelList = mapper.toModelList(Collections.singletonList(document));

        assertNotNull(modelList);
        assertEquals(1, modelList.size());
        assertEquals(document.getId(), modelList.get(0).getId());
    }

    @Test
    void testToDocumentList() {
        SmsNotification model = createSampleModel();

        List<SmsNotificationDocument> docList = mapper.toDocumentList(Collections.singletonList(model));

        assertNotNull(docList);
        assertEquals(1, docList.size());
        assertEquals(model.getId(), docList.get(0).getId());
    }

    private SmsNotification createSampleModel() {
        SmsNotification notification = new SmsNotification();
        notification.setId("123");
        notification.setOrderId("order-456");
        notification.setClientPhone("+1234567890");
        notification.setMessage("Test message");
        notification.setPin("1234");
        notification.setRestaurantName("Restaurant A");
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRetryAttempts(1);
        return notification;
    }

    private SmsNotificationDocument createSampleDocument() {
        SmsNotificationDocument doc = new SmsNotificationDocument();
        doc.setId("123");
        doc.setOrderId("order-456");
        doc.setClientPhone("+1234567890");
        doc.setMessage("Test message");
        doc.setPin("1234");
        doc.setRestaurantName("Restaurant A");
        doc.setStatus(NotificationStatus.PENDING);
        doc.setCreatedAt(LocalDateTime.now());
        doc.setUpdatedAt(LocalDateTime.now());
        doc.setRetryAttempts(1);
        return doc;
    }
}
