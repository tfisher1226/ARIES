package nam.client;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractGeneratorHelper;
import aries.generation.engine.GenerationContext;


public class ClientModuleHelper extends AbstractGeneratorHelper {

	//private ClientModuleBuilder clientModuleBuilder;

	
	public ClientModuleHelper(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		//ariesModelHelper.assureApplications(project);
		ariesModelHelper.assureModule(project, module);
	}
	
}
