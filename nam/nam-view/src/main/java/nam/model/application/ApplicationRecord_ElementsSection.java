package nam.model.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Application;
import nam.model.util.ApplicationUtil;


@SessionScoped
@Named("applicationElementsSection")
public class ApplicationRecord_ElementsSection extends AbstractWizardPage<Application> implements Serializable {

	private Application application;

	
	public ApplicationRecord_ElementsSection() {
		setName("Elements");
		setUrl("elements");
	}

	
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public String getIcon() {
		return "/icons/nam/Element16.gif";
	}
	
	@Override
	public void initialize(Application application) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setApplication(application);
	}
	
	@Override
	public void validate() {
		if (application == null) {
			validator.missing("Application");
		} else {
		}
	}

}
