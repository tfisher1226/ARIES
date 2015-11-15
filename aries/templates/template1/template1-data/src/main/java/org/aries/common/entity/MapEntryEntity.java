package org.aries.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "MapEntry")
@Table(name = "map_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MapEntryEntity {

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		MapEntryEntity other = (MapEntryEntity) object;
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}