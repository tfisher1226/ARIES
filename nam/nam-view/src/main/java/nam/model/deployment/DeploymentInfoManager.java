package nam.model.deployment;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Deployment;
import nam.model.Project;
import nam.model.util.DeploymentUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("deploymentInfoManager")
public class DeploymentInfoManager extends AbstractNamRecordManager<Deployment> implements Serializable {
	
	@Inject
	private DeploymentWizard deploymentWizard;
	
	@Inject
	private DeploymentDataManager deploymentDataManager;
	
	@Inject
	private DeploymentPageManager deploymentPageManager;
	
	@Inject
	private DeploymentEventManager deploymentEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private DeploymentHelper deploymentHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public DeploymentInfoManager() {
		setInstanceName("deployment");
	}
	
	
	public Deployment getDeployment() {
		return getRecord();
	}
	
	public Deployment getSelectedDeployment() {
		return selectionContext.getSelection("deployment");
	}
	
	@Override
	public Class<Deployment> getRecordClass() {
		return Deployment.class;
	}
	
	@Override
	public boolean isEmpty(Deployment deployment) {
		return deploymentHelper.isEmpty(deployment);
	}
	
	@Override
	public String toString(Deployment deployment) {
		return deploymentHelper.toString(deployment);
	}
	
	@Override
	public void initialize() {
		Deployment deployment = selectionContext.getSelection("deployment");
		if (deployment != null)
			initialize(deployment);
	}
	
	protected void initialize(Deployment deployment) {
		DeploymentUtil.initialize(deployment);
		deploymentWizard.initialize(deployment);
		setContext("deployment", deployment);
	}
	
	public void handleDeploymentSelected(@Observes @Selected Deployment deployment) {
		selectionContext.setSelection("deployment",  deployment);
		deploymentPageManager.updateState(deployment);
		deploymentPageManager.refreshMembers();
		setRecord(deployment);
	}
	
	@Override
	public String newRecord() {
		return newDeployment();
	}
	
	public String newDeployment() {
		try {
			Deployment deployment = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("deployment",  deployment);
			String url = deploymentPageManager.initializeDeploymentCreationPage(deployment);
			deploymentPageManager.pushContext(deploymentWizard);
			initialize(deployment);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Deployment create() {
		Deployment deployment = DeploymentUtil.create();
		return deployment;
	}
	
	@Override
	public Deployment clone(Deployment deployment) {
		deployment = DeploymentUtil.clone(deployment);
		return deployment;
	}
	
	@Override
	public String viewRecord() {
		return viewDeployment();
	}
	
	public String viewDeployment() {
		Deployment deployment = selectionContext.getSelection("deployment");
		String url = viewDeployment(deployment);
		return url;
	}
	
	public String viewDeployment(Deployment deployment) {
		try {
			String url = deploymentPageManager.initializeDeploymentSummaryView(deployment);
			deploymentPageManager.pushContext(deploymentWizard);
			initialize(deployment);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editDeployment();
	}
	
	public String editDeployment() {
		Deployment deployment = selectionContext.getSelection("deployment");
		String url = editDeployment(deployment);
		return url;
	}
	
	public String editDeployment(Deployment deployment) {
		try {
			//deployment = clone(deployment);
			selectionContext.resetOrigin();
			selectionContext.setSelection("deployment",  deployment);
			String url = deploymentPageManager.initializeDeploymentUpdatePage(deployment);
			deploymentPageManager.pushContext(deploymentWizard);
			initialize(deployment);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveDeployment() {
		Deployment deployment = getDeployment();
		if (validateDeployment(deployment)) {
			if (isImmediate())
				persistDeployment(deployment);
			outject("deployment", deployment);
		}
	}
	
	public void persistDeployment(Deployment deployment) {
		saveDeployment(deployment);
	}
	
	public void saveDeployment(Deployment deployment) {
		try {
			saveDeploymentToSystem(deployment);
			deploymentEventManager.fireAddedEvent(deployment);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveDeploymentToSystem(Deployment deployment) {
		deploymentDataManager.saveDeployment(deployment);
	}
	
	public void handleSaveDeployment(@Observes @Add Deployment deployment) {
		saveDeployment(deployment);
	}
	
	public void addDeployment(Deployment deployment) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichDeployment(Deployment deployment) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Deployment deployment) {
		return validateDeployment(deployment);
	}
	
	public boolean validateDeployment(Deployment deployment) {
		Validator validator = getValidator();
		boolean isValid = DeploymentUtil.validate(deployment);
		Display display = getFromSession("display");
		display.setModule("deploymentInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveDeployment() {
		display = getFromSession("display");
		display.setModule("deploymentInfo");
		Deployment deployment = selectionContext.getSelection("deployment");
		if (deployment == null) {
			display.error("Deployment record must be selected.");
		}
	}
	
	public String handleRemoveDeployment(@Observes @Remove Deployment deployment) {
		display = getFromSession("display");
		display.setModule("deploymentInfo");
		try {
			display.info("Removing Deployment "+DeploymentUtil.getLabel(deployment)+" from the system.");
			removeDeploymentFromSystem(deployment);
			selectionContext.clearSelection("deployment");
			deploymentEventManager.fireClearSelectionEvent();
			deploymentEventManager.fireRemovedEvent(deployment);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeDeploymentFromSystem(Deployment deployment) {
		if (deploymentDataManager.removeDeployment(deployment))
			setRecord(null);
	}
	
	public void cancelDeployment() {
		BeanContext.removeFromSession("deployment");
		deploymentPageManager.removeContext(deploymentWizard);
	}
	
}
