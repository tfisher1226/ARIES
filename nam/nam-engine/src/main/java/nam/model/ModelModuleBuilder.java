package nam.model;

import java.util.Collection;
import java.util.List;

import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.src.main.java.ElementUtilBuilder;
import nam.model.src.main.java.ModelBeanClassBuilder;
import nam.model.src.main.java.ModelFixtureClassBuilder;
import nam.model.src.main.java.ModelHelperClassBuilder;
import nam.model.src.main.java.ObjectFactoryClassBuilder;
import nam.model.src.main.java.ServiceFaultBuilder;
import nam.model.src.main.resources.model.ModelXSDBuilder;
import aries.codegen.application.AbstractModuleBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelFile;
import aries.generation.model.ModelPackage;


public class ModelModuleBuilder extends AbstractModuleBuilder {

	// main sources area
	private ModelModuleHelper modelModuleHelper;
	private ModelBeanClassBuilder modelBeanClassBuilder;
	private ModelHelperClassBuilder modelHelperClassBuilder;
	private ObjectFactoryClassBuilder objectFactoryClassBuilder;
	private ElementUtilBuilder elementUtilBuilder;
	private ServiceFaultBuilder serviceFaultBuilder;

	//main resources
	private ModelXSDBuilder modelXSDBuilder;

	// test sources area
	private ModelFixtureClassBuilder modelFixtureClassBuilder;


	public ModelModuleBuilder(GenerationContext context) {
		// main sources area
		modelModuleHelper = new ModelModuleHelper(context);
		modelBeanClassBuilder = new ModelBeanClassBuilder(context);
		modelHelperClassBuilder = new ModelHelperClassBuilder(context);
		modelFixtureClassBuilder = new ModelFixtureClassBuilder(context);
		objectFactoryClassBuilder = new ObjectFactoryClassBuilder(context);
		elementUtilBuilder = new ElementUtilBuilder(context);
		serviceFaultBuilder = new ServiceFaultBuilder(context);

		//main resources
		modelXSDBuilder = new ModelXSDBuilder(context);

		// test sources area
		this.context = context;
	}

	public void initialize(Project project, Module module) throws Exception {
		modelModuleHelper.initialize(project, module);
		context.initialize(project, module);
	}

	/*
	 * Main classes
	 * ------------
	 */

	public Collection<ModelPackage> buildModelBeanPackages(List<Namespace> namespaces) throws Exception {
		return modelBeanClassBuilder.buildPackages(namespaces);
	}
	
	public ModelPackage buildModelBeanPackage(Namespace namespace) throws Exception {
		return modelBeanClassBuilder.buildPackage(namespace);
	}

	public Collection<ModelClass> buildModelHelperClasses(List<Namespace> namespaces) throws Exception {
		return modelHelperClassBuilder.buildClasses(namespaces);
	}
	
	public ModelClass buildModelHelperClass(Namespace namespace) throws Exception {
		return modelHelperClassBuilder.buildClass(namespace);
	}

	public Collection<ModelClass> buildObjectFactoryClasses(List<Namespace> namespaces) throws Exception {
		return objectFactoryClassBuilder.buildModelClasses(namespaces);
	}

	public Collection<ModelClass> buildElementUtilClasses(List<Namespace> namespaces) throws Exception {
		return elementUtilBuilder.buildClasses(namespaces);
	}

	public Collection<ModelClass> buildServiceFaultClasses(Service service) throws Exception {
		return serviceFaultBuilder.buildClasses(service);
	}
	
	/*
	 * Main resources
	 * --------------
	 */

	public Collection<ModelFile> buildModelXSDFiles(List<Namespace> namespaces) throws Exception {
		List<ModelFile> modelFiles = modelXSDBuilder.buildFiles(namespaces);
		return modelFiles;
	}

	/*
	 * Test classes
	 * ------------
	 */

	public List<ModelClass> buildModelFixtureClasses(List<Namespace> namespaces) throws Exception {
		return modelFixtureClassBuilder.buildClasses(namespaces);
	}

}
