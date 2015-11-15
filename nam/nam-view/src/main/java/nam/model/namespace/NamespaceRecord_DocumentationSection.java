package nam.model.namespace;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Namespace;
import nam.model.util.NamespaceUtil;


@SessionScoped
@Named("namespaceDocumentationSection")
public class NamespaceRecord_DocumentationSection extends AbstractWizardPage<Namespace> implements Serializable {
	
	private Namespace namespace;
	
	
	public NamespaceRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Namespace getNamespace() {
		return namespace;
	}
	
	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}
	
	@Override
	public void initialize(Namespace namespace) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setNamespace(namespace);
	}
	
	@Override
	public void validate() {
		if (namespace == null) {
			validator.missing("Namespace");
		} else {
		}
	}
	
}
