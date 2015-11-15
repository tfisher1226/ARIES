package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementListMenuXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementListMenuXhtmlBuilder elementListMenuXhtmlBuilder;
	
	
	public ElementListMenuXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementListMenuXhtmlBuilder = new ElementListMenuXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementListMenuXhtmlBuilder.buildFile(information, element));
	}

}
