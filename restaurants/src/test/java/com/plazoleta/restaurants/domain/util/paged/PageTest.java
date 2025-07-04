package com.plazoleta.restaurants.domain.util.paged;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    @Test
    void shouldCreatePageWithDefaultConstructor() {
        // Given & When
        Page<String> page = new Page<>();

        // Then
        assertNull(page.getContent());
        assertEquals(0, page.getPageNumber());
        assertEquals(0, page.getPageSize());
        assertEquals(0L, page.getTotalElements());
        assertEquals(0, page.getTotalPages());
        assertFalse(page.isHasNext());
        assertFalse(page.isHasPrevious());
    }

    @Test
    void shouldCreatePageWithParameterizedConstructor() {
        // Given
        List<String> content = Arrays.asList("item1", "item2", "item3");
        int pageNumber = 1;
        int pageSize = 10;
        long totalElements = 25L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(content, page.getContent());
        assertEquals(pageNumber, page.getPageNumber());
        assertEquals(pageSize, page.getPageSize());
        assertEquals(totalElements, page.getTotalElements());
        assertEquals(3, page.getTotalPages()); // ceil(25/10) = 3
        assertTrue(page.isHasNext()); // pageNumber(1) < totalPages(3) - 1
        assertTrue(page.isHasPrevious()); // pageNumber(1) > 0
    }

    @Test
    void shouldCalculateTotalPagesCorrectly() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 0;
        int pageSize = 5;
        long totalElements = 12L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(3, page.getTotalPages()); // ceil(12/5) = 3
    }

    @Test
    void shouldCalculateTotalPagesWhenExactDivision() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 0;
        int pageSize = 5;
        long totalElements = 10L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(2, page.getTotalPages()); // ceil(10/5) = 2
    }

    @Test
    void shouldCalculateHasNextForFirstPage() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 0;
        int pageSize = 5;
        long totalElements = 15L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertTrue(page.isHasNext()); // pageNumber(0) < totalPages(3) - 1
        assertFalse(page.isHasPrevious()); // pageNumber(0) not > 0
    }

    @Test
    void shouldCalculateHasNextForLastPage() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 2; // Last page (0-indexed)
        int pageSize = 5;
        long totalElements = 15L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertFalse(page.isHasNext()); // pageNumber(2) not < totalPages(3) - 1
        assertTrue(page.isHasPrevious()); // pageNumber(2) > 0
    }

    @Test
    void shouldCalculateHasNextForMiddlePage() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 1; // Middle page
        int pageSize = 5;
        long totalElements = 20L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertTrue(page.isHasNext()); // pageNumber(1) < totalPages(4) - 1
        assertTrue(page.isHasPrevious()); // pageNumber(1) > 0
    }

    @Test
    void shouldHandleEmptyContent() {
        // Given
        List<String> emptyContent = Collections.emptyList();
        int pageNumber = 0;
        int pageSize = 10;
        long totalElements = 0L;

        // When
        Page<String> page = new Page<>(emptyContent, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(emptyContent, page.getContent());
        assertTrue(page.getContent().isEmpty());
        assertEquals(0, page.getTotalPages()); // ceil(0/10) = 0
        assertFalse(page.isHasNext());
        assertFalse(page.isHasPrevious());
    }

    @Test
    void shouldHandleSingleElementPage() {
        // Given
        List<String> singleContent = Collections.singletonList("single");
        int pageNumber = 0;
        int pageSize = 1;
        long totalElements = 1L;

        // When
        Page<String> page = new Page<>(singleContent, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(1, page.getContent().size());
        assertEquals("single", page.getContent().get(0));
        assertEquals(1, page.getTotalPages()); // ceil(1/1) = 1
        assertFalse(page.isHasNext()); // pageNumber(0) not < totalPages(1) - 1
        assertFalse(page.isHasPrevious()); // pageNumber(0) not > 0
    }

    @Test
    void shouldSetAndGetContent() {
        // Given
        Page<String> page = new Page<>();
        List<String> content = Arrays.asList("test1", "test2");

        // When
        page.setContent(content);

        // Then
        assertEquals(content, page.getContent());
    }

    @Test
    void shouldSetAndGetPageNumber() {
        // Given
        Page<String> page = new Page<>();
        int pageNumber = 5;

        // When
        page.setPageNumber(pageNumber);

        // Then
        assertEquals(pageNumber, page.getPageNumber());
    }

    @Test
    void shouldSetAndGetPageSize() {
        // Given
        Page<String> page = new Page<>();
        int pageSize = 20;

        // When
        page.setPageSize(pageSize);

        // Then
        assertEquals(pageSize, page.getPageSize());
    }

    @Test
    void shouldSetAndGetTotalElements() {
        // Given
        Page<String> page = new Page<>();
        long totalElements = 100L;

        // When
        page.setTotalElements(totalElements);

        // Then
        assertEquals(totalElements, page.getTotalElements());
    }

    @Test
    void shouldSetAndGetTotalPages() {
        // Given
        Page<String> page = new Page<>();
        int totalPages = 10;

        // When
        page.setTotalPages(totalPages);

        // Then
        assertEquals(totalPages, page.getTotalPages());
    }

    @Test
    void shouldSetAndGetHasNext() {
        // Given
        Page<String> page = new Page<>();
        boolean hasNext = true;

        // When
        page.setHasNext(hasNext);

        // Then
        assertEquals(hasNext, page.isHasNext());
    }

    @Test
    void shouldSetAndGetHasPrevious() {
        // Given
        Page<String> page = new Page<>();
        boolean hasPrevious = true;

        // When
        page.setHasPrevious(hasPrevious);

        // Then
        assertEquals(hasPrevious, page.isHasPrevious());
    }

    @Test
    void shouldHandleNullContent() {
        // Given
        Page<String> page = new Page<>();

        // When
        page.setContent(null);

        // Then
        assertNull(page.getContent());
    }

    @Test
    void shouldHandleZeroPageSize() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 0;
        int pageSize = 1; // Minimum valid page size
        long totalElements = 5L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(5, page.getTotalPages()); // ceil(5/1) = 5
        assertTrue(page.isHasNext()); // pageNumber(0) < totalPages(5) - 1
        assertFalse(page.isHasPrevious()); // pageNumber(0) not > 0
    }

    @Test
    void shouldHandleEdgeCaseCalculations() {
        // Given
        List<String> content = Arrays.asList("item1");
        int pageNumber = 0;
        int pageSize = 3;
        long totalElements = 1L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(1, page.getTotalPages()); // ceil(1/3) = 1
        assertFalse(page.isHasNext()); // pageNumber(0) not < totalPages(1) - 1
        assertFalse(page.isHasPrevious()); // pageNumber(0) not > 0
    }

    @Test
    void shouldHandleLargeNumbers() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 100;
        int pageSize = 50;
        long totalElements = 10000L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(200, page.getTotalPages()); // ceil(10000/50) = 200
        assertTrue(page.isHasNext()); // pageNumber(100) < totalPages(200) - 1
        assertTrue(page.isHasPrevious()); // pageNumber(100) > 0
    }

    @Test
    void shouldHandleNegativePageNumber() {
        // Given
        List<String> content = Arrays.asList("item1");
        int pageNumber = -1;
        int pageSize = 10;
        long totalElements = 20L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(2, page.getTotalPages()); // ceil(20/10) = 2
        assertTrue(page.isHasNext()); // pageNumber(-1) < totalPages(2) - 1
        assertFalse(page.isHasPrevious()); // pageNumber(-1) not > 0
    }

    @Test
    void shouldWorkWithDifferentGenericTypes() {
        // Given
        List<Integer> intContent = Arrays.asList(1, 2, 3);
        int pageNumber = 0;
        int pageSize = 5;
        long totalElements = 15L;

        // When
        Page<Integer> intPage = new Page<>(intContent, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(intContent, intPage.getContent());
        assertEquals(Integer.valueOf(1), intPage.getContent().get(0));
        assertEquals(3, intPage.getTotalPages());
    }

    @Test
    void shouldOverrideCalculatedValuesWhenSetExplicitly() {
        // Given
        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 1;
        int pageSize = 10;
        long totalElements = 25L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Override calculated values
        page.setTotalPages(5);
        page.setHasNext(false);
        page.setHasPrevious(false);

        // Then
        assertEquals(5, page.getTotalPages()); // Overridden value, not calculated
        assertFalse(page.isHasNext()); // Overridden value
        assertFalse(page.isHasPrevious()); // Overridden value
    }

    @Test
    void shouldCalculateCorrectlyWhenTotalElementsIsOne() {
        // Given
        List<String> content = Collections.singletonList("onlyItem");
        int pageNumber = 0;
        int pageSize = 10;
        long totalElements = 1L;

        // When
        Page<String> page = new Page<>(content, pageNumber, pageSize, totalElements);

        // Then
        assertEquals(1, page.getTotalPages()); // ceil(1/10) = 1
        assertFalse(page.isHasNext()); // pageNumber(0) not < totalPages(1) - 1
        assertFalse(page.isHasPrevious()); // pageNumber(0) not > 0
    }
}