package com.zync.mail.service;

import java.util.List;

/**
 * 邮件服务Service
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/13 11:48
 */
public interface MailService {

    /**
     * 发生纯文本格式的邮件
     * @param subject 主题
     * @param content 内容
     * @param tos 接收人列表
     * @param ccs 抄送人列表
     * @return
     */
    boolean sendSimpleMail(String subject, String content, List<String> tos, List<String> ccs);

    /**
     * 发生HTML格式的邮件
     * @param subject 主题
     * @param content 内容
     * @param tos 接收人列表
     * @param ccs 抄送人列表
     * @return
     */
    boolean sendHtmlMail(String subject, String content, List<String> tos, List<String> ccs);
}
