package com.mvalentin.noticacao.business.service;

import com.mvalentin.noticacao.business.dto.TarefasDto;
import com.mvalentin.noticacao.infrastructure.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    /**
     * Injeta o endereço de e-mail do remetente configurado externamente (ex: application.yml).
     * Permite alterar o e-mail de envio sem precisar recompilar a aplicação,
     * facilitando a transição entre ambientes de teste e produção.
     */
    @Value("${envio.email.remetente}")
    public String remetente;
    /**
     * Injeta o nome amigável que aparecerá na caixa de entrada do usuário (ex: "Agendador de Tarefas").
     */
    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;

    public void enviaEmail(TarefasDto tarefasDto){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"UTF-8");
                    // MimeMessageHelper(message, true, StandardCharsets.UTF_8);

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMessageHelper.setTo(InternetAddress.parse(tarefasDto.getEmailUsuario()));
            mimeMessageHelper.setSubject("Notificação de Tarefa");

            Context context = new Context();
            context.setVariable("nomeTarefa",tarefasDto.getNomeTarefa());
            context.setVariable("dataEvento",tarefasDto.getDataEvento());
            context.setVariable("descricao", tarefasDto.getDescricao());

            String template = templateEngine.process("notificacao", context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(message);

        } catch (EmailException | MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar e-mail", e.getCause());
        }
    }
}
