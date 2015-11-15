package admin.ui.action;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import admin.Action;
import admin.util.ActionUtil;


@SessionScoped
@Named("actionHelper")
public class ActionHelper extends AbstractEnumerationHelper<Action> implements Serializable {

	//SEAM @Factory(value = "actionList")
	public Action[] getActionArray() {
		return Action.values();
	}
	
	@Override
	public String toString(Action action) {
		return action.name();
	}

	@Override
	public String toString(Collection<Action> actionList) {
		return ActionUtil.toString(actionList);
	}

}
