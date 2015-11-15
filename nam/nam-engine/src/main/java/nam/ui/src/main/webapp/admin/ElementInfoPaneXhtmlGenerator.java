package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementInfoPaneXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementInfoPaneXhtmlBuilder elementInfoPaneXhtmlBuilder;
	
	
	public ElementInfoPaneXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementInfoPaneXhtmlBuilder = new ElementInfoPaneXhtmlBuilder(context);
		super.initialize(project, module);
	}

	public void generate(Information information, Element element) throws Exception {
		generateFiles(elementInfoPaneXhtmlBuilder.buildFiles(information, element));
	}

}
