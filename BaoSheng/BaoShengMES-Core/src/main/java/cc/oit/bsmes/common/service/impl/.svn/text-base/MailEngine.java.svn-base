package cc.oit.bsmes.common.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

 

/**
 * 邮件发送程序
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-3-4 下午5:57:09
 * @since
 * @version
 */
@Service
public class MailEngine {
	protected final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
	private MailSender mailSender;    
	@Resource
	DatabasePropertyLoaderStrategy databasePropertyLoaderStrategy;    
    private static Object synobject=new Object();
    private final static String MAIL_DEFAULT_FROM_KEY="mail.default.from";
    private final static String MAIL_SMTP_AUTH_KEY="mail.smtp.auth";    
    private final static String MAIL_HOST_KEY="mail.host";
    private final static String MAIL_USERNAME_KEY="mail.username";
    private final static String MAIL_PASSWORD_KEY="mail.password"; 
    private final static String defaultEncoding="UTF-8";
    private static Map<String, String> propmap =new HashMap<String, String>(); 
    
    private String defaultFrom;
	 
	/**
	 * Send a simple message with pre-populated values.
	 * 
	 * @param msg
	 */
	public void send(SimpleMailMessage msg,String from) {
		try {
			from=init(from);
			msg.setFrom(from);
			mailSender.send(msg);
		} catch (MailException ex) {
			// log it and go on
			log.error(ex.getMessage());
		}
	}

	/**
	 * @param emailAddresses
	 * @param subject
	 * @param bodyText
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendMessage(String[] emailAddresses, String subject,
			String bodyText,String from) throws MessagingException,
			UnsupportedEncodingException {
		from=init(from);
		MimeMessage message = mailSender
				.createMimeMessage();
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(emailAddresses);
		helper.setSubject(subject);
		helper.setText(bodyText,true);
		  mailSender.send(message);
	}

	/**
	 * Convenience method for sending messages with attachments.
	 * 
	 * @param emailAddresses
	 * @param resource
	 * @param bodyText
	 * @param subject
	 * @param attachmentName
	 * @throws MessagingException
	 * @author zdp
	 */
	public void sendMessageAttachment(String[] emailAddresses,
			ClassPathResource resource, String bodyText, String subject,
			String attachmentName,String from) throws MessagingException {
		from=init(from);
		MimeMessage message =  mailSender
				.createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(emailAddresses);
		helper.setText(bodyText);
		helper.setSubject(subject);

		helper.addAttachment(attachmentName, resource);

		mailSender.send(message);
	}

	private String init(String from) {

		synchronized (synobject) {
			if (propmap == null ||propmap.isEmpty()) {
				propmap = databasePropertyLoaderStrategy.loadProperties();
				defaultFrom = propmap.get(MAIL_DEFAULT_FROM_KEY);
				mailSender.setHost(propmap.get(MAIL_HOST_KEY));
				mailSender.setDefaultEncoding(defaultEncoding);
				mailSender.setUsername(propmap.get(MAIL_USERNAME_KEY));
				mailSender.setPassword(propmap.get(MAIL_PASSWORD_KEY));
				Properties javaMailProperties = new Properties();
				javaMailProperties.put(MAIL_SMTP_AUTH_KEY,propmap.get(MAIL_SMTP_AUTH_KEY));
				mailSender.setJavaMailProperties(javaMailProperties);
			}
		}
		if (!StringUtils.isEmpty(from)) {
			return from;
		} else {
			return defaultFrom;
		}

	}
	
	public static void main(String argp[])
	{
		MailSender mailSender=new MailSender();
		MimeMessage message =  mailSender
				.createMimeMessage();
		mailSender.setDefaultEncoding(defaultEncoding);
		mailSender.setHost("smtp.163.com");
		mailSender.setPassword("bsdlmestest");
		mailSender.setUsername("bsdlmes@163.com");
		Properties javaMailProperties=new Properties();
		javaMailProperties.put(MAIL_SMTP_AUTH_KEY,"true");
		mailSender.setJavaMailProperties(javaMailProperties);

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);			
			helper.setFrom("bsdlmes@163.com");
			helper.setTo("bsdlmes@163.com");
			helper.setText("test");
			helper.setSubject("test"); 

			mailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
