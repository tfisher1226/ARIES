package nam.model;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractGeneratorHelper;
import aries.generation.engine.GenerationContext;


public class ModelModuleHelper extends AbstractGeneratorHelper {

	public ModelModuleHelper(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		//ariesModelHelper.assureApplication(project);
		ariesModelHelper.assureModule(project, module);
		//assureNamespaces(project);
	}
	
}
