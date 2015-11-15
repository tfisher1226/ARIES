package nam.model.volume;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Volume;
import nam.model.util.VolumeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("volumeDataManager")
public class VolumeDataManager implements Serializable {
	
	@Inject
	private VolumeEventManager volumeEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Volume> getVolumeList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Volume> getDefaultList() {
		return null;
	}
	
	public void saveVolume(Volume volume) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeVolume(Volume volume) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
