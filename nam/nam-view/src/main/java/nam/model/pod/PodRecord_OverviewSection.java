package nam.model.pod;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Pod;
import nam.model.util.PodUtil;


@SessionScoped
@Named("podOverviewSection")
public class PodRecord_OverviewSection extends AbstractWizardPage<Pod> implements Serializable {
	
	private Pod pod;
	
	
	public PodRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setBackEnabled(false);
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
