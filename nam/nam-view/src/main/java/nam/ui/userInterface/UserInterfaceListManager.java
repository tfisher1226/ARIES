package nam.ui.userInterface;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import nam.model.Application;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ProjectUtil;
import nam.ui.UserInterface;
import nam.ui.design.SelectionContext;
import nam.ui.util.UserInterfaceUtil;

import org.aries.RuntimeContext;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import aries.generation.engine.GenerationContext;


@SessionScoped
@Named("userInterfaceListManager")
public class UserInterfaceListManager extends AbstractDomainListManager<UserInterface, UserInterfaceListObject> implements Serializable {
	
	@Inject
	private UserInterfaceDataManager userInterfaceDataManager;
	
	@Inject
	private UserInterfaceEventManager userInterfaceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "userInterfaceList";
	}
	
	@Override
	public String getTitle() {
		return "UserInterface List";
	}
	
	@Override
	public Object getRecordKey(UserInterface userInterface) {
		return UserInterfaceUtil.getKey(userInterface);
	}
	
	@Override
	public String getRecordName(UserInterface userInterface) {
		return UserInterfaceUtil.toString(userInterface);
	}
	
	@Override
	protected Class<UserInterface> getRecordClass() {
		return UserInterface.class;
	}
	
	@Override
	protected UserInterface getRecord(UserInterfaceListObject rowObject) {
		return rowObject.getUserInterface();
	}
	
	@Override
	public UserInterface getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? UserInterfaceUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(UserInterface userInterface) {
		super.setSelectedRecord(userInterface);
		fireSelectedEvent(userInterface);
	}
	
	protected void fireSelectedEvent(UserInterface userInterface) {
		userInterfaceEventManager.fireSelectedEvent(userInterface);
	}
	
	public boolean isSelected(UserInterface userInterface) {
		UserInterface selection = selectionContext.getSelection("userInterface");
		boolean selected = selection != null && selection.equals(userInterface);
		return selected;
	}
	
	@Override
	protected UserInterfaceListObject createRowObject(UserInterface userInterface) {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Application application = UserInterfaceUtil.getApplication(projectList, userInterface);
		UserInterfaceListObject listObject = new UserInterfaceListObject(application, userInterface);
		listObject.setSelected(isSelected(userInterface));
		return listObject;
	}
	
	protected GenerationContext getContext() {
		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		String contextId = (String) servletContext.getAttribute("contextId");
		GenerationContext context = BeanContext.getFromSession(contextId + ".context");
		return context;
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
	protected Collection<UserInterface> createRecordList() {
		try {
			Collection<UserInterface> userInterfaceList = userInterfaceDataManager.getUserInterfaceList();
			if (userInterfaceList != null)
				return userInterfaceList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewUserInterface() {
		return viewUserInterface(selectedRecordKey);
	}
	
	public String viewUserInterface(Object recordKey) {
		UserInterface userInterface = recordByKeyMap.get(recordKey);
		return viewUserInterface(userInterface);
	}
	
	public String viewUserInterface(UserInterface userInterface) {
		UserInterfaceInfoManager userInterfaceInfoManager = BeanContext.getFromSession("userInterfaceInfoManager");
		String url = userInterfaceInfoManager.viewUserInterface(userInterface);
		return url;
	}
	
	public String editUserInterface() {
		return editUserInterface(selectedRecordKey);
	}
	
	public String editUserInterface(Object recordKey) {
		UserInterface userInterface = recordByKeyMap.get(recordKey);
		return editUserInterface(userInterface);
	}
	
	public String editUserInterface(UserInterface userInterface) {
		UserInterfaceInfoManager userInterfaceInfoManager = BeanContext.getFromSession("userInterfaceInfoManager");
		String url = userInterfaceInfoManager.editUserInterface(userInterface);
		return url;
	}
	
	public void removeUserInterface() {
		removeUserInterface(selectedRecordKey);
	}
	
	public void removeUserInterface(Object recordKey) {
		UserInterface userInterface = recordByKeyMap.get(recordKey);
		removeUserInterface(userInterface);
	}
	
	public void removeUserInterface(UserInterface userInterface) {
		try {
			if (userInterfaceDataManager.removeUserInterface(userInterface))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelUserInterface(@Observes @Cancelled UserInterface userInterface) {
		try {
			//Object key = UserInterfaceUtil.getKey(userInterface);
			//recordByKeyMap.put(key, userInterface);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("userInterface");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateUserInterface(Collection<UserInterface> userInterfaceList) {
		return UserInterfaceUtil.validate(userInterfaceList);
	}
	
	public void exportUserInterfaceList(@Observes @Export String tableId) {
		//String tableId = "pageForm:userInterfaceListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
