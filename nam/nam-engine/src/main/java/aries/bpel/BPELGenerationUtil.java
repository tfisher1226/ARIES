package aries.bpel;

import java.util.Collection;
import java.util.Iterator;

import javax.wsdl.Input;
import javax.xml.namespace.QName;

import nam.ProjectLevelHelper;
import nam.model.Service;

import org.aries.util.NameUtil;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Part;

import aries.codegen.util.Buf;


public class BPELGenerationUtil {

	public static String generateCallToRemoteOperation(ActivityGenerator<?> generator, Service remoteService, Operation remoteOperation, String inputVariableName, String outputVariableName) {
		//get source code for method body
		Buf buf = new Buf();
		//String portTypeName = TypeUtil.getTypeFromQName(portType.getQName());
		//String portTypeLocalName = TypeUtil.getClassName(portTypeName);
		String portTypePackageName = ProjectLevelHelper.getPackageName(remoteService.getNamespace());
		String portTypeLocalName = NameUtil.capName(remoteService.getName());
		String portTypeClassName = portTypeLocalName + "Client";
		
		buf.putLine2("ProxyLocator proxyLocator = BeanContext.get(\"org.aries.proxyLocator\");");
		buf.putLine2(portTypeClassName+" proxy = proxyLocator.get(\""+portTypeLocalName+"\");");
		//buf.putLine2(portTypeClassName+" proxy = new "+portTypeClassName+"();");
		buf.putLine2("proxy.setCorrelationId(getCorrelationId());");
		
		Input input = remoteOperation.getInput();
		QName qName = input.getMessage().getQName();
		input.getMessage().getParts();
		
		generator.addImportedClass("org.aries.process.ProxyLocator");
		generator.addImportedClass(portTypePackageName+"."+portTypeClassName);

		if (outputVariableName != null) {
			buf.put2(outputVariableName+" = proxy."+remoteOperation.getName()+"(");
		} else {
			buf.put2("proxy."+remoteOperation.getName()+"(");
		}

		Message inputMessage = (Message) remoteOperation.getInput().getMessage();
		@SuppressWarnings("unchecked") Collection<Part> parts = inputMessage.getParts().values();
		Iterator<Part> iterator = parts.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Part part = iterator.next();
			//String partName = NameUtil.uncapName(part.getName());
			//String variableName = "this."+inputVariableName+"_"+part.getName();
			//String variableName = CodeUtil.getVariableName(inputVariable);
			//String typeName = TypeUtil.getTypeFromMessagePart(part);
			//String packageName = TypeUtil.getPackageName(typeName);
			//String className = TypeUtil.getClassName(typeName);
			//ModelParameter parameter = new ModelParameter();
			//parameter.setPackageName(packageName);
			//parameter.setClassName(className);
			//parameter.setName(partName);
			//modelOperation.addParameter(parameter);
			//if (TypeUtil.isImportedClassRequired(packageName, className))
			//	addImportedClass(packageName+"."+className);
			if (i > 0)
				buf.put(", ");
			buf.put(inputVariableName);
		}
		buf.putLine(");");
		return buf.get();
	}

}
