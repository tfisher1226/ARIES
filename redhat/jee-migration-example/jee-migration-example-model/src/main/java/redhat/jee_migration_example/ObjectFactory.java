package redhat.jee_migration_example;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {
	
	public ObjectFactory() {
		//nothing for now
	}
	
	
	public Event createEvent() {
		return new Event();
	}
	
	public Item createItem() {
		return new Item();
	}
	
}
