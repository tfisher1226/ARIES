package nam.ui;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractGeneratorHelper;
import aries.generation.engine.GenerationContext;


public class ViewModuleHelper extends AbstractGeneratorHelper {

	public ViewModuleHelper(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		//ariesModelHelper.assureApplications(project);
		ariesModelHelper.assureModule(project, module);
	}
	
}
