package nam.data.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Query;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.QueryUtil;
import nam.model.util.TypeUtil;

import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


public class ManagerBeanProvider extends AbstractManagementBeanProvider {
	
	protected ModelUnit modelUnit;
	
	
	public ManagerBeanProvider(GenerationContext context) {
		super(context);
	}

	/**
	 * Initialize operation
	 * ------------------------
	 */
	public void generateSourceCode_Initialize(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		//String mapperClassName = DataLayerHelper.getMapperClassName(element);
		//String mapperNameUncapped = NameUtil.uncapName(mapperClassName);

		Buf buf = new Buf();
		buf.putLine2(daoNameUncapped+".initialize(\""+elementName+"\");");
		buf.putLine2(daoNameUncapped+".setEntityManager(entityManager);");
		buf.putLine2(daoNameUncapped+".setEntityClass("+entityClassName+".class);");
		
		//TODO only do these for element(s) that inherit from an abstract base element
		//buf.putLine2(mapperNameUncapped+".setEntityClass("+entityClassName+".class);");
		//buf.putLine2(mapperNameUncapped+".setModelClass("+elementType+".class);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * ClearContext operation
	 * ----------------------
	 */
	public void generateSourceCode_ClearContext(ModelOperation modelOperation, Element element) {
		Buf buf = new Buf();
		buf.putLine2("entityManager.clear();");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * ImportElements operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_ImportElements(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2(elementNameUncapped+"Importer.import"+elementName+"Records();");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetAllElements operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_GetAllElements(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoNameUncapped+".getAll"+elementName+"Records();");
		if (inverseReference != null)
			buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(null, entityList);");
		else buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementListByField operation
	 * -------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByField(ModelOperation modelOperation, Element element, Field field) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = TypeUtil.getClassName(field.getType());
		//Element fieldElement = context.getElementByType(field.getType());
		//String fieldClassName = ModelLayerHelper.getElementClassName(fieldElement);
		//String fieldEntityClassName = DataLayerHelper.getConcreteEntityClassName(fieldElement);
		//String fieldEntityName = DataLayerHelper.getEntityNameUncapped(fieldElement);

		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName)) {
			//fieldNameCapped = NameUtil.assureEndsWith(fieldNameCapped, "Id");
			//fieldNameUncapped = NameUtil.assureEndsWith(fieldNameUncapped, "Id");
			fieldClassName = "Long";
		}

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+fieldNameUncapped+", \""+fieldNameCapped+" must be specified\");");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoNameUncapped+".get"+elementName+"RecordsBy"+fieldNameCapped+"("+fieldNameUncapped+");");
		if (inverseReference != null)
			buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(null, entityList);");
		else buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * GetElementListByPage operation
	 * ------------------------------
	 */
	@Override
	//TODO add sanity checks for pageIndex and pageSize params
	public void generateSourceCode_GetElementListByPage(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(pageIndex, \"Page-index must be specified\");");
		buf.putLine2("Assert.notNull(pageSize, \"Page-size must be specified\");");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoNameUncapped+".get"+elementName+"RecordsByPage(pageIndex, pageSize);");
		if (inverseReference != null)
			buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(null, entityList);");
		else buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * GetElementByCriteria operation
	 * ----------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByCriteria(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String operationName = "get"+elementName+"RecordByCriteria";

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+"Criteria must be specified\");");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoNameUncapped+"."+operationName+"("+elementTypeUncapped+"Criteria);");
		if (inverseReference != null)
			buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(null, entityList);");
		else buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * GetElementListByCriteria operation
	 * ----------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByCriteria(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String operationName = "get"+elementName+"RecordsByCriteria";

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoNameUncapped+"."+operationName+"("+elementTypeUncapped+"Criteria);");
		if (inverseReference != null)
			buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(null, entityList);");
		else buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementByQuery operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByQuery(ModelOperation modelOperation, Element element, Query query) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		//String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String operationName = NameUtil.uncapName(query.getName());

		Buf buf = new Buf();
		List<String> parameters = QueryUtil.getParameterNames(query);
		Iterator<String> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			String parameterName = iterator.next();
			String parameterNameCapped = NameUtil.capName(parameterName);
			buf.putLine2("Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
		}

		buf.put2(entityClassName+" entity = "+daoNameUncapped+"."+operationName+"(");
		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String parameterName = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameterName);
		}

		buf.putLine(");");
		if (inverseReference != null)
			buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(null, entity);");
		else buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(entity);");
		buf.putLine2("return model;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementListByQueryCriteria operation
	 * ---------------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByQueryCriteria(ModelOperation modelOperation, Element element, Query query) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String operationName = NameUtil.uncapName(query.getName());
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"Criteria, \""+elementName+"Criteria must be specified\");");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoNameUncapped+"."+operationName+"("+elementNameUncapped+"Criteria);");
		if (inverseReference != null)
			buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(null, entityList);");
		else buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementListByQueryCondition operation
	 * ----------------------------------------
	 */
	@Override
	public void generateSourceCode_GetElementListByQueryCondition(ModelOperation modelOperation, Element element, Query query) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String operationName = NameUtil.uncapName(query.getName());
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		List<String> parameters = QueryUtil.getParameterNames(query);
		Iterator<String> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			String parameterName = iterator.next();
			String parameterNameCapped = NameUtil.capName(parameterName);
			buf.putLine2("Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
		}

		buf.put2("List<"+entityClassName+"> entityList = "+daoNameUncapped+"."+operationName+"(");
		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String parameterName = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameterName);
		}

		buf.putLine(");");
		if (inverseReference == null)
			buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(null, entityList);");
		else buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementById operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_GetElementById(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementTypeName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(id, \"ID must be specified\");");
		//buf.putLine2("Assert.notNull("+elementTypeName+"Id, \"ID must be specified\");");
		buf.putLine2(entityClassName+" entity = "+daoNameUncapped+".get"+elementName+"RecordById(id);");
		//buf.putLine2(entityClassName+" entity = "+daoNameUncapped+".get"+elementName+"RecordById("+elementTypeName+"Id);");
		if (inverseReference != null)
			buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(null, entity);");
		else buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(entity);");
		buf.putLine2("return model;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementByKey operation
	 * -------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByKey(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String helperName = ModelLayerHelper.getModelHelperBeanName(element);
		addImportedClassForHelper(modelUnit, element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(key, \"Key must exist\");");
		buf.putLine2(elementName+"Criteria "+elementType+"Criteria = "+helperName+".create"+elementName+"CriteriaFromKey(key);");
		buf.putLine2(entityClassName+" entity = "+daoNameUncapped+".get"+elementName+"RecordByCriteria("+elementType+"Criteria);");
		if (inverseReference != null)
			buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(null, entity);");
		else buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(entity);");
		buf.putLine2("return model;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * GetElementsByKeys operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_GetElementsByKeys(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(keyList, \"Key list must exist\");");
		buf.putLine2("Assert.notEmpty(keyList, \"At least one Key must be specified\");");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoNameUncapped+".get"+elementName+"RecordsByKeys(keyList);");
		buf.putLine2("List<"+elementClassName+"> modelList = "+mapperNameUncapped+".toModelList(entityList);");
		buf.putLine2("return modelList;");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * GetElementByField operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_GetElementByField(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2(entityClassName+" entity = "+daoNameUncapped+".get"+elementClassName+"RecordBy"+fieldNameCapped+"("+fieldNameUncapped+");");
		if (inverseReference != null)
			buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(null, entity);");
		else buf.putLine2(elementClassName+" model = "+mapperNameUncapped+".toModel(entity);");
		buf.putLine2("return model;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * AddElement operation
	 * ---------------------
	 */
	@Override
	public void generateSourceCode_AddElement(ModelOperation modelOperation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementName+" record must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2(entityClassName+" entity = "+mapperNameUncapped+".toEntity("+elementNameUncapped+");");
		if (twoWaySelfReferencing)
			buf.putLine2("Long id = "+daoNameUncapped+".add"+elementName+"Record(parentId, entity);");
		else buf.putLine2("Long id = "+daoNameUncapped+".add"+elementName+"Record(entity);");
		buf.putLine2("Assert.notNull(id, \"Id must exist\");");
		buf.putLine2("return id;");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * AddElementList operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_AddElementList(ModelOperation modelOperation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"List, \""+elementName+" record list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("List<"+entityClassName+"> entityList = "+mapperNameUncapped+".toEntityList("+elementNameUncapped+"List);");
		if (twoWaySelfReferencing)
			buf.putLine2("List<Long> idList = "+daoNameUncapped+".add"+elementName+"Records(parentId, entityList);");
		else buf.putLine2("List<Long> idList = "+daoNameUncapped+".add"+elementName+"Records(entityList);");
		buf.putLine2("Assert.notNull(idList, \"IdList must exist\");");
		buf.putLine2("Assert.equals(idList.size(), "+elementNameUncapped+"List.size(), \"Id count not correct\");");
		buf.putLine2("return idList;");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * AddElementMap operation
	 * -----------------------
	 */
	@Override
	public void generateSourceCode_AddElementMap(ModelOperation modelOperation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"Map, \""+elementName+" record map must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("List<"+entityClassName+"> entityList = "+mapperNameUncapped+".toEntityList("+elementNameUncapped+"Map.values());");
		if (twoWaySelfReferencing)
			buf.putLine2("List<Long> idList = "+daoNameUncapped+".add"+elementName+"Records(parentId, entityList);");
		else buf.putLine2("List<Long> idList = "+daoNameUncapped+".add"+elementName+"Records(entityList);");
		buf.putLine2("Assert.notNull(idList, \"IdList must exist\");");
		buf.putLine2("Assert.isTrue(idList.size() == "+elementNameUncapped+"Map.size(), \"Id count not correct\");");
		buf.putLine2("return idList;");
		modelOperation.addInitialSource(buf.get());
	}
	

	/**
	 * MoveElement operation
	 * ---------------------
	 */
	@Override
	public void generateSourceCode_MoveElement(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2(""+daoNameUncapped+".move"+elementName+"(parentId, "+elementNameUncapped+"Id);");
		//buf.putLine2(entityClassName+" childEntity = "+elementNameUncapped+"Mapper.toEntity("+elementNameUncapped+");");
		//buf.putLine2(entityClassName+" parentEntity = "+elementNameUncapped+"Mapper.toEntity(newParent);");
		//buf.putLine2(elementNameUncapped+"Dao.move"+elementName+"(childEntity, parentEntity);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * SaveElement operation
	 * ---------------------
	 */
	@Override
	public void generateSourceCode_SaveElement(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementName+" record must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2(entityClassName+" entity = "+mapperNameUncapped+".toEntity("+elementNameUncapped+");");
		buf.putLine2(daoNameUncapped+".save"+elementName+"Record(entity);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * SaveElementList operation
	 * -------------------------
	 */
	@Override
	public void generateSourceCode_SaveElementList(ModelOperation modelOperation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"List, \""+elementName+" record list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("List<"+entityClassName+"> entityList = "+mapperNameUncapped+".toEntityList("+elementNameUncapped+"List);");
		if (twoWaySelfReferencing)
			buf.putLine2(daoNameUncapped+".save"+elementName+"Records(parentId, entityList);");
		else buf.putLine2(daoNameUncapped+".save"+elementName+"Records(entityList);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * SaveElementMap operation
	 * ------------------------
	 */
	@Override
	public void generateSourceCode_SaveElementMap(ModelOperation modelOperation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"Map, \""+elementName+" record map must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("List<"+entityClassName+"> entityList = "+mapperNameUncapped+".toEntityList("+elementNameUncapped+"Map.values());");
		if (twoWaySelfReferencing)
			buf.putLine2(daoNameUncapped+".save"+elementName+"Records(parentId, entityList);");
		else buf.putLine2(daoNameUncapped+".save"+elementName+"Records(entityList);");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * RemoveAllElements operations
	 * ----------------------------
	 */
	@Override
	public void generateSourceCode_RemoveAllElements(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2(daoNameUncapped+".removeAll"+elementType+"Records();");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElement operation
	 * -----------------------
	 */
	@Override
	public void generateSourceCode_RemoveElement(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementName+" record must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2(entityClassName+" entity = "+mapperNameUncapped+".toEntity("+elementNameUncapped+");");
		buf.putLine2(daoNameUncapped+".remove"+elementName+"Record(entity);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementById operation
	 * ----------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementById(ModelOperation modelOperation, Element element) {
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementTypeCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(id, \""+elementTypeCapped+" record ID must be specified\");");
		//buf.putLine2("Assert.notNull("+elementTypeUncapped+"Id, \""+elementTypeCapped+" record ID must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2(elementNameCapped+"Entity entity = "+daoNameUncapped+".get"+elementTypeCapped+"RecordById(id);");
		//buf.putLine2(elementNameCapped+"Entity entity = "+daoNameUncapped+".get"+elementTypeCapped+"RecordById("+elementTypeUncapped+"Id);");
		buf.putLine2(daoNameUncapped+".remove"+elementTypeCapped+"Record(entity);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementByKey operation
	 * ----------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementByKey(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String helperName = ModelLayerHelper.getModelHelperBeanName(element);

		Buf buf = new Buf();
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("Assert.notNull(key, \""+elementName+" record key must be specified\");");
		buf.putLine2(elementName+"Criteria "+elementType+"Criteria = "+helperName+".create"+elementName+"CriteriaFromKey(key);");
		buf.putLine2(daoNameUncapped+".remove"+elementName+"Records("+elementType+"Criteria);");
		modelOperation.addInitialSource(buf.get());
	}
	
	
	/**
	 * RemoveElementListByKeys operation
	 * ---------------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementListByKeys(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(keyList, \""+elementName+" record key list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2(daoNameUncapped+".remove"+elementName+"RecordsByKey(keyList);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementList operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementList(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"List, \""+elementName+" record list must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("List<"+entityClassName+"> entityList = "+mapperNameUncapped+".toEntityList("+elementNameUncapped+"List);");
		buf.putLine2(daoNameUncapped+".remove"+elementName+"Records(entityList);");
		modelOperation.addInitialSource(buf.get());
	}
	
	/**
	 * RemoveElementMap operation
	 * ---------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementMap(ModelOperation modelOperation, Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"Map, \""+elementName+" record map must be specified\");");
		buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
		buf.putLine2("List<"+entityClassName+"> entityList = "+mapperNameUncapped+".toEntityList("+elementNameUncapped+"Map.values());");
		buf.putLine2(daoNameUncapped+".remove"+elementName+"Records(entityList);");
		modelOperation.addInitialSource(buf.get());
	}

	/**
	 * RemoveElementListByCriteria operation
	 * ----------------------------------
	 */
	@Override
	public void generateSourceCode_RemoveElementListByCriteria(ModelOperation modelOperation, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String operationName = "remove"+elementName+"Records";

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2(daoNameUncapped+"."+operationName+"("+elementTypeUncapped+"Criteria);");
		modelOperation.addInitialSource(buf.get());
	}
	
}
