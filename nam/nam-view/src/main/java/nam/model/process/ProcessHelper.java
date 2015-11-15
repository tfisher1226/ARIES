package nam.model.process;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Cache;
import nam.model.Unit;
import nam.model.Process;
import nam.model.cache.CacheListManager;
import nam.model.cache.CacheListObject;
import nam.model.unit.UnitListManager;
import nam.model.unit.UnitListObject;
import nam.model.util.ProcessUtil;


@SessionScoped
@Named("processHelper")
public class ProcessHelper extends AbstractElementHelper<Process> implements Serializable {
	
	@Override
	public boolean isEmpty(Process process) {
		return ProcessUtil.isEmpty(process);
	}
	
	@Override
	public String toString(Process process) {
		return ProcessUtil.toString(process);
	}
	
	@Override
	public String toString(Collection<Process> processList) {
		return ProcessUtil.toString(processList);
	}
	
	@Override
	public boolean validate(Process process) {
		return ProcessUtil.validate(process);
	}
	
	@Override
	public boolean validate(Collection<Process> processList) {
		return ProcessUtil.validate(processList);
	}
	
	public DataModel<CacheListObject> getCacheUnits(Process process) {
		if (process == null)
			return null;
		return getCacheUnits(process.getCacheUnits());
	}
	
	public DataModel<CacheListObject> getCacheUnits(Collection<Cache> cacheUnitsList) {
		CacheListManager cacheListManager = BeanContext.getFromSession("cacheListManager");
		DataModel<CacheListObject> dataModel = cacheListManager.getDataModel(cacheUnitsList);
		return dataModel;
	}
	
	public DataModel<UnitListObject> getDataUnits(Process process) {
		if (process == null)
			return null;
		return getDataUnits(process.getDataUnits());
	}
	
	public DataModel<UnitListObject> getDataUnits(Collection<Unit> dataUnitsList) {
		UnitListManager unitListManager = BeanContext.getFromSession("unitListManager");
		DataModel<UnitListObject> dataModel = unitListManager.getDataModel(dataUnitsList);
		return dataModel;
	}
	
}
