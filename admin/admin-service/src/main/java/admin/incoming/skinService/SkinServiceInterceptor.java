package admin.incoming.skinService;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import admin.Skin;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class SkinServiceInterceptor extends MessageInterceptor<SkinService> {
	
	private static final Log log = LogFactory.getLog(SkinServiceInterceptor.class);
	
	@Inject
	protected SkinServiceHandler skinServiceHandler;
	

	@TransactionAttribute(REQUIRED)
	public Message getSkinList(Message message) {
		try {
			List<Skin> skinList = skinServiceHandler.getSkinList();
			Assert.notNull(skinList, "SkinList must exist");
			message.addPart("skinList", skinList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getSkinById(Message message) {
		try {
			Long id = message.getPart("id");
			Skin skin = skinServiceHandler.getSkinById(id);
			Assert.notNull(skin, "Skin must exist");
			message.addPart("skin", skin);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message getSkinByName(Message message) {
		try {
			String name = message.getPart("name");
			Skin skin = skinServiceHandler.getSkinByName(name);
			Assert.notNull(skin, "Skin must exist");
			message.addPart("skin", skin);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message addSkin(Message message) {
		try {
			Skin skin = message.getPart("skin");
			Long id = skinServiceHandler.addSkin(skin);
			Assert.notNull(id, "Id must exist");
			message.addPart("id", id);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message saveSkin(Message message) {
		try {
			Skin skin = message.getPart("skin");
			skinServiceHandler.saveSkin(skin);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message deleteSkin(Message message) {
		try {
			Skin skin = message.getPart("skin");
			skinServiceHandler.deleteSkin(skin);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
}
