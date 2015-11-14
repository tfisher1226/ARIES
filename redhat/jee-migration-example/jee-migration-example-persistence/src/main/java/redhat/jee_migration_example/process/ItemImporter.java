package redhat.jee_migration_example.process;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(ItemImporter.class)
public class ItemImporter implements Serializable {
	
	public void importItemRecords() {
		//nothing for now
	}
	
}
