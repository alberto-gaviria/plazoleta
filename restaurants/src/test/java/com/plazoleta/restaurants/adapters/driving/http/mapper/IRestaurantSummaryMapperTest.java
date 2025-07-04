package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantSummaryResponse;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IRestaurantSummaryMapperTest {

    private IRestaurantSummaryMapper mapper;
    private IRestaurantSummaryMapper spyMapper;

    @BeforeEach
    void setUp() {
        // Instancia real generada por MapStruct
        mapper = Mappers.getMapper(IRestaurantSummaryMapper.class);
        // Spy para verificar llamadas internas
        spyMapper = Mockito.spy(mapper);
    }

    @Test
    @DisplayName("toResponse: mapea correctamente nombre y urlLogo")
    void testToResponseMapping() {
        // Mock del dominio con getters
        RestaurantSummary domain = Mockito.mock(RestaurantSummary.class);
        Mockito.when(domain.getNombre()).thenReturn("La Pizzería");
        Mockito.when(domain.getUrlLogo()).thenReturn("pizzeria.png");

        RestaurantSummaryResponse dto = mapper.toResponse(domain);

        assertEquals("La Pizzería", dto.getNombre());
        assertEquals("pizzeria.png", dto.getUrlLogo());
    }

    @Test
    @DisplayName("toResponseList: mapea lista no vacía y lista vacía")
    void testToResponseList() {
        // Primer elemento
        RestaurantSummary r1 = Mockito.mock(RestaurantSummary.class);
        Mockito.when(r1.getNombre()).thenReturn("A");
        Mockito.when(r1.getUrlLogo()).thenReturn("a.png");
        // Segundo elemento
        RestaurantSummary r2 = Mockito.mock(RestaurantSummary.class);
        Mockito.when(r2.getNombre()).thenReturn("B");
        Mockito.when(r2.getUrlLogo()).thenReturn("b.png");

        List<RestaurantSummaryResponse> mapped = mapper.toResponseList(Arrays.asList(r1, r2));
        assertEquals(2, mapped.size());
        assertEquals("A", mapped.get(0).getNombre());
        assertEquals("a.png", mapped.get(0).getUrlLogo());
        assertEquals("B", mapped.get(1).getNombre());
        assertEquals("b.png", mapped.get(1).getUrlLogo());

        // Lista vacía → debe devolver lista vacía
        List<RestaurantSummaryResponse> empty = mapper.toResponseList(Collections.emptyList());
        assertTrue(empty.isEmpty());
    }

    @Test
    @DisplayName("toPageResponse: delega en toResponseList y propaga metadatos")
    void testToPageResponse() {
        // Creamos un Page real del dominio
        List<RestaurantSummary> domainContent = Collections.singletonList(Mockito.mock(RestaurantSummary.class));
        Page<RestaurantSummary> page = new Page<>(domainContent, 1, 10, 15);

        // Stub al spy para controlar el resultado de toResponseList
        List<RestaurantSummaryResponse> fakeDtoList =
                Collections.singletonList(new RestaurantSummaryResponse("X", "Y"));
        Mockito.doReturn(fakeDtoList)
                .when(spyMapper).toResponseList(domainContent);

        // Llamamos al default method
        PageResponse<RestaurantSummaryResponse> resp = spyMapper.toPageResponse(page);

        // Verificamos delegación
        Mockito.verify(spyMapper, Mockito.times(1)).toResponseList(domainContent);

        // Comprobamos que el contenido y metadatos se propaguen
        assertSame(fakeDtoList, resp.getContent(), "Debe usar la lista devuelta por toResponseList");
        assertEquals(1, resp.getPageNumber());
        assertEquals(10, resp.getPageSize());
        assertEquals(15, resp.getTotalElements());
        // totalPages = ceil(15/10) = 2
        assertEquals(2, resp.getTotalPages());
        // Para pageNumber=1 con totalPages=2 → hasNext=false, hasPrevious=true
        assertFalse(resp.isHasNext(), "hasNext debe ser false cuando pageNumber == totalPages-1");
        assertTrue(resp.isHasPrevious(), "hasPrevious debe ser true cuando pageNumber > 0");
    }

}
