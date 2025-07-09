package com.plazoleta.messaging.adapters.driving.http.mapper;

import com.plazoleta.messaging.adapters.driving.http.dto.request.OrderReadyNotificationRequest;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface INotificationRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "errorMessage", ignore = true)
    @Mapping(target = "retryAttempts", ignore = true)
    SmsNotification toModel(OrderReadyNotificationRequest request);
}