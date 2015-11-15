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

import nam.model.DependencyType;


public class DependencyTypeUtil extends BaseUtil {
	
	public static final DependencyType[] VALUES_ARRAY = new DependencyType[] {
		DependencyType.EAR,
		DependencyType.EJB,
		DependencyType.JAR,
		DependencyType.POM,
		DependencyType.RAR,
		DependencyType.SAR,
		DependencyType.WAR
	};
	
	public static final List<DependencyType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static DependencyType getDependencyType(int ordinal) {
		if (ordinal == DependencyType.EAR.ordinal())
			return DependencyType.EAR;
		if (ordinal == DependencyType.EJB.ordinal())
			return DependencyType.EJB;
		if (ordinal == DependencyType.JAR.ordinal())
			return DependencyType.JAR;
		if (ordinal == DependencyType.POM.ordinal())
			return DependencyType.POM;
		if (ordinal == DependencyType.RAR.ordinal())
			return DependencyType.RAR;
		if (ordinal == DependencyType.SAR.ordinal())
			return DependencyType.SAR;
		if (ordinal == DependencyType.WAR.ordinal())
			return DependencyType.WAR;
		return null;
	}
	
	public static String toString(DependencyType dependencyType) {
		return dependencyType.name();
	}
	
	public static String toString(Collection<DependencyType> dependencyTypeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<DependencyType> iterator = dependencyTypeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			DependencyType dependencyType = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(dependencyType);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<DependencyType> dependencyTypeList) {
		Collections.sort(dependencyTypeList, createDependencyTypeComparator());
	}
	
	public static Collection<DependencyType> sortRecords(Collection<DependencyType> dependencyTypeCollection) {
		List<DependencyType> list = new ArrayList<DependencyType>(dependencyTypeCollection);
		Collections.sort(list, createDependencyTypeComparator());
		return list;
	}
	
	public static Comparator<DependencyType> createDependencyTypeComparator() {
		return new Comparator<DependencyType>() {
			public int compare(DependencyType dependencyType1, DependencyType dependencyType2) {
				String text1 = dependencyType1.value();
				String text2 = dependencyType2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
