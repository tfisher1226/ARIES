package nam.service.src.test.java;

import nam.model.Operation;
import aries.codegen.AbstractBeanProvider;
import aries.generation.engine.GenerationContext;


public abstract class AbstractListenerTestProvider extends AbstractBeanProvider {

	private AbstractListenerTestBuilder builder;

	
	public AbstractListenerTestProvider(GenerationContext context, AbstractListenerTestBuilder builder) {
		super(context);
		this.builder = builder;
	}

	protected boolean isServiceFaultExpected() {
		return false;
	}

	protected String getOperationName(Operation operation) {
		return operation.getName();
	}
	
	protected String getEmptyRequestErrorMessage(Operation operation) {
		String messageClassName = builder.getMessageClassName(operation);
		String message = messageClassName;
		return messageClassName;
	}
	


}
