package bookshop2.supplier.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(BookImporter.class)
public class BookImporter implements Serializable {
	
	public void importBookRecords() {
		//nothing for now
	}
	
}
