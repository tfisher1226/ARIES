package nam.model.project;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Project;
import nam.model.util.ProjectUtil;


@SessionScoped
@Named("projectIdentificationSection")
public class ProjectRecord_IdentificationSection extends AbstractWizardPage<Project> implements Serializable {
	
	private Project project;

	
	public ProjectRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setProject(project);
	}
	
	@Override
	public void validate() {
		if (project == null) {
			validator.missing("Project");
		} else {
			if (StringUtils.isEmpty(project.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(project.getLabel()))
				validator.missing("Label");
			if (StringUtils.isEmpty(project.getDomain()))
				validator.missing("Domain");
			if (StringUtils.isEmpty(project.getNamespace()))
				validator.missing("Namespace");
			if (StringUtils.isEmpty(project.getVersion()))
				validator.missing("Version");
		}
	}

}
