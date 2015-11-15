package org.aries.util.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.ant.util.LineTokenizer;
import org.aries.Assert;
import org.aries.util.FileUtil;


public class PropertyInitializer {

	private static Log log = LogFactory.getLog(PropertyInitializer.class);
	
//	private static PropertyInitializer instance = new PropertyInitializer();
//	
//	public static PropertyInitializer getInstance() {
//		return instance;
//	}

	private String runtimeLocation;

	private String workingLocation;

	private String propertyLocation;
	
	private String subFolder;
	
	
	public PropertyInitializer() {
		//nothing for now
	}
	
	public String getRuntimeLocation() {
		return runtimeLocation;
	}

	public void setRuntimeLocation(String runtimeLocation) {
		this.runtimeLocation = FileUtil.normalizePath(runtimeLocation);
	}

	public String getWorkingLocation() {
		return workingLocation;
	}

	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = FileUtil.normalizePath(workingLocation);
	}
	
	public String getPropertyLocation() {
		return propertyLocation;
	}

	public void setPropertyLocation(String propertyLocation) {
		this.propertyLocation = FileUtil.normalizePath(propertyLocation);
	}
	
	public String getSubFolder() {
		return subFolder;
	}

	public void setSubFolder(String subFolder) {
		if (subFolder != null)
			this.subFolder = FileUtil.normalizePath(subFolder);
	}

	public void initialize() {
		String runtimeDir = getRuntimeLocation();
		//String propertyDir = getPropertyLocation();
		String workingDir = getWorkingLocation();
		//initialize(runtimeDir, propertyDir);
		initialize(runtimeDir, workingDir);
	}

	public void initialize(ServletContext servletContext) {
		String runtimeDir = getRuntimeLocation();
		String workingDir = servletContext.getRealPath("/");
		initialize(runtimeDir, workingDir);
	}
	
	protected void initialize(String runtimeDir, String workingDir) {
		try {
			if (subFolder != null) {
				runtimeDir += subFolder;
				workingDir += subFolder;
			}
			//FileUtil.removeDirectory(runtimeDir);
			FileUtil.assureDirectoryExists(runtimeDir);
			executeTokenSubstitution(runtimeDir, workingDir, workingDir);
		} catch (Exception e) {
			log.error("Error", e);
		}
	}
	
