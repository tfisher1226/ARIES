package nam.model.volume;

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

import nam.model.Volume;
import nam.model.util.VolumeUtil;


@SessionScoped
@Named("volumeSelectManager")
public class VolumeSelectManager extends AbstractSelectManager<Volume, VolumeListObject> implements Serializable {
	
	@Inject
	private VolumeDataManager volumeDataManager;
	
	@Inject
	private VolumeHelper volumeHelper;
	
	
	@Override
	public String getClientId() {
		return "volumeSelect";
	}
	
	@Override
	public String getTitle() {
		return "Volume Selection";
	}
	
	@Override
	protected Class<Volume> getRecordClass() {
		return Volume.class;
	}
	
	@Override
	public boolean isEmpty(Volume volume) {
		return volumeHelper.isEmpty(volume);
	}
	
	@Override
	public String toString(Volume volume) {
		return volumeHelper.toString(volume);
	}
	
	protected VolumeHelper getVolumeHelper() {
		return BeanContext.getFromSession("volumeHelper");
	}
	
	protected VolumeListManager getVolumeListManager() {
		return BeanContext.getFromSession("volumeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshVolumeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Volume> recordList) {
		VolumeListManager volumeListManager = getVolumeListManager();
		DataModel<VolumeListObject> dataModel = volumeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshVolumeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Volume> refreshRecords() {
		try {
			Collection<Volume> records = volumeDataManager.getVolumeList();
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
	public void sortRecords(List<Volume> volumeList) {
		Collections.sort(volumeList, new Comparator<Volume>() {
			public int compare(Volume volume1, Volume volume2) {
				String text1 = VolumeUtil.toString(volume1);
				String text2 = VolumeUtil.toString(volume2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
