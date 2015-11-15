package nam.model.packageType;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.PackageType;


public class PackageTypeListObject extends AbstractListObject<PackageType> implements Comparable<PackageTypeListObject>, Serializable {
	
	private PackageType packageType;
	
	
	public PackageTypeListObject(PackageType packageType) {
		this.packageType = packageType;
	}
	
	
	public PackageType getPackageType() {
		return packageType;
	}
	
	@Override
	public String getLabel() {
		return toString(packageType);
	}
	
	@Override
	public String toString() {
		return toString(packageType);
	}
	
	@Override
	public String toString(PackageType packageType) {
		return packageType.name();
	}
	
	@Override
	public int compareTo(PackageTypeListObject other) {
		String thisText = toString(this.packageType);
		String otherText = toString(other.packageType);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PackageTypeListObject other = (PackageTypeListObject) object;
		String thisText = toString(this.packageType);
		String otherText = toString(other.packageType);
		return thisText.equals(otherText);
	}
	
}
