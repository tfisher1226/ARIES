package nam.ear.src.main.application.METAINF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Configuration;
import nam.model.Dependency;
import nam.model.DependencyType;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ConfigurationUtil;
import nam.model.util.ProjectUtil;
import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ApplicationXMLBuilder extends AbstractFileBuilder {

	public ApplicationXMLBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public ModelFile buildFile() throws Exception {
		ModelFile modelFile = createFile(context.getMainApplicationFolder() + "/META-INF", "application.xml");
		modelFile.setVersion("5");
		modelFile.setTextContent(getFileContent(modelFile, false));
		return modelFile;
	}
	
	public String getFileContent(ModelFile modelFile, boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put(generateXmlOpen(modelFile));
		buf.put(generateXmlContent(modelFile));
		buf.put(generateXmlClose(modelFile));
		return buf.get();
	}
	
	protected String generateXmlOpen(ModelFile modelFile) {
		Buf buf = new Buf();
		if (modelFile.getVersion().equals("5")) {
			buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buf.putLine();
			buf.putLine("<application");
			buf.putLine("	version=\"5\""); 
			buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/javaee\""); 
			buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			buf.putLine("	xmlns:application=\"http://java.sun.com/xml/ns/javaee/application_5.xsd\"");
			buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/application_5.xsd\">");
		}
		return buf.get();
	}

	protected String generateXmlContent(ModelFile modelFile) {
		Project project = context.getProject();
		Application application = context.getApplication();
		Module earModule = ApplicationUtil.getEarModule(application);
		Buf buf = new Buf();
		buf.putLine();
		buf.putLine1("<description>"+earModule.getName()+"</description>");
		buf.putLine1("<display-name>"+earModule.getName()+"</display-name>");
		
		Set<Module> modules = new LinkedHashSet<Module>();
		modules.addAll(getModuleDependencies(ProjectUtil.getSubProjects(project)));
		modules.addAll(getModuleDependencies(project));
		buf.put(generateModuleDescripters(modules));
		
		//buf.put(generateWebModuleDescripter(application));
		//buf.put(generateEJBModuleDescripters(application));
		//buf.put(generateJavaModuleDescriptersForProjectModules(project));
		//buf.put(generateJavaModuleDescriptersForProjectModules(ProjectUtil.getSubProjects(project)));
		//buf.put(generateJavaModuleDescriptersForApplicationModules(application));
		
		buf.put(generateJavaModuleDescriptersForDependencies(ApplicationUtil.getDependencies(application)));
		buf.put(generateEJBModuleDescripters(application.getConfiguration()));
		buf.putLine1("");
		buf.putLine1("<library-directory>lib</library-directory>");
		return buf.get();
	}

	protected Collection<Module> getModuleDependencies(List<Project> subProjects) {
		Set<Module> modules = new LinkedHashSet<Module>();
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			modules.addAll(getModuleDependencies(project, true));
		}
		return modules;
	}

	protected Collection<Module> getModuleDependencies(Project project) {
		return getModuleDependencies(project, false);
	}

	protected Collection<Module> getModuleDependencies(Project project, boolean isHostedSubProject) {
		Collection<Module> modules = new LinkedHashSet<Module>();
		Collection<Module> projectModules = ProjectUtil.getDeployableModules(project, !isHostedSubProject, true);

		modules.addAll(projectModules);
		modules.addAll(getModuleDependencies(project, isHostedSubProject, true));
		modules.addAll(getModuleDependencies(project, isHostedSubProject, false));
		return modules;
	}

	protected Collection<Module> getModuleDependencies(Project project, boolean isHostedSubProject, boolean addHostedApplications) {
		Collection<Module> modules = new LinkedHashSet<Module>();
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			boolean isHomeApplication = ProjectUtil.isHomeApplication(project, application);
			boolean isMainApplication = !isHostedSubProject && isHomeApplication;
			boolean isHostedApplication = isHostedSubProject || isHomeApplication;
			if (addHostedApplications && !isHostedApplication)
				continue;
			if (!addHostedApplications && isHostedApplication)
				continue;
			Collection<Module> applicationModules = ProjectUtil.getDeployableModules(application, isMainApplication, isHostedApplication);
			modules.addAll(applicationModules);
		}
		return modules;
	}
	
	protected String generateModuleDescripters(Set<Module> modules) {
		Buf buf = new Buf();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			switch (module.getType()) {
			case MODEL:
				buf.putLine1("");
				buf.putLine1("<module>");
				buf.putLine1("	<java>"+module.getArtifactId()+"-"+module.getVersion()+".jar</java>");
				buf.putLine1("</module>");
				break;
			case CLIENT:
			case SERVICE:
			case DATA:
				buf.putLine1("");
				buf.putLine1("<module>");
				buf.putLine1("	<ejb>"+module.getArtifactId()+"-"+module.getVersion()+".jar</ejb>");
				buf.putLine1("</module>");
				break;
			case VIEW:
				buf.putLine1("");
				buf.putLine1("<module>");
				buf.putLine1("	<web>");
				buf.putLine1("		<web-uri>"+module.getArtifactId()+"-"+module.getVersion()+".war</web-uri>");
				buf.putLine1("		<context-root>"+context.getApplication().getContextRoot()+"</context-root>");
				buf.putLine1("	</web>");
				buf.putLine1("</module>");
				break;
			default:
				break;
			}
		}
		return buf.get();
	}

