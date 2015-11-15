package nam.ear;

import java.util.ArrayList;
import java.util.List;


public class EARModuleSettings {

	//private static Map<String, String> configurations = new HashMap<String, String>();
	
	public static List<String> getRequiredConfigurationList() {
		List<String> list = new ArrayList<String>();
        list.add("commons");
        list.add("drools");
        list.add("eclipse-emf");
        list.add("eclipse-jdt");
        list.add("ehcache");
        list.add("jta");
        list.add("seam");
        //list.add("tx-manager-api");
        list.add("util");
        return list;
	}

	public static List<String> getCompleteConfigurationList() {
		List<String> list = new ArrayList<String>();
		list.add("activemq");
        list.add("aries-base");
        list.add("aries-base-test");
        list.add("aries-client-layer");
        list.add("aries-client-layer-test");
        list.add("aries-data-layer");
        list.add("aries-data-layer-test");
        list.add("aries-model-layer");
        list.add("aries-model-layer-test");
        list.add("aries-platform-base");
        list.add("aries-platform-client-layer");
        list.add("aries-platform-client-layer-test");
        list.add("aries-platform-data-layer");
        list.add("aries-platform-data-layer-test");
        list.add("aries-platform-model-layer");
        list.add("aries-platform-model-layer-test");
        list.add("aries-platform-service-layer");
        list.add("aries-platform-service-layer-test");
        list.add("aries-service-layer");
        list.add("aries-service-layer-test");
        list.add("commons");
        list.add("drools");
        list.add("eclipse-emf");
        list.add("eclipse-jdt");
        list.add("ehcache");
        list.add("hibernate");
        list.add("hornetq");
        list.add("javax");
        list.add("jaxb");
        list.add("jaxws");
        list.add("jndi");
        list.add("jta");
        list.add("logging");
        list.add("mysql");
        list.add("plexus");
        list.add("seam");
        list.add("testing");
        //list.add("tx-manager-api");
        list.add("util");
        return list;
	}
	
}
