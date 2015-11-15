package nam.model.project;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("projectWizard")
@SuppressWarnings("serial")
public class ProjectWizard extends AbstractDomainElementWizard<Project> implements Serializable {
	
	@Inject
	private ProjectDataManager projectDataManager;
	
	@Inject
	private ProjectPageManager projectPageManager;
	
	@Inject
	private ProjectEventManager projectEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Project";
	}
	
	@Override
	public String getUrlContext() {
		return projectPageManager.getProjectWizardPage();
	}

	@Override
	public void initialize(Project project) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(projectPageManager.getSections());
		super.initialize(project);
	}

	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}

	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}

	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}

	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		projectPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		projectPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		projectPageManager.updateState();
		return url;
	}

	@Override
	public String next() {
		String url = super.next();
		projectPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	public String finish() {
		Project project = getInstance();
		projectDataManager.saveProject(project);
		projectEventManager.fireSavedEvent(project);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Project project = getInstance();
		//TODO take this out soon
		if (project == null)
			project = new Project();
		projectEventManager.fireCancelledEvent(project);
		String url = selectionContext.popOrigin();
		return url;
	}

	public String populateDefaultValues() {
		Project project = selectionContext.getSelection("project");
		String name = project.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("projectWizard");
			display.error("Project name must be specified");
			return null;
		}
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		
		project.setName(nameUncapped);
		project.setLabel(nameCapped);
		project.setDomain("domain1");
		project.setNamespace("http://domain1/"+nameUncapped);
		project.setVersion("0.0.1-SNAPSHOT");
		project.setDescription("This is a Project named \""+name+"\"");
		return getUrl();
	}
	
	//protected void postProjectAddEvent(Project project) {
	//	projectEventManager.fireAddEvent(project);
	//}
	
}
