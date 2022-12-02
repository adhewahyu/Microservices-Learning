package com.dan.msnotification.adaptor;

import com.dan.msnotification.model.request.SendBasicMailRequest;
import com.dan.msnotification.utility.EmailProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class MailSenderAdaptor {

    @Value("${config.mail.sender}")
    private String displayAddress;

    private final EmailProperties emailProperties;

    @Async
    public void doSendBasicMail(SendBasicMailRequest request, String body, Boolean useImage, Map<String,String> mapInLineImages) throws MessagingException {
        log.info("Prepare to Send Email");
        MimeMessage mimeMessage = prepareEmail(request);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html");
        Multipart multipart = new MimeMultipart();

        if(!useImage){
            multipart.addBodyPart(mimeBodyPart);
            mimeMessage.setContent(multipart);
        }else{
            multipart.addBodyPart(mimeBodyPart);
            if(ObjectUtils.isNotEmpty(mapInLineImages) || mapInLineImages.size() > 0){
                Set<String> setImageId = mapInLineImages.keySet();
                for(String contentId : setImageId){
                    MimeBodyPart imagePart = new MimeBodyPart();
                    imagePart.setHeader("Content-ID", "<"+contentId+">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                    String imageFilePath = mapInLineImages.get(contentId);
                    try{
                        URLDataSource source = new URLDataSource(this.getClass().getResource(imageFilePath));
                        imagePart.setDataHandler(new DataHandler(source));
                    }catch (MessagingException me){
                        log.error(me.getMessage());
                    }
                    multipart.addBodyPart(imagePart);
                }
            }
        }
        log.info("Sending Email...");
        Transport.send(mimeMessage);


    }

    private MimeMessage prepareEmail(SendBasicMailRequest request) throws MessagingException {
        Properties emailProps = emailProperties.setEmailProperties();
        Authenticator auth = getAuthenticaton(emailProps.get("mail.smtp.username").toString(),
                emailProps.get("mail.smtp.password").toString());
        Session session = Session.getInstance(emailProps, auth);
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(displayAddress));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(request.getMailTo()));
        mimeMessage.setSubject(request.getSubject());
        return mimeMessage;
    }

    private Authenticator getAuthenticaton(String username, String password){
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
    }

}
