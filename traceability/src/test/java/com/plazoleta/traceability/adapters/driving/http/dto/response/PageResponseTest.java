package com.plazoleta.traceability.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageResponseTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        PageResponse<String> response = new PageResponse<>();

        List<String> content = Arrays.asList("item1", "item2");
        int pageNumber = 2;
        int pageSize = 10;
        long totalElements = 45L;
        int totalPages = 5;
        boolean hasNext = true;
        boolean hasPrevious = false;

        response.setContent(content);
        response.setPageNumber(pageNumber);
        response.setPageSize(pageSize);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        response.setHasNext(hasNext);
        response.setHasPrevious(hasPrevious);

        assertEquals(content, response.getContent());
        assertEquals(pageNumber, response.getPageNumber());
        assertEquals(pageSize, response.getPageSize());
        assertEquals(totalElements, response.getTotalElements());
        assertEquals(totalPages, response.getTotalPages());
        assertTrue(response.isHasNext());
        assertFalse(response.isHasPrevious());
    }

    @Test
    void testAllArgsConstructor() {
        List<String> content = List.of("A", "B", "C");
        int pageNumber = 1;
        int pageSize = 3;
        long totalElements = 9L;
        int totalPages = 3;
        boolean hasNext = false;
        boolean hasPrevious = true;

        PageResponse<String> response = new PageResponse<>(
                content, pageNumber, pageSize, totalElements, totalPages, hasNext, hasPrevious
        );

        assertEquals(content, response.getContent());
        assertEquals(pageNumber, response.getPageNumber());
        assertEquals(pageSize, response.getPageSize());
        assertEquals(totalElements, response.getTotalElements());
        assertEquals(totalPages, response.getTotalPages());
        assertFalse(response.isHasNext());
        assertTrue(response.isHasPrevious());
    }
}
