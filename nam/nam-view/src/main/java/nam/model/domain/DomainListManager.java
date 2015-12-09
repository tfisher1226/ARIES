package nam.model.domain;

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

import nam.model.Domain;
import nam.model.util.DomainUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("domainListManager")
public class DomainListManager extends AbstractDomainListManager<Domain, DomainListObject> implements Serializable {
	
	@Inject
	private DomainDataManager domainDataManager;
	
	@Inject
	private DomainEventManager domainEventManager;
	
	@Inject
	private DomainInfoManager domainInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "domainList";
	}
	
	@Override
	public String getTitle() {
		return "Domain List";
	}
	
	@Override
	public Object getRecordKey(Domain domain) {
		return DomainUtil.getKey(domain);
	}
	
	@Override
	public String getRecordName(Domain domain) {
		return DomainUtil.getLabel(domain);
	}
	
	@Override
	protected Class<Domain> getRecordClass() {
		return Domain.class;
	}
	
	@Override
	protected Domain getRecord(DomainListObject rowObject) {
		return rowObject.getDomain();
	}
	
	@Override
	public Domain getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? DomainUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Domain domain) {
		super.setSelectedRecord(domain);
		fireSelectedEvent(domain);
	}
	
	protected void fireSelectedEvent(Domain domain) {
		domainEventManager.fireSelectedEvent(domain);
	}
	
	public boolean isSelected(Domain domain) {
		Domain selection = selectionContext.getSelection("domain");
		boolean selected = selection != null && selection.equals(domain);
		return selected;
	}
	
	public boolean isChecked(Domain domain) {
		Collection<Domain> selection = selectionContext.getSelection("domainSelection");
		boolean checked = selection != null && selection.contains(domain);
		return checked;
	}
	
	@Override
	protected DomainListObject createRowObject(Domain domain) {
		DomainListObject listObject = new DomainListObject(domain);
		listObject.setSelected(isSelected(domain));
		listObject.setChecked(isChecked(domain));
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
	protected Collection<Domain> createRecordList() {
		try {
			Collection<Domain> domainList = domainDataManager.getDomainList();
			if (domainList != null)
				return domainList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewDomain() {
		return viewDomain(selectedRecordKey);
	}
	
	public String viewDomain(Object recordKey) {
		Domain domain = recordByKeyMap.get(recordKey);
		return viewDomain(domain);
	}
	
	public String viewDomain(Domain domain) {
		String url = domainInfoManager.viewDomain(domain);
		return url;
	}
	
	public String editDomain() {
		return editDomain(selectedRecordKey);
	}
	
	public String editDomain(Object recordKey) {
		Domain domain = recordByKeyMap.get(recordKey);
		return editDomain(domain);
	}
	
	public String editDomain(Domain domain) {
		String url = domainInfoManager.editDomain(domain);
		return url;
	}
	
	public void removeDomain() {
		removeDomain(selectedRecordKey);
	}
	
	public void removeDomain(Object recordKey) {
		Domain domain = recordByKeyMap.get(recordKey);
		removeDomain(domain);
	}
	
	public void removeDomain(Domain domain) {
		try {
			if (domainDataManager.removeDomain(domain))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelDomain(@Observes @Cancelled Domain domain) {
		try {
			//Object key = DomainUtil.getKey(domain);
			//recordByKeyMap.put(key, domain);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("domain");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateDomain(Collection<Domain> domainList) {
		return DomainUtil.validate(domainList);
	}
	
	public void exportDomainList(@Observes @Export String tableId) {
		//String tableId = "pageForm:domainListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
