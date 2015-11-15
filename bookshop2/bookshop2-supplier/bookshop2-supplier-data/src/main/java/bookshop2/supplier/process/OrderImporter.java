package bookshop2.supplier.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(OrderImporter.class)
public class OrderImporter implements Serializable {
	
	public void importOrderRecords() {
		//nothing for now
	}
	
}
