package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.List;


public interface ServiceDescripter extends Serializable {

	public static final String DEFAULT_SERVICE_METHOD = "process";

	public String getServiceId();

	public String getVersion();

	public String getDescription();

	public String getGroupName();

	public String getServiceName();

	public String getServiceQName();

	public String getPortQName();

	public String getTargetNamespace();

	public String getClassName();

	public String getMethodName();

    public void initialize(String groupName);

	public ApplicationProfile getApplicationProfile();

	public List<ProcessDescripter> getProcessDescripters();

	public ProcessDescripter getProcessDescripter(String processName);

	public void addProcessDescripter(ProcessDescripter processDescripter);

	public List<ListenerDescripter> getListenerDescripters();

	public ListenerDescripter getListenerDescripterForName(String channelName);

	public ListenerDescripter getListenerDescripterForType(String channeltype);

	public ListenerDescripter getListenerDescripterForType(ChannelType channeltype);

	public void addListenerDescripter(ListenerDescripter listenerDescripter);

	public void removeListenerDescripters();

	public List<PropertyDescripter> getPropertyDescripters();

	public void addPropertyDescripter(PropertyDescripter propertyDescripter);

}