//	//TODO make this reliable...
//	//TODO we cannot assume the root is always first
//	protected String getClassPathRoot() {
//		List<String> tokens = new ArrayList<String>();
//		String classPath = System.getProperty("java.class.path");
//		StringTokenizer stringTokenizer = new StringTokenizer(classPath, ";");
//		while (stringTokenizer.hasMoreTokens()) {
//			String token = stringTokenizer.nextToken();
//			if (!token.endsWith(".jar") && !token.endsWith(".zip"))
//				tokens.add(token);
//		}
//		if (tokens.size() > 0)
//			return tokens.get(0);
//		return null;
//	}

	public void executeTokenSubstitution() throws Exception {
		String runtimeDir = getRuntimeLocation();
		String workingDir = getWorkingLocation();
		if (subFolder != null) {
			runtimeDir += subFolder;
			workingDir += subFolder;
		}
		executeTokenSubstitution(runtimeDir, workingDir, workingDir);
	}

	protected void executeTokenSubstitution(String runtimeLocation, String workingLocation, String sourcePath) throws Exception {
		Assert.notNull(runtimeLocation, "Runtime location must be specified");
		Assert.notNull(workingLocation, "Working location must be specified");
		Assert.notNull(sourcePath, "Source file location must be specified");
		log.info("*** Processing token substitution on: "+workingLocation);
		//log.debug("*** Using runtime location: "+runtimeLocation);
		//log.debug("*** Using working location: "+workingLocation);
		//log.debug("*** Using model source: "+sourcePath);
		File sourceFile = new File(sourcePath);
		executeTokenSubstitution(runtimeLocation, workingLocation, sourceFile);
        //ClassLoader loader = Thread.currentThread().getContextClassLoader();
        //InputStream inputStream = loader.getResourceAsStream(sourceFile);
		//executeTokenSubstitution(runtimeLocation, workingLocation, inputStream);
	}
	
	protected void executeTokenSubstitution(String runtimeLocation, String workingLocation, File sourceFile) throws Exception {
		FileUtil.assureDirectoryExists(runtimeLocation);
		if (sourceFile.getPath().equals(runtimeLocation)) {
			return;
		}
		
		if (sourceFile.exists()) {
			String name = sourceFile.getPath().toLowerCase();
			//System.out.println(">>>"+name);
			if (!sourceFile.isDirectory() && (name.endsWith(".properties") || name.endsWith(".xsd") || name.endsWith(".xml") || name.endsWith(".wsdl") || name.endsWith(".bpel") || name.endsWith(".aries") || name.endsWith(".ariel"))) {
				log.info("Property substitution: " + sourceFile.getName() + ", destination: " + runtimeLocation);
				
				//String fileSeparator = System.getProperty("file.separator");
				//String workingDir = getWorkingLocation().replace("/", fileSeparator);
				//if (workingDir.substring(0, 1).equals(fileSeparator))
				//	workingDir = workingDir.substring(1);
				executeTokenSubstitutionOnFile(runtimeLocation, workingLocation, sourceFile);
				//System.out.println("****** File: "+sourceFile);
				return;
				
			} else if (sourceFile.isDirectory()) {
				//System.out.println("*** File: "+sourceFile);
				File[] files = sourceFile.listFiles();
				if (files != null) {
					for (File file: files) {
						executeTokenSubstitution(runtimeLocation, workingLocation, file);
					}
				}
				return;
			}
		}
	}

	public void executeTokenSubstitutionOnFile(String runtimeLocation, String workingLocation, File sourceFile) throws Exception {
		//System.out.println("File: "+sourceFile);
		if (!sourceFile.exists())
			throw new RuntimeException("File not found: "+sourceFile);
		String fileSeparator = System.getProperty("file.separator");
		
		//TODO skip this for now
		String name = sourceFile.getName();
		if (name.startsWith("timer"))
			return;
		
		String absoluteSourcePath = sourceFile.getAbsolutePath();
		if (!absoluteSourcePath.contains(workingLocation))
			throw new RuntimeException("Unexpected file location: "+absoluteSourcePath);
		
		String targetPath = runtimeLocation;
		String sourceRelativePath = absoluteSourcePath.replace(workingLocation, "");
		if (!sourceRelativePath.startsWith(fileSeparator + "model"))
			targetPath += fileSeparator + "model";
		targetPath += sourceRelativePath;

		//String readFileToString = org.apache.commons.io.FileUtils.readFileToString(sourceFile);
		//String sourceFileRelativePath = absolutePath.replace(workingLocation, "");
		//targetPath = runtimeLocation + fileSeparator + "model" + sourceRelativePath;

//		if (sourceFile.toString().contains("bookshop_messaging.aries"))
//			System.out.println();

		FilterSetCollection filters = createPropertyFilterSetCollection();
		FileUtils.getFileUtils().copyFile(sourceFile.getPath(), targetPath, filters, true);
		//FileUtils.getFileUtils().copyFile(runtimeFile, sourceFile);
		//System.out.println("File copy: "+sourceFile+" to "+targetPath);
	}
	
	protected String getTempDir() throws Exception {
		//String tempDir = System.getProperty("java.io.tmpdir")
		String userDir = System.getProperty("user.dir");
		String tempDir = userDir+"/.tmp";
		FileUtil.removeDirectory(tempDir);
		FileUtil.assureDirectoryExists(tempDir);
		return tempDir;
	}
	
