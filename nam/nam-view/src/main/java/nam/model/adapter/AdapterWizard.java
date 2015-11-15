package nam.model.adapter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Adapter;
import nam.model.Project;
import nam.model.util.AdapterUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("adapterWizard")
@SuppressWarnings("serial")
public class AdapterWizard extends AbstractDomainElementWizard<Adapter> implements Serializable {
	
	@Inject
	private AdapterDataManager adapterDataManager;
	
	@Inject
	private AdapterPageManager adapterPageManager;
	
	@Inject
	private AdapterEventManager adapterEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Adapter";
	}
	
	@Override
	public String getUrlContext() {
		return adapterPageManager.getAdapterWizardPage();
	}
	
	@Override
	public void initialize(Adapter adapter) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(adapterPageManager.getSections());
		super.initialize(adapter);
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
		adapterPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		adapterPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		adapterPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		adapterPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Adapter adapter = getInstance();
		adapterDataManager.saveAdapter(adapter);
		adapterEventManager.fireSavedEvent(adapter);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Adapter adapter = getInstance();
		//TODO take this out soon
		if (adapter == null)
			adapter = new Adapter();
		adapterEventManager.fireCancelledEvent(adapter);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Adapter adapter = selectionContext.getSelection("adapter");
		String name = adapter.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("adapterWizard");
			display.error("Adapter name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
