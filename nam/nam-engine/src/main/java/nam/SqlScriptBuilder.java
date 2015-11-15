package nam;

import java.util.ArrayList;
import java.util.List;

import nam.model.Persistence;
import nam.model.Project;

import org.apache.tools.ant.types.FilterSet;
import org.aries.util.NameUtil;

import aries.codegen.AbstractScriptBuilder;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelScript;


public class SqlScriptBuilder extends AbstractScriptBuilder {

	public SqlScriptBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public List<ModelScript> buildScripts(Project project) throws Exception {
		List<ModelScript> scripts = new ArrayList<ModelScript>();
		scripts.addAll(buildDatabaseCreationScripts(project));
//		scripts.add(buildDatabaseCreationScript(project));
		return scripts;
	}

	public List<ModelScript> buildDatabaseCreationScripts(Project project) throws Exception {
		List<ModelScript> scripts = new ArrayList<ModelScript>();
		scripts.add(buildProjectDatabaseCreationScript(project));
		scripts.add(buildTestDatabaseCreationScript(project));
		return scripts;
	}

	public ModelScript buildProjectDatabaseCreationScript(Project project) throws Exception {
		String projectName = context.getProjectNameCamelCased();
		String databaseName = projectName+"DB";
		//TODO externalize
		String databaseUser = projectName+"Manager";
		String databasePassword = projectName+"Password";
		String targetScriptName = "db-create-"+databaseName+".sql";
		FilterSet filterSet = buildDatabaseCreationFilterSet(databaseName, databaseUser, databasePassword);
		ModelScript modelScript = buildModelScript("deploy/sql", "db-create.sql", "deploy/sql", targetScriptName , filterSet);
		return modelScript;
	}
	
	public ModelScript buildTestDatabaseCreationScript(Project project) throws Exception {
		String projectName = context.getProjectNameCamelCased();
		String databaseName = projectName+"TestDB";
		//TODO externalize
		String databaseUser = projectName+"TestManager";
		String databasePassword = projectName+"Password";
		String targetScriptName = "db-create-"+databaseName+".sql";
		FilterSet filterSet = buildDatabaseCreationFilterSet(databaseName, databaseUser, databasePassword);
		ModelScript modelScript = buildModelScript("deploy/sql", "db-create.sql", "deploy/sql", targetScriptName, filterSet);
		return modelScript;
	}

	protected FilterSet buildDatabaseCreationFilterSet(String databaseName, String databaseUser, String databasePassword) {
		String projectName = context.getProjectNameCamelCased();
		String projectNameCapped = NameUtil.capName(projectName);
		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1DB", databaseName);
		filterSet.addFilter("template1User", databaseUser);
		filterSet.addFilter("template1Password", databasePassword);
		//filterSet.addFilter("Template1", projectNameCapped);
		//filterSet.addFilter("template1", projectName);
		return filterSet;
	}

}
