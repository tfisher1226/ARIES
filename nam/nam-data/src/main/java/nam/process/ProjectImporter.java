package nam.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(ProjectImporter.class)
public class ProjectImporter implements Serializable {
	
	public void importProjectRecords() {
		//nothing for now
	}
	
}
