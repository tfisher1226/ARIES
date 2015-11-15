package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.Attachment;
import org.aries.common.EmailAddressList;
import org.aries.common.EmailMessage;
import org.aries.common.entity.AttachmentEntity;
import org.aries.common.entity.EmailAddressListEntity;
import org.aries.common.entity.EmailMessageEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(EmailMessageMapper.class)
public class EmailMessageMapper extends AbstractMapper<EmailMessage, EmailMessageEntity> {
	
	@Inject
	protected EmailAddressMapper emailAddressMapper;
	
	@Inject
	protected EmailAddressListMapper emailAddressListMapper;
	
	@Inject
	protected AttachmentMapper attachmentMapper;
	
	
	public EmailMessageMapper() {
		setModelClass(EmailMessage.class);
		setEntityClass(EmailMessageEntity.class);
		if (emailAddressMapper == null)
			emailAddressMapper = new EmailAddressMapper();
		if (emailAddressListMapper == null)
			emailAddressListMapper = new EmailAddressListMapper();
		if (attachmentMapper == null)
			attachmentMapper = new AttachmentMapper();
	}
	
	
	public EmailMessage toModel(EmailMessageEntity emailMessageEntity) {
		if (emailMessageEntity == null)
			return null;
		EmailMessage emailMessageModel = createModel();
		emailMessageModel.setId(emailMessageEntity.getId());
		emailMessageModel.setContent(emailMessageEntity.getContent());
		emailMessageModel.setSubject(emailMessageEntity.getSubject());
		emailMessageModel.setTimestamp(emailMessageEntity.getTimestamp());
		emailMessageModel.setSourceId(emailMessageEntity.getSourceId());
		emailMessageModel.setSmtpHost(emailMessageEntity.getSmtpHost());
		emailMessageModel.setSmtpPort(emailMessageEntity.getSmtpPort());
		emailMessageModel.setFromAddress(emailAddressMapper.toModel(emailMessageEntity.getFromAddress()));
		emailMessageModel.setToAddresses(new ArrayList<EmailAddressList>(emailAddressListMapper.toModelList(emailMessageEntity.getToAddresses())));
		emailMessageModel.setBccAddresses(new ArrayList<EmailAddressList>(emailAddressListMapper.toModelList(emailMessageEntity.getBccAddresses())));
		emailMessageModel.setCcAddresses(new ArrayList<EmailAddressList>(emailAddressListMapper.toModelList(emailMessageEntity.getCcAddresses())));
		emailMessageModel.setReplytoAddresses(new ArrayList<EmailAddressList>(emailAddressListMapper.toModelList(emailMessageEntity.getReplytoAddresses())));
		emailMessageModel.setAdminAddresses(new ArrayList<EmailAddressList>(emailAddressListMapper.toModelList(emailMessageEntity.getAdminAddresses())));
		emailMessageModel.setAttachments(new ArrayList<Attachment>(attachmentMapper.toModelList(emailMessageEntity.getAttachments())));
		emailMessageModel.setSendAsHtml(emailMessageEntity.isSendAsHtml());
		return emailMessageModel;
	}
	
	public List<EmailMessage> toModelList(Collection<EmailMessageEntity> emailMessageEntityList) {
		if (emailMessageEntityList == null)
			return null;
		List<EmailMessage> emailMessageModelList = new ArrayList<EmailMessage>();
		for (EmailMessageEntity emailMessageEntity : emailMessageEntityList) {
			EmailMessage emailMessageModel = toModel(emailMessageEntity);
			emailMessageModelList.add(emailMessageModel);
		}
		return emailMessageModelList;
	}
	
	public EmailMessageEntity toEntity(EmailMessage emailMessageModel) {
		if (emailMessageModel == null)
			return null;
		EmailMessageEntity emailMessageEntity = createEntity();
		toEntity(emailMessageEntity, emailMessageModel);
		return emailMessageEntity;
	}
	
	public void toEntity(EmailMessageEntity emailMessageEntity, EmailMessage emailMessageModel) {
		if (emailMessageEntity != null && emailMessageModel != null) {
			emailMessageEntity.setId(emailMessageModel.getId());
			emailMessageEntity.setContent(emailMessageModel.getContent());
			emailMessageEntity.setSubject(emailMessageModel.getSubject());
			emailMessageEntity.setTimestamp(emailMessageModel.getTimestamp());
			emailMessageEntity.setSourceId(emailMessageModel.getSourceId());
			emailMessageEntity.setSmtpHost(emailMessageModel.getSmtpHost());
			emailMessageEntity.setSmtpPort(emailMessageModel.getSmtpPort());
			emailMessageEntity.setFromAddress(emailAddressMapper.toEntity(emailMessageModel.getFromAddress()));
			emailMessageEntity.setToAddresses(new ArrayList<EmailAddressListEntity>(emailAddressListMapper.toEntityList(emailMessageModel.getToAddresses())));
			emailMessageEntity.setBccAddresses(new ArrayList<EmailAddressListEntity>(emailAddressListMapper.toEntityList(emailMessageModel.getBccAddresses())));
			emailMessageEntity.setCcAddresses(new ArrayList<EmailAddressListEntity>(emailAddressListMapper.toEntityList(emailMessageModel.getCcAddresses())));
			emailMessageEntity.setReplytoAddresses(new ArrayList<EmailAddressListEntity>(emailAddressListMapper.toEntityList(emailMessageModel.getReplytoAddresses())));
			emailMessageEntity.setAdminAddresses(new ArrayList<EmailAddressListEntity>(emailAddressListMapper.toEntityList(emailMessageModel.getAdminAddresses())));
			emailMessageEntity.setAttachments(new ArrayList<AttachmentEntity>(attachmentMapper.toEntityList(emailMessageModel.getAttachments())));
			emailMessageEntity.setSendAsHtml(emailMessageModel.isSendAsHtml());
		}
	}
	
	public List<EmailMessageEntity> toEntityList(Collection<EmailMessage> emailMessageModelList) {
		if (emailMessageModelList == null)
			return null;
		List<EmailMessageEntity> emailMessageEntityList = new ArrayList<EmailMessageEntity>();
		for (EmailMessage emailMessageModel : emailMessageModelList) {
			EmailMessageEntity emailMessageEntity = toEntity(emailMessageModel);
			emailMessageEntityList.add(emailMessageEntity);
		}
		return emailMessageEntityList;
	}
	
}
