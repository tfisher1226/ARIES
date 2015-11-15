package nam.ui.src.main.webapp.WEBINF;

import aries.codegen.AbstractDataLayerFileGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class BeansXMLGenerator extends AbstractDataLayerFileGenerator {

	private BeansXMLBuilder beansXMLBuilder;
	
	public BeansXMLGenerator(GenerationContext context) {
		super(context);
		beansXMLBuilder = new BeansXMLBuilder(context);
	}

	public void generate() throws Exception {
		ModelFile modelFile = beansXMLBuilder.buildFile();
		generateFile(modelFile);
	}
	
}
