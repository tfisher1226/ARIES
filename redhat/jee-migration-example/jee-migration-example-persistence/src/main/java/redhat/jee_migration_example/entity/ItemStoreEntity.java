package redhat.jee_migration_example.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;


@Entity(name = "ItemStore")
@Cache(usage = READ_WRITE)
@Table(name = "item_store", uniqueConstraints = @UniqueConstraint(columnNames = "itemKey"))
public class ItemStoreEntity extends AbstractItemEntity implements Serializable {
	
	@Column(name = "itemKey", nullable = false, unique = true)
	private String itemKey;
	
	
	public String getItemKey() {
		return itemKey;
	}
	
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
}
