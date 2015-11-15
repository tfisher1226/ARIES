package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Event Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementEventManagerBuilder extends AbstractElementManagerBuilder {

	public ElementEventManagerBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Type type) throws Exception {
		if (!ElementUtil.isElement(type))
			return null;

		Element element = (Element) type;
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "EventManager";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractEventManager<"+elementName+">");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Element type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		
		//if (type.getType().endsWith("user"))
		//	System.out.println();
		
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		//modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		modelClass.addImportedClass("nam.ui.design.SelectionContext");
		modelClass.addImportedClass("nam.ui.design.AbstractEventManager");

		List<Field> fields = ElementUtil.getFields(type);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field instanceof Reference == false)
				continue;
			
			String fieldPackageName = TypeUtil.getPackageName(field.getType());
			//String fieldClassName = TypeUtil.getClassName(field.getType()) + "Util";
			//modelClass.addImportedClass(fieldPackageName + ".util." + fieldClassName);
		}

		if (ElementUtil.isRoot(type)) {
			//modelClass.addImportedClass("org.aries.Assert");

		} else if (ElementUtil.isElement(type)) {

		} else if (ElementUtil.isEnumeration(type)) {
		}
		
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Begin");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.In");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Observer");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Out");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		//modelClass.addImportedClass("javax.enterprise.event.Event");
		//modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");
		
//		modelClass.addImportedClass("org.aries.runtime.BeanContext");
//		modelClass.addImportedClass("org.aries.ui.Display");
//		modelClass.addImportedClass("org.aries.util.Validator");
//		//modelClass.addImportedClass("org.aries.util.CollectionUtil");
//		modelClass.addImportedClass("org.aries.ui.event.Add");
//		modelClass.addImportedClass("org.aries.ui.event.Remove");
//		modelClass.addImportedClass("org.aries.ui.event.Selected");
//		modelClass.addImportedClass("org.aries.ui.event.Updated");
		
		//modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Map");
		super.initializeImportedClasses(modelClass);
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		//modelClass.getClassAnnotations().add(AnnotationUtil.createStartupAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createAnnotation("AutoCreate"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"InfoManager"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createRestrictAnnotation("#{identity.loggedIn}"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressWarning("serial", "unused"));
		
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"EventManager"));
	}

	
	/*
	 * Class Constructor(s)
	 */
	
	protected void initializeClassConstructors(ModelClass modelClass, Type element) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(element));
	}
	
	protected ModelConstructor createConstructor(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		buf.putLine2("setInstanceName(\""+elementNameUncapped+"\");");
		//buf.putLine2("setCancelEvent(\"org.aries.cancel"+elementClassName+"\");");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceReference(createReference_SelectionContext(element));
	}
	
	public ModelReference createReference_SelectionContext(Element element) {
		String packageName = "nam.ui.design";
		String className = "SelectionContext";
		String elementName = "selectionContext";
		
		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceOperation(createOperation_getInstance(element));
		modelClass.addInstanceOperation(createOperation_removeElement(element));
	}

	protected ModelOperation createOperation_getInstance(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getInstance");
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return selectionContext.getSelection(\""+elementNameUncapped+"\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_removeElement(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = getInstance();");
		buf.putLine2("fireRemoveEvent("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
}
