package nam.model.element;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Element;
import nam.model.Namespace;
import nam.model.namespace.NamespaceListManager;
import nam.model.util.ElementUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;

import aries.generation.engine.GenerationContext;


@SessionScoped
@Named("elementNamespacesSection")
public class ElementRecord_NamespacesSection extends AbstractWizardPage<Element> implements Serializable {

	private Element element;
	
	private NamespaceListManager namespaceListManager;


	public ElementRecord_NamespacesSection() {
		setName("Namespaces");
		setUrl("namespaces");
	}
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
	
	protected GenerationContext getContext() {
		GenerationContext context = BeanContext.getFromSession("context");
		return context;
	}

	public void initialize(Element element) {
		GenerationContext context = getContext();
		Namespace namespace = context.getNamespaceByUri(element.getNamespace());
		Collection<Namespace> namespaces = ElementUtil.getNamespaces(element);
		namespaceListManager.initialize(namespaces);
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
