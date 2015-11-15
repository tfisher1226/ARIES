package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementSelectPaneXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementSelectPaneXhtmlBuilder elementSelectPaneXhtmlBuilder;
	
	
	public ElementSelectPaneXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementSelectPaneXhtmlBuilder = new ElementSelectPaneXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementSelectPaneXhtmlBuilder.buildFile(information, element));
	}

}
