package nam.model.node;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Node;
import nam.model.util.NodeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("nodeListManager")
public class NodeListManager extends AbstractDomainListManager<Node, NodeListObject> implements Serializable {
	
	@Inject
	private NodeDataManager nodeDataManager;
	
	@Inject
	private NodeEventManager nodeEventManager;
	
	@Inject
	private NodeInfoManager nodeInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "nodeList";
	}
	
	@Override
	public String getTitle() {
		return "Node List";
	}
	
	@Override
	public Object getRecordKey(Node node) {
		return NodeUtil.getKey(node);
	}
	
	@Override
	public String getRecordName(Node node) {
		return NodeUtil.toString(node);
	}
	
	@Override
	protected Class<Node> getRecordClass() {
		return Node.class;
	}
	
	@Override
	protected Node getRecord(NodeListObject rowObject) {
		return rowObject.getNode();
	}
	
	@Override
	public Node getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? NodeUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Node node) {
		super.setSelectedRecord(node);
		fireSelectedEvent(node);
	}
	
	protected void fireSelectedEvent(Node node) {
		nodeEventManager.fireSelectedEvent(node);
	}
	
	public boolean isSelected(Node node) {
		Node selection = selectionContext.getSelection("node");
		boolean selected = selection != null && selection.equals(node);
		return selected;
	}
	
	@Override
	protected NodeListObject createRowObject(Node node) {
		NodeListObject listObject = new NodeListObject(node);
		listObject.setSelected(isSelected(node));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Node> createRecordList() {
		try {
			Collection<Node> nodeList = nodeDataManager.getNodeList();
			if (nodeList != null)
				return nodeList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewNode() {
		return viewNode(selectedRecordKey);
	}
	
	public String viewNode(Object recordKey) {
		Node node = recordByKeyMap.get(recordKey);
		return viewNode(node);
	}
	
	public String viewNode(Node node) {
		String url = nodeInfoManager.viewNode(node);
		return url;
	}
	
	public String editNode() {
		return editNode(selectedRecordKey);
	}
	
	public String editNode(Object recordKey) {
		Node node = recordByKeyMap.get(recordKey);
		return editNode(node);
	}
	
	public String editNode(Node node) {
		String url = nodeInfoManager.editNode(node);
		return url;
	}
	
	public void removeNode() {
		removeNode(selectedRecordKey);
	}
	
	public void removeNode(Object recordKey) {
		Node node = recordByKeyMap.get(recordKey);
		removeNode(node);
	}
	
	public void removeNode(Node node) {
		try {
			if (nodeDataManager.removeNode(node))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelNode(@Observes @Cancelled Node node) {
		try {
			//Object key = NodeUtil.getKey(node);
			//recordByKeyMap.put(key, node);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("node");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateNode(Collection<Node> nodeList) {
		return NodeUtil.validate(nodeList);
	}
	
	public void exportNodeList(@Observes @Export String tableId) {
		//String tableId = "pageForm:nodeListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
