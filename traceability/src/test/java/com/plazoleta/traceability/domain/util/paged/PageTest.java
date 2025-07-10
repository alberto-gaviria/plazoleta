package com.plazoleta.traceability.domain.util.paged;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    @Test
    void testEmptyConstructorAndSetters() {
        Page<String> page = new Page<>();
        List<String> content = Arrays.asList("A", "B");

        page.setContent(content);
        page.setPageNumber(1);
        page.setPageSize(10);
        page.setTotalElements(20);
        page.setTotalPages(2);
        page.setHasNext(true);
        page.setHasPrevious(true);

        assertEquals(content, page.getContent());
        assertEquals(1, page.getPageNumber());
        assertEquals(10, page.getPageSize());
        assertEquals(20, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
        assertTrue(page.isHasNext());
        assertTrue(page.isHasPrevious());
    }

    @Test
    void testConstructorCalculatesFieldsCorrectly_SinglePage() {
        List<String> content = Arrays.asList("one", "two", "three");
        Page<String> page = new Page<>(content, 0, 3, 3);

        assertEquals(content, page.getContent());
        assertEquals(0, page.getPageNumber());
        assertEquals(3, page.getPageSize());
        assertEquals(3, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
        assertFalse(page.isHasNext());
        assertFalse(page.isHasPrevious());
    }

    @Test
    void testConstructorCalculatesFieldsCorrectly_MultiplePages() {
        List<Integer> content = Arrays.asList(1, 2, 3);
        Page<Integer> page = new Page<>(content, 1, 3, 9); // 3 pages total

        assertEquals(3, page.getPageSize());
        assertEquals(9, page.getTotalElements());
        assertEquals(3, page.getTotalPages());
        assertTrue(page.isHasNext());
        assertTrue(page.isHasPrevious());
    }

    @Test
    void testConstructorEdgeCase_LastPage() {
        List<String> content = Collections.singletonList("last");
        Page<String> page = new Page<>(content, 2, 3, 7); // totalPages = 3

        assertEquals(3, page.getTotalPages());
        assertFalse(page.isHasNext());
        assertTrue(page.isHasPrevious());
    }
}
