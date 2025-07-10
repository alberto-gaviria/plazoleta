package com.plazoleta.traceability.adapters.driven.mongodb.adapter;

import com.plazoleta.traceability.adapters.driven.mongodb.document.OrderTraceabilityDocument;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityDatabaseException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityRetrieveException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilitySaveException;
import com.plazoleta.traceability.adapters.driven.mongodb.mapper.ITraceabilityDocumentMapper;
import com.plazoleta.traceability.adapters.driven.mongodb.repository.IOrderTraceabilityRepository;
import com.plazoleta.traceability.adapters.driven.mongodb.util.TraceabilityAdapterConstants;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.spi.ITraceabilityPersistencePort;
import com.plazoleta.traceability.domain.util.paged.Page;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class TraceabilityMongoAdapter implements ITraceabilityPersistencePort {

    private final IOrderTraceabilityRepository traceabilityRepository;
    private final ITraceabilityDocumentMapper traceabilityMapper;

    public TraceabilityMongoAdapter(IOrderTraceabilityRepository traceabilityRepository,
                                    ITraceabilityDocumentMapper traceabilityMapper) {
        this.traceabilityRepository = traceabilityRepository;
        this.traceabilityMapper = traceabilityMapper;
    }

    @Override
    public OrderTraceability saveTraceability(OrderTraceability traceability) {
        try {
            OrderTraceabilityDocument document = traceabilityMapper.toDocument(traceability);
            OrderTraceabilityDocument savedDocument = traceabilityRepository.save(document);
            return traceabilityMapper.toModel(savedDocument);

        } catch (DataAccessException e) {
            throw new TraceabilitySaveException(
                    TraceabilityAdapterConstants.ErrorMessages.TRACEABILITY_SAVE_ERROR, e);
        } catch (Exception e) {
            throw new TraceabilityDatabaseException(
                    TraceabilityAdapterConstants.ErrorMessages.TRACEABILITY_UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public Page<OrderTraceability> findByOrderIdAndClientId(Long orderId, Long clientId, int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize,
                                               Sort.by(TraceabilityAdapterConstants.TIMESTAMP_FIELD).ascending());

            var mongoPage = traceabilityRepository.findByOrderIdAndClientIdOrderByTimestampAsc(
                    orderId, clientId, pageable);

            List<OrderTraceability> content = traceabilityMapper.toModelList(mongoPage.getContent());

            return new Page<>(
                    content,
                    mongoPage.getNumber(),
                    mongoPage.getSize(),
                    mongoPage.getTotalElements()
            );

        } catch (DataAccessException e) {
            throw new TraceabilityRetrieveException(
                    TraceabilityAdapterConstants.ErrorMessages.TRACEABILITY_RETRIEVE_ERROR, e);
        } catch (Exception e) {
            throw new TraceabilityDatabaseException(
                    TraceabilityAdapterConstants.ErrorMessages.TRACEABILITY_UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public boolean existsOrderForClient(Long orderId, Long clientId) {
        try {
            return traceabilityRepository.existsByOrderIdAndClientId(orderId, clientId);
        } catch (DataAccessException e) {
            throw new TraceabilityRetrieveException(
                    TraceabilityAdapterConstants.ErrorMessages.TRACEABILITY_RETRIEVE_ERROR, e);
        } catch (Exception e) {
            throw new TraceabilityDatabaseException(
                    TraceabilityAdapterConstants.ErrorMessages.TRACEABILITY_UNEXPECTED_ERROR, e);
        }
    }
}