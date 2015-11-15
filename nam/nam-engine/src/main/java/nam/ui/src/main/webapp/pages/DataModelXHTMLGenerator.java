package nam.ui.src.main.webapp.pages;

import nam.model.Module;
import nam.model.Project;
import nam.ui.src.main.webapp.AbstractXHTMLGenerator;
import aries.generation.engine.GenerationContext;


public class DataModelXHTMLGenerator extends AbstractXHTMLGenerator {

	public DataModelXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void generate() throws Exception {
		super.generate("error.xhtml");
	}

	public void generate(Project project, Module module) {
		// TODO Auto-generated method stub
		
	}

}
