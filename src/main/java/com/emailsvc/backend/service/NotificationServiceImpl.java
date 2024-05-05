package com.emailsvc.backend.service;

import com.emailsvc.backend.model.EmailNotification;
import com.emailsvc.backend.model.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

@Service
public class NotificationServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public NotificationServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("<your email>");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.printf("Mail Sent to %s\n", toEmail);
    }

    public void sendEmailDynamic(String fromEmail, String toEmail,
                          String subject,
                          String body
    ) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(toEmail);
//        message.setText(body);
//        message.setSubject(subject);
//        mailSender.send(message);
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
        mimeMailMessage.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setText(body, true);
        helper.setSubject(subject);
        mailSender.send(mimeMailMessage);
        System.out.printf("Mail Sent to %s\n", toEmail);
    }

    public String applyTemplate(NotificationRequest notificationRequest){
        if(notificationRequest == null)
            return "notifreq empty";
        String content = null;
        String channel = notificationRequest.channel;
        if(channel == null || channel.isEmpty())
            return "no channel";
        String sender = notificationRequest.sender;
        String receiver = notificationRequest.receiver;
        EmailNotification emailNotification = notificationRequest.emailNotification;
//        if(channel.equalsIgnoreCase("Email")){
////            emailNotification = notificationRequest.notification.getEmailNotification();
//        }
        if(emailNotification == null)
            return "emailnotif empty";
        HashMap<String, String> contextVarsMp = emailNotification.body;
        if(contextVarsMp == null)
            return "body empty";
        String lang = notificationRequest.lang;
        if(lang.isEmpty() || lang == null)
            return "language empty";
        Locale locale;
//        if(lang.equalsIgnoreCase("english"))
//            locale = (Locale.ENGLISH);
//        else
//            locale = (Locale.FRENCH);
        locale = Locale.forLanguageTag(lang);
        Context context = new Context(locale);
        for(String key : contextVarsMp.keySet()){
            context.setVariable(key, contextVarsMp.get(key));
        }
        //internationalization
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n");
        messageSource.setDefaultEncoding("UTF-8");
        System.out.println(messageSource.getMessage("greeting", null, locale));
//        SpringApplication.run(Javai18nspringbootApplication.class, args);
        String templateFile = "email1.html";
        content = templateEngine.process(templateFile, context);
        return content;
    }

//    public String sendEmail(String sender, String subject, String content){
//        String host = "smtp.gmail.com";
//        Properties properties = System.getProperties();
//        System.out.println(properties);
//
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", 465);
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//
//        //get session object
//
//        return null;
//    }

}