//	protected String generateWebModuleDescripter(Application application) {
//		Buf buf = new Buf();
//		Set<Module> modules = ApplicationUtil.getViewModules(application);
//		Assert.isTrue(modules.size() <= 1, "Only one web module can be specified");
//		String contextRoot = application.getContextRoot();
//		if (StringUtils.isEmpty(contextRoot))
//			contextRoot = application.getArtifactId();
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = (Module) iterator.next();
//			buf.putLine();
//			buf.putLine1("<module>");
//			buf.putLine1("	<web>");
//			buf.putLine1("		<web-uri>"+module.getArtifactId()+"-"+module.getVersion()+".war</web-uri>");
//			buf.putLine1("		<context-root>"+contextRoot+"</context-root>");
//			buf.putLine1("	</web>");
//			buf.putLine1("</module>");
//		}
//		return buf.get();
//	}
//
//	protected String generateEJBModuleDescripters(Application application) {
//		Buf buf = new Buf();
//		Set<Module> modules = ApplicationUtil.getServiceModules(application);
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = (Module) iterator.next();
//			buf.putLine();
//			buf.putLine1("<module>");
//			buf.putLine1("	<ejb>"+module.getArtifactId()+"-"+module.getVersion()+".jar</ejb>");
//			buf.putLine1("</module>");
//		}
//		return buf.get();
//	}

	protected String generateEJBModuleDescripters(Configuration configuration) {
		Buf buf = new Buf();
		List<Dependency> dependencies = ConfigurationUtil.getTransitiveDependencies(configuration);
		Iterator<Dependency> iterator2 = dependencies.iterator();
		while (iterator2.hasNext()) {
			Dependency dependency = iterator2.next();
			if (dependency.getType() == DependencyType.EJB) {
				buf.putLine();
				buf.putLine1("<module>");
				buf.putLine1("	<ejb>"+dependency.getArtifactId()+"-"+dependency.getVersion()+".jar</ejb>");
				buf.putLine1("</module>");
			}
		}
		return buf.get();
	}

	protected String generateJavaModuleDescriptersForProjectModules(Project project) {
		Set<Module> modules = ProjectUtil.getProjectModules(project);
		return generateJavaModuleDescriptersForModules(modules);
	}
	
	protected String generateJavaModuleDescriptersForProjectModules(List<Project> subProjects) {
		Buf buf = new Buf();
		Iterator<Project> iterator = subProjects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			buf.put(generateJavaModuleDescriptersForProjectModules(project));
		}
		return buf.get();
	}

	protected String generateJavaModuleDescriptersForApplicationModules(Application application) {
		List<Module> modules = new ArrayList<Module>();
		modules.addAll(ApplicationUtil.getClientModules(application));
		modules.addAll(ApplicationUtil.getModelModules(application));
		modules.addAll(ApplicationUtil.getDataModules(application));
		return generateJavaModuleDescriptersForModules(modules);
	}

	protected String generateJavaModuleDescriptersForModules(Collection<Module> modules) {
		Buf buf = new Buf();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getType() == ModuleType.POM)
				continue;
			if (module.getType() == ModuleType.TEST)
				continue;
			buf.putLine();
			buf.putLine1("<module>");
			buf.putLine1("	<java>"+module.getArtifactId()+"-"+module.getVersion()+".jar</java>");
			buf.putLine1("</module>");
		}
		return buf.get();
	}
	
	protected String generateJavaModuleDescriptersForDependencies(List<Dependency> dependencies) {
		Buf buf = new Buf();
		Iterator<Dependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			if (dependency.getVersion() == null)
				throw new RuntimeException("CODE PROBLEM");
			buf.putLine();
			buf.putLine1("<module>");
			buf.putLine1("	<java>"+dependency.getArtifactId()+"-"+dependency.getVersion()+".jar</java>");
			buf.putLine1("</module>");
		}
		return buf.get();
	}

	protected String generateXmlClose(ModelFile modelFile) {
		return "</application>";
	}
	
}
