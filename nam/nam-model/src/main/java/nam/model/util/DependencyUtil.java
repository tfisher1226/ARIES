package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Dependency;
import nam.model.DependencyScope;
import nam.model.DependencyType;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class DependencyUtil extends BaseUtil {
	
	public static Object getKey(Dependency dependency) {
		return dependency.getGroupId() + "." + dependency.getArtifactId();
	}
	
	public static String getLabel(Dependency dependency) {
		return dependency.getGroupId() + "." + dependency.getArtifactId();
	}
	
	public static boolean isEmpty(Dependency dependency) {
		if (dependency == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Dependency> dependencyList) {
		if (dependencyList == null  || dependencyList.size() == 0)
			return true;
		Iterator<Dependency> iterator = dependencyList.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (!isEmpty(dependency))
				return false;
		}
		return true;
	}
	
	public static String toString(Dependency dependency) {
		if (isEmpty(dependency))
			return "Dependency: [uninitialized] "+dependency.toString();
		String text = dependency.toString();
		return text;
	}
	
	public static String toString(Collection<Dependency> dependencyList) {
		if (isEmpty(dependencyList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Dependency> iterator = dependencyList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Dependency dependency = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(dependency);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Dependency create() {
		Dependency dependency = new Dependency();
		initialize(dependency);
		return dependency;
	}
	
	public static void initialize(Dependency dependency) {
		//nothing for now
	}
	
	public static boolean validate(Dependency dependency) {
		if (dependency == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Dependency> dependencyList) {
		Validator validator = Validator.getValidator();
		Iterator<Dependency> iterator = dependencyList.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			//TODO break or accumulate?
			validate(dependency);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Dependency> dependencyList) {
		Collections.sort(dependencyList, createDependencyComparator());
	}
	
	public static Collection<Dependency> sortRecords(Collection<Dependency> dependencyCollection) {
		List<Dependency> list = new ArrayList<Dependency>(dependencyCollection);
		Collections.sort(list, createDependencyComparator());
		return list;
	}
	
	public static Comparator<Dependency> createDependencyComparator() {
		return new Comparator<Dependency>() {
			public int compare(Dependency dependency1, Dependency dependency2) {
				Object key1 = getKey(dependency1);
				Object key2 = getKey(dependency2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Dependency clone(Dependency dependency) {
		if (dependency == null)
			return null;
		Dependency clone = create();
		clone.setName(ObjectUtil.clone(dependency.getName()));
		clone.setArtifactId(ObjectUtil.clone(dependency.getArtifactId()));
		clone.setGroupId(ObjectUtil.clone(dependency.getGroupId()));
		clone.setVersion(ObjectUtil.clone(dependency.getVersion()));
		clone.setDescription(ObjectUtil.clone(dependency.getDescription()));
		clone.setScope(dependency.getScope());
		clone.setType(dependency.getType());
		return clone;
	}
	
	public static List<Dependency> clone(List<Dependency> dependencyList) {
		if (dependencyList == null)
			return null;
		List<Dependency> newList = new ArrayList<Dependency>();
		Iterator<Dependency> iterator = dependencyList.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			Dependency clone = clone(dependency);
			newList.add(clone);
		}
		return newList;
	}


	public static DependencyType getDependencyType(String type) {
		return type == null ? null : DependencyType.fromValue(type);
	}
	
	public static DependencyScope getDependencyScope(String scope) {
		return scope == null ? null : DependencyScope.fromValue(scope);
	}

	public static Dependency createDependency(String groupId, String artifactId, String version, String scope, String type) {
		Dependency dependency = new Dependency();
		dependency.setGroupId(groupId);
		dependency.setArtifactId(artifactId);
		dependency.setVersion(version);
		if (type == null)
			type = "jar";
		if (scope == null && type.equals("test-jar"))
			scope = "test";
		if (scope == null)
			scope = "compile";
		dependency.setScope(getDependencyScope(scope));
		dependency.setType(getDependencyType(type));
		return dependency;
	}

	public static String getDependencyKey(Dependency dependency) {
		return dependency.getGroupId() + "." + dependency.getArtifactId() + "." + dependency.getType();
	}
	
	public static void sortDependencies(List<Dependency> list) {
		Collections.sort(list, new Comparator<Dependency>() {
			public int compare(Dependency dependency1, Dependency dependency2) {
				String key1 = getDependencyKey(dependency1);
				String key2 = getDependencyKey(dependency2);
				return key1.compareTo(key2);
			}
		});
	}

	public static List<Dependency> removeUnwantedDependencies(List<Dependency> list) {
		Map<String, Dependency> ejbModules = new HashMap<String, Dependency>();
		Iterator<Dependency> iterator = list.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (dependency.getType() == DependencyType.EJB) {
				String key = getDependencyKey(dependency);
				ejbModules.put(key, dependency);
			}
		}
		List<Dependency> newList = new ArrayList<Dependency>();
		iterator = list.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			String key = getDependencyKey(dependency);
			if (dependency.getType() == DependencyType.EJB || !ejbModules.containsKey(key))
				newList.add(dependency);
		}
		newList = removeDuplicateDependencies(newList);
		return newList;
	}

	public static List<Dependency> removeDuplicateDependencies(List<Dependency> list) {
		Map<String, Dependency> map = new HashMap<String, Dependency>();
		List<Dependency> filteredList = new ArrayList<Dependency>();
		Iterator<Dependency> iterator = list.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			String key = getDependencyKey(dependency);
			if (!map.containsKey(key)) {
				map.put(key, dependency);
				filteredList.add(dependency);
			}
		}
		return filteredList;
	}
	
	public static List<Dependency> filterDependencies(List<Dependency> dependencies, String groupId) {
		List<Dependency> filteredList = new ArrayList<Dependency>();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (dependency.getGroupId().equals(groupId)) {
				filteredList.add(dependency);
			}
		}
		return filteredList;
	}
	
}
