package com.zync.mail.service.controller;

import com.zync.mail.service.MailService;
import com.zync.mail.service.pojo.SendMailBO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送邮件控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/13 13:00
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send/v1")
    public ResponseEntity<String> sendV1(@RequestBody SendMailBO sendMail) {
        mailService.sendSimpleMail(sendMail.getSubject(), sendMail.getContent(), sendMail.getTos(), sendMail.getCcs());
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/send/v2")
    public ResponseEntity<String> sendV2(@RequestBody SendMailBO sendMail) {
        mailService.sendHtmlMail(sendMail.getSubject(), sendMail.getContent(), sendMail.getTos(), sendMail.getCcs());
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }
}
