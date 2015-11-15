package org.aries.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.aries.Action;



public class ActionRepository {

    private Map<String, Action> _actions;

    
	public void addAction(Action action) {
        if (_actions == null)
        	_actions = new HashMap<String, Action>();
        _actions.put(action.getName(), action);
    }

	public Action getAction(String actionId) {
        return _actions.get(actionId);
    }

	public Collection<Action> getActions() {
        return _actions.values();
    }

}
