package nam.data.src.main.resources;

import nam.model.Persistence;
import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ImportsSQLBuilder extends AbstractDataLayerFileBuilder {

	public ImportsSQLBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelFile buildFile(boolean isTest, Persistence persistence) throws Exception {
		ModelFile modelFile = createMainResourcesFile("imports.sql");
		modelFile.setTextContent(getXhtmlContent(isTest));
		return modelFile;
	}
	
	public String getXhtmlContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("");
		return buf.get();
	}
	
}
