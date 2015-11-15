package org.aries.tx;

import org.aries.util.properties.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import sample.restaurant.RestaurantClient;
import sample.restaurant.RestaurantLauncher;
import sample.restaurant.RestaurantManager;
import sample.theatre.TheatreLauncher;
import sample.theatre.TheatreManager;

import common.tx.management.TXManagerClientSideLauncher;
import common.tx.management.TXManagerServiceSideLauncher;


@RunWith(MockitoJUnitRunner.class)
public class UserTransactionTest {

	private UserTransaction fixture;

	//private PluginInitializer pluginInitializer;

	private TXManagerClientSideLauncher txManagerClientSideLauncher;

	private TXManagerServiceSideLauncher txManagerServiceSideLauncher;


	@Before
	public void setUp() throws Exception {
		String propertyLocation = System.getProperty("user.dir") + "/../tx-manager/runtime/properties";
		PropertyManager propertyManager = new PropertyManager();
		propertyManager.setPropertyLocation(propertyLocation);
		propertyManager.initialize();
		startServices("localhost", 8080);
		createFixture();
	}

	@After
	public void tearDown() throws Exception {
		stopServices();
		fixture = null;
	}

	UserTransaction createFixture() {
		fixture = UserTransactionFactory.getUserTransaction();
		return fixture;
	}

	protected void startServices(String host, int port) {
		txManagerClientSideLauncher = new TXManagerClientSideLauncher();
		txManagerServiceSideLauncher = new TXManagerServiceSideLauncher();
		txManagerClientSideLauncher.startup(host, port);
		txManagerServiceSideLauncher.startup(host, port);
		RestaurantLauncher.INSTANCE.initialize(host, port);
		TheatreLauncher.INSTANCE.initialize(host, port);
	}

	protected void stopServices() {
		RestaurantLauncher.INSTANCE.shutdown();
		TheatreLauncher.INSTANCE.shutdown();
		txManagerClientSideLauncher.shutdown();
		txManagerServiceSideLauncher.shutdown();
	}

	@Test
	public void bookSeatsTest() throws Exception {
		try {
			System.out.println(">>>"+fixture.getStatus());
			fixture.begin();
			
			RestaurantClient restaurantClient = new RestaurantClient("localhost", 8080);
			restaurantClient.bookSeats(4);
			//TheatreClient.INSTANCE.bookSeats(4, 1);

			System.out.println(">>>"+fixture.getStatus());
			fixture.commit();

			System.out.println(">>>"+fixture.getStatus());

			System.out.println(">>>"+RestaurantManager.INSTANCE.getNBookedSeats());
			System.out.println(">>>"+TheatreManager.INSTANCE.getNBookedSeats(1));
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				fixture.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Test
	public void bookSeatsTest_Rollback() throws Exception {
		try {
			System.out.println(">>>"+fixture.getStatus());
			fixture.begin();
			
			RestaurantClient restaurantClient = new RestaurantClient("localhost", 8080);
			restaurantClient.bookSeats(4);
			//TheatreClient.INSTANCE.bookSeats(4, 1);

			System.out.println(">>>"+fixture.getStatus());
			fixture.rollback();

			System.out.println(">>>"+fixture.getStatus());

			System.out.println(">>>"+RestaurantManager.INSTANCE.getNBookedSeats());
			System.out.println(">>>"+TheatreManager.INSTANCE.getNBookedSeats(1));
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				fixture.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
}
