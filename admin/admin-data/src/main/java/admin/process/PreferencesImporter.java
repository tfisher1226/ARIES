package admin.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(PreferencesImporter.class)
public class PreferencesImporter implements Serializable {
	
	public void importPreferencesRecords() {
		//nothing for now
	}
	
}
