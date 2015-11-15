package nam.model.packageType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;

import nam.model.PackageType;


@SessionScoped
@Named("packageTypeListManager")
public class PackageTypeListManager extends AbstractTabListManager<PackageType, PackageTypeListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "PackageTypeList";
	}
	
	@Override
	public String getTitle() {
		return "PackageType List";
	}
	
	@Override
	public String getRecordName(PackageType packageType) {
		return packageType.name();
	}
	
	@Override
	protected Class<PackageType> getRecordClass() {
		return PackageType.class;
	}
	
	@Override
	protected PackageType getRecord(PackageTypeListObject rowObject) {
		return rowObject.getPackageType();
	}
	
	@Override
	protected PackageTypeListObject createRowObject(PackageType packageType) {
		return new PackageTypeListObject(packageType);
	}
	
	protected PackageTypeHelper getPackageTypeHelper() {
		return BeanContext.getFromSession("packageTypeHelper");
	}
	
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
	protected List<PackageType> createRecordList() {
		try {
			List<PackageType> packageTypeList = Arrays.asList(PackageType.values());
			return packageTypeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
