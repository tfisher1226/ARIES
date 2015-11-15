package org.aries.tx.client;

import org.aries.Assert;
import org.aries.tx.UserTransaction;
import org.aries.tx.UserTransactionFactory;
import org.aries.util.properties.PropertyManager;
import org.aries.util.test.AbstractIT;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import sample.restaurant.RestaurantClient;
import sample.restaurant.RestaurantLauncher;
import sample.restaurant.RestaurantManager;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import common.tx.management.TXManagerClientSideLauncher;


@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceIT extends AbstractIT {

	private UserTransaction fixture;

	//private org.jnp.server.Main jndi;

	private int port = 8082;

	private TXManagerClientSideLauncher clientSideLauncher;
	

	@Before
	public void setUp() throws Exception {
		PropertyManager.getInstance().setPropertyLocation("../runtime/properties");
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
		clientSideLauncher = new TXManagerClientSideLauncher();
		clientSideLauncher.startup(host, port);
		RestaurantLauncher.INSTANCE.initialize(host, port);
	}

	protected void stopServices() {
		RestaurantLauncher.INSTANCE.shutdown();
		clientSideLauncher.shutdown();
	}

	@Test
	@Ignore
	public void testBookSeats_Committed() throws Exception {
		int requestedNBookedSeats = 4;
		
		try {
			fixture.begin();
			printStatus("afterBegin");
			
			RestaurantClient restaurantClient = new RestaurantClient("localhost", port);
			restaurantClient.bookSeats(requestedNBookedSeats);

			printStatus("beforeCommit");
			fixture.commit();
			//fixture.rollback();
			printStatus("afterCommit");

			int expectedNBookedSeats = requestedNBookedSeats;
			int actualNBookedSeats = RestaurantManager.INSTANCE.getNBookedSeats();
			log.info("RestaurantManager[NBookedSeats]: "+actualNBookedSeats);
			Assert.equals(expectedNBookedSeats, actualNBookedSeats, "Unexpected result");
			
		} catch (Exception e) {
			log.error("Error: "+e.getMessage());
			fixture.rollback();

		} finally {
			RestaurantLauncher.INSTANCE.shutdown();
		}
	}

	@Test
	@Ignore
	public void testBookSeats_AbortedRolledback() throws Exception {
		int requestedNBookedSeats = 4;
		
		try {
			fixture.begin();
			printStatus("afterBegin");
			
			RestaurantClient restaurantClient = new RestaurantClient("localhost", port);
			restaurantClient.bookSeats(requestedNBookedSeats);

			printStatus("beforeCommit");
			//fixture.commit();
			fixture.rollback();
			printStatus("afterCommit");

			int expectedNBookedSeats = 0;
			int actualNBookedSeats = RestaurantManager.INSTANCE.getNBookedSeats();
			log.info("RestaurantManager[NBookedSeats]: "+actualNBookedSeats);
			Assert.equals(expectedNBookedSeats, actualNBookedSeats, "Unexpected result");
			
		} catch (Exception e) {
			log.error("Error: "+e.getMessage());

		} finally {
			RestaurantLauncher.INSTANCE.shutdown();
		}
	}
	
	protected void printStatus(String name) {
		String state = ActionStatus.stringForm(fixture.getStatus());
		System.out.println("TransactionStatus["+name+"]: "+state);
	}
	
}
