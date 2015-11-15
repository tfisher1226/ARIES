package admin.skin;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.Skin;
import admin.client.skinService.SkinService;
import admin.util.SkinUtil;


@SessionScoped
@Named("skinSelectManager")
public class SkinSelectManager extends AbstractSelectManager<Skin, SkinListObject> implements Serializable {
	
	@Inject
	private SkinDataManager skinDataManager;
	
	@Inject
	private SkinHelper skinHelper;
	
	
	@Override
	public String getClientId() {
		return "skinSelect";
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
		return skinHelper.isEmpty(skin);
	}
	
	@Override
	public String toString(Skin skin) {
		return skinHelper.toString(skin);
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
	
	public void refreshSkinList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Skin> refreshRecords() {
		try {
			Collection<Skin> records = skinDataManager.getSkinList();
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
