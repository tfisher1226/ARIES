package nam.model.listener;

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

import nam.model.Listener;
import nam.model.util.ListenerUtil;


@SessionScoped
@Named("listenerSelectManager")
public class ListenerSelectManager extends AbstractSelectManager<Listener, ListenerListObject> implements Serializable {
	
	@Inject
	private ListenerDataManager listenerDataManager;
	
	@Inject
	private ListenerHelper listenerHelper;
	
	
	@Override
	public String getClientId() {
		return "listenerSelect";
	}
	
	@Override
	public String getTitle() {
		return "Listener Selection";
	}
	
	@Override
	protected Class<Listener> getRecordClass() {
		return Listener.class;
	}
	
	@Override
	public boolean isEmpty(Listener listener) {
		return listenerHelper.isEmpty(listener);
	}
	
	@Override
	public String toString(Listener listener) {
		return listenerHelper.toString(listener);
	}
	
	protected ListenerHelper getListenerHelper() {
		return BeanContext.getFromSession("listenerHelper");
	}
	
	protected ListenerListManager getListenerListManager() {
		return BeanContext.getFromSession("listenerListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshListenerList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Listener> recordList) {
		ListenerListManager listenerListManager = getListenerListManager();
		DataModel<ListenerListObject> dataModel = listenerListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshListenerList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Listener> refreshRecords() {
		try {
			Collection<Listener> records = listenerDataManager.getListenerList();
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
	public void sortRecords(List<Listener> listenerList) {
		Collections.sort(listenerList, new Comparator<Listener>() {
			public int compare(Listener listener1, Listener listener2) {
				String text1 = ListenerUtil.toString(listener1);
				String text2 = ListenerUtil.toString(listener2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
