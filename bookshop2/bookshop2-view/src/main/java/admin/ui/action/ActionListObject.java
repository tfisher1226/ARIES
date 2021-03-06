package admin.ui.action;

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
	public String getLabel() {
		return toString(action);
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
		String thisText = toString(this.action);
		String otherText = toString(other.action);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
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
