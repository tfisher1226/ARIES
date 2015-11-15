package nam.ui.graphics;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.Graphics;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;


@SessionScoped
@Named("graphicsWizard")
@SuppressWarnings("serial")
public class GraphicsWizard extends AbstractDomainElementWizard<Graphics> implements Serializable {
	
	@Inject
	private GraphicsDataManager graphicsDataManager;
	
	@Inject
	private GraphicsPageManager graphicsPageManager;
	
	@Inject
	private GraphicsEventManager graphicsEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Graphics";
	}
	
	@Override
	public String getUrlContext() {
		return graphicsPageManager.getGraphicsWizardPage();
	}
	
	@Override
	public void initialize(Graphics graphics) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(graphicsPageManager.getSections());
		super.initialize(graphics);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		graphicsPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		graphicsPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		graphicsPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		graphicsPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Graphics graphics = getInstance();
		graphicsDataManager.saveGraphics(graphics);
		graphicsEventManager.fireSavedEvent(graphics);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Graphics graphics = getInstance();
		//TODO take this out soon
		if (graphics == null)
			graphics = new Graphics();
		graphicsEventManager.fireCancelledEvent(graphics);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Graphics graphics = selectionContext.getSelection("graphics");
		String name = graphics.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("graphicsWizard");
			display.error("Graphics name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
