package nam.ui.userInterfaceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.ui.UserInterfaceType;


@SessionScoped
@Named("userInterfaceTypeSelectManager")
public class UserInterfaceTypeSelectManager extends AbstractSelectManager<UserInterfaceType, UserInterfaceTypeListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "userInterfaceTypeSelect";
	}
	
	@Override
	public String getTitle() {
		return "UserInterfaceType Selection";
	}
	
	@Override
	protected Class<UserInterfaceType> getRecordClass() {
		return UserInterfaceType.class;
	}
	
	@Override
	public String toString(UserInterfaceType userInterfaceType) {
		return userInterfaceType.name();
	}
	
	protected UserInterfaceTypeHelper getUserInterfaceTypeHelper() {
		return BeanContext.getFromSession("userInterfaceTypeHelper");
	}
	
	protected UserInterfaceTypeListManager getUserInterfaceTypeListManager() {
		return BeanContext.getFromSession("userInterfaceTypeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshUserInterfaceTypeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<UserInterfaceType> recordList) {
		UserInterfaceTypeListManager userInterfaceTypeListManager = getUserInterfaceTypeListManager();
		DataModel<UserInterfaceTypeListObject> dataModel = userInterfaceTypeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshUserInterfaceTypeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<UserInterfaceType> refreshRecords() {
		UserInterfaceType[] values = UserInterfaceType.values();
		List<UserInterfaceType> masterList = new ArrayList<UserInterfaceType>();
		for (UserInterfaceType capability : values) {
			masterList.add(capability);
		}
		return masterList;
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
	public void sortRecords(List<UserInterfaceType> userInterfaceTypeList) {
		Collections.sort(userInterfaceTypeList);
	}
	
}
