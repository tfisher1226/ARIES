package nam.model.messagingProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;


@SessionScoped
@Named("messagingProviderIdentificationSection")
public class MessagingProviderRecord_IdentificationSection extends AbstractWizardPage<MessagingProvider> implements Serializable {
	
	private MessagingProvider messagingProvider;
	
	
	public MessagingProviderRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public MessagingProvider getMessagingProvider() {
		return messagingProvider;
	}
	
	public void setMessagingProvider(MessagingProvider messagingProvider) {
		this.messagingProvider = messagingProvider;
	}
	
	@Override
	public void initialize(MessagingProvider messagingProvider) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setMessagingProvider(messagingProvider);
	}
	
	@Override
	public void validate() {
		if (messagingProvider == null) {
			validator.missing("MessagingProvider");
		} else {
		}
	}
	
}
