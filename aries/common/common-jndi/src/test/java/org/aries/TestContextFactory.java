package org.aries;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;


public class TestContextFactory implements InitialContextFactory {

	private static Context context;
	
	
	static {
		try {
			context = new TestContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static void setContext(Context context) {
		TestContextFactory.context = context;
	}
	
	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
		return context;
	}

}
