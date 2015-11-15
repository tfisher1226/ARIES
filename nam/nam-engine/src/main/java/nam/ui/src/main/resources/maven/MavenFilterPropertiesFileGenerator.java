package nam.ui.src.main.resources.maven;

import aries.codegen.AbstractFileGenerator;
import aries.generation.engine.GenerationContext;


public class MavenFilterPropertiesFileGenerator extends AbstractFileGenerator {

	private MavenFilterPropertiesFileBuilder builder;
	
	
	public MavenFilterPropertiesFileGenerator(GenerationContext context) {
		this.context = context;
	}

	public void generate() throws Exception {
		generateFile(builder.buildFile());
	}

	public void generateForTest() throws Exception {
		generateFile(builder.buildFile(true));
	}
	
}
