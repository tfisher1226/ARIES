package nam.model.variable;

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

import nam.model.Variable;
import nam.model.util.VariableUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("variableListManager")
public class VariableListManager extends AbstractDomainListManager<Variable, VariableListObject> implements Serializable {
	
	@Inject
	private VariableDataManager variableDataManager;
	
	@Inject
	private VariableEventManager variableEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "variableList";
	}
	
	@Override
	public String getTitle() {
		return "Variable List";
	}
	
	@Override
	public Object getRecordKey(Variable variable) {
		return VariableUtil.getKey(variable);
	}
	
	@Override
	public String getRecordName(Variable variable) {
		return VariableUtil.toString(variable);
	}
	
	@Override
	protected Class<Variable> getRecordClass() {
		return Variable.class;
	}
	
	@Override
	protected Variable getRecord(VariableListObject rowObject) {
		return rowObject.getVariable();
	}
	
	@Override
	public Variable getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? VariableUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Variable variable) {
		super.setSelectedRecord(variable);
		fireSelectedEvent(variable);
	}
	
	protected void fireSelectedEvent(Variable variable) {
		variableEventManager.fireSelectedEvent(variable);
	}
	
	public boolean isSelected(Variable variable) {
		Variable selection = selectionContext.getSelection("variable");
		boolean selected = selection != null && selection.equals(variable);
		return selected;
	}
	
	@Override
	protected VariableListObject createRowObject(Variable variable) {
		VariableListObject listObject = new VariableListObject(variable);
		listObject.setSelected(isSelected(variable));
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
		refreshModel(recordList);
	}
	
	@Override
	protected Collection<Variable> createRecordList() {
		try {
			Collection<Variable> variableList = variableDataManager.getVariableList();
			if (variableList != null)
				return variableList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewVariable() {
		return viewVariable(selectedRecordKey);
	}
	
	public String viewVariable(Object recordKey) {
		Variable variable = recordByKeyMap.get(recordKey);
		return viewVariable(variable);
	}
	
	public String viewVariable(Variable variable) {
		VariableInfoManager variableInfoManager = BeanContext.getFromSession("variableInfoManager");
		String url = variableInfoManager.viewVariable(variable);
		return url;
	}
	
	public String editVariable() {
		return editVariable(selectedRecordKey);
	}
	
	public String editVariable(Object recordKey) {
		Variable variable = recordByKeyMap.get(recordKey);
		return editVariable(variable);
	}
	
	public String editVariable(Variable variable) {
		VariableInfoManager variableInfoManager = BeanContext.getFromSession("variableInfoManager");
		String url = variableInfoManager.editVariable(variable);
		return url;
	}
	
	public void removeVariable() {
		removeVariable(selectedRecordKey);
	}
	
	public void removeVariable(Object recordKey) {
		Variable variable = recordByKeyMap.get(recordKey);
		removeVariable(variable);
	}
	
	public void removeVariable(Variable variable) {
		try {
			if (variableDataManager.removeVariable(variable))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelVariable(@Observes @Cancelled Variable variable) {
		try {
			//Object key = VariableUtil.getKey(variable);
			//recordByKeyMap.put(key, variable);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("variable");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateVariable(Collection<Variable> variableList) {
		return VariableUtil.validate(variableList);
	}
	
	public void exportVariableList(@Observes @Export String tableId) {
		//String tableId = "pageForm:variableListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
