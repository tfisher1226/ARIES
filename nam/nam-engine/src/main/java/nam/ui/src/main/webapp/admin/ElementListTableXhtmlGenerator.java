package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementListTableXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementListTableXhtmlBuilder elementListTableXhtmlBuilder;
	
	
	public ElementListTableXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementListTableXhtmlBuilder = new ElementListTableXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementListTableXhtmlBuilder.buildFile(information, element));
	}

}
