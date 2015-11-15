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

import nam.model.ModuleLevel;


public class ModuleLevelUtil extends BaseUtil {
	
	public static final ModuleLevel[] VALUES_ARRAY = new ModuleLevel[] {
		ModuleLevel.PROJECT_LEVEL,
		ModuleLevel.GROUP_LEVEL,
		ModuleLevel.APPLICATION_LEVEL
	};
	
	public static final List<ModuleLevel> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static ModuleLevel getModuleLevel(int ordinal) {
		if (ordinal == ModuleLevel.PROJECT_LEVEL.ordinal())
			return ModuleLevel.PROJECT_LEVEL;
		if (ordinal == ModuleLevel.GROUP_LEVEL.ordinal())
			return ModuleLevel.GROUP_LEVEL;
		if (ordinal == ModuleLevel.APPLICATION_LEVEL.ordinal())
			return ModuleLevel.APPLICATION_LEVEL;
		return null;
	}
	
	public static String toString(ModuleLevel moduleLevel) {
		return moduleLevel.name();
	}
	
	public static String toString(Collection<ModuleLevel> moduleLevelList) {
		StringBuffer buf = new StringBuffer();
		Iterator<ModuleLevel> iterator = moduleLevelList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModuleLevel moduleLevel = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(moduleLevel);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<ModuleLevel> moduleLevelList) {
		Collections.sort(moduleLevelList, createModuleLevelComparator());
	}
	
	public static Collection<ModuleLevel> sortRecords(Collection<ModuleLevel> moduleLevelCollection) {
		List<ModuleLevel> list = new ArrayList<ModuleLevel>(moduleLevelCollection);
		Collections.sort(list, createModuleLevelComparator());
		return list;
	}
	
	public static Comparator<ModuleLevel> createModuleLevelComparator() {
		return new Comparator<ModuleLevel>() {
			public int compare(ModuleLevel moduleLevel1, ModuleLevel moduleLevel2) {
				String text1 = moduleLevel1.value();
				String text2 = moduleLevel2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
