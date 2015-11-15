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

import nam.model.Persistence;
import nam.model.Repository;
import nam.model.Unit;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class RepositoryUtil extends BaseUtil {
	
	public static Object getKey(Repository repository) {
		return repository.getName();
	}
	
	public static String getLabel(Repository repository) {
		return repository.getName();
	}
	
	public static boolean isEmpty(Repository repository) {
		if (repository == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Repository> repositoryList) {
		if (repositoryList == null  || repositoryList.size() == 0)
			return true;
		Iterator<Repository> iterator = repositoryList.iterator();
		while (iterator.hasNext()) {
			Repository repository = iterator.next();
			if (!isEmpty(repository))
				return false;
		}
		return true;
	}
	
	public static String toString(Repository repository) {
		if (isEmpty(repository))
			return "Repository: [uninitialized] "+repository.toString();
		String text = repository.toString();
		return text;
	}
	
	public static String toString(Collection<Repository> repositoryList) {
		if (isEmpty(repositoryList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Repository> iterator = repositoryList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Repository repository = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(repository);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Repository create() {
		Repository repository = new Repository();
		initialize(repository);
		return repository;
	}
	
	public static void initialize(Repository repository) {
		//nothing for now
	}
	
	public static boolean validate(Repository repository) {
		if (repository == null)
			return false;
		Validator validator = Validator.getValidator();
		//SerializableUtil.validate(repository.getMembers());
		List<Serializable> members = repository.getMembers();
		Iterator<Serializable> iterator = members.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Unit)
				UnitUtil.validate((Unit) object);
		}
		PropertyUtil.validate(repository.getProperties());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Repository> repositoryList) {
		Validator validator = Validator.getValidator();
		Iterator<Repository> iterator = repositoryList.iterator();
		while (iterator.hasNext()) {
			Repository repository = iterator.next();
			//TODO break or accumulate?
			validate(repository);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Repository> repositoryList) {
		Collections.sort(repositoryList, createRepositoryComparator());
	}
	
	public static Collection<Repository> sortRecords(Collection<Repository> repositoryCollection) {
		List<Repository> list = new ArrayList<Repository>(repositoryCollection);
		Collections.sort(list, createRepositoryComparator());
		return list;
	}
	
	public static Comparator<Repository> createRepositoryComparator() {
		return new Comparator<Repository>() {
			public int compare(Repository repository1, Repository repository2) {
				Object key1 = getKey(repository1);
				Object key2 = getKey(repository2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Repository clone(Repository repository) {
		if (repository == null)
			return null;
		Repository clone = create();
		clone.setName(ObjectUtil.clone(repository.getName()));
		clone.setLabel(ObjectUtil.clone(repository.getLabel()));
		clone.setDescription(ObjectUtil.clone(repository.getDescription()));
		clone.setProperties(PropertyUtil.clone(repository.getProperties()));
		List<Serializable> members = repository.getMembers();
		Iterator<Serializable> iterator = members.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Unit)
				clone.addToMembers(UnitUtil.clone((Unit) object));
		}
		clone.setMembers(members);
		return clone;
	}


//	public static Map<String, Repository> createRepositoryMap(Data repositories) {
//		if (repositories == null)
//			return new HashMap<String, Repository>();
//		return createRepositoryMap(repositories.getRepositories());
//	}

	public static Map<String, Repository> createRepositoryMap(List<Repository> repositories) {
		Map<String, Repository> map = new HashMap<String, Repository>();
		Iterator<Repository> iterator = repositories.iterator();
		while (iterator.hasNext()) {
			Repository repository = (Repository) iterator.next();
			String name = repository.getName();
			if (name != null)
				map.put(name, repository);
		}
		return map;
	}

	
	/*
	 * Unit related
	 */
	
	public static boolean isUnitExists(Repository repository, Unit unit) {
		List<Unit> units = getUnits(repository);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit1 = iterator.next();
			if (UnitUtil.equals(unit1, unit)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Unit> getUnits(Repository repository) {
		List<Unit> list = new ArrayList<Unit>();
		List<Serializable> children = repository.getMembers();
		Iterator<Serializable> iterator = children.iterator();
		while (iterator.hasNext()) {
			Serializable child = iterator.next();
			if (child instanceof Unit)
				list.add((Unit) child);
		}
		return list;
	}
	
	public static Unit getUnitByName(Repository repository, String name) {
		List<Unit> units = getUnits(repository);
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
	
	public static void addUnit(Repository repository, Unit unit) {
		List<Unit> units = getUnits(repository);
		if (!containsUnit(units, unit))
			repository.getMembers().add(unit);
	}
	
//	public static void addUnits(Project project, List<Unit> units) {
//		addUnits(ProjectUtil.getRepository(project), units);
//	}

	public static void addUnits(Repository repository, List<Unit> units) {
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			addUnit(repository, unit);
		}
	}
	
	public static boolean removeUnit(Repository repository, Unit unit) {
		List<Unit> units = getUnits(repository);
		if (containsUnit(units, unit)) {
			return repository.getMembers().remove(unit);
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
	
}
