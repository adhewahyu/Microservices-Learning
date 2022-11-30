package com.dan.mstask.service.audit;

import com.dan.mstask.adaptor.rest.audit.AddAuditAdaptor;
import com.dan.mstask.model.request.audit.AddAuditRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddAuditAsyncService {

    private final AddAuditAdaptor addAuditAdaptor;

    @Async
    public void execute(AddAuditRequest addAuditRequest){
        log.info("AddAuditAsyncService - Called");
        addAuditAdaptor.execute(addAuditRequest);
    }

}
