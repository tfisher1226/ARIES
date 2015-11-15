package nam.model.node;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Node;
import nam.model.Project;
import nam.model.util.NodeUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("nodeWizard")
@SuppressWarnings("serial")
public class NodeWizard extends AbstractDomainElementWizard<Node> implements Serializable {
	
	@Inject
	private NodeDataManager nodeDataManager;
	
	@Inject
	private NodePageManager nodePageManager;
	
	@Inject
	private NodeEventManager nodeEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Node";
	}
	
	@Override
	public String getUrlContext() {
		return nodePageManager.getNodeWizardPage();
	}
	
	@Override
	public void initialize(Node node) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(nodePageManager.getSections());
		super.initialize(node);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		nodePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		nodePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		nodePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		nodePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Node node = getInstance();
		nodeDataManager.saveNode(node);
		nodeEventManager.fireSavedEvent(node);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Node node = getInstance();
		//TODO take this out soon
		if (node == null)
			node = new Node();
		nodeEventManager.fireCancelledEvent(node);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Node node = selectionContext.getSelection("node");
		String name = node.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("nodeWizard");
			display.error("Node name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
