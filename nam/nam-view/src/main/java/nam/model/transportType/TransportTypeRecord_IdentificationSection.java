package nam.model.transportType;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.TransportType;


@SessionScoped
@Named("transportTypeIdentificationSection")
public class TransportTypeRecord_IdentificationSection extends AbstractWizardPage<TransportType> implements Serializable {
	
	private TransportType transportType;
	
	
	public TransportTypeRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public TransportType getTransportType() {
		return transportType;
	}
	
	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	
	@Override
	public void initialize(TransportType transportType) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setTransportType(transportType);
	}
	
	@Override
	public void validate() {
		if (transportType == null) {
			validator.missing("TransportType");
		} else {
		}
	}
	
}
