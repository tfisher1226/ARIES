package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.ClassUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceListenerForJMSUnitTestBuilder extends AbstractServiceListenerUnitTestBuilder {

	public ServiceListenerForJMSUnitTestBuilder(GenerationContext context) {
		super(context);
	}

	@Override
	protected String getFixtureClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForJMS";
	}

	@Override
	protected String getTestClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForJMSUnitTest";
	}

	@Override
	protected String getTestParentClassName() {
		return "org.aries.tx.AbstractListenerForJMSUnitTest";
	}

	@Override
	protected AbstractServiceListenerUnitTestProvider createProvider(GenerationContext context) {
		return new ServiceListenerForJMSUnitTestProvider(context, this);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		modelClass.addStaticImportedClass("org.mockito.Mockito.when");
		super.initializeImportedClasses(modelClass, service);
	}
	
	protected void initializeInstanceMethods(ModelClass modelClass, Service service, Operation operation) throws Exception {
		super.initializeInstanceMethods(modelClass, service, operation);
		createOperation_SetupBeforeInvocation(modelClass, service, operation);
		createOperation_ValidateAfterInvocation(modelClass, service, operation);
	}
	
	protected void createOperation_SetupBeforeInvocation(ModelClass modelClass, Service service, Operation operation) {
		//String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		//String handlerClassName = ServiceLayerHelper.getHandlerInterfaceName(service);
		String argumentString = CodeUtil.getArgumentString(operation);
		if (argumentString.isEmpty())
			argumentString = "null";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("setupBeforeInvocation");
		modelOperation.addException("Exception");
		modelOperation.getParameters().addAll(util.createModelParameters(operation));
		Buf buf = new Buf();
		buf.putLine2("when(mockMessage.getObject()).thenReturn("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
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
