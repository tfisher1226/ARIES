package nam.model.volume;

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

import nam.model.Volume;
import nam.model.util.VolumeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("volumeListManager")
public class VolumeListManager extends AbstractDomainListManager<Volume, VolumeListObject> implements Serializable {
	
	@Inject
	private VolumeDataManager volumeDataManager;
	
	@Inject
	private VolumeEventManager volumeEventManager;
	
	@Inject
	private VolumeInfoManager volumeInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "volumeList";
	}
	
	@Override
	public String getTitle() {
		return "Volume List";
	}
	
	@Override
	public Object getRecordKey(Volume volume) {
		return VolumeUtil.getKey(volume);
	}
	
	@Override
	public String getRecordName(Volume volume) {
		return VolumeUtil.getLabel(volume);
	}
	
	@Override
	protected Class<Volume> getRecordClass() {
		return Volume.class;
	}
	
	@Override
	protected Volume getRecord(VolumeListObject rowObject) {
		return rowObject.getVolume();
	}
	
	@Override
	public Volume getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? VolumeUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Volume volume) {
		super.setSelectedRecord(volume);
		fireSelectedEvent(volume);
	}
	
	protected void fireSelectedEvent(Volume volume) {
		volumeEventManager.fireSelectedEvent(volume);
	}
	
	public boolean isSelected(Volume volume) {
		Volume selection = selectionContext.getSelection("volume");
		boolean selected = selection != null && selection.equals(volume);
		return selected;
	}
	
	@Override
	protected VolumeListObject createRowObject(Volume volume) {
		VolumeListObject listObject = new VolumeListObject(volume);
		listObject.setSelected(isSelected(volume));
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
	protected Collection<Volume> createRecordList() {
		try {
			Collection<Volume> volumeList = volumeDataManager.getVolumeList();
			if (volumeList != null)
				return volumeList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewVolume() {
		return viewVolume(selectedRecordKey);
	}
	
	public String viewVolume(Object recordKey) {
		Volume volume = recordByKeyMap.get(recordKey);
		return viewVolume(volume);
	}
	
	public String viewVolume(Volume volume) {
		String url = volumeInfoManager.viewVolume(volume);
		return url;
	}
	
	public String editVolume() {
		return editVolume(selectedRecordKey);
	}
	
	public String editVolume(Object recordKey) {
		Volume volume = recordByKeyMap.get(recordKey);
		return editVolume(volume);
	}
	
	public String editVolume(Volume volume) {
		String url = volumeInfoManager.editVolume(volume);
		return url;
	}
	
	public void removeVolume() {
		removeVolume(selectedRecordKey);
	}
	
	public void removeVolume(Object recordKey) {
		Volume volume = recordByKeyMap.get(recordKey);
		removeVolume(volume);
	}
	
	public void removeVolume(Volume volume) {
		try {
			if (volumeDataManager.removeVolume(volume))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelVolume(@Observes @Cancelled Volume volume) {
		try {
			//Object key = VolumeUtil.getKey(volume);
			//recordByKeyMap.put(key, volume);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("volume");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateVolume(Collection<Volume> volumeList) {
		return VolumeUtil.validate(volumeList);
	}
	
	public void exportVolumeList(@Observes @Export String tableId) {
		//String tableId = "pageForm:volumeListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
