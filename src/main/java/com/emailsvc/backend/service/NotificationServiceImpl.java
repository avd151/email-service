package com.emailsvc.backend.service;

import com.emailsvc.backend.model.EmailNotification;
import com.emailsvc.backend.model.NotificationRequest;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;

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
        content = templateEngine.process("email1", context);
        return content;
    }
}
