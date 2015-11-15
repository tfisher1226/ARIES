package nam.service.src.main.java;

import java.util.List;

import nam.model.Unit;
import aries.codegen.AbstractBeanBuilder;
import aries.codegen.MethodType;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;


/**
 * Builds a DataUnit Manager {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * <h3>Properties</h3>
 * The following properties can be used to configure execution of this builder: 
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * <h3>Dependencies</h3>
 * Execution of this builder must come after the following builders: 
 * <ul>
 * <li>ProcessClassBuilder</li>
 * </ul>
 * 
 * @author tfisher
 */
public abstract class AbstractDataUnitManagerBuilder extends AbstractBeanBuilder {

	public AbstractDataUnitManagerBuilder(GenerationContext context) {
		super(context);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		this.modelUnit = modelClass;
		modelClass.addImportedClass("java.util.Collection");
		
		//modelClass.addImportedClass("org.aries.tx.TransactionHelper");
		modelClass.addImportedClass("common.tx.state.AbstractStateManager");
		modelClass.addImportedClass("common.tx.state.ActionState");
		modelClass.addImportedClass("common.jmx.MBeanUtil");
		
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
			modelClass.addImportedClass("javax.ejb.Startup");
			//modelClass.addImportedClass("javax.ejb.Singleton");
			modelClass.addImportedClass("javax.ejb.Stateful");
			modelClass.addImportedClass("javax.ejb.LocalBean");
			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
			modelClass.addImportedClass("javax.ejb.TransactionAttribute");
			modelClass.addImportedClass("javax.ejb.TransactionManagement");
			modelClass.addImportedClass("javax.annotation.PostConstruct");
			modelClass.addImportedClass("javax.annotation.PreDestroy");
			modelClass.addImportedClass("javax.inject.Inject");
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
			classAnnotations.add(AnnotationUtil.createStartupAnnotation());
			//classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
			classAnnotations.add(AnnotationUtil.createStatefulAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
			classAnnotations.add(AnnotationUtil.createTransactionAttributeAnnotation());
			break;
			
		case SEAM:
			break;
		}
	}

	protected String getDataAccessMethodSuffix(MethodType methodType, String structure) {
		switch (methodType) {
		//case GetAsItemByKey:
		//case RemoveAsItemByKey: 
		//	return "";
		//case GetAsListByKeys:
		//case RemoveAsListByKeys: 
		//	return "";
		default: 
			return super.getDataAccessMethodSuffix(methodType, structure);
		}
	}
	
//	protected <T extends Type> void createMethods_DataAccessOLD(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
//		boolean isCacheRelated = DataLayerHelper.isCacheRelated(this.getClass()); 
//		String structure = dataItem.getStructure();
//
//		if (structure.equals("item")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			
//		} else if (structure.equals("list")) {
//			modelUnit.addImportedClass("java.util.Set");
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			if (isCacheRelated)
//				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//
//		} else if (structure.equals("set")) {
//			modelUnit.addImportedClass("java.util.Set");
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			if (isCacheRelated)
//				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//
//		} else if (structure.equals("set")) {
//			modelUnit.addImportedClass("java.util.Set");
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			if (isCacheRelated)
//				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//
//		} else if (structure.equals("map")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsMap));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemByKey));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByKeys));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsMapByKeys));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsMap));
//			if (isCacheRelated)
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
