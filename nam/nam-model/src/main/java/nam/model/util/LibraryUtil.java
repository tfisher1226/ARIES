package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.Library;


public class LibraryUtil extends BaseUtil {
	
	public static Object getKey(Library library) {
		return library.getGroupId() + "." + library.getArtifactId();
	}
	
	public static String getLabel(Library library) {
		return library.getGroupId() + "." + library.getArtifactId();
	}
	
	public static boolean isEmpty(Library library) {
		if (library == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Library> libraryList) {
		if (libraryList == null  || libraryList.size() == 0)
			return true;
		Iterator<Library> iterator = libraryList.iterator();
		while (iterator.hasNext()) {
			Library library = iterator.next();
			if (!isEmpty(library))
				return false;
		}
		return true;
	}
	
	public static String toString(Library library) {
		if (isEmpty(library))
			return "Library: [uninitialized] "+library.toString();
		String text = library.toString();
		return text;
	}
	
	public static String toString(Collection<Library> libraryList) {
		if (isEmpty(libraryList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Library> iterator = libraryList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Library library = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(library);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Library create() {
		Library library = new Library();
		initialize(library);
		return library;
	}
	
	public static void initialize(Library library) {
		//nothing for now
	}
	
	public static boolean validate(Library library) {
		if (library == null)
			return false;
		Validator validator = Validator.getValidator();
		DependencyUtil.validate(library.getDependencies());
		DependencyUtil.validate(library.getExclusions());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Library> libraryList) {
		Validator validator = Validator.getValidator();
		Iterator<Library> iterator = libraryList.iterator();
		while (iterator.hasNext()) {
			Library library = iterator.next();
			//TODO break or accumulate?
			validate(library);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Library> libraryList) {
		Collections.sort(libraryList, createLibraryComparator());
	}
	
	public static Collection<Library> sortRecords(Collection<Library> libraryCollection) {
		List<Library> list = new ArrayList<Library>(libraryCollection);
		Collections.sort(list, createLibraryComparator());
		return list;
	}
	
	public static Comparator<Library> createLibraryComparator() {
		return new Comparator<Library>() {
			public int compare(Library library1, Library library2) {
				Object key1 = getKey(library1);
				Object key2 = getKey(library2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Library clone(Library library) {
		if (library == null)
			return null;
		Library clone = create();
		clone.setExclusions(DependencyUtil.clone(library.getExclusions()));
		clone.setDependencies(DependencyUtil.clone(library.getDependencies()));
		return clone;
	}
	
}
