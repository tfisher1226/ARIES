package nam.ui.graphics;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.ui.Graphics;
import nam.ui.design.SelectionContext;
import nam.ui.util.GraphicsUtil;


@SessionScoped
@Named("graphicsDataManager")
public class GraphicsDataManager implements Serializable {
	
	@Inject
	private GraphicsEventManager graphicsEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Graphics> getGraphicsList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Graphics> getDefaultList() {
		return null;
	}
	
	public void saveGraphics(Graphics graphics) {
		if (scope != null) {
		}
	}
	
	public boolean removeGraphics(Graphics graphics) {
		if (scope != null) {
		}
		return false;
	}
	
}
