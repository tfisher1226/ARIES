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

import nam.model.ModuleType;


public class ModuleTypeUtil extends BaseUtil {

	public static final ModuleType[] VALUES_ARRAY = new ModuleType[] {
		ModuleType.POM, 
		ModuleType.MODEL, 
		ModuleType.DATA, 
		ModuleType.CLIENT, 
		ModuleType.SERVICE, 
		ModuleType.VIEW,
		ModuleType.TEST,
		ModuleType.WAR,
		ModuleType.EAR
	};

	public static final List<ModuleType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	
	public static ModuleType getModuleType(int ordinal) {
		if (ordinal == ModuleType.POM.ordinal()) 
			return ModuleType.POM;
		if (ordinal == ModuleType.MODEL.ordinal()) 
			return ModuleType.MODEL;
		if (ordinal == ModuleType.DATA.ordinal()) 
			return ModuleType.DATA;
		if (ordinal == ModuleType.CLIENT.ordinal()) 
			return ModuleType.CLIENT;
		if (ordinal == ModuleType.SERVICE.ordinal()) 
			return ModuleType.SERVICE;
		if (ordinal == ModuleType.VIEW.ordinal()) 
			return ModuleType.VIEW;
		if (ordinal == ModuleType.TEST.ordinal()) 
			return ModuleType.TEST;
		if (ordinal == ModuleType.WAR.ordinal())
			return ModuleType.WAR;
		if (ordinal == ModuleType.EAR.ordinal())
			return ModuleType.EAR;
		return null;
	}

	public static String toString(ModuleType moduleType) {
		return moduleType.name();
	}
	
	public static String toString(Collection<ModuleType> moduleTypeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<ModuleType> iterator = moduleTypeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModuleType moduleType = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(moduleType);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<ModuleType> moduleTypeList) {
		Collections.sort(moduleTypeList, createModuleTypeComparator());
	}
	
	public static Collection<ModuleType> sortRecords(Collection<ModuleType> moduleTypeCollection) {
		List<ModuleType> list = new ArrayList<ModuleType>(moduleTypeCollection);
		Collections.sort(list, createModuleTypeComparator());
		return list;
	}
	
	public static Comparator<ModuleType> createModuleTypeComparator() {
		return new Comparator<ModuleType>() {
			public int compare(ModuleType moduleType1, ModuleType moduleType2) {
				String text1 = moduleType1.value();
				String text2 = moduleType2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
