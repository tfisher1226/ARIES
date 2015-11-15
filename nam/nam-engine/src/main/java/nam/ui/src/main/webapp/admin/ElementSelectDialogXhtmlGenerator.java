package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementSelectDialogXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementSelectDialogXhtmlBuilder elementSelectDialogXhtmlBuilder;
	
	
	public ElementSelectDialogXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementSelectDialogXhtmlBuilder = new ElementSelectDialogXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementSelectDialogXhtmlBuilder.buildFile(information, element));
	}

}
