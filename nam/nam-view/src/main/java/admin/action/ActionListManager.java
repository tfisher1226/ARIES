package admin.action;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import admin.Action;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("actionListManager")
public class ActionListManager extends AbstractDomainListManager<Action, ActionListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "actionList";
	}
	
	@Override
	public String getTitle() {
		return "Action List";
	}

	@Override
	public Object getRecordKey(Action action) {
		return action.name();
	}
	
	@Override
	public String getRecordName(Action action) {
		return action.name();
	}
	
	@Override
	protected Class<Action> getRecordClass() {
		return Action.class;
	}
	
	@Override
	protected Action getRecord(ActionListObject rowObject) {
		return rowObject.getAction();
	}
	
	@Override
	public Action getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(Action action) {
		super.setSelectedRecord(action);
	}
	
	public boolean isSelected(Action action) {
		Action selection = selectionContext.getSelection("action");
		boolean selected = selection != null && selection.equals(action);
		return selected;
	}
	
	public boolean isChecked(Action action) {
		Collection<Action> selection = selectionContext.getSelection("actionSelection");
		boolean checked = selection != null && selection.contains(action);
		return checked;
	}
	
	@Override
	protected ActionListObject createRowObject(Action action) {
		ActionListObject listObject = new ActionListObject(action);
		listObject.setSelected(isSelected(action));
		listObject.setChecked(isChecked(action));
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
	protected Collection<Action> createRecordList() {
		try {
			Collection<Action> actionList = Arrays.asList(Action.values());
			return actionList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
