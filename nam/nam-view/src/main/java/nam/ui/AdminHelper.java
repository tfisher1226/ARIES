package nam.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import nam.model.TransportType;


@SessionScoped
@Named("adminHelper")
public class AdminHelper implements Serializable {
	
	@Produces
	@Named("transportTypes")
	public TransportType[] getTransportTypes() {
		return TransportType.values();
	}

	public String toString(Object object) {
		if (object == null)
			return null;
		return object.toString();
	}
	
}
