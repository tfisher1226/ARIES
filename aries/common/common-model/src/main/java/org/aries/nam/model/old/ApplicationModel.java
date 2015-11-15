package org.aries.nam.model.old;



public class ApplicationModel {

	private static ApplicationProfile applicationProfile;
	
	public static ApplicationProfile getApplicationProfile() {
		return ApplicationModel.applicationProfile;
	}

	public static void setApplicationProfile(ApplicationProfile applicationProfile) {
		ApplicationModel.applicationProfile = applicationProfile;
	}

	public static boolean hasServiceDescripter(String serviceId) {
		return applicationProfile != null && applicationProfile.getServiceDescripter(serviceId) != null;
	}

	public static ServiceDescripter getServiceDescripter(String serviceId) {
		return applicationProfile.getServiceDescripter(serviceId);
	}

	public static ChannelDescripter getChannelDescripterByName(String channelName) {
		return applicationProfile.getChannelDescripterByName(channelName);
	}
	
}
