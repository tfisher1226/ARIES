package nam.model.field;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Field;
import nam.model.util.FieldUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.util.Validator;


@SessionScoped
@Named("fieldInfoManager")
public class FieldInfoManager extends AbstractRecordManager<Field> implements Serializable {
	
	public FieldInfoManager() {
		setInstanceName("field");
	}
	
	
	public Field getField() {
		return getRecord();
	}
	
	@Override
	public Class<Field> getRecordClass() {
		return Field.class;
	}
	
	@Override
	public boolean isEmpty(Field field) {
		return getFieldHelper().isEmpty(field);
	}
	
	@Override
	public String toString(Field field) {
		return getFieldHelper().toString(field);
	}
	
	protected FieldHelper getFieldHelper() {
		return BeanContext.getFromSession("fieldHelper");
	}
	
	protected void initialize(Field field) {
		FieldUtil.initialize(field);
		initializeOutjectedState(field);
		setContext("field", field);
	}
	
	protected void initializeOutjectedState(Field field) {
		outject(instanceName, field);
	}
	
	public void activate() {
		initializeContext();
		Field field = BeanContext.getFromSession(getInstanceId());
		if (field == null)
			newField();
		else editField(field);
	}
	
	public void newField() {
		try {
			Field field = create();
			initialize(field);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Field create() {
		Field field = FieldUtil.create();
		return field;
	}
	
	@Override
	public Field clone(Field field) {
		field = FieldUtil.clone(field);
		return field;
	}
	
	public void editField(Field field) {
		try {
			field = clone(field);
			initialize(field);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void saveField() {
		setModule("Field");
		Field field = getField();
		enrichField(field);
		if (validate(field)) {
			saveField(field);
		}
	}
	
	public void processField(Field field) {
			saveField(field);
	}
	
	public void saveField(Field field) {
		try {
			raiseEvent("org.aries.refreshFieldList");
			raiseEvent(actionEvent);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichField(Field field) {
		//nothing for now
	}
	
	public boolean validate(Field field) {
		Validator validator = getValidator();
		boolean isValid = FieldUtil.validate(field);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void cancelField() {
		BeanContext.removeFromSession("field");
	}
	
}
