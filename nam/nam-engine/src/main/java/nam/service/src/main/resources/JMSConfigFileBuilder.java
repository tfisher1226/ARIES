package nam.service.src.main.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Channel;
import nam.model.JmsTransport;
import nam.model.Messaging;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ProjectUtil;
import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.JMSUtil;
import aries.codegen.util.JMSUtil.JMSDestination;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JMSConfigFileBuilder extends AbstractFileBuilder {
	
	private Map<String, JMSDestination> destinations;

	
	public JMSConfigFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public List<ModelFile> buildFiles() throws Exception {
		return buildFiles(false);
	}

	public List<ModelFile> buildForTest() throws Exception {
		return buildFiles(true);
	}

//	public List<ModelFile> buildFiles(boolean isTest) throws Exception {
//		Project project = context.getProject();
//		destinations = JMSUtil.buildJMSDestinations(project);
//		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
//		List<Messaging> blocks = ProjectUtil.getMessagingBlocks(project);
//		Iterator<Messaging> iterator = blocks.iterator();
//		while (iterator.hasNext()) {
//			Messaging block = iterator.next();
//			context.setMessaging(block);
//			String name = NameUtil.uncapName(block.getName());
//			ModelFile modelFile = buildFile(isTest, name+"-jms.xml");
//			modelFiles.add(modelFile);
//			context.setMessaging(null);
//		}
//		return modelFiles;
//	}

	public List<ModelFile> buildFiles(boolean isTest) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		modelFiles.addAll(buildRequiredJMSFiles(isTest));
		
		Project project = context.getProject();
		destinations = JMSUtil.buildJMSDestinations(project);
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		modelFiles.addAll(buildJMSFiles(isTest, messagingBlocks));
		return modelFiles;
	}

	protected List<ModelFile> buildRequiredJMSFiles(boolean isTest) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		Application application = context.getApplication();
		List<String> projectNames = ApplicationUtil.getProjectNames(application);
		Iterator<String> iterator = projectNames.iterator();
		while (iterator.hasNext()) {
			String projectName = iterator.next();
			Project project = context.getProjectByName(projectName);
			destinations = JMSUtil.buildJMSDestinations(project);
			List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
			modelFiles.addAll(buildJMSFiles(isTest, messagingBlocks));
		}
		return modelFiles;
	}

	protected List<ModelFile> buildJMSFiles(boolean isTest, List<Messaging> messagingBlocks) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messagingBlock = iterator.next();
			modelFiles.add(buildJMSFile(isTest, messagingBlock));
		}
		return modelFiles;
	}

	protected ModelFile buildJMSFile(boolean isTest, Messaging messagingBlock) throws Exception {
		String fileName = MessagingUtil.getJMSDescriptorFileName(messagingBlock);
		ModelFile modelFile = buildJMSFile(isTest, fileName, messagingBlock);
		return modelFile;
	}
	
	protected ModelFile buildJMSFile(boolean isTest, String fileName, Messaging messagingBlock) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, fileName);
		modelFile.setTextContent(getFileContent(isTest, messagingBlock));
		return modelFile;
	}

	public String getFileContent(boolean isTest, Messaging messagingBlock) throws Exception {
		Buf buf = new Buf();
		buf.put(buildXmlOpen());
		buf.put(buildXmlContent(messagingBlock));
		buf.put(buildXmlClose());
		return buf.get();
	}
	
	protected String buildXmlOpen() {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		buf.putLine("<messaging-deployment"); 
		buf.putLine("	xmlns=\"urn:jboss:messaging-deployment:1.0\">");
		return buf.get();
	}

	protected String buildXmlContent(Messaging messagingBlock) {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine("	<hornetq-server>");
		buf.putLine("		<jms-destinations>");
		Set<String> keySet = destinations.keySet();
		List<String> keys = new ArrayList<String>(keySet);
		//List<String> keys = NameUtil.sortStrings(keySet);
		
		String currentChannel = null;
		Iterator<String> iterator = keys.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String key = iterator.next();

			JMSDestination destination = destinations.get(key);
			if (!destination.channel.getName().equals(currentChannel)) {
				currentChannel = destination.channel.getName();
				buf.putLine("");
				buf.putLine3("<!-- \""+currentChannel+"\" channel destinations -->");
			}
			buf.put(buildXmlContentForDestination(destination));
		}
		buf.put(buildXmlContentForTestDestinations());
		buf.putLine("		</jms-destinations>");
		buf.putLine("	</hornetq-server>");
		return buf.get();
	}

	protected String buildXmlContentForDestination(JMSDestination jmsDestination) {
		return buildXmlContent(jmsDestination.service, jmsDestination.channel, jmsDestination.transport);
	}

	protected String buildXmlContent(Service service, Channel channel, JmsTransport transport) {
//		if (service.getName().equals("queryBooks"))
//			System.out.println();
		
		String domain = service.getDomain();
		//String domain = context.getService().getDomain();
		String name = JMSUtil.getDestinationName(domain, service.getName(), channel);
		String type = "queue";
		String typeCapped = "Queue";
		name += "_"+type;
		
		Buf buf = new Buf();
		buf.putLine("");
		/*
		 * TODO Add description comment here
		 */
//		buf.putLine2("	<!--");
//		buf.putLine2("		JMS "+typeCapped+"");
//		buf.putLine2("		*******************");
//		buf.putLine2("		Channel: "+channel.getName());
//		buf.putLine2("		Domain: "+service.getDomain());
//		buf.putLine2("		Service: "+service.getInterfaceName());
//		buf.putLine2("	-->");
		buf.putLine2("	<jms-"+type+" name=\""+name+"\">");
		buf.putLine2("		<entry name=\"/"+type+"/"+name+"\" />");
		buf.putLine2("		<entry name=\"java:jboss/exported/jms/"+type+"/"+name+"\" />");
		buf.putLine2("	</jms-"+type+">");
		return buf.get();
	}

	protected String buildXmlContentForTestDestinations() {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine3("<!-- \"Test\" channel destinations -->");
		buf.put(buildXmlContentForTestDestinations("queue", "a"));
		buf.put(buildXmlContentForTestDestinations("queue", "b"));
		buf.put(buildXmlContentForTestDestinations("queue", "c"));
		buf.put(buildXmlContentForTestDestinations("queue", "d"));
		buf.put(buildXmlContentForTestDestinations("queue", "e"));
		buf.put(buildXmlContentForTestDestinations("queue", "f"));
		return buf.get();
	}
	
	protected String buildXmlContentForTestDestinations(String type, String name) {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine2("	<jms-"+type+" name=\"test_message_domain_test_destination_queue_"+name+"\">");
		buf.putLine2("		<entry name=\"/"+type+"/test_message_domain_test_destination_queue_"+name+"\" />");
		buf.putLine2("		<entry name=\"java:jboss/exported/jms/"+type+"/test_message_domain_test_destination_queue_"+name+"\" />");
		buf.putLine2("	</jms-"+type+">");
		return buf.get();
	}

	protected String buildXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</messaging-deployment>");
		return buf.get();
	}

	
