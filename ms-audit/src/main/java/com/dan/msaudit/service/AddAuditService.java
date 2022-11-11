package com.dan.msaudit.service;

import com.alibaba.fastjson2.JSON;
import com.dan.msaudit.model.entity.Audit;
import com.dan.msaudit.model.request.AddAuditRequest;
import com.dan.msaudit.repository.AuditRepository;
import com.dan.msaudit.utility.Constants;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.SharedUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddAuditService implements BaseService<AddAuditRequest, ValidationResponse> {

    private final AuditRepository auditRepository;
    private final SharedUtility sharedUtility;

    @Override
    public ValidationResponse execute(AddAuditRequest input) {
        log.info("AddAuditService - Called");
        log.info("Request = {}", JSON.toJSONString(input));
        doValidateRequest(input);
        Audit audit = new Audit();
        BeanUtils.copyProperties(input,audit);
        audit.setId(sharedUtility.getRandomUUID());
        audit.setCreatedDate(new Date(input.getCreatedDate()));
        auditRepository.save(audit);
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateRequest(AddAuditRequest input) {
        if(StringUtils.isEmpty(input.getModule())){
            log.error(Constants.ERR_MSG_MODULE_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_MODULE_REQUIRED);
        }
        if(StringUtils.isEmpty(input.getActivity())){
            log.error(Constants.ERR_MSG_ACTIVITY_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_ACTIVITY_REQUIRED);
        }
        if(ObjectUtils.isEmpty(input.getCreatedDate())){
            log.error(Constants.ERR_MSG_CREATED_DATE_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_CREATED_DATE_REQUIRED);
        }
        if(StringUtils.isEmpty(input.getCreatedBy())){
            log.error(Constants.ERR_MSG_CREATED_BY_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_CREATED_BY_REQUIRED);
        }
    }
}
