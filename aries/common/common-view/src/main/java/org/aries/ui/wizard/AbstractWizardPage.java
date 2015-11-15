package org.aries.ui.wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.aries.common.Status;
import org.aries.runtime.BeanContext;
import org.aries.ui.Messages;
import org.aries.util.text.TextUtil;

@SuppressWarnings("serial")
public class AbstractWizardPage implements Serializable {

	private String owner;
	
//	private String fileName;

	private String title;

	private String message;

	private Status status;

	private boolean visible;

	private boolean enabled;

	private boolean backEnabled;

	private boolean nextEnabled;

	private boolean finishEnabled;
	
	protected Validator validator;

	
	public AbstractWizardPage() {
		validator = new Validator();
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
//	public String getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public AbstractWizardPage getPreviousPage() {
		return null;
	}

	public AbstractWizardPage getNextPage() {
		return null;
	}

	public boolean isBackEnabled() {
		return backEnabled;
	}

	public void setBackEnabled(boolean backEnabled) {
		this.backEnabled = backEnabled;
	}

	public boolean isNextEnabled() {
		return nextEnabled;
	}

	public void setNextEnabled(boolean nextEnabled) {
		this.nextEnabled = nextEnabled;
	}

	public boolean isFinishEnabled() {
		return finishEnabled;
	}

	public void setFinishEnabled(boolean finishEnabled) {
		this.finishEnabled = finishEnabled;
	}

	public boolean isValid() {
		validator.reset();
		enhance();
		validate();
		return validator.result();
	}

	public void enhance() {
		//nothing by default
	}

	public void validate() {
		//nothing by default
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		return this == object;
	}

	public int hashCode() {
		if (getOwner() != null)
			return getOwner().hashCode();
		return 0;
	}

	
	protected class Validator {

		private List<String> missingTypes;

		private Map<String, String> invalidTypes;

		private Messages messages;


		public void reset() {
			missingTypes = new ArrayList<String>();			
			invalidTypes = new LinkedHashMap<String, String>();			
		}
		
		public void missing(String name) {
			missingTypes.add(name);
		}

		public void invalid(String name, String message) {
			invalidTypes.put(name, message);
		}

		public boolean result() {
			messages = BeanContext.get("messages");
			processMissingTypes();
			processInvalidTypes();
			int messageCount = messages.getMessageCount();
			return messageCount == 0;
		}

		protected void processMissingTypes() {
			String types = TextUtil.joinTextWithCommas(missingTypes);
			if (types.length() > 0)
				messages.error(getOwner(), types + " must be specified.");
		}

		protected void processInvalidTypes() {
			Iterator<String> iterator = invalidTypes.keySet().iterator();
			while (iterator.hasNext()) {
				String type = (String) iterator.next();
				String message = invalidTypes.get(type);
				//TODO type not being used for now
				messages.error(getOwner(), message);
			}
		}

	}
	
}
