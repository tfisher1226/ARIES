package org.aries.common.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.aries.common.entity.MapEntryEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Map")
@Table(name = "map")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MapEntity {

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<MapEntryEntity> mapEntry;


	public List<MapEntryEntity> getMapEntry() {
		if (mapEntry == null)
			mapEntry = new ArrayList<MapEntryEntity>();
		return mapEntry;
	}

	public void setMapEntry(List<MapEntryEntity> mapEntry) {
		this.mapEntry = mapEntry;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		MapEntity other = (MapEntity) object;
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}