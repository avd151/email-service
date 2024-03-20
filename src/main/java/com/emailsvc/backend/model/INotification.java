package com.emailsvc.backend.model;

import lombok.Data;

import java.util.HashMap;

@Data
public abstract class INotification {
    public String channel;
    public String sender;
    public String receiver;

    public HashMap<String, String> body;

//    public EmailNotification getEmailNotification(){
//        return new EmailNotification(sender, receiver, body, channel);
//    }
}
