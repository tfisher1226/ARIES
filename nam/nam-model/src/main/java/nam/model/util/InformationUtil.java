package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Import;
import nam.model.Information;
import nam.model.Namespace;
import nam.model.Type;

import org.apache.commons.collections.CollectionUtils;
import org.aries.Assert;


public class InformationUtil {

	public static Information newInformation() {
		Information information = new Information();
		return information;
	}
	
	public static boolean containsImport(Information information, Import model) {
		return ProjectUtil.containsImport(getImports(information), model);
	}
	
	public static void addImport(Information information, Import model) {
		if (!containsImport(information, model))
			information.getImports().add(model);
	}

//	public static void addImports(Project project, List<Import> imports) {
//		addImports(ProjectUtil.getInformation(project), imports);
//	}
	
	public static void addImports(Information information, List<Import> imports) {
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import model = iterator.next();
			addImport(information, model);
		}
	}

	public static List<Import> getImports(Information information) {
		//return getObjectList(information, Import.class);
		return information.getImports();
	}

	/**
	 * Merges <code>Information</code> blocks.
	 * Merges into block1 all Imports from block2.
	 */
	public static void mergeImports(Information block1, Information block2) {
		List<Import> list = new ArrayList<Import>(); 
		Iterator<Import> iterator = block2.getImports().iterator();
		while (iterator.hasNext()) {
			Import block2Import = iterator.next();
			if (!ImportUtil.isImportExists(block1.getImports(), block2Import)) {
				list.add(block2Import);
			} else {
				//TODO Merge the two importObjects here
			}
		}
		addImports(block1, list);
	}

	/**
	 * Merges <code>Information</code> blocks.
	 * Merges into block1 all Namespaces from block2.
	 */
	public static void mergeNamespaces(Information block1, Information block2) {
		List<Namespace> list = new ArrayList<Namespace>(); 
		Iterator<Namespace> iterator = block2.getNamespaces().iterator();
		while (iterator.hasNext()) {
			Namespace block2Namespace = iterator.next();
			if (!NamespaceUtil.isNamespaceExists(block1.getNamespaces(), block2Namespace)) {
				list.add(block2Namespace);
			} else {
				String uri = block2Namespace.getUri();
				Namespace existingNamespace = NamespaceUtil.getNamespace(block1.getNamespaces(), uri);
				NamespaceUtil.mergeNamespaces(existingNamespace, block2Namespace);
			}
		}
		addNamespaces(block1, list);
	}

	public static void addNamespace(Information information, Namespace namespace) {
		if (!containsNamespace(information, namespace)) {
			//System.out.println(">>> Adding namespace to block: "+information.getName()+": "+namespace.getUri());
			NamespaceUtil.addNamespace(information.getNamespaces(), namespace);
		}
	}
	
//	public static void addNamespaces(Project project, List<Namespace> namespaces) {
//		addNamespaces(ProjectUtil.getInformation(project), namespaces);
//	}

	public static void addNamespaces(Information information, List<Namespace> namespaces) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			addNamespace(information, namespace);
		}
	}
	
	public static List<Namespace> getNamespaces(Information information) {
		//return getObjectList(information, Namespace.class);
		return information.getNamespaces();
	}

	public static Collection<Namespace> getAllNamespaces(Collection<Information> informationBlocks) {
		Set<Namespace> set = new LinkedHashSet<Namespace>();
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			Collection<Namespace> namespaces = getAllNamespaces(information);
			set.addAll(namespaces);
		}
		return set;
	}
	
	public static Collection<Namespace> getAllNamespaces(Information information) {
		Set<Namespace> set = new LinkedHashSet<Namespace>();
		List<Import> imports = information.getImports();
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import importObject = iterator.next();
			Object object = importObject.getObject();
			if (object == null)
				continue;
			Assert.isInstanceOf(Information.class, object, "Unexpected object type");
			Information importedInformation = (Information) object;
			List<Namespace> namespaces = importedInformation.getNamespaces();
			set.addAll(namespaces);
		}
		List<Namespace> namespaces = InformationUtil.getNamespaces(information);
		set.addAll(namespaces);
		set.addAll(getNamespacesToDependUpon(namespaces));
		return set;
	}
	
	public static Collection<Namespace> getNamespacesToDependUpon(Collection<Namespace> namespaces) {
		Set<Namespace> set = new LinkedHashSet<Namespace>();
		Iterator<Namespace> iterator2 = namespaces.iterator();
		while (iterator2.hasNext()) {
			Namespace namespace = iterator2.next();
			List<Namespace> importedNamespaces = NamespaceUtil.getImports(namespace);
			set.addAll(importedNamespaces);
			Collection unprocessedNamespaces = CollectionUtils.subtract(importedNamespaces, namespaces);
			set.addAll(getNamespacesToDependUpon(unprocessedNamespaces));
		}
		return set;
	}
	
	public static boolean containsNamespace(Information information, Namespace namespace) {
		List<Namespace> namespaces = information.getNamespaces();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace currentNamespace = iterator.next();
			if (NamespaceUtil.equals(currentNamespace, namespace)) {
				return true;
			}
		}
		return false;
	}

	public static boolean removeNamespace(Information information, Namespace namespace) {
		return information.getNamespaces().remove(namespace);
	}

	
	public static List<Type> getTypes(Information information) {
		List<Type> types = new ArrayList<Type>();
		if (information != null) {
			List<Namespace> namespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				List<Type> namespaceTypes = NamespaceUtil.getTypes(namespace);
				types.addAll(namespaceTypes);
			}
		}
		return types;
	}

	public static Map<String, Type> getTypeMap(Information information) {
		List<Namespace> namespaces = getNamespaces(information);
		return NamespaceUtil.getTypeMap(namespaces);
	}
	
	public static List<Element> getElements(Information information) {
		List<Element> elements = new ArrayList<Element>();
		if (information != null) {
			List<Namespace> namespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				//TODO if (!namespace.getName().equals(information.getName()))
				//TODO 	continue;
				if (NamespaceUtil.isImported(namespace) && !NamespaceUtil.isIncluded(namespace))
					continue;
				List<Element> namespaceElements = NamespaceUtil.getElements(namespace);
				elements.addAll(namespaceElements);
			}
		}
		return elements;
	}

	public static List<Element> getElements(Collection<Information> informationBlocks) {
		List<Element> list = new ArrayList<Element>();
		Iterator<Information> iterator = informationBlocks.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			List<Element> elements = getElements(information);
			list.addAll(elements);
		}
		return list;
	}
	
	public static List<Enumeration> getEnumerations(Information information) {
		List<Enumeration> enumerations = new ArrayList<Enumeration>();
		if (information != null) {
			List<Namespace> namespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				List<Enumeration> namespaceEnumerations = NamespaceUtil.getEnumerations(namespace);
				enumerations.addAll(namespaceEnumerations);
			}
		}
		return enumerations;
	}

	public static Serializable clone(Information object) {
		// TODO Auto-generated method stub
		return null;
	}

//	public static <T> T getObjectByName(Information information, Class<?> objectClass, String name) {
//		List<Object> objectList = getObjectList(information, objectClass);
//		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
//	}
//	
//	public static <T> List<T> getObjectList(Information information, Class<?> objectClass) {
//		List<Serializable> objects = information.getNamespaces();
//		return ListUtil.getObjectList(objects, objectClass);
//	}
//	
//	public static <T> T getObject(Information information, Class<?> objectClass) {
//		List<Serializable> objects = information.getNamespaces();
//		return ListUtil.getObject(objects, objectClass);
//	}
	
}
