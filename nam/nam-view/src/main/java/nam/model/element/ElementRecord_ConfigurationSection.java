package nam.model.element;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Element;
import nam.model.util.ElementUtil;


@SessionScoped
@Named("elementConfigurationSection")
public class ElementRecord_ConfigurationSection extends AbstractWizardPage<Element> implements Serializable {

	private Element element;

	
	public ElementRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
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
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setElement(element);
	}
	
	@Override
	public void validate() {
		if (element == null) {
			validator.missing("Element");
		} else {
		}
	}
	
}
