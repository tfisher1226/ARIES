package nam.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Group;
import nam.model.Interaction;
import nam.model.Interactor;
import nam.model.Link;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Role2;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.LinkUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;

import org.aries.Assert;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.wst.wsdl.PortType;

import aries.bpel.BPELRuntimeCache;
import aries.codegen.configuration.AbstractPOMBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelDependency;
import aries.generation.model.ModelPom;


public class PomXMLBuilder extends AbstractPOMBuilder {

	public PomXMLBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelPom build() throws Exception {
		ModelPom modelPom = createPom(context.getProject(), context.getModule());
		return modelPom;
	}
	
	protected ModelPom createParentPom(Module module, ModelPom pom) throws Exception {
		switch (module.getLevel()) {
		case PROJECT_LEVEL: return super.createProjectPomAsParent(pom);
		case GROUP_LEVEL: return super.createProjectAppPomAsParent(pom);
		case APPLICATION_LEVEL: return super.createApplicationPomAsParent(pom);
		default: return null;
		}
	}

//	public List<String> initializePomFilters() {
//		List<String> filters = new ArrayList<String>();
//		filters.add("src/main/resources/maven/server.properties");
//		return filters;
//	}
	
	public List<String> initializePomPlugins() {
		List<String> plugins = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine3("<plugin>");
		buf.putLine3("	<artifactId>maven-jar-plugin</artifactId>");
		buf.putLine3("	<configuration>");
		buf.putLine3("		<includes>");
		buf.putLine3("			<include>**/*.class</include>");
		buf.putLine3("			<include>**/*.xml</include>");
		buf.putLine3("			<include>**/*.properties</include>");
		buf.putLine3("			<include>**/*.launch</include>");
		buf.putLine3("		</includes>");
		buf.putLine3("	</configuration>");
		buf.putLine3("</plugin>");
		plugins.add(buf.get());
		return plugins;
	}
	
	public List<String> initializePomProfiles() {
		List<String> profiles = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine2("<profile>");
		buf.putLine2("	<id>it</id>");
		buf.putLine2("	<build>");
		buf.putLine2("		<plugins>");
		buf.putLine2("			<plugin>");
		buf.putLine2("				<groupId>org.apache.maven.plugins</groupId>");
		buf.putLine2("				<artifactId>maven-failsafe-plugin</artifactId>");
		buf.putLine2("			</plugin>");
		buf.putLine2("		</plugins>");
		buf.putLine2("	</build>");
		buf.putLine2("</profile>");
		profiles.add(buf.get());
		return profiles;
	}

	public List<String> initializePomProfilesNOTYET() {
		String mainClass = "org.model1.BankLoanQuoteMain";

		List<String> profiles = new ArrayList<String>();
		Buf buf = new Buf();
		buf.putLine2("<profile>");
		buf.putLine2("<id>server</id>");
		buf.putLine2("	<build>");
		buf.putLine2("		<defaultGoal>test</defaultGoal>");
		buf.putLine2("		<plugins>");
		buf.putLine2("			<plugin>");
		buf.putLine2("				<groupId>org.codehaus.mojo</groupId>");
		buf.putLine2("				<artifactId>exec-maven-plugin</artifactId>");
		buf.putLine2("				<executions>");
		buf.putLine2("					<execution>");
		buf.putLine2("						<phase>test</phase>");
		buf.putLine2("						<goals>");
		buf.putLine2("							<goal>java</goal>");
		buf.putLine2("						</goals>");
		buf.putLine2("						<configuration>");
		buf.putLine2("							<mainClass>"+mainClass+"</mainClass>");
		buf.putLine2("							<classpathScope>runtime</classpathScope>");
		buf.putLine2("						</configuration>");
		buf.putLine2("					</execution>");
		buf.putLine2("				</executions>");
		buf.putLine2("			</plugin>");
		buf.putLine2("		</plugins>");
		buf.putLine2("	</build>");
		buf.putLine2("</profile>");
		profiles.add(buf.get());
		return profiles;
	}

