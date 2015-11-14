package redhat.jee_migration_example.util;

import org.aries.util.AbstractModelHelper;

import redhat.jee_migration_example.Item;


public class JeeMigrationExampleHelper extends AbstractModelHelper {

	public static String getItemKey(Item item) {
		return item.getName();
	}
	
	
}
