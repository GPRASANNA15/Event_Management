package com.example.Event_Management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendResultEmail(String toEmail) {
        try {
            String subject = "Event Submission";
            String body = "Congratulations, You have successfully registered for the event.We expect your arrival to the event";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("prasannag1505@gmail.com");

            mailSender.send(message);
            System.out.println("Result email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

}
