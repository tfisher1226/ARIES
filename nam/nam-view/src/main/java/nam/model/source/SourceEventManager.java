package nam.model.source;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Source;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("sourceEventManager")
public class SourceEventManager extends AbstractEventManager<Source> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Source getInstance() {
		return selectionContext.getSelection("source");
	}
	
	public void removeSource() {
		Source source = getInstance();
		fireRemoveEvent(source);
	}
	
}
