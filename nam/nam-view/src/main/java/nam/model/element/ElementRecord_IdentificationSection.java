package nam.model.element;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Element;
import nam.model.util.ElementUtil;


@SessionScoped
@Named("elementIdentificationSection")
public class ElementRecord_IdentificationSection extends AbstractWizardPage<Element> implements Serializable {

	private Element element;


	public ElementRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}

	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
	
	@Override
	public void initialize(Element element) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setElement(element);
	}
	
	@Override
	public void validate() {
		if (element == null) {
			validator.missing("Element");
		} else {
			if (StringUtils.isEmpty(element.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(element.getType()))
				validator.missing("Type");
		}
	}
	
}
