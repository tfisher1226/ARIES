package nam.model.adapter;

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

import nam.model.Adapter;
import nam.model.util.AdapterUtil;


@SessionScoped
@Named("adapterSelectManager")
public class AdapterSelectManager extends AbstractSelectManager<Adapter, AdapterListObject> implements Serializable {
	
	@Inject
	private AdapterDataManager adapterDataManager;
	
	@Inject
	private AdapterHelper adapterHelper;
	
	
	@Override
	public String getClientId() {
		return "adapterSelect";
	}
	
	@Override
	public String getTitle() {
		return "Adapter Selection";
	}
	
	@Override
	protected Class<Adapter> getRecordClass() {
		return Adapter.class;
	}
	
	@Override
	public boolean isEmpty(Adapter adapter) {
		return adapterHelper.isEmpty(adapter);
	}
	
	@Override
	public String toString(Adapter adapter) {
		return adapterHelper.toString(adapter);
	}
	
	protected AdapterHelper getAdapterHelper() {
		return BeanContext.getFromSession("adapterHelper");
	}
	
	protected AdapterListManager getAdapterListManager() {
		return BeanContext.getFromSession("adapterListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshAdapterList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Adapter> recordList) {
		AdapterListManager adapterListManager = getAdapterListManager();
		DataModel<AdapterListObject> dataModel = adapterListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshAdapterList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Adapter> refreshRecords() {
		try {
			Collection<Adapter> records = adapterDataManager.getAdapterList();
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
	public void sortRecords(List<Adapter> adapterList) {
		Collections.sort(adapterList, new Comparator<Adapter>() {
			public int compare(Adapter adapter1, Adapter adapter2) {
				String text1 = AdapterUtil.toString(adapter1);
				String text2 = AdapterUtil.toString(adapter2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
