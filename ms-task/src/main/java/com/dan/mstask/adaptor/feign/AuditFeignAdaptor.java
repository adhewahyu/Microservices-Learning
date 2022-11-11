package com.dan.mstask.adaptor.feign;

import com.dan.mstask.model.request.audit.AddAuditRequest;
import com.dan.shared.model.response.RestResponse;
import com.dan.shared.utility.CommonConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${config.integration.ms-audit.url}", url = "${config.integration.ms-audit.url}", contextId = "msAuditContext")
public interface AuditFeignAdaptor {

    @PostMapping("/v1/add")
    ResponseEntity<RestResponse> addAudit(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddAuditRequest addAuditRequest);

}
