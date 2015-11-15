package admin.incoming.userService;

import org.aries.launcher.Bootstrap;
import org.aries.launcher.Launcher;
import org.aries.runtime.BeanContext;


public class UserServiceMain {
	
	public static final String FILE_NAME = "userService.aries";
	
	public static final String RUNTIME_HOME = "../admin";
	
	public static final String HOST = "localhost";
	
	public static final int PORT = 9082;
	
	public static String getDomain() {
		return "admin";
	}

	public static String getApplication() {
		return "admin";
	}

	public static String getModule() {
		return "admin-service";
	}
	
	public static void main(String[] args) throws Exception {
		try {
	        String applicationServiceHost = "localhost";
	        int applicationServicePort = 8090;
	        String applicationHome = "../..";
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

		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
