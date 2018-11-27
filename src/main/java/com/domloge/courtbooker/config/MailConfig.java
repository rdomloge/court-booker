package com.domloge.courtbooker.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

@Configuration
public class MailConfig {
	
	@Value("${smtp_username:rdomloge@gmail.com}")
	private String smtp_username;
	
	@Value("${smtp_password}")
	private String smtp_password;
	
	@Value("${smtp_host:smtp.gmail.com}")
	private String smtp_host;
	
	@Value("${smtp_port:587}")
	private int smtp_port;
	
	@Bean
    public SpringResourceTemplateResolver htmlTemplateResolver(){
        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
//        emailTemplateResolver.setPrefix("classpath:/templates/");
//        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return emailTemplateResolver;
    }
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(smtp_host);
	    mailSender.setPort(smtp_port);
	     
	    mailSender.setUsername(smtp_username);
	    
	    mailSender.setPassword(smtp_password);
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.from.email", smtp_username);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
}

