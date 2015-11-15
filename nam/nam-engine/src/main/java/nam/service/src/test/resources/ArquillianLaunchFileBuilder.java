package nam.service.src.test.resources;

import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ArquillianLaunchFileBuilder extends AbstractDataLayerFileBuilder {

	public ArquillianLaunchFileBuilder(GenerationContext context) {
		super(context);
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}
	
	public ModelFile buildFile(boolean isTest) throws Exception {
		ModelFile modelFile = createTestResourcesFile("arquillian.launch");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put("serverGroup01");
		return buf.get();
	}
	
}
