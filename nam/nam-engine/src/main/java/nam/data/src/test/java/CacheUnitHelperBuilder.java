package nam.data.src.test.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Item;
import nam.model.ModelLayerHelper;
import nam.model.util.CacheUtil;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;


public class CacheUnitHelperBuilder extends AbstractDataUnitHelperBuilder {

	public CacheUnitHelperBuilder(GenerationContext context) {
		super(context);
	}

	protected void initialize() {
	}
	
	public ModelClass buildClass(Cache cache) throws Exception {
		return buildClass(cache, cache.getNamespace());
	}
	
	protected ModelClass buildClass(Cache cache, String namespace) throws Exception {
		String packageName = DataLayerHelper.getHelperPackageName(cache);
		String className = DataLayerHelper.getHelperClassName(cache);
		String beanName = DataLayerHelper.getHelperNameUncapped(cache);

		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		//modelClass.setType(daoType);
		initializeClass(modelClass, cache, namespace);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Cache cache, String namespace) throws Exception {
		List<Item> items = CacheUtil.getItems(cache);
		initializeImportedClasses(modelClass, cache, namespace, items);
		initializeClassAnnotations(modelClass);
		initializeInstanceConstructor(modelClass, cache, namespace, items);
		initializeInstanceFields(modelClass, cache, namespace, items);
		initializeInstanceMethods(modelClass, cache, namespace, items);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Cache cache, String namespace, List<Item> items) throws Exception {
		super.initializeImportedClasses(modelClass);
		
		modelClass.addImportedClass("javax.persistence.EntityManager");
		String mapperFixtureQualifiedName = DataLayerHelper.getMapperFixtureQualifiedName(namespace);
		modelClass.addImportedClass(mapperFixtureQualifiedName);
		
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(item);
			//String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
			//String modelFixtureQualifiedName = ModelLayerHelper.getModelFixtureQualifiedName(element);
			modelClass.addImportedClass(elementQualifiedName);
			//modelClass.addImportedClass(entityQualifiedName);
			//modelClass.addImportedClass(modelFixtureQualifiedName);
		}
	}

	protected void initializeInstanceConstructor(ModelClass modelClass, Cache cache, String namespace, List<Item> elements) { 
		modelClass.getInstanceConstructors().add(createInstanceConstructor(modelClass, cache, namespace, elements));
	}

	protected ModelConstructor createInstanceConstructor(ModelClass modelClass, Cache cache, String namespace, List<Item> items) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addParameter(createParameter("EntityManager", "entityManager"));
		modelConstructor.addInitialSource(createSourceCode_Constructor(items));
		modelClass.addImportedClass("javax.persistence.EntityManager");
		return modelConstructor;
	}
	
	protected String createSourceCode_Constructor(List<Item> elements) {
		Buf buf = new Buf();

		return buf.get();
	}

	protected void initializeInstanceFields(ModelClass modelClass, Cache cache, String namespace, List<Item> items) throws Exception {
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			initializeInstanceFields(modelClass, namespace, item);
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, String namespace, Item item) throws Exception {
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Cache cache, String namespace, List<Item> items) throws Exception {
	}

}
