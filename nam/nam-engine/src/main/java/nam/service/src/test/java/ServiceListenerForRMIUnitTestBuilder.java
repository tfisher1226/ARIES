package nam.service.src.test.java;

import java.lang.reflect.Modifier;

import nam.model.Operation;
import nam.model.Service;
import nam.service.ServiceLayerHelper;
import aries.codegen.util.Buf;
import aries.generation.AriesModelUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceListenerForRMIUnitTestBuilder extends AbstractServiceListenerUnitTestBuilder {

	public ServiceListenerForRMIUnitTestBuilder(GenerationContext context) {
		super(context);
	}

	@Override
	protected String getFixtureClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForRMI";
	}

	@Override
	protected String getTestClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForRMIUnitTest";
	}

	@Override
	protected String getTestParentClassName() {
		return "org.aries.tx.AbstractListenerForRMIUnitTest";
	}

	@Override
	protected AbstractServiceListenerUnitTestProvider createProvider(GenerationContext context) {
		return new ServiceListenerForRMIUnitTestProvider(context, this);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		super.initializeImportedClasses(modelClass, service);
		modelClass.addImportedClass("org.aries.message.Message");
		modelClass.addImportedClass("org.aries.message.util.MessageConstants");
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Service service, Operation operation) throws Exception {
		super.initializeInstanceMethods(modelClass, service, operation);
		createOperation_ValidateAfterInvocation(modelClass, service, operation);
	}

	protected void createOperation_ValidateAfterInvocation(ModelClass modelClass, Service service, Operation operation) {
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		String handlerClassName = provider.getHandlerInterfaceName(service);
		//String argumentString = CodeUtil.getArgumentString(operation);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("validateAfterInvocation");
		modelOperation.getParameters().add(AriesModelUtil.createModelParameter("message", "Message"));
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("if (!isExpectedValidationError)");
		buf.putLine2("	verify(mock"+handlerClassName+")."+serviceName+"(message);");
		//buf.putLine2("	verify(mock"+handlerClassName+")."+serviceName+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
}
