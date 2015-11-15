package aries.codegen.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.util.FileUtils;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;


public class GenerateUtil {

	private static String DEFAULT_TOKEN_HOME = "c:/workspace/ARIES/nam/tokens";

	
	public static void generateFiles(String sourcePath, String targetPath, List<Pattern> excludes) throws Exception {
		File directory = new File(sourcePath);
		File[] list = directory.listFiles();
		for (int i = 0; i < list.length; i++) {
			File file = list[i];
			generateFiles(sourcePath, targetPath, file, excludes);
		}
	}
	
	protected static void generateFiles(String sourcePath, String targetPath, File sourceFile, List<Pattern> excludes) throws Exception {
		String fileName = sourceFile.getName();
		if (!isMatch(excludes, fileName)) {
			if (sourceFile.isDirectory()) {
				sourcePath += "/" + fileName;
				targetPath += "/" + fileName;
				generateFiles(sourcePath, targetPath, excludes); 
			} else {
				generateFile(sourcePath, fileName, targetPath, fileName); 
			}
		}
	}

	public static boolean isMatch(List<Pattern> patterns, String text) {
		Iterator<Pattern> iterator = patterns.iterator();
		while (iterator.hasNext()) {
			Pattern pattern = iterator.next();
	        if (pattern != null) {
	            Matcher matcher = pattern.matcher(text);
	            if (matcher.matches())
	            	return true;
	        }
		}
    	return false;
	}

	
	public static void generateFile(String sourcePath, String sourceFile, String targetPath, String targetFile) throws Exception {
		FilterSetCollection filters = FilterUtil.createFilterSetCollection(DEFAULT_TOKEN_HOME);
		generateFile(sourcePath, sourceFile, targetPath, targetFile, filters);
	}

	public static void generateFile(String sourcePath, String sourceFile, String targetPath, String targetFile, FilterSet filters) throws Exception {
		FilterSetCollection filterSetCollection = FilterUtil.createFilterSetCollection(filters);
		generateFile(sourcePath, sourceFile, targetPath, targetFile, filterSetCollection);
	}
	
	public static void generateFile(String sourcePath, String sourceFile, String targetPath, String targetFile, FilterSetCollection filters) throws Exception {
		String tempFile = System.getProperty("java.io.tmpdir") + targetFile;
		FileUtil.assureDirectoryExists(targetPath);
		//System.out.println(">>>"+sourceFile+", "+targetFile);
		FileUtils.getFileUtils().copyFile(sourcePath+"/"+sourceFile, tempFile, filters, true);
		FileUtils.getFileUtils().copyFile(tempFile, targetPath+"/"+targetFile);
	}


	public static String convertPackageToPath(String packageName) {
		if (packageName == null)
			return "";
		StringBuffer path = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(packageName, ".");
		while (tokenizer.hasMoreElements()) {
			String folder = tokenizer.nextToken();
			path.append("/"+folder);
		}
		return path.toString();
	}

}
