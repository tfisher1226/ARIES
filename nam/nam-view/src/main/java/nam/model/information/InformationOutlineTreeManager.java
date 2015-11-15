package nam.model.information;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.tree.ModelTreeNode;

import org.richfaces.model.TreeNode;


@SessionScoped
@Named("informationOutlineTreeManager")
@SuppressWarnings("serial")
public class InformationOutlineTreeManager extends AbstractInformationTreeManager implements Serializable {

	private ModelTreeNode informationOutlineTreeRootNode;

	private ModelTreeNode informationOutlineTreeSelectedNode;

	protected InformationOutlineTreeBuilder informationOutlineTreeBuilder;

	
	public InformationOutlineTreeManager() {
		informationOutlineTreeBuilder = new InformationOutlineTreeBuilder();
	}

	@Override
	public String getTreeId() {
		return "informationOutlineTree";
	}

	public String getTitle() {
		return "Information View";
	}
	
	public String getSubTitle() {
		if (informationOutlineTreeSelectedNode != null)
			return informationOutlineTreeSelectedNode.getData().getLabel();
		return "Artifact List";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return informationOutlineTreeRootNode;
	}

	@Override
	public TreeNode getNodeById(String nodeId) {
		return informationOutlineTreeBuilder.getNodeById(nodeId);
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
		//TODO informationOutlineTreeRootNode = informationOutlineTreeBuilder.createTree(projects);
	}

}
