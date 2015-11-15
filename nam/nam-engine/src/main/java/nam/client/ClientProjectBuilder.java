package nam.client;

import aries.codegen.AbstractProjectBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelPom;


public class ClientProjectBuilder extends AbstractProjectBuilder {

	private PomXMLBuilder pomXMLBuilder;

	
	public ClientProjectBuilder(GenerationContext context) {
		pomXMLBuilder = new PomXMLBuilder(context);
		this.context = context;
	}

	public ModelPom buildModelPom() throws Exception {
		ModelPom modelPom = pomXMLBuilder.build();
		return modelPom;
	}
	

}
