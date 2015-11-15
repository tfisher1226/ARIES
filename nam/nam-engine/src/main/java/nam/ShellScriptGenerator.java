package nam;

import java.util.List;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractScriptGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelScript;


public class ShellScriptGenerator extends AbstractScriptGenerator {

	private ShellScriptBuilder shellScriptBuilder;

	
	public ShellScriptGenerator(GenerationContext context) {
		shellScriptBuilder = new ShellScriptBuilder(context);
		this.context = context;
	}

	public void generateScripts(Project project, Module module) throws Exception {
		List<ModelScript> shellScripts = shellScriptBuilder.buildScripts(project);
		generateScripts(shellScripts);
	}

}
