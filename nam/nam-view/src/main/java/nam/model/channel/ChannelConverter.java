package nam.model.channel;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Channel;
import nam.model.util.ChannelUtil;


@FacesConverter(value = "channelConverter", forClass = Channel.class)
public class ChannelConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ChannelListManager channelListManager = BeanContext.getFromSession("channelListManager");
		Channel channel = channelListManager.getRecordByName(value);
		return channel;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Channel channel = (Channel) value;
		return ChannelUtil.toString(channel);
	}
	
}
