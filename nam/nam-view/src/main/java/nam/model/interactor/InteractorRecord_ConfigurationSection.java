package nam.model.interactor;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Interactor;
import nam.model.util.InteractorUtil;


@SessionScoped
@Named("interactorConfigurationSection")
public class InteractorRecord_ConfigurationSection extends AbstractWizardPage<Interactor> implements Serializable {
	
	private Interactor interactor;
	
	
	public InteractorRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Interactor getInteractor() {
		return interactor;
	}
	
	public void setInteractor(Interactor interactor) {
		this.interactor = interactor;
	}
	
	@Override
	public void initialize(Interactor interactor) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setInteractor(interactor);
	}
	
	@Override
	public void validate() {
		if (interactor == null) {
			validator.missing("Interactor");
		} else {
		}
	}
	
}
