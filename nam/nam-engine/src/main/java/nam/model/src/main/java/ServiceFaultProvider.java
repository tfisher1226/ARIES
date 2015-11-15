package nam.model.src.main.java;

import java.util.Iterator;
import java.util.List;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class ServiceFaultProvider extends AbstractBeanProvider {

	public ServiceFaultProvider(GenerationContext context) {
		super(context);
	}

//	public String generate(ModelClass modelClass, Service service) throws Exception {
//		Buf buf = new Buf();
//		List<Operation> operations = service.getOperations();
//		Iterator<Operation> iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			Operation operation = iterator.next();
//			buf.put(getSource(modelClass, operation));
//		}
//		return buf.get();
//	}

	protected String generate(ModelClass modelClass, ModelOperation modelOperation) {
		String serviceName = modelClass.getPackageName()+"."+NameUtil.uncapName(modelClass.getClassName());
		String serviceContext = "infosgi-service";
		String serviceLocator = "org.aries.serviceLocator";
		
		modelClass.addImportedClass("org.aries.message.Message");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.registry.ServiceLocator");
		modelClass.addImportedClass("org.aries.service.ServiceProxy");
		modelClass.addImportedClass("org.aries.Transport");
		
		Buf buf = new Buf();
		buf.putLine2("Message requestMessage = new Message();");
		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			ModelParameter modelParameter = iterator.next();
			String name = modelParameter.getName();
			buf.putLine2("requestMessage.addPart(\""+name+"\", "+name+");");
		}
		buf.putLine2("ServiceLocator serviceLocator = BeanContext.get(\""+serviceLocator+"\");");
		buf.putLine2("ServiceProxy serviceProxy = serviceLocator.lookup(\"/"+serviceContext+"\", \"0.0.1-SNAPSHOT\", \""+serviceName+"\", Transport.RMI);");

		String returnType = modelOperation.getResultType();
		if (returnType != null && !returnType.equals("void")) {
			String className = NameUtil.getClassName(returnType);
			buf.putLine2("Message responseMessage = serviceProxy.invoke(requestMessage);");
			buf.putLine2(className+" result = responseMessage.getPart(\"response\");");
			buf.putLine2("return result;");
		} else {
			buf.putLine2("serviceProxy.invoke(requestMessage);");
		}
		return buf.get();
	}

}
