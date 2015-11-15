package nam.model.pod;

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

import nam.model.Pod;
import nam.model.util.PodUtil;


@SessionScoped
@Named("podSelectManager")
public class PodSelectManager extends AbstractSelectManager<Pod, PodListObject> implements Serializable {
	
	@Inject
	private PodDataManager podDataManager;
	
	@Inject
	private PodHelper podHelper;
	
	
	@Override
	public String getClientId() {
		return "podSelect";
	}
	
	@Override
	public String getTitle() {
		return "Pod Selection";
	}
	
	@Override
	protected Class<Pod> getRecordClass() {
		return Pod.class;
	}
	
	@Override
	public boolean isEmpty(Pod pod) {
		return podHelper.isEmpty(pod);
	}
	
	@Override
	public String toString(Pod pod) {
		return podHelper.toString(pod);
	}
	
	protected PodHelper getPodHelper() {
		return BeanContext.getFromSession("podHelper");
	}
	
	protected PodListManager getPodListManager() {
		return BeanContext.getFromSession("podListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshPodList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Pod> recordList) {
		PodListManager podListManager = getPodListManager();
		DataModel<PodListObject> dataModel = podListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshPodList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Pod> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Pod> podList = BeanContext.getFromConversation(instanceId);
		return podList;
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
	public void sortRecords(List<Pod> podList) {
		Collections.sort(podList, new Comparator<Pod>() {
			public int compare(Pod pod1, Pod pod2) {
				String text1 = PodUtil.toString(pod1);
				String text2 = PodUtil.toString(pod2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
