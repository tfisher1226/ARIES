package nam.model.enumeration;

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

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;


@SessionScoped
@Named("enumerationSelectManager")
public class EnumerationSelectManager extends AbstractSelectManager<Enumeration, EnumerationListObject> implements Serializable {
	
	@Inject
	private EnumerationDataManager enumerationDataManager;
	
	@Inject
	private EnumerationHelper enumerationHelper;
	
	
	@Override
	public String getClientId() {
		return "enumerationSelect";
	}
	
	@Override
	public String getTitle() {
		return "Enumeration Selection";
	}
	
	@Override
	protected Class<Enumeration> getRecordClass() {
		return Enumeration.class;
	}
	
	@Override
	public boolean isEmpty(Enumeration enumeration) {
		return enumerationHelper.isEmpty(enumeration);
	}
	
	@Override
	public String toString(Enumeration enumeration) {
		return enumerationHelper.toString(enumeration);
	}
	
	protected EnumerationHelper getEnumerationHelper() {
		return BeanContext.getFromSession("enumerationHelper");
	}
	
	protected EnumerationListManager getEnumerationListManager() {
		return BeanContext.getFromSession("enumerationListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshEnumerationList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Enumeration> recordList) {
		EnumerationListManager enumerationListManager = getEnumerationListManager();
		DataModel<EnumerationListObject> dataModel = enumerationListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshEnumerationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Enumeration> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Enumeration> enumerationList = BeanContext.getFromConversation(instanceId);
		return enumerationList;
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
	public void sortRecords(List<Enumeration> enumerationList) {
		Collections.sort(enumerationList, new Comparator<Enumeration>() {
			public int compare(Enumeration enumeration1, Enumeration enumeration2) {
				String text1 = EnumerationUtil.toString(enumeration1);
				String text2 = EnumerationUtil.toString(enumeration2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
