package nam.ui.src.main.webapp.WEBINF;

import nam.model.Application;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ProjectUtil;

import org.apache.tools.ant.types.FilterSet;
import org.aries.Assert;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class PagesXMLGenerator extends AbstractFileGenerator {

	public PagesXMLGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		Project project = context.getProject();
		String projectName = context.getProjectName();
		//String projectNameCapped = context.getProjectNameCapped();

		setSourceFile("pages.xml");
		setTargetFile("pages.xml");
		setSourceFolder("src/main/webapp/WEB-INF");
		setTargetFolder("src/main/webapp/WEB-INF");

		Application hostingApplication = ProjectUtil.getApplicationByNamespace(project, project.getNamespace());
		Assert.notNull(hostingApplication, "Hosting application not found");
		
		Module earModule = ApplicationUtil.getEarModule(hostingApplication);
		Assert.notNull(earModule, "EAR module for hosting application not found");
		String eventDomainName = earModule.getGroupId().replace("-", ".").replace("_", ".");
		
		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1}", projectName);
		filterSet.addFilter("${template1_event_domain}", eventDomainName);
		generateFile(filterSet);
	}

}
