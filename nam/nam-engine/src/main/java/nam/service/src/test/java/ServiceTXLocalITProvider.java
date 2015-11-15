package nam.service.src.test.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Execution;
import nam.model.Invocation;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Process;

import aries.bpel.BPELRuntimeCache;
import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceTXLocalITProvider extends AbstractBeanProvider {

	public ServiceTXLocalITProvider(GenerationContext context) {
		super(context);
	}

	protected String generate(Service service, Operation operation, ModelClass modelClass, ModelOperation modelOperation) {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterClassName = TypeUtil.getClassName(parameter.getType());
			String constructor = CodeUtil.generateConstructor(parameterClassName);
			buf.putLine2(parameterClassName+" "+parameterName+" = new "+constructor+";");
			//buf.putLine2("request.setBorrowerName(\"Tom Fisher\");");
		}
		
		Process process = BPELRuntimeCache.INSTANCE.getProcess(service);
		if (process != null) {
			//String processClassName = NameUtil.capName(service.getName())+"Process";
			String processOperationName = CodeUtil.getProcessOperationName(process);
			
			List<Result> results = operation.getResults();
			Result result = null;
			if (!results.isEmpty()) {
				result = results.get(0);
				String resultType = result.getType();
				String resultClassName = TypeUtil.getClassName(resultType);
				buf.putLine2(resultClassName+" result = new "+resultClassName+"();");

				//assuming just one execution for now
				List<Execution> executions = ServiceUtil.getExecutions(service);
				if (executions.size() > 0) {
					Execution execution = executions.get(0);
					List<Invocation> actions = execution.getInvocations();
					Iterator<Invocation> actionIterator = actions.iterator();
					while (actionIterator.hasNext()) {
						Invocation action = actionIterator.next();
						String actionName = NameUtil.capName(action.getName());
						String className = actionName+"Process";
						buf.putLine2("when(mockProcessLocator.lookup("+className+".class)).thenReturn(mock"+className+");");
						buf.put2("when(mock"+className+"."+processOperationName+"(");
						buf.put(generateParameterString(operation));
						buf.putLine(")).thenReturn(result);");
					}
				}

				buf.put2(resultClassName+" response = fixture."+operation.getName()+"(");
				buf.put(generateParameterString(operation));
				buf.putLine(");");
				
				buf.putLine2("Assert.notNull(response, \"Response should exist\");");
				buf.putLine2("Assert.equals(response, result, \"Response not correct\");");
				return buf.get();
			}
		}

		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty()) {
			result = results.get(0);
			String resultType = result.getType();
			String resultClassName = TypeUtil.getClassName(resultType);

			buf.put2(resultClassName+" response = fixture."+operation.getName()+"(");
			buf.put(generateParameterString(operation));
			buf.putLine(");");

			buf.putLine2("Assert.notNull(response, \"Response should exist\");");
			return buf.get();
		}
		
		buf.put2("fixture."+operation.getName()+"(");
		buf.put(generateParameterString(operation));
		buf.putLine(");");
		return buf.get();
	}

	protected String generateParameterString(Operation operation) {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			if (i > 0)
				buf.put(", ");
			buf.put(parameterName);
		}
		return buf.get();
	}

}
