package nam.ui.src.main.webapp.admin.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ElementUtil;
import nam.model.util.InformationUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.src.main.webapp.admin.AbstractCompositionXHTMLGenerator;
import aries.generation.engine.GenerationContext;


public abstract class AbstractDataElementXHTMLGenerator extends AbstractCompositionXHTMLGenerator {

	private AbstractDataElementXHTMLBuilder builder;
	
	
	public AbstractDataElementXHTMLGenerator(GenerationContext context) {
		super(context);
	}
	
	protected void setBuilder(AbstractDataElementXHTMLBuilder builder) {
		this.builder = builder;
	}

	public void initialize(Project project, Module module) throws Exception {
		super.initialize(project, module);
	}

	public void generate(Project project, Module module) throws Exception {
		List<Information> informationSets = new ArrayList<Information>();
		informationSets.addAll(ProjectUtil.getInformationBlocksFromModules(project));
		informationSets.add(module.getInformation());
		Iterator<Information> iterator = informationSets.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			generate(project, information);
		}
	}

	public void generate(Project project, Information information) throws Exception {
		if (information != null) {
			Collection<Namespace> namespaces = InformationUtil.getNamespaces(information);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				generateElements(namespace);
			}
		}
	}
	
	public void generateElements(Namespace namespace) throws Exception {
		Collection<Element> elements = NamespaceUtil.getElements(namespace);
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
		generateFile(builder.buildFile(namespace, element));
	}

}
