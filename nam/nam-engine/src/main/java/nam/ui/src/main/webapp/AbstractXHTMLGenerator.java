package nam.ui.src.main.webapp;

import nam.model.Module;
import nam.model.Project;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public abstract class AbstractXHTMLGenerator extends AbstractFileGenerator {

	public AbstractXHTMLGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void initialize(Project project, Module module) throws Exception {
		//nothing for now
	}
	
	public void generate(Project project, Module module) throws Exception {
		//nothing for now
	}
	

	/*
	 * Template file approach:
	 * -----------------------
	 */
	
	public void generate(String fileName) throws Exception {
		generate(fileName, null);
	}
	
	public void generate(String fileName, FilterSet filters) throws Exception {
		String projectName = context.getProjectName();
		String projectNameCapped = context.getProjectNameCapped();
		String projectNameUpper = context.getProjectNameUpper();
		String projectDomain = context.getProjectDomain();

		setSourceFile(fileName);
		setTargetFile(fileName);
		setSourceFolder("src/main/webapp");
		setTargetFolder("src/main/webapp");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1", projectName);
		filterSet.addFilter("Template1", projectNameCapped);
		filterSet.addFilter("TEMPLATE1", projectNameUpper);
		filterSet.addFilter("template1Domain", projectDomain);
		if (filters != null)
			filterSet.addConfiguredFilterSet(filters);
		generateFile(filterSet);
	}

}
