package redhat.jee_migration_example.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.data.map.AbstractMapper;

import redhat.jee_migration_example.Item;
import redhat.jee_migration_example.entity.ItemStoreEntity;
import redhat.jee_migration_example.util.ItemUtil;


@Stateless
@Local(ItemStoreMapper.class)
public class ItemStoreMapper extends AbstractMapper<Item, ItemStoreEntity> {
	
	public ItemStoreMapper() {
		setModelClass(Item.class);
		setEntityClass(ItemStoreEntity.class);
	}
	
	
	public Item toModel(ItemStoreEntity itemStoreEntity) {
		if (itemStoreEntity == null)
			return null;
		Item itemModel = createModel();
		itemModel.setId(itemStoreEntity.getId());
		itemModel.setName(itemStoreEntity.getName());
		return itemModel;
	}
	
	public List<Item> toModelList(Collection<ItemStoreEntity> itemStoreEntityList) {
		if (itemStoreEntityList == null)
			return null;
		List<Item> itemModelList = new ArrayList<Item>();
		for (ItemStoreEntity itemStoreEntity : itemStoreEntityList) {
			Item itemModel = toModel(itemStoreEntity);
			itemModelList.add(itemModel);
		}
		return itemModelList;
	}

	public Map<String, Item> toModelMap(Collection<ItemStoreEntity> itemStoreEntityList) {
		if (itemStoreEntityList == null)
			return null;
		Map<String, Item> itemModelMap = new HashMap<String, Item>();
		for (ItemStoreEntity itemStoreEntity : itemStoreEntityList) {
			String key = itemStoreEntity.getItemKey();
			Item itemModel = toModel(itemStoreEntity);
			itemModelMap.put(key, itemModel);
		}
		return itemModelMap;
	}

	public ItemStoreEntity toEntity(String key, Item itemModel) {
		if (itemModel == null)
			return null;
		ItemStoreEntity itemStoreEntity = createEntity();
		itemStoreEntity.setItemKey(key);
		toEntity(itemStoreEntity, itemModel);
		return itemStoreEntity;
	}
	
	public void toEntity(ItemStoreEntity itemStoreEntity, Item itemModel) {
		if (itemStoreEntity != null && itemModel != null) {
			itemStoreEntity.setId(itemModel.getId());
			itemStoreEntity.setName(itemModel.getName());
		}
	}
	
//	public Collection<ItemStoreEntity> toEntityList(Collection<Item> itemList) {
//		if (itemList == null)
//			return null;
//		List<ItemStoreEntity> itemStoreEntityList = new ArrayList<ItemStoreEntity>();
//		for (Item itemModel : itemList) {
//			String key = ItemUtil.getKey(itemModel);
//			ItemStoreEntity itemStoreEntity = toEntity(key, itemModel);
//			itemStoreEntityList.add(itemStoreEntity);
//		}
//		return itemStoreEntityList;
//	}
	
	public Collection<ItemStoreEntity> toEntityList(Map<String, Item> itemMap) {
		if (itemMap == null)
			return null;
		List<ItemStoreEntity> itemStoreEntityList = new ArrayList<ItemStoreEntity>();
		for (String key : itemMap.keySet()) {
			Item itemModel = itemMap.get(key);
			ItemStoreEntity itemStoreEntity = toEntity(key, itemModel);
			itemStoreEntityList.add(itemStoreEntity);
		}
		return itemStoreEntityList;
	}
	
}
