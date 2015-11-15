package nam.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(WorkspaceImporter.class)
public class WorkspaceImporter implements Serializable {
	
	public void importWorkspaceRecords() {
		//nothing for now
	}
	
}
