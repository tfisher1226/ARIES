package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.common.Property;
import org.aries.common.entity.PropertyEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(PropertyMapper.class)
public class PropertyMapper extends AbstractMapper<Property, PropertyEntity> {
	
	public PropertyMapper() {
		setModelClass(Property.class);
		setEntityClass(PropertyEntity.class);
	}
	
	
	public Property toModel(PropertyEntity propertyEntity) {
		if (propertyEntity == null)
			return null;
		Property propertyModel = createModel();
		propertyModel.setId(propertyEntity.getId());
		propertyModel.setName(propertyEntity.getName());
		propertyModel.setValue(propertyEntity.getValue());
		return propertyModel;
	}
	
	public List<Property> toModelList(Collection<PropertyEntity> propertyEntityList) {
		if (propertyEntityList == null)
			return null;
		List<Property> propertyModelList = new ArrayList<Property>();
		for (PropertyEntity propertyEntity : propertyEntityList) {
			Property propertyModel = toModel(propertyEntity);
			propertyModelList.add(propertyModel);
		}
		return propertyModelList;
	}
	
	public PropertyEntity toEntity(Property propertyModel) {
		if (propertyModel == null)
			return null;
		PropertyEntity propertyEntity = createEntity();
		toEntity(propertyEntity, propertyModel);
		return propertyEntity;
	}
	
	public void toEntity(PropertyEntity propertyEntity, Property propertyModel) {
		if (propertyEntity != null && propertyModel != null) {
			propertyEntity.setId(propertyModel.getId());
			propertyEntity.setName(propertyModel.getName());
			propertyEntity.setValue(propertyModel.getValue());
		}
	}
	
	public List<PropertyEntity> toEntityList(Collection<Property> propertyModelList) {
		if (propertyModelList == null)
			return null;
		List<PropertyEntity> propertyEntityList = new ArrayList<PropertyEntity>();
		for (Property propertyModel : propertyModelList) {
			PropertyEntity propertyEntity = toEntity(propertyModel);
			propertyEntityList.add(propertyEntity);
		}
		return propertyEntityList;
	}
	
}
