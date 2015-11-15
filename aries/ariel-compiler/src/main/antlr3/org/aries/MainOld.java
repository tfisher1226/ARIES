package org.aries;

import java.io.File;
import java.net.URL;

import org.aries.nam.model.Project;


public class MainOld {

	private static String FILE_NAME = "bookshop.ariel";
	
	//private static String FILE_NAME = "timedDelivery.ariel";
	
	//private static String FILE_NAME = "tradeSettlement.ariel";

	
	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(FILE_NAME);
		File file = new File(resource.getFile());
		long start = System.currentTimeMillis();
		processFile(file);
		
		long stop = System.currentTimeMillis();
		long totalTime = stop - start;
		System.out.println("finished parsing in "+totalTime+"ms");
	}

	public static void processFile(File inputFile) throws Exception {
		//If this is a directory, walk through each underlying file/dir
		if (inputFile.isDirectory()) {
			String files[] = inputFile.list();
			for(int i=0; i < files.length; i++) {
				File file = new File(inputFile, files[i]);
				processFile(file);
			}

		//proceed here if this is an Ariel file
		} else if (inputFile.getName().endsWith(".ariel")) {
			System.out.println("parsing "+inputFile.getAbsolutePath());
			parseFile(inputFile.getAbsolutePath());
		}
	}

	public static void parseFile(String arielFile) throws Exception {
		try {
			ProjectBuilder builder = new ProjectBuilder();
			Project project = builder.buildProject(arielFile);

			//using XML output, generate sources
			//AriesProjectAndSourceGenerator ariesProjectAndSourceGenerator = new AriesProjectAndSourceGenerator(builder.getEngine());
			//ariesProjectAndSourceGenerator.generate(project);
			
		} catch (Exception e) {
			System.err.println("parser exception: "+e);
			e.printStackTrace();		
		}
	}

}
