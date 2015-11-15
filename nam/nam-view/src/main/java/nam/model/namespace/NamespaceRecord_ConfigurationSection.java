package nam.model.namespace;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Namespace;
import nam.model.util.NamespaceUtil;


@SessionScoped
@Named("namespaceConfigurationSection")
public class NamespaceRecord_ConfigurationSection extends AbstractWizardPage<Namespace> implements Serializable {
	
	private Namespace namespace;
	
	
	public NamespaceRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
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
