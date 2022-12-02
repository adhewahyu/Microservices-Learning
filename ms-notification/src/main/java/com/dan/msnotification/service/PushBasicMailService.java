package com.dan.msnotification.service;

import com.dan.msnotification.model.request.SendBasicMailRequest;
import com.dan.msnotification.queue.publisher.PublishMailService;
import com.dan.shared.enums.RegexType;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.SharedUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PushBasicMailService implements BaseService<SendBasicMailRequest, ValidationResponse> {

    private final PublishMailService publishMailService;
    private final SharedUtility sharedUtility;

    @Override
    public ValidationResponse execute(SendBasicMailRequest input) {
        log.info("PushMailService - Called");
        this.doValidateRequest(input);
        publishMailService.execute(input);
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateRequest(SendBasicMailRequest input){
        if(StringUtils.isEmpty(input.getMailTo())){
            log.error("Mail recipient is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail recipient is required");
        }
        if(StringUtils.isEmpty(input.getSubject())){
            log.error("Mail subject is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail subject is required");
        }
        if(StringUtils.isEmpty(input.getMessage())){
            log.error("Mail message is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail message is required");
        }
        if(!sharedUtility.isValidRegex(input.getMailTo(), null, RegexType.EMAIL.getValue())){
            log.error("Invalid regex for mail recipient");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid format mail recipient");
        }
        sharedUtility.doCheckInputContainsHTML(input.getSubject());
        sharedUtility.doCheckInputContainsHTML(input.getMessage());
    }

}
