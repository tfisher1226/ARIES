package nam.model.namespace;

import java.io.Serializable;

import nam.model.Namespace;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


/*
 * In order to maximize the potential for hiding (localizing) namespace complexities:
 * 1) minimize definition of elements to those which are to be strictly public
 * 2) specify elementFormDefault as unqualified to hide element namespaces
 * 
 * In order to maximize the potential for reuse as well as maximize the ease or flexibility of
 * switching back and forth between hiding or exposing element namespaces:
 * 1) Maximum reuse of specified models
 * 2) Maximum namespace hiding
 * 
 * one may need to sacrifice the ability to turn on/off namespace exposure because 
 * he/she may require instance documents to be able to use element substitution
 * 
 * control whether namespaces are hidden or exposed by simply setting the value of elementFormDefault 
 */
@SuppressWarnings("serial")
public class NamespaceSetupPage extends AbstractWizardPage<Namespace> implements Serializable {

	//private static Log log = LogFactory.getLog(NamespaceSetupPage.class);

	private Namespace namespace;

	
	public NamespaceSetupPage(String owner) {
		setTitle("Specify Namespace information.");
		//initialize(namespace);
		setOwner(owner);
	}

	public Namespace getNamespace() {
		return namespace;
	}

	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}
	
	public void initialize(Namespace namespace) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setNamespace(namespace);
	}
	
	public void enhance() {
		if (StringUtils.isEmpty(namespace.getPrefix()))
			namespace.setPrefix(namespace.getName());
		if (StringUtils.isEmpty(namespace.getVersion()))
			namespace.setVersion("0.0.1");
	}
	
	public void validate() {
		if (StringUtils.isEmpty(namespace.getName()))
			validator.missing("Name");
		if (StringUtils.isEmpty(namespace.getPrefix()))
			validator.missing("Prefix");
		if (StringUtils.isEmpty(namespace.getUri()))
			validator.missing("URI");
		if (StringUtils.isEmpty(namespace.getVersion()))
			validator.missing("Version");
	}
	
}
