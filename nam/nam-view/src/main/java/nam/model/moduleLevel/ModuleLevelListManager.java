package nam.model.moduleLevel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.ModuleLevel;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("moduleLevelListManager")
public class ModuleLevelListManager extends AbstractDomainListManager<ModuleLevel, ModuleLevelListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "moduleLevelList";
	}
	
	@Override
	public String getTitle() {
		return "ModuleLevel List";
	}
	
	@Override
	public Object getRecordKey(ModuleLevel moduleLevel) {
		return moduleLevel.name();
	}
	
	@Override
	public String getRecordName(ModuleLevel moduleLevel) {
		return moduleLevel.name();
	}
	
	@Override
	protected Class<ModuleLevel> getRecordClass() {
		return ModuleLevel.class;
	}
	
	@Override
	protected ModuleLevel getRecord(ModuleLevelListObject rowObject) {
		return rowObject.getModuleLevel();
	}
	
	@Override
	public ModuleLevel getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(ModuleLevel moduleLevel) {
		super.setSelectedRecord(moduleLevel);
	}
	
	public boolean isSelected(ModuleLevel moduleLevel) {
		ModuleLevel selection = selectionContext.getSelection("moduleLevel");
		boolean selected = selection != null && selection.equals(moduleLevel);
		return selected;
	}
	
	@Override
	protected ModuleLevelListObject createRowObject(ModuleLevel moduleLevel) {
		ModuleLevelListObject listObject = new ModuleLevelListObject(moduleLevel);
		listObject.setSelected(isSelected(moduleLevel));
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
	protected Collection<ModuleLevel> createRecordList() {
		try {
			Collection<ModuleLevel> moduleLevelList = Arrays.asList(ModuleLevel.values());
			return moduleLevelList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
