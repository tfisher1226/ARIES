package org.aries.ui;

import java.io.Serializable;


@SuppressWarnings("serial")
public abstract class AbstractListObject<R> implements Serializable {

	public abstract String toString(R record);
	
	private boolean selected;
	
	private boolean checked;


	public boolean isSelected() {
//		if (selected)
//			System.out.println();
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isChecked() {
		return selected;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		setSelected(checked);
	}
	
	public Object getKey() {
		return toString();
	}
	
	public String getLabel() {
		return toString();
	}
	
}
