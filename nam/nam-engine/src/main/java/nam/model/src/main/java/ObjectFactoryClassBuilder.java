package nam.model.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.util.ElementUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;


public class ObjectFactoryClassBuilder extends AbstractBeanBuilder {

	private Namespace namespace;

	private boolean packageHasExtendedElements;

	
	public ObjectFactoryClassBuilder(GenerationContext context) {
		super(context);
	}
	
	public List<ModelClass> buildModelClasses(List<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		Module module = context.getModule();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getImported() == null || !namespace.getImported() || namespace.getUri().equals(module.getNamespace())) {
				ModelClass modelClass = buildModelClass(namespace);
				modelClasses.add(modelClass);
			}
		}
		return modelClasses;
	}

	public ModelClass buildModelClass(Namespace namespace) throws Exception {
		ModelClass modelClass = new ModelClass();
		this.namespace = namespace;
		packageName = ProjectLevelHelper.getPackageName(namespace.getUri());
		className = "ObjectFactory";
		beanName = NameUtil.uncapName(className);
		typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), beanName);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setType(typeName);
		modelClass.setNamespace(namespace.getUri());
		
		modelClass.addJavadoc("This object contains factory methods for each");
		modelClass.addJavadoc("Java content interface and Java element interface"); 
		modelClass.addJavadoc("generated in the org.sgiusa.model package."); 
		modelClass.addJavadoc("<p>An ObjectFactory allows you to programatically"); 
		modelClass.addJavadoc("construct new instances of the Java representation"); 
		modelClass.addJavadoc("for XML content. The Java representation of XML"); 
		modelClass.addJavadoc("content can consist of schema derived interfaces"); 
		modelClass.addJavadoc("and classes representing the binding of schema"); 
		modelClass.addJavadoc("type definitions, element declarations and model"); 
		modelClass.addJavadoc("groups.  Factory methods for each of these are"); 
		modelClass.addJavadoc("provided in this class.");
		modelClass.addJavadoc("");
		initializeClass(modelClass);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass) throws Exception {
		//initializeStaticFields(modelClass);
		//initializeStaticMethods(modelClass);
		initializeClassConstructors(modelClass);
		//initializeInstanceFields(modelClass);
		initializeInstanceMethods(modelClass);
		initializeClassAnnotations(modelClass);
		initializeImportedClasses(modelClass);
	}

	protected ModelConstructor createConstructor() {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addJavadoc("Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: "+packageName);
		modelConstructor.addJavadoc("");
		return modelConstructor;
	}

//	protected ModelAttribute createAttribute_EnumValue() {
//		ModelAttribute modelAttribute = new ModelAttribute();
//		modelAttribute.setModifiers(Modifier.PRIVATE+Modifier.FINAL);
//		modelAttribute.setClassName("String");
//		modelAttribute.setName("value");
//		modelAttribute.setStructure("item");
//		modelAttribute.setGenerateSetter(false);
//		return modelAttribute;
//	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("javax.xml.bind.annotation.XmlRegistry");
		if (packageHasExtendedElements) {
			modelClass.addImportedClass("javax.xml.bind.JAXBElement");
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlElementDecl");
			modelClass.addImportedClass("javax.xml.namespace.QName");
		}
	}

	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createXmlRegistry(modelClass));
	}

//	protected void initializeAttributeAnnotations(ModelClass modelClass, ModelAttribute modelAttribute, Element element, Attribute attribute) throws Exception {
//		List<ModelAnnotation> annotations = modelAttribute.getAnnotations();
//		//annotations.add(AnnotationUtil.createXmlAttribute(modelClass, modelAttribute));
//		annotations.add(AnnotationUtil.createXmlElement(modelClass, modelAttribute));
//		initializeXmlAdapterAnnotations(modelClass, modelAttribute, attribute);
//		//modelClass.addImportedClass("javax.xml.bind.annotation.XmlAttribute");
//		modelClass.addImportedClass("javax.xml.bind.annotation.XmlElement");
//	}

	protected void initializeInstanceMethods(ModelClass modelClass) throws Exception {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		ElementUtil.sortTypesByQualifiedName(elements);
		initializeInstanceMethods_BasicElementFactory(modelClass, elements);
		//initializeInstanceMethods_JAXBElementFactory(modelClass, NamespaceUtil.getElements(namespace));
	}

	protected void initializeInstanceMethods_BasicElementFactory(ModelClass modelClass, List<Element> elements) {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementClassName = ModelLayerHelper.getElementClassName(element);
			if (modelClass.isAbstract())
				continue;
			if (elementClassName.startsWith("Abstract"))
				continue;
			if (elementClassName.endsWith("Exception"))
				continue;
			ModelOperation modelOperation = createOperation_BasicElementFactory(modelClass, element);
			modelClass.addInstanceOperation(modelOperation);
		}
	}

	protected void initializeInstanceMethods_JAXBElementFactory(ModelClass modelClass, List<Element> elements) {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			modelClass.addInstanceOperation(createOperation_JAXBElementFactory(modelClass, element));
		}
	}
	
	protected ModelOperation createOperation_BasicElementFactory(ModelClass modelClass, Element element) {
		String elementType = element.getType();
		String className = TypeUtil.getClassName(elementType);
		String operationName = "create" + className;
		String resultType = className;
		String resultName = NameUtil.uncapName(className);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType(resultType);
		modelOperation.setResultName(resultName);
		modelOperation.addJavadoc("Create an instance of {@link "+className+"}");
		modelOperation.addJavadoc("");
		
		Buf buf = new Buf();
		buf.putLine2("return new "+className+"();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_JAXBElementFactory(ModelClass modelClass, Element element) {
		String elementType = element.getType();
		String packageName = TypeUtil.getPackageName(elementType);
		String className = TypeUtil.getClassName(elementType);
		String operationName = "create" + className;
		String parameterName = NameUtil.uncapName(className);
		String qnameFieldName = "_" + className + "_QNAME";
		String resultType = "JAXBElement<" + className + ">";
		String resultName = NameUtil.uncapName(className);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.addParameter(CodeUtil.createParameter(packageName, className, parameterName));
		modelOperation.setResultType(resultType);
		modelOperation.setResultName(resultName);
		
		Buf buf = new Buf();
		buf.putLine2("return new JAXBElement<"+className+">("+qnameFieldName+", "+className+".class, null, value);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

}
