package admin.client.skinService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import admin.Skin;


@SuppressWarnings("serial")
public class SkinServiceInterceptor extends MessageInterceptor<SkinService> implements SkinService {
	
	@Override
	public List<Skin> getSkinList() {
		try {
			log.info("#### [admin]: getSkinList() sending...");
			Message request = createMessage("getSkinList");
			Message response = getProxy().invoke(request);
		List<Skin> skinList = response.getPart("skinList");
			return skinList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Skin getSkinById(Long id) {
		try {
			log.info("#### [admin]: getSkinById() sending...");
			Message request = createMessage("getSkinById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Skin skin = response.getPart("skin");
			return skin;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Skin getSkinByName(String name) {
		try {
			log.info("#### [admin]: getSkinByName() sending...");
			Message request = createMessage("getSkinByName");
			request.addPart("name", name);
			Message response = getProxy().invoke(request);
		Skin skin = response.getPart("skin");
			return skin;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addSkin(Skin skin) {
		try {
			log.info("#### [admin]: addSkin() sending...");
			Message request = createMessage("addSkin");
			request.addPart("skin", skin);
			Message response = getProxy().invoke(request);
		Long id = response.getPart("id");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveSkin(Skin skin) {
		try {
			log.info("#### [admin]: saveSkin() sending...");
			Message request = createMessage("saveSkin");
			request.addPart("skin", skin);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteSkin(Skin skin) {
		try {
			log.info("#### [admin]: deleteSkin() sending...");
			Message request = createMessage("deleteSkin");
			request.addPart("skin", skin);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
