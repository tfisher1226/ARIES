package nam.model.listener;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Listener;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("listenerChannelsSection")
public class ListenerRecord_ChannelsSection extends AbstractWizardPage<Listener> {

	private Listener listener;


	public ListenerRecord_ChannelsSection() {
		//setTitle("Specify Listener information.");
		setName("Channels");
		setUrl("channels");
		//setOwner(owner);
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	public void initialize(Listener listener) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setListener(listener);
	}
	
	public void validate() {
		if (listener == null) {
			validator.missing("Listener");
		} else {
		}
	}
	
}
