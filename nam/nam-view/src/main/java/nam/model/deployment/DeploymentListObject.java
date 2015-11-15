package nam.model.deployment;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;


public class DeploymentListObject extends AbstractListObject<Deployment> implements Comparable<DeploymentListObject>, Serializable {
	
	private Deployment deployment;
	
	
	public DeploymentListObject(Deployment deployment) {
		this.deployment = deployment;
	}
	
	
	public Deployment getDeployment() {
		return deployment;
	}
	
	@Override
	public Object getKey() {
		return getKey(deployment);
	}
	
	public Object getKey(Deployment deployment) {
		return DeploymentUtil.getKey(deployment);
	}
	
	@Override
	public String getLabel() {
		return getLabel(deployment);
	}
	
	public String getLabel(Deployment deployment) {
		return DeploymentUtil.getLabel(deployment);
	}
	
	@Override
	public String toString() {
		return toString(deployment);
	}
	
	@Override
	public String toString(Deployment deployment) {
		return DeploymentUtil.toString(deployment);
	}
	
	@Override
	public int compareTo(DeploymentListObject other) {
		Object thisKey = getKey(this.deployment);
		Object otherKey = getKey(other.deployment);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		DeploymentListObject other = (DeploymentListObject) object;
		Object thisKey = DeploymentUtil.getKey(this.deployment);
		Object otherKey = DeploymentUtil.getKey(other.deployment);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
