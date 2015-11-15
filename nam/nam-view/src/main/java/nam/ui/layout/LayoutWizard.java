package nam.ui.layout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.Layout;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;


@SessionScoped
@Named("layoutWizard")
@SuppressWarnings("serial")
public class LayoutWizard extends AbstractDomainElementWizard<Layout> implements Serializable {
	
	@Inject
	private LayoutDataManager layoutDataManager;
	
	@Inject
	private LayoutPageManager layoutPageManager;
	
	@Inject
	private LayoutEventManager layoutEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Layout";
	}
	
	@Override
	public String getUrlContext() {
		return layoutPageManager.getLayoutWizardPage();
	}
	
	@Override
	public void initialize(Layout layout) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(layoutPageManager.getSections());
		super.initialize(layout);
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
		layoutPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		layoutPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		layoutPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		layoutPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Layout layout = getInstance();
		layoutDataManager.saveLayout(layout);
		layoutEventManager.fireSavedEvent(layout);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Layout layout = getInstance();
		//TODO take this out soon
		if (layout == null)
			layout = new Layout();
		layoutEventManager.fireCancelledEvent(layout);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Layout layout = selectionContext.getSelection("layout");
		String name = layout.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("layoutWizard");
			display.error("Layout name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
