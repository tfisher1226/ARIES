package nam.model.transport;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Transport;
import nam.model.util.TransportUtil;


@SessionScoped
@Named("transportHelper")
public class TransportHelper extends AbstractElementHelper<Transport> implements Serializable {
	
	@Override
	public boolean isEmpty(Transport transport) {
		return TransportUtil.isEmpty(transport);
	}
	
	@Override
	public String toString(Transport transport) {
		return TransportUtil.toString(transport);
	}
	
	@Override
	public String toString(Collection<Transport> transportList) {
		return TransportUtil.toString(transportList);
	}
	
	@Override
	public boolean validate(Transport transport) {
		return TransportUtil.validate(transport);
	}
	
	@Override
	public boolean validate(Collection<Transport> transportList) {
		return TransportUtil.validate(transportList);
	}
	
}
