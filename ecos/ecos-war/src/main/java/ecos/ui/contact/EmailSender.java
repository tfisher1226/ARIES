package ecos.ui.contact;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EmailSender {
	
	private static Log log = LogFactory.getLog(EmailSender.class);
	
	private String subject;
	
	private String message;
	
	private String to;

	private String from;

	private String host;
	
	private String userName;
	
	private String password;
	
	private String smtpHost;
	
	private int smtpPort;
	
	private String smtpAuthentication = "true";

	
	public EmailSender() {
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpAuthentication() {
		return smtpAuthentication;
	}

	public void setSmtpAuthentication(String smtpAuthentication) {
		this.smtpAuthentication = smtpAuthentication;
	}

	
	public void sendMessage() throws MessagingException {
		// Get system properties
		Properties properties = System.getProperties();
		properties.setProperty("mail.user", userName);
		properties.setProperty("mail.password", password);
		 
		// Setup mail server
		properties.setProperty("mail.smtp.host", smtpHost);
		properties.setProperty("mail.smtp.auth", smtpAuthentication);

		// Get the default Session object.
		//Authenticator authenticator = new SMTPAuthentication();
		Session session = Session.getDefaultInstance(properties);
		session.setDebug(false);

		try {
			// Create a default MimeMessage object.
			MimeMessage mimeMessage = new MimeMessage(session);

			// Set From: header field of the header.
			mimeMessage.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			mimeMessage.setSubject(subject);

			// Send the actual HTML message, as big as you like
			mimeMessage.setContent(message, "text/html");

			// Send message
			Transport transport = session.getTransport("smtp");
            transport.connect(smtpHost, smtpPort, userName, password);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			log.info("Email message sent");
            transport.close();
			
		} catch (MessagingException e) {
			log.error("Error", e);
			throw e;
		}
	}


	public static void main(String [] args)	{
		EmailSender emailSender = new EmailSender();
		emailSender.setSubject("ECOS Information Request");
		StringBuffer buf = new StringBuffer();
		buf.append("<h1>ECOS Information Request</h1>");
		buf.append("<p/>Sample message text");
		emailSender.setMessage(buf.toString());
		emailSender.setTo("tfisher@kattare.com");
		emailSender.setFrom("info@ecos-net.com");
		emailSender.setHost("localhost");
		emailSender.setUserName("thfisher");
		emailSender.setPassword("!*usa.1");
		emailSender.setSmtpHost("mail.kattare.com");
		emailSender.setSmtpPort(2525);
		emailSender.setSmtpAuthentication("true");
	}
	
}
