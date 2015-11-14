package redhat.jee_migration_example.util;

import org.aries.util.BaseFixture;
import org.aries.util.FieldUtil;

import redhat.jee_migration_example.map.ItemStoreMapper;

	
public class JeeMigrationExampleMapperFixture extends BaseFixture {
	
	private static ItemStoreMapper itemStoreMapper;
	
	
	public static void clear() {
		itemStoreMapper = null;
	}
	
	public static ItemStoreMapper getItemStoreMapper() {
		if (itemStoreMapper == null) {
			itemStoreMapper = new ItemStoreMapper();
		}
		return itemStoreMapper;
	}
	
}
