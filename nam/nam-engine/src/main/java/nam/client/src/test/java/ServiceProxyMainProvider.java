package nam.client.src.test.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceProxyMainProvider extends AbstractBeanProvider {

	public ServiceProxyMainProvider(GenerationContext context) {
		super(context);
	}

	protected String generate(ModelClass modelClass, ModelOperation modelOperation, Operation operation) {
		//modelClass.addImportedClass("org.aries.runtime.Initializer");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterClassName = TypeUtil.getClassName(parameter.getType());
			String parameterName = parameter.getName();
			String constructor = CodeUtil.generateConstructor(parameterClassName);
			buf.putLine3(parameterClassName+" "+parameterName+" = new "+constructor+";");
			//buf.putLine3("request.setBorrowerName(\"Tom Fisher\");");
		}
		
//		List<ModelParameter> parameters = modelOperation.getParameters();
//		Iterator<ModelParameter> iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			ModelParameter modelParameter = iterator.next();
//			String parameterClassName = modelParameter.getClassName();
//			String parameterName = NameUtil.uncapName(parameterClassName);
//			buf.putLine3(parameterClassName+" "+parameterName+" = new "+parameterClassName+"();");
//			//buf.putLine3("request.setBorrowerName(\"Tom Fisher\");");
//		}

		String proxyClassName = NameUtil.capName(context.getService().getName())+"Proxy";
		buf.putLine3(proxyClassName+" proxy = new "+proxyClassName+"();");

		boolean resultExists = false;
		if (!operation.getResults().isEmpty()) {
			Result result = operation.getResults().get(0);
			if (TypeUtil.doesResultExist(result)) {
				String resultType = result.getType();
				String resultClassName = TypeUtil.getClassName(resultType);
				buf.put3(resultClassName+" response = proxy."+operation.getName()+"(");
				buf.put(CodeUtil.generateParameterString(operation));
				buf.putLine(");");
				buf.putLine3("System.out.println(\"Response: \"+response);");
				resultExists = true;
			}
		}
		
		if (!resultExists) {
			buf.put3("proxy."+operation.getName()+"(");
			buf.put(CodeUtil.generateParameterString(operation));
			buf.putLine(");");
		}

		buf.putLine2("} catch (Exception e) {");
		buf.putLine3("e.printStackTrace();");
		buf.putLine2("}");
		return buf.get();
	}

//	protected String generateParameterString(ModelOperation modelOperation) {
//		Buf buf = new Buf();
//		List<ModelParameter> parameters = modelOperation.getParameters();
//		Iterator<ModelParameter> iterator = parameters.iterator();
//		for (int i=0; iterator.hasNext(); i++) {
//			ModelParameter modelParameter = iterator.next();
//			String parameterClassName = modelParameter.getClassName();
//			String parameterName = NameUtil.uncapName(parameterClassName);
//			if (i > 0)
//				buf.put(", ");
//			buf.put(parameterClassName+" "+parameterName);
//		}
//		return buf.get();
//	}

}
