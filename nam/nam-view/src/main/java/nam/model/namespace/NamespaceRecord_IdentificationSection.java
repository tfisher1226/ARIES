package nam.model.namespace;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Namespace;
import nam.model.util.NamespaceUtil;


@SessionScoped
@Named("namespaceIdentificationSection")
public class NamespaceRecord_IdentificationSection extends AbstractWizardPage<Namespace> implements Serializable {
	
	private Namespace namespace;
	
	
	public NamespaceRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setNamespace(namespace);
	}
	
	@Override
	public void validate() {
		if (namespace == null) {
			validator.missing("Namespace");
		} else {
			if (StringUtils.isEmpty(namespace.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(namespace.getUri()))
				validator.missing("URI");
		}
	}
	
}
