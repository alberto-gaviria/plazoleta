package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderDishResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderResponseMapper {

    @Mapping(source = "estado", target = "estado")
    OrderResponse orderToResponse(Order order);

    OrderDishResponse orderDishToResponse(OrderDish orderDish);

    List<OrderResponse> orderListToResponseList(List<Order> orders);

    default String mapOrderStatus(com.plazoleta.restaurants.domain.model.OrderStatus status) {
        return status != null ? status.name() : null;
    }

    default PageResponse<OrderResponse> toPageResponse(Page<Order> page) {
        List<OrderResponse> content = orderListToResponseList(page.getContent());
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