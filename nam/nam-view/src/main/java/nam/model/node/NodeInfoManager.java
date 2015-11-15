package nam.model.node;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Node;
import nam.model.Project;
import nam.model.util.NodeUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("nodeInfoManager")
public class NodeInfoManager extends AbstractNamRecordManager<Node> implements Serializable {
	
	@Inject
	private NodeWizard nodeWizard;
	
	@Inject
	private NodeDataManager nodeDataManager;
	
	@Inject
	private NodePageManager nodePageManager;
	
	@Inject
	private NodeEventManager nodeEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private NodeHelper nodeHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public NodeInfoManager() {
		setInstanceName("node");
	}
	
	
	public Node getNode() {
		return getRecord();
	}
	
	public Node getSelectedNode() {
		return selectionContext.getSelection("node");
	}
	
	@Override
	public Class<Node> getRecordClass() {
		return Node.class;
	}
	
	@Override
	public boolean isEmpty(Node node) {
		return nodeHelper.isEmpty(node);
	}
	
	@Override
	public String toString(Node node) {
		return nodeHelper.toString(node);
	}
	
	@Override
	public void initialize() {
		Node node = selectionContext.getSelection("node");
		if (node != null)
			initialize(node);
	}
	
	protected void initialize(Node node) {
		NodeUtil.initialize(node);
		nodeWizard.initialize(node);
		setContext("node", node);
	}
	
	public void handleNodeSelected(@Observes @Selected Node node) {
		selectionContext.setSelection("node",  node);
		nodePageManager.updateState(node);
		nodePageManager.refreshMembers();
		setRecord(node);
	}
	
	@Override
	public String newRecord() {
		return newNode();
	}
	
	public String newNode() {
		try {
			Node node = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("node",  node);
			String url = nodePageManager.initializeNodeCreationPage(node);
			nodePageManager.pushContext(nodeWizard);
			initialize(node);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Node create() {
		Node node = NodeUtil.create();
		return node;
	}
	
	@Override
	public Node clone(Node node) {
		node = NodeUtil.clone(node);
		return node;
	}
	
	@Override
	public String viewRecord() {
		return viewNode();
	}
	
	public String viewNode() {
		Node node = selectionContext.getSelection("node");
		String url = viewNode(node);
		return url;
	}
	
	public String viewNode(Node node) {
		try {
			String url = nodePageManager.initializeNodeSummaryView(node);
			nodePageManager.pushContext(nodeWizard);
			initialize(node);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editNode();
	}
	
	public String editNode() {
		Node node = selectionContext.getSelection("node");
		String url = editNode(node);
		return url;
	}
	
	public String editNode(Node node) {
		try {
			//node = clone(node);
			selectionContext.resetOrigin();
			selectionContext.setSelection("node",  node);
			String url = nodePageManager.initializeNodeUpdatePage(node);
			nodePageManager.pushContext(nodeWizard);
			initialize(node);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveNode() {
		Node node = getNode();
		if (validateNode(node)) {
			if (isImmediate())
				persistNode(node);
			outject("node", node);
		}
	}
	
	public void persistNode(Node node) {
		saveNode(node);
	}
	
	public void saveNode(Node node) {
		try {
			saveNodeToSystem(node);
			nodeEventManager.fireAddedEvent(node);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveNodeToSystem(Node node) {
		nodeDataManager.saveNode(node);
	}
	
	public void handleSaveNode(@Observes @Add Node node) {
		saveNode(node);
	}
	
	public void addNode(Node node) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichNode(Node node) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Node node) {
		return validateNode(node);
	}
	
	public boolean validateNode(Node node) {
		Validator validator = getValidator();
		boolean isValid = NodeUtil.validate(node);
		Display display = getFromSession("display");
		display.setModule("nodeInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveNode() {
		display = getFromSession("display");
		display.setModule("nodeInfo");
		Node node = selectionContext.getSelection("node");
		if (node == null) {
			display.error("Node record must be selected.");
		}
	}
	
	public String handleRemoveNode(@Observes @Remove Node node) {
		display = getFromSession("display");
		display.setModule("nodeInfo");
		try {
			display.info("Removing Node "+NodeUtil.getLabel(node)+" from the system.");
			removeNodeFromSystem(node);
			selectionContext.clearSelection("node");
			nodeEventManager.fireClearSelectionEvent();
			nodeEventManager.fireRemovedEvent(node);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeNodeFromSystem(Node node) {
		if (nodeDataManager.removeNode(node))
			setRecord(null);
	}
	
	public void cancelNode() {
		BeanContext.removeFromSession("node");
		nodePageManager.removeContext(nodeWizard);
	}
	
}
