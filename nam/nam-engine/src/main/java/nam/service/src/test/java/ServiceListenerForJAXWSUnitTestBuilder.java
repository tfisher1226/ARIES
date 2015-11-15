package nam.service.src.test.java;

import java.lang.reflect.Modifier;

import nam.model.Operation;
import nam.model.Service;
import nam.service.ServiceLayerHelper;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceListenerForJAXWSUnitTestBuilder extends AbstractServiceListenerUnitTestBuilder {

	public ServiceListenerForJAXWSUnitTestBuilder(GenerationContext context) {
		super(context);
	}

	@Override
	protected String getFixtureClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForJAXWS";
	}

	@Override
	protected String getTestClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForJAXWSUnitTest";
	}

	@Override
	protected String getTestParentClassName() {
		return "org.aries.tx.AbstractListenerForJAXWSUnitTest";
	}

	@Override
	protected AbstractServiceListenerUnitTestProvider createProvider(GenerationContext context) {
		return new ServiceListenerForJAXWSUnitTestProvider(context, this);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Service service, Operation operation) throws Exception {
		super.initializeInstanceMethods(modelClass, service, operation);
		createOperation_ValidateAfterInvocation(modelClass, service, operation);
	}
	
	protected void createOperation_ValidateAfterInvocation(ModelClass modelClass, Service service, Operation operation) {
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		String handlerClassName = ServiceLayerHelper.getHandlerInterfaceName(service);
		String argumentString = CodeUtil.getArgumentString(operation);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("validateAfterInvocation");
		modelOperation.getParameters().addAll(util.createModelParameters(operation));
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("if (!isExpectedValidationError)");
		buf.putLine2("	verify(mock"+handlerClassName+")."+serviceName+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
}
