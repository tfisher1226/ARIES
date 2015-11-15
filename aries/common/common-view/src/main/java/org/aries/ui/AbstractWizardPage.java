package org.aries.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.aries.common.Status;
import org.aries.runtime.BeanContext;
import org.aries.util.text.TextUtil;


public abstract class AbstractWizardPage<T> implements Serializable {
	
	public abstract void initialize(T object);
	
	private String url;
	
	private String name;
	
	private String owner;
	
//	private String fileName;

	private String title;

	private String message;

	private Status status;

	private boolean visible;

	private boolean enabled;

	private boolean backVisible;

	private boolean backEnabled;

	private boolean nextVisible;

	private boolean nextEnabled;

	private boolean finishVisible;
	
	private boolean finishEnabled;
	
	private boolean populateVisible;

	private boolean populateEnabled;

//	private AbstractWizardPage<T> previousPage;
//	
//	private AbstractWizardPage<T> nextPage;
	
	protected Validator validator;

	
	public AbstractWizardPage() {
		enabled = false;
		backVisible = true;
		backEnabled = true;
		nextVisible = true;
		nextEnabled = true;
		finishVisible = true;
		finishEnabled = true;
		validator = new Validator();
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isBackVisible() {
		return backVisible;
	}

	public void setBackVisible(boolean backVisible) {
		this.backVisible = backVisible;
	}

	public boolean isBackEnabled() {
		return backEnabled && backVisible;
	}

	public void setBackEnabled(boolean backEnabled) {
		this.backEnabled = backEnabled;
	}

	public boolean isNextVisible() {
		return nextVisible;
	}

	public void setNextVisible(boolean nextVisible) {
		this.nextVisible = nextVisible;
	}

	public boolean isNextEnabled() {
		return nextEnabled && nextVisible;
	}

	public void setNextEnabled(boolean nextEnabled) {
		this.nextEnabled = nextEnabled;
	}

	public boolean isFinishVisible() {
		return finishVisible;
	}

	public void setFinishVisible(boolean finishVisible) {
		this.finishVisible = finishVisible;
	}

	public boolean isFinishEnabled() {
		return finishEnabled && finishVisible;
	}

	public void setFinishEnabled(boolean finishEnabled) {
		this.finishEnabled = finishEnabled;
	}

	public boolean isPopulateVisible() {
		return populateVisible;
	}

	public void setPopulateVisible(boolean populateVisible) {
		this.populateVisible = populateVisible;
	}

	public boolean isPopulateEnabled() {
		return populateEnabled;
	}

	public void setPopulateEnabled(boolean populateEnabled) {
		this.populateEnabled = populateEnabled;
	}

//	public AbstractWizardPage<T> getPreviousPage() {
//		return previousPage;
//	}
//
//	public void setPreviousPage(AbstractWizardPage<T> previousPage) {
//		this.previousPage = previousPage;
//	}
//	
//	public AbstractWizardPage<T> getNextPage() {
//		return nextPage;
//	}
//	
//	public void setNextPage(AbstractWizardPage<T> nextPage) {
//		this.nextPage = nextPage;
//	}
	

//	public <T> void initialize(T object) {
//		//nothing by default
//	}
	
	public boolean isValid() {
		validator.reset();
		enhance();
		validate();
		return validator.result();
	}

	public String first() {
		return null;
	}
	
	public String back() {
		return null;
	}

	public String next() {
		return null;
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

		public void error(String name, String message) {
			invalidTypes.put(name, message);
		}

		public void invalid(String name, String message) {
			String messageInLowerCase = message.toLowerCase();
			if (!messageInLowerCase.endsWith("invalid"))
				message += " is invalid";
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
			Iterator<String> iterator = missingTypes.iterator();
			while (iterator.hasNext()) {
				String missingType = iterator.next();
				messages.error(getOwner(), missingType + " must be specified.");
			}
		}

		protected void processInvalidTypes() {
			Iterator<String> iterator = invalidTypes.keySet().iterator();
			while (iterator.hasNext()) {
				String invalidType = iterator.next();
				String message = invalidTypes.get(invalidType);
				//TODO type not being used for now
				messages.error(getOwner(), message);
			}
		}

		protected void processMissingTypes2() {
			String types = TextUtil.joinTextWithCommas(missingTypes);
			if (types.length() > 0)
				messages.error(getOwner(), types + " must be specified.");
		}

		protected void processInvalidTypes2() {
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