	public Set<ModelDependency> initializePomDependencies(Module module) throws Exception {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		Set<Process> processes = new HashSet<Process>();

		Project project = context.getProject();
		Application application = context.getApplication();

		String namespaceUri = application.getNamespace();
		Namespace namespace = context.getNamespaceByUri(namespaceUri);
		//Persistence persistence = ExtensionsUtil.getPersistenceBlock(project, namespaceUri);
		Persistence persistence = module.getPersistence();

//		if (module.getName().equalsIgnoreCase("bookshop2-shipper-service"))
//			System.out.println();
		
		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module projectModule = moduleIterator.next();
			dependencies.addAll(getModelModuleDependencies(projectModule));
			if (persistence != null)
				dependencies.addAll(getDataModuleDependencies(projectModule));
			dependencies.add(createPomDependency(projectModule));
			dependencies.add(createPomDependency(projectModule, "test", "test-jar"));
		}

		dependencies.addAll(createPomApplicationDependencies(ModuleType.MODEL));
		//dependencies.addAll(getModelModuleDependencies(context.getProject()));

		List<ModelDependency> dataModuleDependencies = null;
		List<ModelDependency> modelModuleDependencies = null;

		//create dependencies for our application default data module (if one exists)
		if (persistence != null) {
			dataModuleDependencies = createPomDependenciesForDataModule(namespace);
			Iterator<ModelDependency> iterator = dataModuleDependencies.iterator();
			while (iterator.hasNext()) {
				ModelDependency modelDependency = iterator.next();
				modelDependency.setGroupId(application.getGroupId());
				modelDependency.setArtifactId(application.getArtifactId() + "-data");
				if (!modelDependency.getScope().equals("test"))
					modelDependency.setType("ejb");
				dependencies.add(modelDependency);
			}
		}
		
		if (namespace != null) {
			//Information information = application.getInformation();
			//Information information2 = module.getInformation();
			List<Namespace> namespaceImports = namespace.getImports();
			Iterator<Namespace> namespaceIterator = namespaceImports.iterator();
			while (namespaceIterator.hasNext()) {
				Namespace importedNamespace = namespaceIterator.next();
				modelModuleDependencies = createPomDependenciesForModelModule(importedNamespace);
				dependencies.addAll(modelModuleDependencies);
			}
		}
		
//		//create dependency for our own model-module (if any)
//		String serviceDependencyArtifactId = module.getArtifactId();
//		String modelDependencyArtifactId = serviceDependencyArtifactId.replace("-service", "-model");
//		Module modelModule = ModuleUtil.getModule(project, module.getGroupId(), modelDependencyArtifactId);
//		if (modelModule != null) {
//			ModelDependency dependency = new ModelDependency();
//			dependency.setGroupId(module.getGroupId());
//			dependency.setArtifactId(modelDependencyArtifactId);
//			dependency.setVersion(module.getVersion());
//			dependency.setScope("provided");
//			dependency.setType("jar");
//			dependencies.add(dependency);
//			
//			dependency = new ModelDependency();
//			dependency.setGroupId(module.getGroupId());
//			dependency.setArtifactId(modelDependencyArtifactId);
//			dependency.setVersion(module.getVersion());
//			dependency.setScope("test");
//			dependency.setType("test-jar");
//			dependencies.add(dependency);
//		}
		
		//create dependency for our own client-module
		ModelDependency dependency = new ModelDependency();
		dependency.setGroupId(module.getGroupId());
		//TODO make sure to get the correct artifactId here:
		String serviceDependencyArtifactId = module.getArtifactId();
		String clientDependencyArtifactId = serviceDependencyArtifactId.replace("-service", "-client");
		dependency.setArtifactId(clientDependencyArtifactId);
		dependency.setVersion(module.getVersion());
		dependency.setScope("provided");
		dependency.setType("ejb");
		dependencies.add(dependency);

		//create dependency for our own client-module test-jar
		dependency = new ModelDependency();
		dependency.setGroupId(module.getGroupId());
		//TODO make sure to get the correct artifactId here:
		serviceDependencyArtifactId = module.getArtifactId();
		clientDependencyArtifactId = serviceDependencyArtifactId.replace("-service", "-client");
		dependency.setArtifactId(clientDependencyArtifactId);
		dependency.setVersion(module.getVersion());
		dependency.setScope("test");
		dependency.setType("test-jar");
		dependencies.add(dependency);

//		//create dependency for our own data-module (if any)
//		serviceDependencyArtifactId = module.getArtifactId();
//		String dataDependencyArtifactId = serviceDependencyArtifactId.replace("-service", "-data");
//		Module dataModule = ModuleUtil.getModule(project, module.getGroupId(), dataDependencyArtifactId);
//		if (dataModule != null) {
//			dependency = new ModelDependency();
//			dependency.setGroupId(module.getGroupId());
//			dependency.setArtifactId(dataDependencyArtifactId);
//			dependency.setVersion(module.getVersion());
//			dependency.setScope("provided");
//			dependency.setType("jar");
//			dependencies.add(dependency);
//		}
//		
//		if (dataModule != null) {
//			//create dependency for our own data-module test-jar (if any)
//			dependency = new ModelDependency();
//			dependency.setGroupId(module.getGroupId());
//			//TODO make sure to get the correct artifactId here:
//			serviceDependencyArtifactId = module.getArtifactId();
//			dataDependencyArtifactId = serviceDependencyArtifactId.replace("-service", "-data");
//			dependency.setArtifactId(dataDependencyArtifactId);
//			dependency.setVersion(module.getVersion());
//			dependency.setScope("test");
//			dependency.setType("test-jar");
//			dependencies.add(dependency);
//		}
		
