package com.plazoleta.traceability.adapters.driving.http.mapper;

import com.plazoleta.traceability.adapters.driving.http.dto.request.RecordStatusChangeRequest;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRecordStatusChangeRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    OrderTraceability toModel(RecordStatusChangeRequest request);
}