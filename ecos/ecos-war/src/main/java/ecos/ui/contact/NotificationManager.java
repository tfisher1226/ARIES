package ecos.ui.contact;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.Messages;
import org.aries.ui.dialog.MessageDialog;
import org.aries.ui.wizard.AbstractManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


@Startup
@AutoCreate
@Name("notificationManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class NotificationManager extends AbstractManager {
	
	private String firstName; 

	private String lastName; 

	private String organization; 

	private String emailAddress; 

	private String messageText; 

	@In(required = true)
	private Messages messages;

	@In(required = true)
	private MessageDialog messageDialog;
	
	
	public NotificationManager() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}


	public void sendMessage() {
		if (StringUtils.isEmpty(firstName))
			messages.error("contact", "First name is required");
		if (StringUtils.isEmpty(lastName))
			messages.error("contact", "Last name is required");
		if (StringUtils.isEmpty(emailAddress))
			messages.error("contact", "E-mail address is required");
		if (messages.isMessagesExist("contact"))
			return;
		
		EmailSender emailSender = new EmailSender();
		emailSender.setSubject("ECOS Information Request");
		emailSender.setTo("tfisher@kattare.com");
		emailSender.setFrom("info@ecos-net.com");
		//emailSender.setHost("localhost");
		emailSender.setUserName("thfisher");
		emailSender.setPassword("!*usa.1");
		emailSender.setSmtpHost("mail.kattare.com");
		emailSender.setSmtpPort(2525);

		StringBuffer buf = new StringBuffer();
		buf.append("<h2>ECOS Information Request</h2>");
		buf.append("<p/>");
		buf.append("<p/><b>Name</b>: "+getFirstName()+" "+getLastName());
		buf.append("<p/>");
		buf.append("<p/><b>Email</b>: "+getEmailAddress());
		buf.append("<p/>");
		buf.append("<p/><b>Organization</b>: "+getOrganization());
		buf.append("<p/>");
		buf.append("<p/>"+messageText);
		emailSender.setMessage(buf.toString());

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String remoteHost = request.getRemoteHost();
		String sessionId = request.getRequestedSessionId();
		sessionId = sessionId.replace(".undefined", "");		

//		Event event = new Event();
//		event.setTimestamp(new Date());
//		event.setEventAction(EventAction.ACCESSED);
//		event.setEventSeverity(EventSeverity.EVENT);
//		event.setSource(remoteHost+":"+remotePort);
//		event.setDescription(sessionId);
		
		try {
			emailSender.sendMessage();
			//messages.error("contact", "Error: bad destination");
			log.info("Email contact request sent from user: host=" + remoteHost+", session="+sessionId);

		} catch (MessagingException e) {
			messages.error("contact", "Error: "+e.getMessage());
			
		} finally {
			messageText = "";
		}
	}

	public void clear() {
		firstName = "";
		lastName = "";
		emailAddress = "";
		organization = "";
		messageText = "";
		messages.reset("contact");
		messageDialog.setMessage("");
	}
}
