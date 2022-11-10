package com.dan.msaudit.service;

import com.dan.msaudit.model.entity.Audit;
import com.dan.msaudit.model.response.AuditResponse;
import com.dan.msaudit.repository.AuditRepository;
import com.dan.shared.model.request.SpecificationRequest;
import com.dan.shared.model.response.PageResponse;
import com.dan.shared.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchAuditService implements BaseService<SpecificationRequest, PageResponse> {

    private final AuditRepository auditRepository;

    @Override
    public PageResponse execute(SpecificationRequest input) {
        Page<Audit> pageAudit = this.auditRepository.findAll(input.getSpecification(), input.getPageable());
        return PageResponse.builder().page(pageAudit.isEmpty() ? null : pageAudit.map(data -> 
            AuditResponse.builder()
                .id(data.getId())
                .createdBy(data.getCreatedBy())
                .module(data.getModule())
                .activity(data.getActivity())
                .createdDate(data.getCreatedDate().getTime())
            .build()
        )).build();
    }
    
}
