package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.domain.model.Category;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ICategoryEntityMapper.class})
public interface IDishWithCategoryMapper {

    @Mapping(target = "categoria", source = "category")
    @Mapping(source = "dishEntity.id", target = "id")
    @Mapping(source = "dishEntity.nombre", target = "nombre")
    @Mapping(source = "dishEntity.precio", target = "precio")
    @Mapping(source = "dishEntity.descripcion", target = "descripcion")
    @Mapping(source = "dishEntity.urlImagen", target = "urlImagen")
    @Mapping(source = "dishEntity.idRestaurante", target = "idRestaurante")
    @Mapping(source = "dishEntity.activo", target = "activo")
    DishWithCategory toModel(DishEntity dishEntity, Category category);
}