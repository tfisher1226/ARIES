package nam.model.field;

import nam.model.Attribute;
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
@Name("attributeManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class AttributeManager extends AbstractDomainElementManager {

	//private static Log log = LogFactory.getLog(AttributeManager.class);

	@Out(required=false, value="nam.attribute")
	private Attribute transientAttribute;
	
	@Out(required=false, value="nam.selectedAttribute")
	private Attribute selectedAttribute;

	@Out(required=true, value="nam.attributeSetupPanel")
	private AttributeSetupPage attributeSetupPanel;

	@Out(required=true, value="nam.attributeAnnotationPanel")
	private FieldAnnotationPage attributeAnnotationPanel;
	
	
	public AttributeManager() {
		attributeSetupPanel = new AttributeSetupPage("attributeManager");
		attributeAnnotationPanel = new FieldAnnotationPage("attributeManager");
	}
	
	public void initialize(Attribute attribute) {
		attributeSetupPanel.initialize(attribute);
		attributeAnnotationPanel.initialize(attribute);
		attributeSetupPanel.setVisible(true);
		attributeAnnotationPanel.setVisible(true);
		this.transientAttribute = attribute;
	}

	protected void refresh() {
		//nothing for now
	}

	public Attribute getAttribute() {
		return transientAttribute;
	}

	public Attribute getSelectedAttribute() {
		return selectedAttribute;
	}
	
	public void setAttribute(Attribute attribute) {
		this.transientAttribute = attribute;
	}

	public void selectAttribute(Attribute attribute) {
		this.selectedAttribute = attribute;
		initialize(attribute);
	}

	@Override
	protected String getRefreshEvent() {
		return "nam.fieldsChanged";
	}
	
	@Begin(join=true)
	public void editAttribute() {
		editAttribute(transientAttribute);
	}
	
	@Begin(join=true)
	public void editAttribute(Attribute attribute) {
		setTitle("Attribute: "+attribute.getName()+"");
		initialize(attribute);
	}
	
	public void saveAttribute() {
		if (attributeSetupPanel.isValid()) {
			saveProject();
		}
	}

	@Override
	public void submit() {
		super.submit();
		saveAttribute();
		refresh();
	}
	
}
