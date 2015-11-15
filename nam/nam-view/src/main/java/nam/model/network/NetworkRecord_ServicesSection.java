package nam.model.network;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Network;
import nam.model.util.NetworkUtil;


@SessionScoped
@Named("networkServicesSection")
public class NetworkRecord_ServicesSection extends AbstractWizardPage<Network> implements Serializable {
	
	private Network network;
	
	
	public NetworkRecord_ServicesSection() {
		setName("Services");
		setUrl("services");
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
