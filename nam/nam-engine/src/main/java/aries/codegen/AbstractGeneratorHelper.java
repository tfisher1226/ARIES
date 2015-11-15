package aries.codegen;

import aries.generation.AriesModelFactory;
import aries.generation.AriesModelHelper;
import aries.generation.engine.GenerationContext;


public abstract class AbstractGeneratorHelper implements Builder {

	protected GenerationContext context;

	protected AriesModelFactory ariesModelFactory;
	
	protected AriesModelHelper ariesModelHelper;

	
	public AbstractGeneratorHelper(GenerationContext context) {
		this.context = context;
		ariesModelFactory = context.getEngine().getModelFactory();
		ariesModelHelper = context.getEngine().getModelHelper();
	}

	public GenerationContext getContext() {
		return context;
	}

	public void setContext(GenerationContext context) {
		this.context = context;
	}

}
