package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Element;
import nam.model.Field;
import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelField;
import aries.generation.model.ModelInterface;


public abstract class AbstractDataUnitBuilder extends AbstractBeanBuilder {

	public AbstractDataUnitBuilder(GenerationContext context) {
		super(context);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface) throws Exception {
		modelInterface.addImportedClass("java.util.Collection");
	}
	
	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Set");
		//modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass("java.util.Collection");
		//modelClass.addImportedClass("java.util.ArrayList");
		//modelClass.addImportedClass("java.util.HashSet");
		
//		Project project = context.getProject();
//		Application application = context.getApplication();
//		List<Namespace> namespaces = ApplicationUtil.getNamespaces(application);
//		Iterator<Namespace> iterator = namespaces.iterator();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//			Module modelModule = ProjectUtil.getModelModule(project, namespace);
//			if (modelModule != null) {
//				String helperQualifiedName = ModelLayerHelper.getHelperQualifiedName(namespace);
//				modelClass.addImportedClass(helperQualifiedName);
//			}
//		}
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
			modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
			modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");
			if (DataLayerHelper.isCacheUnitRelated(this.getClass())) {
				modelClass.addImportedClass("javax.ejb.Singleton");
				modelClass.addImportedClass("javax.ejb.Local");
			} else {
				modelClass.addImportedClass("org.aries.Assert");
				modelClass.addImportedClass("javax.ejb.Stateful");
				modelClass.addImportedClass("javax.ejb.LocalBean");
				modelClass.addImportedClass("javax.inject.Inject");
			}
			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
			modelClass.addImportedClass("javax.ejb.TransactionAttribute");
			modelClass.addImportedClass("javax.ejb.TransactionManagement");
			modelClass.addImportedClass("javax.annotation.PostConstruct");
			//modelClass.addImportedClass("javax.ejb.Asynchronous");
			//modelClass.addImportedClass("javax.ejb.AccessTimeout");
			break;
			
		case SEAM:
			break;
		}
	}

	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			if (DataLayerHelper.isCacheUnitRelated(this.getClass())) {
				classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
				classAnnotations.add(AnnotationUtil.createLocalAnnotation(modelUnit.getName()));
			} else {
				classAnnotations.add(AnnotationUtil.createStatefulAnnotation());
				classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
			}
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			classAnnotations.add(AnnotationUtil.createTransactionAttributeAnnotation());
			classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
			break;
			
		case SEAM:
			break;
		}
	}

	protected void initializeClassConstructor(ModelClass modelClass, Cache cache) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		//constructor.addInitialSource(cacheUnitProvider.generateSource_Constructor(cache));
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeStaticFields(ModelClass modelClass) throws Exception {
		//nothing for now
	}

	protected void initializeFieldAnnotations(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		List<ModelAnnotation> fieldAnnotations = AnnotationUtil.createModelAnnotations(field.getAnnotations());
		modelField.setAnnotations(fieldAnnotations);
	}

//	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
//		String structure = dataItem.getStructure();
//		
//		//TODO add Java comment here for data type name
//		
//		if (structure.equals("item")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//
//		} else if (structure.equals("list")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsList));
//			if (this.getClass().equals(DataUnitBuilder.class))
//				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//			
//		} else if (structure.equals("set")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsList));
//			if (this.getClass().equals(DataUnitBuilder.class))
//				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//			
//		} else if (structure.equals("map")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsMap));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByKeys));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsMapByKeys));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsMap));
//			if (this.getClass().equals(CacheUnitBuilder.class))
//				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsMap));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemByKey));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByKeys));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));
//		}
//	}
	
	
}
