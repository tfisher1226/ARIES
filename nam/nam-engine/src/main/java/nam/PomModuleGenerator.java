package nam;

import nam.model.Module;
import nam.model.Project;
import nam.model.util.ModuleUtil;

import org.aries.util.properties.PropertyManager;

import aries.codegen.application.AbstractModuleGenerator;
import aries.generation.engine.GenerationContext;


public class PomModuleGenerator extends AbstractModuleGenerator {

	private PomModuleBuilder pomModuleBuilder; 
	private PomProjectGenerator pomProjectGenerator;
	private ShellScriptGenerator shellScriptGenerator;
	private SqlScriptGenerator sqlScriptGenerator;
	
	
	public PomModuleGenerator(GenerationContext context) {
		pomModuleBuilder = new PomModuleBuilder(context);
		pomProjectGenerator = new PomProjectGenerator(context);
		shellScriptGenerator = new ShellScriptGenerator(context);
		sqlScriptGenerator = new SqlScriptGenerator(context);
		PropertyManager.getInstance().initialize();
		this.context = context;
	}

	public void initialize(Project project, Module module) throws Exception {
		pomModuleBuilder.initialize(project, module);
	}

//	public void generate(Project project) throws Exception {
//		System.out.println("\nGenerating POM-module-specific source files");
//		//log.info("Generating POM-module-specific source files");
//		generate(project, ProjectUtil.getProjectModules(project));
//		generate(project, ProjectUtil.getPomModules(project));
//	}
//	
//	public void generate(Project project, List<Module> modules) throws Exception {
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = iterator.next();
//			if (module.getType() == ModuleType.POM)
//				generate(project, module);
//		}
//	}

	public void generate(Project project, Module module) throws Exception {
		if (context.canGeneratePom()) {
			System.out.println("\nGenerating POM-module: "+ModuleUtil.getModuleId(module));
			if (context.canGenerate("project"))
				pomProjectGenerator.generate(project, module);
			if (context.canGenerate("sources")) {
				shellScriptGenerator.generateScripts(project, module);
				sqlScriptGenerator.generateScripts(project, module);
			}
		}
	}
	
//	public void generateOLD(Project project, List<Module> modules) throws Exception {
//		Assert.isTrue(modules.size() <= 1, "Supporting only 1 POM-module per Application");
//		//Assert.isTrue(pomModules.size() == 1, "1 POM-module must exist per Model");
//		if (context.canGenerate("pom")) {
//			if (modules.size() == 1) {
//				//context.setModule(pomModules.get(0));
//				pomProjectGenerator.generate(project);
//				shellScriptGenerator.generateScripts(project);
//				sqlScriptGenerator.generateScripts(project);
//			} else {
//				pomProjectGenerator.generate(project);
//				shellScriptGenerator.generateScripts(project);
//				sqlScriptGenerator.generateScripts(project);
//			}
//		}
//		
////		List<Project> subProjects = ProjectUtil.getProjects(project);
////		Iterator<Project> iterator = subProjects.iterator();
////		while (iterator.hasNext()) {
////			Project subProject = (Project) iterator.next();
////			generate(subProject);
////		}
//	}

}

