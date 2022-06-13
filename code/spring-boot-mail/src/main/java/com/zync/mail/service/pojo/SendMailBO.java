package com.zync.mail.service.pojo;

import java.util.List;

/**
 * 发送邮件信息BO
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/13 13:02
 */
public class SendMailBO {

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 接收人列表
     */
    private List<String> tos;

    /**
     * 抄送人列表
     */
    private List<String> ccs;

    /**
     * 密件抄送人列表
     */
    private List<String> bccs;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTos() {
        return tos;
    }

    public void setTos(List<String> tos) {
        this.tos = tos;
    }

    public List<String> getCcs() {
        return ccs;
    }

    public void setCcs(List<String> ccs) {
        this.ccs = ccs;
    }

    public List<String> getBccs() {
        return bccs;
    }

    public void setBccs(List<String> bccs) {
        this.bccs = bccs;
    }

    @Override
    public String toString() {
        return "SendMailBO{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", tos=" + tos +
                ", ccs=" + ccs +
                ", bccs=" + bccs +
                '}';
    }
}
