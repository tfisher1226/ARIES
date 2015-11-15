package nam.model.field;

import java.io.Serializable;

import nam.model.Attribute;
import nam.model.Field;
import nam.model.Reference;

import org.aries.Assert;


@SuppressWarnings("serial")
public class FieldListObject implements Serializable, Comparable<FieldListObject> {
	
	private Field field;

	private String type;

	private String icon;

	
	public FieldListObject(Field field) {
		setField(field);
		if (field instanceof Attribute)
			processAttribute((Attribute) field);
		if (field instanceof Reference)
			processReference((Reference) field);
	}

	protected void processAttribute(Attribute attribute) {
		type = "Attribute";
		icon = "/icons/Attribute16.gif";
	}

	protected void processReference(Reference reference) {
		type = "Reference";
		if (reference.getMinOccurs() == 0 && reference.getMaxOccurs() == 1)
			icon = "/icons/Reference16.gif";
		if (reference.getMinOccurs() == 1 && reference.getMaxOccurs() == 1)
			icon = "/icons/ReferenceOne16.gif";
		if (reference.getMinOccurs() == 1 && reference.getMaxOccurs() == -1)
			icon = "/icons/ReferenceSetOne16.gif";
		if (reference.getMinOccurs() == 0 && reference.getMaxOccurs() == -1)
			icon = "/icons/ReferenceSetZero16.gif";
		Assert.notNull(icon, "Unexpected type");
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Override
	public int compareTo(FieldListObject other) {
		String thisName = field.getName();
		String otherName = other.field.getName();
		String thisType = field.getType();
		String otherType = other.field.getType();
		if (!thisName.equals(otherName))
			return thisName.compareTo(otherName);
		if (!thisType.equals(otherType))
			return thisType.compareTo(otherType);
		return 0;
	}

}
