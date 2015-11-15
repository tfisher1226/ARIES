package nam.client.src.test.resources;

import java.util.ArrayList;
import java.util.List;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JMSNodePropertyFileBuilder extends AbstractFileBuilder {

	public JMSNodePropertyFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public List<ModelFile> buildFiles() throws Exception {
		return buildFiles(false);
	}

	public List<ModelFile> buildForTest() throws Exception {
		return buildFiles(true);
	}

	public List<ModelFile> buildFiles(boolean isTest) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		modelFiles.add(buildLocalPropertiesFile(isTest, "node1-jms-local.properties"));
		modelFiles.add(buildRemotePropertiesFile(isTest, "node1-jms-remote.properties"));
		return modelFiles;
	}

	public ModelFile buildLocalPropertiesFile(boolean isTest, String fileName) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "provider/hornetq", fileName);
		modelFile.setTextContent(buildLocalPropertiesFileContent(isTest));
		return modelFile;
	}
	
	public ModelFile buildRemotePropertiesFile(boolean isTest, String fileName) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "provider/hornetq", fileName);
		modelFile.setTextContent(buildRemotePropertiesFileContent(isTest));
		return modelFile;
	}

	protected String buildLocalPropertiesFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("bindAddress=${BIND_SERVER01}");
		buf.putLine("managementPort=9999");
		buf.putLine("username=testuser");
		buf.putLine("password=password");
		buf.putLine("administered.queue=/queue/test");
		buf.putLine("administered.topic=/topic/test");
		buf.putLine("localConnectionFactory=/ConnectionFactory");
		buf.putLine("remoteConnectionFactory=jms/RemoteConnectionFactory");
		buf.putLine("xaConnectionFactory=/XAConnectionFactory");
		buf.putLine("#xaConnectionFactory=jms/JmsXA");
		return buf.get();
	}

	protected String buildRemotePropertiesFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("bindAddress=${BIND_SERVER01}");
		buf.putLine("managementPort=9999");
		buf.putLine("username=testuser");
		buf.putLine("password=password");
		buf.putLine("administered.queue=jms/queue/test");
		buf.putLine("administered.topic=jms/topic/test");
		buf.putLine("localConnectionFactory=/ConnectionFactory");
		buf.putLine("remoteConnectionFactory=jms/RemoteConnectionFactory");
		buf.putLine("xaConnectionFactory=/XAConnectionFactory");
		buf.putLine("#xaConnectionFactory=jms/JmsXA");
		return buf.get();
	}

}
