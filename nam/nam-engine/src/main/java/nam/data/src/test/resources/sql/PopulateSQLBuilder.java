package nam.data.src.test.resources.sql;

import nam.model.Unit;
import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class PopulateSQLBuilder extends AbstractDataLayerFileBuilder {

	public PopulateSQLBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelFile buildFile(boolean isTest, Unit unit) throws Exception {
		ModelFile modelFile = createTestResourcesFile("sql", "populate.sql");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("");
		return buf.get();
	}
	
}
