package nam.ear;

import nam.ear.src.main.application.METAINF.ApplicationXMLGenerator;
import nam.ear.src.main.application.METAINF.JBossAppXMLGenerator;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ModuleUtil;

import org.aries.util.properties.PropertyManager;

import aries.codegen.application.AbstractModuleGenerator;
import aries.generation.engine.GenerationContext;


public class EARModuleGenerator extends AbstractModuleGenerator {

	private EARModuleBuilder earModuleBuilder; 
	private EARProjectGenerator earProjectGenerator;

	//resources generators:
	private ApplicationXMLGenerator applicationXMLGenerator; 
	private JBossAppXMLGenerator jbossAppXMLGenerator; 

	
	public EARModuleGenerator(GenerationContext context) {
		earModuleBuilder = new EARModuleBuilder(context);
		earProjectGenerator = new EARProjectGenerator(context);
		applicationXMLGenerator = new ApplicationXMLGenerator(context);
		jbossAppXMLGenerator = new JBossAppXMLGenerator(context);
		PropertyManager.getInstance().initialize();
		this.context = context;
	}

	public void initialize(Project project, Module module) throws Exception {
		earModuleBuilder.initialize(project, module);
	}
	
	public void generate(Project project, Module module) throws Exception {
		if (context.canGenerateEAR()) {
			System.out.println("\nGenerating EAR-module "+ModuleUtil.getModuleId(module));
			if (context.canGenerate("project"))
				earProjectGenerator.generate(project, module);
			if (context.canGenerate("sources"))
				generateSources();
			if (context.canGenerate("tests"))
				generateTests();
		}
	}
	
	protected void generateSources() throws Exception {
		context.setOptionGenerateTests(false);
		applicationXMLGenerator.generateFile(earModuleBuilder.buildApplicationXML());
		//jbossAppXMLGenerator.generate(earModuleBuilder.buildJBossAppXML());
	}
	
	protected void generateTests() throws Exception {
		context.setOptionGenerateTests(true);
	}

}
