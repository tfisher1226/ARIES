package admin.ui.registration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Registration;
import admin.client.registrationService.RegistrationService;
import admin.util.RegistrationUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("registrationListManager")
@Scope(ScopeType.SESSION)
@Role(name = "mainRegistrationListManager", scope = ScopeType.SESSION)
public class RegistrationListManager extends AbstractTabListManager<Registration, RegistrationListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "RegistrationList";
	}
	
	@Override
	public String getTitle() {
		return "Registration List";
	}
	
	@Override
	public Object getRecordId(Registration registration) {
		return registration.getId();
	}
	
	@Override
	public String getRecordName(Registration registration) {
		return RegistrationUtil.toString(registration);
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
	protected RegistrationListObject createRowObject(Registration registration) {
		return new RegistrationListObject(registration);
	}
	
	protected RegistrationHelper getRegistrationHelper() {
		return BeanContext.getFromSession("registrationHelper");
	}
	
	protected RegistrationService getRegistrationService() {
		return BeanContext.getFromSession(RegistrationService.ID);
	}
	
	protected RegistrationInfoManager getRegistrationInfoManager() {
		return BeanContext.getFromSession("registrationInfoManager");
	}
	
	@Override
	//@Observer("org.aries.ui.reset")
	public void reset() {
		//refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	@Observer("org.aries.refreshRegistrationList")
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected List<Registration> createRecordList() {
		try {
			List<Registration> registrationList = getRegistrationService().getRegistrationList();
			return registrationList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void editRegistration() {
		editRegistration(selectedRecordId);
	}
	
	public void editRegistration(String recordId) {
		editRegistration(Long.parseLong(recordId));
	}
	
	public void editRegistration(Long recordId) {
		Registration registration = recordByIdMap.get(recordId);
		editRegistration(registration);
	}
	
	public void editRegistration(Registration registration) {
		RegistrationInfoManager registrationInfoManager = BeanContext.getFromSession("registrationInfoManager");
		registrationInfoManager.editRegistration(registration);
	}
	
	@Observer("org.aries.removeRegistration")
	public void removeRegistration() {
		removeRegistration(selectedRecordId);
	}
	
	public void removeRegistration(String recordId) {
		removeRegistration(Long.parseLong(recordId));
	}
	
	public void removeRegistration(Long recordId) {
		Registration registration = recordByIdMap.get(recordId);
		removeRegistration(registration);
	}
	
	public void removeRegistration(Registration registration) {
		try {
			getRegistrationService().deleteRegistration(selectedRecord);
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Observer("org.aries.cancelRegistration")
	public void cancelRegistration() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("registration");
			Long id = selectedRecord.getId();
			Registration registration = getRegistrationService().getRegistrationById(id);
			recordByIdMap.put(id, registration);
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateRegistration(Collection<Registration> registrationList) {
		return RegistrationUtil.validate(registrationList);
	}
	
//	@Observer("org.aries.exportRegistrationList")
//	public void exportRegistrationList() {
//		String tableId = "pageForm:registrationListTable";
//		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
//		exportManager.exportToXLS(tableId);
//	}
	
}