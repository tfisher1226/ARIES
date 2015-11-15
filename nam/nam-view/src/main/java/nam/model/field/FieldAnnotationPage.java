package nam.model.field;

import java.io.Serializable;

import nam.model.Field;
import nam.model.annotation.AnnotationListManager;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;


@SuppressWarnings("serial")
public class FieldAnnotationPage extends AbstractWizardPage<Field> implements Serializable {

	private Field field;
	
	private AnnotationListManager annotationListManager;
	

	public FieldAnnotationPage(String owner) {
		setTitle("Select Annotation.");
		//initialize(field);
		setOwner(owner);
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void initialize(Field field) {
		//annotationListManager = BeanContext.get("annotationListManager");
		//annotationListManager.refresh(field);
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setField(field);
	}

	public void validate() {
	}

}
