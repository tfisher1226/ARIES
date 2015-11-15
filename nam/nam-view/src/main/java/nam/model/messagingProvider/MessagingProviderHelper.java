package nam.model.messagingProvider;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;


@SessionScoped
@Named("messagingProviderHelper")
public class MessagingProviderHelper extends AbstractElementHelper<MessagingProvider> implements Serializable {
	
	@Override
	public boolean isEmpty(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.isEmpty(messagingProvider);
	}
	
	@Override
	public String toString(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.toString(messagingProvider);
	}
	
	@Override
	public String toString(Collection<MessagingProvider> messagingProviderList) {
		return MessagingProviderUtil.toString(messagingProviderList);
	}
	
	@Override
	public boolean validate(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.validate(messagingProvider);
	}
	
	@Override
	public boolean validate(Collection<MessagingProvider> messagingProviderList) {
		return MessagingProviderUtil.validate(messagingProviderList);
	}
	
}
