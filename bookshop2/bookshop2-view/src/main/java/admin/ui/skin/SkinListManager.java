package admin.ui.skin;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import admin.Skin;
import admin.client.skinService.SkinService;
import admin.util.SkinUtil;


@SessionScoped
@Named("skinListManager")
public class SkinListManager extends AbstractTabListManager<Skin, SkinListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "SkinList";
	}
	
	@Override
	public String getTitle() {
		return "Skin List";
	}
	
	@Override
	public Object getRecordId(Skin skin) {
		return skin.getId();
	}
	
	@Override
	public String getRecordName(Skin skin) {
		return SkinUtil.toString(skin);
	}
	
	@Override
	protected Class<Skin> getRecordClass() {
		return Skin.class;
	}
	
	@Override
	protected Skin getRecord(SkinListObject rowObject) {
		return rowObject.getSkin();
	}
	
	@Override
	protected SkinListObject createRowObject(Skin skin) {
		return new SkinListObject(skin);
	}
	
	protected SkinHelper getSkinHelper() {
		return BeanContext.getFromSession("skinHelper");
	}
	
	protected SkinService getSkinService() {
		return BeanContext.getFromSession(SkinService.ID);
	}
	
	protected SkinInfoManager getSkinInfoManager() {
		return BeanContext.getFromSession("skinInfoManager");
	}
	
	@Override
	//SEAM @Observer("org.aries.ui.reset")
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	//SEAM @Observer("org.aries.refreshSkinList")
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected List<Skin> createRecordList() {
		try {
			List<Skin> skinList = getSkinService().getSkinList();
			return skinList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void editSkin() {
		editSkin(selectedRecordId);
	}
	
	public void editSkin(String recordId) {
		editSkin(Long.parseLong(recordId));
	}
	
	public void editSkin(Long recordId) {
		Skin skin = recordByIdMap.get(recordId);
		editSkin(skin);
	}
	
	public void editSkin(Skin skin) {
		SkinInfoManager skinInfoManager = BeanContext.getFromSession("skinInfoManager");
		skinInfoManager.editSkin(skin);
	}
	
	//SEAM @Observer("org.aries.removeSkin")
	public void removeSkin() {
		removeSkin(selectedRecordId);
	}
	
	public void removeSkin(String recordId) {
		removeSkin(Long.parseLong(recordId));
	}
	
	public void removeSkin(Long recordId) {
		Skin skin = recordByIdMap.get(recordId);
		removeSkin(skin);
	}
	
	public void removeSkin(Skin skin) {
		try {
			getSkinService().deleteSkin(selectedRecord);
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.cancelSkin")
	public void cancelSkin() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("skin");
			Long id = selectedRecord.getId();
			Skin skin = getSkinService().getSkinById(id);
			recordByIdMap.put(id, skin);
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateSkin(Collection<Skin> skinList) {
		return SkinUtil.validate(skinList);
	}
	
//	//SEAM @Observer("org.aries.exportSkinList")
//	public void exportSkinList() {
//		String tableId = "pageForm:skinListTable";
//		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
//		exportManager.exportToXLS(tableId);
//	}
	
}
