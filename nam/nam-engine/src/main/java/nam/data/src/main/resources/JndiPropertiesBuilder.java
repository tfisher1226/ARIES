package nam.data.src.main.resources;

import nam.model.Persistence;
import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JndiPropertiesBuilder extends AbstractDataLayerFileBuilder {

	public JndiPropertiesBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelFile buildFile(boolean isTest, Persistence persistence) throws Exception {
		ModelFile modelFile = createTestResourcesFile("jndi.properties");
		modelFile.setTextContent(getXhtmlContent(isTest));
		return modelFile;
	}
	
	public String getXhtmlContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("java.naming.factory.initial=org.aries.TestContextFactory");
		return buf.get();
	}

}
