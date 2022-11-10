package com.dan.msaudit.service;

import com.dan.msaudit.repository.AuditRepository;
import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HousekeepAuditService implements BaseService<BaseRequest, ValidationResponse> {

    private final AuditRepository auditRepository;

    @Value("${config.housekeep.interval.audit}")
    private String intervalHousekeepAudit;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        auditRepository.doHouskeepAudit(intervalHousekeepAudit);
        return ValidationResponse.builder().result(true).build();
    }
    
}
