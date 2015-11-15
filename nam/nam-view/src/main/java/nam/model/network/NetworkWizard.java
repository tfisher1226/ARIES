package nam.model.network;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Network;
import nam.model.Project;
import nam.model.util.NetworkUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("networkWizard")
@SuppressWarnings("serial")
public class NetworkWizard extends AbstractDomainElementWizard<Network> implements Serializable {
	
	@Inject
	private NetworkDataManager networkDataManager;
	
	@Inject
	private NetworkPageManager networkPageManager;
	
	@Inject
	private NetworkEventManager networkEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Network";
	}
	
	@Override
	public String getUrlContext() {
		return networkPageManager.getNetworkWizardPage();
	}
	
	@Override
	public void initialize(Network network) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(networkPageManager.getSections());
		super.initialize(network);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		networkPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		networkPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		networkPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		networkPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Network network = getInstance();
		networkDataManager.saveNetwork(network);
		networkEventManager.fireSavedEvent(network);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Network network = getInstance();
		//TODO take this out soon
		if (network == null)
			network = new Network();
		networkEventManager.fireCancelledEvent(network);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Network network = selectionContext.getSelection("network");
		String name = network.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("networkWizard");
			display.error("Network name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
