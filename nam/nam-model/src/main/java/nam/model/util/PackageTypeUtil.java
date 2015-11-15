package nam.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;

import nam.model.PackageType;


public class PackageTypeUtil extends BaseUtil {
	
	public static final PackageType[] VALUES_ARRAY = new PackageType[] {
		PackageType.JAR,
		PackageType.WAR,
		PackageType.EAR,
		PackageType.RAR,
		PackageType.SAR,
		PackageType.NONE
	};
	
	public static final List<PackageType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static PackageType getPackageType(int ordinal) {
		if (ordinal == PackageType.JAR.ordinal())
			return PackageType.JAR;
		if (ordinal == PackageType.WAR.ordinal())
			return PackageType.WAR;
		if (ordinal == PackageType.EAR.ordinal())
			return PackageType.EAR;
		if (ordinal == PackageType.RAR.ordinal())
			return PackageType.RAR;
		if (ordinal == PackageType.SAR.ordinal())
			return PackageType.SAR;
		if (ordinal == PackageType.NONE.ordinal())
			return PackageType.NONE;
		return null;
	}
	
	public static String toString(PackageType packageType) {
		return packageType.name();
	}
	
	public static String toString(Collection<PackageType> packageTypeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<PackageType> iterator = packageTypeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PackageType packageType = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(packageType);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<PackageType> packageTypeList) {
		Collections.sort(packageTypeList, createPackageTypeComparator());
	}
	
	public static Collection<PackageType> sortRecords(Collection<PackageType> packageTypeCollection) {
		List<PackageType> list = new ArrayList<PackageType>(packageTypeCollection);
		Collections.sort(list, createPackageTypeComparator());
		return list;
	}
	
	public static Comparator<PackageType> createPackageTypeComparator() {
		return new Comparator<PackageType>() {
			public int compare(PackageType packageType1, PackageType packageType2) {
				String text1 = packageType1.value();
				String text2 = packageType2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
