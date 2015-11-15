package nam.model.configuration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.design.AbstractDomainTreeManager;
import nam.ui.tree.ModelTreeNode;

import org.aries.Assert;
import org.richfaces.model.TreeNode;


@SessionScoped
@Named("configurationTreeManager")
@SuppressWarnings("serial")
public class ConfigurationTreeManager extends AbstractDomainTreeManager implements Serializable {

	private ModelTreeNode configurationTreeRootNode;

	private ModelTreeNode configurationTreeSelectedNode;

	protected ConfigurationTreeBuilder configurationTreeBuilder;

	private String rerenderList;


	public ConfigurationTreeManager() {
		configurationTreeBuilder = new ConfigurationTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "configurationTree";
	}

	@Override
	public ModelTreeNode getRootNode() {
		return configurationTreeRootNode;
	}

	@Override
	public TreeNode getNodeById(String nodeId) {
		return configurationTreeBuilder.getNodeById(nodeId);
	}

	public String getRerenderList() {
		return rerenderList;
	}

	public void setRerenderList(String rerenderList) {
		this.rerenderList = rerenderList;
	}

	
	@Override
	public void refreshModel() {
		//TODO ModelTreeNode rootNode = configurationTreeBuilder.createTree(projects);
		//TODO configurationTreeRootNode = rootNode;
	}
	
	public String saveSelection() {
		if (configurationTreeSelectedNode != null) {
			Assert.notNull(configurationTreeSelectedNode, "An configuration must be selected");
			//ModelTreeObject<?> data = configurationTreeSelectedNode.getData();
			//TODO Configuration configuration = (Configuration) data.getObject();
			//TODO selectionListener.handle(configuration);
		}
		return null;
	}

}

