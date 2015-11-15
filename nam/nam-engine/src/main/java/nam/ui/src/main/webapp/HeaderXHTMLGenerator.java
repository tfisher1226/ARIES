package nam.ui.src.main.webapp;

import aries.generation.engine.GenerationContext;


public class HeaderXHTMLGenerator extends AbstractXHTMLGenerator {

	public HeaderXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		super.generate("header.xhtml");
	}

}
