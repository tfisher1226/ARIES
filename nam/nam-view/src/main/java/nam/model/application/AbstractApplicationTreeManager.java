package nam.model.application;

import java.io.Serializable;

import nam.ui.design.AbstractDomainTreeManager;

import org.richfaces.component.UITree;


@SuppressWarnings("serial")
public abstract class AbstractApplicationTreeManager extends AbstractDomainTreeManager implements Serializable {

	private int expansionState = 1;
	

	public Boolean adviseNodeOpened(UITree tree) {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ResponseStateManager stateManager = facesContext.getRenderKit().getResponseStateManager();
//		if (!stateManager.isPostback(facesContext))
//			return Boolean.FALSE;
//		TreeRowKey currentRowKey = getCurrentRowKey(tree);
//		boolean expanded = componentState.isExpanded(currentRowKey);
//		return expanded;
		return Boolean.TRUE;
	}

	public Boolean adviseNodeSelected(UITree tree) {
//		@SuppressWarnings("unchecked") TreeRowKey rowKey = componentState.getSelectedNode();
//		if (rowKey == null)
//			return Boolean.FALSE;
//		try {
//			ModelTreeNode selectedNode = getSelectedTreeNode(tree, rowKey);
//			ModelTreeNode currentNode = getCurrentTreeNode(tree);
//			if (selectedNode == null || !selectedNode.equals(currentNode))
//				return Boolean.FALSE;
//			processSelection(currentNode);
//			return Boolean.TRUE;
//		} catch (Exception e) {
//			return Boolean.TRUE;
//		}
		return Boolean.FALSE;
	}
	
//	public void nodeSelected(NodeSelectedEvent event) {
//		super.processTreeSelectionChange(event);
//		processSelection();
//	}

//	protected void processSelection() {
//		processSelection((ModelTreeNode) getSelectedNode());
//	}
//	
//	protected void processSelection(ModelTreeNode currentNode) {
//		ModelTreeObject<?> data = currentNode.getData();
//		Object selectedObject = data.getObject();
//		if (selectedObject instanceof Configuration)
//			configurationManager.selectConfiguration((Configuration) selectedObject);
//		if (selectedObject instanceof Application)
//			applicationManager.selectApplication((Application) selectedObject);
//		if (selectedObject instanceof Module)
//			moduleManager.selectModule((Module) selectedObject);
//	}
	
	
	public void collapse() {
		log.info("collapse()");
		expansionState = (expansionState + 1) % 2;
	}
	
	public void expand() {
		expansionState = 2;
	}

//	public void dropListener(DropEvent dropEvent) {
//		// resolve drag destination attributes
//		UITreeNode destNode = (dropEvent.getSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getSource() : null;
//		UITree destTree = destNode != null ? destNode.getUITree() : null;
//		TreeRowKey dropNodeKey = (dropEvent.getDropValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDropValue() : null;
//		TreeNode droppedInNode = dropNodeKey != null ? destTree.getTreeNode(dropNodeKey) : null;
//		ModelTreeNode dropNode = (ModelTreeNode) destTree.getModelTreeNode(dropNodeKey);
//		//ModelTreeNode dropNode = (ModelTreeNode) droppedInNode;
//		
//		// resolve drag source attributes
//		//UITreeNode srcNode = (dropEvent.getDraggableSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getDraggableSource() : null;
//		//UITree srcTree = srcNode != null ? srcNode.getUITree() : null;
//		
//		Object dragValue = dropEvent.getDragValue();
//		//TreeRowKey dragNodeKey = (dragValue instanceof TreeRowKey) ? (TreeRowKey) dragValue : null;
//		//TreeNode draggedNode = dragNodeKey != null ? srcTree.getTreeNode(dragNodeKey) : null;
//		//if (dropEvent.getDraggableSource() instanceof UIDragSupport && srcTree == null && draggedNode == null && dragValue instanceof TreeNode) {        
//		//	srcTree = destTree;
//		//	draggedNode = (TreeNode) dragValue;
//		//	dragNodeKey = srcTree.getTreeNodeRowKey(draggedNode) instanceof TreeRowKey ? (TreeRowKey) srcTree.getTreeNodeRowKey(draggedNode) : null;
//		//}
//		
//		// Note: check if we dropped node on to itself or to item instead of
//		// folder here
//		//if (droppedInNode != null && droppedInNode.equals(draggedNode)) {
//		//	return;
//		//}
//
//		//FacesContext context = FacesContext.getCurrentInstance();
//
//		if (dropNodeKey != null) {
//			// add destination node for rerender
//			//destTree.addRequestKey(dropNodeKey);
//
//			//Object state = null;
//			if (dragValue != null) {
//				//System.out.println(">>>"+dragValue);
//
//				Module module = new Module();
//				module.setType(ModuleType.CLIENT);
//				module.setGroupId("org.aries.broker");
//				module.setArtifactId("broker-client");
//				//treeNodeFactory.createModuleNode(dropNode, module);
//				//draggedNode.setParent(droppedInNode);
//				
//				//TODO Application application = (Application) dropNode.getData().getObject();
//				//TODO ApplicationUtil.getModules(application).add(module);
//				refresh();
//			}
//
//			// generate new node id
//			//Object id = getNewId(destTree.getTreeNode(dropNodeKey));
//			//destTree.addNode(dropNodeKey, draggedNode, id, state);
//		}
//
//		try {
//			// Add destination tree to reRender
//			//AjaxContext ajaxContext = AjaxContext.getCurrentInstance();
//			//ajaxContext.addComponentToAjaxRender(destTree);
//			//TODO don't hard-code this...
//			//ajaxContext.addRenderedArea("applicationTreePanel");
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
//	}
	
}
