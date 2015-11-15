package nam.model.annotation;

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

import nam.model.Annotation;
import nam.model.Project;
import nam.model.util.AnnotationUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("annotationInfoManager")
public class AnnotationInfoManager extends AbstractNamRecordManager<Annotation> implements Serializable {
	
	@Inject
	private AnnotationWizard annotationWizard;
	
	@Inject
	private AnnotationDataManager annotationDataManager;
	
	@Inject
	private AnnotationPageManager annotationPageManager;
	
	@Inject
	private AnnotationEventManager annotationEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private AnnotationHelper annotationHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public AnnotationInfoManager() {
		setInstanceName("annotation");
	}
	
	
	public Annotation getAnnotation() {
		return getRecord();
	}
	
	public Annotation getSelectedAnnotation() {
		return selectionContext.getSelection("annotation");
	}
	
	@Override
	public Class<Annotation> getRecordClass() {
		return Annotation.class;
	}
	
	@Override
	public boolean isEmpty(Annotation annotation) {
		return annotationHelper.isEmpty(annotation);
	}
	
	@Override
	public String toString(Annotation annotation) {
		return annotationHelper.toString(annotation);
	}
	
	@Override
	public void initialize() {
		Annotation annotation = selectionContext.getSelection("annotation");
		if (annotation != null)
			initialize(annotation);
	}
	
	protected void initialize(Annotation annotation) {
		AnnotationUtil.initialize(annotation);
		annotationWizard.initialize(annotation);
		setContext("annotation", annotation);
	}
	
	public void handleAnnotationSelected(@Observes @Selected Annotation annotation) {
		selectionContext.setSelection("annotation",  annotation);
		annotationPageManager.updateState(annotation);
		annotationPageManager.refreshMembers();
		setRecord(annotation);
	}
	
	@Override
	public String newRecord() {
		return newAnnotation();
	}
	
	public String newAnnotation() {
		try {
			Annotation annotation = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("annotation",  annotation);
			String url = annotationPageManager.initializeAnnotationCreationPage(annotation);
			annotationPageManager.pushContext(annotationWizard);
			initialize(annotation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Annotation create() {
		Annotation annotation = AnnotationUtil.create();
		return annotation;
	}
	
	@Override
	public Annotation clone(Annotation annotation) {
		annotation = AnnotationUtil.clone(annotation);
		return annotation;
	}
	
	@Override
	public String viewRecord() {
		return viewAnnotation();
	}
	
	public String viewAnnotation() {
		Annotation annotation = selectionContext.getSelection("annotation");
		String url = viewAnnotation(annotation);
		return url;
	}
	
	public String viewAnnotation(Annotation annotation) {
		try {
			String url = annotationPageManager.initializeAnnotationSummaryView(annotation);
			annotationPageManager.pushContext(annotationWizard);
			initialize(annotation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editAnnotation();
	}
	
	public String editAnnotation() {
		Annotation annotation = selectionContext.getSelection("annotation");
		String url = editAnnotation(annotation);
		return url;
	}
	
	public String editAnnotation(Annotation annotation) {
		try {
			//annotation = clone(annotation);
			selectionContext.resetOrigin();
			selectionContext.setSelection("annotation",  annotation);
			String url = annotationPageManager.initializeAnnotationUpdatePage(annotation);
			annotationPageManager.pushContext(annotationWizard);
			initialize(annotation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveAnnotation() {
		Annotation annotation = getAnnotation();
		if (validateAnnotation(annotation)) {
			saveAnnotation(annotation);
		}
	}
	
	public void persistAnnotation(Annotation annotation) {
		saveAnnotation(annotation);
	}
	
	public void saveAnnotation(Annotation annotation) {
		try {
			saveAnnotationToSystem(annotation);
			annotationEventManager.fireAddedEvent(annotation);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveAnnotationToSystem(Annotation annotation) {
		annotationDataManager.saveAnnotation(annotation);
	}
	
	public void handleSaveAnnotation(@Observes @Add Annotation annotation) {
		saveAnnotation(annotation);
	}
	
	public void enrichAnnotation(Annotation annotation) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Annotation annotation) {
		return validateAnnotation(annotation);
	}
	
	public boolean validateAnnotation(Annotation annotation) {
		Validator validator = getValidator();
		boolean isValid = AnnotationUtil.validate(annotation);
		Display display = getFromSession("display");
		display.setModule("annotationInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveAnnotation() {
		display = getFromSession("display");
		display.setModule("annotationInfo");
		Annotation annotation = selectionContext.getSelection("annotation");
		if (annotation == null) {
			display.error("Annotation record must be selected.");
		}
	}
	
	public String handleRemoveAnnotation(@Observes @Remove Annotation annotation) {
		display = getFromSession("display");
		display.setModule("annotationInfo");
		try {
			display.info("Removing Annotation "+AnnotationUtil.getLabel(annotation)+" from the system.");
			removeAnnotationFromSystem(annotation);
			selectionContext.clearSelection("annotation");
			annotationEventManager.fireClearSelectionEvent();
			annotationEventManager.fireRemovedEvent(annotation);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeAnnotationFromSystem(Annotation annotation) {
		if (annotationDataManager.removeAnnotation(annotation))
			setRecord(null);
	}
	
	public void cancelAnnotation() {
		BeanContext.removeFromSession("annotation");
		annotationPageManager.removeContext(annotationWizard);
	}
	
}
