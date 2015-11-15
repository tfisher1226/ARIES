package nam.ui.src.main.webapp.WEBINF.tags;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class TaglibXMLGenerator extends AbstractFileGenerator {

	public TaglibXMLGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName();
		String projectNameCapped = context.getProjectNameCapped();
		String projectDomain = context.getProjectDomain();

		setSourceFile("aries.taglib.xml");
		setTargetFile("aries.taglib.xml");
		//setTargetFile(projectName+".taglib.xml");
		//setTargetFile(projectDomain+".taglib.xml");
		setSourceFolder("src/main/webapp/WEB-INF/tags");
		setTargetFolder("src/main/webapp/WEB-INF/tags");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1Domain", projectDomain);
		generateFile(filterSet);
	}

}
