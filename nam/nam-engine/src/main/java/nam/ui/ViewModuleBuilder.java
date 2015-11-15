package nam.ui;

import java.util.Collection;

import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import nam.ui.src.main.java.ServletListenerBuilder;
import nam.ui.src.main.java.admin.data.ElementConverterBuilder;
import nam.ui.src.main.java.admin.data.ElementDataManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementEventManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementHelperBuilder;
import nam.ui.src.main.java.admin.data.ElementInfoManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementListManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementListObjectBuilder;
import nam.ui.src.main.java.admin.data.ElementPageManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementRecordSectionBuilder;
import nam.ui.src.main.java.admin.data.ElementSelectManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementServiceManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementTreeBuilderBuilder;
import nam.ui.src.main.java.admin.data.ElementTreeManagerBuilder;
import nam.ui.src.main.java.admin.data.ElementValidatorBuilder;
import nam.ui.src.main.java.admin.data.ElementWizardManagerBuilder;
import nam.ui.src.main.webapp.pages.DataModelXHTMLBuilder;
import aries.codegen.application.AbstractModuleBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


public class ViewModuleBuilder extends AbstractModuleBuilder {

	private ViewModuleHelper viewModuleHelper;
	//private ElementUtilBuilder elementUtilBuilder;
	private ElementHelperBuilder elementHelperBuilder;
	private ElementConverterBuilder elementConverterBuilder;
	private ElementValidatorBuilder elementValidatorBuilder;
	private ElementDataManagerBuilder elementDataManagerBuilder;
	private ElementEventManagerBuilder elementEventManagerBuilder;
	private ElementPageManagerBuilder elementPageManagerBuilder;
	private ElementRecordSectionBuilder elementRecordSectionBuilder;
	private ElementSelectManagerBuilder elementSelectManagerBuilder;
	private ElementServiceManagerBuilder elementServiceManagerBuilder;
	private ElementInfoManagerBuilder elementInfoManagerBuilder;
	private ElementListManagerBuilder elementListManagerBuilder;
	private ElementListObjectBuilder elementListObjectBuilder;
	private ElementTreeManagerBuilder elementTreeManagerBuilder;
	private ElementTreeBuilderBuilder elementTreeBuilderBuilder;
	private ElementWizardManagerBuilder elementWizardManagerBuilder;
	
	private DataModelXHTMLBuilder dataModelXHTMLBuilder;

	private ServletListenerBuilder servletListenerBuilder;

	
	public ViewModuleBuilder(GenerationContext context) {
		viewModuleHelper = new ViewModuleHelper(context);
		//elementUtilBuilder = new ElementUtilBuilder(context);
		elementHelperBuilder = new ElementHelperBuilder(context);
		elementConverterBuilder = new ElementConverterBuilder(context);
		elementValidatorBuilder = new ElementValidatorBuilder(context);
		elementDataManagerBuilder = new ElementDataManagerBuilder(context);
		elementEventManagerBuilder = new ElementEventManagerBuilder(context);
		elementPageManagerBuilder = new ElementPageManagerBuilder(context);
		elementRecordSectionBuilder = new ElementRecordSectionBuilder(context);
		elementSelectManagerBuilder = new ElementSelectManagerBuilder(context);
		elementServiceManagerBuilder = new ElementServiceManagerBuilder(context);
		elementInfoManagerBuilder = new ElementInfoManagerBuilder(context);
		elementListManagerBuilder = new ElementListManagerBuilder(context);
		elementListObjectBuilder = new ElementListObjectBuilder(context);
		elementTreeManagerBuilder = new ElementTreeManagerBuilder(context);
		elementTreeBuilderBuilder = new ElementTreeBuilderBuilder(context);
		elementWizardManagerBuilder = new ElementWizardManagerBuilder(context);

		dataModelXHTMLBuilder = new DataModelXHTMLBuilder(context);
		servletListenerBuilder = new ServletListenerBuilder(context);
		this.context = context;
	}
	
//	public void initialize(Project project) throws Exception {
//		//ModuleFactory moduleFactory = new ModuleFactory();
//		//Module module = moduleFactory.createWarModule();
//		List<Module> modules = ProjectUtil.getViewModules(project);
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = iterator.next();
//			initialize(project, module);
//		}
//	}
	
	public void initialize(Project project, Module module) throws Exception {
		viewModuleHelper.initialize(project, module);
		dataModelXHTMLBuilder.initialize(project, module);
		context.initialize(project, module);
	}

	public void build(Project project, Module module) throws Exception {
		dataModelXHTMLBuilder.build(project, module);
	}

	public Information buildElementPanelXhtmlFile() throws Exception {
		return null;
	}

	public ModelClass buildServletListenerClass(Project project, Module module) throws Exception {
		return servletListenerBuilder.buildClass(module);
	}

//	public List<ModelClass> buildElementUtilClasses(Project project, Module module) throws Exception {
//		return elementUtilBuilder.buildClasses(project, module);
//	}

	public Collection<ModelClass> buildElementHelperClasses(Project project, Module module) throws Exception {
		return elementHelperBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementConverterClasses(Project project, Module module) throws Exception {
		return elementConverterBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementValidatorClasses(Project project, Module module) throws Exception {
		return elementValidatorBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementDataManagerClasses(Project project, Module module) throws Exception {
		return elementDataManagerBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementEventManagerClasses(Project project, Module module) throws Exception {
		return elementEventManagerBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementPageManagerClasses(Project project, Module module) throws Exception {
		return elementPageManagerBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementRecordSectionClasses(Project project, Module module) throws Exception {
		return elementRecordSectionBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementSelectManagerClasses(Project project, Module module) throws Exception {
		return elementSelectManagerBuilder.buildClasses(project, module);
	}
	
	public Collection<ModelClass> buildElementServiceManagerClasses(Project project, Module module) throws Exception {
		return elementServiceManagerBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementInfoManagerClasses(Project project, Module module) throws Exception {
		return elementInfoManagerBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementListManagerClasses(Project project, Module module) throws Exception {
		return elementListManagerBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementListObjectClasses(Project project, Module module) throws Exception {
		return elementListObjectBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementTreeManagerClasses(Project project, Module module) throws Exception {
		return elementTreeManagerBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementTreeBuilderClasses(Project project, Module module) throws Exception {
		return elementTreeBuilderBuilder.buildClasses(project, module);
	}

	public Collection<ModelClass> buildElementWizardManagerClasses(Project project, Module module) throws Exception {
		return elementWizardManagerBuilder.buildClasses(project, module);
	}


}
