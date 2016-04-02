package com.qianyan.lab.util.send;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;
import com.qianyan.lab.util.properties.PropertiesUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 16-2-3
 * Time: 上午8:57.
 */
public class MailSendUtil {

    private static final MailSendUtil mail = new MailSendUtil();
    private static final String propName = "lab-mail";

    //协议
    private static String smtp;
    //协议地址
    private static String smtpHost;
    //服务邮箱
    private static String serviceMail;
    //服务邮箱用户名
    private static String serviceMailUser;
    //服务邮箱密码
    private static String serviceMailPS;
    //内容文本类型
    private static String contentType;

    //单利
    private MailSendUtil() {
        smtp = PropertiesUtil.readString(propName, "com.qianyan.lab.util.send.smtp");
        smtpHost = PropertiesUtil.readString(propName, "com.qianyan.lab.util.send.smtpHost");
        serviceMail = PropertiesUtil.readString(propName, "com.qianyan.lab.util.send.serviceMail");
        serviceMailUser = PropertiesUtil.readString(propName, "com.qianyan.lab.util.send.serviceMailUser");
        serviceMailPS = PropertiesUtil.readString(propName, "com.qianyan.lab.util.send.serviceMailPS");
        //serviceMailPS = .readString(propName,"com.qianyan.lab.util.send.smtp");
        contentType = PropertiesUtil.readString(propName, "com.qianyan.lab.util.send.contentType");
    }

    public static MailSendUtil getInstance() {
        return mail;
    }

    private static Session mailToSession() {
        return Session.getInstance(mailToProperties());
    }

    private static Properties mailToProperties() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", smtp);
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.auth", "true");
        return properties;
    }

    public void sendMail(String toMail, String mailTitle, String mailContent) {
        Session session = mailToSession();
        sendMail(session, toMail, mailTitle, mailContent);
    }

    /**
     * 一次发送多个邮件
     *
     * @param toMails      目标邮箱地址集合
     * @param mailTitle    标题头
     * @param mailContents 邮件内容集合
     */
    public void sendMail(List<String> toMails, String mailTitle, List<String> mailContents) {
        if (toMails != null && mailContents != null) {
            Session session = mailToSession();
            int mailCount = toMails.size();
            int mailContentCount = mailContents.size();
            for (int i = 0; i < mailCount; i++) {
                String mailContent = "";
                if (mailContentCount >= i) {
                    mailContent = mailContents.get(i);
                }
                sendMail(session, toMails.get(i), mailTitle, mailContent);
            }
        }
    }

    private void sendMail(Session session, String toMail, String mailTitle, String mailContent) {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(serviceMail));
            //用户邮箱地址
            message.setRecipients(Message.RecipientType.TO, toMail);
            message.setSubject(mailTitle);
            //邮件内容
            message.setContent(mailContent, contentType);
            Transport transport = session.getTransport();
            //账号用户名，密码
            transport.connect(serviceMailUser, serviceMailPS);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200012);
        }
    }
}
