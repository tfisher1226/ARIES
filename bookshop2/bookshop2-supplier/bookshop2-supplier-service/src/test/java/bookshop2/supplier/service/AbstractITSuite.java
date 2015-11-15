package bookshop2.supplier.service;

import org.jboss.arquillian.container.test.api.Config;
import org.jboss.arquillian.container.test.api.ContainerController;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.test.api.ArquillianResource;


public class AbstractITSuite {

	@ArquillianResource
    private ContainerController controller;
    
	@ArquillianResource
    private Deployer deployer;
    
    protected String TEST_CLASS = " -Dorg.jboss.jbossts.xts.servicetests.XTSServiceTestName=@ClassName@";

    protected String javaVmArguments;
    
    protected String testName;
    
    protected String scriptName;

	
	protected void runTest(String testClass) throws Exception {
		Config config = new Config();
		config.add("javaVmArguments", javaVmArguments + TEST_CLASS.replace("@ClassName@", testClass));

		controller.start("jboss-as", config.map());
		//deployer.undeploy("xtstest");
		deployer.deploy("xtstest");

		//Waiting for crashing
		controller.kill("jboss-as");

		//Boot jboss-as after crashing
		config.add("javaVmArguments", javaVmArguments);
		controller.start("jboss-as", config.map());

		//redeploy xtstest
		//deployer.undeploy("xtstest");
		//deployer.deploy("xtstest");

		//Waiting for recovery
		//Thread.sleep(waitForRecovery * 60 * 1000);

		//deployer.undeploy("xtstest");
		//controller.stop("jboss-as");
		controller.kill("jboss-as");
	}

}
