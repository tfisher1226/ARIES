package admin.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(RoleImporter.class)
public class RoleImporter implements Serializable {
	
	public void importRoleRecords() {
		//nothing for now
	}
	
}
