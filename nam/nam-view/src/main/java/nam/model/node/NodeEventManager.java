package nam.model.node;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Node;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("nodeEventManager")
public class NodeEventManager extends AbstractEventManager<Node> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Node getInstance() {
		return selectionContext.getSelection("node");
	}
	
	public void removeNode() {
		Node node = getInstance();
		fireRemoveEvent(node);
	}
	
}
