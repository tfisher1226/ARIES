/*
   Copyright 2010 Rozene Technologies

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.aries.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.FileSet;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.aries.ProjectBuilder;
import org.aries.nam.model.Project;
import org.codehaus.plexus.util.FileUtils;

import aries.generation.engine.GenerationContext;


/**
 * @goal compile
 * @phase compile
 * @author Tom Fisher
 */
public class GeneratorMojo extends AbstractMojo {

	protected static String USER_DIR = System.getProperty("user.dir");

	protected static String MODEL_LOCATION = USER_DIR + "/src/main/resources";
	
	protected static String PROPERTY_LOCATION = "c:/workspace/ARIES/aries/properties";
	
	//protected static String PROPERTY_LOCATION = USER_DIR + "/properties";

	protected static String WORKSPACE_LOCATION = "c:/workspace/ARIES";
	
	//protected static String WORKSPACE_LOCATION = FilenameUtils.getFullPath(USER_DIR);
	
	protected static String RUNTIME_LOCATION = USER_DIR + "/target/runtime";
	
	
	/**
	 * @parameter
	 */
    private String subsetType;

	/**
	 * @parameter
	 */
    private FileSet fileSet;

	/**
	 * @parameter
	 */
    private String projectName;

	/**
	 * @parameter
	 */
    private String templateHome = "..";

	/**
	 * @parameter
	 */
    private String templateName = "template1";

	/**
	 * @parameter
	 */
    private String targetHome;

	/**
	 * @parameter
	 */
    private String targetWorkspace;

	/**
	 * @parameter
	 */
    private boolean dryRun;

    /**
     * @execute
     * throw new MojoFailureException();
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        File dir = new File(fileSet.getDirectory());
        if (dir.isDirectory()) {
            getLog().info("Reading model files from: " + dir);
            
            String includes = getCommaSeparatedList(fileSet.getIncludes());
            String excludes = getCommaSeparatedList(fileSet.getExcludes());
            
            if (dryRun) {
                getLog().info("Dry run, not writing compilation output");
                // Don't write the output anywhere.
                //engine.setDryRun(true);
            }
            
            try {
                List<?> files = FileUtils.getFiles(dir, includes, excludes);
                for (Object object : files) {
                    File file = (File) object;
                    String filePath = file.getAbsolutePath();
                    //String parentDirectory = NameUtil.getParentDirectoryName(filePath);
					getLog().info("Generating from model: " + filePath);
					
                    try {
                		ProjectBuilder builder = new ProjectBuilder(createContext());
                		builder.initialize(file.getName());
                		List<Project> projects = builder.buildProjects();
                		builder.generateProjects(projects);
                		
                		//GenerationContext context = createGenerationContext();
                    	//GeneratorEngine engine = new GeneratorEngine(context);
                    	//engine.setInputFile(filePath);
                    	//engine.initialize();
                    	//engine.generate();
                    } catch (Exception e) {
                        throw new MojoFailureException("Error: " + filePath, e);
                    }
                }

            } catch (IOException e) {
                throw new MojoFailureException("Error", e);
            }
            
        } else {
            throw new MojoFailureException(dir + " is not a directory.");
        }
    }
    
	protected GenerationContext createContext() {
		GenerationContext context = new GenerationContext();
		context.setModelLocation(MODEL_LOCATION);
		context.setPropertyLocation(PROPERTY_LOCATION);
		context.setWorkspaceLocation(WORKSPACE_LOCATION);
		context.setRuntimeLocation(RUNTIME_LOCATION);
		context.setProperty("generateServiceTransport", "JAXWS");
		context.setProperty("useQualifiedContextNames");
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
    	//context.setTargetWorkspace("../../ARIES_GENERATED");
    	//context.setTargetWorkspace("C:/workspace/ARIES");
    	//context.setTargetWorkspace("..");
    	context.setTemplateWorkspace("..");
    	context.setTemplateName("template1");
		context.addSubset("project");
		context.addSubset("service");
		context.addSubset("model");
		context.addSubset("client");
		context.addSubset("data");
		context.addSubset("view");
		context.addSubset("ear");
		return context;
	}
	
    protected GenerationContext createGenerationContext() {
		GenerationContext context = new GenerationContext();
    	if (!StringUtils.isEmpty(projectName)) context.setProjectName(projectName);
    	if (!StringUtils.isEmpty(templateHome)) context.setTemplateHome(templateHome);
    	if (!StringUtils.isEmpty(templateName)) context.setTemplateName(templateName);
    	if (!StringUtils.isEmpty(targetHome)) context.setTargetHome(targetHome);
    	if (!StringUtils.isEmpty(targetWorkspace)) context.setTargetWorkspace(targetWorkspace);
    	if (!StringUtils.isEmpty(subsetType)) {
    		context.getSubsets().clear();
    		StringTokenizer tokenizer = new StringTokenizer(subsetType);
    		while (tokenizer.hasMoreElements()) {
				String subset = (String) tokenizer.nextElement();
	    		context.addSubset(subset);
			}
    	}
		return context;
	}

	protected String getCommaSeparatedList(List<?> list) {
        StringBuffer buffer = new StringBuffer();
        for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
            Object element = iterator.next();
            buffer.append(element.toString());
            if (iterator.hasNext()) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

}
