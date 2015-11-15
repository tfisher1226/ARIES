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
 * Builds an Element Validator Object Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementValidatorBuilder extends AbstractBeanBuilder {

	public ElementValidatorBuilder(GenerationContext context) {
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
		String className = elementName + "Validator";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.setParentClassName("AbstractValidator");
		modelClass.addImplementedInterface("Validator");
		modelClass.addImplementedInterface("Serializable");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Type element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.ui.validate.AbstractValidator");
		
		//modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
		
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.faces.Validator");
//		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("javax.enterprise.context.ApplicationScoped");
		modelClass.addImportedClass("javax.inject.Named");
		
		modelClass.addImportedClass("javax.faces.component.UIComponent");
		modelClass.addImportedClass("javax.faces.context.FacesContext");
		modelClass.addImportedClass("javax.faces.validator.ValidatorException");
		modelClass.addImportedClass("javax.faces.validator.Validator");

		modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImportedClass("java.util.Collection");
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		modelClass.getClassAnnotations().add(AnnotationUtil.createApplicationScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"Validator"));
		
		////modelClass.getClassAnnotations().add(AnnotationUtil.createStartupAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createValidatorAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createAutoCreateAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"Validator"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.APPLICATION));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
	}
	
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Type element) throws Exception {
		modelClass.addInstanceOperation(createOperation_validate(element));
	}

	protected ModelOperation createOperation_validate(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate");
		modelOperation.addException("ValidatorException");
		modelOperation.addParameter(createParameter("FacesContext", "facesContext"));
		modelOperation.addParameter(createParameter("UIComponent", "uiComponent"));
		modelOperation.addParameter(createParameter("Object", "value"));
		
		Buf buf = new Buf();
		buf.putLine2("resetState();");
		buf.putLine2("if (value != null) {");
		buf.putLine2("	if (value instanceof "+elementClassName+" == false)");
		buf.putLine2("		throwError(\"Unexpected value: \"+value);");
		if (!ElementUtil.isEnumeration(element)) {
			buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = ("+elementClassName+") value;");
			buf.putLine2("	if ("+elementClassName+"Util.validate("+elementNameUncapped+") == false) {");
			//buf.putLine2("		org.aries.ui.validate.Validator validator = org.aries.ui.validate.Validator.getValidator();");
			buf.putLine2("		Collection<String> messages = getValidator().getMessages();");
			buf.putLine2("		getDisplay().addErrors(messages);");
			buf.putLine2("		throwErrors(messages);");
			buf.putLine2("	}");
		}
		buf.putLine2("}");
		
//		buf.putLine2("	List<String> errors = "+elementClassName+"Util.validate("+elementClassName+");");
//		buf.putLine2("	getDisplay().error(errors);");
//		buf.putLine2("	if (errors != null)");
//		buf.putLine2("		throwErrors(errors);");
//		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
