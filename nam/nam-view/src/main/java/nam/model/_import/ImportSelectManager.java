package nam.model._import;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Import;
import nam.model.util.ImportUtil;


@SessionScoped
@Named("importSelectManager")
public class ImportSelectManager extends AbstractSelectManager<Import, ImportListObject> implements Serializable {
	
	@Inject
	private ImportDataManager importDataManager;
	
	@Inject
	private ImportHelper importHelper;
	
	
	@Override
	public String getClientId() {
		return "importSelect";
	}
	
	@Override
	public String getTitle() {
		return "Import Selection";
	}
	
	@Override
	protected Class<Import> getRecordClass() {
		return Import.class;
	}
	
	@Override
	public boolean isEmpty(Import _import) {
		return importHelper.isEmpty(_import);
	}
	
	@Override
	public String toString(Import _import) {
		return importHelper.toString(_import);
	}
	
	protected ImportHelper getImportHelper() {
		return BeanContext.getFromSession("importHelper");
	}
	
	protected ImportListManager getImportListManager() {
		return BeanContext.getFromSession("importListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshImportList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Import> recordList) {
		ImportListManager importListManager = getImportListManager();
		DataModel<ImportListObject> dataModel = importListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshImportList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Import> refreshRecords() {
		try {
			Collection<Import> records = importDataManager.getImportList();
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
	public void sortRecords(List<Import> importList) {
		Collections.sort(importList, new Comparator<Import>() {
			public int compare(Import import1, Import import2) {
				String text1 = ImportUtil.toString(import1);
				String text2 = ImportUtil.toString(import2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
