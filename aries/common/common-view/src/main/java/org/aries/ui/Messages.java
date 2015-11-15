package org.aries.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


//@SessionScoped
//@Named("messageHelper")

@Startup
@AutoCreate
@Name("messages")
@Scope(ScopeType.SESSION)
public class Messages implements Serializable {
	
//	@Out(required = false, scope = ScopeType.EVENT)
	private boolean messagesExist;
	
//	@Out(required = false)
//	private List<FacesMessage> messageList;

	private String iconLibrary;

	
	
	public Messages() {
		iconLibrary = "graphics";
	}
	
	public String getIconLibrary() {
		return iconLibrary;
	}

	public void setIconLibrary(String iconLibrary) {
		this.iconLibrary = iconLibrary;
	}

	@Create
	public void create() {
		BeanContext.set("messages",  this);
	}
	
	@Destroy
	public void destroy() {
		BeanContext.set("messages",  null);
	}
	
	public void reset() {
		messagesExist = false;
		Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages();
		while (messages.hasNext()) {
			messages.next();
			messages.remove();
		}
	}

	public void reset(String clientId) {
		messagesExist = false;
		Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages(clientId);
		while (messages.hasNext()) {
			messages.next();
			messages.remove();
		}
	}

	public void info(String module, String detail, Object... params) {
		add(FacesMessage.SEVERITY_INFO, module, "", detail, params);
	}

	public void warn(String module, String detail, Object... params) {
		add(FacesMessage.SEVERITY_WARN, module, "", detail, params);
	}

	public void error(String module, String detail, Object... params) {
		add(FacesMessage.SEVERITY_ERROR, module, "", detail, params);
	}

//	public void error(String module, String detail, Collection<Object> params) {
//		add(FacesMessage.SEVERITY_ERROR, module, "", detail, params);
//	}

	public void add(Severity severity, String module, String summary, String detail, Object... params) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		add(module, message);
	}

//	public void add(FacesMessage message) {
//		add(display.getModule(), message);
//	}
	
	public void add(String clientId, FacesMessage message) {
//		if (clientId == null)
//			System.out.println();
		if (clientId != null)
			clientId = clientId.toLowerCase();
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (clientId == null || clientId.equals("global"))
			clientId = null;
		if (currentInstance != null)
			currentInstance.addMessage(clientId, message);
//		if (message.getSeverity() != FacesMessage.SEVERITY_INFO)
//			System.out.println();
		if (clientId == null)
			clientId = "";
		List<FacesMessage> list = getMessages(clientId);
		messagesExist = list.size() > 0;
//		messageList = list;
	}

	public boolean isExist() {
		return messagesExist;
	}

	public boolean isGlobalMessagesExist() {
		//return FacesMessages.instance().getCurrentMessages().size() > 0;
		List<FacesMessage> messages = FacesContext.getCurrentInstance().getMessageList();
		return messages != null && messages.size() > 0;
	}

	public boolean isMessagesExist(String clientId) {
		clientId = clientId.toLowerCase();
		//System.out.println(">>>"+clientId);
		if (StringUtils.isEmpty(clientId))
			return false;
		//if (clientId.equalsIgnoreCase("saveUser"))
		//	System.out.println("INVOKED");
		//if (clientId.equals("global"))
		//	return isGlobalMessagesExist();
		if (clientId.equals("global"))
			clientId = null;
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance != null) {
			Iterator<FacesMessage> messages = currentInstance.getMessages(clientId);
			boolean hasMessages = messages != null && messages.hasNext();
			//if (clientId != null && clientId.equalsIgnoreCase("saveUser") && hasMessages)
			//	System.out.println("YES");
//			if (clientId != null && clientId.equals("contact") && hasMessages)
//				System.out.println();
			return hasMessages;
		}
		return false;
	}
    
	public boolean isFlagsExist(String clientId) {
		clientId = clientId.toLowerCase();
		if (StringUtils.isEmpty(clientId))
			return false;
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance != null) {
			Iterator<FacesMessage> messages = currentInstance.getMessages(clientId);
			while (messages.hasNext()) {
				FacesMessage facesMessage = (FacesMessage) messages.next();
				if (facesMessage.getSeverity().equals(FacesMessage.SEVERITY_WARN) ||
					facesMessage.getSeverity().equals(FacesMessage.SEVERITY_ERROR) ||
					facesMessage.getSeverity().equals(FacesMessage.SEVERITY_FATAL))
						return true;
			}
		}
		return false;
	}

//	public void setMessagesExist(boolean value) {
//		//NO-OP
//	}
	
	public int getMessageCount() {
		List<FacesMessage> messages = FacesContext.getCurrentInstance().getMessageList();
		return messages.size();
	}

	public List<FacesMessage> getMessages() {
		List<FacesMessage> list = new ArrayList<FacesMessage>();
		Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance().getMessages();
		while (iterator.hasNext()) {
			FacesMessage facesMessage = (FacesMessage) iterator.next();
			list.add(facesMessage);
		}
		return list;
	}

	public int getMessageCount(String clientId) {
		List<FacesMessage> messages = getMessages(clientId);
		return messages.size();
	}
	
	public String getMessage(String clientId) {
		List<FacesMessage> messages = getMessages(clientId);
		if (messages.size() > 0) {
			FacesMessage message = messages.get(0);
			return message.getDetail();
		}
		return null;
	}
	
	public List<FacesMessage> getMessages(String clientId) {
		clientId = clientId.toLowerCase();
		List<FacesMessage> list = new ArrayList<FacesMessage>();
		if (StringUtils.isEmpty(clientId))
			return list;
		if (clientId.equals("global"))
			return getMessages();
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance != null) {
			Iterator<FacesMessage> messages = currentInstance.getMessages(clientId);
			//List<FacesMessage> messages = FacesMessages.instance().getCurrentMessages(clientId);
			while (messages.hasNext()) {
				FacesMessage message = (FacesMessage) messages.next();
				list.add(message);
			}
		}
		//if (clientId.equals("OrganizationTransferOrganizationSelect"))
		//	System.out.println();
		return list;
	}
}
