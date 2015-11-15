package nam.service.src.test.resources;

import aries.codegen.AbstractDataLayerFileGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ArquillianXMLGenerator extends AbstractDataLayerFileGenerator {

	private ArquillianXMLBuilder arquillianXMLBuilder;
	
	public ArquillianXMLGenerator(GenerationContext context) {
		super(context);
		arquillianXMLBuilder = new ArquillianXMLBuilder(context);
	}

	public void generate() throws Exception {
		ModelFile modelFile = arquillianXMLBuilder.buildFile();
		generateFile(modelFile);
	}
	
}
