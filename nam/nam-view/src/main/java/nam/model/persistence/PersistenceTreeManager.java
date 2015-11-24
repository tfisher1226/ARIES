package nam.model.persistence;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.EventManager;
import org.aries.ui.event.Added;
import org.aries.ui.event.Refresh;
import org.aries.ui.event.Removed;
import org.aries.util.NameUtil;
import org.richfaces.model.TreeNode;

import nam.model.Persistence;
import nam.model.Project;
import nam.model.util.PersistenceUtil;
import nam.ui.design.AbstractDomainTreeManager;
import nam.ui.design.SelectionContext;
import nam.ui.tree.ModelTreeNode;


@SessionScoped
@Named("persistenceTreeManager")
public class PersistenceTreeManager extends AbstractDomainTreeManager implements Serializable {
	
	private ModelTreeNode rootNode;
	
	private ModelTreeNode selectedNode;
	
	private PersistenceTreeBuilder persistenceTreeBuilder;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PersistenceTreeManager() {
		persistenceTreeBuilder = new PersistenceTreeBuilder();
	}
	
	
	@Override
	public String getTreeId() {
		return "persistenceTree";
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
		return persistenceTreeBuilder.getNodeById(nodeId);
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
		List<Project> projectList = selectionContext.getSelection("projectList");
		refreshModel(projectList);
	}
	
	public void refreshModel(List<Project> projectList) {
		rootNode = persistenceTreeBuilder.createTree(projectList);
	}
	
	public void handleAdded(@Observes @Added Persistence persistence) {
		refreshModel();
	}
	
	public void handleRemoved(@Observes @Removed Persistence persistence) {
		selectionContext.clearSelection("persistence");
		refreshModel();
	}
	
}