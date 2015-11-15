package nam.ear;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractGeneratorHelper;
import aries.generation.engine.GenerationContext;


public class EARModuleHelper extends AbstractGeneratorHelper {

	public EARModuleHelper(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		//ariesModelHelper.assureApplications(project);
		ariesModelHelper.assureModule(project, module);
	}
	
}
