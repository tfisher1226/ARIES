package nam.ui.design;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.RecordManager;


public abstract class AbstractNamRecordManager<T> extends AbstractRecordManager<T> implements RecordManager<T> {

	public WorkspaceManager getWorkspaceManager() {
		return getFromSession("workspaceManager");
	}
	
//	public PageManager getPageManager() {
//		return getFromSession("pageManager");
//	}
	
	protected SelectionContext getSelectionContext() {
		return getFromSession("selectionContext");
	}

//	protected EventMulticaster getEventMulticaster() {
//		return getFromSession("eventMulticaster");
//	}

	@SuppressWarnings("unchecked")
	public <Bean> Bean getFromSession(String beanName) {
		Bean bean = (Bean) BeanContext.getFromSession(beanName);
		return bean;
	}
	
	protected String getContextKey() {
		String contextId = getWorkspaceManager().getContextId();
		String contextKey = contextId + ".projects";
		return contextKey;
	}

	@Override
	protected String getMessageDomain() {
		return getSelectionContext().getMessageDomain();
	}
	
	/*
	 * TODO take these out later
	 */
	
	@Override
	public String viewRecord() {
		return null;
	}
	
	@Override
	public String newRecord() {
		return null;
	}
	
	@Override
	public String editRecord() {
		return null;
	}
	
}
