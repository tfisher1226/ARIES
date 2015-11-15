package nam.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.src.main.java.ElementUtilGenerator;
import nam.model.src.main.java.ModelBeanClassGenerator;
import nam.model.src.main.java.ModelFixtureClassGenerator;
import nam.model.src.main.java.ModelHelperClassGenerator;
import nam.model.src.main.java.ObjectFactoryClassGenerator;
import nam.model.src.main.java.ServiceFaultGenerator;
import nam.model.src.main.resources.model.ModelXSDGenerator;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;

import org.aries.util.properties.PropertyManager;

import aries.codegen.application.AbstractModuleGenerator;
import aries.generation.engine.GenerationContext;


public class ModelModuleGenerator extends AbstractModuleGenerator {

	// main sources area
	private ModelModuleBuilder modelModuleBuilder; 
	private ModelProjectGenerator modelProjectGenerator;
	private ModelBeanClassGenerator modelBeanClassGenerator;
	private ModelHelperClassGenerator modelHelperClassGenerator;
	private ModelFixtureClassGenerator modelFixtureClassGenerator;
	private ObjectFactoryClassGenerator objectFactoryClassGenerator;
	private ElementUtilGenerator elementUtilGenerator;
	private ServiceFaultGenerator serviceFaultGenerator;

	// main resources area
	private ModelXSDGenerator modelXSDGenerator; 

	// test sources area

	
	public ModelModuleGenerator(GenerationContext context) {
		// main sources area
		modelModuleBuilder = new ModelModuleBuilder(context);
		modelProjectGenerator = new ModelProjectGenerator(context);
		modelBeanClassGenerator = new ModelBeanClassGenerator(context);
		modelHelperClassGenerator = new ModelHelperClassGenerator(context);
		modelFixtureClassGenerator = new ModelFixtureClassGenerator(context);
		objectFactoryClassGenerator = new ObjectFactoryClassGenerator(context);
		elementUtilGenerator = new ElementUtilGenerator(context);
		serviceFaultGenerator = new ServiceFaultGenerator(context);
		
		// main resources area
		modelXSDGenerator = new ModelXSDGenerator(context);

		// test sources area

		PropertyManager.getInstance().initialize();
		this.context = context;
	}

	public void initialize(Project project, Module module) throws Exception {
		modelModuleBuilder.initialize(project, module);
	}

	public void generate(Project project, Module module) throws Exception {
		if (context.canGenerateModel()) {
			System.out.println("\nGenerating MODEL-module: "+ModuleUtil.getModuleId(module));
			if (context.canGenerate("project"))
				modelProjectGenerator.generate(project, module);
			generateContents(project, module);
		}
	}
	
	protected void generateContents(Project project, Module module) throws Exception {
		//List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
		List<Namespace> namespaces = module.getInformation().getNamespaces();
		//namespaces.add(context.getNamespaceByUri(module.getNamespace()));
		
		if (context.canGenerate("sources")) {
			context.setOptionGenerateTests(false);
			generateSources(module, namespaces);
			generateResources(module, namespaces);
		}
		if (context.canGenerate("tests")) {
			context.setOptionGenerateTests(true);
			generateTests(namespaces);
		}
	}

	protected void generateSources(Module module, List<Namespace> namespaces) throws Exception {
		modelBeanClassGenerator.generatePackages(modelModuleBuilder.buildModelBeanPackages(namespaces));
		modelHelperClassGenerator.generateClasses(modelModuleBuilder.buildModelHelperClasses(namespaces));
		modelFixtureClassGenerator.generateClasses(modelModuleBuilder.buildModelFixtureClasses(namespaces));
		objectFactoryClassGenerator.generateClasses(modelModuleBuilder.buildObjectFactoryClasses(namespaces));
		elementUtilGenerator.generateClasses(modelModuleBuilder.buildElementUtilClasses(namespaces));
		Collection<Service> services = ProjectUtil.getServices(context.getProject());
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			serviceFaultGenerator.generateClasses(modelModuleBuilder.buildServiceFaultClasses(service));
		}
	}

	protected void generateResources(Module module, List<Namespace> namespaces) throws Exception {
		modelXSDGenerator.generateFiles(modelModuleBuilder.buildModelXSDFiles(namespaces));
	}
	
	protected void generateTests(List<Namespace> namespaces) throws Exception {
		//modelBeanGenerator.generate(modelPackages);
	}

}
