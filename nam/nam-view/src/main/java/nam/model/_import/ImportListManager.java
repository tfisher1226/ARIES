package nam.model._import;

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

import nam.model.Import;
import nam.model.util.ImportUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("importListManager")
public class ImportListManager extends AbstractDomainListManager<Import, ImportListObject> implements Serializable {
	
	@Inject
	private ImportDataManager importDataManager;
	
	@Inject
	private ImportEventManager importEventManager;
	
	@Inject
	private ImportInfoManager importInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "importList";
	}
	
	@Override
	public String getTitle() {
		return "Import List";
	}
	
	@Override
	public Object getRecordKey(Import _import) {
		return ImportUtil.getKey(_import);
	}
	
	@Override
	public String getRecordName(Import _import) {
		return ImportUtil.getLabel(_import);
	}
	
	@Override
	protected Class<Import> getRecordClass() {
		return Import.class;
	}
	
	@Override
	protected Import getRecord(ImportListObject rowObject) {
		return rowObject.getImport();
	}
	
	@Override
	public Import getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ImportUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Import _import) {
		super.setSelectedRecord(_import);
		fireSelectedEvent(_import);
	}
	
	protected void fireSelectedEvent(Import _import) {
		importEventManager.fireSelectedEvent(_import);
	}
	
	public boolean isSelected(Import _import) {
		Import selection = selectionContext.getSelection("import");
		boolean selected = selection != null && selection.equals(_import);
		return selected;
	}
	
	@Override
	protected ImportListObject createRowObject(Import _import) {
		ImportListObject listObject = new ImportListObject(_import);
		listObject.setSelected(isSelected(_import));
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
	protected Collection<Import> createRecordList() {
		try {
			Collection<Import> importList = importDataManager.getImportList();
			if (importList != null)
				return importList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewImport() {
		return viewImport(selectedRecordKey);
	}
	
	public String viewImport(Object recordKey) {
		Import _import = recordByKeyMap.get(recordKey);
		return viewImport(_import);
	}
	
	public String viewImport(Import _import) {
		String url = importInfoManager.viewImport(_import);
		return url;
	}
	
	public String editImport() {
		return editImport(selectedRecordKey);
	}
	
	public String editImport(Object recordKey) {
		Import _import = recordByKeyMap.get(recordKey);
		return editImport(_import);
	}
	
	public String editImport(Import _import) {
		String url = importInfoManager.editImport(_import);
		return url;
	}
	
	public void removeImport() {
		removeImport(selectedRecordKey);
	}
	
	public void removeImport(Object recordKey) {
		Import _import = recordByKeyMap.get(recordKey);
		removeImport(_import);
	}
	
	public void removeImport(Import _import) {
		try {
			if (importDataManager.removeImport(_import))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelImport(@Observes @Cancelled Import _import) {
		try {
			//Object key = ImportUtil.getKey(import);
			//recordByKeyMap.put(key, import);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("import");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateImport(Collection<Import> importList) {
		return ImportUtil.validate(importList);
	}
	
	public void exportImportList(@Observes @Export String tableId) {
		//String tableId = "pageForm:importListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
