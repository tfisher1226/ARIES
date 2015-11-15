package nam.model.annotation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("annotationDataManager")
public class AnnotationDataManager implements Serializable {
	
	@Inject
	private AnnotationEventManager annotationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Annotation> getAnnotationList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Annotation> getDefaultList() {
		return null;
	}
	
	public void saveAnnotation(Annotation annotation) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeAnnotation(Annotation annotation) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
