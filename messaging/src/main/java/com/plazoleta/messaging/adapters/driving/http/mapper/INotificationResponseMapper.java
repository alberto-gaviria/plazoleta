package com.plazoleta.messaging.adapters.driving.http.mapper;

import com.plazoleta.messaging.adapters.driving.http.dto.response.NotificationResponse;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface INotificationResponseMapper {

    NotificationResponse toResponse(SmsNotification notification);

    List<NotificationResponse> toResponseList(List<SmsNotification> notifications);
}