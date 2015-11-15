package admin.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.Action;


@SessionScoped
@Named("actionSelectManager")
public class ActionSelectManager extends AbstractSelectManager<Action, ActionListObject> implements Serializable {

	@Inject
	private ActionHelper actionHelper;
	
	
	@Override
	public String getClientId() {
		return "actionSelect";
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
	
	public void refreshActionList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Action> refreshRecords() {
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
