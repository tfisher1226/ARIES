package nam.data.src.main.resources.METAINF;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.data.src.test.java.EntityClassHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Properties;
import nam.model.Reference;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.PropertyUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class QueriesXMLBuilder extends AbstractDataLayerFileBuilder {

	public QueriesXMLBuilder(GenerationContext context) {
		super(context);
	}
//
//	protected String getFileName(Unit unit) {
//		String targetFile = null;
//		boolean mainFileGenerated = false;
//		if (!mainFileGenerated) {
//			mainFileGenerated = true;
//			targetFile  = "queries.xml";
//		} else {
//			String packageName = UnitUtil.getPackageName(unit);
//			targetFile = "queries-"+packageName+".xml";
//		}
//		return targetFile;
//	}

	public ModelFile buildFile(boolean isTest, Unit unit) throws Exception {
		ModelFile modelFile = createMainResourcesFile("META-INF", getQueriesfileName(unit));
		modelFile.setTextContent(getFileContent(isTest, unit));
		return modelFile;
	}

	public String getFileContent(boolean isTest, Unit unit) throws Exception {
		Properties properties = unit.getProperties();
		boolean generateMappings = PropertyUtil.isEnabled(properties, "generate-mappings");
		if (generateMappings == false)
			return "";

		Buf buf = new Buf();
		buf.put(generateQueriesFileOpen());
		buf.put(generateQueriesFilePackage(unit));
		buf.put(generateQueryFileEntityList(unit));
		buf.put(generateQueriesFileClose());
		return buf.get();
	}

	protected String generateQueriesFileOpen() {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		switch (jpaVersion) {
		case JPA_VERSION_1:
			buf.putLine("<entity-mappings");
			buf.putLine("	version=\"1.0\"");
			buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/persistence/orm\"");
			buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_1_0.xsd\">");
			break;
		case JPA_VERSION_2:
			buf.putLine("<entity-mappings");
			buf.putLine("	version=\"2.0\"");
			buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/persistence/orm\"");
			buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_2_0.xsd\">");
			break;
		}
		return buf.get();
	}

	protected String generateQueriesFilePackage(Unit unit) {
		Buf buf = new Buf();
		buf.putLine("");
		String packageName = UnitUtil.getPackageName(unit);
		buf.putLine("	<package>"+packageName+".entity</package>\n");
		return buf.get();
	}

	//	protected String generateQueryFileEntityList(List<ModelPackage> modelPackages) {
	//		Buf buf = new Buf();
	//		buf.putLine();
	//		Iterator<ModelPackage> iterator = modelPackages.iterator();
	//		while (iterator.hasNext()) {
	//			ModelPackage modelPackage = iterator.next();
	//			buf.put(generateQueryFileEntityList(modelPackage));
	//		}
	//		buf.putLine();
	//		buf.putLine2("<exclude-unlisted-classes>true</exclude-unlisted-classes>");
	//		return buf.get();
	//	}

	protected String generateQueryFileEntityList(Unit unit) {
		Buf buf = new Buf();
		List<Element> elements = UnitUtil.getElements(unit);
		//List<Element> elements = UnitUtil.getFunctionalElementsBasedOnName(unit);
		String entityPackageName = DataLayerHelper.getEntityPackageName(unit.getNamespace());
		Map<String, Set<Element>> elementsByTypeMap = createElementsByTypeMap(unit, elements); 
		Iterator<String> iterator = elementsByTypeMap.keySet().iterator();
		while (iterator.hasNext()) {
			String elementClassName = iterator.next();
			Collection<Element> elementList = elementsByTypeMap.get(elementClassName);
			Iterator<Element> iterator2 = elementList.iterator();
			//TODO having a list here may be "meaningless"..
			while (iterator2.hasNext()) {
				Element element = iterator2.next();
				if (!ElementUtil.isRoot(element))
					continue;
				buf.put(generateQueryFileEntityOpen(entityPackageName+"."+elementClassName));
				buf.put(generateQueryFileQueryList(unit, element));
				buf.put(generateQueryFileEntityClose());
			}
		}
		return buf.get();
	}

	protected Map<String, Set<Element>> createElementsByTypeMap(Unit unit, List<Element> elements) {
		Map<String, Set<Element>> map = new LinkedHashMap<String, Set<Element>>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			
			String entityClassName = DataLayerHelper.getEntityClassName(element);
			String concreteEntityClassName = DataLayerHelper.getConcreteEntityClassName(element);
			
			Set<Element> elementSet = map.get(concreteEntityClassName);
			if (elementSet == null) {
				elementSet = new HashSet<Element>();
				map.put(concreteEntityClassName, elementSet);
				
				if (concreteEntityClassName.equals(entityClassName)) {
					Set<Element> elementSet2 = map.get(entityClassName);
					if (elementSet2 == null) {
						elementSet2 = new HashSet<Element>();
						map.put(entityClassName, elementSet2);
					}
					elementSet2.add(element);
				}
			}
			elementSet.add(element);
			map.putAll(createElementsByTypeMapForFields(unit, element));
		}
		return map;
	}

	protected Map<String, Set<Element>> createElementsByTypeMapForFields(Unit unit, Element element) {
		Map<String, Set<Element>> map = new HashMap<String, Set<Element>>();
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (FieldUtil.isTransient(reference))
				continue;
			
			EntityClassHelper entityClassHelper = new EntityClassHelper(context);
			entityClassHelper.initialize(unit.getNamespace(), element, reference);
			String entityClassName = entityClassHelper.getEntityClassName();
			Element targetElement = entityClassHelper.getTargetElement();
			
			Set<Element> elementSet = map.get(entityClassName);
			if (elementSet == null) {
				elementSet = new HashSet<Element>();
				map.put(entityClassName, elementSet);
			}
			elementSet.add(targetElement);
		}
		return map;
	}
	
	protected String generateQueryFileEntityOpen(String entityClassName) {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine("	<entity class=\""+entityClassName+"\">");
		return buf.get();
	}

	protected String generateQueryFileEntityClose() {
		Buf buf = new Buf();
		buf.putLine("	</entity>");
		return buf.get();
	}

	protected String generateQueryFileQueryList(Unit unit, Element element) {
		Buf buf = new Buf();
		buf.put(generateNamedQuery_getRecordById(element));
		buf.put(generateNamedQuery_getRecordByUniqueFields(element));
		buf.put(generateNamedQuery_getAllRecords(element));
		buf.put(generateNamedQuery_getRecordsByIndexedFields(element));
		buf.put(generateNamedQuery_removeAllRecords(element));
		return buf.get();
	}

	protected String generateNamedQuery_getRecordById(Element element) {
		String elementName = NameUtil.capName(element.getName());
		String tableName = ModelLayerHelper.getElementNameCapped(element);

		Buf buf = new Buf();
		buf.putLine2("<named-query name=\"get"+elementName+"RecordById\">");
		buf.putLine2("	<query>");
		buf.putLine2("		<![CDATA[");
		buf.putLine2("			select distinct x from "+tableName+" x where x.id = :id");
		buf.putLine2("		]]>");
		buf.putLine2("	</query>");
		buf.putLine2("</named-query>");
		return buf.get();
	}

	protected String generateNamedQuery_getAllRecords(Element element) {
		Buf buf = new Buf();
		String tableName = ModelLayerHelper.getElementNameCapped(element);
		String elementName = NameUtil.capName(element.getName());

		buf.putLine2("");
		buf.putLine2("<named-query name=\"getAll"+elementName+"Records\">");
		buf.putLine2("	<query>");
		buf.putLine2("		<![CDATA[");
		buf.putLine2("			select x from "+tableName+" x");
		buf.putLine2("		]]>");
		buf.putLine2("	</query>");
		buf.putLine2("</named-query>");
		return buf.get();
	}

	protected String generateNamedQuery_getRecordByUniqueFields(Element element) {
		List<Field> fields = ElementUtil.getUniqueFields(element);
		Iterator<Field> iterator = fields.iterator();
		Buf buf = new Buf();
		for (int i=0; iterator.hasNext();) {
			Field field = iterator.next();
			buf.put(generateNamedQuery_getRecordByField(element, field));
		}
		return buf.get();
	}

	protected String generateNamedQuery_getRecordsByIndexedFields(Element element) {
		List<Field> fields = ElementUtil.getIndexedFields(element);
		Iterator<Field> iterator = fields.iterator();
		Buf buf = new Buf();
		for (int i=0; iterator.hasNext(); i++) {
			Field field = iterator.next();
			if (FieldUtil.isUnique(field))
				continue;
			buf.put(generateNamedQuery_getRecordsByField(element, field));
		}
		return buf.get();
	}

	protected String generateNamedQuery_getRecordByField(Element element, Field field) {
		//String fieldType = ModelLayerHelper.getFieldType(field);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldNameQualified = ModelLayerHelper.getFieldNameUncapped(field);
		//Element fieldElement = context.getElementByType(fieldType);
		String elementName = NameUtil.capName(element.getName());
		String tableName = ModelLayerHelper.getElementNameCapped(element);

		if (field instanceof Reference) {
			fieldNameCapped = NameUtil.assureEndsWith(fieldNameCapped, "Id");
			fieldNameQualified = NameUtil.assureEndsWith(fieldNameUncapped, ".id");
			fieldNameUncapped = NameUtil.assureEndsWith(fieldNameUncapped, "Id");
		}

		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("<named-query name=\"get"+elementName+"RecordBy"+fieldNameCapped+"\">");
		buf.putLine2("	<query>");
		buf.putLine2("		<![CDATA[");
		buf.putLine2("			select distinct x from "+tableName+" x where x."+fieldNameQualified+" = :"+fieldNameUncapped);
		buf.putLine2("		]]>");
		buf.putLine2("	</query>");
		buf.putLine2("</named-query>");
		return buf.get();
	}

	protected String generateNamedQuery_getRecordsByField(Element element, Field field) {
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldNameQualified = ModelLayerHelper.getFieldNameUncapped(field);
		String elementName = NameUtil.capName(element.getName());
		String tableName = ModelLayerHelper.getElementNameCapped(element);

		if (field instanceof Reference) {
			fieldNameCapped = NameUtil.assureEndsWith(fieldNameCapped, "Id");
			fieldNameQualified = NameUtil.assureEndsWith(fieldNameUncapped, ".id");
			fieldNameUncapped = NameUtil.assureEndsWith(fieldNameUncapped, "Id");
		}

		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("<named-query name=\"get"+elementName+"RecordsBy"+fieldNameCapped+"\">");
		buf.putLine2("	<query>");
		buf.putLine2("		<![CDATA[");
		buf.putLine2("			select x from "+tableName+" x where x."+fieldNameQualified+" = :"+fieldNameUncapped);
		buf.putLine2("		]]>");
		buf.putLine2("	</query>");
		buf.putLine2("</named-query>");
		return buf.get();
	}
	
	protected String generateNamedQuery_removeAllRecords(Element element) {
		String elementName = NameUtil.capName(element.getName());
		String tableName = ModelLayerHelper.getElementNameCapped(element);

		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("<named-query name=\"removeAll"+elementName+"Records\">");
		buf.putLine2("	<query>");
		buf.putLine2("		<![CDATA[");
		buf.putLine2("			delete from "+tableName+" x");
		buf.putLine2("		]]>");
		buf.putLine2("	</query>");
		buf.putLine2("</named-query>");
		return buf.get();
	}

	protected String generateQueriesFileClose() {
		Buf buf = new Buf();
		buf.put("</entity-mappings>\n");
		return buf.get();
	}

}
