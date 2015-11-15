package nam.ui.src.main.webapp.admin;

import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class DomainServicesXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private DomainServicesXhtmlBuilder domainServicesXhtmlBuilder;
	
	
	public DomainServicesXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		domainServicesXhtmlBuilder = new DomainServicesXhtmlBuilder(context);
		super.initialize(project, module);
	}

	public void generate(Information information) throws Exception {
		generateFile(domainServicesXhtmlBuilder.buildFile(information));
	}

}
