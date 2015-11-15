package admin.ui.skin;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.util.Validator;

import admin.Skin;
import admin.client.skinService.SkinService;
import admin.util.SkinUtil;


@SessionScoped
@Named("skinInfoManager")
public class SkinInfoManager extends AbstractRecordManager<Skin> implements Serializable {
	
	public SkinInfoManager() {
		setInstanceName("skin");
	}
	
	
	public Skin getSkin() {
		return getRecord();
	}
	
	@Override
	public Class<Skin> getRecordClass() {
		return Skin.class;
	}
	
	@Override
	public boolean isEmpty(Skin skin) {
		return getSkinHelper().isEmpty(skin);
	}
	
	@Override
	public String toString(Skin skin) {
		return getSkinHelper().toString(skin);
	}
	
	protected SkinHelper getSkinHelper() {
		return BeanContext.getFromSession("skinHelper");
	}
	
	protected SkinService getSkinService() {
		return BeanContext.getFromSession(SkinService.ID);
	}
	
	protected void initialize(Skin skin) {
		SkinUtil.initialize(skin);
		initializeOutjectedState(skin);
		setContext("Skin", skin);
	}
	
	protected void initializeOutjectedState(Skin skin) {
		outject(instanceName, skin);
	}
	
	public void activate() {
		initializeContext();
		Skin skin = BeanContext.getFromSession(getInstanceId());
		if (skin == null)
			newSkin();
		else editSkin(skin);
	}
	
	//SEAM @Begin
	public void newSkin() {
		try {
			Skin skin = create();
			initialize(skin);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Skin create() {
		Skin skin = SkinUtil.create();
		return skin;
	}
	
	@Override
	public Skin clone(Skin skin) {
		skin = SkinUtil.clone(skin);
		return skin;
	}
	
	//SEAM @Begin
	public void editSkin(Skin skin) {
		try {
			skin = clone(skin);
			initialize(skin);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.saveSkin")
	public void saveSkin() {
		setModule("Skin");
		Skin skin = getSkin();
		enrichSkin(skin);
		if (validate(skin)) {
			if (isImmediate())
				processSkin(skin);
			outject("skin", skin);
			raiseEvent(actionEvent);
		}
	}
	
	public void processSkin(Skin skin) {
		Long id = skin.getId();
		if (id != null) {
			saveSkin(skin);
		} else {
			addSkin(skin);
		}
	}
	
	public void saveSkin(Skin skin) {
		try {
			getSkinService().saveSkin(skin);
			raiseEvent("org.aries.refreshSkinList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void addSkin(Skin skin) {
		try {
			Long id = getSkinService().addSkin(skin);
			Assert.notNull(id, "Skin Id should not be null");
			raiseEvent("org.aries.refreshSkinList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichSkin(Skin skin) {
		//nothing for now
	}
	
	public boolean validate(Skin skin) {
		Validator validator = getValidator();
		boolean isValid = SkinUtil.validate(skin);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
}
