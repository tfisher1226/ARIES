package nam.model.field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Field;
import nam.model.Reference;
import nam.model.util.ElementUtil;

import org.aries.Assert;
import org.aries.ui.AbstractTabListManager;
import org.aries.ui.model.ListTableModel;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;



public class FieldListManager2 implements Serializable {

	private Element element;

	private Field field;

	private DataModel<FieldListObject> fieldList;

	private ReferenceManager referenceManager;

	private AttributeManager attributeManager;

	
	public void reset() {
		fieldList = null;
		refresh(element);
	}

	public void clearSelection() {
		field = null;
	}

	public boolean isSelected() {
		return field != null;
	}

//	public FieldListObject getSelection() {
//		return selectedRow;
//	}

	public void setSelection(FieldListObject rowObject) {
		field = rowObject.getField();
		//selectedRow = rowObject;
	}

	public Field getSelectedRecord() {
		return isSelected() ? field : null;
	}

	public DataModel<FieldListObject> getFieldList() {
		return fieldList;
	}

	@Observer("nam.fieldsChanged")
	public void refresh() {
		refresh(element);
	}
	
	@SuppressWarnings("unchecked")
	public void refresh(Element element) {
		fieldList = new ListTableModel<FieldListObject>(getFields(element));
		this.element = element;
		//clearSelection();
	}

	protected List<FieldListObject> getFields(Element element) {
		List<Field> fields = ElementUtil.getFields(element);
		Assert.notNull(fields, "Fields should not be null");
		List<FieldListObject> list = new ArrayList<FieldListObject>();
		for (Field field : fields) {
			FieldListObject item = new FieldListObject(field);
			list.add(item);
		}
		return list;
	}

	public void viewFields() {
		refresh(element);
	}

	public void editField() {
		if (field instanceof Attribute)
			editField((Attribute) field);
		if (field instanceof Reference)
			editField((Reference) field);
	}

	public void editField(Attribute attribute) {
		attributeManager.editAttribute(attribute);
	}

	public void editField(Reference reference) {
		referenceManager.editReference(reference);
	}

	public String getDialogName(Attribute attribute) {
		return "attributeDialog";
	}
	
	public String getDialogName(Reference reference) {
		return "referenceDialog";
	}

}
