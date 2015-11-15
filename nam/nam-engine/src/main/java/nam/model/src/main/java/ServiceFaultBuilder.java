package nam.model.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Attribute;
import nam.model.Fault;
import nam.model.Operation;
import nam.model.Service;
import nam.model.util.FieldUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelParameter;


/**
 * Builds a Service Fault {@link ModelClass} object given an {@link Exception} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceFaultBuilder extends AbstractBeanBuilder {

	private ServiceFaultProvider serviceFaultProvider;
	
	
	public ServiceFaultBuilder(GenerationContext context) {
		serviceFaultProvider = new ServiceFaultProvider(context);
		this.context = context;
	}

	public List<ModelClass> buildClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = (Operation) iterator.next();
			modelClasses.addAll(buildClasses(operation));
		}
		return modelClasses; 
	}

	public List<ModelClass> buildClasses(Operation operation) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Fault> faults = operation.getFaults();
		Iterator<Fault> iterator = faults.iterator();
		while (iterator.hasNext()) {
			Fault fault = iterator.next();
			modelClasses.add(buildClass(fault));
		}
		return modelClasses;
	}

	public ModelClass buildClass(Fault fault) throws Exception {
		String faultType = fault.getType();
		//String packageName = context.getModule().getGroupId();
		String packageName = TypeUtil.getPackageName(faultType);
		String className = TypeUtil.getClassName(faultType);
		//String faultName = TypeUtil.getLocalPart(fault.getType());
		String faultName = NameUtil.uncapName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(faultType);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(faultName);
		String superType = fault.getExtends();
		String parentClassName = superType;
		if (!CodeGenUtil.isJavaDefaultType(superType))
			parentClassName = TypeUtil.getClassName(superType);
		if (parentClassName == null)
			parentClassName = "Exception";
		modelClass.setParentClassName(parentClassName);
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass("java.io.Serializable");
		initializeClass(modelClass, fault);
		return modelClass;
	}

	protected void initializeClass(ModelClass modelClass, Fault fault) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, fault);
		initializeClassConstructors(modelClass, fault);
		initializeImportedClasses(modelClass, fault);
		initializeStaticAttributes(modelClass, fault);
		initializeInstanceAttributes(modelClass, fault);
		initializeInstanceOperations(modelClass, fault);
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Fault fault) throws Exception {
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addClassAnnotation(AnnotationUtil.createApplicationExceptionAnnotation(fault));
			modelClass.addImportedClass("javax.ejb.ApplicationException");
			//modelClass.addClassAnnotation(AnnotationUtil.createWebFaultAnnotation(fault));
			//modelClass.addImportedClass("javax.xml.ws.WebFault");
		}
	}

	protected void initializeClassConstructors(ModelClass modelClass, Fault fault) {
		modelClass.addInstanceConstructor(createConstructor());
		modelClass.addInstanceConstructor(createConstructorWithMessage());
		modelClass.addInstanceConstructor(createConstructorWithCause());
		modelClass.addInstanceConstructor(createConstructorWithMessageAndCause());
		//modelClass.addInstanceConstructor(createConstructorWithMessageAndFields(fault));
		//modelClass.addInstanceConstructor(createConstructorWithMessageAndFieldsAndCause(fault));
	}

	protected ModelConstructor createConstructor() {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessage() {
		ModelConstructor modelConstructor = createConstructor();
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("message");
		modelParameter.setClassName("String");
		Buf buf = new Buf();
		buf.putLine2("super(message);");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithCause() {
		ModelConstructor modelConstructor = createConstructor();
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		Buf buf = new Buf();
		buf.putLine2("super(cause.getMessage(), cause);");
		modelConstructor.getInitialSource().clear();
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessageAndCause() {
		ModelConstructor modelConstructor = createConstructorWithMessage();
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		Buf buf = new Buf();
		buf.putLine2("super(message, cause);");
		modelConstructor.getInitialSource().clear();
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessageAndFields(Fault fault) {
		ModelConstructor modelConstructor = createConstructorWithMessage();
		addFieldParametersToConstructor(modelConstructor, fault.getAttributes());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessageAndFieldsAndCause(Fault fault) {
		ModelConstructor modelConstructor = createConstructorWithMessageAndFields(fault);
		Buf buf = new Buf();
		buf.putLine2("super(message, cause);");
		List<String> initialSource = modelConstructor.getInitialSource();
		initialSource.set(0, buf.get());
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		return modelConstructor;
	}

	protected void addFieldParametersToConstructor(ModelConstructor modelConstructor, List<Attribute> attributes) {
		Buf buf = new Buf();
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			ModelParameter modelParameter = new ModelParameter();
			modelConstructor.getParameters().add(modelParameter);
			modelParameter.setName(attribute.getName());
			modelParameter.setClassName(TypeUtil.getClassName(attribute.getType()));
			buf.putLine2("this."+attribute.getName()+" = "+attribute.getName()+";");
		}
		modelConstructor.addInitialSource(buf.get());
	}

	protected void initializeImportedClasses(ModelClass modelClass, Fault fault) throws Exception {
		if (modelClass.getParentClassName() != null)
			modelClass.addImportedClass(modelClass.getParentClassName());
		String faultPackageName = TypeUtil.getPackageName(fault.getType());
		if (!modelClass.getPackageName().equals(faultPackageName))
			modelClass.addImportedClass(TypeUtil.getJavaName(fault.getType()));
	}

	protected void initializeStaticAttributes(ModelClass modelClass, Fault fault) {
		CodeUtil.addSerialVersionUIDField(modelClass);
	}

	protected void initializeInstanceAttributes(ModelClass modelClass, Fault fault) {
		List<Attribute> attributes = fault.getAttributes();
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			ModelAttribute modelAttribute = new ModelAttribute();
			modelAttribute.setModifiers(Modifier.PRIVATE);
			String packageName = TypeUtil.getPackageName(attribute.getType());
			String className = TypeUtil.getClassName(attribute.getType());
			modelAttribute.setPackageName(packageName);
			modelAttribute.setClassName(className);
			modelAttribute.setName(attribute.getName());
			modelAttribute.setKeyEnabled(FieldUtil.isUseForEquals(attribute));
			modelAttribute.setUnique(FieldUtil.isUnique(attribute));
			modelClass.addInstanceAttribute(modelAttribute);
		}
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Fault fault) {
		//modelClass.addInstanceOperation(createOperation_Equals(modelClass, fault));
		//modelClass.addInstanceOperation(createOperation_Hashcode(modelClass, fault));
	}

//	protected ModelOperation createOperation_Equals() {
//		return null;
//	}
//
//	protected ModelOperation createOperation_HashCode() {
//		return null;
//	}

}
