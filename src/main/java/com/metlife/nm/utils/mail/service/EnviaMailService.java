package com.metlife.nm.utils.mail.service;

import javax.mail.MessagingException;

import com.metlife.nm.utils.notificacion.vo.MailVO;
import com.metlife.nm.utils.notificacion.vo.MensajeVO;

public interface EnviaMailService {

    void enviaMails(MensajeVO vo, String destinatario) throws Exception, IllegalArgumentException;
    
    void sendMail(String fileName, MailVO mailVO);
    
    void enviaSingleMail(MailVO mailVO);
    
    void enviaMailConEstilo(MailVO mailVO) throws MessagingException, Exception;
}
