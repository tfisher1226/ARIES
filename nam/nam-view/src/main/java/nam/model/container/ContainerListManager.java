package nam.model.container;

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

import nam.model.Container;
import nam.model.util.ContainerUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("containerListManager")
public class ContainerListManager extends AbstractDomainListManager<Container, ContainerListObject> implements Serializable {
	
	@Inject
	private ContainerDataManager containerDataManager;
	
	@Inject
	private ContainerEventManager containerEventManager;
	
	@Inject
	private ContainerInfoManager containerInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "containerList";
	}
	
	@Override
	public String getTitle() {
		return "Container List";
	}
	
	@Override
	public Object getRecordKey(Container container) {
		return ContainerUtil.getKey(container);
	}
	
	@Override
	public String getRecordName(Container container) {
		return ContainerUtil.getLabel(container);
	}
	
	@Override
	protected Class<Container> getRecordClass() {
		return Container.class;
	}
	
	@Override
	protected Container getRecord(ContainerListObject rowObject) {
		return rowObject.getContainer();
	}
	
	@Override
	public Container getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ContainerUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Container container) {
		super.setSelectedRecord(container);
		fireSelectedEvent(container);
	}
	
	protected void fireSelectedEvent(Container container) {
		containerEventManager.fireSelectedEvent(container);
	}
	
	public boolean isSelected(Container container) {
		Container selection = selectionContext.getSelection("container");
		boolean selected = selection != null && selection.equals(container);
		return selected;
	}
	
	@Override
	protected ContainerListObject createRowObject(Container container) {
		ContainerListObject listObject = new ContainerListObject(container);
		listObject.setSelected(isSelected(container));
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
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Container> createRecordList() {
		try {
			Collection<Container> containerList = containerDataManager.getContainerList();
			if (containerList != null)
				return containerList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewContainer() {
		return viewContainer(selectedRecordKey);
	}
	
	public String viewContainer(Object recordKey) {
		Container container = recordByKeyMap.get(recordKey);
		return viewContainer(container);
	}
	
	public String viewContainer(Container container) {
		String url = containerInfoManager.viewContainer(container);
		return url;
	}
	
	public String editContainer() {
		return editContainer(selectedRecordKey);
	}
	
	public String editContainer(Object recordKey) {
		Container container = recordByKeyMap.get(recordKey);
		return editContainer(container);
	}
	
	public String editContainer(Container container) {
		String url = containerInfoManager.editContainer(container);
		return url;
	}
	
	public void removeContainer() {
		removeContainer(selectedRecordKey);
	}
	
	public void removeContainer(Object recordKey) {
		Container container = recordByKeyMap.get(recordKey);
		removeContainer(container);
	}
	
	public void removeContainer(Container container) {
		try {
			if (containerDataManager.removeContainer(container))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelContainer(@Observes @Cancelled Container container) {
		try {
			//Object key = ContainerUtil.getKey(container);
			//recordByKeyMap.put(key, container);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("container");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateContainer(Collection<Container> containerList) {
		return ContainerUtil.validate(containerList);
	}
	
	public void exportContainerList(@Observes @Export String tableId) {
		//String tableId = "pageForm:containerListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
