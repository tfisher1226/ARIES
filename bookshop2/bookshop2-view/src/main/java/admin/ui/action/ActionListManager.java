package admin.ui.action;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import admin.Action;


@SessionScoped
@Named("actionListManager")
public class ActionListManager extends AbstractTabListManager<Action, ActionListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "ActionList";
	}
	
	@Override
	public String getTitle() {
		return "Action List";
	}
	
	@Override
	public Object getRecordId(Action action) {
		return action.ordinal();
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
	protected ActionListObject createRowObject(Action action) {
		return new ActionListObject(action);
	}
	
	protected ActionHelper getActionHelper() {
		return BeanContext.getFromSession("actionHelper");
	}
	
	@Override
	//SEAM @Observer("org.aries.ui.reset")
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	//SEAM @Observer("org.aries.refreshActionList")
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected List<Action> createRecordList() {
		try {
			List<Action> actionList = Arrays.asList(Action.values());
			return actionList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
