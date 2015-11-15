package nam.ui.src.main.webapp;

import aries.generation.engine.GenerationContext;


public class IndexXHTMLGenerator extends AbstractXHTMLGenerator {

	public IndexXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		super.generate("index.html");
	}

}
