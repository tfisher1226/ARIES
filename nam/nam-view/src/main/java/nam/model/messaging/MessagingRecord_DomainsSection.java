package nam.model.messaging;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Messaging;
import nam.model.util.MessagingUtil;


@SessionScoped
@Named("messagingDomainsSection")
public class MessagingRecord_DomainsSection extends AbstractWizardPage<Messaging> implements Serializable {
	
	private Messaging messaging;
	
	
	public MessagingRecord_DomainsSection() {
		setName("Domains");
		setUrl("domains");
	}
	
	
	public Messaging getMessaging() {
		return messaging;
	}
	
	public void setMessaging(Messaging messaging) {
		this.messaging = messaging;
	}
	
	@Override
	public void initialize(Messaging messaging) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setMessaging(messaging);
	}
	
	@Override
	public void validate() {
		if (messaging == null) {
			validator.missing("Messaging");
		} else {
		}
	}
	
}
