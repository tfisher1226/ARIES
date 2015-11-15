package bookshop2.supplier.incoming.reserveBooks;

import org.aries.launcher.Bootstrap;
import org.aries.launcher.Launcher;
import org.aries.runtime.BeanContext;


public class ReserveBooksMain {
	
	public static final String FILE_NAME = "reserveBooks.aries";
	
	public static final String RUNTIME_HOME = "../bookshop2";
	
	public static final String HOST = "localhost";
	
	public static final int PORT = 9082;
	
	public static void main(String[] args) throws Exception {
		try {
			Bootstrap bootstrapper = new Bootstrap();
			BeanContext.set("bookshop2.supplier.bootstrapper", bootstrapper);
			bootstrapper.initialize(RUNTIME_HOME);
			Launcher launcher = new Launcher(HOST, PORT);
			launcher.initialize(FILE_NAME, "bookshop2.supplier", "supplier", "bookshop2.supplier.service", true);
			launcher.launch();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
