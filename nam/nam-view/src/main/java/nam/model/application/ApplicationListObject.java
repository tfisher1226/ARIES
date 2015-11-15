package nam.model.application;

import java.io.Serializable;

import nam.model.Application;
import nam.model.Project;
import nam.model.util.ApplicationUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;


public class ApplicationListObject extends AbstractListObject<Application> implements Comparable<ApplicationListObject>, Serializable {
	
	private Project project;
	
	private Application application;
	
	
	public ApplicationListObject(Project project, Application application) {
		this.project = project;
		this.application = application;
	}
	
	public Project getProject() {
		return project;
	}
	
	public Application getApplication() {
		return application;
	}
	
	@Override
	public Object getKey() {
		return getKey(application);
	}
	
	public Object getKey(Application application) {
		return ApplicationUtil.getKey(application);
	}
	
	@Override
	public String getLabel() {
		return getLabel(application);
	}
	
	public String getLabel(Application application) {
		return ApplicationUtil.getLabel(application);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		fireChangeEvent(application, checked);
	}
	
	protected static void fireChangeEvent(Application application, boolean checked) {
		ApplicationEventManager eventManager = BeanContext.getFromSession("applicationEventManager");
		if (checked) {
			eventManager.fireSelectedEvent(application);
		} else {
			eventManager.fireUnselectedEvent(application);
		}
	}
	
	@Override
	public String toString() {
		return toString(application);
	}
	
	@Override
	public String toString(Application application) {
		return ApplicationUtil.toString(application);
	}

	@Override
	public int compareTo(ApplicationListObject other) {
		Object thisKey = getKey(this.application);
		Object otherKey = getKey(other.application);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ApplicationListObject other = (ApplicationListObject) object;
		Object thisKey = ApplicationUtil.getKey(this.application);
		Object otherKey = ApplicationUtil.getKey(other.application);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
