package nam.model.source;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Source;
import nam.model.util.SourceUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("sourceWizard")
@SuppressWarnings("serial")
public class SourceWizard extends AbstractDomainElementWizard<Source> implements Serializable {
	
	@Inject
	private SourceDataManager sourceDataManager;
	
	@Inject
	private SourcePageManager sourcePageManager;
	
	@Inject
	private SourceEventManager sourceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Source";
	}
	
	@Override
	public String getUrlContext() {
		return sourcePageManager.getSourceWizardPage();
	}
	
	@Override
	public void initialize(Source source) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(sourcePageManager.getSections());
		super.initialize(source);
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
		sourcePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		sourcePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		sourcePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		sourcePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Source source = getInstance();
		sourceDataManager.saveSource(source);
		sourceEventManager.fireSavedEvent(source);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Source source = getInstance();
		//TODO take this out soon
		if (source == null)
			source = new Source();
		sourceEventManager.fireCancelledEvent(source);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Source source = selectionContext.getSelection("source");
		String name = source.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("sourceWizard");
			display.error("Source name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
