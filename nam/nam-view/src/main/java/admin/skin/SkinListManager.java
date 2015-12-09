package admin.skin;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import admin.Skin;
import admin.util.SkinUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("skinListManager")
public class SkinListManager extends AbstractDomainListManager<Skin, SkinListObject> implements Serializable {
	
	@Inject
	private SkinDataManager skinDataManager;
	
	@Inject
	private SkinEventManager skinEventManager;
	
	@Inject
	private SkinInfoManager skinInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "skinList";
	}
	
	@Override
	public String getTitle() {
		return "Skin List";
	}
	
	public Object getRecordId(Skin skin) {
		return skin.getId();
	}
	
	@Override
	public Object getRecordKey(Skin skin) {
		return SkinUtil.getKey(skin);
	}
	
	@Override
	public String getRecordName(Skin skin) {
		return SkinUtil.getLabel(skin);
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
	public Skin getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? SkinUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Skin skin) {
		super.setSelectedRecord(skin);
		fireSelectedEvent(skin);
	}
	
	protected void fireSelectedEvent(Skin skin) {
		skinEventManager.fireSelectedEvent(skin);
	}
	
	public boolean isSelected(Skin skin) {
		Skin selection = selectionContext.getSelection("skin");
		boolean selected = selection != null && selection.equals(skin);
		return selected;
	}
	
	public boolean isChecked(Skin skin) {
		Collection<Skin> selection = selectionContext.getSelection("skinSelection");
		boolean checked = selection != null && selection.contains(skin);
		return checked;
	}
	
	@Override
	protected SkinListObject createRowObject(Skin skin) {
		SkinListObject listObject = new SkinListObject(skin);
		listObject.setSelected(isSelected(skin));
		listObject.setChecked(isChecked(skin));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Skin> createRecordList() {
		try {
			Collection<Skin> skinList = skinDataManager.getSkinList();
			if (skinList != null)
			return skinList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewSkin() {
		return viewSkin(selectedRecordKey);
	}
	
	public String viewSkin(Object recordKey) {
		Skin skin = recordByKeyMap.get(recordKey);
		return viewSkin(skin);
	}
	
	public String viewSkin(Skin skin) {
		String url = skinInfoManager.viewSkin(skin);
		return url;
	}
	
	public String editSkin() {
		return editSkin(selectedRecordKey);
	}
	
	public String editSkin(Object recordKey) {
		Skin skin = recordByKeyMap.get(recordKey);
		return editSkin(skin);
	}
	
	public String editSkin(Skin skin) {
		String url = skinInfoManager.editSkin(skin);
		return url;
	}
	
	public void removeSkin() {
		removeSkin(selectedRecordKey);
	}
	
	public void removeSkin(Object recordKey) {
		Skin skin = recordByKeyMap.get(recordKey);
		removeSkin(skin);
	}
	
	public void removeSkin(Skin skin) {
		try {
			if (skinDataManager.removeSkin(skin))
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelSkin(@Observes @Cancelled Skin skin) {
		try {
			//Object key = SkinUtil.getKey(skin);
			//recordByKeyMap.put(key, skin);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("skin");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateSkin(Collection<Skin> skinList) {
		return SkinUtil.validate(skinList);
	}
	
	public void exportSkinList(@Observes @Export String tableId) {
		//String tableId = "pageForm:skinListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
