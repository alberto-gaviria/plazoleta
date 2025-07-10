package com.plazoleta.traceability.adapters.driven.mongodb.mapper;

import com.plazoleta.traceability.adapters.driven.mongodb.document.OrderTraceabilityDocument;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITraceabilityDocumentMapper {

    OrderTraceability toModel(OrderTraceabilityDocument document);
    OrderTraceabilityDocument toDocument(OrderTraceability model);
    List<OrderTraceability> toModelList(List<OrderTraceabilityDocument> documents);
}