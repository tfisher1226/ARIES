package nam.model.listener;

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

import nam.model.Listener;
import nam.model.util.ListenerUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("listenerListManager")
public class ListenerListManager extends AbstractDomainListManager<Listener, ListenerListObject> implements Serializable {
	
	@Inject
	private ListenerDataManager listenerDataManager;
	
	@Inject
	private ListenerEventManager listenerEventManager;
	
	@Inject
	private ListenerInfoManager listenerInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "listenerList";
	}
	
	@Override
	public String getTitle() {
		return "Listener List";
	}
	
	@Override
	public Object getRecordKey(Listener listener) {
		return ListenerUtil.getKey(listener);
	}
	
	@Override
	public String getRecordName(Listener listener) {
		return ListenerUtil.getLabel(listener);
	}
	
	@Override
	protected Class<Listener> getRecordClass() {
		return Listener.class;
	}
	
	@Override
	protected Listener getRecord(ListenerListObject rowObject) {
		return rowObject.getListener();
	}
	
	@Override
	public Listener getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ListenerUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Listener listener) {
		super.setSelectedRecord(listener);
		fireSelectedEvent(listener);
	}
	
	protected void fireSelectedEvent(Listener listener) {
		listenerEventManager.fireSelectedEvent(listener);
	}
	
	public boolean isSelected(Listener listener) {
		Listener selection = selectionContext.getSelection("listener");
		boolean selected = selection != null && selection.equals(listener);
		return selected;
	}
	
	@Override
	protected ListenerListObject createRowObject(Listener listener) {
		ListenerListObject listObject = new ListenerListObject(listener);
		listObject.setSelected(isSelected(listener));
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
	protected Collection<Listener> createRecordList() {
		try {
			Collection<Listener> listenerList = listenerDataManager.getListenerList();
			if (listenerList != null)
				return listenerList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewListener() {
		return viewListener(selectedRecordKey);
	}
	
	public String viewListener(Object recordKey) {
		Listener listener = recordByKeyMap.get(recordKey);
		return viewListener(listener);
	}
	
	public String viewListener(Listener listener) {
		String url = listenerInfoManager.viewListener(listener);
		return url;
	}
	
	public String editListener() {
		return editListener(selectedRecordKey);
	}
	
	public String editListener(Object recordKey) {
		Listener listener = recordByKeyMap.get(recordKey);
		return editListener(listener);
	}
	
	public String editListener(Listener listener) {
		String url = listenerInfoManager.editListener(listener);
		return url;
	}
	
	public void removeListener() {
		removeListener(selectedRecordKey);
	}
	
	public void removeListener(Object recordKey) {
		Listener listener = recordByKeyMap.get(recordKey);
		removeListener(listener);
	}
	
	public void removeListener(Listener listener) {
		try {
			if (listenerDataManager.removeListener(listener))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelListener(@Observes @Cancelled Listener listener) {
		try {
			//Object key = ListenerUtil.getKey(listener);
			//recordByKeyMap.put(key, listener);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("listener");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateListener(Collection<Listener> listenerList) {
		return ListenerUtil.validate(listenerList);
	}
	
	public void exportListenerList(@Observes @Export String tableId) {
		//String tableId = "pageForm:listenerListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
