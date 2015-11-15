package nam.model.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Application;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Application;
import nam.model.util.ApplicationUtil;


@SessionScoped
@Named("applicationIdentificationSection")
public class ApplicationRecord_IdentificationSection extends AbstractWizardPage<Application> implements Serializable {
	
	private Application application;

	
	public ApplicationRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}

	
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public void initialize(Application application) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setApplication(application);
	}
	
	@Override
	public void validate() {
		if (application == null) {
			validator.missing("Application");
		} else {
			if (StringUtils.isEmpty(application.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(application.getGroupId()))
				validator.missing("Group ID");
			if (StringUtils.isEmpty(application.getArtifactId()))
				validator.missing("Artifact ID");
			if (StringUtils.isEmpty(application.getNamespace()))
				validator.missing("Namespace");
			if (StringUtils.isEmpty(application.getVersion()))
				validator.missing("Version");
		}
	}

}
