package nam.ui.src.main.resources;

import nam.model.Application;
import nam.model.Module;
import nam.model.util.ApplicationUtil;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class ComponentsPropertiesGenerator extends AbstractFileGenerator {

	public ComponentsPropertiesGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName();
		String projectNameCapped = context.getProjectNameCapped();
		String projectDomain = context.getProjectDomain();
		
		Application application = context.getApplication();
		Module earModule = ApplicationUtil.getEarModule(application);

		setSourceFile("components.properties");
		setTargetFile("components.properties");
		setSourceFolder("src/main/resources");
		setTargetFolder("src/main/resources");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1", earModule.getName());
		//filterSet.addFilter("Template1", projectNameCapped);
		//filterSet.addFilter("template1Domain", projectDomain);
		generateFile(filterSet);
	}

}
