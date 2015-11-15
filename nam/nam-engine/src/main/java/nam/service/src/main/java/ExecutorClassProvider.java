package nam.service.src.main.java;

import aries.codegen.AbstractBeanProvider;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


public class ExecutorClassProvider extends AbstractBeanProvider {
	
	protected ModelClass currentClass;


	public ExecutorClassProvider(GenerationContext context) {
		super(context);
	}

	public void setCurrentClass(ModelClass modelClass) {
		this.currentClass = modelClass;
	}

}
