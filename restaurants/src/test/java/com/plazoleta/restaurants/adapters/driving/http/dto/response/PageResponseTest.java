package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PageResponse Tests")
class PageResponseTest {

    @Test
    @DisplayName("Should create PageResponse with default constructor")
    void shouldCreatePageResponseWithDefaultConstructor() {
        // Given & When
        PageResponse<String> pageResponse = new PageResponse<>();

        // Then
        assertNull(pageResponse.getContent());
        assertEquals(0, pageResponse.getPageNumber());
        assertEquals(0, pageResponse.getPageSize());
        assertEquals(0L, pageResponse.getTotalElements());
        assertEquals(0, pageResponse.getTotalPages());
        assertFalse(pageResponse.isHasNext());
        assertFalse(pageResponse.isHasPrevious());
    }

    @Test
    @DisplayName("Should create PageResponse with parameterized constructor")
    void shouldCreatePageResponseWithParameterizedConstructor() {
        // Given
        List<String> content = Arrays.asList("item1", "item2", "item3");
        int pageNumber = 1;
        int pageSize = 10;
        long totalElements = 25L;
        int totalPages = 3;
        boolean hasNext = true;
        boolean hasPrevious = false;

        // When
        PageResponse<String> pageResponse = new PageResponse<>(
                content, pageNumber, pageSize, totalElements, totalPages, hasNext, hasPrevious
        );

        // Then
        assertEquals(content, pageResponse.getContent());
        assertEquals(pageNumber, pageResponse.getPageNumber());
        assertEquals(pageSize, pageResponse.getPageSize());
        assertEquals(totalElements, pageResponse.getTotalElements());
        assertEquals(totalPages, pageResponse.getTotalPages());
        assertEquals(hasNext, pageResponse.isHasNext());
        assertEquals(hasPrevious, pageResponse.isHasPrevious());
    }

    @Test
    @DisplayName("Should set and get content correctly")
    void shouldSetAndGetContent() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();
        List<String> content = Arrays.asList("test1", "test2");

        // When
        pageResponse.setContent(content);

        // Then
        assertEquals(content, pageResponse.getContent());
    }

    @Test
    @DisplayName("Should set and get pageNumber correctly")
    void shouldSetAndGetPageNumber() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();
        int pageNumber = 5;

        // When
        pageResponse.setPageNumber(pageNumber);

        // Then
        assertEquals(pageNumber, pageResponse.getPageNumber());
    }

    @Test
    @DisplayName("Should set and get pageSize correctly")
    void shouldSetAndGetPageSize() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();
        int pageSize = 20;

        // When
        pageResponse.setPageSize(pageSize);

        // Then
        assertEquals(pageSize, pageResponse.getPageSize());
    }

    @Test
    @DisplayName("Should set and get totalElements correctly")
    void shouldSetAndGetTotalElements() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();
        long totalElements = 100L;

        // When
        pageResponse.setTotalElements(totalElements);

        // Then
        assertEquals(totalElements, pageResponse.getTotalElements());
    }

    @Test
    @DisplayName("Should set and get totalPages correctly")
    void shouldSetAndGetTotalPages() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();
        int totalPages = 10;

        // When
        pageResponse.setTotalPages(totalPages);

        // Then
        assertEquals(totalPages, pageResponse.getTotalPages());
    }

    @Test
    @DisplayName("Should set and get hasNext correctly")
    void shouldSetAndGetHasNext() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();
        boolean hasNext = true;

        // When
        pageResponse.setHasNext(hasNext);

        // Then
        assertEquals(hasNext, pageResponse.isHasNext());
    }

    @Test
    @DisplayName("Should set and get hasPrevious correctly")
    void shouldSetAndGetHasPrevious() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();
        boolean hasPrevious = true;

        // When
        pageResponse.setHasPrevious(hasPrevious);

        // Then
        assertEquals(hasPrevious, pageResponse.isHasPrevious());
    }

    @Test
    @DisplayName("Should handle null content")
    void shouldHandleNullContent() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();

        // When
        pageResponse.setContent(null);

        // Then
        assertNull(pageResponse.getContent());
    }

    @Test
    @DisplayName("Should handle edge values")
    void shouldHandleEdgeValues() {
        // Given
        PageResponse<String> pageResponse = new PageResponse<>();

        // When & Then
        pageResponse.setPageNumber(-1);
        assertEquals(-1, pageResponse.getPageNumber());

        pageResponse.setPageSize(0);
        assertEquals(0, pageResponse.getPageSize());

        pageResponse.setTotalElements(0L);
        assertEquals(0L, pageResponse.getTotalElements());

        pageResponse.setTotalPages(0);
        assertEquals(0, pageResponse.getTotalPages());
    }
}