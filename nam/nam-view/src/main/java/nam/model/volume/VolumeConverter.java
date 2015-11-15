package nam.model.volume;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Volume;
import nam.model.util.VolumeUtil;


@FacesConverter(value = "volumeConverter", forClass = Volume.class)
public class VolumeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		VolumeListManager volumeListManager = BeanContext.getFromSession("volumeListManager");
		Volume volume = volumeListManager.getRecordByName(value);
		return volume;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Volume volume = (Volume) value;
		return VolumeUtil.getLabel(volume);
	}
	
}
