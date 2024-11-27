package com.salvai.eldar.services.impl;

import com.salvai.eldar.services.EmailService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @NonNull
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String content){
        try {
            final var message = javaMailSender.createMimeMessage();
            final var helper = new MimeMessageHelper(message);
            helper.setFrom("salvaieldarchallenge@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);

            javaMailSender.send(message);
        } catch (Exception ex){
            log.error("It was not possible to send the email to %s".formatted(to));
        }
    }
}
