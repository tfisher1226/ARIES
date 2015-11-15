package nam.service.src.test.java;

import nam.model.Fault;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Service;
import nam.model.util.FieldUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;


public abstract class AbstractServiceListenerUnitTestProvider extends AbstractListenerTestProvider {
	
	protected AbstractServiceListenerUnitTestBuilder builder;
	
	
	public AbstractServiceListenerUnitTestProvider(GenerationContext context, AbstractServiceListenerUnitTestBuilder builder) {
		super(context, builder);
		this.builder = builder;
	}

	protected String getHandlerInterfaceName(Service service) {
		return ServiceLayerHelper.getHandlerInterfaceName(service);
	}

	protected String getHandlerInstanceName(Service service) {
		return ServiceLayerHelper.getHandlerInstanceName(service);
	}

	public String getMethodSource_Setup(Service service) {
		Buf buf = new Buf();
		if (ServiceUtil.isStateful(service)) {
			String contextClassName = ServiceLayerHelper.getProcessContextClassName(service.getProcess());
			String handlerClassName = getHandlerInterfaceName(service);
			buf.putLine2("mock"+contextClassName+" = new "+contextClassName+"();");
			buf.putLine2("mock"+handlerClassName+" = mock("+handlerClassName+".class);");
		}
		String fileNamePrefix = ModuleUtil.getFileNamePrefix(context.getModule());
		String eventsFileName = fileNamePrefix + "-checks.xml";
		//buf.putLine2("CheckpointManager.setDomain(getDomain());");
		buf.putLine2("CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());");
		buf.putLine2("CheckpointManager.addConfiguration(\""+eventsFileName+"\");");
		buf.putLine2("super.setUp();");
		return buf.get();
	}

	public String getMethodSource_TearDown(Service service) {
		Buf buf = new Buf();
		if (service.getProcess() != null) {
			String contextClassName = ServiceLayerHelper.getProcessContextClassName(service.getProcess());
			String handlerClassName = getHandlerInterfaceName(service);

			buf.putLine2("mock"+contextClassName+" = null;");
			buf.putLine2("mock"+handlerClassName+" = null;");
		}
		buf.putLine2("fixture = null;");
		buf.putLine2("super.tearDown();");
		return  buf.get();
	}

	public String getMethodSource_CreateFixture(Service service) {
		String fixtureClassName = builder.getFixtureClassName(service);

		Buf buf = new Buf();
		buf.putLine2("fixture = new "+fixtureClassName+"();");
		buf.putLine2("initialize(fixture);");
		buf.putLine2("return fixture;");
		return buf.get();
	}
	
	public String getMethodSource_CreateFixture(Process process, Service service) {
		String fixtureClassName = builder.getFixtureClassName(service);
		String contextClassName = ServiceLayerHelper.getProcessContextClassName(process);
		String handlerClassName = getHandlerInterfaceName(service);
		String handlerFieldName = getHandlerInstanceName(service);
		String contextFieldName = NameUtil.uncapName(contextClassName);

		Buf buf = new Buf();
		buf.putLine2("fixture = new "+fixtureClassName+"();");
		buf.putLine2("FieldUtil.setFieldValue(fixture, \""+contextFieldName+"\", mock"+contextClassName+");");
		buf.putLine2("FieldUtil.setFieldValue(fixture, \""+handlerFieldName+"\", mock"+handlerClassName+");");
		buf.putLine2("initialize(fixture);");

		//buf.putLine2("fixture.setStateManager(mock"+baseNameCapped+"StateManager);");
		
		/*
		//assuming just one execution for now
		List<Execution> executions = service.getExecutions();
		if (executions.size() > 0) {
			Execution execution = executions.get(0);
			List<Action> actions = execution.getActions();
			Iterator<Action> iterator = actions.iterator();
			while (iterator.hasNext()) {
				Action action = (Action) iterator.next();
				String actionName = NameUtil.capName(action.getName());
				String className = ProcessUtil.getClassName(actionName);
				buf.putLine2("mock"+className+" = mock("+className+".class);");
			}
		}
		*/

		/*
		Process process = context.getProcess();
		if (process != null) {
			String processClassName = NameUtil.capName(service.getName())+"Process";
			buf.putLine2("mockProcessLocator = mock(ProcessLocator.class);");
			buf.putLine2("mock"+processClassName+" = mock("+processClassName+".class);");
			buf.putLine2("BeanContext.set(\"org.aries.processLocator\", mockProcessLocator);");
		}
		*/
		
		buf.putLine2("return fixture;");
		return buf.get();
	}

