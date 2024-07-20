package com.raguzf.roommatch.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.raguzf.roommatch.model.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
/**
 * Service class responsible for sending emails
 * Uses JavaMailSender and SpringTemplateEngine
 * @author raguzf
 */
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    
    /**
     * Sends an email asynchronously using the specified parameters.
     *
     * @param to the recipient's email address
     * @param username the username of the recipient
     * @param emailTemplate the template to use for the email content
     * @param confirmationUrl the confirmation URL 
     * @param activationCode the activation code to activate the account
     * @param subject the subject of the email
     * @throws MessagingException if there is an error creating or sending the email
     */
    @Async
    public void sendEmail(
        String to,
        String username,
        EmailTemplateName emailTemplate,
        String confirmationUrl,
        String activationCode,
        String subject) throws MessagingException {
            String templateName;
            if(emailTemplate == null) {
                templateName = "confirm-email";
            } else {
                templateName = emailTemplate.name();
            }
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
                );
                Map<String, Object> properties = new HashMap<>();
                properties.put("username", username);
                properties.put("confirmationUrl", confirmationUrl);
                properties.put("activationCode", activationCode);

                Context context = new Context();
                context.setVariables(properties);

                helper.setFrom("contact@RoomMatch.com");
                helper.setTo(to);
                helper.setSubject(subject);

                String template = templateEngine.process(templateName, context);

                helper.setText(template, true);
                mailSender.send(mimeMessage);

        }
}
