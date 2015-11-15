package org.aries.common.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.common.Attachment;
import org.aries.common.entity.AttachmentEntity;
import org.aries.data.map.AbstractMapper;


@Stateless
@Local(AttachmentMapper.class)
public class AttachmentMapper extends AbstractMapper<Attachment, AttachmentEntity> {
	
	public AttachmentMapper() {
		setModelClass(Attachment.class);
		setEntityClass(AttachmentEntity.class);
	}
	
	
	public Attachment toModel(AttachmentEntity attachmentEntity) {
		if (attachmentEntity == null)
			return null;
		Attachment attachmentModel = createModel();
		attachmentModel.setId(attachmentEntity.getId());
		attachmentModel.setName(attachmentEntity.getName());
		attachmentModel.setSize(attachmentEntity.getSize());
		attachmentModel.setFileName(attachmentEntity.getFileName());
		attachmentModel.setFileData(attachmentEntity.getFileData());
		attachmentModel.setContentType(attachmentEntity.getContentType());
		return attachmentModel;
	}
	
	public List<Attachment> toModelList(Collection<AttachmentEntity> attachmentEntityList) {
		if (attachmentEntityList == null)
			return null;
		List<Attachment> attachmentModelList = new ArrayList<Attachment>();
		for (AttachmentEntity attachmentEntity : attachmentEntityList) {
			Attachment attachmentModel = toModel(attachmentEntity);
			attachmentModelList.add(attachmentModel);
		}
		return attachmentModelList;
	}
	
	public AttachmentEntity toEntity(Attachment attachmentModel) {
		if (attachmentModel == null)
			return null;
		AttachmentEntity attachmentEntity = createEntity();
		toEntity(attachmentEntity, attachmentModel);
		return attachmentEntity;
	}
	
	public void toEntity(AttachmentEntity attachmentEntity, Attachment attachmentModel) {
		if (attachmentEntity != null && attachmentModel != null) {
			attachmentEntity.setId(attachmentModel.getId());
			attachmentEntity.setName(attachmentModel.getName());
			attachmentEntity.setSize(attachmentModel.getSize());
			attachmentEntity.setFileName(attachmentModel.getFileName());
			attachmentEntity.setFileData(attachmentModel.getFileData());
			attachmentEntity.setContentType(attachmentModel.getContentType());
		}
	}
	
	public List<AttachmentEntity> toEntityList(Collection<Attachment> attachmentModelList) {
		if (attachmentModelList == null)
			return null;
		List<AttachmentEntity> attachmentEntityList = new ArrayList<AttachmentEntity>();
		for (Attachment attachmentModel : attachmentModelList) {
			AttachmentEntity attachmentEntity = toEntity(attachmentModel);
			attachmentEntityList.add(attachmentEntity);
		}
		return attachmentEntityList;
	}
	
}
