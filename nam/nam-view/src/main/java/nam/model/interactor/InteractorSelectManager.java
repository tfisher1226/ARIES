package nam.model.interactor;

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

import nam.model.Interactor;
import nam.model.util.InteractorUtil;


@SessionScoped
@Named("interactorSelectManager")
public class InteractorSelectManager extends AbstractSelectManager<Interactor, InteractorListObject> implements Serializable {
	
	@Inject
	private InteractorDataManager interactorDataManager;
	
	@Inject
	private InteractorHelper interactorHelper;
	
	
	@Override
	public String getClientId() {
		return "interactorSelect";
	}
	
	@Override
	public String getTitle() {
		return "Interactor Selection";
	}
	
	@Override
	protected Class<Interactor> getRecordClass() {
		return Interactor.class;
	}
	
	@Override
	public boolean isEmpty(Interactor interactor) {
		return interactorHelper.isEmpty(interactor);
	}
	
	@Override
	public String toString(Interactor interactor) {
		return interactorHelper.toString(interactor);
	}
	
	protected InteractorHelper getInteractorHelper() {
		return BeanContext.getFromSession("interactorHelper");
	}
	
	protected InteractorListManager getInteractorListManager() {
		return BeanContext.getFromSession("interactorListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshInteractorList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Interactor> recordList) {
		InteractorListManager interactorListManager = getInteractorListManager();
		DataModel<InteractorListObject> dataModel = interactorListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshInteractorList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Interactor> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Interactor> interactorList = BeanContext.getFromConversation(instanceId);
		return interactorList;
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
	public void sortRecords(List<Interactor> interactorList) {
		Collections.sort(interactorList, new Comparator<Interactor>() {
			public int compare(Interactor interactor1, Interactor interactor2) {
				String text1 = InteractorUtil.toString(interactor1);
				String text2 = InteractorUtil.toString(interactor2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
