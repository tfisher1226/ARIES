package org.aries.bank;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BankLoanQuoteTest {

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
		return "http://localhost:8080/bank/0.0.1";
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
		//JAXWSServiceLauncher.launch(getServiceURL(), fixture);
		//Assert.notNull(organizations, "Organizations should exist");
	}

}
