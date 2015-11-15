package nam.ui.workspace;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Import;
import nam.model.Project;
import nam.ui.design.AbstractDomainTreeManager;
import nam.ui.design.SelectionContext;
import nam.ui.tree.ModelTreeNode;

import org.aries.ui.event.Clear;
import org.aries.ui.event.Selected;
import org.richfaces.model.TreeNode;


@SessionScoped
@Named("modelFileTreeManager")
public class ModelFileTreeManager extends AbstractDomainTreeManager implements Serializable {

	private ModelTreeNode rootNode;

	private ModelTreeNode selectedNode;

	protected ModelFileTreeBuilder modelFileTreeBuilder;

	@Inject
	private SelectionContext selectionContext;

	@Inject
	@Selected
	private Event<Import> selectedEvent;

	@Inject
	@Clear
	private Event<Object> clearSelectionEvent;


	public ModelFileTreeManager() {
		modelFileTreeBuilder = new ModelFileTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "modelFileTree";
	}

	public String getTitle() {
		return "Model Organization";
	}
	
	public String getSubTitle() {
		if (selectedNode != null)
			return selectedNode.getData().getLabel();
		return "None";
	}
	
	@Override
	public TreeNode getNodeById(String nodeId) {
		return modelFileTreeBuilder.getNodeById(nodeId);
	}

	@Override
	public ModelTreeNode getRootNode() {
		return rootNode;
	}

	public ModelTreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode treeNode) {
		selectedNode = (ModelTreeNode) treeNode;
		super.setSelectedNode(treeNode);
		postNodeSelectedEvent();
		postClearSelectionEvent();
	}
	
	
	protected void postNodeSelectedEvent() {
		String type = selectedNode.getType();
		Object object = selectedNode.getData().getObject();
		if (type.equals("GROUP")) {
			postModelFileSelectedEvent((Import) object);
		}
	}
	
	protected void postModelFileSelectedEvent(Import importedFile) {
		selectedEvent.fire(importedFile);
	}
	
	protected void postClearSelectionEvent() {
		clearSelectionEvent.fire(getTreeId());
	}
	
	
	
//	public boolean getShowModuleTypeInLabels() {
//		return showModuleTypeInLabels;
//	}
//
//	public void setShowModuleTypeInLabels(boolean value) {
//		showModuleTypeInLabels = value;
//	}
//
//	public void toggleShowModuleTypeInLabels() {
//		setShowModuleTypeInLabels(!getShowModuleTypeInLabels());
//	}
	
	public String refresh() {
		return super.refresh();
	}
	
	@Override
	public void refreshModel() {
		List<Project> projectList = selectionContext.getSelection("projectList");
		rootNode = modelFileTreeBuilder.createTree(projectList);
	}

	public Import getSelectedModelFile() {
		if (selectedNode != null)
			return (Import) selectedNode.getData().getObject();
		return null;
	}
	
}

