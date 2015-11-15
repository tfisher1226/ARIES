package nam.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Configuration;
import nam.model.Configurations;
import nam.model.Dependency;
import nam.model.DependencyScope;
import nam.model.DependencyType;



public class ConfigurationUtil {

	public static List<Configuration> getConfigurations(Configurations configurations) {
		return configurations.getConfigurations();
	}
	
	public static boolean containsConfiguration(Configurations configurations, Configuration configuration) {
		List<Configuration> list = getConfigurations(configurations);
		if (list.contains(configuration))
			return true;
		Iterator<Configuration> iterator = list.iterator();
		String configurationName = configuration.getName();
		String configurationNamespace = configuration.getNamespace();
		while (iterator.hasNext()) {
			Configuration currentConfiguration = iterator.next();
			if (currentConfiguration.getName().equals(configurationName) &&
				currentConfiguration.getNamespace().equals(configurationNamespace))
					return true;
		}
		return false;
	}
	
	public static void addConfiguration(Configurations configurations, Configuration configuration) {
		if (!containsConfiguration(configurations, configuration))
			configurations.getConfigurations().add(configuration);
	}
	
	public static String getConfigurationKey(Configuration configuration) {
		return configuration.getGroupId() + "." + configuration.getArtifactId();
	}

	public static void sortConfigurations(List<Configuration> list) {
		Collections.sort(list, new Comparator<Configuration>() {
			public int compare(Configuration configuration1, Configuration configuration2) {
				String key1 = getConfigurationKey(configuration1);
				String key2 = getConfigurationKey(configuration2);
				return key1.compareTo(key2);
			}
		});
	}
	
	public static List<Dependency> getTransitiveDependencies(Configuration configuration) {
		Map<String, Dependency> map = getTransitiveDependencyMap(configuration);
		List<Dependency> list = new ArrayList<Dependency>(map.values());
		list = DependencyUtil.removeUnwantedDependencies(list);
		DependencyUtil.sortDependencies(list);
		return list;
	}
	
	public static Map<String, Dependency> getTransitiveDependencyMap(Configuration configuration) {
		Map<String, Dependency> map = new HashMap<String, Dependency>();
		map.putAll(getTransitiveDependencyMap(configuration.getConfigurations()));
		List<Dependency> dependencies = configuration.getDependencies();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			DependencyType type = dependency.getType();
			DependencyScope scope = dependency.getScope();
//			if (type == null)
//				System.out.println();
//			if (scope == null)
//				System.out.println();
			if (scope == null || scope != DependencyScope.SYSTEM) {
				//String key = dependency.getGroupId()+"."+dependency.getArtifactId();
				String key = DependencyUtil.getDependencyKey(dependency);
				map.put(key, dependency);
			}
		}
		return map;
	}

	public static Map<String, Dependency> getTransitiveDependencyMap(List<Configuration> configurations) {
		Map<String, Dependency> map = new HashMap<String, Dependency>();
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			map.putAll(getTransitiveDependencyMap(configuration));
		}
		return map;
	}

	
}
