package nam.model.packageType;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.PackageType;
import nam.model.util.PackageTypeUtil;


@SessionScoped
@Named("packageTypeHelper")
public class PackageTypeHelper extends AbstractEnumerationHelper<PackageType> implements Serializable {
	
	@Produces
	public PackageType[] getPackageTypeArray() {
		return PackageType.values();
	}
	
	@Override
	public String toString(PackageType packageType) {
		return packageType.name();
	}
	
	@Override
	public String toString(Collection<PackageType> packageTypeList) {
		return PackageTypeUtil.toString(packageTypeList);
	}
	
}
