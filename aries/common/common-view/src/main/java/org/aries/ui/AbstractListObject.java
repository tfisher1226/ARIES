package org.aries.ui;

import java.io.Serializable;


@SuppressWarnings("serial")
public abstract class AbstractListObject<R> implements Serializable {

	public abstract String toString(R record);
	
	private boolean selected;
	
	private boolean checked;

	private String backgroundColor = "white";


	public Object getKey() {
		return toString();
	}
	
	public String getLabel() {
		return toString();
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isChecked() {
//		if (checked)
//			System.out.println(this);
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIcon() {
		return "/icons/common/Default16.gif";
	}
	
	public String getBackgroundColor() {
//		if (checked)
//			return "#eef";
//		if (selected)
//			return "#FFEBDA";
		return "inherit";
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
}
