package admin.ui.skin;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.Skin;
import admin.client.skinService.SkinService;
import admin.util.SkinUtil;


@SessionScoped
@Named("skinSelectManager")
public class SkinSelectManager extends AbstractSelectManager<Skin, SkinListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "SkinSelect";
	}
	
	@Override
	public String getTitle() {
		return "Skin Selection";
	}
	
	@Override
	protected Class<Skin> getRecordClass() {
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
	
	protected SkinListManager getSkinListManager() {
		return BeanContext.getFromSession("skinListManager");
	}
	
	@Override
	//SEAM @Observer("org.aries.ui.reset")
	public void initialize() {
		initializeContext(); 
		refreshSkinList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Skin> recordList) {
		SkinListManager skinListManager = getSkinListManager();
		DataModel<SkinListObject> dataModel = skinListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	//SEAM @Observer("org.aries.refreshSkinList")
	public void refreshSkinList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<Skin> refreshRecords() {
		try {
			SkinService skinService = getSkinService();
			List<Skin> records = skinService.getSkinList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Skin> skinList) {
		sortRecordsByName(skinList);
	}
	
	public void sortRecordsByName(List<Skin> skinList) {
		SkinUtil.sortRecordsByName(skinList);
	}
	
}
