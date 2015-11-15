package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;
import aries.generation.engine.GenerationContext;


public class ElementWizardControlXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementWizardControlXhtmlBuilder elementWizardControlXhtmlBuilder;
	
	
	public ElementWizardControlXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementWizardControlXhtmlBuilder = new ElementWizardControlXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Project project, Module module) throws Exception {
		generate(module.getInformation());
	}

	public void generate(Information information) throws Exception {
		if (information != null) {
			generateElements(information);
			//generateTypes(information);
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
			generate(information, element);
		}
	}

//	public void generateTypes(Information information) throws Exception {
//		List<Type> elements = InformationUtil.getTypes(information);
//		Iterator<Type> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Type type = iterator.next();
//			if (type instanceof Element)
//				continue;
//			generate(information, type);
//		}
//	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementWizardControlXhtmlBuilder.buildElementFile(information, element));
	}
	
//	public void generate(Information information, Type type) throws Exception {
//		generateFile(elementWizardControlXhtmlBuilder.buildTypeFile(information, type));
//	}

}
