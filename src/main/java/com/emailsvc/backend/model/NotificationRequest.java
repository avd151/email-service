package com.emailsvc.backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NotificationRequest {

    public Long id;
    public String sender;
    public String receiver;
//    public INotification notification;
    public String channel;

    public EmailNotification emailNotification;
    public String lang;
}
