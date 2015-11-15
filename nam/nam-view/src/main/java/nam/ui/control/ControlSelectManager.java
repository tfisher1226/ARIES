package nam.ui.control;

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

import nam.ui.Control;
import nam.ui.util.ControlUtil;


@SessionScoped
@Named("controlSelectManager")
public class ControlSelectManager extends AbstractSelectManager<Control, ControlListObject> implements Serializable {
	
	@Inject
	private ControlDataManager controlDataManager;
	
	
	@Override
	public String getClientId() {
		return "controlSelect";
	}
	
	@Override
	public String getTitle() {
		return "Control Selection";
	}
	
	@Override
	protected Class<Control> getRecordClass() {
		return Control.class;
	}
	
	@Override
	public boolean isEmpty(Control control) {
		return getControlHelper().isEmpty(control);
	}
	
	@Override
	public String toString(Control control) {
		return getControlHelper().toString(control);
	}
	
	protected ControlHelper getControlHelper() {
		return BeanContext.getFromSession("controlHelper");
	}
	
	protected ControlListManager getControlListManager() {
		return BeanContext.getFromSession("controlListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshControlList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Control> recordList) {
		ControlListManager controlListManager = getControlListManager();
		DataModel<ControlListObject> dataModel = controlListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshControlList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Control> refreshRecords() {
		try {
			Collection<Control> records = controlDataManager.getControlList();
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
	public void sortRecords(List<Control> controlList) {
		Collections.sort(controlList, new Comparator<Control>() {
			public int compare(Control control1, Control control2) {
				String text1 = ControlUtil.toString(control1);
				String text2 = ControlUtil.toString(control2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
