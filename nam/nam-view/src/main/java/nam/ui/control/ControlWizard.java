package nam.ui.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.Control;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;


@SessionScoped
@Named("controlWizard")
@SuppressWarnings("serial")
public class ControlWizard extends AbstractDomainElementWizard<Control> implements Serializable {
	
	@Inject
	private ControlDataManager controlDataManager;
	
	@Inject
	private ControlPageManager controlPageManager;
	
	@Inject
	private ControlEventManager controlEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Control";
	}
	
	@Override
	public String getUrlContext() {
		return controlPageManager.getControlWizardPage();
	}
	
	@Override
	public void initialize(Control control) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(controlPageManager.getSections());
		super.initialize(control);
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
		controlPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		controlPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		controlPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		controlPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Control control = getInstance();
		controlDataManager.saveControl(control);
		controlEventManager.fireSavedEvent(control);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Control control = getInstance();
		//TODO take this out soon
		if (control == null)
			control = new Control();
		controlEventManager.fireCancelledEvent(control);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Control control = selectionContext.getSelection("control");
		String name = control.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("controlWizard");
			display.error("Control name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
