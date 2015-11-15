package bookshop2.supplier.data.bookInventory;

import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateful;


@Startup
@Stateful
@LocalBean
public class Tmp {

	private String hello;

	
	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}
	
}
