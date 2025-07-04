package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantSummaryResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRestaurantSummaryMapper {

    RestaurantSummaryResponse toResponse(RestaurantSummary restaurantSummary);

    List<RestaurantSummaryResponse> toResponseList(List<RestaurantSummary> restaurantSummaries);

    default PageResponse<RestaurantSummaryResponse> toPageResponse(Page<RestaurantSummary> page) {
        List<RestaurantSummaryResponse> content = toResponseList(page.getContent());
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