package bookshop2.supplier.service;

import org.junit.Ignore;
import org.junit.Test;


//@RunWith(Arquillian.class)
public class SupplierTests extends AbstractITSuite {
	
	public SupplierTests() {
		scriptName = "ATCrashDuringCommit";
	}
	
	@Test
	@Ignore
	public void MultiParticipantPrepareAndCommitTest() throws Exception {
		testName = "MultiParticipantPrepareAndCommitTest";
		String testClass = "org.jboss.jbossts.xts.servicetests.test.at.MultiParticipantPrepareAndCommitTest";
		runTest(testClass);
	}
	
	@Test
	@Ignore
	public void MultiServicePrepareAndCommitTest() throws Exception {
		testName = "MultiServicePrepareAndCommitTest";
		String testClass = "org.jboss.jbossts.xts.servicetests.test.at.MultiServicePrepareAndCommitTest";
		runTest(testClass);
	}
}
