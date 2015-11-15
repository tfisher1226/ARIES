package nam.model.persistence;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.tree.ModelTreeNode;

import org.richfaces.model.TreeNode;


@SessionScoped
@Named("persistenceNamespaceTreeManager")
@SuppressWarnings("serial")
public class PersistenceNamespaceTreeManager extends AbstractPersistenceTreeManager implements Serializable {

	private ModelTreeNode persistenceNamespaceTreeRootNode;

	private ModelTreeNode persistenceNamespaceTreeSelectedNode;

	protected PersistenceNamespaceTreeBuilder persistenceNamespaceTreeBuilder;

	
	public PersistenceNamespaceTreeManager() {
		persistenceNamespaceTreeBuilder = new PersistenceNamespaceTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "persistenceNamespaceTree";
	}

	public String getTitle() {
		return "Persistence View";
	}
	
	public String getSubTitle() {
		if (persistenceNamespaceTreeSelectedNode != null)
			return persistenceNamespaceTreeSelectedNode.getData().getLabel();
		return "Artifact List";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return persistenceNamespaceTreeRootNode;
	}
	
	@Override
	public TreeNode getNodeById(String nodeId) {
		return persistenceNamespaceTreeBuilder.getNodeById(nodeId);
	}

	@Override
	public void refreshModel() {
		//persistenceNamespaceTreeRootNode = persistenceNamespaceTreeBuilder.createTree(projects);
	}

}

