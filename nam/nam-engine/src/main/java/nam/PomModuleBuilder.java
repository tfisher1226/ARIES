package nam;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.application.AbstractModuleBuilder;
import aries.generation.engine.GenerationContext;


public class PomModuleBuilder extends AbstractModuleBuilder {

	private PomModuleHelper pomModuleHelper;

	
	public PomModuleBuilder(GenerationContext context) {
		pomModuleHelper = new PomModuleHelper(context);
		this.context = context;
	}

//	public void initialize(Project project) throws Exception {
//		initialize(project, ProjectUtil.getProjectModules(project));
//		List<Application> applications = ProjectUtil.getApplications(project);
//		Iterator<Application> iterator = applications.iterator();
//		while (iterator.hasNext()) {
//			Application application = (Application) iterator.next();
//			initialize(project, ApplicationUtil.getModules(application));
//		}
//		
////		ModuleFactory moduleFactory = new ModuleFactory();
////		Module module = moduleFactory.createPomModule();
////		initialize(project, module);
//		
////		List<Project> subProjects = ProjectUtil.getProjects(project);
////		Iterator<Project> iterator = subProjects.iterator();
////		while (iterator.hasNext()) {
////			Project subProject = (Project) iterator.next();
////			initialize(subProject);
////		}
//	}

//	public void initialize(Project project, List<Module> modules) throws Exception {
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = (Module) iterator.next();
//			if (module.getType() == ModuleType.POM)
//				initialize(project, module);
//		}
//	}

	public void initialize(Project project, Module module) throws Exception {
		pomModuleHelper.initialize(project, module);
	}
	
}

