package nam.data.src.main.resources;

import nam.model.Persistence;
import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class SeamPropertiesBuilder extends AbstractDataLayerFileBuilder {

	public SeamPropertiesBuilder(GenerationContext context) {
		super(context);
	}

	public ModelFile buildFile(boolean isTest, Persistence persistence) throws Exception {
		ModelFile modelFile = createMainResourcesFile("seam.properties");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("");
		return buf.get();
	}
	
}
