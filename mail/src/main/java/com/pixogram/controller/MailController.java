package com.pixogram.controller;

import com.pixogram.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Component
@RestController
public class MailController {
    @Autowired
    private MailService mailService;

    @PutMapping("/sendSimpleMail")
    public ResponseEntity<Void> sendSimpleMail(@NotNull @RequestParam  String to, @RequestParam(required = false)  String subject, @NotNull @RequestParam  String text) {
        mailService.sendSimpleMessage(to, subject, text);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/sendConfirmationSignup")
    public ResponseEntity<Void> sendConfirmationSignup(@NotNull @RequestParam  String username, @RequestParam  String confirmationCode) {
        mailService.sendConfirmationSignup(username, confirmationCode);
        return ResponseEntity.noContent().build();
    }
}
