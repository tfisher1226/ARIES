package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.EmailAccount;
import org.aries.common.EmailBox;
import org.aries.common.EmailMessage;
import org.aries.common.entity.EmailAccountEntity;
import org.aries.common.entity.EmailBoxEntity;
import org.aries.common.entity.EmailMessageEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(EmailBoxMapper.class)
public class EmailBoxMapper extends AbstractMapper<EmailBox, EmailBoxEntity> {
	
	@Inject
	protected EmailMessageMapper emailMessageMapper;
	
	
	public EmailBoxMapper() {
		setModelClass(EmailBox.class);
		setEntityClass(EmailBoxEntity.class);
		if (emailMessageMapper == null)
			emailMessageMapper = new EmailMessageMapper();
	}
		
//	@SuppressWarnings("unchecked")
//	public EmailBox toModel(EmailBox parentEmailBox, EmailBoxEntity emailBoxEntity) {
//		if (emailBoxEntity == null)
//			return null;
//		EmailBox emailBoxModel = toModelFlat(parentEmailBox, emailBoxEntity);
//		Set<EmailBoxEntity> parentBoxEntityList = (Set<EmailBoxEntity>) emailBoxEntity.getParentBox();
//		if (parentBoxEntityList != null) {
//			List<EmailBox> parentBoxModelList = toModelList(emailBoxModel, parentBoxEntityList);
//			ListUtil.replaceElements((Set<EmailBox>) emailBoxModel.getParentBox(), parentBoxModelList);
//		}
//		return emailBoxModel;
//	}
	
//	public EmailBox toModelFlat(EmailBox parentEmailBox, EmailBoxEntity emailBoxEntity) {
//		if (emailBoxEntity == null)
//			return null;
//		EmailBox emailBoxModel = createModel();
//		emailBoxModel.setId(emailBoxEntity.getId());
//		emailBoxModel.setType(emailBoxEntity.getType());
//		emailBoxModel.setName(emailBoxEntity.getName());
//		emailBoxModel.setEmailAccount(toModel(emailBoxEntity.getEmailAccount());
//		emailBoxModel.setParentBox(parentEmailBox);
//		emailBoxModel.setMessages(new ArrayList<EmailMessage>(emailMessageMapper.toModelList(emailBoxEntity.getMessages())));
//		emailBoxModel.setCreationDate(emailBoxEntity.getCreationDate());
//		emailBoxModel.setLastUpdate(emailBoxEntity.getLastUpdate());
//		return emailBoxModel;
//	}
	
	public EmailBox toModel(EmailAccount emailAccount, EmailBoxEntity emailBoxEntity) {
		if (emailBoxEntity == null)
			return null;
		EmailBox emailBoxModel = createModel();
		emailBoxModel.setId(emailBoxEntity.getId());
		emailBoxModel.setType(emailBoxEntity.getType());
		emailBoxModel.setName(emailBoxEntity.getName());
		emailBoxModel.setEmailAccount(emailAccount);
		EmailBoxEntity parentBoxEntity = (EmailBoxEntity) emailBoxEntity.getParentBox();
		emailBoxModel.setParentBox(this.toModel(emailAccount, parentBoxEntity));
		emailBoxModel.setMessages(new ArrayList<EmailMessage>(emailMessageMapper.toModelList(emailBoxEntity.getMessages())));
		emailBoxModel.setCreationDate(emailBoxEntity.getCreationDate());
		emailBoxModel.setLastUpdate(emailBoxEntity.getLastUpdate());
		return emailBoxModel;
	}
	
	public List<EmailBox> toModelList(EmailAccount emailAccount, Collection<EmailBoxEntity> emailBoxEntityList) {
		if (emailBoxEntityList == null)
			return null;
		List<EmailBox> emailBoxModelList = new ArrayList<EmailBox>();
		for (EmailBoxEntity emailBoxEntity : emailBoxEntityList) {
			EmailBox emailBoxModel = toModel(emailAccount, emailBoxEntity);
			emailBoxModelList.add(emailBoxModel);
		}
		return emailBoxModelList;
	}
	
	public EmailBoxEntity toEntity(EmailAccountEntity emailAccountEntity, EmailBox emailBoxModel) {
		if (emailBoxModel == null)
			return null;
		EmailBoxEntity emailBoxEntity = createEntity();
		toEntity(emailAccountEntity, emailBoxEntity, emailBoxModel);
		return emailBoxEntity;
	}
	
	public void toEntity(EmailAccountEntity emailAccountEntity, EmailBoxEntity emailBoxEntity, EmailBox emailBoxModel) {
		if (emailBoxEntity != null && emailBoxModel != null) {
			emailBoxEntity.setId(emailBoxModel.getId());
			emailBoxEntity.setType(emailBoxModel.getType());
			emailBoxEntity.setName(emailBoxModel.getName());
			emailBoxEntity.setEmailAccount(emailAccountEntity);
			EmailBox parentBoxModel = (EmailBox) emailBoxModel.getParentBox();
			emailBoxEntity.setParentBox(this.toEntity(emailAccountEntity, parentBoxModel));
			emailBoxEntity.setMessages(new ArrayList<EmailMessageEntity>(emailMessageMapper.toEntityList(emailBoxModel.getMessages())));
			emailBoxEntity.setCreationDate(emailBoxModel.getCreationDate());
			emailBoxEntity.setLastUpdate(emailBoxModel.getLastUpdate());
		}
	}
	
	public List<EmailBoxEntity> toEntityList(EmailAccountEntity emailAccountEntity, Collection<EmailBox> emailBoxModelList) {
		if (emailBoxModelList == null)
			return null;
		List<EmailBoxEntity> emailBoxEntityList = new ArrayList<EmailBoxEntity>();
		for (EmailBox emailBoxModel : emailBoxModelList) {
			EmailBoxEntity emailBoxEntity = toEntity(emailAccountEntity, emailBoxModel);
			emailBoxEntityList.add(emailBoxEntity);
		}
		return emailBoxEntityList;
	}
	
}
