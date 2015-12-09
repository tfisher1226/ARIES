package nam.model.application;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Project;
import nam.ui.design.AbstractDomainTreeManager;
import nam.ui.design.SelectionContext;
import nam.ui.tree.ModelTreeNode;

import org.aries.runtime.BeanContext;
import org.aries.ui.EventManager;
import org.aries.ui.event.Added;
import org.aries.ui.event.Refresh;
import org.aries.ui.event.Removed;
import org.aries.util.NameUtil;
import org.richfaces.model.TreeNode;


@SessionScoped
@Named("applicationTreeManager")
public class ApplicationTreeManager extends AbstractDomainTreeManager implements Serializable {

	private ModelTreeNode rootNode;

	private ModelTreeNode selectedNode;

	private ApplicationTreeBuilder applicationTreeBuilder;

	@Inject
	private SelectionContext selectionContext;

	
	public ApplicationTreeManager() {
		applicationTreeBuilder = new ApplicationTreeBuilder();
	}
	
	
	@Override
	public String getTreeId() {
		return "applicationTree";
	}

	@Override
	public ModelTreeNode getRootNode() {
		return rootNode;
	}

	@Override
	public ModelTreeNode getSelectedNode() {
		return selectedNode;
	}
	
	@Override
	public void setSelectedNode(TreeNode treeNode) {
		selectedNode = (ModelTreeNode) treeNode;
		selectionContext.setSelectedType(selectedNode.getType());
		Object object = selectedNode.getData().getObject();
		super.setSelectedNode(selectedNode);
		fireSelectionEvent(selectedNode);
	}

	@Override
	public String getSelectedNodeName() {
		if (selectedNode != null)
			return NameUtil.capName(selectedNode.getLabel() + " " + selectedNode.getType());
		return null;
	}

	@Override
	public TreeNode getNodeById(String nodeId) {
		return applicationTreeBuilder.getNodeById(nodeId);
	}

	public EventManager<?> getEventManager(String elementType) {
		String elementTypeUncapped = NameUtil.uncapName(elementType);
		EventManager<?> eventManager = BeanContext.getFromSession(elementTypeUncapped + "EventManager");
		return eventManager;
	}
	
	public void fireSelectionEvent(ModelTreeNode treeNode) {
		Object object = treeNode.getData().getObject();
		if (object != null) {
			Class<?> classObject = object.getClass();
			String type = classObject.getSimpleName();

			EventManager<?> eventManager = getEventManager(type);
			eventManager.fireSelectedEvent(object);
		
			ModelTreeNode parentNode = (ModelTreeNode) treeNode.getParent();
			if (parentNode != null) {
				//fire selection event
				fireSelectionEvent(parentNode);
			}
		}
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//List<Project> projectList = selectionContext.getSelection("projectList");
		//refreshModel(projectList);
		//clearSelection();
	}
	
	public void handleRefresh(@Observes @Refresh List<Project> projectList) {
		refreshModel(projectList);
		clearSelection();
	}
	
	public void handleRefresh(@Observes @Refresh Project project) {
		List<Project> projectList = selectionContext.getSelection("projectList");
		refreshModel(projectList);
		clearSelection();
	}
	
	@Override
	public void refreshModel() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		refreshModel(projectList);
	}

	public void refreshModel(Collection<Project> projectList) {
		rootNode = applicationTreeBuilder.createTree(projectList);
	}

	public void handleAdded(@Observes @Added Application application) {
		refreshModel();
	}
	
	public void handleRemoved(@Observes @Removed Application application) {
		selectionContext.clearSelection("application");
		refreshModel();
	}

}
