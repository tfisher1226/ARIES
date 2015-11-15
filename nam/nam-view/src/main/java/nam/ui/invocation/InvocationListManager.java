package nam.ui.invocation;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.ui.Invocation;
import nam.ui.design.SelectionContext;
import nam.ui.util.InvocationUtil;


@SessionScoped
@Named("invocationListManager")
public class InvocationListManager extends AbstractDomainListManager<Invocation, InvocationListObject> implements Serializable {
	
	@Inject
	private InvocationDataManager invocationDataManager;
	
	@Inject
	private InvocationEventManager invocationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "invocationList";
	}
	
	@Override
	public String getTitle() {
		return "Invocation List";
	}
	
	@Override
	public Object getRecordKey(Invocation invocation) {
		return InvocationUtil.getKey(invocation);
	}
	
	@Override
	public String getRecordName(Invocation invocation) {
		return InvocationUtil.toString(invocation);
	}
	
	@Override
	protected Class<Invocation> getRecordClass() {
		return Invocation.class;
	}
	
	@Override
	protected Invocation getRecord(InvocationListObject rowObject) {
		return rowObject.getInvocation();
	}
	
	@Override
	public Invocation getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? InvocationUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Invocation invocation) {
		super.setSelectedRecord(invocation);
		fireSelectedEvent(invocation);
	}
	
	protected void fireSelectedEvent(Invocation invocation) {
		invocationEventManager.fireSelectedEvent(invocation);
	}
	
	public boolean isSelected(Invocation invocation) {
		Invocation selection = selectionContext.getSelection("invocation");
		boolean selected = selection != null && selection.equals(invocation);
		return selected;
	}
	
	@Override
	protected InvocationListObject createRowObject(Invocation invocation) {
		InvocationListObject listObject = new InvocationListObject(invocation);
		listObject.setSelected(isSelected(invocation));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Invocation> createRecordList() {
		try {
			Collection<Invocation> invocationList = invocationDataManager.getInvocationList();
			if (invocationList != null)
				return invocationList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewInvocation() {
		return viewInvocation(selectedRecordKey);
	}
	
	public String viewInvocation(Object recordKey) {
		Invocation invocation = recordByKeyMap.get(recordKey);
		return viewInvocation(invocation);
	}
	
	public String viewInvocation(Invocation invocation) {
		InvocationInfoManager invocationInfoManager = BeanContext.getFromSession("invocationInfoManager");
		String url = invocationInfoManager.viewInvocation(invocation);
		return url;
	}
	
	public String editInvocation() {
		return editInvocation(selectedRecordKey);
	}
	
	public String editInvocation(Object recordKey) {
		Invocation invocation = recordByKeyMap.get(recordKey);
		return editInvocation(invocation);
	}
	
	public String editInvocation(Invocation invocation) {
		InvocationInfoManager invocationInfoManager = BeanContext.getFromSession("invocationInfoManager");
		String url = invocationInfoManager.editInvocation(invocation);
		return url;
	}
	
	public void removeInvocation() {
		removeInvocation(selectedRecordKey);
	}
	
	public void removeInvocation(Object recordKey) {
		Invocation invocation = recordByKeyMap.get(recordKey);
		removeInvocation(invocation);
	}
	
	public void removeInvocation(Invocation invocation) {
		try {
			if (invocationDataManager.removeInvocation(invocation))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelInvocation(@Observes @Cancelled Invocation invocation) {
		try {
			//Object key = InvocationUtil.getKey(invocation);
			//recordByKeyMap.put(key, invocation);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("invocation");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateInvocation(Collection<Invocation> invocationList) {
		return InvocationUtil.validate(invocationList);
	}
	
	public void exportInvocationList(@Observes @Export String tableId) {
		//String tableId = "pageForm:invocationListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
