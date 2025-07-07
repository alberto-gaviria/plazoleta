package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.CategoryResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishWithCategoryResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.domain.model.Category;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDishWithCategoryResponseMapper {

    @Mapping(target = "categoria", source = "categoria")
    DishWithCategoryResponse toResponse(DishWithCategory dishWithCategory);

    CategoryResponse categoryToResponse(Category category);

    List<DishWithCategoryResponse> toResponseList(List<DishWithCategory> dishes);

    default PageResponse<DishWithCategoryResponse> toPageResponse(Page<DishWithCategory> page) {
        List<DishWithCategoryResponse> content = toResponseList(page.getContent());
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
