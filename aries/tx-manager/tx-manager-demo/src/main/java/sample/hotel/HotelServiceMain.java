package sample.hotel;

import org.aries.util.properties.PropertyManager;

import common.tx.client.TXManagerClientSideLauncher;


public class HotelServiceMain {

	public static void main(String[] args) throws Exception {
		PropertyManager.getInstance().initialize();
		TXManagerClientSideLauncher clientSideLauncher = new TXManagerClientSideLauncher();
		clientSideLauncher.startup("localhost", 8082);
		HotelLauncher.INSTANCE.startup("localhost", 8082);
	}


}
