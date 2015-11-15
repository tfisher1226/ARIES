package nam.ui.design;

import java.io.Serializable;

import nam.ui.wizard.AbstractManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.core.Events;


@SuppressWarnings("serial")
public abstract class AbstractDomainElementManager extends AbstractManager implements Serializable {

	@In(required = true)
	private Events events;

	@In(required = true)
	private WorkspaceManager workspaceManager;
	
	protected abstract String getRefreshEvent();

	
	public void saveProject() {
		workspaceManager.saveProject();
		events.raiseEvent(getRefreshEvent());
	}
	
}
