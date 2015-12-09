package nam.model.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Adapter;
import nam.model.Information;
import nam.model.Messaging;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Placeholders;
import nam.model.Project;
import nam.model.Services;
import nam.model.Source;


public class PlaceholderUtil {

	public static boolean containsPlaceholder(Placeholders placeholders, Serializable placeholder) {
		return placeholders.getProjectsAndServicesAndInformations().contains(placeholder);
	}
	
	public static void addPlaceholder(Placeholders placeholders, Serializable placeholder) {
		if (!placeholders.getProjectsAndServicesAndInformations().contains(placeholder))
			placeholders.getProjectsAndServicesAndInformations().add(placeholder);
	}

	
	public static List<Project> getProjectPlaceholders(Placeholders placeholders) {
		return getObjectList(placeholders, Project.class);
	}

	public static List<Services> getServicesPlaceholders(Placeholders placeholders) {
		return getObjectList(placeholders, Services.class);
	}

	public static List<Information> getInformationPlaceholders(Placeholders placeholders) {
		return getObjectList(placeholders, Information.class);
	}

	public static List<Persistence> getPersistencePlaceholders(Placeholders placeholders) {
		return getObjectList(placeholders, Persistence.class);
	}

	public static List<Messaging> getMessagingPlaceholders(Placeholders placeholders) {
		return getObjectList(placeholders, Messaging.class);
	}

	public static <T> List<T> getObjectList(Placeholders placeholders, Class<?> objectClass) {
		List<Serializable> objects = placeholders.getProjectsAndServicesAndInformations();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Placeholders placeholders, Class<?> objectClass) {
		List<Serializable> objects = placeholders.getProjectsAndServicesAndInformations();
		return ListUtil.getObject(objects, objectClass);
	}

	
	
	public static Source getImportedSource(Placeholders placeholders, String sourceName) {
		List<Persistence> persistencePlaceholders = getPersistencePlaceholders(placeholders);
		Iterator<Persistence> iterator = persistencePlaceholders.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			Source source = PersistenceUtil.getSourceByName(persistence, sourceName);
			if (source != null)
				return source;
		}
		return null;
	}
	
	public static Adapter getImportedAdapter(Placeholders placeholders, String adapterName) {
		List<Persistence> persistencePlaceholders = getPersistencePlaceholders(placeholders);
		Iterator<Persistence> iterator = persistencePlaceholders.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			Adapter adapter = PersistenceUtil.getAdapterByName(persistence, adapterName);
			if (adapter != null)
				return adapter;
		}
		return null;
	}
	
	public static Map<String, Namespace> getImportedNamespaces(Placeholders placeholders) {
		Map<String, Namespace> map = new HashMap<String, Namespace>();
		//Set<Namespace> set = new HashSet<Namespace>();
		List<Information> informationPlaceholders = getInformationPlaceholders(placeholders);
		Iterator<Information> iterator = informationPlaceholders.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			Collection<Namespace> namespaces = InformationUtil.getNamespaces(information);
			//set.addAll(namespaces);
			Iterator<Namespace> iterator2 = namespaces.iterator();
			while (iterator2.hasNext()) {
				Namespace namespace = iterator2.next();
				map.put(namespace.getUri(), namespace);
			}
		}
		return map;
	}
	
}
