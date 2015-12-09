package admin.skin;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.util.Validator;

import admin.Skin;
import admin.client.skinService.SkinService;
import admin.util.SkinUtil;


@SessionScoped
@Named("skinInfoManager")
public class SkinInfoManager extends AbstractNamRecordManager<Skin> implements Serializable {
	
	@Inject
	private SkinWizard skinWizard;
	
	@Inject
	private SkinDataManager skinDataManager;
	
	@Inject
	private SkinPageManager skinPageManager;
	
	@Inject
	private SkinEventManager skinEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private SkinHelper skinHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public SkinInfoManager() {
		setInstanceName("skin");
	}
	
	
	public Skin getSkin() {
		return getRecord();
	}
	
	public Skin getSelectedSkin() {
		return selectionContext.getSelection("skin");
	}
	
	@Override
	public Class<Skin> getRecordClass() {
		return Skin.class;
	}
	
	@Override
	public boolean isEmpty(Skin skin) {
		return skinHelper.isEmpty(skin);
	}
	
	@Override
	public String toString(Skin skin) {
		return skinHelper.toString(skin);
	}
	
	protected SkinService getSkinService() {
		return BeanContext.getFromSession(SkinService.ID);
	}
	
	@Override
	public void initialize() {
		Skin skin = selectionContext.getSelection("skin");
		if (skin != null)
			initialize(skin);
	}
	
	protected void initialize(Skin skin) {
		skinWizard.initialize(skin);
		setContext("skin", skin);
	}
	
	@Override
	public String newRecord() {
		return newSkin();
	}
	
	public String newSkin() {
		try {
			Skin skin = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("skin",  skin);
			String url = skinPageManager.initializeSkinCreationPage(skin);
			skinPageManager.pushContext(skinWizard);
			initialize(skin);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
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
	
	@Override
	public String viewRecord() {
		return viewSkin();
	}
	
	public String viewSkin() {
		Skin skin = selectionContext.getSelection("skin");
		String url = viewSkin(skin);
		return url;
	}
	
	public String viewSkin(Skin skin) {
		try {
			String url = skinPageManager.initializeSkinSummaryView(skin);
			skinPageManager.pushContext(skinWizard);
			initialize(skin);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editSkin();
	}
	
	public String editSkin() {
		Skin skin = selectionContext.getSelection("skin");
		String url = editSkin(skin);
		return url;
	}
	
	public String editSkin(Skin skin) {
		try {
			//skin = clone(skin);
			selectionContext.resetOrigin();
			selectionContext.setSelection("skin",  skin);
			String url = skinPageManager.initializeSkinUpdatePage(skin);
			skinPageManager.pushContext(skinWizard);
			initialize(skin);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveSkin() {
		Skin skin = getSkin();
		if (validateSkin(skin)) {
			if (isImmediate())
				persistSkin(skin);
			outject("skin", skin);
		}
	}
	
	public void persistSkin(Skin skin) {
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
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveSkinToSystem(Skin skin) {
		skinDataManager.saveSkin(skin);
	}
	
	public void handleSaveSkin(@Observes @Add Skin skin) {
		saveSkin(skin);
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
	
	@Override
	public boolean validate(Skin skin) {
		return validateSkin(skin);
	}
	
	public boolean validateSkin(Skin skin) {
		Validator validator = getValidator();
		boolean isValid = SkinUtil.validate(skin);
		Display display = getFromSession("display");
		display.setModule("skinInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveSkin() {
		display = getFromSession("display");
		display.setModule("skinInfo");
		Skin skin = selectionContext.getSelection("skin");
		if (skin == null) {
			display.error("Skin record must be selected.");
		}
	}
	
	public String handleRemoveSkin(@Observes @Remove Skin skin) {
		display = getFromSession("display");
		display.setModule("skinInfo");
		try {
			display.info("Removing Skin "+SkinUtil.getLabel(skin)+" from the system.");
			removeSkinFromSystem(skin);
			selectionContext.clearSelection("skin");
			skinEventManager.fireClearSelectionEvent();
			skinEventManager.fireRemovedEvent(skin);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeSkinFromSystem(Skin skin) {
		if (skinDataManager.removeSkin(skin))
			setRecord(null);
	}
	
	public void cancelSkin() {
		BeanContext.removeFromSession("skin");
		skinPageManager.removeContext(skinWizard);
	}
	
}
