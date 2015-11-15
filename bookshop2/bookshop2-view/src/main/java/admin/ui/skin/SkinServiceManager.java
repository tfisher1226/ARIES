package admin.ui.skin;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractInvocationManager;
import org.aries.ui.InvocationValues;

import admin.Skin;
import admin.client.skinService.SkinService;
import admin.client.skinService.SkinServiceClient;


@SessionScoped
@Named("skinServiceManager")
public class SkinServiceManager extends AbstractInvocationManager implements Serializable {
	
	public SkinServiceManager() {
		initializeGetSkinList();
		initializeGetSkinById();
		initializeGetSkinByName();
		initializeAddSkin();
		initializeSaveSkin();
		initializeDeleteSkin();
	}
	
	
	protected SkinService getSkinService() {
		SkinServiceClient skinService = BeanContext.getFromSession(SkinService.ID);
		skinService.setTransportType(getTransportType());
		return skinService;
	}
	
	protected SkinInfoManager getSkinInfoManager() {
		return BeanContext.getFromSession("skinInfoManager");
	}
	
	protected SkinListManager getSkinListManager() {
		return BeanContext.getFromSession("skinListManager");
	}
	
	protected SkinSelectManager getSkinSelectManager() {
		return BeanContext.getFromSession("skinSelectManager");
	}
	
	public void initializeGetSkinList() {
		InvocationValues invocationValues = getInvocationValues("getSkinList");
		invocationValues.addResultListPlaceholder("Skin", "skinList");
	}
	
	public void initializeGetSkinById() {
		InvocationValues invocationValues = getInvocationValues("getSkinById");
		invocationValues.addParameterPlaceholder("Long", "id");
		invocationValues.addResultPlaceholder("Skin", "skin");
	}
	
	public void initializeGetSkinByName() {
		InvocationValues invocationValues = getInvocationValues("getSkinByName");
		invocationValues.addParameterPlaceholder("String", "name");
		invocationValues.addResultPlaceholder("Skin", "skin");
	}
	
	public void initializeAddSkin() {
		InvocationValues invocationValues = getInvocationValues("addSkin");
		invocationValues.addParameterPlaceholder("Skin", "skin");
		invocationValues.addResultPlaceholder("Long", "id");
	}
	
	public void initializeSaveSkin() {
		InvocationValues invocationValues = getInvocationValues("saveSkin");
		invocationValues.addParameterPlaceholder("Skin", "skin");
	}
	
	public void initializeDeleteSkin() {
		InvocationValues invocationValues = getInvocationValues("deleteSkin");
		invocationValues.addParameterPlaceholder("Skin", "skin");
	}
	
	//SEAM @Observer("skinEntered")
	public void handleSkinEntered() {
		SkinInfoManager manager = getSkinInfoManager();
		String serviceId = manager.getTargetService();
		Skin skin = manager.getSkin();
		setParameterValue(serviceId, "skin", skin);
	}
	
	//SEAM @Observer("skinSelected")
	public void handleSkinSelected() {
		SkinSelectManager manager = getSkinSelectManager();
		String serviceId = manager.getTargetService();
		Skin selectedSkin = manager.getSelectedRecord();
		setParameterValue(serviceId, "skin", selectedSkin);
	}
	
	//SEAM @Observer("skinListSelected")
	public void handleSkinListSelected() {
		SkinSelectManager manager = getSkinSelectManager();
		String serviceId = manager.getTargetService();
		Collection<Skin> selectedSkinList = manager.getSelectedRecords();
		setParameterValue(serviceId, "skinList", selectedSkinList);
	}
	
	public void executeGetSkinList() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			List<Skin> skinList = getSkinService().getSkinList();
			Assert.notNull(skinList, "SkinList result(s) not found");
			invocationValues.setResultValue(skinList);
			outject("skinList", skinList);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetSkinById() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Long id = invocationValues.getParameterValue("id");
			Assert.notNull(id, "Id parameter must be specified");
			Skin skin = getSkinService().getSkinById(id);
			Assert.notNull(skin, "Skin result not found");
			invocationValues.setResultValue(skin);
			outject("skin", skin);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetSkinByName() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			String name = invocationValues.getParameterValue("name");
			Assert.notNull(name, "Name parameter must be specified");
			Skin skin = getSkinService().getSkinByName(name);
			Assert.notNull(skin, "Skin result not found");
			invocationValues.setResultValue(skin);
			outject("skin", skin);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeAddSkin() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Skin skin = invocationValues.getParameterValue("skin");
			Assert.notNull(skin, "Skin parameter must be specified");
			Long id = getSkinService().addSkin(skin);
			Assert.notNull(id, "Id result not found");
			invocationValues.setResultValue(id);
			outject("id", id);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeSaveSkin() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Skin skin = invocationValues.getParameterValue("skin");
			Assert.notNull(skin, "Skin parameter must be specified");
			getSkinService().saveSkin(skin);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeDeleteSkin() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Skin skin = invocationValues.getParameterValue("skin");
			Assert.notNull(skin, "Skin parameter must be specified");
			getSkinService().deleteSkin(skin);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
}
