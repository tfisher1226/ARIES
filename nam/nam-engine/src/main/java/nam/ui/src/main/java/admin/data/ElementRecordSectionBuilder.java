package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.ViewUtil;
import nam.ui.Relation;
import nam.ui.View;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record List Object Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementRecordSectionBuilder extends AbstractBeanBuilder {

	public ElementRecordSectionBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public Collection<ModelClass> buildClasses(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);

		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		if (!ElementUtil.isEnumeration(type)) {
			modelClasses.add(buildOverviewSectionClass(type));
			modelClasses.add(buildIdentificationSectionClass(type));
			modelClasses.add(buildConfigurationSectionClass(type));
			modelClasses.add(buildDocumentationSectionClass(type));
		}
		
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
				modelClasses.add(buildClass(type, childNameCappedPlural));
			}
		}
		return modelClasses;
	}

	protected ModelClass buildOverviewSectionClass(Type type) throws Exception {
		return buildClass(type, "Overview");
	}

	protected ModelClass buildIdentificationSectionClass(Type type) throws Exception {
		return buildClass(type, "Identification");
	}

	protected ModelClass buildConfigurationSectionClass(Type type) throws Exception {
		return buildClass(type, "Configuration");
	}

	protected ModelClass buildDocumentationSectionClass(Type type) throws Exception {
		return buildClass(type, "Documentation");
	}

	public ModelClass buildClass(Type type, String section) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "Record_" + section + "Section";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.setParentClassName("AbstractWizardPage<"+elementClassName+">");
		modelClass.addImplementedInterface("Serializable");
		initializeClass(modelClass, type, section);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Type type, String section) throws Exception {
		initializeImportedClasses(modelClass, type);
		initializeClassAnnotations(modelClass, type, section);
		initializeClassConstructors(modelClass, type, section);
		initializeInstanceFields(modelClass, type, section);
		initializeInstanceOperations(modelClass, type, section);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);

		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		
		if (ElementUtil.isEnumeration(type)) {
			//nothing for now
			
		} else if (ElementUtil.isElement(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		}
		
		modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
		modelClass.addImportedClass("org.aries.ui.AbstractWizardPage");
		modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		//modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type element, String section) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped + section + "Section"));
	}
	
	
	/*
	 * Class Constructor(s)
	 */
	
	protected void initializeClassConstructors(ModelClass modelClass, Type element, String section) throws Exception {
		ModelConstructor modelConstructor = createConstructor(element, section);
		modelClass.addInstanceConstructor(modelConstructor);
	}
	
	protected ModelConstructor createConstructor(Type element, String section) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String sectionUncapped = NameUtil.uncapName(section);
		
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		buf.putLine2("setName(\""+section+"\");");
		buf.putLine2("setUrl(\""+sectionUncapped+"\");");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Type element, String section) throws Exception {
		modelClass.addInstanceReference(createReference_Element(element));
	}
	
	public ModelReference createReference_Element(Type element) {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String className = ModelLayerHelper.getElementClassName(element);
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(true);
		modelReference.setGenerateSetter(true);
		return modelReference;
	}
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Type element, String section) throws Exception {
		modelClass.addInstanceOperation(createOperation_initialize(element, section));
		modelClass.addInstanceOperation(createOperation_validate(element, section));
	}
	
	protected ModelOperation createOperation_initialize(Type element, String section) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//TODO this is "too crude" - externalize this logic for all buttons
		boolean backEnabled = !section.equals("Identification") && !section.equals("Overview");
		boolean populateVisible = section.equals("Identification");
		boolean populateEnabled = populateVisible; //&& testModeEnabled;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("setEnabled(true);");
		buf.putLine2("setBackEnabled("+backEnabled+");");
		buf.putLine2("setNextEnabled(true);");
		buf.putLine2("setFinishEnabled(false);");
		if (populateVisible)
			buf.putLine2("setPopulateVisible(true);");
		if (populateEnabled)
			buf.putLine2("setPopulateEnabled(true);");
		buf.putLine2("set"+elementClassName+"("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_validate(Type element, String section) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate");
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+" == null) {");
		buf.putLine2("	validator.missing(\""+elementNameCapped+"\");");
		buf.putLine2("} else {");
		buf.putLine2("}");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
