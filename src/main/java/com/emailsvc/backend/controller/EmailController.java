/**
 *
 */
package com.emailsvc.backend.controller;

import com.emailsvc.backend.model.NotificationRequest;
import com.emailsvc.backend.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String apply(@RequestBody NotificationRequest notificationRequest){
        String channel = notificationRequest.channel;
        String content = "empty";
//		if(channel.equalsIgnoreCase("Email")){
//			EmailNotification emailNotification = notificationRequest.notification.getEmailNotification();
//
//		}
        content = notificationService.applyTemplate(notificationRequest);
        System.out.println(notificationRequest.channel);
        System.out.println(notificationRequest.sender);
        System.out.println(notificationRequest.receiver);
        System.out.println(notificationRequest.emailNotification);
        return content;
    }

    @PostMapping("/email")
    public String sendEmail(){
        return null;
    }
}
