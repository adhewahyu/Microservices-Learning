package com.dan.msnotification.service;

import com.alibaba.fastjson2.JSON;
import com.dan.msnotification.model.entity.EmailTemplate;
import com.dan.msnotification.repository.EmailTemplateRepository;
import com.dan.msnotification.utility.Constants;
import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CacheUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshMailTemplateService implements BaseService<BaseRequest, ValidationResponse> {

    private final EmailTemplateRepository emailTemplateRepository;
    private final CacheUtility cacheUtility;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findActiveEmailTemplate();
        cacheUtility.set(Constants.RDS_NOTIFICATION, Constants.RDS_NOTIFICATION_EMAIL_TEMPLATE, JSON.toJSONString(emailTemplateList) , null);
        return ValidationResponse.builder().result(true).build();
    }

}
