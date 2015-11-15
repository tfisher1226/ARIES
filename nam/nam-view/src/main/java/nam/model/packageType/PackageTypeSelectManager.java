package nam.model.packageType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.PackageType;


@SessionScoped
@Named("packageTypeSelectManager")
public class PackageTypeSelectManager extends AbstractSelectManager<PackageType, PackageTypeListObject> implements Serializable {
	
	//@Override
	public String getModule() {
		return "PackageTypeSelect";
	}
	
	@Override
	public String getTitle() {
		return "PackageType Selection";
	}
	
	@Override
	protected Class<PackageType> getRecordClass() {
		return PackageType.class;
	}
	
	@Override
	public String toString(PackageType packageType) {
		return packageType.name();
	}
	
	protected PackageTypeHelper getPackageTypeHelper() {
		return BeanContext.getFromSession("packageTypeHelper");
	}
	
	protected PackageTypeListManager getPackageTypeListManager() {
		return BeanContext.getFromSession("packageTypeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshPackageTypeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<PackageType> recordList) {
		PackageTypeListManager packageTypeListManager = getPackageTypeListManager();
		DataModel<PackageTypeListObject> dataModel = packageTypeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshPackageTypeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<PackageType> refreshRecords() {
		PackageType[] values = PackageType.values();
		List<PackageType> masterList = new ArrayList<PackageType>();
		for (PackageType capability : values) {
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
	public void sortRecords(List<PackageType> packageTypeList) {
		Collections.sort(packageTypeList);
	}
	
}
