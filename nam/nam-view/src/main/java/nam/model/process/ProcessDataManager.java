package nam.model.process;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("processDataManager")
public class ProcessDataManager implements Serializable {
	
	@Inject
	private ProcessEventManager processEventManager;
	
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
	
	public Collection<Process> getProcessList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Process> getDefaultList() {
		return null;
	}
	
	public void saveProcess(Process process) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeProcess(Process process) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
