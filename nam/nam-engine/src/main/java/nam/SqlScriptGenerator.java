package nam;

import java.util.List;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractScriptGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelScript;


public class SqlScriptGenerator extends AbstractScriptGenerator {

	private SqlScriptBuilder sqlScriptBuilder;

	
	public SqlScriptGenerator(GenerationContext context) {
		sqlScriptBuilder = new SqlScriptBuilder(context);
		this.context = context;
	}
	
	public void generateScripts(Project project, Module module) throws Exception {
		List<ModelScript> shellScripts = sqlScriptBuilder.buildScripts(project);
		generateScripts(shellScripts);
	}

}
