package nam.model.deployment;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Deployment;
import nam.model.Project;
import nam.model.util.DeploymentUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("deploymentWizard")
@SuppressWarnings("serial")
public class DeploymentWizard extends AbstractDomainElementWizard<Deployment> implements Serializable {
	
	@Inject
	private DeploymentDataManager deploymentDataManager;
	
	@Inject
	private DeploymentPageManager deploymentPageManager;
	
	@Inject
	private DeploymentEventManager deploymentEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Deployment";
	}
	
	@Override
	public String getUrlContext() {
		return deploymentPageManager.getDeploymentWizardPage();
	}
	
	@Override
	public void initialize(Deployment deployment) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(deploymentPageManager.getSections());
		super.initialize(deployment);
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
		deploymentPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		deploymentPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		deploymentPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		deploymentPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Deployment deployment = getInstance();
		deploymentDataManager.saveDeployment(deployment);
		deploymentEventManager.fireSavedEvent(deployment);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Deployment deployment = getInstance();
		//TODO take this out soon
		if (deployment == null)
			deployment = new Deployment();
		deploymentEventManager.fireCancelledEvent(deployment);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Deployment deployment = selectionContext.getSelection("deployment");
		String name = deployment.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("deploymentWizard");
			display.error("Deployment name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
