package nam.model.node;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Node;
import nam.model.util.NodeUtil;


@SessionScoped
@Named("nodeSelectManager")
public class NodeSelectManager extends AbstractSelectManager<Node, NodeListObject> implements Serializable {
	
	@Inject
	private NodeDataManager nodeDataManager;
	
	@Inject
	private NodeHelper nodeHelper;
	
	
	@Override
	public String getClientId() {
		return "nodeSelect";
	}
	
	@Override
	public String getTitle() {
		return "Node Selection";
	}
	
	@Override
	protected Class<Node> getRecordClass() {
		return Node.class;
	}
	
	@Override
	public boolean isEmpty(Node node) {
		return nodeHelper.isEmpty(node);
	}
	
	@Override
	public String toString(Node node) {
		return nodeHelper.toString(node);
	}
	
	protected NodeHelper getNodeHelper() {
		return BeanContext.getFromSession("nodeHelper");
	}
	
	protected NodeListManager getNodeListManager() {
		return BeanContext.getFromSession("nodeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshNodeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Node> recordList) {
		NodeListManager nodeListManager = getNodeListManager();
		DataModel<NodeListObject> dataModel = nodeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshNodeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Node> refreshRecords() {
		try {
			Collection<Node> records = nodeDataManager.getNodeList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Node> nodeList) {
		Collections.sort(nodeList, new Comparator<Node>() {
			public int compare(Node node1, Node node2) {
				String text1 = NodeUtil.toString(node1);
				String text2 = NodeUtil.toString(node2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
