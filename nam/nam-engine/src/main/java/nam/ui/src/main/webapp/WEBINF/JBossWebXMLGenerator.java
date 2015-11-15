package nam.ui.src.main.webapp.WEBINF;

import org.apache.tools.ant.types.FilterSet;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class JBossWebXMLGenerator extends AbstractFileGenerator {

	public JBossWebXMLGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		String projectName = context.getProjectName();
		String projectNameCapped = context.getProjectNameCapped();

		setSourceFile("jboss-web.xml");
		setTargetFile("jboss-web.xml");
		setSourceFolder("src/main/webapp/WEB-INF");
		setTargetFolder("src/main/webapp/WEB-INF");

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1", projectName);
		filterSet.addFilter("Template1", projectNameCapped);
		generateFile(filterSet);
	}

}
