package com.plazoleta.traceability.adapters.driven.mongodb.repository;

import com.plazoleta.traceability.adapters.driven.mongodb.document.OrderTraceabilityDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IOrderTraceabilityRepository extends MongoRepository<OrderTraceabilityDocument, String> {

    @Query("{ 'order_id': ?0, 'client_id': ?1 }")
    Page<OrderTraceabilityDocument> findByOrderIdAndClientIdOrderByTimestampAsc(
            Long orderId, Long clientId, Pageable pageable);

    @Query(value = "{ 'order_id': ?0, 'client_id': ?1 }", exists = true)
    boolean existsByOrderIdAndClientId(Long orderId, Long clientId);
}