//	protected String buildXmlContent_JMSDestinations(List<Callback> callbacks) {
//		Buf buf = new Buf();
//		Iterator<Callback> iterator = callbacks.iterator();
//		while (iterator.hasNext()) {
//			Callback callback = iterator.next();
//			//TODO add description here
//			buf.put(buildXmlContent_JMSDestinations(callback));
//		}
//		return buf.get();
//	}
//
//	protected String buildXmlContent_JMSDestinations(Service service) {
//		Buf buf = new Buf();
//		List<Channel> channels = ServiceUtil.getChannels(service);
//		Iterator<Channel> iterator = channels.iterator();
//		while (iterator.hasNext()) {
//			Channel channel = iterator.next();
//			buf.put(buildXmlContent_JMSDestination(service, channel));
//		}
//		return buf.get();
//	}
//	
//	protected String buildXmlContent_JMSDestination(Service service, Channel channel) {
//		Project project = context.getProject();
//		Messaging messaging = context.getMessaging();
//		List<Link> links = MessagingUtil.getLinks(messaging);
//		List<Interactor> interactors = ServiceUtil.getInteractors(service);
//		
//		Buf buf = new Buf();
//		List<Listener> listeners = ServiceUtil.getListeners(service);
//		Iterator<Listener> iterator = listeners.iterator();
//		while (iterator.hasNext()) {
//			Listener listener = iterator.next();
//			//Role role = MessagingUtil.getRole(project, channel, listener.getRole());
//			Role role = MessagingUtil.getRole(project, channel, "user");
//			Collection<JmsTransport> transports = MessagingUtil.getJMSTransports(project, channel, role);
//			buf.put(buildXmlContent_JMSDestination(service, channel, transports));
//		}
//		return buf.get();
//	}
//
//	protected String buildXmlContent_JMSDestination(Service service, Channel channel, Collection<JmsTransport> transports) {
//		Buf buf = new Buf();
//		Iterator<JmsTransport> iterator = transports.iterator();
//		while (iterator.hasNext()) {
//			JmsTransport transport = iterator.next();
//			buf.put(buildXmlContent_JMSDestination(service, channel, transport));
//		}
//		return buf.get();
//	}

}
