package nam.model.namespace;

import nam.model.Namespace;
import nam.ui.design.AbstractDomainElementManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


@Startup
@AutoCreate
@Name("namespaceManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class NamespaceManager extends AbstractDomainElementManager {

	//private static Log log = LogFactory.getLog(NamespaceManager.class);

	@Out(required=false, value="nam.namespace")
	private Namespace transientNamespace;

	@Out(required=false, value="nam.selectedNamespace")
	private Namespace selectedNamespace;

	@Out(required=true, value="nam.namespaceSetupPanel")
	private NamespaceSetupPage namespaceSetupPanel;

	@Out(required=true, value="nam.namespaceElementPanel")
	private NamespaceElementPage namespaceElementPanel;

	
	public NamespaceManager() {
		namespaceSetupPanel = new NamespaceSetupPage("namespaceManager");
		namespaceElementPanel = new NamespaceElementPage("namespaceManager");
		//initialize(selectedNamespace);
	}

	public void initialize(Namespace namespace) {
		namespaceSetupPanel.initialize(namespace);
		namespaceElementPanel.initialize(namespace);
		namespaceSetupPanel.setVisible(true);
		namespaceElementPanel.setVisible(true);
		setNamespace(namespace);
	}

	protected void refresh() {
		//nothing for now
	}
	
	public Namespace getNamespace() {
		return transientNamespace;
	}

	public Namespace getSelectedNamespace() {
		return selectedNamespace;
	}
	
	public void setNamespace(Namespace namespace) {
		this.transientNamespace = namespace;
	}

	public void selectNamespace(Namespace namespace) {
		this.selectedNamespace = namespace;
		initialize(namespace);
	}
	
	@Override
	public String getTitle() {
		if (transientNamespace != null)
			return "Namespace: "+transientNamespace.getName();
		return null;
	}
	
	@Override
	protected String getRefreshEvent() {
		return "nam.namespacesChanged";
	}

	@Begin(join=true)
	public void editNamespace() {
		editNamespace(transientNamespace);
	}
	
	@Begin(join=true)
	public void editNamespace(Namespace namespace) {
		setTitle("Namespace: "+namespace.getName()+"");
		initialize(namespace);
	}
	
	public void saveNamespace() {
		if (namespaceSetupPanel.isValid() &&
			namespaceElementPanel.isValid()) {
			saveProject();
		}
	}
	
	@Override
	public void submit() {
		super.submit();
		saveNamespace();
		refresh();
	}
	
}
