package com.dan.msaudit.service;

import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HousekeepAuditService implements BaseService<BaseRequest, ValidationResponse> {

    @Value("${config.housekeep.interval.audit}")
    private String intervalHousekeepAudit;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        log.info("HousekeepAuditService - Called");
        jdbcTemplate.execute("DELETE FROM AUDITS WHERE CREATED_DATE <  (CURRENT_DATE - interval '"+intervalHousekeepAudit+"')");
        return ValidationResponse.builder().result(true).build();
    }
    
}
