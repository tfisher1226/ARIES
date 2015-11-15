package nam.client.src.test.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.TestUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceProxyTestProvider extends AbstractBeanProvider {

	public ServiceProxyTestProvider(GenerationContext context) {
		super(context);
	}

	protected String generate(ModelClass modelClass, ModelOperation modelOperation, Service service, Operation operation) {
		String serviceName = NameUtil.capName(service.getName());
		String serviceNamespace = service.getNamespace();
		String serviceVersion = service.getVersion();
		String invocationMethod = "TransportType.HTTP";
		
		Buf buf = new Buf();
		buf.putLine2("String targetName = \""+serviceName+"\";");
		buf.putLine2("String targetNamespace = \""+serviceNamespace+"\";");
		buf.putLine2("String targetVersion = \""+serviceVersion+"\";");
		buf.putLine2("when(mockServiceLocator.lookup(targetNamespace+\"/\"+targetName, targetVersion, "+invocationMethod+")).thenReturn(mockServiceProxy);");
		
		buf.putLine("");
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterClassName = TypeUtil.getClassName(parameter.getType());
			String parameterValue = TestUtil.getRandomValue(parameterClassName);
			String constructor = CodeUtil.generateConstructor(parameterClassName, parameterValue);
			buf.putLine2(parameterClassName+" "+parameterName+" = new "+constructor+";");
		}

		String resultName = null;
		boolean resultExists = false;
		if (!operation.getResults().isEmpty()) {
			Result result = operation.getResults().get(0);
			if (TypeUtil.doesResultExist(result)) {
				resultName = result.getName();
				String resultType = result.getType();
				String resultClassName = TypeUtil.getClassName(resultType);
				String resultValue = TestUtil.getRandomValue(resultClassName);
				String constructor = CodeUtil.generateConstructor(resultClassName, resultValue);
				buf.putLine2(resultClassName+" "+resultName+" = new "+constructor+";");
			}
		}
		
		buf.putLine("");
		buf.putLine2("Message requestMessage = new Message();");
		buf.putLine2("Message responseMessage = new Message();");

		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			buf.putLine2("requestMessage.addPart(\""+parameterName+"\", "+parameterName+");");
		}
		
		resultExists = false;
		if (!operation.getResults().isEmpty()) {
			Result result = operation.getResults().get(0);
			if (TypeUtil.doesResultExist(result)) {
				String resultType = result.getType();
				String resultClassName = TypeUtil.getClassName(resultType);
				
				buf.putLine2("responseMessage.addPart(\"result\", "+resultName+");");
				buf.putLine2("when(mockServiceProxy.invoke(requestMessage)).thenReturn(responseMessage);");
	
				buf.putLine("");
				buf.put2(resultClassName+" response = fixture."+operation.getName()+"(");
				buf.put(generateParameterString(operation));
				buf.putLine(");");
	
				buf.putLine2("Assert.equals(response, "+resultName+", \"Incorrect response: \"+response);");
				buf.putLine2("System.out.println(\"Response: \"+response);");
				resultExists = true;
			}
		}
		
		if (!resultExists) {
			buf.put2("fixture."+operation.getName()+"(");
			buf.put(generateParameterString(operation));
			buf.putLine(");");
		}
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
