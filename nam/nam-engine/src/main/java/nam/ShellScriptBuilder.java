package nam;

import java.util.ArrayList;
import java.util.List;

import nam.model.Project;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractScriptBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelScript;


public class ShellScriptBuilder extends AbstractScriptBuilder {

	public ShellScriptBuilder(GenerationContext context) {
		this.context = context;
	}

	//TODO 
	//Extract all unique databases (i.e. loop thru all persistence units?),
	//then provide way to extract configuration info for each, then create scripts for each
	public List<ModelScript> buildScripts(Project project) throws Exception {
		String projectName = context.getProjectNameCamelCased();
		String databaseName = projectName+"DB";
		String testDatabaseName = projectName+"TestDB";
		
		String[] databaseNames = new String[] {databaseName, testDatabaseName};
		List<ModelScript> scripts = new ArrayList<ModelScript>();
		for (String name : databaseNames) {
			scripts.add(buildSetenvScript(name));
			scripts.add(buildDatabaseSetupScript(name));
			scripts.add(buildDatabaseCreationScript(name));
		}
		scripts.add(buildDatabaseSetupScript(databaseNames));
		return scripts;
	}

//	public List<ModelScript> buildScripts(String databaseName) throws Exception {
//		List<ModelScript> scripts = new ArrayList<ModelScript>();
//		scripts.add(buildSetenvScript(databaseName));
//		scripts.add(buildDatabaseSetupScript(databaseName));
//		scripts.add(buildDatabaseCreationScript(databaseName));
//		return scripts;
//	}
	
	private ModelScript buildDatabaseSetupScript(String[] databaseNames) {
		Buf buf = new Buf();
		buf.putLine("#!/bin/sh");
		buf.putLine("");
		for (String databaseName : databaseNames) {
			buf.putLine("db-setup-"+databaseName+".sh");
		}
		ModelScript modelScript = new ModelScript();
		modelScript.setSourceFolder("deploy/sql");
		modelScript.setSourceFile("db-setup.sh");
		modelScript.setTargetFolder("deploy/sql");
		modelScript.setTargetFile("db-setup.sh");
		modelScript.setContent(buf.get());
		return modelScript;
	}

	public ModelScript buildSetenvScript(String databaseName) throws Exception {
		String scriptName = "setenv-"+databaseName+".sh";
		FilterSet filterSet = new MyFilterSet("template1DB", databaseName);
		ModelScript modelScript = buildModelScript("deploy/sql", "setenv.sh", "deploy/sql", scriptName, filterSet);
		return modelScript;
	}

	public ModelScript buildDatabaseSetupScript(String databaseName) throws Exception {
		String scriptName = "db-create-"+databaseName+".sh";
		FilterSet filterSet = new MyFilterSet("template1DB", databaseName);
		ModelScript modelScript = buildModelScript("deploy/sql", "db-create.sh", "deploy/sql", scriptName, filterSet);
		return modelScript;
	}

	public ModelScript buildDatabaseCreationScript(String databaseName) throws Exception {
		String scriptName = "db-create-"+databaseName+".sh";
		FilterSet filterSet = new MyFilterSet("template1DB", databaseName);
		ModelScript modelScript = buildModelScript("deploy/sql", "db-create.sh", "deploy/sql", scriptName, filterSet);
		return modelScript;
	}

}