//	protected void integrateProperties(String pathName, String fileName) throws IOException {
//		String sourceFile = pathName + "/" + fileName;
//		File file = new File(sourceFile);
//		if (file.exists()) {
//			String tempFile = System.getProperty("java.io.tmpdir") + "/" + fileName;
//			FilterSetCollection filters = createPropertyFilterSetCollection();
//			FileUtils.getFileUtils().copyFile(sourceFile, tempFile, filters, true);
//			FileUtils.getFileUtils().copyFile(tempFile, sourceFile);
//		}
//	}

	protected FilterSetCollection createPropertyFilterSetCollection() {
		//String defaultPropertyFile = PropertyManager.getInstance().getDefaultPropertyFile();
		//String serverPropertyFile = PropertyManager.getInstance().getServerPropertyFile();
		//String globalPropertyLocation = PropertyManager.getInstance().getGlobalPropertyLocation();
		//FilterSet filterSet1 = createPropertyFilterSetFromFile(defaultPropertyFile);
		//FilterSet filterSet2 = createPropertyFilterSetFromFile(serverPropertyFile);
		FilterSet filterSet3 = createPropertyFilterSetFromDirectory(propertyLocation);
		FilterSetCollection filters = new FilterSetCollection();
		//filters.addFilterSet(filterSet1);
		//filters.addFilterSet(filterSet2);
		filters.addFilterSet(filterSet3);
		return filters;
	}

	public FilterSet createPropertyFilterSetFromFile(String sourceFile) {
		FilterSet filterSet = new FilterSet();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(sourceFile);
		if (resource != null) {
			File file = new File(resource.getPath());
			if (file.exists()) {
				String path = file.getPath();
				if (path.endsWith(".properties")) {
					filterSet.readFiltersFromFile(file);
				} else if (file.isDirectory()) {
					FilterSet fs = createPropertyFilterSetFromDirectory(path);
					filterSet.addConfiguredFilterSet(fs);
				}
			}
		}
		return filterSet;
	}
	
	public FilterSet createPropertyFilterSetFromDirectory(String sourceDirectory) {
		File directory = new File(sourceDirectory);
		FilterSet filterSet = new FilterSet();
		File[] files = directory.listFiles();
		if (files != null) {
			for (int i=0; i < files.length; i++) {
				File file = files[i];
				String path = file.getPath();
				if (path.endsWith(".properties")) {
					filterSet.readFiltersFromFile(file);
				} else if (path.endsWith("/properties")) {
					filterSet.readFiltersFromFile(file);
				} else if (file.isDirectory()) {
					FilterSet fs = createPropertyFilterSetFromDirectory(path);
					filterSet.addConfiguredFilterSet(fs);
				}
			}
		}
		return filterSet;
	}
	
	
	public void XXX(InputStream inputStream, OutputStream outputStream, FilterSetCollection filters) throws IOException {
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            in = new BufferedReader(isr);
            out = new BufferedWriter(osw);

            LineTokenizer lineTokenizer = new LineTokenizer();
            lineTokenizer.setIncludeDelims(true);
        
            String newline = null;
            String line = lineTokenizer.getToken(in);
            while (line != null) {
                if (line.length() == 0) {
                    // this should not happen, because the lines are
                    // returned with the end of line delimiter
                    out.newLine();
                } else {
                    newline = filters.replaceTokens(line);
                    out.write(newline);
                }
                line = lineTokenizer.getToken(in);
            }
        } finally {
            FileUtils.close(out);
            FileUtils.close(in);
        }
	}

	public String executeTokenSubstitution(InputStream inputStream) throws IOException {
		FilterSetCollection filters = createPropertyFilterSetCollection();
		return executeTokenSubstitution(inputStream, filters);
	}
	
	public String executeTokenSubstitution(InputStream inputStream, FilterSetCollection filters) throws IOException {
        BufferedReader in = null;
        StringWriter out = null;

        try {
            InputStreamReader reader = new InputStreamReader(inputStream);
            in = new BufferedReader(reader);
            out = new StringWriter();
            
            LineTokenizer lineTokenizer = new LineTokenizer();
            lineTokenizer.setIncludeDelims(true);
        
            String newline = null;
            String line = lineTokenizer.getToken(in);
            while (line != null) {
                if (line.length() == 0) {
                    // this should not happen, because the lines are
                    // returned with the end of line delimiter
                    out.write("\n");
                } else {
                    newline = filters.replaceTokens(line);
                    out.write(newline);
                }
                line = lineTokenizer.getToken(in);
            }
        } finally {
            FileUtils.close(out);
            FileUtils.close(in);
        }
        
        return out.toString();
	}
}
