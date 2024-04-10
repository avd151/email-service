package com.emailsvc.backend.service;

import com.emailsvc.backend.model.EmailNotification;
import com.emailsvc.backend.model.NotificationRequest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

@Service
public class NotificationServiceImpl {

    private final TemplateEngine templateEngine;

    public NotificationServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
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
        Context context = new Context();
        for(String key : contextVarsMp.keySet()){
            context.setVariable(key, contextVarsMp.get(key));
        }
        //internationalization
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n");
        messageSource.setDefaultEncoding("UTF-8");
        System.out.println(messageSource.getMessage("greeting", null, Locale.ENGLISH));
//        SpringApplication.run(Javai18nspringbootApplication.class, args);
        String templateFile = "i18n.html";
        content = templateEngine.process(templateFile, context);
        return content;
    }

    public String sendEmail(String sender, String subject, String content){
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        System.out.println(properties);

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //get session object

        return null;
    }

}
