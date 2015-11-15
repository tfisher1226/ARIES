package nam.ui.design;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import nam.model.Project;

import org.aries.runtime.BeanContext;
import org.aries.ui.event.Selected;


@SessionScoped
@Named("selectionContext")
@SuppressWarnings("serial")
public class SelectionContext extends AbstractSelectionContext implements Serializable {

	private Map<String, Object> values = new HashMap<String, Object>();
	
	private String selectedArea;
	
	private String selectedType;

	private String selectedName;

	private String selectedAction;

	private String currentArea;

	private String messageDomain;
	
	
	@SuppressWarnings("unchecked")
	public <T> T getSelection(String key) {
//		if (key.equals("service"))
//			System.out.println();
		synchronized (values) {
			return (T) values.get(key.toLowerCase());
		}
	}
	
	public <T> void setSelection(String keyBase, T value) {
//		if (key.equals("projectList"))
//			System.out.println();
		synchronized (values) {
			String key = keyBase.toLowerCase();
			String key2 = key + "selection";
			Collection<T> selection = getSelection(key2);
			if (selection == null) {
				selection = new HashSet<T>();
				values.put(key2, selection);
			}
			selection.add(value);
			values.put(key, value);
		}
	}
	
	public <T> void unsetSelection(String keyBase, T value) {
		synchronized (values) {
			String key = keyBase.toLowerCase();
			String key2 = key + "Selection";
			Collection<T> selection = getSelection(key2);
			if (selection == null) {
				selection = new HashSet<T>();
				values.put(key2, selection);
			}
			selection.remove(value);
			Object object = values.get(key);
			if (object == null) {
				values.put(key, null);
			}
		}
	}
	
	public void refreshSelection(@Observes @Selected Object object) {
		String key = object.getClass().getSimpleName();
		setSelection(key, object);
	}

	public void clearSelection(String key) {
		setSelection(key, null);
	}

	public void clearSelection() {
		synchronized (values) {
			values.clear();
		}
	}

	public void clearSelectedArea() {
		selectedArea = null;
	}

	public String getSelectedArea() {
		return selectedArea;
	}

	public void setSelectedArea(String selectedArea) {
		this.selectedArea = selectedArea;
	}

	public void clearSelectedType() {
		selectedType = null;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public void clearSelectedName() {
		selectedName = null;
	}

	public String getSelectedName() {
		return selectedName;
	}

	public void setSelectedName(String selectedName) {
		this.selectedName = selectedName;
	}

	public void clearSelectedAction() {
		selectedAction = null;
	}

	public String getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}

	public void clearCurrentArea() {
		selectedArea = null;
	}

	public void restoreCurrentArea() {
		//this.currentArea = getOrigin();
	}

	public String getCurrentArea() {
		return currentArea;
	}

	public void setCurrentArea(String currentArea) {
		this.currentArea = currentArea;
	}
	
	public void clearMessageDomain() {
		selectedArea = null;
	}

	public String getMessageDomain() {
		return messageDomain;
	}

	public void setMessageDomain(String messageDomain) {
		this.messageDomain = messageDomain;
	}

	

}
