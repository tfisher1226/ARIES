package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.common.PhoneNumber;
import org.aries.common.entity.PhoneNumberEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(PhoneNumberMapper.class)
public class PhoneNumberMapper extends AbstractMapper<PhoneNumber, PhoneNumberEntity> {
	
	public PhoneNumberMapper() {
		setModelClass(PhoneNumber.class);
		setEntityClass(PhoneNumberEntity.class);
	}
	
	
	public PhoneNumber toModel(PhoneNumberEntity phoneNumberEntity) {
		if (phoneNumberEntity == null)
			return null;
		PhoneNumber phoneNumberModel = createModel();
		phoneNumberModel.setId(phoneNumberEntity.getId());
		phoneNumberModel.setArea(phoneNumberEntity.getArea());
		phoneNumberModel.setNumber(phoneNumberEntity.getNumber());
		phoneNumberModel.setExtension(phoneNumberEntity.getExtension());
		phoneNumberModel.setCountry(phoneNumberEntity.getCountry());
		phoneNumberModel.setType(phoneNumberEntity.getType());
		phoneNumberModel.setValue(phoneNumberEntity.getValue());
		return phoneNumberModel;
	}
	
	public List<PhoneNumber> toModelList(Collection<PhoneNumberEntity> phoneNumberEntityList) {
		if (phoneNumberEntityList == null)
			return null;
		List<PhoneNumber> phoneNumberModelList = new ArrayList<PhoneNumber>();
		for (PhoneNumberEntity phoneNumberEntity : phoneNumberEntityList) {
			PhoneNumber phoneNumberModel = toModel(phoneNumberEntity);
			phoneNumberModelList.add(phoneNumberModel);
		}
		return phoneNumberModelList;
	}
	
	public PhoneNumberEntity toEntity(PhoneNumber phoneNumberModel) {
		if (phoneNumberModel == null)
			return null;
		PhoneNumberEntity phoneNumberEntity = createEntity();
		toEntity(phoneNumberEntity, phoneNumberModel);
		return phoneNumberEntity;
	}
	
	public void toEntity(PhoneNumberEntity phoneNumberEntity, PhoneNumber phoneNumberModel) {
		if (phoneNumberEntity != null && phoneNumberModel != null) {
			phoneNumberEntity.setId(phoneNumberModel.getId());
			phoneNumberEntity.setArea(phoneNumberModel.getArea());
			phoneNumberEntity.setNumber(phoneNumberModel.getNumber());
			phoneNumberEntity.setExtension(phoneNumberModel.getExtension());
			phoneNumberEntity.setCountry(phoneNumberModel.getCountry());
			phoneNumberEntity.setType(phoneNumberModel.getType());
			phoneNumberEntity.setValue(phoneNumberModel.getValue());
		}
	}
	
	public List<PhoneNumberEntity> toEntityList(Collection<PhoneNumber> phoneNumberModelList) {
		if (phoneNumberModelList == null)
			return null;
		List<PhoneNumberEntity> phoneNumberEntityList = new ArrayList<PhoneNumberEntity>();
		for (PhoneNumber phoneNumberModel : phoneNumberModelList) {
			PhoneNumberEntity phoneNumberEntity = toEntity(phoneNumberModel);
			phoneNumberEntityList.add(phoneNumberEntity);
		}
		return phoneNumberEntityList;
	}
	
}
