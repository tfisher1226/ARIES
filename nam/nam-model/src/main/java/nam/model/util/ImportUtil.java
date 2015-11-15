package nam.model.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Import;
import nam.model.Model;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ImportUtil extends BaseUtil {
	
	public static Object getKey(Import _import) {
		return _import.getDir() + "/" + _import.getFile();
	}
	
	public static String getLabel(Import _import) {
		return _import.getDir() + "/" + _import.getFile();
	}
	
	public static boolean isEmpty(Import _import) {
		if (_import == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Import> importList) {
		if (importList == null  || importList.size() == 0)
			return true;
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import _import = iterator.next();
			if (!isEmpty(_import))
				return false;
		}
		return true;
	}
	
	public static String toString(Import _import) {
		if (isEmpty(_import))
			return "Import: [uninitialized] "+_import.toString();
		String text = _import.toString();
		return text;
	}
	
	public static String toString(Collection<Import> importList) {
		if (isEmpty(importList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Import> iterator = importList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Import _import = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(_import);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Import create() {
		Import _import = new Import();
		initialize(_import);
		return _import;
	}
	
	public static void initialize(Import _import) {
		//nothing for now
	}
	
	public static boolean validate(Import _import) {
		if (_import == null)
			return false;
		Validator validator = Validator.getValidator();
		ImportUtil.validate(_import.getImports());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Import> importList) {
		Validator validator = Validator.getValidator();
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import _import = iterator.next();
			//TODO break or accumulate?
			validate(_import);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Import> importList) {
		Collections.sort(importList, createImportComparator());
	}
	
	public static Collection<Import> sortRecords(Collection<Import> importCollection) {
		List<Import> list = new ArrayList<Import>(importCollection);
		Collections.sort(list, createImportComparator());
		return list;
	}
	
	public static Comparator<Import> createImportComparator() {
		return new Comparator<Import>() {
			public int compare(Import import1, Import import2) {
				Object key1 = getKey(import1);
				Object key2 = getKey(import2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Import clone(Import _import) {
		if (_import == null)
			return null;
		Import clone = create();
		clone.setDir(ObjectUtil.clone(_import.getDir()));
		clone.setFile(ObjectUtil.clone(_import.getFile()));
		clone.setType(ObjectUtil.clone(_import.getType()));
		clone.setPrefix(ObjectUtil.clone(_import.getPrefix()));
		clone.setNamespace(ObjectUtil.clone(_import.getNamespace()));
		clone.setImports(ImportUtil.clone(_import.getImports()));
		return clone;
	}
	
	public static List<Import> clone(List<Import> importList) {
		if (importList == null)
			return null;
		List<Import> newList = new ArrayList<Import>();
		Iterator<Import> iterator = importList.iterator();
		while (iterator.hasNext()) {
			Import _import = iterator.next();
			Import clone = clone(_import);
			newList.add(clone);
		}
		return newList;
	}


	public static boolean isImportExists(List<Import> imports, Import importObject) {
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import import1 = iterator.next();
			if (import1.getFile().equals(importObject.getFile())) {
				return true;
			}
		}
		return false;
	}

	public static void addImports(List<Import> imports, List<Import> newImports) {
		Iterator<Import> iterator = newImports.iterator();
		while (iterator.hasNext()) {
			Import newImport = iterator.next();
			addImport(imports, newImport);
		}
	}

	public static void addImport(List<Import> imports, Import newImport) {
		if (!isImportExists(imports, newImport)) {
			imports.add(newImport);
		}
	}
	
	public static Set<String> getImportedFileFolders(File sourceLocation) {
		Set<String> subFolders = new HashSet<String>();
		File[] files = sourceLocation.listFiles();
		if (files != null) {
			for (File file: files) {
				if (file.isDirectory()) {
					subFolders.addAll(getImportedFileFolders(file));
				} else {
					try {
						@SuppressWarnings("unchecked") List<String> lines = FileUtils.readLines(file);
						Iterator<String> iterator = lines.iterator();
						while (iterator.hasNext()) {
							String line = iterator.next();
							if (line.contains("<import ")) {
								line = line.replace(" ", "");
								int indexOf = line.indexOf("file=\"");
								int lastIndexOf = line.lastIndexOf("\"");
								String fileName = line.substring(indexOf+6, lastIndexOf);
								//System.out.println(fileName);
								if (fileName.startsWith("/")) {
									lastIndexOf = fileName.lastIndexOf("/");
									if (lastIndexOf != -1) {
										String subFolder = fileName.substring(0, lastIndexOf);
										subFolders.add(subFolder);
									}
								}
							}
						}
					} catch (IOException e) {
						//log.error("Error", e);
						e.printStackTrace();
					}
				}
			}
		}
		return subFolders;
	}

	public static String getImportedFileName(Import modelFile) {
		String fileName = NameUtil.getFirstSegment(modelFile.getFile());
		return fileName;
	}

	public static void mergeImports(List<Import> imports1, List<Import> imports2) {
		Iterator<Import> iterator2 = imports2.iterator();
		while (iterator2.hasNext()) {
			Import import2 = iterator2.next();
			if (!imports1.contains(import2)) {
				imports1.add(import2);
			}
		}
	}

	public static void mergeImport(Import _import1, Import _import2) {
		_import1.setDir(_import2.getDir());
		_import1.setFile(_import2.getFile());
		_import1.setInclude(_import2.getInclude());
		_import1.setNamespace(_import2.getNamespace());
		_import1.setPrefix(_import2.getPrefix());
		_import1.setType(_import2.getType());
		_import1.setRef(_import2.getRef());
	}
	
}
