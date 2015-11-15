package nam.model.network;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Network;
import nam.model.util.NetworkUtil;


@SessionScoped
@Named("networkHelper")
public class NetworkHelper extends AbstractElementHelper<Network> implements Serializable {
	
	@Override
	public boolean isEmpty(Network network) {
		return NetworkUtil.isEmpty(network);
	}
	
	@Override
	public String toString(Network network) {
		return NetworkUtil.toString(network);
	}
	
	@Override
	public String toString(Collection<Network> networkList) {
		return NetworkUtil.toString(networkList);
	}
	
	@Override
	public boolean validate(Network network) {
		return NetworkUtil.validate(network);
	}
	
	@Override
	public boolean validate(Collection<Network> networkList) {
		return NetworkUtil.validate(networkList);
	}
	
}
