package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementListPanelXHTMLGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementListPanelXHTMLBuilder elementListPanelXHTMLBuilder;
	
	
	public ElementListPanelXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementListPanelXHTMLBuilder = new ElementListPanelXHTMLBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementListPanelXHTMLBuilder.buildFile(information, element));
	}

}
