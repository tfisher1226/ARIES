package nam.model.annotation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;


@SessionScoped
@Named("annotationConfigurationSection")
public class AnnotationRecord_ConfigurationSection extends AbstractWizardPage<Annotation> implements Serializable {
	
	private Annotation annotation;
	
	
	public AnnotationRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
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
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
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
