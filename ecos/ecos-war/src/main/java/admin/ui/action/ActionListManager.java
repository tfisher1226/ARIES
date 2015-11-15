package admin.ui.action;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Action;


@Startup
@AutoCreate
@BypassInterceptors
@Name("actionListManager")
@Scope(ScopeType.SESSION)
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
	@Observer("org.aries.ui.reset")
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	@Observer("org.aries.refreshActionList")
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
