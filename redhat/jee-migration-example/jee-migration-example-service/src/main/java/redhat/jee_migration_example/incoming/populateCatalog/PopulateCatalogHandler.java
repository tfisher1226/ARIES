package redhat.jee_migration_example.incoming.populateCatalog;

import org.aries.tx.Transactional;


public interface PopulateCatalogHandler extends Transactional {
	
	public void populateCatalog();
	
}
