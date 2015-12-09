package nam.model.application;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Domain;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.event.Selected;
import org.aries.ui.manager.ExportManager;


@SessionScoped
@Named("applicationListManager")
public class ApplicationListManager extends AbstractDomainListManager<Application, ApplicationListObject> implements Serializable {
	
	@Inject
	private ApplicationDataManager applicationDataManager;
	
	@Inject
	private ApplicationEventManager applicationEventManager;
	
	@Inject
	private ApplicationInfoManager applicationInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public String getClientId() {
		return "applicationList";
	}
	
	@Override
	public String getTitle() {
		return "Application List";
	}
	
	@Override
	public Object getRecordKey(Application application) {
		return ApplicationUtil.getKey(application);
	}
	
	@Override
	public String getRecordName(Application application) {
		return ApplicationUtil.getLabel(application);
	}
	
	@Override
	protected Class<Application> getRecordClass() {
		return Application.class;
	}
	
	@Override
	protected Application getRecord(ApplicationListObject rowObject) {
		return rowObject.getApplication();
	}
	
	@Override
	public Application getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ApplicationUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Application application) {
		super.setSelectedRecord(application);
		fireSelectedEvent(application);
	}
	
	protected void fireSelectedEvent(Application application) {
		applicationEventManager.fireSelectedEvent(application);
	}
	
	public boolean isSelected(Application application) {
		Application selection = selectionContext.getSelection("application");
		boolean selected = selection != null && selection.equals(application);
		return selected;
	}

	public boolean isChecked(Application application) {
		Collection<Application> selection = selectionContext.getSelection("applicationSelection");
		boolean checked = selection != null && selection.contains(application);
		return checked;
	}

	@Override
	protected ApplicationListObject createRowObject(Application application) {
		Project project = selectionContext.getSelection("project");
		ApplicationListObject listObject = new ApplicationListObject(project, application);
		listObject.setSelected(isSelected(application));
		listObject.setChecked(isChecked(application));
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
	protected Collection<Application> createRecordList() {
		try {
			Collection<Application> applicationList = applicationDataManager.getApplicationList();
			if (applicationList != null)
				return applicationList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewApplication() {
		return viewApplication(selectedRecordKey);
	}
	
	public String viewApplication(Object recordKey) {
		Application application = recordByKeyMap.get(recordKey);
		return viewApplication(application);
	}
	
	public String viewApplication(Application application) {
		String url = applicationInfoManager.viewApplication(application);
		return url;
	}
	
	public String editApplication() {
		return editApplication(selectedRecordKey);
	}
	
	public String editApplication(Object recordKey) {
		Application application = recordByKeyMap.get(recordKey);
		return editApplication(application);
	}
	
	public String editApplication(Application application) {
		String url = applicationInfoManager.editApplication(application);
		return url;
	}
	
	public void removeApplication() {
		removeApplication(selectedRecordKey);
	}
	
	public void removeApplication(Object recordKey) {
		Application application = recordByKeyMap.get(recordKey);
		removeApplication(application);
	}
	
	public void removeApplication(Application application) {
		try {
			if (applicationDataManager.removeApplication(application))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelApplication(@Observes @Cancelled Application application) {
		try {
			//Object key = ApplicationUtil.getKey(application);
			//recordByKeyMap.put(key, application);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("application");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateApplication(Collection<Application> applicationList) {
		return ApplicationUtil.validate(applicationList);
	}
	
	public void exportApplicationList(@Observes @Export String tableId) {
		//String tableId = "pageForm:applicationListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
