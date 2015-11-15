package nam.model.module;

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

import nam.model.Module;
import nam.model.util.ModuleUtil;


@SessionScoped
@Named("moduleSelectManager")
public class ModuleSelectManager extends AbstractSelectManager<Module, ModuleListObject> implements Serializable {
	
	@Inject
	private ModuleDataManager moduleDataManager;
	
	@Inject
	private ModuleHelper moduleHelper;
	
	
	@Override
	public String getClientId() {
		return "moduleSelect";
	}
	
	@Override
	public String getTitle() {
		return "Module Selection";
	}
	
	@Override
	protected Class<Module> getRecordClass() {
		return Module.class;
	}
	
	@Override
	public boolean isEmpty(Module module) {
		return moduleHelper.isEmpty(module);
	}
	
	@Override
	public String toString(Module module) {
		return moduleHelper.toString(module);
	}
	
	protected ModuleHelper getModuleHelper() {
		return BeanContext.getFromSession("moduleHelper");
	}
	
	protected ModuleListManager getModuleListManager() {
		return BeanContext.getFromSession("moduleListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshModuleList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Module> recordList) {
		ModuleListManager moduleListManager = getModuleListManager();
		DataModel<ModuleListObject> dataModel = moduleListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshModuleList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Module> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Module> moduleList = BeanContext.getFromConversation(instanceId);
		return moduleList;
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
	public void sortRecords(List<Module> moduleList) {
		Collections.sort(moduleList, new Comparator<Module>() {
			public int compare(Module module1, Module module2) {
				String text1 = ModuleUtil.toString(module1);
				String text2 = ModuleUtil.toString(module2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