		//first check for existence of any links
		List<Link> links = new ArrayList<Link>();
		switch (module.getType()) {
		case SERVICE: {
			List<Service> services = ModuleUtil.getServices(module);
			Iterator<Service> iterator = services.iterator();
			while (iterator.hasNext()) {
				Service service = iterator.next();
				ProjectUtil.setProject(project);
				Set<Link> serviceLinks = getLinks(service);
				if (serviceLinks.size() > 0) {
					links.addAll(serviceLinks);
				} else {
					if (service.getProcess() != null) {
						String processName = ServiceLayerHelper.getProcessName(service.getProcess());
						Process process = BPELRuntimeCache.INSTANCE.getProcessByName(processName);
						if (process != null && !processes.contains(process)) {
							List<PartnerLink> partnerLinks = process.getPartnerLinks().getChildren();
							Set<Link> linksFromProcess = LinkUtil.createLinks(partnerLinks);
							links.addAll(linksFromProcess);
							processes.add(process);
						}
					}
				}
			}
			break;
		}
		
		case CLIENT:
			links.addAll(ModuleUtil.getSendLinks(module));
		}

		//next accumulate dependencies from modules that we invoke
		Set<ModelDependency> dependencySet = new LinkedHashSet<ModelDependency>();
		Iterator<Link> iterator = links.iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
			
			//Role roleTMP = new Role();
			//roleTMP.setName(link.getRole());
			//link.getRoles().add(roleTMP);
			
