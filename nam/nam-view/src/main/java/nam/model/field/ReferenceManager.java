package nam.model.field;

import nam.model.Reference;
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
@Name("referenceManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class ReferenceManager extends AbstractDomainElementManager {

	//private static Log log = LogFactory.getLog(ReferenceManager.class);

	@Out(required=false, value="nam.reference")
	private Reference transientReference;
	
	@Out(required=false, value="nam.selectedReference")
	private Reference selectedReference;

	@Out(required=true, value="nam.referenceSetupPanel")
	private ReferenceSetupPage referenceSetupPanel;

	@Out(required=true, value="nam.referenceAnnotationPanel")
	private FieldAnnotationPage referenceAnnotationPanel;
	
	
	public ReferenceManager() {
		referenceSetupPanel = new ReferenceSetupPage("referenceManager");
		referenceAnnotationPanel = new FieldAnnotationPage("referenceManager");
	}
	
	public void initialize(Reference reference) {
		referenceSetupPanel.initialize(reference);
		referenceAnnotationPanel.initialize(reference);
		referenceSetupPanel.setVisible(true);
		referenceAnnotationPanel.setVisible(true);
		this.transientReference = reference;
	}

	protected void refresh() {
		//nothing for now
	}

	public Reference getReference() {
		return transientReference;
	}

	public Reference getSelectedReference() {
		return selectedReference;
	}
	
	public void setReference(Reference reference) {
		this.transientReference = reference;
	}

	public void selectReference(Reference reference) {
		this.selectedReference = reference;
		initialize(reference);
	}

	@Override
	protected String getRefreshEvent() {
		return "nam.fieldsChanged";
	}
	
	@Begin(join=true)
	public void editReference() {
		editReference(transientReference);
	}
	
	@Begin(join=true)
	public void editReference(Reference reference) {
		setTitle("Reference: "+reference.getName()+"");
		initialize(reference);
	}
	
	public void saveReference() {
		if (referenceSetupPanel.isValid()) {
			saveProject();
		}
	}

	@Override
	public void submit() {
		super.submit();
		saveReference();
		refresh();
	}
	
}
