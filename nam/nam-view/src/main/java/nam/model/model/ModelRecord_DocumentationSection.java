package nam.model.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Model;
import nam.model.util.ModelUtil;


@SessionScoped
@Named("modelDocumentationSection")
public class ModelRecord_DocumentationSection extends AbstractWizardPage<Model> implements Serializable {
	
	private Model model;
	
	
	public ModelRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Model getModel() {
		return model;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	@Override
	public void initialize(Model model) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setModel(model);
	}
	
	@Override
	public void validate() {
		if (model == null) {
			validator.missing("Model");
		} else {
		}
	}
	
}
