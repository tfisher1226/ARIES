package nam.model.volume;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Volume;
import nam.model.util.VolumeUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("volumeWizard")
@SuppressWarnings("serial")
public class VolumeWizard extends AbstractDomainElementWizard<Volume> implements Serializable {
	
	@Inject
	private VolumeDataManager volumeDataManager;
	
	@Inject
	private VolumePageManager volumePageManager;
	
	@Inject
	private VolumeEventManager volumeEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Volume";
	}
	
	@Override
	public String getUrlContext() {
		return volumePageManager.getVolumeWizardPage();
	}
	
	@Override
	public void initialize(Volume volume) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(volumePageManager.getSections());
		super.initialize(volume);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		volumePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		volumePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		volumePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		volumePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Volume volume = getInstance();
		volumeDataManager.saveVolume(volume);
		volumeEventManager.fireSavedEvent(volume);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Volume volume = getInstance();
		//TODO take this out soon
		if (volume == null)
			volume = new Volume();
		volumeEventManager.fireCancelledEvent(volume);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Volume volume = selectionContext.getSelection("volume");
		String name = volume.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("volumeWizard");
			display.error("Volume name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
