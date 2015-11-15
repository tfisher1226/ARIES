package org.aries.ui.mobile;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


public class SupportedMobileDeviceValidator {
	
	private SupportedMobileDeviceValidator() {
		
	}
	
	public static boolean isSupportedDevice(List<UserAgent> supportedUserAgents) {
		HttpServletRequest req =
			(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		String userAgent = req.getHeader("user-agent");
		if (userAgent == null) {
			return false;
		}
		
		userAgent = userAgent.toLowerCase();
		for (UserAgent supportedUserAgent : supportedUserAgents) {
			String compare = supportedUserAgent.getOperatingSystem().toLowerCase();
			
			if (userAgent.contains(compare)) {
				return true;
			}
		}
		
		return false;
	}
}
