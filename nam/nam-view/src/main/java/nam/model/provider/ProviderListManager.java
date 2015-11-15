package nam.model.provider;

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

import nam.model.Provider;
import nam.model.util.ProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("providerListManager")
public class ProviderListManager extends AbstractDomainListManager<Provider, ProviderListObject> implements Serializable {
	
	@Inject
	private ProviderDataManager providerDataManager;
	
	@Inject
	private ProviderEventManager providerEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "providerList";
	}
	
	@Override
	public String getTitle() {
		return "Provider List";
	}
	
	@Override
	public Object getRecordKey(Provider provider) {
		return ProviderUtil.getKey(provider);
	}
	
	@Override
	public String getRecordName(Provider provider) {
		return ProviderUtil.toString(provider);
	}
	
	@Override
	protected Class<Provider> getRecordClass() {
		return Provider.class;
	}
	
	@Override
	protected Provider getRecord(ProviderListObject rowObject) {
		return rowObject.getProvider();
	}
	
	@Override
	public Provider getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ProviderUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Provider provider) {
		super.setSelectedRecord(provider);
		fireSelectedEvent(provider);
	}
	
	protected void fireSelectedEvent(Provider provider) {
		providerEventManager.fireSelectedEvent(provider);
	}
	
	public boolean isSelected(Provider provider) {
		Provider selection = selectionContext.getSelection("provider");
		boolean selected = selection != null && selection.equals(provider);
		return selected;
	}
	
	@Override
	protected ProviderListObject createRowObject(Provider provider) {
		ProviderListObject listObject = new ProviderListObject(provider);
		listObject.setSelected(isSelected(provider));
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
	protected Collection<Provider> createRecordList() {
		try {
			Collection<Provider> providerList = providerDataManager.getProviderList();
			if (providerList != null)
				return providerList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewProvider() {
		return viewProvider(selectedRecordKey);
	}
	
	public String viewProvider(Object recordKey) {
		Provider provider = recordByKeyMap.get(recordKey);
		return viewProvider(provider);
	}
	
	public String viewProvider(Provider provider) {
		ProviderInfoManager providerInfoManager = BeanContext.getFromSession("providerInfoManager");
		String url = providerInfoManager.viewProvider(provider);
		return url;
	}
	
	public String editProvider() {
		return editProvider(selectedRecordKey);
	}
	
	public String editProvider(Object recordKey) {
		Provider provider = recordByKeyMap.get(recordKey);
		return editProvider(provider);
	}
	
	public String editProvider(Provider provider) {
		ProviderInfoManager providerInfoManager = BeanContext.getFromSession("providerInfoManager");
		String url = providerInfoManager.editProvider(provider);
		return url;
	}
	
	public void removeProvider() {
		removeProvider(selectedRecordKey);
	}
	
	public void removeProvider(Object recordKey) {
		Provider provider = recordByKeyMap.get(recordKey);
		removeProvider(provider);
	}
	
	public void removeProvider(Provider provider) {
		try {
			if (providerDataManager.removeProvider(provider))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelProvider(@Observes @Cancelled Provider provider) {
		try {
			//Object key = ProviderUtil.getKey(provider);
			//recordByKeyMap.put(key, provider);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("provider");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateProvider(Collection<Provider> providerList) {
		return ProviderUtil.validate(providerList);
	}
	
	public void exportProviderList(@Observes @Export String tableId) {
		//String tableId = "pageForm:providerListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
