package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;
import aries.generation.engine.GenerationContext;


public class ElementTreeActionsXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementTreeActionsXhtmlBuilder elementTreeActionsXhtmlBuilder;
	
	
	public ElementTreeActionsXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementTreeActionsXhtmlBuilder = new ElementTreeActionsXhtmlBuilder(context);
		super.initialize(project, module);
	}

	public void generate(Information information) throws Exception {
		if (information != null) {
			generateElements(information);
		}
	}

	public void generateElements(Information information) throws Exception {
		List<Element> elements = InformationUtil.getElements(information);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			if (!ElementUtil.isHierarchical(element))
				continue;
			generate(information, element);
		}
	}

	public void generate(Information information, Element element) throws Exception {
		generateFile(elementTreeActionsXhtmlBuilder.buildFile(information, element));
	}
	
}