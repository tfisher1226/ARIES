package nam.model.node;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Node;
import nam.model.util.NodeUtil;


@SessionScoped
@Named("nodeIdentificationSection")
public class NodeRecord_IdentificationSection extends AbstractWizardPage<Node> implements Serializable {
	
	private Node node;
	
	
	public NodeRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
