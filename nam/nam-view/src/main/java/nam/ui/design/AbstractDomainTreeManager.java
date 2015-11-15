package nam.ui.design;

import nam.ui.tree.ModelTreeNode;

import org.aries.Handler;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTreeManager;
import org.aries.util.NameUtil;
import org.richfaces.component.UITree;


public abstract class AbstractDomainTreeManager extends AbstractTreeManager<ModelTreeNode> {

//	private String pageTitle;

//	protected User user;

	//@In(required = false, scope=ScopeType.EVENT)
//	protected List<Project> projects;

//	protected Object selectedArtifact;
	
//	private List<ModelTreeNode> selectedNodeChildren = new ArrayList<ModelTreeNode>();
	
//	protected TreeState componentState;
	
	protected Handler<Object> selectionListener;


	@Override
	public String getFormId() {
		return "pageForm";
	}

//	public String getPageTitle() {
//		//pageTitle = "Messages for: "+UserUtil.toString(user);
//		return pageTitle;
//	}
//
//	public void setPageTitle(String title) {
//		this.pageTitle = title;
//	}

	public String getSelectedNodeName() {
		if (selectedNode != null)
			return NameUtil.capName(selectedNode.toString());
		return null;
	}

	public String getSelectedNodeType() {
		ModelTreeNode node = (ModelTreeNode) getSelectedNode();
		if (node != null) {
			String object = node.getType();
			return object;
		}
		return null;
	}

	public boolean isTypeSelected(Class<?> classObject) {
		Object object = getSelectedObject();
		return object != null && object.getClass().equals(classObject);
	}
	
	public Object getSelectedObject() {
		ModelTreeNode node = (ModelTreeNode) getSelectedNode();
		if (node != null) {
			Object object = node.getData().getObject();
			return object;
		}
		return null;
	}
	
