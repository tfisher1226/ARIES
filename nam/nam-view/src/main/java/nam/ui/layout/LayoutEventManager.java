package nam.ui.layout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Layout;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("layoutEventManager")
public class LayoutEventManager extends AbstractEventManager<Layout> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Layout getInstance() {
		return selectionContext.getSelection("layout");
	}
	
	public void removeLayout() {
		Layout layout = getInstance();
		fireRemoveEvent(layout);
	}
	
}
