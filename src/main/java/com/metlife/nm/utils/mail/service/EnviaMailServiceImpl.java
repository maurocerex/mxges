package com.metlife.nm.utils.mail.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.utils.notificacion.vo.MailVO;
import com.metlife.nm.utils.notificacion.vo.MensajeVO;

@Service(value = BeanNames.EnviaMailService)
public class EnviaMailServiceImpl implements EnviaMailService {

    private static final Logger log = Logger.getLogger(EnviaMailServiceImpl.class);

    @Resource(name = BeanNames.JavaMailSender)
    //@Resource(name = "mailSender")
    private JavaMailSender javaMailSender;

    public void enviaMails(MensajeVO vo, String destinatario) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("enviaMails activado");
            log.debug("Asunto " + vo.getAsunto());
            log.debug("Cuerpo " + vo.getCuerpo());
            log.debug("destinatario " + destinatario);

        }
        MailVO obj = null;

        obj = new MailVO();
        obj.setEmailSubject(vo.getAsunto());
        obj.setEmailContent(vo.getCuerpo());
        obj.setEmailTo(destinatario);
        enviaSingleMail(obj);

    }

    public void sendMail(String fileName, MailVO mailVO) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(mailVO.getEmailTo());
            helper.setSubject("Reporte de Calidad de Datos");
            helper.setText("Reporte de Calidad de Datos");

            FileSystemResource file = new FileSystemResource(fileName);
            helper.addAttachment(file.getFilename(), file);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new MailParseException(e);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }

    }

    public void enviaSingleMail(MailVO mailVO) {
        if (log.isDebugEnabled()) {
            log.debug("QWERTY " + (javaMailSender == null));

        }
        MimeMessage message = javaMailSender.createMimeMessage();
        
        try {
        	
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(mailVO.getEmailTo());
            helper.setSubject(mailVO.getEmailSubject());
            helper.setText(mailVO.getEmailContent());

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new MailParseException(e);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        
        
    }
    
    public void enviaMailConEstilo(MailVO mailVO) throws MessagingException, Exception{
    	MimeMessage message = javaMailSender.createMimeMessage();
    	message.setSubject(mailVO.getEmailSubject());
    	message.setFrom(new InternetAddress("metlife.individualp@metlife.com.mx"));
    	message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailVO.getEmailTo()));
    	
    	Multipart mp = new MimeMultipart();
    	
    	MimeBodyPart messageBodyPart = new MimeBodyPart();
    	
    	messageBodyPart.setContent(mailVO.getEmailContent(), "text/html");
    	
    	MimeBodyPart attachPart = new MimeBodyPart();
    	ResourceLoader testResourceLoader = new FileSystemResourceLoader();
		org.springframework.core.io.Resource testResource = testResourceLoader.getResource("classpath:com/metlife/nm/utils/notificacion/service/metlife.png");
    	DataSource source = new ByteArrayDataSource(testResource.getInputStream(), "image/png");
    	attachPart.setDataHandler(new DataHandler(source));
    	attachPart.setFileName(testResource.getFilename());
    	
    	mp.addBodyPart(messageBodyPart);
    	mp.addBodyPart(attachPart);
    	
    	message.setContent(mp);
    	
    	javaMailSender.send(message);

    }

    
}
