package com.ashokit.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendMail(String to, String subject, String body) {
		boolean isSend = false;
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(body, true);;
			mailSender.send(message);
			isSend=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isSend;
	}
}
