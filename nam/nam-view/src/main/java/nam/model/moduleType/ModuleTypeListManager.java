package nam.model.moduleType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.ModuleType;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("moduleTypeListManager")
public class ModuleTypeListManager extends AbstractDomainListManager<ModuleType, ModuleTypeListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "moduleTypeList";
	}
	
	@Override
	public String getTitle() {
		return "ModuleType List";
	}
	
	@Override
	public Object getRecordKey(ModuleType moduleType) {
		return moduleType.name();
	}
	
	@Override
	public String getRecordName(ModuleType moduleType) {
		return moduleType.name();
	}
	
	@Override
	protected Class<ModuleType> getRecordClass() {
		return ModuleType.class;
	}
	
	@Override
	protected ModuleType getRecord(ModuleTypeListObject rowObject) {
		return rowObject.getModuleType();
	}
	
	@Override
	public ModuleType getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(ModuleType moduleType) {
		super.setSelectedRecord(moduleType);
	}
	
	public boolean isSelected(ModuleType moduleType) {
		ModuleType selection = selectionContext.getSelection("moduleType");
		boolean selected = selection != null && selection.equals(moduleType);
		return selected;
	}
	
	@Override
	protected ModuleTypeListObject createRowObject(ModuleType moduleType) {
		ModuleTypeListObject listObject = new ModuleTypeListObject(moduleType);
		listObject.setSelected(isSelected(moduleType));
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
	protected Collection<ModuleType> createRecordList() {
		try {
			Collection<ModuleType> moduleTypeList = Arrays.asList(ModuleType.values());
			return moduleTypeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
