package simpleinvoke.helloworld;

import org.aries.bank.BankLoanQuote;
import org.aries.bank.BankLoanQuoteImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class HelloWorldWSTest {

	private BankLoanQuote fixture;

	
	@BeforeClass  
	public static void beforeClass() {
		//setupEntityManager("Model1TestDS");
	}  

	@AfterClass  
	public static void afterClass() {
		//teardownEntityManager();
	}

	private String getServiceURL() {
		return "http://localhost:8080/SimpleInvoke/HelloWorld";
	}
	
	@Before
	public void setUp() throws Exception {
		fixture = createFixture();
		//super.setUp();
	}

	protected BankLoanQuote createFixture() {
		fixture = new BankLoanQuoteImpl();
		return fixture;
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		//super.tearDown();
	}

	@Test
	@Ignore
	public void testGetOrganizationUsingQuery() throws Exception {
		JAXWSServiceLauncher.launch(getServiceURL(), fixture);
		//Assert.notNull(organizations, "Organizations should exist");
	}

}
