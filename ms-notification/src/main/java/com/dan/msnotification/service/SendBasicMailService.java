package com.dan.msnotification.service;

import com.alibaba.fastjson2.JSON;
import com.dan.msnotification.adaptor.MailSenderAdaptor;
import com.dan.msnotification.model.entity.EmailTemplate;
import com.dan.msnotification.model.request.SendBasicMailRequest;
import com.dan.msnotification.model.response.SendMailResponse;
import com.dan.msnotification.repository.EmailTemplateRepository;
import com.dan.msnotification.utility.Constants;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CacheUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Service
@Slf4j
@RequiredArgsConstructor
public class SendBasicMailService implements BaseService<SendBasicMailRequest, SendMailResponse> {

    private final EmailTemplateRepository emailTemplateRepository;
    private final MailSenderAdaptor mailSenderAdaptor;
    private final CacheUtility cacheUtility;

    @Override
    public SendMailResponse execute(SendBasicMailRequest input) {
        try{
            String htmlTemplate = this.getEmailTemplate(emailTemplateRepository, input.getMessage(), Constants.EMAIL_WELCOME);
            mailSenderAdaptor.doSendBasicMail(input, htmlTemplate, false, null);
            return SendMailResponse.builder()
                    .emailResponse("Send Email Success!!!")
                    .successSent(true)
                    .build();
        }catch (Exception e){
            log.error("SendBasicMailService.error = {}",e.getMessage());
            return SendMailResponse.builder()
                    .emailResponse("Send Email Failed!!!")
                    .successSent(false)
                    .build();
        }
    }

    private String getEmailTemplate(EmailTemplateRepository emailTemplateRepository, String message, String type){
        AtomicReference<String> emailTemplateReference = new AtomicReference<>();
        String emailTemplateCache = cacheUtility.get(Constants.RDS_NOTIFICATION, Constants.RDS_NOTIFICATION_EMAIL_TEMPLATE);
        EmailTemplate rawMessageTemplate = EmailTemplate.builder().template(message).build();
        if(StringUtils.isEmpty(emailTemplateCache)){
            log.info("Email template cache is empty, find from db directly or set raw message");
            emailTemplateReference.set(emailTemplateRepository.findByType(type)
                    .orElse(rawMessageTemplate)
                    .getTemplate());
        }else{
            log.info("Email template cache is found, find from cache or set raw message");
            List<EmailTemplate> emailTemplateList = JSON.parseArray(emailTemplateCache, EmailTemplate.class);
            emailTemplateReference.set(emailTemplateList.stream().filter(template -> template.getType().equals(type))
                    .findAny()
                    .orElse(rawMessageTemplate)
                    .getTemplate());
        }
        return emailTemplateReference.get();
    }
}
