package nam;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractProjectBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class PomProjectBuilder extends AbstractProjectBuilder {

	private PomXMLBuilder pomXMLBuilder;

	
	public PomProjectBuilder(GenerationContext context) {
		pomXMLBuilder = new PomXMLBuilder(context);
		this.context = context;
	}

	public ModelPom buildModelPom(Project project, Module module) throws Exception {
		ModelPom modelPom = pomXMLBuilder.buildModelPom(project, module);
		return modelPom;
	}
	
}
