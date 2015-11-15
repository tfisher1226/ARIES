package admin.ui.registration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.Registration;
import admin.client.registrationService.RegistrationService;
import admin.util.RegistrationUtil;


@SessionScoped
@Named("registrationSelectManager")
public class RegistrationSelectManager extends AbstractSelectManager<Registration, RegistrationListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "RegistrationSelect";
	}
	
	@Override
	public String getTitle() {
		return "Registration Selection";
	}
	
	@Override
	protected Class<Registration> getRecordClass() {
		return Registration.class;
	}
	
	@Override
	public boolean isEmpty(Registration registration) {
		return getRegistrationHelper().isEmpty(registration);
	}
	
	@Override
	public String toString(Registration registration) {
		return getRegistrationHelper().toString(registration);
	}
	
	protected RegistrationHelper getRegistrationHelper() {
		return BeanContext.getFromSession("registrationHelper");
	}
	
	protected RegistrationService getRegistrationService() {
		return BeanContext.getFromSession(RegistrationService.ID);
	}
	
	protected RegistrationListManager getRegistrationListManager() {
		return BeanContext.getFromSession("registrationListManager");
	}
	
	@Override
	//SEAM @Observer("org.aries.ui.reset")
	public void initialize() {
		initializeContext(); 
		refreshRegistrationList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Registration> recordList) {
		RegistrationListManager registrationListManager = getRegistrationListManager();
		DataModel<RegistrationListObject> dataModel = registrationListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	//SEAM @Observer("org.aries.refreshRegistrationList")
	public void refreshRegistrationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<Registration> refreshRecords() {
		try {
			RegistrationService registrationService = getRegistrationService();
			List<Registration> records = registrationService.getRegistrationList();
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
	public void sortRecords(List<Registration> registrationList) {
		sortRecordsByUser(registrationList);
	}
	
	public void sortRecordsByUser(List<Registration> registrationList) {
		RegistrationUtil.sortRecordsByUser(registrationList);
	}
	
}
