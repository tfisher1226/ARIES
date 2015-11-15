package org.aries.ui;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.application.FacesMessage;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.runtime.BeanContext;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


//@SessionScoped
//@Named("display")

@Startup
@AutoCreate
@Name("display")
@Scope(ScopeType.SESSION)
public class Display implements Serializable {

	private String module;
	
	private int screenWidth;

	private int screenHeight;
	
	@In(required=true)
	private Messages messages;
	

	@Create
	public void create() {
		BeanContext.set("display",  this);
	}
	
	@Destroy
	public void destroy() {
		BeanContext.set("display",  null);
	}
	
	public void initialize(String module) {
		setModule(module);
		getMessages().reset();
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	
	public Messages getMessages() {
		if (messages == null)
			messages = BeanContext.getFromSession("messages");
		return messages;
	}
	
	public boolean messagesExist() {
		return getMessages().isMessagesExist(module);
	}
	

	public void info(String message) {
		//Messages.instance().info(module, message);
		getMessages().info(module, message);
	}

	public void info(String module, String message) {
		getMessages().info(module, message);
	}

	public void warn(String message) {
		//Messages.instance().warn(module, message);
		getMessages().warn(module, message);
	}

	public void warn(String module, String message) {
		getMessages().warn(module, message);
	}

	public void error(String message) {
		//Messages.instance().error(module, message);
		getMessages().error(module, message);
	}

	public void addErrors(Collection<String> messages) {
		addErrors(module, messages);
	}
	
	public void addErrors(String module, Collection<String> messages) {
		Iterator<String> iterator = messages.iterator();
		while (iterator.hasNext()) {
			String message = iterator.next();
			//Messages.instance().error(module, message);
			getMessages().error(module, message);
		}
	}

	public void error(String module, String message) {
		getMessages().error(module, message);
	}

	public void error(Throwable exception) {
		getMessages().error(module, ExceptionUtils.getRootCauseMessage(exception));
	}

	public void add(FacesMessage message) {
		getMessages().add(module, message);
	}

}
