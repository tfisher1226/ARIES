package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.Person;
import org.aries.common.entity.PersonEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(PersonMapper.class)
public class PersonMapper extends AbstractMapper<Person, PersonEntity> {
	
	@Inject
	protected PersonNameMapper personNameMapper;
	
	@Inject
	protected PhoneNumberMapper phoneNumberMapper;
	
	@Inject
	protected EmailAddressMapper emailAddressMapper;
	
	@Inject
	protected StreetAddressMapper streetAddressMapper;
	
	
	public PersonMapper() {
		setModelClass(Person.class);
		setEntityClass(PersonEntity.class);
		if (personNameMapper == null)
			personNameMapper = new PersonNameMapper();
		if (phoneNumberMapper == null)
			phoneNumberMapper = new PhoneNumberMapper();
		if (emailAddressMapper == null)
			emailAddressMapper = new EmailAddressMapper();
		if (streetAddressMapper == null)
			streetAddressMapper = new StreetAddressMapper();
	}
	
	
	public Person toModel(PersonEntity personEntity) {
		if (personEntity == null)
			return null;
		Person personModel = createModel();
		personModel.setId(personEntity.getId());
		personModel.setUserId(personEntity.getUserId());
		personModel.setName(personNameMapper.toModel(personEntity.getName()));
		personModel.setPhoneNumber(phoneNumberMapper.toModel(personEntity.getPhoneNumber()));
		personModel.setEmailAddress(emailAddressMapper.toModel(personEntity.getEmailAddress()));
		personModel.setStreetAddress(streetAddressMapper.toModel(personEntity.getStreetAddress()));
		return personModel;
	}
	
	public List<Person> toModelList(Collection<PersonEntity> personEntityList) {
		if (personEntityList == null)
			return null;
		List<Person> personModelList = new ArrayList<Person>();
		for (PersonEntity personEntity : personEntityList) {
			Person personModel = toModel(personEntity);
			personModelList.add(personModel);
		}
		return personModelList;
	}
	
	public PersonEntity toEntity(Person personModel) {
		if (personModel == null)
			return null;
		PersonEntity personEntity = createEntity();
		toEntity(personEntity, personModel);
		return personEntity;
	}
	
	public void toEntity(PersonEntity personEntity, Person personModel) {
		if (personEntity != null && personModel != null) {
			personEntity.setId(personModel.getId());
			personEntity.setUserId(personModel.getUserId());
			personEntity.setName(personNameMapper.toEntity(personModel.getName()));
			personEntity.setPhoneNumber(phoneNumberMapper.toEntity(personModel.getPhoneNumber()));
			personEntity.setEmailAddress(emailAddressMapper.toEntity(personModel.getEmailAddress()));
			personEntity.setStreetAddress(streetAddressMapper.toEntity(personModel.getStreetAddress()));
		}
	}
	
	public List<PersonEntity> toEntityList(Collection<Person> personModelList) {
		if (personModelList == null)
			return null;
		List<PersonEntity> personEntityList = new ArrayList<PersonEntity>();
		for (Person personModel : personModelList) {
			PersonEntity personEntity = toEntity(personModel);
			personEntityList.add(personEntity);
		}
		return personEntityList;
	}
	
}
