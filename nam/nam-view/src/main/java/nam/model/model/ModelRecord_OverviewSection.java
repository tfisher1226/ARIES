package nam.model.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Model;
import nam.model.util.ModelUtil;


@SessionScoped
@Named("modelOverviewSection")
public class ModelRecord_OverviewSection extends AbstractWizardPage<Model> implements Serializable {
	
	private Model model;
	
	
	public ModelRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setBackEnabled(false);
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
