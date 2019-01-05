package com.yd.spring.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * EmailServiceImp.java 功能：（一句话描述类的功能）
 * <p>
 * 修订历史：(格式为："@since 日期 作者 修订内容简介")
 *
 * @author Administrator, 1406721322@qq.com
 * @version 1.0
 * @since 2017年7月17日 Administrator 初版
 */
@Service("service.emailService")
public class EmailServiceImp implements EmailService {
    @Resource(name = "java163MailSender")
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toAdress, String subject, String htmlText) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper msgHelper = new MimeMessageHelper(msg, true, "UTF-8");
            msgHelper.setFrom("zyy6721322@163.com");

            msgHelper.setTo(toAdress);
            msgHelper.setSubject(subject);
            msgHelper.setText(htmlText, true);

            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
