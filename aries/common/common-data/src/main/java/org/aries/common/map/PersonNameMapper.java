package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.common.PersonName;
import org.aries.common.entity.PersonNameEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(PersonNameMapper.class)
public class PersonNameMapper extends AbstractMapper<PersonName, PersonNameEntity> {
	
	public PersonNameMapper() {
		setModelClass(PersonName.class);
		setEntityClass(PersonNameEntity.class);
	}
	
	
	public PersonName toModel(PersonNameEntity personNameEntity) {
		if (personNameEntity == null)
			return null;
		PersonName personNameModel = createModel();
		personNameModel.setId(personNameEntity.getId());
		personNameModel.setLastName(personNameEntity.getLastName());
		personNameModel.setFirstName(personNameEntity.getFirstName());
		personNameModel.setMiddleInitial(personNameEntity.getMiddleInitial());
		return personNameModel;
	}
	
	public List<PersonName> toModelList(Collection<PersonNameEntity> personNameEntityList) {
		if (personNameEntityList == null)
			return null;
		List<PersonName> personNameModelList = new ArrayList<PersonName>();
		for (PersonNameEntity personNameEntity : personNameEntityList) {
			PersonName personNameModel = toModel(personNameEntity);
			personNameModelList.add(personNameModel);
		}
		return personNameModelList;
	}
	
	public PersonNameEntity toEntity(PersonName personNameModel) {
		if (personNameModel == null)
			return null;
		PersonNameEntity personNameEntity = createEntity();
		toEntity(personNameEntity, personNameModel);
		return personNameEntity;
	}
	
	public void toEntity(PersonNameEntity personNameEntity, PersonName personNameModel) {
		if (personNameEntity != null && personNameModel != null) {
			personNameEntity.setId(personNameModel.getId());
			personNameEntity.setLastName(personNameModel.getLastName());
			personNameEntity.setFirstName(personNameModel.getFirstName());
			personNameEntity.setMiddleInitial(personNameModel.getMiddleInitial());
		}
	}
	
	public List<PersonNameEntity> toEntityList(Collection<PersonName> personNameModelList) {
		if (personNameModelList == null)
			return null;
		List<PersonNameEntity> personNameEntityList = new ArrayList<PersonNameEntity>();
		for (PersonName personNameModel : personNameModelList) {
			PersonNameEntity personNameEntity = toEntity(personNameModel);
			personNameEntityList.add(personNameEntity);
		}
		return personNameEntityList;
	}
	
}
