package nam.model.reference;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Reference;
import nam.model.util.ReferenceUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("referenceWizard")
@SuppressWarnings("serial")
public class ReferenceWizard extends AbstractDomainElementWizard<Reference> implements Serializable {
	
	@Inject
	private ReferenceDataManager referenceDataManager;
	
	@Inject
	private ReferencePageManager referencePageManager;
	
	@Inject
	private ReferenceEventManager referenceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Reference";
	}
	
	@Override
	public String getUrlContext() {
		return referencePageManager.getReferenceWizardPage();
	}
	
	@Override
	public void initialize(Reference reference) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(referencePageManager.getSections());
		super.initialize(reference);
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
		referencePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		referencePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		referencePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		referencePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Reference reference = getInstance();
		referenceDataManager.saveReference(reference);
		referenceEventManager.fireSavedEvent(reference);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Reference reference = getInstance();
		//TODO take this out soon
		if (reference == null)
			reference = new Reference();
		referenceEventManager.fireCancelledEvent(reference);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Reference reference = selectionContext.getSelection("reference");
		String name = reference.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("referenceWizard");
			display.error("Reference name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
