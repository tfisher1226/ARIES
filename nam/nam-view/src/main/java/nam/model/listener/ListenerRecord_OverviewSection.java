package nam.model.listener;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Listener;
import nam.model.util.ListenerUtil;


@SessionScoped
@Named("listenerOverviewSection")
public class ListenerRecord_OverviewSection extends AbstractWizardPage<Listener> implements Serializable {
	
	private Listener listener;
	
	
	public ListenerRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Listener getListener() {
		return listener;
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@Override
	public void initialize(Listener listener) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setListener(listener);
	}
	
	@Override
	public void validate() {
		if (listener == null) {
			validator.missing("Listener");
		} else {
		}
	}
	
}
