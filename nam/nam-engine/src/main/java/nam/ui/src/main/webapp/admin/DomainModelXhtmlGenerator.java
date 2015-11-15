package nam.ui.src.main.webapp.admin;

import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class DomainModelXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private DomainModelXhtmlBuilder domainModelXhtmlBuilder;
	
	
	public DomainModelXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		domainModelXhtmlBuilder = new DomainModelXhtmlBuilder(context);
		super.initialize(project, module);
	}

	public void generate(Information information) throws Exception {
		generateFile(domainModelXhtmlBuilder.buildFile(information));
	}

}
