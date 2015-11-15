package org.aries.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class PackageUtil {

    private static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList(new String[] {
    		"abstract", "boolean", "break", "byte", "case", "catch", "char", "class",
    		"const", "continue", "default", "do", "double", "else", "extends", "final",
    		"finally", "float", "for", "goto", "if", "implements", "import", "instanceof",
    		"int", "interface", "long", "native", "new", "package", "private", "protected",
    		"public", "return", "short", "static", "strictfp", "super", "switch",
    		"synchronized", "this", "throw", "throws", "transient", "try", "void",
    		"volatile", "while", "true", "false", "null", "assert", "enum"}
    ));
    
	public static ArrayList<String> getClassNamesFromPackage(String packageName) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();     
		ArrayList<String> names = new ArrayList<String>();
		packageName = packageName.replace(".", File.separator) + File.separator;     
		URL packageURL = classLoader.getResource(packageName);      
		if (packageURL.getProtocol().equals("jar")) {         
			String jarFileName;         
			JarFile jf;         
			Enumeration<JarEntry> jarEntries;         
			String entryName;          
			// build jar file name         
			jarFileName= packageURL.getFile();         
			jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));         
			System.out.println(">"+jarFileName);         
			jf = new JarFile(jarFileName);         
			jarEntries = jf.entries();         
			while (jarEntries.hasMoreElements()) {             
				entryName = jarEntries.nextElement().getName();             
				if (entryName.startsWith(packageName) && entryName.length() > packageName.length()+5) {                 
					entryName = entryName.substring(packageName.length(),entryName.lastIndexOf('.'));                 
					names.add(entryName);             
				}         
			}     
		} else {        
			File folder = new File(packageURL.getFile());        
			File[] contenuti = folder.listFiles();     
			String entryName;       
			for (File actual: contenuti){        
				entryName = actual.getName();      
				entryName = entryName.substring(0, entryName.lastIndexOf('.'));    
				names.add(entryName); 
			}  
		}  
		return names;
	}

	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		Class<?>[] classesArray = classes.toArray(new Class[classes.size()]);
		return classesArray;
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
				Class<?> classObject = classLoader.loadClass(className);
				classes.add(classObject);
			}
		}
		return classes;
	}


	public static String getPackageNameByNameSpaceURI(String nameSpaceURI) {
		int idx = nameSpaceURI.indexOf(':');
		String scheme = "";
		if (idx >= 0) {
			scheme = nameSpaceURI.substring(0, idx);
			if ("http".equalsIgnoreCase(scheme) || "urn".equalsIgnoreCase(scheme)) {
				nameSpaceURI = nameSpaceURI.substring(idx + 1);
			}
		}

		List<String> tokens = tokenize(nameSpaceURI, "/: ");
		if (tokens.size() == 0) {
			return null;
		}

		if (tokens.size() > 1) {
			String lastToken = tokens.get(tokens.size() - 1);
			idx = lastToken.lastIndexOf('.');
			if (idx > 0) {
				lastToken = lastToken.substring(0, idx);
				tokens.set(tokens.size() - 1, lastToken);
			}
		}

		String domain = tokens.get(0);
		idx = domain.indexOf(':');
		if (idx >= 0) {
			domain = domain.substring(0, idx);
		}
		List<String> r = reverse(tokenize(domain, "urn".equals(scheme) ? ".-" : "."));
		if ("www".equalsIgnoreCase(r.get(r.size() - 1))) {
			// remove leading www
			r.remove(r.size() - 1);
		}

		// replace the domain name with tokenized items
		tokens.addAll(1, r);
		tokens.remove(0);

		// iterate through the tokens and apply xml->java name algorithm
		for (int i = 0; i < tokens.size(); i++) {

			// get the token and remove illegal chars
			String token = tokens.get(i);
			token = removeIllegalIdentifierChars(token);

			// this will check for reserved keywords
			if (containsReservedKeywords(token)) {
				token = '_' + token;
			}

			tokens.set(i, token.toLowerCase());
		}

		// concat all the pieces and return it
		return combine(tokens, '.');
	}

	private static List<String> tokenize(String str, String sep) {
		StringTokenizer tokens = new StringTokenizer(str, sep);
		List<String> r = new ArrayList<String>();

		while (tokens.hasMoreTokens()) {
			r.add(tokens.nextToken());
		}
		return r;
	}

    private static <T> List<T> reverse(List<T> a) {
        List<T> r = new ArrayList<T>();

        for (int i = a.size() - 1; i >= 0; i--) {
            r.add(a.get(i));
        }
        return r;
    }

    private static String removeIllegalIdentifierChars(String token) {
        StringBuilder newToken = new StringBuilder();
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);

            if (i == 0 && !Character.isJavaIdentifierStart(c)) {
                // prefix an '_' if the first char is illegal
                newToken.append("_" + c);
            } else if (!Character.isJavaIdentifierPart(c)) {
                // replace the char with an '_' if it is illegal
                newToken.append('_');
            } else {
                // add the legal char
                newToken.append(c);
            }
        }
        return newToken.toString();
    }

    private static String combine(List r, char sep) {
        StringBuilder buf = new StringBuilder(r.get(0).toString());

        for (int i = 1; i < r.size(); i++) {
            buf.append(sep);
            buf.append(r.get(i));
        }

        return buf.toString();
    }

    private static boolean containsReservedKeywords(String token) {
        return KEYWORDS.contains(token);
    }
    
}
