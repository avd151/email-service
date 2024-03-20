package com.emailsvc.backend.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class EmailNotification{
    public String channel;
    public String sender;
    public String receiver;

    public HashMap<String, String> body;
//    public EmailNotification(String sender, String receiver, HashMap<String, String> body, String channel) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.body = body;
//        this.channel = channel;
//    }
}
