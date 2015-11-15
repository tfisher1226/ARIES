package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementListPaneXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementListPaneXhtmlBuilder elementListPaneXhtmlBuilder;
	
	
	public ElementListPaneXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementListPaneXhtmlBuilder = new ElementListPaneXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementListPaneXhtmlBuilder.buildFile(information, element));
	}

}
