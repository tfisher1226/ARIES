package nam.service.src.test.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Service;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public class ServiceListenerForRMIUnitTestProvider extends AbstractServiceListenerUnitTestProvider {

	public ServiceListenerForRMIUnitTestProvider(GenerationContext context, AbstractServiceListenerUnitTestBuilder builder) {
		super(context, builder);
	}

	protected boolean isServiceFaultExpected() {
		return true;
	}

	protected String getHandlerInterfaceName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "Interceptor";
	}
	
	protected String getHandlerInstanceName(Service service) {
		return NameUtil.uncapName(getHandlerInterfaceName(service));
	}
	
	public String getMethodSource_RunTest(Service service, Operation operation) {
		//String argumentString = CodeUtil.getArgumentString(operation);
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Message message = new Message();");
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterInstanceName = parameter.getName();
			buf.putLine2("	message.addPart(\""+parameterInstanceName+"\",  "+parameterInstanceName+");");
		}
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		buf.putLine2("	message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, \""+serviceName+"\");");
		buf.putLine3("");
		buf.putLine2("	fixture = createFixture();");
		buf.putLine2("	fixture.invoke(message, correlationId, 60000L);");
		//buf.putLine2("	fixture."+getOperationName(operation)+"("+argumentString+");");
		buf.putLine3("");
		buf.putLine2("	validateAfterInvocation(message);");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	validateAfterException(e);");
		buf.putLine3("");
		buf.putLine2("} finally {");
		buf.putLine2("	validateAfterExecution();");
		buf.putLine2("}");
		return buf.get();
	}

}
