package common.tx.service;

import org.aries.util.properties.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import sample.hotel.HotelClient;
import sample.hotel.HotelLauncher;
import sample.restaurant.RestaurantClient;
import sample.restaurant.RestaurantLauncher;
import sample.restaurant.RestaurantManager;
import sample.theatre.TheatreClient;
import sample.theatre.TheatreLauncher;
import sample.theatre.TheatreManager;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import common.tx.UserTransaction;
import common.tx.UserTransactionFactory;
import common.tx.client.TXManagerClientSideLauncher;


@RunWith(MockitoJUnitRunner.class)
public class UserTransactionIT {

	private UserTransaction fixture;

	//private org.jnp.server.Main jndi;

	private int port = 8082;
	

	@Before
	public void setUp() throws Exception {
		//jndi = new org.jnp.server.Main();
		//jndi.start();
		PropertyManager.getInstance().initialize();
		startServices("127.0.0.1", port);
		createFixture();
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		stopServices();
		//jndi.stop();
	}

	UserTransaction createFixture() {
		fixture = UserTransactionFactory.getUserTransaction();
		return fixture;
	}

	protected void startServices(String host, int port) {
		//PluginInitializerImpl pluginInitializer = new PluginInitializerImpl();
		TXManagerClientSideLauncher clientSideLauncher = new TXManagerClientSideLauncher();
		//ServiceSideLauncher serviceSideLauncher = new ServiceSideLauncher();
		//pluginInitializer.startup(host, port);
		clientSideLauncher.startup(host, port);
		//serviceSideLauncher.startup(host, port);
		RestaurantLauncher.INSTANCE.startup(host, port);
		TheatreLauncher.INSTANCE.startup(host, port);
		HotelLauncher.INSTANCE.startup(host, port);
	}

	protected void stopServices() {
	}

	//@Test
	public void bookSeatsTest() throws Exception {
		try {
			fixture.begin();
			System.out.println("TransactionStatus[afterBegin]: "+ActionStatus.stringForm(fixture.getStatus()));
			
			RestaurantClient restaurantClient = new RestaurantClient("localhost", port);
			restaurantClient.bookSeats(4);

			TheatreClient theatreClient = new TheatreClient("localhost", port);
			theatreClient.bookSeats(4, 1);

			HotelClient hotelClient = new HotelClient("localhost", port);
			hotelClient.bookRoom(89);

			System.out.println("TransactionStatus[beforeCommit]: "+ActionStatus.stringForm(fixture.getStatus()));
			fixture.commit();
			//fixture.rollback();

			System.out.println("TransactionStatus[afterCommit]: "+ActionStatus.stringForm(fixture.getStatus()));
			System.out.println("RestaurantManager[NBookedSeats]: "+RestaurantManager.INSTANCE.getNBookedSeats());
			System.out.println("TheatreManager[NReservedTickets[area=1]]: "+TheatreManager.INSTANCE.getNBookedSeats(1));
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
