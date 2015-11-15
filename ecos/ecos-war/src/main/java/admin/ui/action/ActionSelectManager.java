package admin.ui.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;
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
@Name("actionSelectManager")
@Scope(ScopeType.SESSION)
public class ActionSelectManager extends AbstractSelectManager<Action, ActionListObject> implements Serializable {

	@Override
	public String getClientId() {
		return "ActionSelect";
	}
	
	@Override
	public String getTitle() {
		return "Action Selection";
	}
	
	@Override
	protected Class<Action> getRecordClass() {
		return Action.class;
	}
	
	@Override
	public String toString(Action action) {
		return action.name();
	}
	
	protected ActionHelper getActionHelper() {
		return BeanContext.getFromSession("actionHelper");
	}
	
	protected ActionListManager getActionListManager() {
		return BeanContext.getFromSession("actionListManager");
	}
	
	@Override
	@Observer("org.aries.ui.reset")
	public void initialize() {
		initializeContext(); 
		refreshActionList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Action> recordList) {
		ActionListManager actionListManager = getActionListManager();
		DataModel<ActionListObject> dataModel = actionListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	@Observer("org.aries.refreshActionList")
	public void refreshActionList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<Action> refreshRecords() {
		Action[] values = Action.values();
		List<Action> masterList = new ArrayList<Action>();
		for (Action capability : values) {
			masterList.add(capability);
		}
		return masterList;
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
	public void sortRecords(List<Action> actionList) {
		Collections.sort(actionList);
	}
	
}
