package sample.theatre;

import org.aries.util.properties.PropertyManager;

import common.tx.management.TXManagerClientSideLauncher;


public class TheatreServiceMain {

	public static void main(String[] args) throws Exception {
		PropertyManager.getInstance().initialize();
		TXManagerClientSideLauncher clientSideLauncher = new TXManagerClientSideLauncher();
		clientSideLauncher.startup("localhost", 8082);
		TheatreLauncher.INSTANCE.initialize("localhost", 8082);
	}

}
