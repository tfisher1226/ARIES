package nam.data;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractGeneratorHelper;
import aries.generation.engine.GenerationContext;


public class DataModuleHelper extends AbstractGeneratorHelper {

	//private ModelModuleHelper modelModuleHelper;
	
	//private PersistenceXMLBuilder persistenceXMLBuilder;
	
	
	public DataModuleHelper(GenerationContext context) {
		super(context);
		//modelModuleHelper = new ModelModuleHelper(context);
		//persistenceXMLBuilder = new PersistenceXMLBuilder(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		//modelModuleHelper.initialize(project, module);
		//ariesModelHelper.assureApplications(project);
		ariesModelHelper.assureModule(project, module);
	}
	
}
