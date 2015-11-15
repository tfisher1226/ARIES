package nam.model.reference;

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

import nam.model.Reference;
import nam.model.util.ReferenceUtil;


@SessionScoped
@Named("referenceSelectManager")
public class ReferenceSelectManager extends AbstractSelectManager<Reference, ReferenceListObject> implements Serializable {
	
	@Inject
	private ReferenceDataManager referenceDataManager;
	
	@Inject
	private ReferenceHelper referenceHelper;
	
	
	@Override
	public String getClientId() {
		return "referenceSelect";
	}
	
	@Override
	public String getTitle() {
		return "Reference Selection";
	}
	
	@Override
	protected Class<Reference> getRecordClass() {
		return Reference.class;
	}
	
	@Override
	public boolean isEmpty(Reference reference) {
		return referenceHelper.isEmpty(reference);
	}
	
	@Override
	public String toString(Reference reference) {
		return referenceHelper.toString(reference);
	}
	
	protected ReferenceHelper getReferenceHelper() {
		return BeanContext.getFromSession("referenceHelper");
	}
	
	protected ReferenceListManager getReferenceListManager() {
		return BeanContext.getFromSession("referenceListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshReferenceList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Reference> recordList) {
		ReferenceListManager referenceListManager = getReferenceListManager();
		DataModel<ReferenceListObject> dataModel = referenceListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshReferenceList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Reference> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Reference> referenceList = BeanContext.getFromConversation(instanceId);
		return referenceList;
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
	public void sortRecords(List<Reference> referenceList) {
		Collections.sort(referenceList, new Comparator<Reference>() {
			public int compare(Reference reference1, Reference reference2) {
				String text1 = ReferenceUtil.toString(reference1);
				String text2 = ReferenceUtil.toString(reference2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
