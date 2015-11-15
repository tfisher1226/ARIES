package nam.service.src.main.resources;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class SeamPropertiesFileBuilder extends AbstractFileBuilder {

	public SeamPropertiesFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		ModelFile modelFile = createMainResourcesFile("seam.properties");
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("");
		return buf.get();
	}
	
}
