package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.EmailAccount;
import org.aries.common.EmailBox;
import org.aries.common.entity.EmailAccountEntity;
import org.aries.common.entity.EmailBoxEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(EmailAccountMapper.class)
public class EmailAccountMapper extends AbstractMapper<EmailAccount, EmailAccountEntity> {
	
	@Inject
	protected EmailBoxMapper emailBoxMapper;
	
	
	public EmailAccountMapper() {
		setModelClass(EmailAccount.class);
		setEntityClass(EmailAccountEntity.class);
		if (emailBoxMapper == null)
			emailBoxMapper = new EmailBoxMapper();
	}
	
	
	public EmailAccount toModel(EmailAccountEntity emailAccountEntity) {
		if (emailAccountEntity == null)
			return null;
		EmailAccount emailAccountModel = createModel();
		emailAccountModel.setId(emailAccountEntity.getId());
		emailAccountModel.setUserId(emailAccountEntity.getUserId());
		emailAccountModel.setPasswordHash(emailAccountEntity.getPasswordHash());
		emailAccountModel.setPasswordSalt(emailAccountEntity.getPasswordSalt());
		emailAccountModel.setFirstName(emailAccountEntity.getFirstName());
		emailAccountModel.setLastName(emailAccountEntity.getLastName());
		emailAccountModel.setEmailBoxes(new ArrayList<EmailBox>(emailBoxMapper.toModelList(emailAccountModel, emailAccountEntity.getEmailBoxes())));
		emailAccountModel.setEnabled(emailAccountEntity.isEnabled());
		return emailAccountModel;
	}
	
	public List<EmailAccount> toModelList(Collection<EmailAccountEntity> emailAccountEntityList) {
		if (emailAccountEntityList == null)
			return null;
		List<EmailAccount> emailAccountModelList = new ArrayList<EmailAccount>();
		for (EmailAccountEntity emailAccountEntity : emailAccountEntityList) {
			EmailAccount emailAccountModel = toModel(emailAccountEntity);
			emailAccountModelList.add(emailAccountModel);
		}
		return emailAccountModelList;
	}
	
	public EmailAccountEntity toEntity(EmailAccount emailAccountModel) {
		if (emailAccountModel == null)
			return null;
		EmailAccountEntity emailAccountEntity = createEntity();
		toEntity(emailAccountEntity, emailAccountModel);
		return emailAccountEntity;
	}
	
	public void toEntity(EmailAccountEntity emailAccountEntity, EmailAccount emailAccountModel) {
		if (emailAccountEntity != null && emailAccountModel != null) {
			emailAccountEntity.setId(emailAccountModel.getId());
			emailAccountEntity.setUserId(emailAccountModel.getUserId());
			emailAccountEntity.setPasswordHash(emailAccountModel.getPasswordHash());
			emailAccountEntity.setPasswordSalt(emailAccountModel.getPasswordSalt());
			emailAccountEntity.setFirstName(emailAccountModel.getFirstName());
			emailAccountEntity.setLastName(emailAccountModel.getLastName());
			emailAccountEntity.setEmailBoxes(new ArrayList<EmailBoxEntity>(emailBoxMapper.toEntityList(emailAccountEntity, emailAccountModel.getEmailBoxes())));
			emailAccountEntity.setEnabled(emailAccountModel.isEnabled());
		}
	}
	
	public List<EmailAccountEntity> toEntityList(Collection<EmailAccount> emailAccountModelList) {
		if (emailAccountModelList == null)
			return null;
		List<EmailAccountEntity> emailAccountEntityList = new ArrayList<EmailAccountEntity>();
		for (EmailAccount emailAccountModel : emailAccountModelList) {
			EmailAccountEntity emailAccountEntity = toEntity(emailAccountModel);
			emailAccountEntityList.add(emailAccountEntity);
		}
		return emailAccountEntityList;
	}
	
}
