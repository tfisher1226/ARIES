package nam.model.node;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Node;
import nam.model.util.NodeUtil;


@SessionScoped
@Named("nodeOverviewSection")
public class NodeRecord_OverviewSection extends AbstractWizardPage<Node> implements Serializable {
	
	private Node node;
	
	
	public NodeRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Node getNode() {
		return node;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	@Override
	public void initialize(Node node) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setNode(node);
	}
	
	@Override
	public void validate() {
		if (node == null) {
			validator.missing("Node");
		} else {
		}
	}
	
}
