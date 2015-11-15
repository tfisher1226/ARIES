package nam.model.node;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Node;
import nam.model.util.NodeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("nodeDataManager")
public class NodeDataManager implements Serializable {
	
	@Inject
	private NodeEventManager nodeEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Node> getNodeList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Node> getDefaultList() {
		return null;
	}
	
	public void saveNode(Node node) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeNode(Node node) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
