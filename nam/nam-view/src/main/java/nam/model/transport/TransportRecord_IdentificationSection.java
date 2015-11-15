package nam.model.transport;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Transport;
import nam.model.util.TransportUtil;


@SessionScoped
@Named("transportIdentificationSection")
public class TransportRecord_IdentificationSection extends AbstractWizardPage<Transport> implements Serializable {
	
	private Transport transport;
	
	
	public TransportRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
