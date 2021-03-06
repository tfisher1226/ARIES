package nam.model.transport;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Transport;
import nam.model.util.TransportUtil;


@SessionScoped
@Named("transportConfigurationSection")
public class TransportRecord_ConfigurationSection extends AbstractWizardPage<Transport> implements Serializable {
	
	private Transport transport;
	
	
	public TransportRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Transport getTransport() {
		return transport;
	}
	
	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	
	@Override
	public void initialize(Transport transport) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setTransport(transport);
	}
	
	@Override
	public void validate() {
		if (transport == null) {
			validator.missing("Transport");
		} else {
		}
	}
	
}
