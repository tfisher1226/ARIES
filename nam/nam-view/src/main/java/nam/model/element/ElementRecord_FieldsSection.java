package nam.model.element;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Element;
import nam.model.Field;
import nam.model.field.FieldListManager;
import nam.model.util.ElementUtil;

import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("elementFieldsSection")
public class ElementRecord_FieldsSection extends AbstractWizardPage<Element> implements Serializable {

	private Element element;
	
	private FieldListManager fieldListManager;


	public ElementRecord_FieldsSection() {
		setName("Fields");
		setUrl("fields");
	}
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
	
	public void initialize(Element element) {
		List<Field> fields = ElementUtil.getFields(element);
		fieldListManager.initialize(fields);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setElement(element);
	}
	
	public void validate() {
		if (element == null) {
			validator.missing("Element");
		} else {
		}
	}
	
}
