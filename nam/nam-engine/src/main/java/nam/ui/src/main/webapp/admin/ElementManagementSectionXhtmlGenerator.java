package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;
import nam.model.util.ViewUtil;
import nam.ui.Relation;
import nam.ui.View;
import aries.generation.engine.GenerationContext;


public class ElementManagementSectionXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementManagementSectionXhtmlBuilder elementManagementSectionXHTMLBuilder;
	
	
	public ElementManagementSectionXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementManagementSectionXHTMLBuilder = new ElementManagementSectionXhtmlBuilder(context);
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
		generateFile(elementManagementSectionXHTMLBuilder.buildElementFile(information, element, "Overview"));
		//generateFile(elementManagementSectionXHTMLBuilder.buildElementFile(information, element, "Identification"));
		//generateFile(elementManagementSectionXHTMLBuilder.buildElementFile(information, element, "Configuration"));
		//generateFile(elementManagementSectionXHTMLBuilder.buildElementFile(information, element, "Documentation"));
		View view = context.getModule().getView();
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		Relation relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String managedBy = iterator.next();
				generateFile(elementManagementSectionXHTMLBuilder.buildElementFile(information, element, managedBy));
			}
		}
	}
	
//	public void generate(Information information, Type type) throws Exception {
//		generateFile(elementWizardNavigatorXHTMLBuilder.buildTypeFile(information, type));
//	}

}
