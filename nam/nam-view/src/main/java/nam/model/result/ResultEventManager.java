package nam.model.result;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Result;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("resultEventManager")
public class ResultEventManager extends AbstractEventManager<Result> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Result getInstance() {
		return selectionContext.getSelection("result");
	}
	
	public void removeResult() {
		Result result = getInstance();
		fireRemoveEvent(result);
	}
	
}
