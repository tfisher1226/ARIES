package nam.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Channel;
import nam.model.Link;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Role2;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.LinkUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.service.ServiceLayerHelper;

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
	
	public List<String> initializePomProfilesOLD() {
		String mainClass = "org.model1.BankLoanQuoteClientMain";

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
		Map<String, Link> linkMap = new HashMap<String, Link>();
		Application application = context.getApplication();

		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module projectModule = moduleIterator.next();
			dependencies.addAll(getModelModuleDependencies(projectModule));
			dependencies.add(createPomDependency(projectModule));
			dependencies.add(createPomDependency(projectModule, "test", "test-jar"));
		}
		
		if (application != null) {
			List<Channel> channels = ApplicationUtil.getChannels(application);
			dependencies.addAll(createPomDependenciesFromChannels(channels));
		}

		//use this to avoid adding the same dependency twice 
		Set<ModelDependency> dependencySet = new LinkedHashSet<ModelDependency>();

		//first check for existence of any links
		switch (module.getType()) {
		case CLIENT:
			//LinkUtil.addAll(linkMap, ModuleUtil.getSendLinks(module));
			List<Service> services = ModuleUtil.getServices(module);
			Iterator<Service> iterator = services.iterator();
			while (iterator.hasNext()) {
				Service service = iterator.next();
				Set<Link> serviceLinks = getLinks(service);
				if (serviceLinks.size() > 0) {
					LinkUtil.addAll(linkMap, serviceLinks);
				} else {
					if (service.getProcess() != null) {
						String processName = ServiceLayerHelper.getProcessName(service.getProcess());
						Process process = BPELRuntimeCache.INSTANCE.getProcessByName(processName);
						if (process != null && !processes.contains(process)) {
							List<PartnerLink> partnerLinks = process.getPartnerLinks().getChildren();
							Set<Link> linksFromProcess = LinkUtil.createLinks(partnerLinks);
							LinkUtil.addAll(linkMap, linksFromProcess);
							processes.add(process);
						}
					}
				}
			}
			
			String artifactId = module.getArtifactId();
			String groupId = module.getGroupId();
			String version = module.getVersion();
			
			Assert.isTrue(artifactId.endsWith("-client"), "Unrecognized module artifactId: "+artifactId);
			String modelDependencyArtifactId = artifactId.replace("-client", "-model");
			if (ProjectUtil.getModule(context.getProject(), modelDependencyArtifactId) != null) {
				addDependency(dependencies, dependencySet, createPomDependency(groupId, modelDependencyArtifactId, version));
				addDependency(dependencies, dependencySet, createPomDependency(groupId, modelDependencyArtifactId, version, "test", "test-jar"));
			}
		}

		//next accumulate dependencies from modules that we invoke
		Iterator<Link> iterator = linkMap.values().iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
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
				Map<String, PartnerLink> partnerLinkMap = context.getPartnerLinkMap(); 
				PartnerLink partnerLink = partnerLinkMap.get(link.getName());
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

//				Assert.isTrue(artifactId.endsWith("-client"), "Unrecognized module artifactId: "+artifactId);
//				String modelDependencyArtifactId = artifactId.replace("-client", "-model");
//				if (ProjectUtil.getModule(context.getProject(), modelDependencyArtifactId) != null) {
//					ModelDependency dependency = createPomDependency(groupId, modelDependencyArtifactId, version);
//					addDependency(dependencies, dependencySet, dependency);
//				}
			}
		}
		
		//dependencies.addAll(createPomApplicationDependencies(ModuleType.CLIENT));
		//dependencies.addAll(createPomApplicationDependencies("model"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-client-layer", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "aries-platform-client-layer-test", "0.0.1-SNAPSHOT", "test", "pom"));
		//dependencies.add(createPomDependency("org.aries", "tx-manager-api", "0.0.1-SNAPSHOT", "provided", "pom"));
		dependencies.add(createPomDependency("org.aries.pom", "testing", "0.0.1-SNAPSHOT", "provided", "pom"));
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
			if (channel.getClients().contains(ourRole)) {
				Iterator<String> iterator2 = channel.getServices().iterator();
				while (iterator2.hasNext()) {
					String roleName = iterator2.next();
					Application application = ProjectUtil.getApplicationByName(context.getProject(), roleName);
					Module module = ModuleUtil.getClientModule(application.getModules());

					String groupId = module.getGroupId();
					String artifactId = module.getArtifactId();
					String version = module.getVersion();

					Assert.isTrue(artifactId.endsWith("-client"), "Unrecognized module artifactId: "+artifactId);
					String modelDependencyArtifactId = artifactId.replace("-client", "-model");
					if (ProjectUtil.getModule(context.getProject(), modelDependencyArtifactId) != null) {
						ModelDependency pomDependency = createPomDependency(groupId, modelDependencyArtifactId, version);
						if (!dependencies.contains(pomDependency)) {
							dependencies.add(pomDependency);
						}
					}

					ModelDependency pomDependency = createPomDependency(module);
					if (!dependencies.contains(pomDependency)) {
						dependencies.add(pomDependency);
					}
				}
			}
		}
		return dependencies;
	}
	
}
