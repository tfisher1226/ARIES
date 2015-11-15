package nam.model.source;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Source;
import nam.model.util.ProjectUtil;
import nam.model.util.SourceUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("sourceInfoManager")
public class SourceInfoManager extends AbstractNamRecordManager<Source> implements Serializable {
	
	@Inject
	private SourceWizard sourceWizard;
	
	@Inject
	private SourceDataManager sourceDataManager;
	
	@Inject
	private SourcePageManager sourcePageManager;
	
	@Inject
	private SourceEventManager sourceEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private SourceHelper sourceHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public SourceInfoManager() {
		setInstanceName("source");
	}
	
	
	public Source getSource() {
		return getRecord();
	}
	
	public Source getSelectedSource() {
		return selectionContext.getSelection("source");
	}
	
	@Override
	public Class<Source> getRecordClass() {
		return Source.class;
	}
	
	@Override
	public boolean isEmpty(Source source) {
		return sourceHelper.isEmpty(source);
	}
	
	@Override
	public String toString(Source source) {
		return sourceHelper.toString(source);
	}
	
	@Override
	public void initialize() {
		Source source = selectionContext.getSelection("source");
		if (source != null)
			initialize(source);
	}
	
	protected void initialize(Source source) {
		SourceUtil.initialize(source);
		sourceWizard.initialize(source);
		setContext("source", source);
	}
	
	public void handleSourceSelected(@Observes @Selected Source source) {
		selectionContext.setSelection("source",  source);
		sourcePageManager.updateState(source);
		sourcePageManager.refreshMembers();
		setRecord(source);
	}
	
	@Override
	public String newRecord() {
		return newSource();
	}
	
	public String newSource() {
		try {
			Source source = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("source",  source);
			String url = sourcePageManager.initializeSourceCreationPage(source);
			sourcePageManager.pushContext(sourceWizard);
			initialize(source);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Source create() {
		Source source = SourceUtil.create();
		return source;
	}
	
	@Override
	public Source clone(Source source) {
		source = SourceUtil.clone(source);
		return source;
	}
	
	@Override
	public String viewRecord() {
		return viewSource();
	}
	
	public String viewSource() {
		Source source = selectionContext.getSelection("source");
		String url = viewSource(source);
		return url;
	}
	
	public String viewSource(Source source) {
		try {
			String url = sourcePageManager.initializeSourceSummaryView(source);
			sourcePageManager.pushContext(sourceWizard);
			initialize(source);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editSource();
	}
	
	public String editSource() {
		Source source = selectionContext.getSelection("source");
		String url = editSource(source);
		return url;
	}
	
	public String editSource(Source source) {
		try {
			//source = clone(source);
			selectionContext.resetOrigin();
			selectionContext.setSelection("source",  source);
			String url = sourcePageManager.initializeSourceUpdatePage(source);
			sourcePageManager.pushContext(sourceWizard);
			initialize(source);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveSource() {
		Source source = getSource();
		if (validateSource(source)) {
			if (isImmediate())
				persistSource(source);
			outject("source", source);
		}
	}
	
	public void persistSource(Source source) {
		saveSource(source);
	}
	
	public void saveSource(Source source) {
		try {
			saveSourceToSystem(source);
			sourceEventManager.fireAddedEvent(source);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveSourceToSystem(Source source) {
		sourceDataManager.saveSource(source);
	}
	
	public void handleSaveSource(@Observes @Add Source source) {
		saveSource(source);
	}
	
	public void addSource(Source source) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichSource(Source source) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Source source) {
		return validateSource(source);
	}
	
	public boolean validateSource(Source source) {
		Validator validator = getValidator();
		boolean isValid = SourceUtil.validate(source);
		Display display = getFromSession("display");
		display.setModule("sourceInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveSource() {
		display = getFromSession("display");
		display.setModule("sourceInfo");
		Source source = selectionContext.getSelection("source");
		if (source == null) {
			display.error("Source record must be selected.");
		}
	}
	
	public String handleRemoveSource(@Observes @Remove Source source) {
		display = getFromSession("display");
		display.setModule("sourceInfo");
		try {
			display.info("Removing Source "+SourceUtil.getLabel(source)+" from the system.");
			removeSourceFromSystem(source);
			selectionContext.clearSelection("source");
			sourceEventManager.fireClearSelectionEvent();
			sourceEventManager.fireRemovedEvent(source);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeSourceFromSystem(Source source) {
		if (sourceDataManager.removeSource(source))
			setRecord(null);
	}
	
	public void cancelSource() {
		BeanContext.removeFromSession("source");
		sourcePageManager.removeContext(sourceWizard);
	}
	
}
