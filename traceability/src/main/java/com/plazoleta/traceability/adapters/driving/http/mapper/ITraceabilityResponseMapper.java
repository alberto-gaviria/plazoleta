package com.plazoleta.traceability.adapters.driving.http.mapper;

import com.plazoleta.traceability.adapters.driving.http.dto.response.GetOrderTraceabilityResponse;
import com.plazoleta.traceability.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.util.paged.Page;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITraceabilityResponseMapper {

    GetOrderTraceabilityResponse toResponse(OrderTraceability orderTraceability);
    List<GetOrderTraceabilityResponse> toResponseList(List<OrderTraceability> traceabilities);

    default PageResponse<GetOrderTraceabilityResponse> toPageResponse(Page<OrderTraceability> page) {
        List<GetOrderTraceabilityResponse> content = toResponseList(page.getContent());
        return new PageResponse<>(
                content,
                page.getPageNumber(),
                page.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isHasNext(),
                page.isHasPrevious()
        );
    }
}