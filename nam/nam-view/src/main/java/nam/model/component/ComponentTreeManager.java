package nam.model.component;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Configuration;
import nam.model.Module;
import nam.model.Project;
import nam.model.Service;
import nam.ui.design.AbstractDomainTreeManager;
import nam.ui.tree.ModelTreeNode;

import org.richfaces.model.TreeNode;


@SessionScoped
@Named("componentTreeManager")
@SuppressWarnings("serial")
public class ComponentTreeManager extends AbstractDomainTreeManager implements Serializable {

	private ModelTreeNode componentTreeRootNode;

	private ModelTreeNode componentTreeSelectedNode;

	private Object componentTreeSelectedObject;

	protected ComponentTreeBuilder componentTreeBuilder;

	
	public ComponentTreeManager() {
		componentTreeBuilder = new ComponentTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "componentTree";
	}

	public String getTitle() {
		if (componentTreeSelectedNode != null)
			return componentTreeSelectedNode.getData().getLabel();
		return "Component View";
	}
	
	public String getSubTitle() {
		if (componentTreeSelectedObject != null)
			return getSectionHeaderLabel(componentTreeSelectedObject);
		return "Components";
	}
	
	public static String getSectionHeaderLabel(Object object) {
		if (object instanceof Configuration)
			return ((Configuration) object).getArtifactId();
		if (object instanceof Application)
			return ((Application) object).getArtifactId();
		if (object instanceof Module)
			return ((Module) object).getArtifactId();
		if (object instanceof Service)
			return ((Service) object).getName();
		return "Component View";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return componentTreeRootNode;
	}

	@Override
	public TreeNode getNodeById(String nodeId) {
		return componentTreeBuilder.getNodeById(nodeId);
	}

	public void refresh(Object object) {
		componentTreeSelectedObject = object;
		refresh();
	}

	@Override
	public void refreshModel() {
		//List<Project> projectList = selectionContext.getSelection("projectList");
		//componentTreeRootNode = componentTreeBuilder.createTree(projects);
	}
	
//	public void nodeSelected(NodeSelectedEvent event) {
//		super.processTreeSelectionChange(event);
//		//modelTreeManager.refresh();
//	}

}

