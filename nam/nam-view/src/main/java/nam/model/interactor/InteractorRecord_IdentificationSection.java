package nam.model.interactor;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Interactor;
import nam.model.util.InteractorUtil;


@SessionScoped
@Named("interactorIdentificationSection")
public class InteractorRecord_IdentificationSection extends AbstractWizardPage<Interactor> implements Serializable {
	
	private Interactor interactor;
	
	
	public InteractorRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
