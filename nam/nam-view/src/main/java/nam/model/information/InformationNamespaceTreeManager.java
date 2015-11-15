package nam.model.information;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.tree.ModelTreeNode;

import org.richfaces.model.TreeNode;


@SessionScoped
@Named("informationNamespaceTreeManager")
@SuppressWarnings("serial")
public class InformationNamespaceTreeManager extends AbstractInformationTreeManager implements Serializable {

	private ModelTreeNode informationNamespaceTreeRootNode;

	private ModelTreeNode informationNamespaceTreeSelectedNode;

	protected InformationNamespaceTreeBuilder informationNamespaceTreeBuilder;
	
	
	public InformationNamespaceTreeManager() {
		informationNamespaceTreeBuilder = new InformationNamespaceTreeBuilder();		
	}

	@Override
	public String getTreeId() {
		return "informationNamespaceTree";
	}

	public String getTitle() {
		return "Information View";
	}
	
	public String getSubTitle() {
		if (informationNamespaceTreeSelectedNode != null)
			return informationNamespaceTreeSelectedNode.getData().getLabel();
		return "Artifact List";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return informationNamespaceTreeRootNode;
	}

	@Override
	public TreeNode getNodeById(String nodeId) {
		return informationNamespaceTreeBuilder.getNodeById(nodeId);
	}

//	public boolean isNamespaceSelected() {
//		return isTypeSelected(Namespace.class);
//	}
//
//	public boolean isElementSelected() {
//		return isTypeSelected(Namespace.class);
//	}
//
//	public boolean isFieldSelected() {
//		return isTypeSelected(Field.class);
//	}
//
//	public boolean isAnnotationSelected() {
//		return isTypeSelected(Annotation.class);
//	}
	
	public String refresh() {
		return super.refresh();
	}
	
	@Override
	public void refreshModel() {
		//TODO informationNamespaceTreeRootNode = informationNamespaceTreeBuilder.createTree(projects);
	}

}
