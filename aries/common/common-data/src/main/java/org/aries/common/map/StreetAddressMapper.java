package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.StreetAddress;
import org.aries.common.entity.StreetAddressEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(StreetAddressMapper.class)
public class StreetAddressMapper extends AbstractMapper<StreetAddress, StreetAddressEntity> {
	
	@Inject
	protected ZipCodeMapper zipCodeMapper;
	
	
	public StreetAddressMapper() {
		setModelClass(StreetAddress.class);
		setEntityClass(StreetAddressEntity.class);
		if (zipCodeMapper == null)
			zipCodeMapper = new ZipCodeMapper();
	}
	
	
	public StreetAddress toModel(StreetAddressEntity streetAddressEntity) {
		if (streetAddressEntity == null)
			return null;
		StreetAddress streetAddressModel = createModel();
		streetAddressModel.setId(streetAddressEntity.getId());
		streetAddressModel.setStreet(streetAddressEntity.getStreet());
		streetAddressModel.setCity(streetAddressEntity.getCity());
		streetAddressModel.setState(streetAddressEntity.getState());
		streetAddressModel.setZipCode(zipCodeMapper.toModel(streetAddressEntity.getZipCode()));
		streetAddressModel.setCountry(streetAddressEntity.getCountry());
		streetAddressModel.setLatitude(streetAddressEntity.getLatitude());
		streetAddressModel.setLongitude(streetAddressEntity.getLongitude());
		return streetAddressModel;
	}
	
	public List<StreetAddress> toModelList(Collection<StreetAddressEntity> streetAddressEntityList) {
		if (streetAddressEntityList == null)
			return null;
		List<StreetAddress> streetAddressModelList = new ArrayList<StreetAddress>();
		for (StreetAddressEntity streetAddressEntity : streetAddressEntityList) {
			StreetAddress streetAddressModel = toModel(streetAddressEntity);
			streetAddressModelList.add(streetAddressModel);
		}
		return streetAddressModelList;
	}
	
	public StreetAddressEntity toEntity(StreetAddress streetAddressModel) {
		if (streetAddressModel == null)
			return null;
		StreetAddressEntity streetAddressEntity = createEntity();
		toEntity(streetAddressEntity, streetAddressModel);
		return streetAddressEntity;
	}
	
	public void toEntity(StreetAddressEntity streetAddressEntity, StreetAddress streetAddressModel) {
		if (streetAddressEntity != null && streetAddressModel != null) {
			streetAddressEntity.setId(streetAddressModel.getId());
			streetAddressEntity.setStreet(streetAddressModel.getStreet());
			streetAddressEntity.setCity(streetAddressModel.getCity());
			streetAddressEntity.setState(streetAddressModel.getState());
			streetAddressEntity.setZipCode(zipCodeMapper.toEntity(streetAddressModel.getZipCode()));
			streetAddressEntity.setCountry(streetAddressModel.getCountry());
			streetAddressEntity.setLatitude(streetAddressModel.getLatitude());
			streetAddressEntity.setLongitude(streetAddressModel.getLongitude());
		}
	}
	
	public List<StreetAddressEntity> toEntityList(Collection<StreetAddress> streetAddressModelList) {
		if (streetAddressModelList == null)
			return null;
		List<StreetAddressEntity> streetAddressEntityList = new ArrayList<StreetAddressEntity>();
		for (StreetAddress streetAddressModel : streetAddressModelList) {
			StreetAddressEntity streetAddressEntity = toEntity(streetAddressModel);
			streetAddressEntityList.add(streetAddressEntity);
		}
		return streetAddressEntityList;
	}
	
}
