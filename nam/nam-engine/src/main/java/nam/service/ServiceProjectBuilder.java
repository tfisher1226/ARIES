package nam.service;

import aries.codegen.AbstractProjectBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class ServiceProjectBuilder extends AbstractProjectBuilder {

	private PomXMLBuilder pomXMLBuilder;

	
	public ServiceProjectBuilder(GenerationContext context) {
		pomXMLBuilder = new PomXMLBuilder(context);
		this.context = context;
	}

	public ModelPom buildModelPom() throws Exception {
		ModelPom modelPom = pomXMLBuilder.build();
		return modelPom;
	}
	

}
