package nam.model.volume;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Volume;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("volumeEventManager")
public class VolumeEventManager extends AbstractEventManager<Volume> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Volume getInstance() {
		return selectionContext.getSelection("volume");
	}
	
	public void removeVolume() {
		Volume volume = getInstance();
		fireRemoveEvent(volume);
	}
	
}
