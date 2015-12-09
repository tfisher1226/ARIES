package admin.action;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.Action;


public class ActionListObject extends AbstractListObject<Action> implements Comparable<ActionListObject>, Serializable {
	
	private Action action;

	
	public ActionListObject(Action action) {
		this.action = action;
	}

	
	public Action getAction() {
		return action;
	}
	
	@Override
	public Object getKey() {
		return action.name();
	}
	
	@Override
	public String getLabel() {
		return action.name();
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Action16.gif";
	}
	
	@Override
	public String toString() {
		return toString(action);
	}
	
	@Override
	public String toString(Action action) {
		return action.name();
	}

	@Override
	public int compareTo(ActionListObject other) {
		String thisText = this.action.name();
		String otherText = other.action.name();
		return thisText.compareTo(otherText);
	}

	@Override
	public boolean equals(Object object) {
		ActionListObject other = (ActionListObject) object;
		String thisText = toString(this.action);
		String otherText = toString(other.action);
		return thisText.equals(otherText);
	}
	
}
