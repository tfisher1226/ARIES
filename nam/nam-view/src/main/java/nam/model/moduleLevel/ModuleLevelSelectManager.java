package nam.model.moduleLevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.ModuleLevel;


@SessionScoped
@Named("moduleLevelSelectManager")
public class ModuleLevelSelectManager extends AbstractSelectManager<ModuleLevel, ModuleLevelListObject> implements Serializable {
	
	@Inject
	private ModuleLevelHelper moduleLevelHelper;
	
	
	@Override
	public String getClientId() {
		return "moduleLevelSelect";
	}
	
	@Override
	public String getTitle() {
		return "ModuleLevel Selection";
	}
	
	@Override
	protected Class<ModuleLevel> getRecordClass() {
		return ModuleLevel.class;
	}
	
	@Override
	public String toString(ModuleLevel moduleLevel) {
		return moduleLevel.name();
	}
	
	protected ModuleLevelHelper getModuleLevelHelper() {
		return BeanContext.getFromSession("moduleLevelHelper");
	}
	
	protected ModuleLevelListManager getModuleLevelListManager() {
		return BeanContext.getFromSession("moduleLevelListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshModuleLevelList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<ModuleLevel> recordList) {
		ModuleLevelListManager moduleLevelListManager = getModuleLevelListManager();
		DataModel<ModuleLevelListObject> dataModel = moduleLevelListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshModuleLevelList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<ModuleLevel> refreshRecords() {
		ModuleLevel[] values = ModuleLevel.values();
		List<ModuleLevel> masterList = new ArrayList<ModuleLevel>();
		for (ModuleLevel capability : values) {
			masterList.add(capability);
		}
		return masterList;
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
	public void sortRecords(List<ModuleLevel> moduleLevelList) {
		Collections.sort(moduleLevelList);
	}
	
}
