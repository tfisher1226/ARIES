package nam.ui.section;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.Section;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;


@SessionScoped
@Named("sectionWizard")
@SuppressWarnings("serial")
public class SectionWizard extends AbstractDomainElementWizard<Section> implements Serializable {
	
	@Inject
	private SectionDataManager sectionDataManager;
	
	@Inject
	private SectionPageManager sectionPageManager;
	
	@Inject
	private SectionEventManager sectionEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Section";
	}
	
	@Override
	public String getUrlContext() {
		return sectionPageManager.getSectionWizardPage();
	}
	
	@Override
	public void initialize(Section section) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(sectionPageManager.getSections());
		super.initialize(section);
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
		sectionPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		sectionPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		sectionPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		sectionPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Section section = getInstance();
		sectionDataManager.saveSection(section);
		sectionEventManager.fireSavedEvent(section);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Section section = getInstance();
		//TODO take this out soon
		if (section == null)
			section = new Section();
		sectionEventManager.fireCancelledEvent(section);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Section section = selectionContext.getSelection("section");
		String name = section.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("sectionWizard");
			display.error("Section name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
