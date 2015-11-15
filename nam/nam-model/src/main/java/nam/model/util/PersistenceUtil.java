package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Adapter;
import nam.model.Cache;
import nam.model.Element;
import nam.model.Import;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Repository;
import nam.model.Source;
import nam.model.Sources;
import nam.model.Type;
import nam.model.Unit;
import nam.model.Units;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class PersistenceUtil extends BaseUtil {
	
	public static Object getKey(Persistence persistence) {
		return persistence.getDomain() + "." + persistence.getName();
	}
	
	public static String getLabel(Persistence persistence) {
		return persistence.getDomain() + "." + persistence.getName();
	}
	
	public static boolean isEmpty(Persistence persistence) {
		if (persistence == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Persistence> persistenceList) {
		if (persistenceList == null  || persistenceList.size() == 0)
			return true;
		Iterator<Persistence> iterator = persistenceList.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			if (!isEmpty(persistence))
				return false;
		}
		return true;
	}
	
	public static String toString(Persistence persistence) {
		if (isEmpty(persistence))
			return "Persistence: [uninitialized] "+persistence.toString();
		String text = persistence.toString();
		return text;
	}
	
	public static String toString(Collection<Persistence> persistenceList) {
		if (isEmpty(persistenceList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Persistence> iterator = persistenceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Persistence persistence = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(persistence);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Persistence create() {
		Persistence persistence = new Persistence();
		initialize(persistence);
		return persistence;
	}
	
	public static void initialize(Persistence persistence) {
		if (persistence.getExposed() == null)
			persistence.setExposed(false);
		if (persistence.getImported() == null)
			persistence.setImported(false);
		if (persistence.getIncluded() == null)
			persistence.setIncluded(false);
	}
	
	public static boolean validate(Persistence persistence) {
		if (persistence == null)
			return false;
		Validator validator = Validator.getValidator();
		ImportUtil.validate(persistence.getImports());
		//TODO SerializableUtil.validate(persistence.getMembers());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Persistence> persistenceList) {
		Validator validator = Validator.getValidator();
		Iterator<Persistence> iterator = persistenceList.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			//TODO break or accumulate?
			validate(persistence);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Persistence> persistenceList) {
		Collections.sort(persistenceList, createPersistenceComparator());
	}
	
	public static Collection<Persistence> sortRecords(Collection<Persistence> persistenceCollection) {
		List<Persistence> list = new ArrayList<Persistence>(persistenceCollection);
		Collections.sort(list, createPersistenceComparator());
		return list;
	}
	
	public static Comparator<Persistence> createPersistenceComparator() {
		return new Comparator<Persistence>() {
			public int compare(Persistence persistence1, Persistence persistence2) {
				Object key1 = getKey(persistence1);
				Object key2 = getKey(persistence2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Persistence clone(Persistence persistence) {
		if (persistence == null)
			return null;
		Persistence clone = create();
		clone.setImports(ImportUtil.clone(persistence.getImports()));
		clone.setDomain(ObjectUtil.clone(persistence.getDomain()));
		clone.setName(ObjectUtil.clone(persistence.getName()));
		clone.setLabel(ObjectUtil.clone(persistence.getLabel()));
		clone.setVersion(ObjectUtil.clone(persistence.getVersion()));
		clone.setDescription(ObjectUtil.clone(persistence.getDescription()));
		clone.setNamespace(ObjectUtil.clone(persistence.getNamespace()));
		//TODO clone.setMembers(SerializableUtil.clone(persistence.getMembers()));
		clone.setImported(ObjectUtil.clone(persistence.getImported()));
		clone.setIncluded(ObjectUtil.clone(persistence.getIncluded()));
		clone.setExposed(ObjectUtil.clone(persistence.getExposed()));
		clone.setCreationDate(ObjectUtil.clone(persistence.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(persistence.getLastUpdate()));
		return clone;
	}


	public static String getPersistenceUnitName(Unit unit) {
		return UnitUtil.getUnitName(unit);
	}

	public static String getDatabaseName(Persistence persistenceBlock) {
		String name = getBlockName(persistenceBlock) + "_db";
		return name;
	}

	public static String getDataSourceName(Persistence persistenceBlock) {
		//String name = getBlockName(persistenceBlock) + "";
		String name = getBlockName(persistenceBlock);
		if (!name.endsWith("_ds"))
			name += "_ds";
		return name;
	}
	
	public static String getDataSourceFileName(Persistence persistenceBlock) {
		String dataSourceName = getDataSourceName(persistenceBlock);
		String fileNameBase = dataSourceName.replace("_", "-");
		String fileName = fileNameBase + ".xml";
		return fileName;
	}

	public static String getUserName(Persistence persistenceBlock) {
		String name = NameUtil.getLocalPart(persistenceBlock.getNamespace());
		return name;
	}
	
	public static String getBaseName(Persistence persistenceBlock) {
		String initialName = persistenceBlock.getName();
		String baseName = initialName;
		int indexOfDot = baseName.indexOf(".");
		int indexOfDash = baseName.indexOf("-");
		int indexOfUnderscore = baseName.indexOf("_");
		if (indexOfDot != -1) {
			baseName = baseName.substring(0, indexOfDot);
		} else if (indexOfDash != -1) {
			baseName = baseName.substring(0, indexOfDash);
		} else if (indexOfUnderscore != -1) {
			baseName = baseName.substring(0, indexOfUnderscore);
		}
		return baseName;
	}

	public static String getBlockName(Persistence persistenceBlock) {
		String uri = persistenceBlock.getNamespace();
		String domain = persistenceBlock.getDomain();
		String localPart = NameUtil.getLocalPartFromNamespace(uri);
		String name = NameUtil.uncapName(localPart);
		if (!name.equalsIgnoreCase(domain))
			name = domain + "_" + name;
		//String initialName = persistenceBlock.getName();
		String blockName = name.
				replace("-", "_").
				replace(".", "_");
		//blockName = NameUtil.toCamelCase(blockName);
		blockName = NameUtil.uncapName(blockName);
		return blockName;
	}

	
	public static Persistence newPersistence() {
		Persistence persistence = new Persistence();
		//persistence.getImportsAndResourcesAndProviders().add(new Channels());
		//persistence.getImportsAndResourcesAndProviders().add(new Transports());
		return persistence;
	}
	
	public static boolean containsImport(Persistence persistence, Import model) {
		return ProjectUtil.containsImport(getImports(persistence), model);
	}

	public static void addImport(Persistence persistence, Import model) {
		if (!containsImport(persistence, model))
			persistence.getImports().add(model);
	}

//	public static void addImports(Project project, List<Import> imports) {
//		addImports(ProjectUtil.getPersistence(project), imports);
//	}
	
	public static void addImports(Persistence persistence, List<Import> imports) {
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import model = iterator.next();
			addImport(persistence, model);
		}
	}

	public static List<Import> getImports(Persistence persistence) {
		return persistence.getImports();
	}
	
	/**
	 * Merges <code>Persistence</code> blocks.
	 * Merges into block1 all Imports from block2.
	 */
	public static void mergeImports(Persistence block1, Persistence block2) {
		Iterator<Import> iterator = block2.getImports().iterator();
		while (iterator.hasNext()) {
			Import block2Import = iterator.next();
			if (!ImportUtil.isImportExists(block1.getImports(), block2Import)) {
				ImportUtil.addImport(block1.getImports(), block2Import);
			}
		}
	}

	public static void addNamespace(Persistence persistence, Namespace namespace) {
		if (!persistence.getMembers().contains(namespace))
			persistence.getMembers().add(namespace);
	}
	
//	public static void addNamespaces(Project project, List<Namespace> namespaces) {
//		addNamespaces(ProjectUtil.getPersistence(project), namespaces);
//	}

	public static void addNamespaces(Persistence persistence, Collection<Namespace> namespaces) {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			addNamespace(persistence, namespace);
		}
	}

	public static List<Namespace> getNamespaces(Persistence persistence) {
		return getObjectList(persistence, Namespace.class);
	}

	
	public static List<Provider> getProviders(Project project) {
		List<Provider> list = new ArrayList<Provider>();
		List<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(project);
		Iterator<Persistence> iterator = persistenceBlocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			List<Provider> providers = getProviders(persistence);
			//TODO prevent duplicates
			list.addAll(providers);
		}
		return list;
	}

	public static List<Provider> getProviders(Persistence persistence) {
		List<Provider> list = new ArrayList<Provider>();
		List<Serializable> children = persistence.getMembers();
		Iterator<Serializable> iterator = children.iterator();
		while (iterator.hasNext()) {
			Serializable child = iterator.next();
			if (child instanceof Provider)
				list.add((Provider) child);
		}
		return list;
	}
	
	public static void addProvider(Persistence persistence, Provider provider) {
		if (!persistence.getMembers().contains(provider))
			persistence.getMembers().add(provider);
	}
	
	public static boolean removeProvider(Persistence persistence, Provider provider) {
		if (!persistence.getMembers().contains(provider))
			return persistence.getMembers().remove(provider);
		return false;
	}
	
	
	/*
	 * Adapter related
	 */
	
	public static boolean isAdapterExists(Persistence persistence, Adapter adapter) {
		List<Adapter> adapters = getAdapters(persistence);
		Iterator<Adapter> iterator = adapters.iterator();
		while (iterator.hasNext()) {
			Adapter adapter1 = iterator.next();
			if (AdapterUtil.equals(adapter1, adapter)) {
				return true;
			}
		}
		return false;
	}
	
//	public static Adapter getAdapter(Project project, String name) {
//		return getAdapter(project.getPersistence(), name);
//	}
	
	public static Adapter getAdapter(Persistence persistence, String name) {
		return getObjectByName(persistence, Adapter.class, name);
	}
	
//	public static List<Adapter> getAdapters(Project project) {
//		return getAdapters(project.getPersistence());
//	}

	public static List<Adapter> getAdapters(Persistence persistence) {
		return getObjectList(persistence, Adapter.class);
	}
	
	public static Adapter getAdapterByName(Persistence persistence, String name) {
		//Map<String, Source> map = new HashMap<String, Source>();
		List<Adapter> adapters = getAdapters(persistence);
		Iterator<Adapter> iterator = adapters.iterator();
		while (iterator.hasNext()) {
			Adapter adapter = iterator.next();
			if (adapter.getName().equals(name))
				return adapter;
		}
		return null;
	}
	
	public static void addAdapter(Persistence persistence, Adapter adapter) {
		if (!isAdapterExists(persistence, adapter))
			persistence.getMembers().add(adapter);
	}
	
	/**
	 * Merges the Adapters for two <code>Persistence</code> blocks.
	 * Merges into block1 all Adapters from block2.
	 */
	public static void mergeAdapters(Persistence block1, Persistence block2) {
		List<Adapter> block2Adapters = PersistenceUtil.getAdapters(block2);
		Iterator<Adapter> iterator = block2Adapters.iterator();
		while (iterator.hasNext()) {
			Adapter block2Adapter = iterator.next();
			if (!isAdapterExists(block1, block2Adapter)) {
				addAdapter(block1, block2Adapter);
			}
		}
	}
	
	
	/*
	 * Provider related
	 */
	
//	public static List<Provider> getProviders(Persistence persistence) {
////		if (persistence == null)
////			return new ArrayList<Provider>();
//		Providers providers = persistence.getProviders();
//		if (providers == null)
//			providers = new Providers();
//		return providers.getProviders();
//	}

	public static Map<String, Provider> getProvidersAsMap(Persistence persistence) {
		return ProviderUtil.createProviderMap(getProviders(persistence));
	}
	
//	public static List<Adapter> getAdapters(Persistence persistence) {
////		if (persistence == null)
////			return new ArrayList<Adapter>();
//		Adapters adapters = persistence.getAdapters();
//		if (adapters == null)
//			adapters = new Adapters();
//		return adapters.getAdapters();
//	}
	
//	public static List<Source> getSources(Persistence persistence) {
////		if (persistence == null)
////			return new ArrayList<Source>();
//		Sources dataSources = persistence.getSources();
//		if (dataSources == null)
//			dataSources = new Sources();
//		return dataSources.getSources();
//	}

//	public static List<Unit> getUnits(Persistence persistence) {
////		if (persistence == null)
////			return new ArrayList<Unit>();
//		if (persistence.getUnits() == null)
//			persistence.setUnits(new Units());
//		Units persistenceUnits = persistence.getUnits();
//		return persistenceUnits.getUnits();
//	}
	
	public static Map<String, Unit> createUnitMap(Units persistenceUnits) {
//		if (persistenceUnits == null)
//			return new HashMap<String, Unit>();
		return createUnitMap(persistenceUnits.getUnits());
	}
	
	public static Map<String, Unit> createUnitMap(List<Unit> persistenceUnits) {
		Map<String, Unit> map = new HashMap<String, Unit>();
		Iterator<Unit> iterator = persistenceUnits.iterator();
		while (iterator.hasNext()) {
			Unit persistenceUnit = iterator.next();
			String name = persistenceUnit.getName();
			if (name != null)
				map.put(name, persistenceUnit);
		}
		return map;
	}
	
	public static List<Cache> getCaches(Persistence persistence) {
		List<Cache> list = new ArrayList<Cache>();
		if (persistence == null)
			return list;
		List<Serializable> children = persistence.getMembers();
		Iterator<Serializable> iterator = children.iterator();
		while (iterator.hasNext()) {
			Serializable child = iterator.next();
			if (child instanceof Cache)
				list.add((Cache) child);
		}
		return list;
	}
	
	public static void addRepositories(Persistence persistence, List<Repository> repositories) {
		Iterator<Repository> iterator = repositories.iterator();
		while (iterator.hasNext()) {
			Repository repository = iterator.next();
			addRepository(persistence, repository);
		}
	}
	
	public static void addRepository(Persistence persistence, Repository repository) {
		if (!persistence.getMembers().contains(repository))
			persistence.getMembers().add(repository);
	}
	
	public static List<Repository> getRepositories(Persistence persistence) {
		List<Repository> list = new ArrayList<Repository>();
		List<Serializable> children = persistence.getMembers();
		Iterator<Serializable> iterator = children.iterator();
		while (iterator.hasNext()) {
			Serializable child = iterator.next();
			if (child instanceof Repository)
				list.add((Repository) child);
		}
		return list;
	}

	
	/*
	 * Unit related
	 */
	
	public static boolean isUnitExists(Persistence persistence, Unit unit) {
		List<Unit> units = getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit1 = iterator.next();
			if (UnitUtil.equals(unit1, unit)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Unit> getUnits(Persistence persistence) {
		List<Unit> list = new ArrayList<Unit>();
		List<Serializable> children = persistence.getMembers();
		Iterator<Serializable> iterator = children.iterator();
		while (iterator.hasNext()) {
			Serializable child = iterator.next();
			if (child instanceof Unit)
				list.add((Unit) child);
		}
		return list;
	}
	
	public static Unit getUnitByName(Persistence persistence, String name) {
		List<Unit> units = getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getRef() != null)
				continue;
			if (unit.getName().equals(name))
				return unit;
		}
		return null;
	}
	
	public static void addUnit(Persistence persistence, Unit unit) {
		List<Unit> units = getUnits(persistence);
		if (!containsUnit(units, unit))
			persistence.getMembers().add(unit);
	}
	
//	public static void addUnits(Project project, List<Unit> units) {
//		addUnits(ProjectUtil.getPersistence(project), units);
//	}

	public static void addUnits(Persistence persistence, List<Unit> units) {
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			addUnit(persistence, unit);
		}
	}
	
	public static boolean removeUnit(Persistence persistence, Unit unit) {
		List<Unit> units = getUnits(persistence);
		if (containsUnit(units, unit)) {
			return persistence.getMembers().remove(unit);
		}
		return false;
	}
	
	public static boolean containsUnit(List<Unit> units, Unit unit) {
		if (units.contains(unit))
			return true;
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit importedUnit = iterator.next();
			if (importedUnit.getName().equals(unit.getName()) && 
				importedUnit.getNamespace().equals(unit.getNamespace()))
					return true;
		}
		return false;
	}
	
	/**
	 * Merges the units for two <code>Persistence</code> blocks.
	 * Merges into block1 all Units from block2.
	 */
	public static void mergeUnits(Persistence block1, Persistence block2) {
		List<Unit> block2Units = PersistenceUtil.getUnits(block2);
		Iterator<Unit> iterator = block2Units.iterator();
		while (iterator.hasNext()) {
			Unit block2Unit = iterator.next();
			if (!isUnitExists(block1, block2Unit)) {
				addUnit(block1, block2Unit);
			}
		}
	}
	

	/*
	 * Source related
	 */

	public static boolean isSourceExists(Persistence persistence, Source source) {
		List<Source> sources = getSources(persistence);
		Iterator<Source> iterator = sources.iterator();
		while (iterator.hasNext()) {
			Source source1 = iterator.next();
			if (SourceUtil.equals(source1, source)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Source> getSources(Persistence persistence) {
		List<Source> list = new ArrayList<Source>();
		List<Serializable> children = persistence.getMembers();
		Iterator<Serializable> iterator = children.iterator();
		while (iterator.hasNext()) {
			Serializable child = iterator.next();
			if (child instanceof Source)
				list.add((Source) child);
		}
		return list;
	}
	
	
	public static Source getSourceByName(Persistence persistence, String name) {
		//Map<String, Source> map = new HashMap<String, Source>();
		List<Source> sources = getSources(persistence);
		Iterator<Source> iterator = sources.iterator();
		while (iterator.hasNext()) {
			Source source = iterator.next();
			if (source.getRef() != null)
				continue;
			if (source.getName().equals(name))
				return source;
		}
		return null;
	}
	
	public static Map<String, Source> createSourceMap(Sources persistenceUnits) {
//		if (persistenceUnits == null)
//			return new HashMap<String, Source>();
		return createSourceMap(persistenceUnits.getSources());
	}

	public static Map<String, Source> createSourceMap(List<Source> persistenceUnits) {
		Map<String, Source> map = new HashMap<String, Source>();
		Iterator<Source> iterator = persistenceUnits.iterator();
		while (iterator.hasNext()) {
			Source persistenceUnit = iterator.next();
			String name = persistenceUnit.getName();
			if (name != null)
				map.put(name, persistenceUnit);
		}
		return map;
	}
	
	public static void addSource(Persistence persistence, Source source) {
		if (!isSourceExists(persistence, source))
			persistence.getMembers().add(source);
	}
	
	/**
	 * Merges the Sources for two <code>Persistence</code> blocks.
	 * Merges into block1 all Sources from block2.
	 */
	public static void mergeSources(Persistence block1, Persistence block2) {
		List<Source> block2Sources = PersistenceUtil.getSources(block2);
		Iterator<Source> iterator = block2Sources.iterator();
		while (iterator.hasNext()) {
			Source block2Source = iterator.next();
			if (!isSourceExists(block1, block2Source)) {
				addSource(block1, block2Source);
			}
		}
	}
	

	/*
	 * Element related
	 */
	
	public static Map<String, Type> getTypeMap(Persistence persistence) {
		List<Namespace> namespaces = getNamespaces(persistence);
		return NamespaceUtil.getTypeMap(namespaces);
	}
	
	public static List<Element> getElements(Persistence persistence) {
		List<Element> elements = new ArrayList<Element>();
		List<Unit> units = PersistenceUtil.getUnits(persistence);
		
		if (units.size() == 0) {
			List<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = (Namespace) iterator.next();
				elements.addAll(NamespaceUtil.getElements(namespace, false));
			}
			
		} else {
			Iterator<Unit> iterator = units.iterator();
			while (iterator.hasNext()) {
				Unit unit = iterator.next();
				if (unit.getRef() != null)
					continue;
				elements.addAll(UnitUtil.getElements(unit));
			}
		}
		return elements;
	}
	
	public static List<Element> getFunctionalElements(Persistence persistence) {
		List<Element> elements = new ArrayList<Element>();
		List<Unit> units = PersistenceUtil.getUnits(persistence);
		
		if (units.size() == 0) {
			List<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = (Namespace) iterator.next();
				elements.addAll(NamespaceUtil.getElements(namespace, false));
			}
			
		} else {
			Iterator<Unit> iterator = units.iterator();
			while (iterator.hasNext()) {
				Unit unit = iterator.next();
				if (unit.getRef() != null)
					continue;
				elements.addAll(UnitUtil.getFunctionalElementsBasedOnType(unit));
			}
		}
		return elements;
	}
	
//	public static <T> T getObjectByName(Project project, Class<?> objectClass, String name) {
//		return getObjectByName(project.getPersistence(), objectClass, name);
//	}
	
	public static <T> T getObjectByName(Persistence persistence, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(persistence, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
//	public static <T> List<T> getObjectList(Project project, Class<?> objectClass) {
//		return getObjectList(project.getPersistence(), objectClass);
//	}
	
	public static <T> List<T> getObjectList(Persistence persistence, Class<?> objectClass) {
		List<Serializable> objects = persistence.getMembers();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
//	public static <T> T getObject(Project project, Class<?> objectClass) {
//		return getObject(project.getPersistence(), objectClass);
//	}
	
	public static <T> T getObject(Persistence persistence, Class<?> objectClass) {
		List<Serializable> objects = persistence.getMembers();
		return ListUtil.getObject(objects, objectClass);
	}

}
