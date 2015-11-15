package nam.model.moduleType;

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

import nam.model.ModuleType;


@SessionScoped
@Named("moduleTypeSelectManager")
public class ModuleTypeSelectManager extends AbstractSelectManager<ModuleType, ModuleTypeListObject> implements Serializable {
	
	@Inject
	private ModuleTypeHelper moduleTypeHelper;
	
	
	@Override
	public String getClientId() {
		return "moduleTypeSelect";
	}
	
	@Override
	public String getTitle() {
		return "ModuleType Selection";
	}
	
	@Override
	protected Class<ModuleType> getRecordClass() {
		return ModuleType.class;
	}
	
	@Override
	public String toString(ModuleType moduleType) {
		return moduleType.name();
	}
	
	protected ModuleTypeHelper getModuleTypeHelper() {
		return BeanContext.getFromSession("moduleTypeHelper");
	}
	
	protected ModuleTypeListManager getModuleTypeListManager() {
		return BeanContext.getFromSession("moduleTypeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshModuleTypeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<ModuleType> recordList) {
		ModuleTypeListManager moduleTypeListManager = getModuleTypeListManager();
		DataModel<ModuleTypeListObject> dataModel = moduleTypeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshModuleTypeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<ModuleType> refreshRecords() {
		ModuleType[] values = ModuleType.values();
		List<ModuleType> masterList = new ArrayList<ModuleType>();
		for (ModuleType capability : values) {
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
	public void sortRecords(List<ModuleType> moduleTypeList) {
		Collections.sort(moduleTypeList);
	}
	
}
