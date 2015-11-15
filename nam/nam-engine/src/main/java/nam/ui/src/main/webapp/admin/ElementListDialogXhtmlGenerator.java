package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementListDialogXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementListDialogXhtmlBuilder elementListDialogXhtmlBuilder;
	
	
	public ElementListDialogXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementListDialogXhtmlBuilder = new ElementListDialogXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementListDialogXhtmlBuilder.buildFile(information, element));
	}

}
