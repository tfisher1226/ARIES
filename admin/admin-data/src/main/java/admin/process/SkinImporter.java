package admin.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(SkinImporter.class)
public class SkinImporter implements Serializable {
	
	public void importSkinRecords() {
		//nothing for now
	}
	
}
