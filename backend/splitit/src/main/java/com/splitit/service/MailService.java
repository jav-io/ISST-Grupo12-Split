package com.splitit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public void enviarRecordatorio(String destinatario, String monto, String acreedor, String grupo) {
        String asunto = "Recordatorio de deuda en Split.it";
        String cuerpo = "Te recuerdo que le debes " + monto + " € a " + acreedor + " en tu grupo " + grupo + ".";

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mensaje.setFrom(env.getProperty("spring.mail.username")); // usa el email configurado

        mailSender.send(mensaje);
    }
}

