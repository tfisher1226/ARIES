package nam.model.element;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Element;
import nam.model.annotation.AnnotationListManager;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("elementAnnotationsSection")
public class ElementRecord_AnnotationsSection extends AbstractWizardPage<Element> implements Serializable {

	private Element element;
	
	private AnnotationListManager annotationListManager;


	public ElementRecord_AnnotationsSection() {
		//setTitle("Select Annotation.");
		setUrl("Annotations");
		setUrl("annotations");
		//setOwner(owner);
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public void initialize(Element element) {
		//annotationListManager = BeanContext.get("annotationListManager");
		//annotationListManager.refresh(element);
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setElement(element);
	}

	public void validate() {
	}

}
