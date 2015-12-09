package admin.registration;

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

import admin.Registration;
import admin.util.RegistrationUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("registrationListManager")
public class RegistrationListManager extends AbstractDomainListManager<Registration, RegistrationListObject> implements Serializable {
	
	@Inject
	private RegistrationDataManager registrationDataManager;
	
	@Inject
	private RegistrationEventManager registrationEventManager;
	
	@Inject
	private RegistrationInfoManager registrationInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "registrationList";
	}
	
	@Override
	public String getTitle() {
		return "Registration List";
	}
	
	public Object getRecordId(Registration registration) {
		return registration.getId();
	}
	
	@Override
	public Object getRecordKey(Registration registration) {
		return RegistrationUtil.getKey(registration);
	}
	
	@Override
	public String getRecordName(Registration registration) {
		return RegistrationUtil.getLabel(registration);
	}
	
	@Override
	protected Class<Registration> getRecordClass() {
		return Registration.class;
	}
	
	@Override
	protected Registration getRecord(RegistrationListObject rowObject) {
		return rowObject.getRegistration();
	}
	
	@Override
	public Registration getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? RegistrationUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Registration registration) {
		super.setSelectedRecord(registration);
		fireSelectedEvent(registration);
	}
	
	protected void fireSelectedEvent(Registration registration) {
		registrationEventManager.fireSelectedEvent(registration);
	}
	
	public boolean isSelected(Registration registration) {
		Registration selection = selectionContext.getSelection("registration");
		boolean selected = selection != null && selection.equals(registration);
		return selected;
	}
	
	public boolean isChecked(Registration registration) {
		Collection<Registration> selection = selectionContext.getSelection("registrationSelection");
		boolean checked = selection != null && selection.contains(registration);
		return checked;
	}
	
	@Override
	protected RegistrationListObject createRowObject(Registration registration) {
		RegistrationListObject listObject = new RegistrationListObject(registration);
		listObject.setSelected(isSelected(registration));
		listObject.setChecked(isChecked(registration));
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
	protected Collection<Registration> createRecordList() {
		try {
			Collection<Registration> registrationList = registrationDataManager.getRegistrationList();
			if (registrationList != null)
			return registrationList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewRegistration() {
		return viewRegistration(selectedRecordKey);
	}
	
	public String viewRegistration(Object recordKey) {
		Registration registration = recordByKeyMap.get(recordKey);
		return viewRegistration(registration);
	}
	
	public String viewRegistration(Registration registration) {
		String url = registrationInfoManager.viewRegistration(registration);
		return url;
	}
	
	public String editRegistration() {
		return editRegistration(selectedRecordKey);
	}
	
	public String editRegistration(Object recordKey) {
		Registration registration = recordByKeyMap.get(recordKey);
		return editRegistration(registration);
	}
	
	public String editRegistration(Registration registration) {
		String url = registrationInfoManager.editRegistration(registration);
		return url;
	}
	
	public void removeRegistration() {
		removeRegistration(selectedRecordKey);
	}
	
	public void removeRegistration(Object recordKey) {
		Registration registration = recordByKeyMap.get(recordKey);
		removeRegistration(registration);
	}
	
	public void removeRegistration(Registration registration) {
		try {
			if (registrationDataManager.removeRegistration(registration))
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelRegistration(@Observes @Cancelled Registration registration) {
		try {
			//Object key = RegistrationUtil.getKey(registration);
			//recordByKeyMap.put(key, registration);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("registration");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateRegistration(Collection<Registration> registrationList) {
		return RegistrationUtil.validate(registrationList);
	}
	
	public void exportRegistrationList(@Observes @Export String tableId) {
		//String tableId = "pageForm:registrationListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
