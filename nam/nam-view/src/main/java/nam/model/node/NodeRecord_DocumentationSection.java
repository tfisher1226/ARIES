package nam.model.node;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Node;
import nam.model.util.NodeUtil;


@SessionScoped
@Named("nodeDocumentationSection")
public class NodeRecord_DocumentationSection extends AbstractWizardPage<Node> implements Serializable {
	
	private Node node;
	
	
	public NodeRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
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
		setBackEnabled(true);
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
