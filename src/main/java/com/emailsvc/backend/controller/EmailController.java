/**
 *
 */
package com.emailsvc.backend.controller;

import com.emailsvc.backend.model.NotificationRequest;
import com.emailsvc.backend.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

//import com.empmgmt.backend.model.User;
//import com.empmgmt.backend.repository.UserRepository;

/**
 *
 */
@RestController
public class EmailController {

//	@Autowired
//	private UserRepository userRepository;

//	@PostMapping("/user")
//	User addUser(@RequestBody User newUser) {
//		return userRepository.save(newUser);
//	}

    @Autowired
    NotificationServiceImpl notificationService;

    @GetMapping("/hello")
    public String healthCheck(){
        return "Hello world";
    }

    @PostMapping("/apply")
    public String apply(@RequestBody NotificationRequest notificationRequest) throws MessagingException {
        String channel = notificationRequest.channel;
//		if(channel.equalsIgnoreCase("Email")){
//			EmailNotification emailNotification = notificationRequest.notification.getEmailNotification();
//
//		}
        String sender = notificationRequest.sender.trim();
        String receiver = notificationRequest.receiver.trim();
        String subject = notificationRequest.emailNotification.body.getOrDefault("subject",
                "Demo subject");
        String content = notificationService.applyTemplate(notificationRequest);
        System.out.println(notificationRequest.channel);
        System.out.println(sender);
        System.out.println(receiver);
        System.out.println(subject);
        System.out.println(content);

        notificationService.sendEmailDynamic(sender, receiver
                , subject, content);
        return content;
    }

    @PostMapping("/email")
//    @EventListener(ApplicationReadyEvent.class)
    public String sendEmail(@RequestBody Map<String, List<String>> body){
        List<String> allToEmails = body.get("emails");
        String subject = body.get("subject").get(0);
        String emailBody = body.get("body").get(0);
//        String subject = "Looking for opportunities in ";
        for(String toEmail: allToEmails){
            notificationService.sendEmail(toEmail, subject,
                    emailBody);
        }
        return "OK";
    }
}
