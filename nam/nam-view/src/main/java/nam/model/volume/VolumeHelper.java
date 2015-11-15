package nam.model.volume;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Volume;
import nam.model.util.VolumeUtil;


@SessionScoped
@Named("volumeHelper")
public class VolumeHelper extends AbstractElementHelper<Volume> implements Serializable {
	
	@Override
	public boolean isEmpty(Volume volume) {
		return VolumeUtil.isEmpty(volume);
	}
	
	@Override
	public String toString(Volume volume) {
		return VolumeUtil.toString(volume);
	}
	
	@Override
	public String toString(Collection<Volume> volumeList) {
		return VolumeUtil.toString(volumeList);
	}
	
	@Override
	public boolean validate(Volume volume) {
		return VolumeUtil.validate(volume);
	}
	
	@Override
	public boolean validate(Collection<Volume> volumeList) {
		return VolumeUtil.validate(volumeList);
	}
	
}
