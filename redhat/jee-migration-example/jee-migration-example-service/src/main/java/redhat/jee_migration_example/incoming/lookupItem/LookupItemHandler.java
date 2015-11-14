package redhat.jee_migration_example.incoming.lookupItem;

import org.aries.tx.Transactional;

import redhat.jee_migration_example.Item;


public interface LookupItemHandler extends Transactional {
	
	public Item lookupItem(Long id);
	
}

