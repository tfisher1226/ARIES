package nam.data.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Properties;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;


/**
 * Builds an Element Importer Bean from an Aries Element or Aries Namespace;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * <li>generateInterface</li>
 * <li>useQualifiedContextNames</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ImporterBeanBuilder extends AbstractBeanBuilder {

	private ImporterBeanProvider provider;
	
	private boolean generateInterface;
	
	
	public ImporterBeanBuilder(GenerationContext context) {
		super(context);
		provider = new ImporterBeanProvider(context);
	}

	protected String getBeanContextName(Namespace namespace, Field field) {
		return getBeanContextName(namespace, field, "Importer");
	}

//	protected String getBeanContextName(ModelClass modelClass, String postfix) {
//		String elementPackageName = modelClass.getPackageName();
//		String elementName = NameUtil.uncapName(modelClass.getName()) + postfix;
//		if (context.isEnabled("useQualifiedContextNames")) {
//			String contextNamePrefix = NameUtil.getQualifiedContextNamePrefix(elementPackageName, 3);
//			return contextNamePrefix + "." + elementName;
//		}
//		return elementName;
//	}
	

	/*
	 * Importer interface creation
	 * -----------------------------
	 */
	
	public List<ModelInterface> buildInterfaces(List<Unit> units) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			modelInterfaces.addAll(buildInterfaces(unit));
		}
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfaces(Unit unit) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		List<Element> elements = ElementUtil.getElements(unit.getElements());
		modelInterfaces.addAll(buildInterfaces(unit, elements));
		return modelInterfaces;
	}
	
	protected Collection<? extends ModelInterface> buildInterfaces(Unit unit, List<Element> elements) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Element> iterator = elements.iterator();
		this.namespace = unit.getNamespace();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isTransient(element)) {
				ModelInterface modelInterface = buildInterface(namespace, element);
				modelInterfaces.add(modelInterface);
			}
		}
		return modelInterfaces;
	}

	public ModelInterface buildInterface(Namespace namespace, Element element) throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		String packageName = DataLayerHelper.getMapperPackageName(namespace);
		String interfaceName = DataLayerHelper.getMapperInterfaceName(element);
		String parentInterfaceName = DataLayerHelper.getMapperParentInterfaceName(element);

		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		if (parentInterfaceName != null)
			modelInterface.addExtendedInterface(parentInterfaceName);
		modelInterface.setName(NameUtil.uncapName(interfaceName));
		initializeInterface(modelInterface, namespace, element);
		return modelInterface;
	}
	
	protected void initializeInterface(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		//initializeInterfaceAnnotations(modelInterface);
		initializeImportedClasses(modelInterface, namespace, element);
		initializeInterfaceMethods(modelInterface, element);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		String parentClassName = DataLayerHelper.getMapperParentInterfaceName(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getEntityClassName(element);

		if (parentClassName != null)
			modelInterface.addImportedClass(parentClassName);
		modelInterface.addImportedClass(elementPackageName + "." + elementClassName);
		modelInterface.addImportedClass(entityPackageName + "." + entityClassName);
		modelInterface.addImportedClass("java.util.Collection");
		modelInterface.addImportedClass("java.util.List");
		
		Set<String> set = new HashSet<String>();
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldType = field.getType();
			if (!provider.isImportRequiredForField(element, field) && !FieldUtil.isInverse(field))
				continue;
			
			String structure = field.getStructure();
			if (structure.equals("list")) {
				modelInterface.addImportedClass("java.util.List");
				modelInterface.addImportedClass("java.util.ArrayList");
			} else if (structure.equals("set")) {
				modelInterface.addImportedClass("java.util.Set");
				modelInterface.addImportedClass("java.util.HashSet");
			} else if (structure.equals("map")) {
				modelInterface.addImportedClass("java.util.Map");
				modelInterface.addImportedClass("java.util.HashMap");
			}
			
			if (element.getType().equals(fieldType))
				continue;
			if (set.contains(fieldType))
				continue;

			String mapperFieldPackageName = DataLayerHelper.getMapperPackageName(field);
			String mapperFieldInterfaceName = DataLayerHelper.getMapperInterfaceName(field);
			modelInterface.addImportedClass(mapperFieldPackageName + "." + mapperFieldInterfaceName);
			set.add(fieldType);
			
//			if (field.getName().toLowerCase().startsWith("group"))
//				System.out.println();

			String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldEntityPackageName = DataLayerHelper.getFieldEntityPackageName(field);
			String fieldEntityClassName = DataLayerHelper.getFieldEntityClassName(field);
			
			if (!element.getType().equals(fieldType)) {
				modelInterface.addImportedClass(fieldPackageName + "." + fieldClassName);
				modelInterface.addImportedClass(fieldEntityPackageName + "." + fieldEntityClassName);
			}
		}
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Element element) throws Exception {
		modelInterface.addInstanceOperation(createInstanceOperation_Execute(element, true));
	}
	
	
	/*
	 * Importer class creation
	 * -------------------------
	 */
	
	public Collection<ModelClass> buildClasses(Unit unit) throws Exception {
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		modelClasses.addAll(buildClasses(unit.getNamespace()));
		modelClasses.addAll(buildClasses(unit.getNamespace(), unit.getElements()));
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelClasses.addAll(buildClasses(namespace));
		}
		return modelClasses;
	}
	
	public Set<ModelClass> buildClasses(Namespace namespace) throws Exception {
		this.namespace = namespace;
		
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		if (context.isEnabled("generate-imported-namespaces"))
			modelClasses.addAll(buildClasses(namespace.getImports()));

		Properties properties = NamespaceUtil.getProperties(namespace);
		boolean isImported = NamespaceUtil.isImported(namespace);
		//boolean isImported = PropertyUtil.isEnabled(properties, "imported");
		if (!context.isEnabled("generate-imported-namespaces") && isImported)
			return modelClasses;
		
		List<Element> elements = NamespaceUtil.getElements(namespace);
		modelClasses.addAll(buildClasses(namespace, elements));
		return modelClasses;
	}
	
	public Set<ModelClass> buildClasses(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildClasses(namespace, list);
	}
	
	public Set<ModelClass> buildClasses(Namespace namespace, List<Element> elements) throws Exception {
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		Iterator<Element> iterator = elements.iterator();
		this.namespace = namespace;
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isRoot(element))
				continue;
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			ModelClass modelClass = buildClass(namespace, element);
			modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Namespace namespace, Element element) throws Exception {
		String packageName = DataLayerHelper.getImporterPackageName(namespace);
		String interfaceName = DataLayerHelper.getImporterInterfaceName(element);
		String className = DataLayerHelper.getImporterClassName(element);
		String parentClassName = DataLayerHelper.getImporterParentClassName(element);
		this.namespace = namespace;
		
		ModelClass modelClass = new ModelClass();
		modelClass.setType(element.getType());
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		if (parentClassName != null)
			modelClass.setParentClassName(parentClassName);
		modelClass.setName(NameUtil.uncapName(interfaceName));
		if (generateInterface)
			modelClass.addImplementedInterface(packageName + "." + interfaceName);
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass("java.io.Serializable");
		initializeClass(modelClass, namespace, element);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		initializeClassAnnotations(modelClass, element);
		initializeImportedClasses(modelClass, namespace, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceMethods(modelClass, element);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getDataLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStatelessAnnotation());
			String className = ModelLayerHelper.getElementTypeLocalPartCapped(element) + "Importer";
			classAnnotations.add(AnnotationUtil.createLocalAnnotation(className));
			break;
		case SEAM:
			//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.APPLICATION));
			//classAnnotations.add(AnnotationUtil.createNameAnnotation(getBeanContextName(modelClass)));
			break;
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		String parentClassName = DataLayerHelper.getImporterParentClassName(element);
//		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
//		String entityClassName = DataLayerHelper.getEntityClassName(element);
//		String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
//		String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);

		if (parentClassName != null)
			modelClass.addImportedClass(parentClassName);
//		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
//		modelClass.addImportedClass(entityPackageName + "." + entityClassName);
//		modelClass.addImportedClass(mapperPackageName + "." + mapperInterfaceName);
//		modelClass.addImportedClass("java.util.ArrayList");
//		modelClass.addImportedClass("java.util.Collection");
//		modelClass.addImportedClass("java.util.List");
		
		switch (context.getDataLayerBeanType()) {
		case EJB:
			modelClass.addImportedClass("javax.ejb.Local");
			modelClass.addImportedClass("javax.ejb.Stateless");
			break;
		case SEAM:
			modelClass.addImportedClass("org.jboss.seam.ScopeType");
			modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//			modelClass.addImportedClass("org.jboss.seam.annotations.In");
			modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			break;
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
	}
	
	protected void initializeInstanceMethods(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceOperation(createInstanceOperation_Execute(element, false));
	}	

	
	/*
	 * Operation creation methods
	 * --------------------------
	 */
	
	/*
	 * execute() methods
	 */
	
	protected ModelOperation createInstanceOperation_Execute(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("import"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		//modelOperation.addParameter(createParameter(entityClassName, elementName+"Entity"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_Execute(element, null));
		return modelOperation;
	}

}
