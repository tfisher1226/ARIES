package nam.ui.src.main.webapp.WEBINF;

import nam.ProjectLevelHelper;
import nam.model.Module;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class FacesConfigXMLGenerator extends AbstractFileGenerator {

	public FacesConfigXMLGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName();
		Module module = context.getModule();
		String packageName = ProjectLevelHelper.getPackageName(module.getNamespace());

		setSourceFile("faces-config.xml");
		setTargetFile("faces-config.xml");
		setSourceFolder("src/main/webapp/WEB-INF");
		setTargetFolder("src/main/webapp/WEB-INF");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1_view_package}", packageName + ".ui");
		filterSet.addFilter("${template1}", projectName);
		generateFile(filterSet);
	}

}
