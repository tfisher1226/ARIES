package common.tx.service;

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

import common.tx.PluginInitializer;
import common.tx.UserTransaction;
import common.tx.UserTransactionFactory;
import common.tx.client.TXManagerClientSideLauncher;
import common.tx.management.TXManagerServiceSideLauncher;


@RunWith(MockitoJUnitRunner.class)
public class UserTransactionTest {

	private UserTransaction fixture;

	private PluginInitializer pluginInitializer;


	@Before
	public void setUp() throws Exception {
		PropertyManager.getInstance().initialize();
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
		TXManagerClientSideLauncher clientSideLauncher = new TXManagerClientSideLauncher();
		TXManagerServiceSideLauncher serviceSideLauncher = new TXManagerServiceSideLauncher();
		clientSideLauncher.startup(host, port);
		serviceSideLauncher.startup(host, port);
		RestaurantLauncher.INSTANCE.startup(host, port);
		TheatreLauncher.INSTANCE.startup(host, port);
	}

	protected void stopServices() {
		RestaurantLauncher.INSTANCE.shutdown();
		TheatreLauncher.INSTANCE.shutdown();
		//pluginInitializer.teardown();
	}

	@Test
	public void placeholderTest() throws Exception {
	}
	
	//@Test
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
		} catch (Exception e) {
			e.printStackTrace();
			try {
				fixture.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	//@Test
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
