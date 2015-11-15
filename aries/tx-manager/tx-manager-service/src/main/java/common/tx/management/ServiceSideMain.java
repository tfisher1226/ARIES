package common.tx.management;

import org.aries.util.properties.PropertyManager;


public class ServiceSideMain {

	public static void main(String[] args) throws Exception {
		PropertyManager.getInstance().initialize();
		TXManagerServiceSideLauncher serviceSideLauncher = new TXManagerServiceSideLauncher();
		serviceSideLauncher.startup("127.0.0.1", 9090);
	}

}
