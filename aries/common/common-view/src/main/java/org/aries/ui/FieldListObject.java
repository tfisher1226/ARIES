package org.aries.ui;

import java.io.Serializable;

import nam.model.Element;
import nam.model.Field;
import nam.model.Item;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;


@SuppressWarnings("serial")
public class FieldListObject implements Serializable, Comparable<FieldListObject> {

	private Field field;
	
	private Element type;

	private Item item;
	
	private boolean checked;

	
	public FieldListObject(Field field, Element type) {
		this.field = field;
		this.type = type;
	}
	
	public FieldListObject(Item item) {
		this.item = item;
	}

	public Field getField() {
		return this.field;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public String getName() {
		return field.getName();
	}

	public String getType() {
		return TypeUtil.getClassName(field.getType());
	}

	public String getTypeTooltip() {
		return field.getType();
	}

	public String getRelation() {
		return FieldUtil.getRelation(field, type);
	}
	
	public Boolean getTwoway() {
		return FieldUtil.isTwoway(field, type);
	}
	
	public Integer getMinOccurs() {
		return field.getMinOccurs();
	}

	public Integer getMaxOccurs() {
		return field.getMaxOccurs();
	}

	public Boolean getRequired() {
		return field.getRequired();
	}

	public Boolean getUnique() {
		return field.getUnique();
	}

//	public boolean isInverse() {
//		return field.getInverse();
//	}

	public Boolean getManaged() {
		return field.getManaged();
	}

	public Boolean getChangeable() {
		return field.getChangeable();
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public int compareTo(FieldListObject other) {
		String thisId = field.getName();
		String otherId = other.field.getName();
		if (thisId == null)
			return -1;
		if (otherId == null)
			return 1;
		return thisId.compareTo(otherId);
	}

}
