package nam.model.parameter;

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

import nam.model.Parameter;
import nam.model.util.ParameterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("parameterListManager")
public class ParameterListManager extends AbstractDomainListManager<Parameter, ParameterListObject> implements Serializable {
	
	@Inject
	private ParameterDataManager parameterDataManager;
	
	@Inject
	private ParameterEventManager parameterEventManager;
	
	@Inject
	private ParameterInfoManager parameterInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "parameterList";
	}
	
	@Override
	public String getTitle() {
		return "Parameter List";
	}
	
	@Override
	public Object getRecordKey(Parameter parameter) {
		return ParameterUtil.getKey(parameter);
	}
	
	@Override
	public String getRecordName(Parameter parameter) {
		return ParameterUtil.getLabel(parameter);
	}
	
	@Override
	protected Class<Parameter> getRecordClass() {
		return Parameter.class;
	}
	
	@Override
	protected Parameter getRecord(ParameterListObject rowObject) {
		return rowObject.getParameter();
	}
	
	@Override
	public Parameter getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ParameterUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Parameter parameter) {
		super.setSelectedRecord(parameter);
		fireSelectedEvent(parameter);
	}
	
	protected void fireSelectedEvent(Parameter parameter) {
		parameterEventManager.fireSelectedEvent(parameter);
	}
	
	public boolean isSelected(Parameter parameter) {
		Parameter selection = selectionContext.getSelection("parameter");
		boolean selected = selection != null && selection.equals(parameter);
		return selected;
	}
	
	public boolean isChecked(Parameter parameter) {
		Collection<Parameter> selection = selectionContext.getSelection("parameterSelection");
		boolean checked = selection != null && selection.contains(parameter);
		return checked;
	}
	
	@Override
	protected ParameterListObject createRowObject(Parameter parameter) {
		ParameterListObject listObject = new ParameterListObject(parameter);
		listObject.setSelected(isSelected(parameter));
		listObject.setChecked(isChecked(parameter));
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
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Parameter> createRecordList() {
		try {
			Collection<Parameter> parameterList = parameterDataManager.getParameterList();
			if (parameterList != null)
			return parameterList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public String viewParameter() {
		return viewParameter(selectedRecordKey);
	}
	
	public String viewParameter(Object recordKey) {
		Parameter parameter = recordByKeyMap.get(recordKey);
		return viewParameter(parameter);
	}
	
	public String viewParameter(Parameter parameter) {
		String url = parameterInfoManager.viewParameter(parameter);
		return url;
	}
	
	public String editParameter() {
		return editParameter(selectedRecordKey);
	}
	
	public String editParameter(Object recordKey) {
		Parameter parameter = recordByKeyMap.get(recordKey);
		return editParameter(parameter);
	}
	
	public String editParameter(Parameter parameter) {
		String url = parameterInfoManager.editParameter(parameter);
		return url;
	}
	
	public void removeParameter() {
		removeParameter(selectedRecordKey);
	}
	
	public void removeParameter(Object recordKey) {
		Parameter parameter = recordByKeyMap.get(recordKey);
		removeParameter(parameter);
	}
	
	public void removeParameter(Parameter parameter) {
		try {
			if (parameterDataManager.removeParameter(parameter))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelParameter(@Observes @Cancelled Parameter parameter) {
		try {
			//Object key = ParameterUtil.getKey(parameter);
			//recordByKeyMap.put(key, parameter);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("parameter");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateParameter(Collection<Parameter> parameterList) {
		return ParameterUtil.validate(parameterList);
	}
	
	public void exportParameterList(@Observes @Export String tableId) {
		//String tableId = "pageForm:parameterListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
