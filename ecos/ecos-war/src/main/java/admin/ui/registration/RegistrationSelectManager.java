package admin.ui.registration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Registration;
import admin.client.registrationService.RegistrationService;
import admin.util.RegistrationUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("registrationSelectManager")
@Scope(ScopeType.SESSION)
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
		return BeanContext.getFromSession("mainRegistrationListManager");
	}
	
	@Override
	//@Observer("org.aries.ui.reset")
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
	
	@Observer("org.aries.refreshRegistrationList")
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
