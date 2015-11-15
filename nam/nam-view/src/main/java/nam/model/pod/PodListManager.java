package nam.model.pod;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Pod;
import nam.model.util.PodUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("podListManager")
public class PodListManager extends AbstractDomainListManager<Pod, PodListObject> implements Serializable {
	
	@Inject
	private PodDataManager podDataManager;
	
	@Inject
	private PodEventManager podEventManager;
	
	@Inject
	private PodInfoManager podInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "podList";
	}
	
	@Override
	public String getTitle() {
		return "Pod List";
	}
	
	@Override
	public Object getRecordKey(Pod pod) {
		return PodUtil.getKey(pod);
	}
	
	@Override
	public String getRecordName(Pod pod) {
		return PodUtil.toString(pod);
	}
	
	@Override
	protected Class<Pod> getRecordClass() {
		return Pod.class;
	}
	
	@Override
	protected Pod getRecord(PodListObject rowObject) {
		return rowObject.getPod();
	}
	
	@Override
	public Pod getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? PodUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Pod pod) {
		super.setSelectedRecord(pod);
		fireSelectedEvent(pod);
	}
	
	protected void fireSelectedEvent(Pod pod) {
		podEventManager.fireSelectedEvent(pod);
	}
	
	public boolean isSelected(Pod pod) {
		Pod selection = selectionContext.getSelection("pod");
		boolean selected = selection != null && selection.equals(pod);
		return selected;
	}
	
	@Override
	protected PodListObject createRowObject(Pod pod) {
		PodListObject listObject = new PodListObject(pod);
		listObject.setSelected(isSelected(pod));
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Pod> createRecordList() {
		try {
			Collection<Pod> podList = podDataManager.getPodList();
			if (podList != null)
				return podList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewPod() {
		return viewPod(selectedRecordKey);
	}
	
	public String viewPod(Object recordKey) {
		Pod pod = recordByKeyMap.get(recordKey);
		return viewPod(pod);
	}
	
	public String viewPod(Pod pod) {
		String url = podInfoManager.viewPod(pod);
		return url;
	}
	
	public String editPod() {
		return editPod(selectedRecordKey);
	}
	
	public String editPod(Object recordKey) {
		Pod pod = recordByKeyMap.get(recordKey);
		return editPod(pod);
	}
	
	public String editPod(Pod pod) {
		String url = podInfoManager.editPod(pod);
		return url;
	}
	
	public void removePod() {
		removePod(selectedRecordKey);
	}
	
	public void removePod(Object recordKey) {
		Pod pod = recordByKeyMap.get(recordKey);
		removePod(pod);
	}
	
	public void removePod(Pod pod) {
		try {
			if (podDataManager.removePod(pod))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelPod(@Observes @Cancelled Pod pod) {
		try {
			//Object key = PodUtil.getKey(pod);
			//recordByKeyMap.put(key, pod);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("pod");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validatePod(Collection<Pod> podList) {
		return PodUtil.validate(podList);
	}
	
	public void exportPodList(@Observes @Export String tableId) {
		//String tableId = "pageForm:podListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
