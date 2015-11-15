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

import nam.model.DependencyScope;


public class DependencyScopeUtil extends BaseUtil {
	
	public static final DependencyScope[] VALUES_ARRAY = new DependencyScope[] {
		DependencyScope.COMPILE,
		DependencyScope.PROVIDED,
		DependencyScope.IMPORT,
		DependencyScope.SYSTEM,
		DependencyScope.TEST
	};
	
	public static final List<DependencyScope> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static DependencyScope getDependencyScope(int ordinal) {
		if (ordinal == DependencyScope.COMPILE.ordinal())
			return DependencyScope.COMPILE;
		if (ordinal == DependencyScope.PROVIDED.ordinal())
			return DependencyScope.PROVIDED;
		if (ordinal == DependencyScope.IMPORT.ordinal())
			return DependencyScope.IMPORT;
		if (ordinal == DependencyScope.SYSTEM.ordinal())
			return DependencyScope.SYSTEM;
		if (ordinal == DependencyScope.TEST.ordinal())
			return DependencyScope.TEST;
		return null;
	}
	
	public static String toString(DependencyScope dependencyScope) {
		return dependencyScope.name();
	}
	
	public static String toString(Collection<DependencyScope> dependencyScopeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<DependencyScope> iterator = dependencyScopeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			DependencyScope dependencyScope = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(dependencyScope);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<DependencyScope> dependencyScopeList) {
		Collections.sort(dependencyScopeList, createDependencyScopeComparator());
	}
	
	public static Collection<DependencyScope> sortRecords(Collection<DependencyScope> dependencyScopeCollection) {
		List<DependencyScope> list = new ArrayList<DependencyScope>(dependencyScopeCollection);
		Collections.sort(list, createDependencyScopeComparator());
		return list;
	}
	
	public static Comparator<DependencyScope> createDependencyScopeComparator() {
		return new Comparator<DependencyScope>() {
			public int compare(DependencyScope dependencyScope1, DependencyScope dependencyScope2) {
				String text1 = dependencyScope1.value();
				String text2 = dependencyScope2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
