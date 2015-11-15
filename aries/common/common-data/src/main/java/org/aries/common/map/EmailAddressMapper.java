package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.EmailAddress;
import org.aries.common.entity.EmailAddressEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(EmailAddressMapper.class)
public class EmailAddressMapper extends AbstractMapper<EmailAddress, EmailAddressEntity> {
	
	@Inject
	protected PhoneNumberMapper phoneNumberMapper;
	
	
	public EmailAddressMapper() {
		setModelClass(EmailAddress.class);
		setEntityClass(EmailAddressEntity.class);
		if (phoneNumberMapper == null)
			phoneNumberMapper = new PhoneNumberMapper();
	}
	
	
	public EmailAddress toModel(EmailAddressEntity emailAddressEntity) {
		if (emailAddressEntity == null)
			return null;
		EmailAddress emailAddressModel = createModel();
		emailAddressModel.setId(emailAddressEntity.getId());
		emailAddressModel.setUrl(emailAddressEntity.getUrl());
		emailAddressModel.setUserId(emailAddressEntity.getUserId());
		emailAddressModel.setFirstName(emailAddressEntity.getFirstName());
		emailAddressModel.setLastName(emailAddressEntity.getLastName());
		emailAddressModel.setPhoneNumber(phoneNumberMapper.toModel(emailAddressEntity.getPhoneNumber()));
		emailAddressModel.setOrganization(emailAddressEntity.getOrganization());
		emailAddressModel.setCreationDate(emailAddressEntity.getCreationDate());
		emailAddressModel.setLastUpdate(emailAddressEntity.getLastUpdate());
		emailAddressModel.setEnabled(emailAddressEntity.isEnabled());
		return emailAddressModel;
	}
	
	public List<EmailAddress> toModelList(Collection<EmailAddressEntity> emailAddressEntityList) {
		if (emailAddressEntityList == null)
			return null;
		List<EmailAddress> emailAddressModelList = new ArrayList<EmailAddress>();
		for (EmailAddressEntity emailAddressEntity : emailAddressEntityList) {
			EmailAddress emailAddressModel = toModel(emailAddressEntity);
			emailAddressModelList.add(emailAddressModel);
		}
		return emailAddressModelList;
	}
	
	public EmailAddressEntity toEntity(EmailAddress emailAddressModel) {
		if (emailAddressModel == null)
			return null;
		EmailAddressEntity emailAddressEntity = createEntity();
		toEntity(emailAddressEntity, emailAddressModel);
		return emailAddressEntity;
	}
	
	public void toEntity(EmailAddressEntity emailAddressEntity, EmailAddress emailAddressModel) {
		if (emailAddressEntity != null && emailAddressModel != null) {
			emailAddressEntity.setId(emailAddressModel.getId());
			emailAddressEntity.setUrl(emailAddressModel.getUrl());
			emailAddressEntity.setUserId(emailAddressModel.getUserId());
			emailAddressEntity.setFirstName(emailAddressModel.getFirstName());
			emailAddressEntity.setLastName(emailAddressModel.getLastName());
			emailAddressEntity.setPhoneNumber(phoneNumberMapper.toEntity(emailAddressModel.getPhoneNumber()));
			emailAddressEntity.setOrganization(emailAddressModel.getOrganization());
			emailAddressEntity.setCreationDate(emailAddressModel.getCreationDate());
			emailAddressEntity.setLastUpdate(emailAddressModel.getLastUpdate());
			emailAddressEntity.setEnabled(emailAddressModel.isEnabled());
		}
	}
	
	public List<EmailAddressEntity> toEntityList(Collection<EmailAddress> emailAddressModelList) {
		if (emailAddressModelList == null)
			return null;
		List<EmailAddressEntity> emailAddressEntityList = new ArrayList<EmailAddressEntity>();
		for (EmailAddress emailAddressModel : emailAddressModelList) {
			EmailAddressEntity emailAddressEntity = toEntity(emailAddressModel);
			emailAddressEntityList.add(emailAddressEntity);
		}
		return emailAddressEntityList;
	}
	
}
