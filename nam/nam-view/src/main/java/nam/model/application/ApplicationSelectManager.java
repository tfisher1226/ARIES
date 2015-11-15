package nam.model.application;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Application;
import nam.model.util.ApplicationUtil;


@SessionScoped
@Named("applicationSelectManager")
public class ApplicationSelectManager extends AbstractSelectManager<Application, ApplicationListObject> implements Serializable {
	
	@Inject
	private ApplicationDataManager applicationDataManager;
	
	@Inject
	private ApplicationHelper applicationHelper;
	
	
	@Override
	public String getClientId() {
		return "applicationSelect";
	}
	
	@Override
	public String getTitle() {
		return "Application Selection";
	}
	
	@Override
	protected Class<Application> getRecordClass() {
		return Application.class;
	}
	
	@Override
	public boolean isEmpty(Application application) {
		return applicationHelper.isEmpty(application);
	}
	
	@Override
	public String toString(Application application) {
		return applicationHelper.toString(application);
	}
	
	protected ApplicationHelper getApplicationHelper() {
		return BeanContext.getFromSession("applicationHelper");
	}
	
	protected ApplicationListManager getApplicationListManager() {
		return BeanContext.getFromSession("applicationListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshApplicationList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Application> recordList) {
		ApplicationListManager applicationListManager = getApplicationListManager();
		DataModel<ApplicationListObject> dataModel = applicationListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshApplicationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Application> refreshRecords() {
		try {
			Collection<Application> records = applicationDataManager.getApplicationList();
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
	public void sortRecords(List<Application> applicationList) {
		Collections.sort(applicationList, new Comparator<Application>() {
			public int compare(Application application1, Application application2) {
				String text1 = ApplicationUtil.toString(application1);
				String text2 = ApplicationUtil.toString(application2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
