package nam.client.src.test.resources;

import java.util.ArrayList;
import java.util.List;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JNDINodePropertyFileBuilder extends AbstractFileBuilder {

	public JNDINodePropertyFileBuilder(GenerationContext context) {
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
		modelFiles.add(buildLocalPropertiesFile(isTest, "node1-jndi-local.properties"));
		modelFiles.add(buildRemotePropertiesFile(isTest, "node1-jndi-remote.properties"));
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
		buf.putLine("java.naming.factory.initial=org.jboss.as.naming.InitialContextFactory");
		buf.putLine("java.naming.factory.url.pkgs=org.jboss.ejb.client.naming");
		buf.putLine("java.naming.provider.url=${BIND_SERVER01}:9999");
		buf.putLine("java.naming.security.principal=testuser");
		buf.putLine("java.naming.security.credentials=password");
		return buf.get();
	}

	protected String buildRemotePropertiesFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("java.naming.factory.initial=org.jboss.naming.remote.client.InitialContextFactory");
		buf.putLine("#java.naming.factory.initial=org.jnp.interfaces.NamingContextFactory");
		buf.putLine("#java.naming.factory.url.pkgs=org.jboss.naming");
		buf.putLine("java.naming.factory.url.pkgs=org.jboss.ejb.client.naming");
		buf.putLine("java.naming.provider.url=remote://${BIND_SERVER01}:4447");
		buf.putLine("#java.naming.provider.url=${BIND_SERVER01}:4447");
		buf.putLine("java.naming.security.principal=testuser");
		buf.putLine("java.naming.security.credentials=password");
		return buf.get();
	}

}
