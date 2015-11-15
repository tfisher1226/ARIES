package nam.model.acknowledgeMode;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.AcknowledgeMode;
import nam.model.util.AcknowledgeModeUtil;


@SessionScoped
@Named("acknowledgeModeHelper")
public class AcknowledgeModeHelper extends AbstractEnumerationHelper<AcknowledgeMode> implements Serializable {
	
	@Produces
	public AcknowledgeMode[] getAcknowledgeModeArray() {
		return AcknowledgeMode.values();
	}
	
	@Override
	public String toString(AcknowledgeMode acknowledgeMode) {
		return acknowledgeMode.name();
	}
	
	@Override
	public String toString(Collection<AcknowledgeMode> acknowledgeModeList) {
		return AcknowledgeModeUtil.toString(acknowledgeModeList);
	}
	
}
