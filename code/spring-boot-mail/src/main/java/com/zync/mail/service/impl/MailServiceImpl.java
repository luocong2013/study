package com.zync.mail.service.impl;

import com.zync.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

/**
 * 邮件服务Service实现类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/13 11:56
 */
@Service
public class MailServiceImpl implements MailService {

    private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendSimpleMail(String subject, String content, List<String> tos, List<String> ccs) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(tos.toArray(new String[0]));
        message.setCc(ccs.toArray(new String[0]));
        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
        logger.info(">>>>> 邮件发送成功 <<<<<");
        return true;
    }

    @Override
    public boolean sendHtmlMail(String subject, String content, List<String> tos, List<String> ccs) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(tos.toArray(new String[0]));
            helper.setCc(ccs.toArray(new String[0]));
            helper.setSentDate(new Date());
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(mimeMessage);
            logger.info(">>>>> 邮件发送成功 <<<<<");
            return true;
        } catch (MessagingException e) {
            logger.error(">>>>> 邮件发送失败 <<<<<", e);
            return false;
        }
    }
}
