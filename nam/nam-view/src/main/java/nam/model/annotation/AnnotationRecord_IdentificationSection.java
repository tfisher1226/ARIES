package nam.model.annotation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;


@SessionScoped
@Named("annotationIdentificationSection")
public class AnnotationRecord_IdentificationSection extends AbstractWizardPage<Annotation> implements Serializable {
	
	private Annotation annotation;
	
	
	public AnnotationRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Annotation getAnnotation() {
		return annotation;
	}
	
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
	
	@Override
	public void initialize(Annotation annotation) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setAnnotation(annotation);
	}
	
	@Override
	public void validate() {
		if (annotation == null) {
			validator.missing("Annotation");
		} else {
		}
	}
	
}
