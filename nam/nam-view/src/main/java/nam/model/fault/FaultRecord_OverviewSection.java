package nam.model.fault;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Fault;
import nam.model.util.FaultUtil;


@SessionScoped
@Named("faultOverviewSection")
public class FaultRecord_OverviewSection extends AbstractWizardPage<Fault> implements Serializable {
	
	private Fault fault;
	
	
	public FaultRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Fault getFault() {
		return fault;
	}
	
	public void setFault(Fault fault) {
		this.fault = fault;
	}
	
	@Override
	public void initialize(Fault fault) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setFault(fault);
	}
	
	@Override
	public void validate() {
		if (fault == null) {
			validator.missing("Fault");
		} else {
		}
	}
	
}
