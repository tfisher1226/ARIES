package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;

import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Type;
import nam.model.util.ElementUtil;
import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds an Element Converter Object Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementConverterBuilder extends AbstractBeanBuilder {

	public ElementConverterBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Type element) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "Converter";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.setParentClassName("AbstractConverter");
		modelClass.addImplementedInterface("Converter");
		modelClass.addImplementedInterface("Serializable");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Type element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);

		modelClass.addImportedClass(elementPackageName + "." + elementClassName);

		if (ElementUtil.isEnumeration(type)) {
			//nothing for now
			
		} else if (ElementUtil.isElement(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
			modelClass.addImportedClass("org.aries.runtime.BeanContext");
		}
		
		modelClass.addImportedClass("org.aries.ui.convert.AbstractConverter");
		
		modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
		
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.faces.Converter");
//		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("javax.faces.component.UIComponent");
		modelClass.addImportedClass("javax.faces.context.FacesContext");
		modelClass.addImportedClass("javax.faces.convert.Converter");
		modelClass.addImportedClass("javax.faces.convert.FacesConverter");
		modelClass.addImportedClass("java.io.Serializable");
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		modelClass.getClassAnnotations().add(AnnotationUtil.createFacesConverterAnnotation(elementNameUncapped, elementClassName));

		////modelClass.getClassAnnotations().add(AnnotationUtil.createStartupAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createConverterAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createAutoCreateAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"Converter"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.APPLICATION));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
	}
	
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Type type) throws Exception {
		modelClass.addInstanceOperation(createOperation_getAsObjectTo(type));
		modelClass.addInstanceOperation(createOperation_getAsString(type));
	}

	protected ModelOperation createOperation_getAsObjectTo(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getAsObject");
		modelOperation.setResultType("Object");
		modelOperation.addParameter(createParameter("FacesContext", "facesContext"));
		modelOperation.addParameter(createParameter("UIComponent", "uiComponent"));
		modelOperation.addParameter(createParameter("String", "value"));
		
		Buf buf = new Buf();
		buf.putLine2("if (StringUtils.isEmpty(value))");
		buf.putLine2("	return null;");
		
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = "+elementClassName+".valueOf(value.toUpperCase());");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2(elementClassName+"ListManager "+elementNameUncapped+"ListManager = BeanContext.getFromSession(\""+elementNameUncapped+"ListManager\");");
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = "+elementNameUncapped+"ListManager.getRecordByName(value);");
		}

		buf.putLine2("return "+elementNameUncapped+";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getAsString(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getAsString");
		modelOperation.setResultType("String");
		modelOperation.addParameter(createParameter("FacesContext", "facesContext"));
		modelOperation.addParameter(createParameter("UIComponent", "uiComponent"));
		modelOperation.addParameter(createParameter("Object", "value"));
		
		Buf buf = new Buf();
		buf.putLine2("if (value == null)");
		buf.putLine2("	return null;");
		
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = null;");
			buf.putLine2("if (value instanceof String)");
			buf.putLine2("	"+elementNameUncapped+" = "+elementClassName+".valueOf((String) value);");
			buf.putLine2("else if (value instanceof "+elementClassName+")");
			buf.putLine2("	"+elementNameUncapped+" = ("+elementClassName+") value;");
			buf.putLine2("return "+elementNameUncapped+".value();");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = ("+elementClassName+") value;");
			buf.putLine2("return "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
