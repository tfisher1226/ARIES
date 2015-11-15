package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.EmailAddress;
import org.aries.common.EmailAddressList;
import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.EmailAddressListEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(EmailAddressListMapper.class)
public class EmailAddressListMapper extends AbstractMapper<EmailAddressList, EmailAddressListEntity> {
	
	@Inject
	protected EmailAddressMapper emailAddressMapper;
	
	
	public EmailAddressListMapper() {
		setModelClass(EmailAddressList.class);
		setEntityClass(EmailAddressListEntity.class);
		if (emailAddressMapper == null)
			emailAddressMapper = new EmailAddressMapper();
	}
	
	
	public EmailAddressList toModel(EmailAddressListEntity emailAddressListEntity) {
		if (emailAddressListEntity == null)
			return null;
		EmailAddressList emailAddressListModel = createModel();
		emailAddressListModel.setId(emailAddressListEntity.getId());
		emailAddressListModel.setName(emailAddressListEntity.getName());
		emailAddressListModel.setAddresses(new ArrayList<EmailAddress>(emailAddressMapper.toModelList(emailAddressListEntity.getAddresses())));
		return emailAddressListModel;
	}
	
	public List<EmailAddressList> toModelList(Collection<EmailAddressListEntity> emailAddressListEntityList) {
		if (emailAddressListEntityList == null)
			return null;
		List<EmailAddressList> emailAddressListModelList = new ArrayList<EmailAddressList>();
		for (EmailAddressListEntity emailAddressListEntity : emailAddressListEntityList) {
			EmailAddressList emailAddressListModel = toModel(emailAddressListEntity);
			emailAddressListModelList.add(emailAddressListModel);
		}
		return emailAddressListModelList;
	}
	
	public EmailAddressListEntity toEntity(EmailAddressList emailAddressListModel) {
		if (emailAddressListModel == null)
			return null;
		EmailAddressListEntity emailAddressListEntity = createEntity();
		toEntity(emailAddressListEntity, emailAddressListModel);
		return emailAddressListEntity;
	}
	
	public void toEntity(EmailAddressListEntity emailAddressListEntity, EmailAddressList emailAddressListModel) {
		if (emailAddressListEntity != null && emailAddressListModel != null) {
			emailAddressListEntity.setId(emailAddressListModel.getId());
			emailAddressListEntity.setName(emailAddressListModel.getName());
			emailAddressListEntity.setAddresses(new ArrayList<EmailAddressEntity>(emailAddressMapper.toEntityList(emailAddressListModel.getAddresses())));
		}
	}
	
	public List<EmailAddressListEntity> toEntityList(Collection<EmailAddressList> emailAddressListModelList) {
		if (emailAddressListModelList == null)
			return null;
		List<EmailAddressListEntity> emailAddressListEntityList = new ArrayList<EmailAddressListEntity>();
		for (EmailAddressList emailAddressListModel : emailAddressListModelList) {
			EmailAddressListEntity emailAddressListEntity = toEntity(emailAddressListModel);
			emailAddressListEntityList.add(emailAddressListEntity);
		}
		return emailAddressListEntityList;
	}
	
}
