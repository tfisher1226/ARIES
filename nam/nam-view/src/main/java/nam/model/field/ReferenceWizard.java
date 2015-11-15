package nam.model.field;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Element;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.ui.design.AbstractDomainElementWizard;

import org.aries.runtime.BeanContext;
import org.aries.ui.Messages;
import org.jboss.seam.annotations.Begin;


//@SessionScoped
//@Named("referenceWizard")
//@SuppressWarnings("serial")
public class ReferenceWizard extends AbstractDomainElementWizard<Reference> {

	//private static Log log = LogFactory.getLog(FieldWizard.class);

	//@In(required=false, scope=ScopeType.SESSION, value="nam.selectedElement")
	private Element element;

	//@Out(required=false, value="nam.reference")
	private Reference reference;

	//@Out(required=true, value="nam.referenceSetupPage")
	private ReferenceSetupPage referenceSetupPage;

	//@Out(required=true, value="nam.referenceAnnotationPage")
	private ReferenceAnnotationPage referenceAnnotationPage;
	
	
	public ReferenceWizard() {
		referenceSetupPage = new ReferenceSetupPage("referenceWizard");
		referenceAnnotationPage = new ReferenceAnnotationPage("referenceWizard");
		addPage(referenceSetupPage);
		addPage(referenceAnnotationPage);
		reset();
	}

	public String getName() {
		return "Reference";
	}

	protected String getUrlContext() {
		return "/nam/design/reference/newReference.xhtml";
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public String refresh() {
		return super.refresh();
	}
	
	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}
	
	@Override
	public String getRefreshEvent() {
		return "nam.fieldsChanged";
	}

	public void initialize(Reference reference) {
		setOrigin(getSelectionContext().getUrl());
		referenceSetupPage.initialize(reference);
		referenceAnnotationPage.initialize(reference);
		referenceSetupPage.setVisible(true);
		referenceAnnotationPage.setVisible(false);
		this.reference = reference;
	}
	
	@Begin(join=true)
	public void newReference() {
		setTitle("New Reference");
		Messages messages = BeanContext.get("messages");
		messages.info("referenceWizard", "Enter information for new Reference.");
		initialize(new Reference());
	}

	@Begin(join=true)
	public void editReference() {
		editReference(reference);
	}
	
	@Begin(join=true)
	public void editReference(Reference reference) {
		setTitle("Reference: "+reference.getName()+"");
		initialize(reference);
	}
	
	public void saveReference() {
		List<Reference> references = ElementUtil.getReferences(element);
		if (!references.contains(reference))
			references.add(reference);
		saveProject();
	}
	
	@Override
	public String finish() {
		super.finish();
		saveReference();
		return super.finish();
	}

}
