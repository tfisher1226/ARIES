package nam.model.application;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Project;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("applicationProjectSelectionSection")
public class ApplicationRecord_ProjectSelectionSection extends AbstractWizardPage<Application> {

	//private static Log log = LogFactory.getLog(ApplicationConfigPage.class);

	private Application application;

	
	public ApplicationRecord_ProjectSelectionSection() {
		//setTitle("Specify desired configuration.");
		setName("Project Selection");
		setUrl("projectSelection");
		//setOwner(owner);
	}
	
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public void initialize(Application application) {
		setBackVisible(false);
		setNextVisible(true);
		setNextEnabled(true);
		setFinishVisible(false);
		setApplication(application);
	}
	
	@Override
	public String next() {
		ApplicationInfoManager applicationInfoManager = BeanContext.getFromSession("applicationInfoManager");
		return applicationInfoManager.newApplication();
	}
	
	public void validate() {
		SelectionContext selectionContext = BeanContext.getFromSession("selectionContext");
		String projectName = selectionContext.getSelectedName();
		if (projectName == null) {
			validator.missing("Project");
		} else {
			//if (application.get
			//if (application.getConfiguration() == null)
			//	validator.missing("Configuration");
		}
	}
	
}
