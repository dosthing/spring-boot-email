package com.naunxin.springbootemail.Serveice;

import com.naunxin.springbootemail.Service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Resource
    MailService mailService;

    @Resource
    TemplateEngine templateEngine;
    @Test
    public void sendSpimpleMailTest(){
        mailService.sendSimpleMail("15507738841@163.com","这是一封简单文本邮件","明天要加油");
    }
    @Test
    public void sendHtmlMailTest() throws MessagingException {
        String content="<html>\n"+
                "<body>\n"+
                    "<h3>hello 明天要加油哦！</h3>\n"+
                "</body>\n"+
                "</html>";
        mailService.sendHtmlMail("15507738841@163.com","这是一封html邮件",content);
    }
    @Test
    public void sendAttachmentMail(){
        String filePath="/springmail/spring-boot-email/favicon.ico";
        mailService.sendAttachmentsMail("15507738841@163.com","这是一封带附件的邮件","明天加油哦,带了附件图片哦",filePath);
    }
    @Test
    public void sendInlineResourceMailTest(){
        String imgPath="/springmail/spring-boot-email/头像.jpg";
        String rscId="touxiang";
        //String content="<html><body>带了一个头像的邮件，明天要加油哦:<img src=\'cid:"+rscId+"\'></img></body></html>";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        mailService.sendInlineResourceMail("15507738841@163.com","这是一封带图片的Html邮件",content,imgPath,rscId);
    }
    @Test
    public  void testTemplateMailTest(){
        Context context = new Context();
        context.setVariable("id","1");
        String emailContent=templateEngine.process("emailTemplate",context);
        mailService.sendHtmlMail("15507738841@163.com","这是一个模板邮件",emailContent);
    }
}
