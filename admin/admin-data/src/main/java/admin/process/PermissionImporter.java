package admin.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(PermissionImporter.class)
public class PermissionImporter implements Serializable {
	
	public void importPermissionRecords() {
		//nothing for now
	}
	
}
