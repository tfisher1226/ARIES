package nam.service.src.main.java;

import nam.model.Cache;
import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Builds a Cache State module {@link ModelClass} object given a {@link Cache} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class AbstractDataUnitStateBuilder extends AbstractBeanBuilder {

	public AbstractDataUnitStateBuilder(GenerationContext context) {
		super(context);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.Iterator");
	}

//	protected <T extends Type> void createMethods_DataAccessOLD(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
//		String structure = dataItem.getStructure();
//		if (structure.equals("item")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			
//		} else if (structure.equals("list")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));
//			
//		} else if (structure.equals("set")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));
//
//		} else if (structure.equals("map")) {
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsMap));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemByKey));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByKeys));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsMapByKeys));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsMap));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsMap));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemByKey));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByKeys));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveMatchingAsList));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));
//		}
//	}

//	protected String getDataAccessMethodPrefix(MethodType methodType, String structure) {
//		switch (methodType) {
//		case GetAsItem:
//			return "get";
//		case GetAsItemByKey:
//		case GetAsListByKeys:
//		case GetAsList:
//		case GetAsMapByKeys: 
//			return "getFrom";
//		case GetAllAsList:
//		case GetAllAsMap: 
//			return "get";
//		case GetMatchingAsList:
//		case GetMatchingAsMap:
//			return "getMatching";
//		case Set: 
//			return "set";
//		case Unset: 
//			return "unset";
//		case AddAsItem: 
//		case AddAsList: 
//		case AddAsMap: 
//			return "addTo";
//		case RemoveAsItem: 
//		case RemoveAsItemByKey: 
//		case RemoveAsListByKeys: 
//		case RemoveAsList: 
//		case RemoveMatchingAsList:
//			return "removeFrom";
//		case RemoveAll: 
//			return "removeAll";
//		default: return null;
//		}
//	}
	
}
