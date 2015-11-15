package nam.service.src.test.java;

import nam.model.Operation;
import nam.model.Service;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;


public class ServiceListenerForJAXWSUnitTestProvider extends AbstractServiceListenerUnitTestProvider {

	public ServiceListenerForJAXWSUnitTestProvider(GenerationContext context, AbstractServiceListenerUnitTestBuilder builder) {
		super(context, builder);
	}

	protected boolean isServiceFaultExpected() {
		return true;
	}

	public String getMethodSource_RunTest(Service service, Operation operation) {
		String argumentString = CodeUtil.getArgumentString(operation);
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	fixture = createFixture();");
		buf.putLine2("	fixture."+getOperationName(operation)+"("+argumentString+");");
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
