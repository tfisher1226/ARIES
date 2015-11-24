package nam.model.volume;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Volume;
import nam.model.util.VolumeUtil;


@SessionScoped
@Named("volumeOverviewSection")
public class VolumeRecord_OverviewSection extends AbstractWizardPage<Volume> implements Serializable {
	
	private Volume volume;
	
	
	public VolumeRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Volume getVolume() {
		return volume;
	}
	
	public void setVolume(Volume volume) {
		this.volume = volume;
	}
	
	@Override
	public void initialize(Volume volume) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setVolume(volume);
	}
	
	@Override
	public void validate() {
		if (volume == null) {
			validator.missing("Volume");
		} else {
		}
	}
	
}