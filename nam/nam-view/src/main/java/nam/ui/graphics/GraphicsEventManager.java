package nam.ui.graphics;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Graphics;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("graphicsEventManager")
public class GraphicsEventManager extends AbstractEventManager<Graphics> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Graphics getInstance() {
		return selectionContext.getSelection("graphics");
	}
	
	public void removeGraphics() {
		Graphics graphics = getInstance();
		fireRemoveEvent(graphics);
	}
	
}
