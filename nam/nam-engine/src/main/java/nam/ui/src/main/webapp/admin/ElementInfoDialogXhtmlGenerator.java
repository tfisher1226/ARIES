package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementInfoDialogXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementInfoDialogXhtmlBuilder elementInfoDialogXhtmlBuilder;
	
	
	public ElementInfoDialogXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementInfoDialogXhtmlBuilder = new ElementInfoDialogXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementInfoDialogXhtmlBuilder.buildFile(information, element));
	}

}