	public String getSelectedObjectType() {
		ModelTreeNode node = (ModelTreeNode) getSelectedNode();
		if (node != null) {
			String objectType = node.getData().getType();
			return objectType;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSelectedObject(ModelTreeNode node, Class<T> classObject) {
		Object object = node.getData().getObject();
		if (object.getClass().equals(classObject))
			return (T) object;
		ModelTreeNode parent = (ModelTreeNode) node.getParent();
		object = getSelectedObject(parent, classObject);
		//if (object != null)
		return (T) object;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getObject(ModelTreeNode treeNode) {
		if (treeNode != null)
			return (T) treeNode.getData().getObject();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getParentObject(ModelTreeNode treeNode, String type) {
		if (treeNode == null)
			return null;
		if (treeNode.getType().equals(type))
			return (T) treeNode.getData().getObject();
		ModelTreeNode parent = (ModelTreeNode) treeNode.getParent();
		return getParentObject(parent, type);
	}

//	protected <T extends TreeNode> TreeRowKey getCurrentRowKey(UITree tree) {
//		@SuppressWarnings("unchecked") TreeRowKey rowKey = (TreeRowKey) tree.getRowKey();
//		return rowKey;
//	}
	
//	protected <T extends TreeNode> T getCurrentTreeNode(UITree tree) {
//		@SuppressWarnings("unchecked") T treeNode = (T) getSelectedTreeNode(tree, (TreeRowKey) tree.getRowKey());
//		return treeNode;
//	}
	
//	@SuppressWarnings("unchecked")
//	protected <T extends TreeNode> T getSelectedTreeNode(UITree tree, TreeRowKey rowKey) {
//		return rowKey != null ? (T) tree.getModelTreeNode(rowKey) : null;
//	}


//	public List<DomainTreeNode> getSelectedNodeChildren() {
//		return selectedNodeChildren;
//	}
//
//	public void setSelectedNodeChildren(List<DomainTreeNode> selectedNodeChildren) {
//		this.selectedNodeChildren = selectedNodeChildren;
//	}

//	public TreeState getComponentState() {
//		return componentState; 
//	}

//	public void setComponentState(TreeState componentState) {
//		this.componentState = componentState;
//	}
	
	
	public void initialize(Handler<Object> selectionListener) {
		initialize(selectionListener, null);
	}

	public void initialize(Handler<Object> selectionListener, Object selectedArtifact) {
		this.selectionListener = selectionListener;
		//TODO this.selectedArtifact = selectedArtifact;
		//TODO configurationSelectRendered = false;
	}

	public void expand() {
	}

	public void expandAll() {
	}

	public void collapse() {
	}

	public void collapseAll() {
	}

	public Boolean adviseNodeOpened(UITree tree) {
		return Boolean.TRUE;
	}

	public Boolean adviseNodeSelected(UITree tree) {
		return Boolean.FALSE;
	}
	
	
	public void setToggledNodeState(String nodeId) {
		ModelTreeNode treeNode = (ModelTreeNode) getNodeById(nodeId);
		treeNode.setExpanded(!treeNode.isExpanded());
		//System.out.println("XXX");
		//TODO remember tree state
		//Preferences preferences = getUser().getPreferences();
		//Map<String, Boolean> openNodes = preferences.getOpenNodes();
		//openNodes.put(toggledNodeId, treeNode.isExpanded());
	}

	
//	public void nodeSelected(NodeSelectedEvent event) {
//		HtmlTree tree = (HtmlTree) event.getComponent();
//		try {
//			if (tree.getRowData() != null) {
//	//			selectedNodeChildren.clear();
//				log.info(">>> selected node: "+ tree.getRowData());
//				ModelTreeNode selectedNode = (ModelTreeNode) tree.getModelTreeNode(tree.getRowKey());
//				log.info(">>> selected type: "+selectedNode.getData().getType());
//				setSelectedNode(selectedNode);
//			}
//		} catch (Exception e) {
//			log.info("ERROR: "+ e.getMessage());
//		}
//	}
	
//	public void dropListener(DropEvent dropEvent) {
//		// resolve drag destination attributes
//		UITreeNode destNode = (dropEvent.getSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getSource() : null;
//		UITree destTree = destNode != null ? destNode.getUITree() : null;
//		TreeRowKey dropNodeKey = (dropEvent.getDropValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDropValue() : null;
//		TreeNode droppedInNode = dropNodeKey != null ? destTree.getTreeNode(dropNodeKey) : null;
//
//		// resolve drag source attributes
//		UITreeNode srcNode = (dropEvent.getDraggableSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getDraggableSource() : null;
//		UITree srcTree = srcNode != null ? srcNode.getUITree() : null;
//		
//		Object dragValue = dropEvent.getDragValue();
//		TreeRowKey dragNodeKey = (dragValue instanceof TreeRowKey) ? (TreeRowKey) dragValue : null;
//		TreeNode draggedNode = dragNodeKey != null ? srcTree.getTreeNode(dragNodeKey) : null;
//		if (dropEvent.getDraggableSource() instanceof UIDragSupport && srcTree == null && draggedNode == null && dragValue instanceof TreeNode) {        
//			srcTree = destTree;
//			draggedNode = (TreeNode) dragValue;
//			dragNodeKey = srcTree.getTreeNodeRowKey(draggedNode) instanceof TreeRowKey ? (TreeRowKey) srcTree.getTreeNodeRowKey(draggedNode) : null;
//		}
//		
//		// Note: check if we dropped node on to itself or to item instead of
//		// folder here
//		if (droppedInNode != null && droppedInNode.equals(draggedNode)) {
//			return;
//		}
//
//		FacesContext context = FacesContext.getCurrentInstance();
//
//		if (dropNodeKey != null) {
//			// add destination node for rerender
//			destTree.addRequestKey(dropNodeKey);
//
//			Object state = null;
//			if (dragNodeKey != null) { // Drag from this or other tree
//				TreeNode parentNode = draggedNode.getParent();
//				// 1. remove node from tree
//				state = srcTree.removeNode(dragNodeKey);
//				// 2. add parent for rerender
//				Object rowKey = srcTree.getTreeNodeRowKey(parentNode);
//				srcTree.addRequestKey(rowKey);
////				if (dropEvent.getDraggableSource() instanceof UIDragSupport) {
////					selectedNodeChildren.remove(draggedNode);
////					// if node was gragged in it's parent place dragged node to
////					// the end of selected nodes in grid
////					if (droppedInNode.equals(parentNode)) {
////						selectedNodeChildren.add((ModelTreeNode) draggedNode);
////					}
////				}
//			} else if (dragValue != null) { // Drag from some
//				// drag source
//				System.out.println(">>>"+dragValue);
//				//selectedModelTreeNode = (ModelTreeNode) droppedInNode;
//				ModelTreeObject object = new ModelTreeObject(dragValue, "Import");
//				draggedNode = new ModelTreeNode(object);
//				draggedNode.setParent(droppedInNode);
//			
//				ModelTreeObject modelObject = (ModelTreeObject) droppedInNode.getData();
//				//TODO Workspace modelInstance = (Workspace) modelObject.getObject();
//				//TODO Import importInstance = (Import) dragValue;
//				//TODO modelInstance.getImports().add(importInstance);
//				refresh();
//			}
//
//			// generate new node id
//			//Object id = getNewId(destTree.getTreeNode(dropNodeKey));
//			//destTree.addNode(dropNodeKey, draggedNode, id, state);
//		}
//
//		AjaxContext ac = AjaxContext.getCurrentInstance();
//		// Add destination tree to reRender
//		try {
//			ac.addComponentToAjaxRender(destTree);
//			//TODO don't hard-code this...
//			ac.addRenderedArea("pageForm");
//		} catch (Exception e) {
//			System.err.print(e.getMessage());
//		}
//
//	}

	
//	private Object getNewId(TreeNode parentNode) {
//		Map<Object, TreeNode> childs = new HashMap<Object, TreeNode>();
//		Iterator<Map.Entry<Object, TreeNode>> iter = parentNode.getChildren();
//		while (iter != null && iter.hasNext()) {
//			Map.Entry<Object, TreeNode> entry = iter.next();
//			childs.put(entry.getKey(), entry.getValue());
//		}
//
//		Integer index = 1;
//		while (childs.containsKey(index)) {
//			index++;
//		}
//		return index;
//	}

	
	
//	public void processExpansion(NodeExpandedEvent evt) {
//		Object source = evt.getSource();
//		if (source instanceof HtmlTreeNode) {
//			UITree tree = ((HtmlTreeNode) source).getUITree();
//			if (tree == null) {
//				return;
//			}
//			// get the row key i.e. id of the given node.
//			Object rowKey = tree.getRowKey();
//			// get the model node of this node.
//			TreeRowKey key = (TreeRowKey) tree.getRowKey();
//
//			TreeState state = (TreeState) tree.getComponentState();
//			if (state.isExpanded(key)) {
//				System.out.println(rowKey + " - expanded");
//			} else {
//				System.out.println(rowKey + " - collapsed");
//			}
//		}
//	}

	
	public WorkspaceManager getWorkspaceManager() {
		return getFromSession("workspaceManager");
	}
	
	protected SelectionContext getSelectionContext() {
		return getFromSession("selectionContext");
	}

//	protected EventMulticaster getEventMulticaster() {
//		return getFromSession("eventMulticaster");
//	}

	@SuppressWarnings("unchecked")
	public <Bean> Bean getFromSession(String beanName) {
		Bean bean = (Bean) BeanContext.getFromSession(beanName);
		return bean;
	}
	
	protected String getContextKey() {
		String contextId = getWorkspaceManager().getContextId();
		String contextKey = contextId + ".projects";
		return contextKey;
	}

}

