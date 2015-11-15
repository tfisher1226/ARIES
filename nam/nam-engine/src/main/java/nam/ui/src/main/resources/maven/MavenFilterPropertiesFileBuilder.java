package nam.ui.src.main.resources.maven;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class MavenFilterPropertiesFileBuilder extends AbstractFileBuilder {

	public MavenFilterPropertiesFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}

	public ModelFile buildForTest() throws Exception {
		return buildFile(true);
	}

	public ModelFile buildFile(boolean isTest) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "maven", "filter.properties");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("");
		return buf.get();
	}
	
}
