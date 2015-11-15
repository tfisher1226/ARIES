package org.aries.util.http;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;


public class ServletUtil {

	public URL getLocation(Class<?> classObject) {
		ProtectionDomain domain = classObject.getProtectionDomain();
		CodeSource codeSource = domain.getCodeSource();
		URL location = codeSource.getLocation();
		return location;
	}
}
