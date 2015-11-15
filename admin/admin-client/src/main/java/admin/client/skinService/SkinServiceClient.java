package admin.client.skinService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import admin.Skin;


public class SkinServiceClient extends AbstractDelegate implements SkinService {
	
	private static final Log log = LogFactory.getLog(SkinServiceClient.class);
	

	@Override
	public String getDomain() {
		return "admin";
	}
	
	@Override
	public String getServiceId() {
		return SkinService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public SkinService getProxy() throws Exception {
		return getProxy(SkinService.ID);
	}
	
	@Override
	public List<Skin> getSkinList() {
		try {
			List<Skin> skinList = getProxy().getSkinList();
			return skinList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Skin getSkinById(Long id) {
		try {
			Skin skin = getProxy().getSkinById(id);
			return skin;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Skin getSkinByName(String name) {
		try {
			Skin skin = getProxy().getSkinByName(name);
			return skin;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addSkin(Skin skin) {
		try {
			Long id = getProxy().addSkin(skin);
			return id;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveSkin(Skin skin) {
		try {
			getProxy().saveSkin(skin);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteSkin(Skin skin) {
		try {
			getProxy().deleteSkin(skin);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
