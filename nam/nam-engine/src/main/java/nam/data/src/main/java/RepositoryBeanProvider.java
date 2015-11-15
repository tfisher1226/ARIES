package nam.data.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Query;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.QueryUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;


public class RepositoryBeanProvider extends AbstractManagementBeanProvider {
	
	public RepositoryBeanProvider(GenerationContext context) {
		super(context);
	}
	

	/**
	 * ClearContext operation
	 * ----------------------
	 * @param unit 
	 */
	public void generateSourceCode_ClearContext(ModelOperation modelOperation, Unit unit) {
		Buf buf = new Buf();
		buf.putLine2("try {");
		
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isTransient(element))
				continue;
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
			buf.putLine2("	"+elementNameUncapped+"Manager.clearContext();");
		}
		
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * GetAllElements operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_GetAllElements(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		//buf.putLine2("	//beginTransaction();");
		buf.putLine2("	List<"+elementClassName+"> records = "+managerName+".getAll"+elementName+"Records();");
		//buf.putLine2("	//commitTransaction();");
		//buf.putLine3("");
		buf.putLine2("	Assert.notNull(records, \""+elementName+" record list null\");");
		buf.putLine2("	return records;");
		//buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		//buf.putLine2("	//rollbackTransaction();");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementListByField operation
	 * -------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByField(ModelOperation modelOperation, Element element, Field field) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = TypeUtil.getClassName(field.getType());
		//Element fieldElement = context.getElementByType(field.getType());
		//String fieldClassName = ModelLayerHelper.getElementClassName(fieldElement);
		//String fieldEntityClassName = DataLayerHelper.getEntityClassName(fieldElement);
		//String fieldEntityName = DataLayerHelper.getEntityNameUncapped(fieldElement);

		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName)) {
			//fieldNameCapped = NameUtil.assureEndsWith(fieldNameCapped, "Id");
			//fieldNameUncapped = NameUtil.assureEndsWith(fieldNameUncapped, "Id");
			//fieldClassName = "Long";
		}


		Buf buf = new Buf();
		buf.putLine2("try {");
		//buf.putLine2("	//beginTransaction();");
		buf.putLine2("	Assert.notNull("+fieldNameUncapped+", \""+fieldNameCapped+" must be specified\");");
		buf.putLine2("	List<"+elementClassName+"> records = "+managerName+".get"+elementName+"RecordsBy"+fieldNameCapped+"("+fieldNameUncapped+");");
		//buf.putLine2("	//commitTransaction();");
		//buf.putLine3("");
		buf.putLine2("	Assert.notNull(records, \""+elementName+" record list null\");");
		buf.putLine2("	return records;");
		buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		//buf.putLine2("	//rollbackTransaction();");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * GetElementListByPage operation
	 * ------------------------------
	 */
	@Override
	//TODO add sanity checks for pageIndex and pageSize params
	public void generateSourceCode_GetElementListByPage(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull(pageIndex, \"Page-index must be specified\");");
		buf.putLine2("	Assert.notNull(pageSize, \"Page-size must be specified\");");
		buf.putLine2("	List<"+elementClassName+"> records = "+managerName+".get"+elementName+"Records(pageIndex, pageSize);");
		buf.putLine2("	Assert.notNull(records, \""+elementName+" record list null\");");
		buf.putLine2("	return records;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementByCriteria operation
	 * ------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByCriteria(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		//String operationName = NameUtil.uncapName(query.getName());
		//String operationName = "get"+elementName+"RecordByCriteria";
		String operationName = "get"+elementName+"Record";

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2("	"+elementClassName+" record = "+managerName+"."+operationName+"("+elementTypeUncapped+"Criteria);");
		//buf.putLine2("	Assert.notNull(record, \""+elementName+" record null\");");
		buf.putLine2("	return record;");
		buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementListByCriteria operation
	 * ----------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByCriteria(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		//String operationName = NameUtil.uncapName(query.getName());
		//String operationName = "get"+elementName+"RecordsByCriteria";
		String operationName = "get"+elementName+"Records";

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2("	List<"+elementClassName+"> records = "+managerName+"."+operationName+"("+elementTypeUncapped+"Criteria);");
		buf.putLine2("	Assert.notNull(records, \""+elementName+" record list null\");");
		buf.putLine2("	return records;");
		buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementByQuery operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByQuery(ModelOperation modelOperation, Element element, Query query) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		String operationName = NameUtil.uncapName(query.getName());

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementNameUncapped+", \""+elementName+" must be specified\");");
		buf.putLine2("	"+elementClassName+" record = "+managerName+".get"+elementName+"RecordsById("+elementNameUncapped+");");
		//buf.putLine2("	Assert.notNull(record, \""+elementName+" record null\");");
		buf.putLine2("	return record;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		
		buf.putLine2("try {");
		List<String> parameters = QueryUtil.getParameterNames(query);
		Iterator<String> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			String parameterName = iterator.next();
			String parameterNameCapped = NameUtil.capName(parameterName);
			buf.putLine3("Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
		}

		buf.put3(elementClassName+" record = "+managerName+"."+operationName+"(");
		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String parameterName = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameterName);
		}

		buf.putLine(");");
		//buf.putLine2("	Assert.notNull(record, \""+elementName+" record null\");");
		buf.putLine2("	return record;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementListByQueryCriteria operation
	 * ---------------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByQueryCriteria(ModelOperation modelOperation, Element element, Query query) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		String operationName = NameUtil.uncapName(query.getName());

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementNameUncapped+"Criteria, \""+elementName+"Criteria must be specified\");");
		buf.putLine2("	List<"+elementClassName+"> records = "+managerName+"."+operationName+"("+elementNameUncapped+"Criteria);");
		buf.putLine2("	Assert.notNull(records, \""+elementName+" record list null\");");
		buf.putLine2("	return records;");
		buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementListByQueryCondition operation
	 * ----------------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByQueryCondition(ModelOperation modelOperation, Element element, Query query) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		String operationName = NameUtil.uncapName(query.getName());

		Buf buf = new Buf();
		buf.putLine2("try {");
		List<String> parameters = QueryUtil.getParameterNames(query);
		Iterator<String> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			String parameterName = iterator.next();
			String parameterNameCapped = NameUtil.capName(parameterName);
			buf.putLine3("Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
		}

		buf.put3("List<"+elementClassName+"> records = "+managerName+"."+operationName+"(");
		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String parameterName = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameterName);
		}

		buf.putLine(");");
		buf.putLine2("	Assert.notNull(records, \""+elementName+" record list null\");");
		buf.putLine2("	return records;");
		buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementById operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_GetElementById(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementTypeName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull(id, \"Id must be specified\");");
		//buf.putLine2("	Assert.notNull("+elementTypeName+"Id, \"Id must be specified\");");
		buf.putLine2("	"+elementClassName+" record = "+managerName+".get"+elementName+"Record(id);");
		//buf.putLine2("	"+elementClassName+" record = "+managerName+".get"+elementName+"RecordById("+elementTypeName+"Id);");
		//buf.putLine2("	Assert.notNull(record, \""+elementName+" record null\");");
		buf.putLine2("	return record;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementByKey operation
	 * -------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByKey(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull(key, \"Key must be specified\");");
		buf.putLine2("	"+elementClassName+" record = "+managerName+".get"+elementName+"Record(key);");
		//buf.putLine2("	Assert.notNull(record, \""+elementName+" record null\");");
		buf.putLine2("	return record;");
		buf.putLine3("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementsByKeys operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_GetElementsByKeys(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull(keyList, \"Key list must exist\");");
		buf.putLine2("	Assert.notEmpty(keyList, \"At least one Key must be specified\");");
		buf.putLine2("	List<"+elementClassName+"> records = "+managerName+".get"+elementName+"RecordsByKeys(keyList);");
		buf.putLine2("	Assert.notNull(records, \""+elementName+" record list null\");");
		buf.putLine2("	return records;");
		buf.putLine3("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * GetElementByField operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByField(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+fieldNameUncapped+", \""+fieldNameCapped+" must be specified\");");
		buf.putLine2("	"+elementClassName+" record = "+managerName+".get"+elementName+"RecordBy"+fieldNameCapped+"("+fieldNameUncapped+");");
		//buf.putLine2("	Assert.notNull(record, \""+elementName+" record null\");");
		buf.putLine2("	return record;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * AddElement operation
	 * ---------------------
	 */
	@Override
	public void generateSourceCode_AddElement(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+", \""+elementName+" record must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	Long id = "+managerName+".add"+elementName+"Record("+elementType+");");
		buf.putLine2("	Assert.notNull(id, \"Id must exist\");");
		buf.putLine2("	return id;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * AddElementList operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_AddElementList(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+"List, \""+elementName+" record list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	List<Long> idList = "+managerName+".add"+elementName+"Records("+elementType+"List);");
		buf.putLine2("	Assert.notNull(idList, \"Id list must exist\");");
		buf.putLine2("	Assert.isTrue(idList.size() == "+elementType+"List.size(), \"Id count must be correct\");");
		buf.putLine2("	return idList;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * AddElementMap operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_AddElementMap(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+"Map, \""+elementName+" record map be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	List<Long> idList = "+managerName+".add"+elementName+"Records("+elementType+"Map);");
		buf.putLine2("	Assert.notNull(idList, \"Id list must exist\");");
		buf.putLine2("	Assert.isTrue(idList.size() == "+elementType+"Map.size(), \"Id count must be correct\");");
		buf.putLine2("	return idList;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * MoveElement operation
	 * ---------------------
	 */
	@Override
	public void generateSourceCode_MoveElement(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+", \""+elementName+" record must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".move"+elementName+"Record("+elementType+");");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * SaveElement operation
	 * ---------------------
	 */
	@Override
	public void generateSourceCode_SaveElement(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+", \""+elementName+" record must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".save"+elementName+"Record("+elementType+");");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * SaveElementList operation
	 * -------------------------
	 */
	@Override
	public void generateSourceCode_SaveElementList(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+"List, \""+elementName+" record list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".save"+elementName+"Records("+elementType+"List);");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * SaveElementMap operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_SaveElementMap(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+"Map, \""+elementName+" record map must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".save"+elementName+"Records("+elementType+"Map);");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveAllElements operations
	 * ----------------------------
	 */
	@Override
	public void generateSourceCode_RemoveAllElements(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	"+managerName+".removeAll"+elementName+"Records();");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * RemoveElement operation
	 * -----------------------
	 */
	@Override
	public void generateSourceCode_RemoveElement(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+", \""+elementName+" record must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".remove"+elementName+"Record("+elementType+");");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementById operation
	 * ----------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementById(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull(id, \""+elementName+" record ID must be specified\");");
		//buf.putLine2("	Assert.notNull("+elementType+"Id, \""+elementName+" record ID must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".remove"+elementName+"Record(id);");
		//buf.putLine2("	"+managerName+".remove"+elementName+"Record("+elementType+"Id);");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementByKey operation
	 * ----------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementByKey(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull(key, \""+elementName+" record key must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".remove"+elementName+"Record(key);");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	
	/**
	 * RemoveElementListByKeys operation
	 * ---------------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementListByKeys(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull(keyList, \""+elementName+" record key list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".remove"+elementName+"RecordsByKeys(keyList);");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementList operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementList(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+"List, \""+elementName+" record list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".remove"+elementName+"Records("+elementType+"List);");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementMap operation
	 * --------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementMap(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementType+"Map, \""+elementName+" record map must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("	"+managerName+".remove"+elementName+"Records("+elementType+"Map);");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementListByCriteria operation
	 * ----------------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementListByCriteria(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		//String operationName = NameUtil.uncapName(query.getName());
		String operationName = "remove"+elementName+"Records";

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2("	"+managerName+"."+operationName+"("+elementTypeUncapped+"Criteria);");
		buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * ImportElements operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_ImportElements(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String managerName = DataLayerHelper.getManagerNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		//buf.putLine2("	//beginTransaction();");
		buf.putLine2("	"+managerName+".import"+elementName+"Records();");
		//buf.putLine2("	//commitTransaction();");
		//buf.putLine3("");	
		buf.putLine2("} catch (Exception e) {");
		//buf.putLine2("	//rollbackTransaction();");
		buf.putLine2("	throw ExceptionUtil.createRuntimeException(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}
	
}