	public String getMethodSource_RunTest_success(Service service, Operation operation) {
		Buf buf = new Buf();
		String argumentString = CodeUtil.getArgumentString(operation);
		buf.put(CodeUtil.getParameterCreationSource(operation));
		if (context.isEnabled("useMessageOrientedInteraction")) {
			buf.putLine2("setupContext(\"dummyCorrelationId\", \"dummyTransactionId\");");
			if (operation.getParameters().size() == 1)
				buf.putLine2("setupMessage("+argumentString+");");
		}
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		return buf.get();
	}

	public String getMethodSource_RunTest_nullParameter(Service service, Operation operation, Parameter parameter, Field field) {
		Buf buf = new Buf();
		String parameterFieldPath = getParameterFieldPath(parameter, field);
		
		Fault fault = builder.getFault(service);
		if (isServiceFaultExpected() && fault != null) {
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);
			buf.putLine2("setupForExpectedAssertionFailure("+faultClassName+".class, \""+parameterFieldPath+"\");");
		} else buf.putLine2("setupForExpectedAssertionFailure(\""+parameterFieldPath+"\");");
		
		if (field != null) {
			buf.put(CodeUtil.getParameterCreationSource(operation));
			String fieldNameCapped = NameUtil.capName(field.getName());
			buf.putLine2(parameter.getName()+".set"+fieldNameCapped+"(null);");
		}
		
		if (context.isEnabled("useMessageOrientedInteraction")) {
			buf.putLine2("setupContext(\"dummyCorrelationId\", \"dummyTransactionId\");");
		}
		
