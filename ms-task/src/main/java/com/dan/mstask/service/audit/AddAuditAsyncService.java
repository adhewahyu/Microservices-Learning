package com.dan.mstask.service.audit;

import com.dan.mstask.adaptor.feign.AuditFeignAdaptor;
import com.dan.mstask.model.request.audit.AddAuditRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddAuditAsyncService {

    private final AuditFeignAdaptor auditFeignAdaptor;

    @Value("${config.integration.ms-audit.key}")
    private String msAuditInternalApiKey;

    @Async
    public void execute(AddAuditRequest addAuditRequest){
        log.info("AddAuditAsyncService - Called");
        auditFeignAdaptor.addAudit(msAuditInternalApiKey, addAuditRequest);
    }

}
