package nam.model.project;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Project;
import nam.model.util.ProjectUtil;


@SessionScoped
@Named("projectProvidersSection")
public class ProjectRecord_ProvidersSection extends AbstractWizardPage<Project> implements Serializable {

	private Project project;

	
	public ProjectRecord_ProvidersSection() {
		setName("Providers");
		setUrl("providers");
	}

	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public void initialize(Project project) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setProject(project);
	}
	
	@Override
	public void validate() {
		if (project == null) {
			validator.missing("Project");
		} else {
		}
	}

}
