package com.naunxin.springbootemail.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String from;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单文本的邮件方法
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to,String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

    /**
     * 发送HTML邮件的方法
     * @param to
     * @param subjecr
     * @param content
     */
    public void sendHtmlMail(String to ,String subjecr,String content){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subjecr);
            helper.setText(content,true);
            mailSender.send(message);
            logger.info("发送静态邮件成功");
        } catch (MessagingException e) {
            logger.error("发送静态邮件失败：",e);
        }

    }

    /**
     * 发送带附件的邮件方法
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content,String filePath){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            //helper.addTo("1213334346@qq.com");
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file =new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName,file);
            helper.addAttachment(fileName,file);//可以带多个附件
            mailSender.send(message);
            logger.info("发送静态邮件成功");
        } catch (MessagingException e) {
            logger.error("发送静态邮件失败：",e);
        }
    }

    /**
     * 发送带图片的HTML邮件方法
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendInlineResourceMail(String to,String subject,String content,
                                       String rscPath,String rscId) {
        //logger.info("发送静态邮件开始：{},{},{},{},{}",to,subject,content,rscPath,rscId);
        String sendToarray[];
        sendToarray = new String[3];
        sendToarray[0]=to;
        sendToarray[1]=from;
        sendToarray[2]="1213334346@qq.com";
        MimeMessage message=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(from);//单个接收人
            //helper.setTo(sendToarray);//多个接收人
            helper.addTo(to);//多个接收人的其他方法
            helper.setSubject(subject);
            helper.setText(content,true);//是能true才能看到HTML页面效果

            FileSystemResource res=new FileSystemResource(new File(rscPath));
            helper.addInline(rscId,res);
            mailSender.send(message);
            logger.info("发送静态邮件成功");
       } catch (MessagingException e) {
           logger.error("发送静态邮件失败：",e);
       }
    }
}
