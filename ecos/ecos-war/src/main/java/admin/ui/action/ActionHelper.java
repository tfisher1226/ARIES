package admin.ui.action;

import java.io.Serializable;
import java.util.Collection;

import org.aries.ui.manager.AbstractEnumerationHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Action;
import admin.util.ActionUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("actionHelper")
@Scope(ScopeType.SESSION)
public class ActionHelper extends AbstractEnumerationHelper<Action> implements Serializable {

	@Factory(value = "actionList")
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
