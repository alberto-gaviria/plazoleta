package com.plazoleta.messaging.adapters.driven.mongodb.mapper;

import com.plazoleta.messaging.adapters.driven.mongodb.document.SmsNotificationDocument;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface INotificationDocumentMapper {

    @Mapping(target = "updatedAt", ignore = true)
    SmsNotificationDocument toDocument(SmsNotification notification);

    SmsNotification toModel(SmsNotificationDocument document);

    List<SmsNotification> toModelList(List<SmsNotificationDocument> documents);

    List<SmsNotificationDocument> toDocumentList(List<SmsNotification> notifications);
}