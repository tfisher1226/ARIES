package nam.ui.control;

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

import nam.ui.Control;
import nam.ui.design.SelectionContext;
import nam.ui.util.ControlUtil;


@SessionScoped
@Named("controlListManager")
public class ControlListManager extends AbstractDomainListManager<Control, ControlListObject> implements Serializable {
	
	@Inject
	private ControlDataManager controlDataManager;
	
	@Inject
	private ControlEventManager controlEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "controlList";
	}
	
	@Override
	public String getTitle() {
		return "Control List";
	}
	
	@Override
	public Object getRecordKey(Control control) {
		return ControlUtil.getKey(control);
	}
	
	@Override
	public String getRecordName(Control control) {
		return ControlUtil.toString(control);
	}
	
	@Override
	protected Class<Control> getRecordClass() {
		return Control.class;
	}
	
	@Override
	protected Control getRecord(ControlListObject rowObject) {
		return rowObject.getControl();
	}
	
	@Override
	public Control getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ControlUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Control control) {
		super.setSelectedRecord(control);
		fireSelectedEvent(control);
	}
	
	protected void fireSelectedEvent(Control control) {
		controlEventManager.fireSelectedEvent(control);
	}
	
	public boolean isSelected(Control control) {
		Control selection = selectionContext.getSelection("control");
		boolean selected = selection != null && selection.equals(control);
		return selected;
	}
	
	@Override
	protected ControlListObject createRowObject(Control control) {
		ControlListObject listObject = new ControlListObject(control);
		listObject.setSelected(isSelected(control));
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
	protected Collection<Control> createRecordList() {
		try {
			Collection<Control> controlList = controlDataManager.getControlList();
			if (controlList != null)
				return controlList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewControl() {
		return viewControl(selectedRecordKey);
	}
	
	public String viewControl(Object recordKey) {
		Control control = recordByKeyMap.get(recordKey);
		return viewControl(control);
	}
	
	public String viewControl(Control control) {
		ControlInfoManager controlInfoManager = BeanContext.getFromSession("controlInfoManager");
		String url = controlInfoManager.viewControl(control);
		return url;
	}
	
	public String editControl() {
		return editControl(selectedRecordKey);
	}
	
	public String editControl(Object recordKey) {
		Control control = recordByKeyMap.get(recordKey);
		return editControl(control);
	}
	
	public String editControl(Control control) {
		ControlInfoManager controlInfoManager = BeanContext.getFromSession("controlInfoManager");
		String url = controlInfoManager.editControl(control);
		return url;
	}
	
	public void removeControl() {
		removeControl(selectedRecordKey);
	}
	
	public void removeControl(Object recordKey) {
		Control control = recordByKeyMap.get(recordKey);
		removeControl(control);
	}
	
	public void removeControl(Control control) {
		try {
			if (controlDataManager.removeControl(control))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelControl(@Observes @Cancelled Control control) {
		try {
			//Object key = ControlUtil.getKey(control);
			//recordByKeyMap.put(key, control);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("control");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateControl(Collection<Control> controlList) {
		return ControlUtil.validate(controlList);
	}
	
	public void exportControlList(@Observes @Export String tableId) {
		//String tableId = "pageForm:controlListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
