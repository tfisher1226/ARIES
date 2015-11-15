package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.src.main.webapp.AbstractXHTMLGenerator;
import aries.generation.engine.GenerationContext;


public abstract class AbstractCompositionXHTMLGenerator extends AbstractXHTMLGenerator {

	public AbstractCompositionXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		super.initialize(project, module);
	}
	
	public void generate(Project project, Module module) throws Exception {
		Set<Information> informationSets = new LinkedHashSet<Information>();
		informationSets.addAll(ProjectUtil.getInformationBlocksFromModules(project));
		Information moduleInformation = module.getInformation();
		if (moduleInformation != null)
			informationSets.add(moduleInformation);
		Iterator<Information> iterator = informationSets.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			generate(information);
		}
	}

	public void generate(Information information) throws Exception {
		if (information != null) {
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
	}
	
	public void generate(Information information, Element element) throws Exception {
		//by default do nothing
	}
	
	public void generate(Namespace namespace) throws Exception {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			generate(namespace, element);
		}
	}
	
	public void generate(Namespace namespace, Element element) throws Exception {
		//by default do nothing
	}

}
