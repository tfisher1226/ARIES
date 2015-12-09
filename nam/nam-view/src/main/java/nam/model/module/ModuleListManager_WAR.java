package nam.model.module;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Service;
import nam.model.service.ServiceListObject;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceManager;


@SessionScoped
@Named("warModuleListManager")
public class ModuleListManager_WAR extends AbstractDomainListManager<Module, ModuleListObject> implements Serializable {
	
	@Inject
	private ModuleDataManager moduleDataManager;
	
	@Inject
	private ModuleEventManager moduleEventManager;
	
	@Inject
	private ModuleInfoManager moduleInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "moduleList";
	}
	
	@Override
	public String getTitle() {
		return "Module List";
	}
	
	@Override
	public Object getRecordKey(Module module) {
		return ModuleUtil.getKey(module);
	}
	
	@Override
	public String getRecordName(Module module) {
		return ModuleUtil.getLabel(module);
	}
	
	@Override
	protected Class<Module> getRecordClass() {
		return Module.class;
	}
	
	@Override
	protected Module getRecord(ModuleListObject rowObject) {
		return rowObject.getModule();
	}
	
	@Override
	public Module getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ModuleUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Module module) {
		super.setSelectedRecord(module);
		fireSelectedEvent(module);
	}
	
	protected void fireSelectedEvent(Module module) {
		moduleEventManager.fireSelectedEvent(module);
	}
	
	public boolean isSelected(Module module) {
		Module selection = selectionContext.getSelection("module");
		boolean selected = selection != null && selection.equals(module);
		return selected;
	}
	
	@Override
	protected ModuleListObject createRowObject(Module module) {
		ModuleListObject listObject = new ModuleListObject(module);
		listObject.setSelected(isSelected(module));
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
	protected Collection<Module> createRecordList() {
		try {
			Collection<Module> moduleList = moduleDataManager.getModuleList(ModuleType.WAR);
			if (moduleList != null)
				return moduleList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewModule() {
		return viewModule(selectedRecordKey);
	}
	
	public String viewModule(Object recordKey) {
		Module module = recordByKeyMap.get(recordKey);
		return viewModule(module);
	}
	
	public String viewModule(Module module) {
		String url = moduleInfoManager.viewModule(module);
		return url;
	}
	
	public String editModule() {
		return editModule(selectedRecordKey);
	}
	
	public String editModule(Object recordKey) {
		Module module = recordByKeyMap.get(recordKey);
		return editModule(module);
	}
	
	public String editModule(Module module) {
		String url = moduleInfoManager.editModule(module);
		return url;
	}
	
	public void removeModule() {
		removeModule(selectedRecordKey);
	}
	
	public void removeModule(Object recordKey) {
		Module module = recordByKeyMap.get(recordKey);
		removeModule(module);
	}
	
	public void removeModule(Module module) {
		try {
			if (moduleDataManager.removeModule(module))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelModule(@Observes @Cancelled Module module) {
		try {
			//Object key = ModuleUtil.getKey(module);
			//recordByKeyMap.put(key, module);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("module");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateModule(Collection<Module> moduleList) {
		return ModuleUtil.validate(moduleList);
	}
	
	public void exportModuleList(@Observes @Export String tableId) {
		//String tableId = "pageForm:moduleListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
