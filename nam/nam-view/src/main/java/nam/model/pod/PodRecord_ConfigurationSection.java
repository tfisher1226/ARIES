package nam.model.pod;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Pod;
import nam.model.util.PodUtil;


@SessionScoped
@Named("podConfigurationSection")
public class PodRecord_ConfigurationSection extends AbstractWizardPage<Pod> implements Serializable {
	
	private Pod pod;
	
	
	public PodRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Pod getPod() {
		return pod;
	}
	
	public void setPod(Pod pod) {
		this.pod = pod;
	}
	
	@Override
	public void initialize(Pod pod) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPod(pod);
	}
	
	@Override
	public void validate() {
		if (pod == null) {
			validator.missing("Pod");
		} else {
		}
	}
	
}
