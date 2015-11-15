package nam.model.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Application;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("applicationWizard")
@SuppressWarnings("serial")
public class ApplicationWizard extends AbstractDomainElementWizard<Application> implements Serializable {

	@Inject
	private ApplicationDataManager applicationDataManager;
	
	@Inject
	private ApplicationPageManager applicationPageManager;

	@Inject
	private ApplicationEventManager applicationEventManager;

	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Application";
	}
	
	@Override
	public String getUrlContext() {
		return applicationPageManager.getApplicationWizardPage();
	}

	@Override
	public void initialize(Application application) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(applicationPageManager.getSections());
		super.initialize(application);
		//configurationTreeManager.refresh(project);
		//configurationTreeManager.setRerenderList("applicationConfigPage");
		//configurationTreeManager.initialize(createConfigurationSelectListener());
	}

//	Handler<Object> createConfigurationSelectListener() {
//		return new Handler<Object>() {
//			public void handle(Object artifact) {
//				if (artifact instanceof Configuration) {
//					Configuration configuration = (Configuration) artifact;
//					Application application = getInstance();
//					application.setConfiguration(configuration);
//				}
//			}
//		};
//	}

//	@Override
//	public String getRefreshEvent() {
//		return "nam.applicationsChanged";
//	}
	
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
		applicationPageManager.updateState();
		return url;
	}

	@Override
	public String first() {
		String url = super.first();
		applicationPageManager.updateState();
		return url;
	}

	@Override
	public String back() {
		String url = super.back();
		applicationPageManager.updateState();
		return url;
	}

	@Override
	public String next() {
		String url = super.next();
		applicationPageManager.updateState();
		return url;
	}
	
	@Override
	//TODO Make this private - should only happen once per request - no EL should call this
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	public String finish() {
		Application application = getInstance();
		applicationDataManager.saveApplication(application);
		applicationEventManager.fireSavedEvent(application);
		String url = selectionContext.popOrigin();
		return url;
	}

	@Override
	public String cancel() {
		Application application = getInstance();
		//TODO take this out soon
		if (application == null)
			application = new Application();
		applicationEventManager.fireCancelledEvent(application);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Application application = selectionContext.getSelection("application");
		String name = application.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("applicationWizard");
			display.error("Application name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);

		application.setName(nameUncapped);
		application.setLabel(nameCapped);
		application.setArtifactId(nameUncapped);
		application.setGroupId(project.getDomain());
		application.setNamespace(project.getNamespace()+"/"+nameUncapped);
		application.setContextRoot("/"+nameUncapped);
		application.setDescription("This is an Application named \""+nameUncapped+"\"");
		application.setVersion("0.0.1-SNAPSHOT");
		return getUrl();
	}

}
