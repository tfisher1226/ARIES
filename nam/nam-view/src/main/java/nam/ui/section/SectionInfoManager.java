package nam.ui.section;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Section;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;
import nam.ui.util.SectionUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;


@SessionScoped
@Named("sectionInfoManager")
public class SectionInfoManager extends AbstractNamRecordManager<Section> implements Serializable {
	
	@Inject
	private SectionWizard sectionWizard;
	
	@Inject
	private SectionDataManager sectionDataManager;
	
	@Inject
	private SectionPageManager sectionPageManager;
	
	@Inject
	private SectionEventManager sectionEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private SectionHelper sectionHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public SectionInfoManager() {
		setInstanceName("section");
	}
	
	
	public Section getSection() {
		return getRecord();
	}
	
	public Section getSelectedSection() {
		return selectionContext.getSelection("section");
	}
	
	@Override
	public Class<Section> getRecordClass() {
		return Section.class;
	}
	
	@Override
	public boolean isEmpty(Section section) {
		return sectionHelper.isEmpty(section);
	}
	
	@Override
	public String toString(Section section) {
		return sectionHelper.toString(section);
	}
	
	protected SectionHelper getSectionHelper() {
		return BeanContext.getFromSession("sectionHelper");
	}
	
	@Override
	public void initialize() {
		Section section = selectionContext.getSelection("section");
		if (section != null)
			initialize(section);
	}
	
	protected void initialize(Section section) {
		SectionUtil.initialize(section);
		sectionWizard.initialize(section);
		setContext("section", section);
	}
	
	public void handleSectionSelected(@Observes @Selected Section section) {
		selectionContext.setSelection("section",  section);
		sectionPageManager.updateState(section);
		sectionPageManager.refreshMembers();
		setRecord(section);
	}
	
	@Override
	public String newRecord() {
		return newSection();
	}
	
	public String newSection() {
		try {
			Section section = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("section",  section);
			String url = sectionPageManager.initializeSectionCreationPage(section);
			sectionPageManager.pushContext(sectionWizard);
			initialize(section);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Section create() {
		Section section = SectionUtil.create();
		return section;
	}
	
	@Override
	public Section clone(Section section) {
		section = SectionUtil.clone(section);
		return section;
	}
	
	@Override
	public String viewRecord() {
		return viewSection();
	}
	
	public String viewSection() {
		Section section = selectionContext.getSelection("section");
		String url = viewSection(section);
		return url;
	}
	
	public String viewSection(Section section) {
		try {
			String url = sectionPageManager.initializeSectionSummaryView(section);
			sectionPageManager.pushContext(sectionWizard);
			initialize(section);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editSection();
	}
	
	public String editSection() {
		Section section = selectionContext.getSelection("section");
		String url = editSection(section);
		return url;
	}
	
	public String editSection(Section section) {
		try {
			//section = clone(section);
			selectionContext.resetOrigin();
			selectionContext.setSelection("section",  section);
			String url = sectionPageManager.initializeSectionUpdatePage(section);
			sectionPageManager.pushContext(sectionWizard);
			initialize(section);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveSection() {
		Section section = getSection();
		if (validateSection(section)) {
			if (isImmediate())
				persistSection(section);
			outject("section", section);
		}
	}
	
	public void persistSection(Section section) {
		saveSection(section);
	}
	
	public void saveSection(Section section) {
		try {
			saveSectionToSystem(section);
			sectionEventManager.fireAddedEvent(section);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveSectionToSystem(Section section) {
		sectionDataManager.saveSection(section);
	}
	
	public void handleSaveSection(@Observes @Add Section section) {
		saveSection(section);
	}
	
	public void addSection(Section section) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichSection(Section section) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Section section) {
		return validateSection(section);
	}
	
	public boolean validateSection(Section section) {
		Validator validator = getValidator();
		boolean isValid = SectionUtil.validate(section);
		Display display = getFromSession("display");
		display.setModule("sectionInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveSection() {
		display = getFromSession("display");
		display.setModule("sectionInfo");
		Section section = selectionContext.getSelection("section");
		if (section == null) {
			display.error("Section record must be selected.");
		}
	}
	
	public String handleRemoveSection(@Observes @Remove Section section) {
		display = getFromSession("display");
		display.setModule("sectionInfo");
		try {
			display.info("Removing Section "+SectionUtil.getLabel(section)+" from the system.");
			removeSectionFromSystem(section);
			selectionContext.clearSelection("section");
			sectionEventManager.fireClearSelectionEvent();
			sectionEventManager.fireRemovedEvent(section);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeSectionFromSystem(Section section) {
		if (sectionDataManager.removeSection(section))
			setRecord(null);
	}
	
	public void cancelSection() {
		BeanContext.removeFromSession("section");
		sectionPageManager.removeContext(sectionWizard);
	}
	
}
