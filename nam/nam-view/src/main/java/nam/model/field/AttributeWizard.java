package nam.model.field;

import java.util.List;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.util.ElementUtil;
import nam.ui.design.AbstractDomainElementWizard;

import org.aries.runtime.BeanContext;
import org.aries.ui.Messages;
import org.jboss.seam.annotations.Begin;


public class AttributeWizard extends AbstractDomainElementWizard {

	//private static Log log = LogFactory.getLog(AttributeWizard.class);

	//@In(required=false, scope=ScopeType.SESSION, value="nam.selectedElement")
	private Element element;

	//@Out(required=false, value="nam.attribute")
	private Attribute attribute;

	//@Out(required=true, value="nam.attributeSetupPage")
	private AttributeSetupPage attributeSetupPage;

	//@Out(required=true, value="nam.attributeAnnotationPage")
	private FieldAnnotationPage attributeAnnotationPage;
	
	
	public AttributeWizard() {
		attributeSetupPage = new AttributeSetupPage("attributeWizard");
		attributeAnnotationPage = new FieldAnnotationPage("attributeWizard");
		addPage(attributeSetupPage);
		addPage(attributeAnnotationPage);
		reset();
	}

	public String getName() {
		return "Attribute";
	}
	
	protected String getUrlContext() {
		return "/nam/design/attribute/newAttribute.xhtml";
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public String refresh() {
		return super.refresh();
	}
	
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public String getRefreshEvent() {
		return "nam.fieldsChanged";
	}

	public void initialize(Attribute attribute) {
		setOrigin(getSelectionContext().getUrl());
		attributeSetupPage.initialize(attribute);
		attributeAnnotationPage.initialize(attribute);
		attributeSetupPage.setVisible(true);
		attributeAnnotationPage.setVisible(false);
		this.attribute = attribute;
	}
	
	@Begin(join=true)
	public void newAttribute() {
		setTitle("New Attribute");
		Messages messages = BeanContext.get("messages");
		messages.info("attributeWizard", "Enter information for new Attribute.");
		initialize(new Attribute());
	}

	@Begin(join=true)
	public void editAttribute() {
		editAttribute(attribute);
	}
	
	@Begin(join=true)
	public void editAttribute(Attribute attribute) {
		setTitle("Attribute: "+attribute.getName()+"");
		initialize(attribute);
	}
	
	public void saveAttribute() {
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		if (!attributes.contains(attribute))
			attributes.add(attribute);
		saveProject();
	}

	@Override
	public String finish() {
		if (isValid())
			saveAttribute();
		return super.finish();
	}

}
