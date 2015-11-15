package nam.model.modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Modules;
import nam.model.util.ModulesUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.aries.ui.manager.ExportManager;


@SessionScoped
@Named("modulesListManager")
public class ModulesListManager extends AbstractTabListManager<Modules, ModulesListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "ModulesList";
	}
	
	@Override
	public String getTitle() {
		return "Modules List";
	}
	
	@Override
	public String getRecordName(Modules modules) {
		return ModulesUtil.toString(modules);
	}
	
	@Override
	protected Class<Modules> getRecordClass() {
		return Modules.class;
	}
	
	@Override
	protected Modules getRecord(ModulesListObject rowObject) {
		return rowObject.getModules();
	}
	
	@Override
	protected ModulesListObject createRowObject(Modules modules) {
		return new ModulesListObject(modules);
	}
	
	protected ModulesHelper getModulesHelper() {
		return BeanContext.getFromSession("modulesHelper");
	}
	
//	protected ModulesInfoManager getModulesInfoManager() {
//		return BeanContext.getFromSession("modulesInfoManager");
//	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected List<Modules> createRecordList() {
		try {
			List<Modules> modulesList = new ArrayList<Modules>();
			return modulesList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void editModules() {
		editModules(selectedRecordId);
	}
	
	public void editModules(String recordId) {
		editModules(Long.parseLong(recordId));
	}
	
	public void editModules(Long recordId) {
		Modules modules = recordByIdMap.get(recordId);
		editModules(modules);
	}
	
	public void editModules(Modules modules) {
		//ModulesInfoManager modulesInfoManager = BeanContext.getFromSession("modulesInfoManager");
		//modulesInfoManager.editModules(modules);
	}
	
	public void removeModules() {
		removeModules(selectedRecordId);
	}
	
	public void removeModules(String recordId) {
		removeModules(Long.parseLong(recordId));
	}
	
	public void removeModules(Long recordId) {
		Modules modules = recordByIdMap.get(recordId);
		removeModules(modules);
	}
	
	public void removeModules(Modules modules) {
		try {
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateModules(Collection<Modules> modulesList) {
		return ModulesUtil.validate(modulesList);
	}
	
	public void exportModulesList() {
		String tableId = "pageForm:modulesListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
