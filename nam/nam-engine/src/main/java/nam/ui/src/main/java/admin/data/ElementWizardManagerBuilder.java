package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record Wizard Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementWizardManagerBuilder extends AbstractElementManagerBuilder {

	public ElementWizardManagerBuilder(GenerationContext context) {
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
		String className = elementName + "Wizard";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractDomainElementWizard<"+elementName+">");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		//initializeClassConstructors(modelClass, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Element type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		Project project = context.getProject();
		String namespace = project.getNamespace();
		String projectPackageName = NameUtil.getPackageNameFromNamespace(namespace);
		//if (type.getType().endsWith("user"))
		//	System.out.println();
		
		modelClass.addImportedClass(elementPackageName + ".Project");
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		modelClass.addImportedClass(elementPackageName + "." + elementNameUncapped + "." + elementClassName + "AddEvent");
		
		modelClass.addImportedClass("nam.ui.design.AbstractDomainElementWizard");
		modelClass.addImportedClass("nam.ui.design.SelectionContext");
		
		modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
		modelClass.addImportedClass("org.aries.util.NameUtil");
		
		List<Field> fields = ElementUtil.getFields(type);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field instanceof Reference == false)
				continue;
			
			String fieldPackageName = TypeUtil.getPackageName(field.getType());
			String fieldClassName = TypeUtil.getClassName(field.getType()) + "Util";
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
		modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");
		
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.ui.AbstractRecordManager");
		//modelClass.addImportedClass("org.aries.util.Validator");
		//modelClass.addImportedClass("org.aries.util.CollectionUtil");
		//modelClass.addImportedClass("nam.ui.design.PageManager");

		//modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
		//modelClass.addImportedClass("org.aries.util.NameUtil");
		
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
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"Wizard"));
		modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
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
		buf.putLine2("addPage(new "+elementClassName+"IdentificationSection(\""+elementNameUncapped+"Wizard\"));");
		buf.putLine2("addPage(new "+elementClassName+"ConfigurationSection(\""+elementNameUncapped+"Wizard\"));");
		buf.putLine2("addPage(new "+elementClassName+"DocumentationSection(\""+elementNameUncapped+"Wizard\"));");
		buf.putLine2("reset();");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceReference(createReference_ElementManager(element, "Data"));
		modelClass.addInstanceReference(createReference_ElementManager(element, "Page"));
		modelClass.addInstanceReference(createReference_ElementManager(element, "Event"));
		modelClass.addInstanceReference(createReference_SelectionContext(element));
	}

	public ModelReference createReference_ElementManager(Element element, String type) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		String namespace = context.getProject().getNamespace();
		String packageName = NameUtil.getPackageNameFromNamespace(namespace) + "";
		String className = elementClassName + type + "Manager";
		String elementName = elementNameUncapped + type + "Manager";
		
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
		modelClass.addInstanceOperation(createOperation_GetName(element));
		modelClass.addInstanceOperation(createOperation_GetUrlContext(element));
		modelClass.addInstanceOperation(createOperation_Initialize(element));
		modelClass.addInstanceOperation(createOperation_isBackEnabled(element));
		modelClass.addInstanceOperation(createOperation_isNextEnabled(element));
		modelClass.addInstanceOperation(createOperation_isFinishEnabled(element));
		modelClass.addInstanceOperation(createOperation_Refresh(element));
		modelClass.addInstanceOperation(createOperation_First(element));
		modelClass.addInstanceOperation(createOperation_Back(element));
		modelClass.addInstanceOperation(createOperation_Next(element));
		modelClass.addInstanceOperation(createOperation_isValid(element));
		modelClass.addInstanceOperation(createOperation_Finish(element));
		modelClass.addInstanceOperation(createOperation_Cancel(element));
		modelClass.addInstanceOperation(createOperation_PopulateDefaultValues(element));
		//modelClass.addInstanceOperation(createOperation_PostElementAddEvent(element));
	}

	protected ModelOperation createOperation_GetName(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getName");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+elementClassName+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_GetUrlContext(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getUrlContext");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementNameUncapped+"PageManager.get"+elementClassName+"WizardPage();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_Initialize(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("setOrigin(getSelectionContext().getUrl());");
		buf.putLine2("assignPages("+elementNameUncapped+"PageManager.getSections());");
		buf.putLine2("super.initialize("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_isBackEnabled(Element element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("isBackEnabled");
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2("return super.isBackEnabled();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_isNextEnabled(Element element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("isNextEnabled");
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2("return super.isNextEnabled();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_isFinishEnabled(Element element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("isFinishEnabled");
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2("return super.isFinishEnabled();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_Refresh(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refresh");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("String url = super.showPage();");
		buf.putLine2("selectionContext.setUrl(url);");
		buf.putLine2(elementNameUncapped+"PageManager.updateState();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_First(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("first");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("String url = super.first();");
		buf.putLine2(elementNameUncapped+"PageManager.updateState();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_Back(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("back");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("String url = super.back();");
		buf.putLine2(elementNameUncapped+"PageManager.updateState();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_Next(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("next");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("String url = super.next();");
		buf.putLine2(elementNameUncapped+"PageManager.updateState();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_isValid(Element element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("isValid");
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2("return super.isValid();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_Finish(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("finish");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = getInstance();");
		//buf.putLine2("post"+elementClassName+"AddEvent("+elementNameUncapped+");");
		buf.putLine2(elementNameUncapped+"DataManager.save"+elementClassName+"("+elementNameUncapped+");");
		buf.putLine2(elementNameUncapped+"EventManager.fireSavedEvent("+elementNameUncapped+");");
		buf.putLine2("String url = selectionContext.popOrigin();");
		//buf.putLine2("String url = "+elementNameUncapped+"PageManager.get"+elementClassName+"ManagementPage();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_Cancel(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("cancel");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = getInstance();");
		buf.putLine2("//TODO take this out soon");
		buf.putLine2("if ("+elementNameUncapped+" == null)");
		buf.putLine2("	"+elementNameUncapped+" = new "+elementClassName+"();");
		buf.putLine2(elementNameUncapped+"EventManager.fireCancelledEvent("+elementNameUncapped+");");
		buf.putLine2("String url = selectionContext.popOrigin();");
		//buf.putLine2("String url = "+elementNameUncapped+"PageManager.get"+elementClassName+"ManagementPage();");
		buf.putLine2("return url;");
		//buf.putLine2("return super.cancel();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_PopulateDefaultValues(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("populateDefaultValues");
		modelOperation.setResultType("String");

		Buf buf = new Buf();
		//buf.putLine2(elementClassName+" "+elementNameUncapped+" = getInstance();");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("String name = "+elementNameUncapped+".getName();");
		buf.putLine2("if (StringUtils.isEmpty(name)) {");
		buf.putLine2("	display = getFromSession(\"display\");");
		buf.putLine2("	display.setModule(\""+elementNameUncapped+"Wizard\");");
		buf.putLine2("	display.error(\""+elementClassName+" name must be specified\");");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		buf.putLine2("");
		//buf.putLine2("SelectionContext selectionContext = getSelectionContext();");
		buf.putLine2("Project project = selectionContext.getSelection(\"project\");");
		buf.putLine2("");
		buf.putLine2("String nameCapped = NameUtil.capName(name);");
		buf.putLine2("String nameUncapped = NameUtil.uncapName(name);");
		buf.putLine2("return getUrl();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
//	protected ModelOperation createOperation_PostElementAddEvent(Element element) throws Exception {
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//		
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PROTECTED);
//		modelOperation.setName("post"+elementClassName+"AddEvent");
//		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
//		
//		Buf buf = new Buf();
//		buf.putLine2(elementClassName+"AddEvent event = new "+elementClassName+"AddEvent();");
//		buf.putLine2("event.set"+elementClassName+"("+elementNameUncapped+");");
//		buf.putLine2(elementNameUncapped+"AddEvent.fire(event);");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
	
}
