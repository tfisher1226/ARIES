package nam.model.master;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Master;
import nam.model.util.MasterUtil;


@SessionScoped
@Named("masterDocumentationSection")
public class MasterRecord_DocumentationSection extends AbstractWizardPage<Master> implements Serializable {
	
	private Master master;
	
	
	public MasterRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Master getMaster() {
		return master;
	}
	
	public void setMaster(Master master) {
		this.master = master;
	}
	
	@Override
	public void initialize(Master master) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setMaster(master);
	}
	
	@Override
	public void validate() {
		if (master == null) {
			validator.missing("Master");
		} else {
		}
	}
	
}
