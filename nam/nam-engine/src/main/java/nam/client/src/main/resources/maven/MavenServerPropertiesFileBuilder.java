package nam.client.src.main.resources.maven;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class MavenServerPropertiesFileBuilder extends AbstractFileBuilder {

	public MavenServerPropertiesFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}
	
	public ModelFile buildForTest() throws Exception {
		return buildFile(true);
	}
	
	public ModelFile buildFile(boolean isTest) throws Exception {	
		ModelFile modelFile = createResourcesFile(isTest, "maven", "server.properties");
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("LIB=.");
		buf.putLine("");
		buf.putLine("BIND_SERVER01=172.18.0.1");
		buf.putLine("BIND_SERVER02=172.18.0.2");
		buf.putLine("BIND_SERVER03=172.18.0.3");
		buf.putLine("BIND_SERVER04=172.18.0.4");
		buf.putLine("BIND_SERVER05=172.18.0.5");
		buf.putLine("BIND_SERVER06=172.18.0.6");
		buf.putLine("");
		buf.putLine("NODE_01=c:/software/jboss-as-7.1.1.Final-node1");
		buf.putLine("NODE_02=c:/software/jboss-as-7.1.1.Final-node2");
		buf.putLine("NODE_03=c:/software/jboss-as-7.1.1.Final-node3");
		buf.putLine("NODE_04=c:/software/jboss-as-7.1.1.Final-node4");
		buf.putLine("NODE_05=c:/software/jboss-as-7.1.1.Final-node5");
		buf.putLine("NODE_06=c:/software/jboss-as-7.1.1.Final-node6");
		buf.putLine("");
		buf.putLine("BYTEMAN_HOME=c:/software/byteman-download-2.1.4.1");
		return buf.get();
	}
	
}
