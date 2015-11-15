package nam.model.listener;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Listener;
import nam.model.util.ListenerUtil;


@SessionScoped
@Named("listenerIdentificationSection")
public class ListenerRecord_IdentificationSection extends AbstractWizardPage<Listener> implements Serializable {
	
	private Listener listener;
	
	
	public ListenerRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setListener(listener);
	}
	
	@Override
	public void validate() {
		if (listener == null) {
			validator.missing("Listener");
		} else {
			if (StringUtils.isEmpty(listener.getName()))
				validator.missing("Name");
		}
	}
	
}
