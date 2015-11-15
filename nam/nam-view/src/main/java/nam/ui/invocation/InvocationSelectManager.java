package nam.ui.invocation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.ui.Invocation;
import nam.ui.util.InvocationUtil;


@SessionScoped
@Named("invocationSelectManager")
public class InvocationSelectManager extends AbstractSelectManager<Invocation, InvocationListObject> implements Serializable {
	
	@Inject
	private InvocationDataManager invocationDataManager;
	
	
	@Override
	public String getClientId() {
		return "invocationSelect";
	}
	
	@Override
	public String getTitle() {
		return "Invocation Selection";
	}
	
	@Override
	protected Class<Invocation> getRecordClass() {
		return Invocation.class;
	}
	
	@Override
	public boolean isEmpty(Invocation invocation) {
		return getInvocationHelper().isEmpty(invocation);
	}
	
	@Override
	public String toString(Invocation invocation) {
		return getInvocationHelper().toString(invocation);
	}
	
	protected InvocationHelper getInvocationHelper() {
		return BeanContext.getFromSession("invocationHelper");
	}
	
	protected InvocationListManager getInvocationListManager() {
		return BeanContext.getFromSession("invocationListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshInvocationList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Invocation> recordList) {
		InvocationListManager invocationListManager = getInvocationListManager();
		DataModel<InvocationListObject> dataModel = invocationListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshInvocationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Invocation> refreshRecords() {
		try {
			Collection<Invocation> records = invocationDataManager.getInvocationList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Invocation> invocationList) {
		Collections.sort(invocationList, new Comparator<Invocation>() {
			public int compare(Invocation invocation1, Invocation invocation2) {
				String text1 = InvocationUtil.toString(invocation1);
				String text2 = InvocationUtil.toString(invocation2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
