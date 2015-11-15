package org.aries.common.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.aries.common.entity.PropertyEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Properties")
@Table(name = "properties")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PropertiesEntity {

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<PropertyEntity> property;


	public List<PropertyEntity> getProperty() {
		if (property == null)
			property = new ArrayList<PropertyEntity>();
		return property;
	}

	public void setProperty(List<PropertyEntity> property) {
		this.property = property;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		PropertiesEntity other = (PropertiesEntity) object;
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}