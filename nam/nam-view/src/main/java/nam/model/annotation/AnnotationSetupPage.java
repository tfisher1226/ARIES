package nam.model.annotation;

import nam.model.Annotation;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SuppressWarnings("serial")
public class AnnotationSetupPage extends AbstractWizardPage<Annotation> {

	//private static Log log = LogFactory.getLog(AnnotationSetupPage.class);

	private Annotation annotation;

	
	public AnnotationSetupPage(String owner) {
		setTitle("Specify Annotation details.");
		setOwner(owner);
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	public void initialize(Annotation annotation) {
		setNextEnabled(false);
		setFinishEnabled(true);
		setAnnotation(annotation);
	}
	
	public void validate() {
		if (StringUtils.isEmpty(annotation.getSource()))
			validator.missing("Name");
//		if (StringUtils.isEmpty(annotation.getType()))
//			validator.missing("Type");
	}
	
}
