package nam.model.namespace;

import java.io.Serializable;
import java.util.List;

import nam.model.Element;
import nam.model.Namespace;
import nam.model.element.ElementListManager;
import nam.model.util.NamespaceUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;


@SuppressWarnings("serial")
public class NamespaceElementPage extends AbstractWizardPage<Namespace> implements Serializable {

	private Namespace namespace;
	
	private ElementListManager elementListManager;


	public NamespaceElementPage(String owner) {
		//setTitle("Select Elements.");
		//initialize(namespace);
		setOwner(owner);
	}

	public Namespace getNamespace() {
		return namespace;
	}

	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}

	public boolean isVisible() {
		return super.isVisible();
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}
	
	public void initialize(Namespace namespace) {
		elementListManager = BeanContext.get("elementListManager");
		List<Element> elements = NamespaceUtil.getElements(namespace);
		elementListManager.initialize(elements);
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setNamespace(namespace);
	}

	public void validate() {
	}
	
}
