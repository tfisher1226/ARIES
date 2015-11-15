package nam.model.deployment;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;


@SessionScoped
@Named("deploymentOverviewSection")
public class DeploymentRecord_OverviewSection extends AbstractWizardPage<Deployment> implements Serializable {
	
	private Deployment deployment;
	
	
	public DeploymentRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Deployment getDeployment() {
		return deployment;
	}
	
	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}
	
	@Override
	public void initialize(Deployment deployment) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setDeployment(deployment);
	}
	
	@Override
	public void validate() {
		if (deployment == null) {
			validator.missing("Deployment");
		} else {
		}
	}
	
}
