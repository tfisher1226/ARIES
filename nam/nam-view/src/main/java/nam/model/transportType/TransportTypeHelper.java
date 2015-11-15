package nam.model.transportType;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.TransportType;
import nam.model.util.TransportTypeUtil;


@SessionScoped
@Named("transportTypeHelper")
public class TransportTypeHelper extends AbstractEnumerationHelper<TransportType> implements Serializable {
	
	@Produces
	public TransportType[] getTransportTypeArray() {
		return TransportType.values();
	}
	
	@Override
	public String toString(TransportType transportType) {
		return transportType.name();
	}
	
	@Override
	public String toString(Collection<TransportType> transportTypeList) {
		return TransportTypeUtil.toString(transportTypeList);
	}
	
}
