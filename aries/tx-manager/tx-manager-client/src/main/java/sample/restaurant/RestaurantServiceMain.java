package sample.restaurant;

import org.aries.util.properties.PropertyManager;

import common.tx.management.TXManagerClientSideLauncher;


public class RestaurantServiceMain {

	public static void main(String[] args) throws Exception {
		PropertyManager.getInstance().initialize();
		TXManagerClientSideLauncher clientSideLauncher = new TXManagerClientSideLauncher();
		clientSideLauncher.startup("localhost", 8082);
		RestaurantLauncher.INSTANCE.initialize("localhost", 8082);
	}


}
