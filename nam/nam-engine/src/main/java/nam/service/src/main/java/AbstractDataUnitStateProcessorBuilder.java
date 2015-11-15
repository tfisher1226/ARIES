package nam.service.src.main.java;

import java.util.List;

import nam.model.Type;
import nam.model.Unit;
import aries.codegen.AbstractBeanBuilder;
import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelUnit;


/**
 * Builds a DataUnit State Processor {@link ModelClass} object given a {@link Unit} Specification as input;
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
public abstract class AbstractDataUnitStateProcessorBuilder extends AbstractBeanBuilder {

	public AbstractDataUnitStateProcessorBuilder(GenerationContext context) {
		super(context);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		//modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Set");
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
			//modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
			//modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");
			modelClass.addImportedClass("javax.ejb.Startup");
			modelClass.addImportedClass("javax.ejb.Singleton");
			modelClass.addImportedClass("javax.ejb.LocalBean");
			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
			//modelClass.addImportedClass("javax.ejb.TransactionAttribute");
			//modelClass.addImportedClass("javax.ejb.TransactionManagement");
			//modelClass.addImportedClass("javax.annotation.PostConstruct");
			//modelClass.addImportedClass("javax.annotation.PreDestroy");
			//modelClass.addImportedClass("javax.inject.Inject");
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
			classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			//classAnnotations.add(AnnotationUtil.createTransactionAttributeAnnotation());
			//classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
			break;
			
		case SEAM:
			break;
		}
	}

	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
		boolean hasCriteriaElement = CodeUtil.hasCriteriaElement(dataItem);
		String structure = dataItem.getStructure();
		
		if (structure.equals("item")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));

		} else if (structure.equals("list")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemById));
			if (hasCriteriaElement) {
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByIds));
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
			}
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemById));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));
			
		} else if (structure.equals("set")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemById));
			if (hasCriteriaElement) {
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByIds));
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
			}
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemById));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));

		} else if (structure.equals("map")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemById));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByIds));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemByKey));
			if (hasCriteriaElement) {
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsMapByKeys));
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
			}
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemById));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemByKey));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByKeys));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));
		}
	}
	
	protected String getDataAccessMethodPrefix(MethodType methodType, String structure) {
		String prefix = super.getDataAccessMethodPrefix(methodType, structure);
		return prefix + "Pending";
	}
	
	
}