		buf.putLine2("isExpectedValidationError = true;");
		if (field != null) {
			String parameterName = parameter.getName();
			buf.putLine2("runTestExecute_"+operation.getName()+"("+parameterName+");");
		} else buf.putLine2("runTestExecute_"+operation.getName()+"(null);");
		return buf.get();
	}

	public String getMethodSource_RunTest_emptyParameter(Service service, Operation operation, Parameter parameter, Field field) {
		Buf buf = new Buf();
		String argumentString = CodeUtil.getArgumentString(operation);
		String parameterFieldPath = getParameterFieldPath(parameter, field);
		
		Fault fault = builder.getFault(service);
		if (isServiceFaultExpected() && fault != null) {
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);
			buf.putLine2("setupForExpectedAssertionFailure("+faultClassName+".class, \""+parameterFieldPath+"\");");
		} else buf.putLine2("setupForExpectedAssertionFailure(\""+parameterFieldPath+"\");");
		
		if (field == null)
			buf.put(CodeUtil.getEmptyParameterCreationSource(operation));
		else buf.put(CodeUtil.getParameterCreationSource(operation));
		
		if (context.isEnabled("useMessageOrientedInteraction")) {
			buf.putLine2("setupContext(\"dummyCorrelationId\", \"dummyTransactionId\");");
			if (operation.getParameters().size() == 1)
				buf.putLine2("setupMessage("+argumentString+");");
		}
		
		if (field != null) {
			String parameterName = parameter.getName();
			String parameterClassName = TypeUtil.getClassName(parameter.getType());

			String fieldType = field.getType();
			String fieldName = field.getName();
			String fieldNameCapped = NameUtil.capName(fieldName);
			String fieldClassName = TypeUtil.getClassName(field.getType());
			
			if (FieldUtil.isString(field)) {
				buf.putLine2(parameterName+".set"+fieldNameCapped+"(\"\");");
			} else {
				String fixtureName = ModelLayerHelper.getModelFixtureBeanName(fieldType);
				buf.putLine2(parameterClassName + " " +parameterName + " = " + fixtureName+".createEmpty_"+fieldClassName+"();");
				buf.putLine2(parameterName+".set"+fieldNameCapped+"("+fieldName+");");
			}
		}
		
		buf.putLine2("isExpectedValidationError = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		return buf.get();
	}

	public String getMethodSource_RunTest_nullCorrelationId(Service service, Operation operation) {
		Buf buf = new Buf();
		Fault fault = builder.getFault(service);
		if (isServiceFaultExpected() && fault != null) {
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);
			buf.putLine2("setupForExpectedAssertionFailure("+faultClassName+".class, \"CorrelationId null\");");
		} else buf.putLine2("setupForExpectedAssertionFailure(\"CorrelationId null\");");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		buf.putLine2("setupContext(null, \"dummyTransactionId\");");
		String argumentString = CodeUtil.getArgumentString(operation);
		if (operation.getParameters().size() == 1)
			buf.putLine2("setupMessage("+argumentString+");");
		buf.putLine2("isExpectedValidationError = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		return buf.get();
	}

	public String getMethodSource_RunTest_nullTransactionId(Service service, Operation operation) {
		Buf buf = new Buf();
		Fault fault = builder.getFault(service);
		if (isServiceFaultExpected() && fault != null) {
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);
			buf.putLine2("setupForExpectedAssertionFailure("+faultClassName+".class, \"TransactionId null\");");
		} else buf.putLine2("setupForExpectedAssertionFailure(\"TransactionId null\");");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		buf.putLine2("setupContext(\"dummyCorrelationId\", null);");
		String argumentString = CodeUtil.getArgumentString(operation);
		if (operation.getParameters().size() == 1)
			buf.putLine2("setupMessage("+argumentString+");");
		buf.putLine2("setGlobalTransactionActive(true);");
		buf.putLine2("isExpectedValidationError = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		return buf.get();
	}

	public String getMethodSource_RunTest_emptyCorrelationId(Service service, Operation operation) {
		Buf buf = new Buf();
		Fault fault = builder.getFault(service);
		if (isServiceFaultExpected() && fault != null) {
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);
			buf.putLine2("setupForExpectedAssertionFailure("+faultClassName+".class, \"CorrelationId empty\");");
		} else buf.putLine2("setupForExpectedAssertionFailure(\"CorrelationId empty\");");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		buf.putLine2("setupContext(\"\", \"dummyTransactionId\");");
		String argumentString = CodeUtil.getArgumentString(operation);
		if (operation.getParameters().size() == 1)
			buf.putLine2("setupMessage("+argumentString+");");
		buf.putLine2("isExpectedValidationError = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		return buf.get();
	}

	public String getMethodSource_RunTest_emptyTransactionId(Service service, Operation operation) {
		Buf buf = new Buf();
		Fault fault = builder.getFault(service);
		if (isServiceFaultExpected() && fault != null) {
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);
			buf.putLine2("setupForExpectedAssertionFailure("+faultClassName+".class, \"TransactionId empty\");");
		} else buf.putLine2("setupForExpectedAssertionFailure(\"TransactionId empty\");");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		buf.putLine2("setupContext(\"dummyCorrelationId\", \"\");");
		String argumentString = CodeUtil.getArgumentString(operation);
		if (operation.getParameters().size() == 1)
			buf.putLine2("setupMessage("+argumentString+");");
		buf.putLine2("setGlobalTransactionActive(true);");
		buf.putLine2("isExpectedValidationError = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		return buf.get();
	}

	public String getMethodSource_RunTest(Service service, Operation operation) {
		String argumentString = CodeUtil.getArgumentString(operation);
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	fixture = createFixture();");
		buf.putLine2("	fixture."+getOperationName(operation)+"();");
		buf.putLine3("");
		buf.putLine2("	validateAfterInvocation("+argumentString+");");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	validateAfterException(e);");
		buf.putLine3("");
		buf.putLine2("} finally {");
		buf.putLine2("	validateAfterExecution();");
		buf.putLine2("}");
		return buf.get();
	}

	protected String getParameterFieldLabel(Parameter parameter, Field field) {
		String parameterName = parameter.getName();
		String parameterNameCapped = NameUtil.capName(parameterName);
		String parameterFieldLabel = parameterNameCapped;
		if (field != null) {
			String fieldName = field.getName();
			String fieldNameCapped = NameUtil.capName(fieldName);
			parameterFieldLabel = parameterNameCapped + fieldNameCapped;
		}
		return parameterFieldLabel;
	}

	protected String getParameterFieldPath(Parameter parameter, Field field) {
		String parameterType = parameter.getType();
		String parameterName = parameter.getName();
		String parameterNameCapped = NameUtil.capName(parameterName);
		String parameterClassName = TypeUtil.getClassName(parameterType);
		String parameterFieldPath = parameterClassName;
		if (field != null) {
			String fieldName = field.getName();
			parameterFieldPath = parameterNameCapped + "/" + fieldName;
		}
		return parameterFieldPath;
	}


}
