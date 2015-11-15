package nam.model.persistence;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.tree.ModelTreeNode;

import org.richfaces.model.TreeNode;


@SessionScoped
@Named("persistenceOutlineTreeManager")
@SuppressWarnings("serial")
public class PersistenceOutlineTreeManager extends AbstractPersistenceTreeManager implements Serializable {

	private ModelTreeNode persistenceOutlineTreeRootNode;

	private ModelTreeNode persistenceOutlineTreeSelectedNode;

	protected PersistenceOutlineTreeBuilder persistenceOutlineTreeBuilder;


	public PersistenceOutlineTreeManager() {
		persistenceOutlineTreeBuilder = new PersistenceOutlineTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "persistenceOutlineTree";
	}

	public String getTitle() {
		return "Persistence View";
	}
	
	public String getSubTitle() {
		if (persistenceOutlineTreeSelectedNode != null)
			return persistenceOutlineTreeSelectedNode.getData().getLabel();
		return "Artifact List";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return persistenceOutlineTreeRootNode;
	}
	
	@Override
	public TreeNode getNodeById(String nodeId) {
		return persistenceOutlineTreeBuilder.getNodeById(nodeId);
	}

	@Override
	public void refreshModel() {
		//persistenceOutlineTreeRootNode = persistenceOutlineTreeBuilder.createTree(projects);
	}

}

