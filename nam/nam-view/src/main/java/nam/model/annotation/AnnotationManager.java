package nam.model.annotation;

import nam.model.Annotation;
import nam.ui.design.AbstractDomainElementManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


@Startup
@AutoCreate
@Name("annotationManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class AnnotationManager extends AbstractDomainElementManager {

	//private static Log log = LogFactory.getLog(FieldManager.class);

	@Out(required=false, value="nam.annotation")
	private Annotation transientAnnotation;
	
	@Out(required=false, value="nam.selectedAnnotation")
	private Annotation selectedAnnotation;

	@Out(required=true, value="nam.annotationSetupPanel")
	private AnnotationSetupPage annotationSetupPanel;

	//@In(required=true)
	//private PropertyListManager propertyListManager;

	
	public AnnotationManager() {
		annotationSetupPanel = new AnnotationSetupPage("annotationManager");
	}

	public void initialize(Annotation annotation) {
		annotationSetupPanel.initialize(annotation);
		annotationSetupPanel.setVisible(true);
		this.selectedAnnotation = annotation;
	}

	protected void refresh() {
		//nothing for now
	}

	public Annotation getAnnotation() {
		return transientAnnotation;
	}

	public Annotation getSelectedAnnotation() {
		return selectedAnnotation;
	}
	
	public void setAnnotation(Annotation annotation) {
		this.transientAnnotation = annotation;
	}

	public void selectAnnotation(Annotation annotation) {
		this.selectedAnnotation = annotation;
		initialize(annotation);
	}
	
	public String getTitle() {
		if (transientAnnotation != null)
			return "Annotation: "+transientAnnotation.getSource();
		return null;
	}

	@Override
	protected String getRefreshEvent() {
		return "nam.fieldsChanged";
	}
	
	@Begin(join=true)
	public void editAnnotation() {
		editAnnotation(transientAnnotation);
	}
	
	@Begin(join=true)
	public void editAnnotation(Annotation annotation) {
		setTitle("Annotation: "+annotation.getSource()+"");
		initialize(annotation);
	}
	
	public void saveAnnotation() {
		if (annotationSetupPanel.isValid()) {
			saveProject();
		}
	}

	@Override
	public void submit() {
		super.submit();
		saveAnnotation();
		refresh();
	}
	
}
