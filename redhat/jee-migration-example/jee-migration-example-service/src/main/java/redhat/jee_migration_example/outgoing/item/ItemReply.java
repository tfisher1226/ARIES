package redhat.jee_migration_example.outgoing.item;

import redhat.jee_migration_example.Item;


public interface ItemReply {
	
	public String ID = "redhat.jee-migration-example.itemReply";
	
	public void replyItem(Item item);
	
}
