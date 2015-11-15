package nam.model.volume;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Volume;
import nam.model.util.ProjectUtil;
import nam.model.util.VolumeUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("volumeInfoManager")
public class VolumeInfoManager extends AbstractNamRecordManager<Volume> implements Serializable {
	
	@Inject
	private VolumeWizard volumeWizard;
	
	@Inject
	private VolumeDataManager volumeDataManager;
	
	@Inject
	private VolumePageManager volumePageManager;
	
	@Inject
	private VolumeEventManager volumeEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private VolumeHelper volumeHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public VolumeInfoManager() {
		setInstanceName("volume");
	}
	
	
	public Volume getVolume() {
		return getRecord();
	}
	
	public Volume getSelectedVolume() {
		return selectionContext.getSelection("volume");
	}
	
	@Override
	public Class<Volume> getRecordClass() {
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
	
	@Override
	public void initialize() {
		Volume volume = selectionContext.getSelection("volume");
		if (volume != null)
			initialize(volume);
	}
	
	protected void initialize(Volume volume) {
		VolumeUtil.initialize(volume);
		volumeWizard.initialize(volume);
		setContext("volume", volume);
	}
	
	public void handleVolumeSelected(@Observes @Selected Volume volume) {
		selectionContext.setSelection("volume",  volume);
		volumePageManager.updateState(volume);
		volumePageManager.refreshMembers();
		setRecord(volume);
	}
	
	@Override
	public String newRecord() {
		return newVolume();
	}
	
	public String newVolume() {
		try {
			Volume volume = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("volume",  volume);
			String url = volumePageManager.initializeVolumeCreationPage(volume);
			volumePageManager.pushContext(volumeWizard);
			initialize(volume);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Volume create() {
		Volume volume = VolumeUtil.create();
		return volume;
	}
	
	@Override
	public Volume clone(Volume volume) {
		volume = VolumeUtil.clone(volume);
		return volume;
	}
	
	@Override
	public String viewRecord() {
		return viewVolume();
	}
	
	public String viewVolume() {
		Volume volume = selectionContext.getSelection("volume");
		String url = viewVolume(volume);
		return url;
	}
	
	public String viewVolume(Volume volume) {
		try {
			String url = volumePageManager.initializeVolumeSummaryView(volume);
			volumePageManager.pushContext(volumeWizard);
			initialize(volume);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editVolume();
	}
	
	public String editVolume() {
		Volume volume = selectionContext.getSelection("volume");
		String url = editVolume(volume);
		return url;
	}
	
	public String editVolume(Volume volume) {
		try {
			//volume = clone(volume);
			selectionContext.resetOrigin();
			selectionContext.setSelection("volume",  volume);
			String url = volumePageManager.initializeVolumeUpdatePage(volume);
			volumePageManager.pushContext(volumeWizard);
			initialize(volume);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveVolume() {
		Volume volume = getVolume();
		if (validateVolume(volume)) {
			if (isImmediate())
				persistVolume(volume);
			outject("volume", volume);
		}
	}
	
	public void persistVolume(Volume volume) {
		saveVolume(volume);
	}
	
	public void saveVolume(Volume volume) {
		try {
			saveVolumeToSystem(volume);
			volumeEventManager.fireAddedEvent(volume);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveVolumeToSystem(Volume volume) {
		volumeDataManager.saveVolume(volume);
	}
	
	public void handleSaveVolume(@Observes @Add Volume volume) {
		saveVolume(volume);
	}
	
	public void addVolume(Volume volume) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichVolume(Volume volume) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Volume volume) {
		return validateVolume(volume);
	}
	
	public boolean validateVolume(Volume volume) {
		Validator validator = getValidator();
		boolean isValid = VolumeUtil.validate(volume);
		Display display = getFromSession("display");
		display.setModule("volumeInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveVolume() {
		display = getFromSession("display");
		display.setModule("volumeInfo");
		Volume volume = selectionContext.getSelection("volume");
		if (volume == null) {
			display.error("Volume record must be selected.");
		}
	}
	
	public String handleRemoveVolume(@Observes @Remove Volume volume) {
		display = getFromSession("display");
		display.setModule("volumeInfo");
		try {
			display.info("Removing Volume "+VolumeUtil.getLabel(volume)+" from the system.");
			removeVolumeFromSystem(volume);
			selectionContext.clearSelection("volume");
			volumeEventManager.fireClearSelectionEvent();
			volumeEventManager.fireRemovedEvent(volume);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeVolumeFromSystem(Volume volume) {
		if (volumeDataManager.removeVolume(volume))
			setRecord(null);
	}
	
	public void cancelVolume() {
		BeanContext.removeFromSession("volume");
		volumePageManager.removeContext(volumeWizard);
	}
	
}
