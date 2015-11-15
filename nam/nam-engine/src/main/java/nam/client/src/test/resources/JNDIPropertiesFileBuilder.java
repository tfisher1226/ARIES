package nam.client.src.test.resources;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JNDIPropertiesFileBuilder extends AbstractFileBuilder {

	public JNDIPropertiesFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}

	public ModelFile buildForTest() throws Exception {
		return buildFile(true);
	}
	
	public ModelFile buildFile(boolean isTest) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "jndi.properties");
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		if (isTest)
			buf.putLine("java.naming.factory.initial=org.aries.TestContextFactory");
		return buf.get();
	}
	
}