			//String linkType = link.getType();
			//String roleName = link.getRole();
			List<Role2> roles = LinkUtil.getRoles(link);
			Iterator<Role2> roleIterator = roles.iterator();
			while (roleIterator.hasNext()) {
				Role2 role = roleIterator.next();
				String artifactId = module.getArtifactId();
				String groupId = module.getGroupId();
				String version = module.getVersion();

				//Map<String, PartnerLinkType> linkTypeMap = context.getPartnerLinkTypeMap(); 
				Map<String, PartnerLink> linkMap = context.getPartnerLinkMap(); 
				PartnerLink partnerLink = linkMap.get(link.getName());
				if (partnerLink != null) {
					PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
					Assert.notNull(partnerLinkType, "PartnerLinkType must exist");
					PortType portType = context.getPortType(partnerLinkType, role.getName());
					//QName portTypeQName = portType.getQName();
					//Definition definition = portType.getEnclosingDefinition();
					//String packageName = NameUtil.getPackageFromNamespace(portTypeQName.getNamespaceURI());
					//String className = portTypeQName.getLocalPart();
					Module portTypeModule = context.getModuleByPortType(portType);
					groupId = portTypeModule.getGroupId();
					artifactId = portTypeModule.getArtifactId();
					version = portTypeModule.getVersion();
				}
				
//				Assert.isTrue(artifactId.endsWith("-service"), "Unrecognized module artifactId: "+artifactId);
//				String modelDependencyArtifactId2 = artifactId.replace("-service", "-model");
//				String clientDependencyArtifactId2 = artifactId.replace("-service", "-client");
//				
//				if (ProjectUtil.getModule(project, modelDependencyArtifactId2) != null) {
//					ModelDependency dependency2 = createPomDependency(groupId, modelDependencyArtifactId2, version);
//					if (!dependencySet.contains(dependency2)) {
//						dependencySet.add(dependency2);
//						dependencies.add(dependency2);
//					}
//				}
//				ModelDependency dependency2 = createPomDependency(groupId, clientDependencyArtifactId2, version);
//				if (!dependencySet.contains(dependency2)) {
//					dependencySet.add(dependency2);
//					dependencies.add(dependency2);
//				}
			}
		}
		
		List<Service> services = ModuleUtil.getServices(module);
		List<Callback> callbacks = ModuleUtil.getOutgoingCallbacks(module);
		dependencies.addAll(createPomDependenciesForServices(services));
		dependencies.addAll(createPomDependenciesForCallbacks(callbacks));
		
		if (application != null) {
			List<Channel> channels = ApplicationUtil.getChannels(application);
			dependencies.addAll(createPomDependenciesFromChannels(channels));
		}
		
		//dependencies.addAll(createPomApplicationDependencies(ModuleType.MODEL));
		//dependencies.addAll(createPomApplicationDependencies(ModuleType.CLIENT));
		//dependencies.addAll(createPomApplicationDependencies(ModuleType.DATA));
		//dependencies.addAll(createPomApplicationDependencies("model"));
		//dependencies.addAll(createPomApplicationDependencies("client"));
		
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-service-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-service-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		//dependencies.add(createPomDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));
		//dependencies.add(createPomDependency("org.aries", "tx-manager-test-api", "0.0.1-SNAPSHOT", "test", "pom"));
		//dependencies.add(createPomDependency("org.aries", "event-client", "0.0.1-SNAPSHOT", "provided", "jar"));
		dependencies.add(createPomDependency("org.aries.pom", "testing", "0.0.1-SNAPSHOT", "provided", "pom"));

		//support for test dependencies
		//dependencies.add(createPomDependency("org.antlr", "antlr", "3.4", "test", "jar"));
		//dependencies.add(createPomDependency("org.antlr", "antlr-runtime", "3.4", "test", "jar"));
		//dependencies.add(createPomDependency("org.aries", "ariel-compiler", "0.0.1-SNAPSHOT", "test", "jar"));
		return dependencies;
	}

	protected Set<ModelDependency> createPomDependenciesForServices(List<Service> services) {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		
		Iterator<Service> serviceIterator = services.iterator();
		while (serviceIterator.hasNext()) {
			Service service = serviceIterator.next();
			List<Interactor> interactors = ServiceUtil.getInteractors(service);
			Iterator<Interactor> invokerIterator = interactors.iterator();
			while (invokerIterator.hasNext()) {
				Interactor invoker = invokerIterator.next();
				if (invoker.getInteraction() == Interaction.INVOKE) {
					Module serviceModule = getServiceModule(invoker);
					
					if (serviceModule != null) {
						ModelDependency dependency = new ModelDependency();
						dependency.setGroupId(serviceModule.getGroupId());
						//TODO make sure to get the correct artifactId here:
						String serviceDependencyArtifactId2 = serviceModule.getArtifactId();
						String clientDependencyArtifactId2 = serviceDependencyArtifactId2.replace("-service", "-client");
						dependency.setArtifactId(clientDependencyArtifactId2);
						dependency.setVersion(serviceModule.getVersion());
						dependency.setScope("provided");
						//TODO figure which of these:
						dependency.setType("ejb");
						//dependency.setType("jar");
						dependencies.add(dependency);

						dependency = new ModelDependency();
						dependency.setGroupId(serviceModule.getGroupId());
						dependency.setArtifactId(clientDependencyArtifactId2);
						dependency.setVersion(serviceModule.getVersion());
						dependency.setScope("test");
						dependency.setType("test-jar");
						dependencies.add(dependency);
					}
				}
			}
		}

		return dependencies;
	}

	protected Set<ModelDependency> createPomDependenciesForCallbacks(List<Callback> callbacks) {
		Set<ModelDependency> dependencies = new LinkedHashSet<ModelDependency>();
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			//TODO should we not use the callback here?
			Callback callback = iterator.next();

			Application application = context.getApplication();
			String artifactId = application.getArtifactId() + "-service";
			//String artifactId = callback.getDomain().replace(".", "-") + "-service";
			
			Module module = context.getModuleByArtifactId(artifactId);
			artifactId = artifactId .replace("-service", "-client");
			String groupId = module.getGroupId();
			String version = module.getVersion();
			dependencies.add(createPomDependency(groupId, artifactId, version, "provided", "ejb"));
			dependencies.add(createPomDependency(groupId, artifactId, version, "test", "test-jar"));
		}
		return dependencies;
	}
	
	protected List<ModelDependency> createPomDependenciesFromChannels(List<Channel> channels) {
		List<ModelDependency> dependencies = new ArrayList<ModelDependency>();
		String ourRole = context.getApplication().getName();
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			
//			if (channel.getClients().contains(ourRole)) {
//				Iterator<String> iterator2 = channel.getServices().iterator();
//				while (iterator2.hasNext()) {
//					String roleName = iterator2.next();
//					Application application = ProjectUtil.getApplicationByName(context.getProject(), roleName);
//					Module module = ModuleUtil.getServiceModule(application.getModules());
//					dependencies.add(createPomDependency(module));
//				}
//			}
			
			if (channel.getServices().contains(ourRole)) {
				Iterator<String> iterator2 = channel.getClients().iterator();
				while (iterator2.hasNext()) {
					String roleName = iterator2.next();
					Application application = ProjectUtil.getApplicationByName(context.getProject(), roleName);
					Module module = ModuleUtil.getClientModule(application.getModules());
					dependencies.add(createPomDependency(module));
				}
			}
		}
		return dependencies;
	}

	protected Module getServiceModule(Interactor interactor) {
		Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
		Module module = getServiceModuleFromApplications(applications, interactor);
		if (module != null)
			return module;
		List<Group> groups = ProjectUtil.getGroups(context.getProject());
		module = getServiceModuleFromGroups(groups, interactor);
		if (module != null)
			return module;
		//throw new RuntimeException("Remote service not found: "+interactor.getLink()+"."+interactor.getService());
		System.err.println("Remote service not found: "+interactor.getLink()+"."+interactor.getService());
		return null;
	}

	protected Module getServiceModuleFromGroups(List<Group> groups, Interactor interactor) {
		Iterator<Group> iterator = groups.iterator();
		while (iterator.hasNext()) {
			Group group = iterator.next();
			if (group.getName().equalsIgnoreCase(interactor.getChannel())) {
				Module module = getServiceModuleFromApplications(group.getApplications(), interactor, true);
				if (module != null)
					return module;
			}
		}
		return null;
	}

	protected Module getServiceModuleFromApplications(Collection<Application> applications, Interactor interactor) {
		return getServiceModuleFromApplications(applications, interactor, false);
	}
	
	protected Module getServiceModuleFromApplications(Collection<Application> applications, Interactor interactor, boolean overrideChannelVerification) {
		Iterator<Application> applicationIterator = applications.iterator();
		boolean applicationFound = false;
		boolean serviceFound = false;
		
		//TODO RESOLVE THIS OUT SOON
		String targetName = interactor.getName();
		if (targetName.endsWith("Group"))
			targetName = targetName.replace("Group", "");
		
		while (applicationIterator.hasNext()) {
			Application application = applicationIterator.next();
			if (application.getName().equalsIgnoreCase(targetName) || overrideChannelVerification) {
				applicationFound = true;
				
				Collection<Module> serviceModules = ApplicationUtil.getServiceModules(application);
				Iterator<Module> moduleIterator = serviceModules.iterator();
				while (moduleIterator.hasNext()) {
					Module module = moduleIterator.next();
					
					List<Service> services = ModuleUtil.getServices(module);
					Iterator<Service> serviceIterator = services.iterator();
					while (serviceIterator.hasNext()) {
						Service service = serviceIterator.next();
						if (service.getName().equalsIgnoreCase(interactor.getService())) {
							serviceFound = true;
							return module;
						}
					}
				}
			}
		}
		//if (!applicationFound)
		//	throw new RuntimeException("Remote application/group not found: "+invoker.getLink());
		//if (!serviceFound)
		//	throw new RuntimeException("Remote service not found: "+invoker.getLink()+"."+invoker.getService());
		return null;
	}
	
	protected Application findRemoteApplication(Interactor interactor) {
		Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
		Iterator<Application> applicationIterator = applications.iterator();
		while (applicationIterator.hasNext()) {
			Application application = applicationIterator.next();
			if (application.getName().equals(interactor.getLink())) {
				Collection<Service> services2 = ApplicationUtil.getServices(application);
				Iterator<Service> serviceIterator2 = services2.iterator();
				while (serviceIterator2.hasNext()) {
					Service service2 = serviceIterator2.next();
					if (service2.getName().equals(interactor.getService())) {
						return application;
					}
				}
			}
		}
		return null;
	}

}
