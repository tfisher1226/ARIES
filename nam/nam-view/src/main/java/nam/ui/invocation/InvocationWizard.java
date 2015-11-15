package nam.ui.invocation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.Invocation;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;


@SessionScoped
@Named("invocationWizard")
@SuppressWarnings("serial")
public class InvocationWizard extends AbstractDomainElementWizard<Invocation> implements Serializable {
	
	@Inject
	private InvocationDataManager invocationDataManager;
	
	@Inject
	private InvocationPageManager invocationPageManager;
	
	@Inject
	private InvocationEventManager invocationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Invocation";
	}
	
	@Override
	public String getUrlContext() {
		return invocationPageManager.getInvocationWizardPage();
	}
	
	@Override
	public void initialize(Invocation invocation) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(invocationPageManager.getSections());
		super.initialize(invocation);
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
		invocationPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		invocationPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		invocationPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		invocationPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Invocation invocation = getInstance();
		invocationDataManager.saveInvocation(invocation);
		invocationEventManager.fireSavedEvent(invocation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Invocation invocation = getInstance();
		//TODO take this out soon
		if (invocation == null)
			invocation = new Invocation();
		invocationEventManager.fireCancelledEvent(invocation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Invocation invocation = selectionContext.getSelection("invocation");
		String name = invocation.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("invocationWizard");
			display.error("Invocation name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
