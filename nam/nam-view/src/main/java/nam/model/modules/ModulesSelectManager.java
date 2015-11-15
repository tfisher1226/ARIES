package nam.model.modules;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Modules;
import nam.model.util.ModulesUtil;


@SessionScoped
@Named("modulesSelectManager")
public class ModulesSelectManager extends AbstractSelectManager<Modules, ModulesListObject> implements Serializable {
	
	public String getModule() {
		return "ModulesSelect";
	}
	
	@Override
	public String getTitle() {
		return "Modules Selection";
	}
	
	@Override
	protected Class<Modules> getRecordClass() {
		return Modules.class;
	}
	
	@Override
	public boolean isEmpty(Modules modules) {
		return getModulesHelper().isEmpty(modules);
	}
	
	@Override
	public String toString(Modules modules) {
		return getModulesHelper().toString(modules);
	}
	
	protected ModulesHelper getModulesHelper() {
		return BeanContext.getFromSession("modulesHelper");
	}
	
	protected ModulesListManager getModulesListManager() {
		return BeanContext.getFromSession("modulesListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshModulesList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Modules> recordList) {
		ModulesListManager modulesListManager = getModulesListManager();
		DataModel<ModulesListObject> dataModel = modulesListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshModulesList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<Modules> refreshRecords() {
		try {
			//ModulesService modulesService = getModulesService();
			//List<Modules> records = modulesService.getModulesList();
			//return records;
			return null;
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
	public void sortRecords(List<Modules> modulesList) {
		Collections.sort(recordList, new Comparator<Modules>() {
			public int compare(Modules modules1, Modules modules2) {
				String text1 = ModulesUtil.toString(modules1);
				String text2 = ModulesUtil.toString(modules2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
