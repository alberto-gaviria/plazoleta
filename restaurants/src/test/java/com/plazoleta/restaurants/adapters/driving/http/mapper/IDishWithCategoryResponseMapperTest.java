package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.CategoryResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishWithCategoryResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.domain.model.Category;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IDishWithCategoryResponseMapperTest {

    private IDishWithCategoryResponseMapper mapper;
    private Category category;
    private DishWithCategory dishWithCategory;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IDishWithCategoryResponseMapper.class);

        category = new Category(1L, "Pizzas", "Pizzas artesanales");

        dishWithCategory = new DishWithCategory(
                1L,
                "Pizza Hawaiana",
                new BigDecimal("25500.00"),
                "Pizza con jamón, piña, queso mozzarella y salsa de tomate",
                "https://example.com/pizza-hawaiana.jpg",
                category,
                1L,
                true
        );
    }

    @Test
    void testToResponse_ValidDishWithCategory_ShouldMapCorrectly() {
        // When
        DishWithCategoryResponse result = mapper.toResponse(dishWithCategory);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizza Hawaiana", result.getNombre());
        assertEquals(new BigDecimal("25500.00"), result.getPrecio());
        assertEquals("Pizza con jamón, piña, queso mozzarella y salsa de tomate", result.getDescripcion());
        assertEquals("https://example.com/pizza-hawaiana.jpg", result.getUrlImagen());
        assertEquals(true, result.getActivo());

        assertNotNull(result.getCategoria());
        assertEquals(1L, result.getCategoria().getId());
        assertEquals("Pizzas", result.getCategoria().getNombre());
        assertEquals("Pizzas artesanales", result.getCategoria().getDescripcion());
    }

    @Test
    void testToResponse_NullDishWithCategory_ShouldReturnNull() {
        // When
        DishWithCategoryResponse result = mapper.toResponse(null);

        // Then
        assertNull(result);
    }

    @Test
    void testToResponse_DishWithNullCategory_ShouldMapWithNullCategory() {
        // Given
        DishWithCategory dishWithNullCategory = new DishWithCategory(
                2L,
                "Pizza Sin Categoría",
                new BigDecimal("20000.00"),
                "Pizza sin categoría definida",
                "https://example.com/pizza.jpg",
                null, // Categoría nula
                1L,
                false
        );

        // When
        DishWithCategoryResponse result = mapper.toResponse(dishWithNullCategory);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Pizza Sin Categoría", result.getNombre());
        assertEquals(new BigDecimal("20000.00"), result.getPrecio());
        assertEquals("Pizza sin categoría definida", result.getDescripcion());
        assertEquals("https://example.com/pizza.jpg", result.getUrlImagen());
        assertEquals(false, result.getActivo());
        assertNull(result.getCategoria());
    }

    @Test
    void testCategoryToResponse_ValidCategory_ShouldMapCorrectly() {
        // When
        CategoryResponse result = mapper.categoryToResponse(category);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizzas", result.getNombre());
        assertEquals("Pizzas artesanales", result.getDescripcion());
    }

    @Test
    void testCategoryToResponse_NullCategory_ShouldReturnNull() {
        // When
        CategoryResponse result = mapper.categoryToResponse(null);

        // Then
        assertNull(result);
    }

    @Test
    void testCategoryToResponse_CategoryWithNullFields_ShouldMapNullFields() {
        // Given
        Category categoryWithNulls = new Category(null, null, null);

        // When
        CategoryResponse result = mapper.categoryToResponse(categoryWithNulls);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getDescripcion());
    }

    @Test
    void testToResponseList_ValidList_ShouldMapAllElements() {
        // Given
        Category category2 = new Category(2L, "Hamburguesas", "Hamburguesas gourmet");
        DishWithCategory dishWithCategory2 = new DishWithCategory(
                2L, "Hamburguesa Clásica", new BigDecimal("18500.00"),
                "Hamburguesa de carne con lechuga, tomate y queso",
                "https://example.com/hamburguesa-clasica.jpg", category2, 1L, true
        );

        List<DishWithCategory> dishes = Arrays.asList(dishWithCategory, dishWithCategory2);

        // When
        List<DishWithCategoryResponse> result = mapper.toResponseList(dishes);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar primer elemento
        assertEquals("Pizza Hawaiana", result.get(0).getNombre());
        assertEquals("Pizzas", result.get(0).getCategoria().getNombre());

        // Verificar segundo elemento
        assertEquals("Hamburguesa Clásica", result.get(1).getNombre());
        assertEquals("Hamburguesas", result.get(1).getCategoria().getNombre());
    }

    @Test
    void testToResponseList_EmptyList_ShouldReturnEmptyList() {
        // Given
        List<DishWithCategory> emptyList = Collections.emptyList();

        // When
        List<DishWithCategoryResponse> result = mapper.toResponseList(emptyList);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToResponseList_NullList_ShouldReturnNull() {
        // When
        List<DishWithCategoryResponse> result = mapper.toResponseList(null);

        // Then
        assertNull(result);
    }

    @Test
    void testToResponseList_ListWithNullElements_ShouldHandleNulls() {
        // Given
        List<DishWithCategory> listWithNulls = Arrays.asList(dishWithCategory, null);

        // When
        List<DishWithCategoryResponse> result = mapper.toResponseList(listWithNulls);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pizza Hawaiana", result.get(0).getNombre());
        assertNull(result.get(1));
    }

    @Test
    void testToPageResponse_ValidPage_ShouldMapCorrectly() {
        // Given
        List<DishWithCategory> content = Arrays.asList(dishWithCategory);
        Page<DishWithCategory> page = new Page<>(content, 0, 10, 1L);

        // When
        PageResponse<DishWithCategoryResponse> result = mapper.toPageResponse(page);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(1L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertFalse(result.isHasNext());
        assertFalse(result.isHasPrevious());

        // Verificar contenido mapeado
        DishWithCategoryResponse firstDish = result.getContent().get(0);
        assertEquals("Pizza Hawaiana", firstDish.getNombre());
        assertEquals("Pizzas", firstDish.getCategoria().getNombre());
    }

    @Test
    void testToPageResponse_EmptyPage_ShouldMapCorrectly() {
        // Given
        List<DishWithCategory> emptyContent = Collections.emptyList();
        Page<DishWithCategory> emptyPage = new Page<>(emptyContent, 0, 10, 0L);

        // When
        PageResponse<DishWithCategoryResponse> result = mapper.toPageResponse(emptyPage);

        // Then
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(0L, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertFalse(result.isHasNext());
        assertFalse(result.isHasPrevious());
    }

    @Test
    void testToPageResponse_PageWithPagination_ShouldCalculateCorrectly() {
        // Given
        List<DishWithCategory> content = Arrays.asList(dishWithCategory);
        // Página 1 de 3, con 10 elementos por página y 25 elementos totales
        Page<DishWithCategory> page = new Page<>(content, 1, 10, 25L);

        // When
        PageResponse<DishWithCategoryResponse> result = mapper.toPageResponse(page);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(25L, result.getTotalElements());
        assertEquals(3, result.getTotalPages()); // 25/10 = 3 páginas
        assertTrue(result.isHasNext());
        assertTrue(result.isHasPrevious());
    }

    @Test
    void testToPageResponse_FirstPage_ShouldNotHavePrevious() {
        // Given
        List<DishWithCategory> content = Arrays.asList(dishWithCategory);
        Page<DishWithCategory> firstPage = new Page<>(content, 0, 10, 25L);

        // When
        PageResponse<DishWithCategoryResponse> result = mapper.toPageResponse(firstPage);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getPageNumber());
        assertTrue(result.isHasNext());
        assertFalse(result.isHasPrevious());
    }

    @Test
    void testToPageResponse_LastPage_ShouldNotHaveNext() {
        // Given
        List<DishWithCategory> content = Arrays.asList(dishWithCategory);
        Page<DishWithCategory> lastPage = new Page<>(content, 2, 10, 25L);

        // When
        PageResponse<DishWithCategoryResponse> result = mapper.toPageResponse(lastPage);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getPageNumber());
        assertFalse(result.isHasNext());
        assertTrue(result.isHasPrevious());
    }

    @Test
    void testToPageResponse_MultipleDishes_ShouldMapAllCorrectly() {
        // Given
        Category category2 = new Category(2L, "Bebidas", "Bebidas frías y calientes");
        DishWithCategory dish2 = new DishWithCategory(
                2L, "Coca Cola", new BigDecimal("4500.00"),
                "Bebida gaseosa 350ml", "https://example.com/coca-cola.jpg",
                category2, 1L, true
        );

        List<DishWithCategory> content = Arrays.asList(dishWithCategory, dish2);
        Page<DishWithCategory> page = new Page<>(content, 0, 10, 2L);

        // When
        PageResponse<DishWithCategoryResponse> result = mapper.toPageResponse(page);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Pizza Hawaiana", result.getContent().get(0).getNombre());
        assertEquals("Pizzas", result.getContent().get(0).getCategoria().getNombre());
        assertEquals("Coca Cola", result.getContent().get(1).getNombre());
        assertEquals("Bebidas", result.getContent().get(1).getCategoria().getNombre());
    }

    @Test
    void testToPageResponse_SingleElementPage_ShouldCalculateCorrectly() {
        // Given
        List<DishWithCategory> content = Arrays.asList(dishWithCategory);
        Page<DishWithCategory> singlePage = new Page<>(content, 0, 10, 1L);

        // When
        PageResponse<DishWithCategoryResponse> result = mapper.toPageResponse(singlePage);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalPages());
        assertFalse(result.isHasNext());
        assertFalse(result.isHasPrevious());
    }
}