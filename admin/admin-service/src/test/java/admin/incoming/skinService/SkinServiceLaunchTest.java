package admin.incoming.skinService;

import java.io.FileNotFoundException;

import junit.framework.TestCase;

import org.aries.Assert;
import org.aries.launcher.Bootstrap;
import org.aries.launcher.Launcher;
import org.aries.runtime.BeanContext;
import org.aries.util.ExceptionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SkinServiceLaunchTest extends TestCase {

	public String getDomain() {
		return "admin";
	}

	public String getApplication() {
		return "admin";
	}

	public String getModule() {
		return "admin-service";
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	protected void initializeModel() throws Exception {
	}
	
	@Test
	public void testLaunch_FileNotFound() throws Exception {
		try {
	        String applicationServiceHost = "localhost";
	        int applicationServicePort = 8090;
	        String applicationHome = "..";
	        //String applicationHome = "C:/workspace/ARIES/infosgi";
	        String applicationDescriptorFile = "/model/admin/admin-service.aries";
	        String runtimeHome = applicationHome+"/runtime";
	        
			Bootstrap bootstrapHelper = new Bootstrap();
			BeanContext.set(getModule() + ".bootstrapper", bootstrapHelper);
			bootstrapHelper.setWorkingLocation(runtimeHome);
			bootstrapHelper.setRuntimeLocation(runtimeHome);
			bootstrapHelper.setPropertyLocation(runtimeHome + "/properties");
			bootstrapHelper.setModelLocation(runtimeHome + "/model");
			bootstrapHelper.setCacheLocation(runtimeHome + "/cache");
			bootstrapHelper.setDomainName(getDomain());
			bootstrapHelper.setModuleName(getModule());
			bootstrapHelper.initialize(runtimeHome);
	        
			Launcher launcher = new Launcher(applicationServiceHost, applicationServicePort);
			launcher.initialize(applicationDescriptorFile, getDomain(), getApplication(), getModule(), true);
			launcher.launch();
			
		} catch (Exception e) {
			//e.printStackTrace();
			e = ExceptionUtil.getRootCause(e);
			Assert.isInstanceOf(FileNotFoundException.class, e);
			//throw new RuntimeException(e);
		}
	}
    
}
