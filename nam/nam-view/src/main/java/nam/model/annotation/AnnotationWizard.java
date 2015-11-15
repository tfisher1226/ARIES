package nam.model.annotation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Annotation;
import nam.model.Project;
import nam.model.util.AnnotationUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("annotationWizard")
@SuppressWarnings("serial")
public class AnnotationWizard extends AbstractDomainElementWizard<Annotation> implements Serializable {

	@Inject
	private AnnotationDataManager annotationDataManager;

	@Inject
	private AnnotationPageManager annotationPageManager;

	@Inject
	private AnnotationEventManager annotationEventManager;

	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Annotation";
	}

	@Override
	public String getUrlContext() {
		return annotationPageManager.getAnnotationWizardPage();
	}
	
	@Override
	public void initialize(Annotation annotation) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(annotationPageManager.getSections());
		super.initialize(annotation);
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
		annotationPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		annotationPageManager.updateState();
		return url;
	}

	@Override
	public String back() {
		String url = super.back();
		annotationPageManager.updateState();
		return url;
	}

	@Override
	public String next() {
		String url = super.next();
		annotationPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Annotation annotation = getInstance();
		annotationDataManager.saveAnnotation(annotation);
		annotationEventManager.fireSavedEvent(annotation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Annotation annotation = getInstance();
		//TODO take this out soon
		if (annotation == null)
			annotation = new Annotation();
		annotationEventManager.fireCancelledEvent(annotation);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Annotation annotation = selectionContext.getSelection("annotation");
		String name = AnnotationUtil.getLabel(annotation);
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("annotationWizard");
			display.error("Annotation name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}

}
