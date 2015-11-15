package nam.service.src.test.java;

import nam.model.Operation;
import nam.model.Service;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;


public class ServiceListenerForJMSUnitTestProvider extends AbstractServiceListenerUnitTestProvider {

	ServiceListenerForJMSUnitTestProvider(GenerationContext context, AbstractServiceListenerUnitTestBuilder builder) {
		super(context, builder);
	}

	@Override
	protected String getOperationName(Operation operation) {
		return "onMessage";
	}

	protected String getEmptyRequestErrorMessage(Operation operation) {
		String message = "Payload not found in message";
		return message;
	}

	public String getMethodSource_RunTest(Service service, Operation operation) {
		String argumentString = CodeUtil.getArgumentString(operation);
		Buf buf = new Buf();
		buf.putLine2("setupBeforeInvocation("+argumentString+");");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	fixture = createFixture();");
		buf.putLine2("	fixture."+getOperationName(operation)+"(mockMessage);");
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

}
