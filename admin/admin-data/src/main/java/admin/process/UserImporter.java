package admin.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(UserImporter.class)
public class UserImporter implements Serializable {
	
	public void importUserRecords() {
		//nothing for now
	}
	
}
