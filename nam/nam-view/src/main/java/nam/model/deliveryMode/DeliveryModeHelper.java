package nam.model.deliveryMode;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.DeliveryMode;
import nam.model.util.DeliveryModeUtil;


@SessionScoped
@Named("deliveryModeHelper")
public class DeliveryModeHelper extends AbstractEnumerationHelper<DeliveryMode> implements Serializable {
	
	@Produces
	public DeliveryMode[] getDeliveryModeArray() {
		return DeliveryMode.values();
	}
	
	@Override
	public String toString(DeliveryMode deliveryMode) {
		return deliveryMode.name();
	}
	
	@Override
	public String toString(Collection<DeliveryMode> deliveryModeList) {
		return DeliveryModeUtil.toString(deliveryModeList);
	}
	
}
