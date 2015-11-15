package simpleinvoke.helloworld;

import org.aries.bank.BankLoanQuoteClient;
import org.aries.bank.BankLoanQuoteProxy;
import org.aries.bank.BankLoanQuoteRequest;
import org.aries.bank.BankLoanQuoteResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SimpleInvokeClientTest {

	private BankLoanQuoteClient fixture;

	
	@BeforeClass  
	public static void beforeClass() {
		//setupEntityManager("Model1TestDS");
	}  

	@AfterClass  
	public static void afterClass() {
		//teardownEntityManager();
	}

	private String getServiceURL() {
		return "http://localhost:8180/SimpleInvoke";
	}
	
	@Before
	public void setUp() throws Exception {
		fixture = createFixture();
		//super.setUp();
	}

	protected BankLoanQuoteClient createFixture() {
		fixture = new BankLoanQuoteClient();
		return fixture;
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		//super.tearDown();
	}

	@Test
	@Ignore
	public void testBankLoanQuoteProxy() throws Exception {
		BankLoanQuoteProxy client = new BankLoanQuoteProxy();
		BankLoanQuoteRequest request = new BankLoanQuoteRequest();
		request.setBorrowerName("Tom Fisher");
		request.setBorrowerSsn("8897761254");
		request.setCreditHistory(10);
		request.setCreditScore(800);
		request.setLoanAmount(300000d);
		request.setLoanDuration(30);
		BankLoanQuoteResponse response = client.getLoanQuote(request);
		System.out.println("Response: "+response.getLoanQuote());
	}

}
