package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.common.ZipCode;
import org.aries.common.entity.ZipCodeEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(ZipCodeMapper.class)
public class ZipCodeMapper extends AbstractMapper<ZipCode, ZipCodeEntity> {
	
	public ZipCodeMapper() {
		setModelClass(ZipCode.class);
		setEntityClass(ZipCodeEntity.class);
	}
	
	
	public ZipCode toModel(ZipCodeEntity zipCodeEntity) {
		if (zipCodeEntity == null)
			return null;
		ZipCode zipCodeModel = createModel();
		zipCodeModel.setId(zipCodeEntity.getId());
		zipCodeModel.setNumber(zipCodeEntity.getNumber());
		zipCodeModel.setExtension(zipCodeEntity.getExtension());
		zipCodeModel.setCountry(zipCodeEntity.getCountry());
		return zipCodeModel;
	}
	
	public List<ZipCode> toModelList(Collection<ZipCodeEntity> zipCodeEntityList) {
		if (zipCodeEntityList == null)
			return null;
		List<ZipCode> zipCodeModelList = new ArrayList<ZipCode>();
		for (ZipCodeEntity zipCodeEntity : zipCodeEntityList) {
			ZipCode zipCodeModel = toModel(zipCodeEntity);
			zipCodeModelList.add(zipCodeModel);
		}
		return zipCodeModelList;
	}
	
	public ZipCodeEntity toEntity(ZipCode zipCodeModel) {
		if (zipCodeModel == null)
			return null;
		ZipCodeEntity zipCodeEntity = createEntity();
		toEntity(zipCodeEntity, zipCodeModel);
		return zipCodeEntity;
	}
	
	public void toEntity(ZipCodeEntity zipCodeEntity, ZipCode zipCodeModel) {
		if (zipCodeEntity != null && zipCodeModel != null) {
			zipCodeEntity.setId(zipCodeModel.getId());
			zipCodeEntity.setNumber(zipCodeModel.getNumber());
			zipCodeEntity.setExtension(zipCodeModel.getExtension());
			zipCodeEntity.setCountry(zipCodeModel.getCountry());
		}
	}
	
	public List<ZipCodeEntity> toEntityList(Collection<ZipCode> zipCodeModelList) {
		if (zipCodeModelList == null)
			return null;
		List<ZipCodeEntity> zipCodeEntityList = new ArrayList<ZipCodeEntity>();
		for (ZipCode zipCodeModel : zipCodeModelList) {
			ZipCodeEntity zipCodeEntity = toEntity(zipCodeModel);
			zipCodeEntityList.add(zipCodeEntity);
		}
		return zipCodeEntityList;
	}
	
}
