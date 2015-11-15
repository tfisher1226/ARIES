package nam.model.annotation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Annotation;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("annotationEventManager")
public class AnnotationEventManager extends AbstractEventManager<Annotation> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Annotation getInstance() {
		return selectionContext.getSelection("annotation");
	}
	
	public void removeAnnotation() {
		Annotation annotation = getInstance();
		fireRemoveEvent(annotation);
	}
	
}
