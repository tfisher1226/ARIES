package admin.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(RegistrationImporter.class)
public class RegistrationImporter implements Serializable {
	
	public void importRegistrationRecords() {
		//nothing for now
	}
	
}
