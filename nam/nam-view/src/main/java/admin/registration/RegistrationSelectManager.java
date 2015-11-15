package admin.registration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.Registration;
import admin.client.registrationService.RegistrationService;
import admin.util.RegistrationUtil;


@SessionScoped
@Named("registrationSelectManager")
public class RegistrationSelectManager extends AbstractSelectManager<Registration, RegistrationListObject> implements Serializable {
	
	@Inject
	private RegistrationDataManager registrationDataManager;
	
	@Inject
	private RegistrationHelper registrationHelper;
	
	
	@Override
	public String getClientId() {
		return "registrationSelect";
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
		return registrationHelper.isEmpty(registration);
	}
	
	@Override
	public String toString(Registration registration) {
		return registrationHelper.toString(registration);
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
	
	public void refreshRegistrationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Registration> refreshRecords() {
		try {
			Collection<Registration> records = registrationDataManager.getRegistrationList();
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
