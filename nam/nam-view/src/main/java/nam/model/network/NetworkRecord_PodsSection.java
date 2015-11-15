package nam.model.network;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Network;

import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("networkPodsSection")
public class NetworkRecord_PodsSection extends AbstractWizardPage<Network> implements Serializable {
	
	private Network network;
	
	
	public NetworkRecord_PodsSection() {
		setName("Pods");
		setUrl("pods");
	}
	
	
	public Network getNetwork() {
		return network;
	}
	
	public void setNetwork(Network network) {
		this.network = network;
	}
	
	@Override
	public void initialize(Network network) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setNetwork(network);
	}
	
	@Override
	public void validate() {
		if (network == null) {
			validator.missing("Network");
		} else {
		}
	}
	
}
