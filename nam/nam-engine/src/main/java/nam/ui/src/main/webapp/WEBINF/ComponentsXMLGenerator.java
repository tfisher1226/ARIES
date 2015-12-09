package nam.ui.src.main.webapp.WEBINF;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.client.ClientLayerHelper;
import nam.model.Application;
import nam.model.Module;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Service;
import nam.model.Unit;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;

import org.apache.tools.ant.types.FilterSet;
import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.Buf;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class ComponentsXMLGenerator extends AbstractFileGenerator {

	public ComponentsXMLGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate() throws Exception {
		Project project = context.getProject();
		String projectName = context.getProjectName();
		String persistenceDeclarations = getPersistenceDeclarations();
		String serviceDeclarations = getServiceDeclarations();

		setSourceFile("components.xml");
		setTargetFile("components.xml");
		setSourceFolder("src/main/webapp/WEB-INF");
		setTargetFolder("src/main/webapp/WEB-INF");

		Application hostingApplication = ProjectUtil.getApplicationByNamespace(project, project.getNamespace());
		Assert.notNull(hostingApplication, "Hosting application not found for: "+project.getNamespace());
		
		Module earModule = ApplicationUtil.getEarModule(hostingApplication);
		Assert.notNull(earModule, "EAR module for hosting application not found");
		String earName = earModule.getName();
		
		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("${template1}", projectName);
		filterSet.addFilter("${template1_earName}", earName);
		filterSet.addFilter("${template1_service_context}", projectName + ".service");
		filterSet.addFilter("${template1_service_package}", projectName + ".service");
		filterSet.addFilter("<!-- ${template1_persistence_declarations} -->", persistenceDeclarations);
		filterSet.addFilter("<!-- ${template1_service_declarations} -->", serviceDeclarations);
		generateFile(filterSet);
	}

	protected String getPersistenceDeclarations() {
		Project project = context.getProject();

		Buf buf = new Buf();
		Set<Module> dataModules = ModuleUtil.getDataModules(project);
		Iterator<Module> iterator = dataModules.iterator();
		while (iterator.hasNext()) {
			Module dataModule = iterator.next();
			Persistence persistence = ModuleUtil.getPersistenceBlock(dataModule);
			Collection<Unit> units = PersistenceUtil.getUnits(persistence);
			buf.put(getPersistenceDeclarations(units));
		}
		
		return buf.get();
	}
	
	protected String getPersistenceDeclarations(Collection<Unit> units) {
		Buf buf = new Buf();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getRef() != null)
				continue;
			String unitName = NameUtil.uncapName(unit.getName());
			
			if (buf.length() == 0) {
				buf.putLine1("");
				buf.putLine1("<!-- Persistence-unit entity manager for: "+unitName+" -->");
			}
			buf.putLine1("<persistence:managed-persistence-context name=\""+unitName+".entityManager\" persistence-unit-jndi-name=\"java:/"+unitName+"EntityManagerFactory\" auto-create=\"true\" />");
		}
		return buf.get();
	}

	protected String getServiceDeclarations() {
		Buf buf = new Buf();
		Project project = context.getProject();
		//buf.put(getServiceDeclarations(ProjectUtil.getSubProjects(project)));
		buf.put(getServiceDeclarations(project));
		return buf.get();
	}

	protected String getServiceDeclarations(List<Project> projects) {
		Buf buf = new Buf();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			buf.put(getServiceDeclarations(project));
		}
		return buf.get();
	}

	protected String getServiceDeclarations(Project project) {
		Buf buf = new Buf();
		Set<Module> serviceModules = ProjectUtil.getServiceModules(project);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			buf.put(getServiceDeclarations(project, module));
		}
		return buf.get();
	}
	
	protected String getServiceDeclarations(Project project, Module module) {
		Buf buf = new Buf();
		List<Service> services = ModuleUtil.getServices(module);
		ServiceUtil.sortServices(services);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			String clientContextName = ClientLayerHelper.getClientContextName(service);
			String clientQualifiedName = ClientLayerHelper.getClientQualifiedClassName(service);
			buf.putLine1("<component name=\""+clientContextName+"\" class=\""+clientQualifiedName+"\" scope=\"session\" auto-create=\"true\" />");
		}
		
		if (buf.length() > 0) {
			buf.putLine1("");
			buf.insertLine("	<!-- Remote service proxy components for module: "+module.getName()+" -->");
		}
		return buf.get();
	}

}
