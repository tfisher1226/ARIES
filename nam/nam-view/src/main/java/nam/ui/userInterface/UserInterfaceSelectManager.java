package nam.ui.userInterface;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.ui.UserInterface;
import nam.ui.util.UserInterfaceUtil;


@SessionScoped
@Named("userInterfaceSelectManager")
public class UserInterfaceSelectManager extends AbstractSelectManager<UserInterface, UserInterfaceListObject> implements Serializable {
	
	@Inject
	private UserInterfaceDataManager userInterfaceDataManager;
	
	
	@Override
	public String getClientId() {
		return "userInterfaceSelect";
	}
	
	@Override
	public String getTitle() {
		return "UserInterface Selection";
	}
	
	@Override
	protected Class<UserInterface> getRecordClass() {
		return UserInterface.class;
	}
	
	@Override
	public boolean isEmpty(UserInterface userInterface) {
		return getUserInterfaceHelper().isEmpty(userInterface);
	}
	
	@Override
	public String toString(UserInterface userInterface) {
		return getUserInterfaceHelper().toString(userInterface);
	}
	
	protected UserInterfaceHelper getUserInterfaceHelper() {
		return BeanContext.getFromSession("userInterfaceHelper");
	}
	
	protected UserInterfaceListManager getUserInterfaceListManager() {
		return BeanContext.getFromSession("userInterfaceListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshUserInterfaceList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<UserInterface> recordList) {
		UserInterfaceListManager userInterfaceListManager = getUserInterfaceListManager();
		DataModel<UserInterfaceListObject> dataModel = userInterfaceListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshUserInterfaceList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<UserInterface> refreshRecords() {
		try {
			Collection<UserInterface> records = userInterfaceDataManager.getUserInterfaceList();
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
	public void sortRecords(List<UserInterface> userInterfaceList) {
		Collections.sort(userInterfaceList, new Comparator<UserInterface>() {
			public int compare(UserInterface userInterface1, UserInterface userInterface2) {
				String text1 = UserInterfaceUtil.toString(userInterface1);
				String text2 = UserInterfaceUtil.toString(userInterface2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
