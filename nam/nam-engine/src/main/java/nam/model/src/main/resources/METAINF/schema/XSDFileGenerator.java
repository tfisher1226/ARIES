package nam.model.src.main.resources.METAINF.schema;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Literal;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Properties;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PropertyUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public class XSDFileGenerator extends AbstractFileGenerator {

	public XSDFileGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void generate(List<Namespace> namespaces) throws Exception {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			generate(namespace);
		}
	}
	
	public void generate(Namespace namespace) throws Exception {
		if (context.isEnabled("generate-imported-namespaces"))
			generate(namespace.getImports());

		Properties properties = namespace.getProperties();
		boolean generateSchema = PropertyUtil.isEnabled(properties, "generate-schema");
		if (generateSchema == false)
			return;

		String xsdFileName = ModelLayerHelper.getXSDFileName(namespace);
		if (context.isEnabled("override-generated-xsd-file-name"))
			xsdFileName = namespace.getName() + ".xsd";

		String targetFolder = null;
		String targetFolderProperty = context.getProperty("target-folder");
		if (targetFolderProperty != null) {
			targetFolder = targetFolderProperty;
		} else {
			String sourceDirectory = !context.isOptionGenerateTests() ? "main" : "test";
			targetFolder = "src/"+sourceDirectory+"/resources/schema";
		}
		
		setTargetFile(xsdFileName);
		setTargetFolder(targetFolder);
		setTargetPath(context.getTargetPath()+"/"+targetFolder);

		Buf buf = new Buf();
		buf.put(generateXSDFileOpen(namespace));
		buf.put(generateXSDFileImports(namespace));
		buf.put(generateXSDFileTypes(namespace));
		buf.put(generateXSDFileClose());
		generateFile(buf.get());
	}

	protected String generateXSDFileOpen(Namespace namespace) {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		buf.putLine("");
		
		buf.putLine("<xs:schema");
		buf.putLine("	version=\"1.0\""); 
		buf.putLine("	jaxb:version=\"2.0\"");
		buf.putLine("	elementFormDefault=\"qualified\""); 
		buf.putLine("	attributeFormDefault=\"qualified\""); 
		buf.putLine("	xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
		buf.putLine("	xmlns:jaxb=\"http://java.sun.com/xml/ns/jaxb\"");
		buf.putLine("	xmlns:xjc=\"http://java.sun.com/xml/ns/jaxb/xjc\"");
		
		List<Namespace> imports = NamespaceUtil.getImports(namespace);
		Iterator<Namespace> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = iterator.next();
			buf.putLine("	xmlns:"+importedNamespace.getPrefix()+"=\""+importedNamespace.getUri()+"\""); 
		}

		buf.putLine("	xmlns:"+namespace.getPrefix()+"=\""+namespace.getUri()+"\""); 
		buf.putLine("	targetNamespace=\""+namespace.getUri()+"\">");
		return buf.get();
	}


	protected String generateXSDFileImports(Namespace namespace) {
		List<Namespace> imports = NamespaceUtil.getImports(namespace);
		Buf buf = new Buf();
		if (imports.size() > 0)
			buf.putLine("");
		
		Iterator<Namespace> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = iterator.next();
			String xsdFileName = ModelLayerHelper.getXSDFileName(importedNamespace);
			
			String schemaLocation = xsdFileName;
			buf.putLine1("<xs:import namespace=\""+importedNamespace.getUri()+"\" schemaLocation=\""+schemaLocation+"\" />");
		}
		return buf.get();
	}
	
	protected String generateXSDFileTypes(Namespace namespace) {
		String packageName = NamespaceUtil.getPackageName(namespace);

		Buf buf = new Buf();
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Enumeration) {
				Enumeration enumeration = (Enumeration) type;
				buf.put(generateXSDSchemaForEnumerationValue(namespace, enumeration, false));
				if (ElementUtil.isEnumerationLabelsExist(enumeration))
					buf.put(generateXSDSchemaForEnumerationValue(namespace, enumeration, true));
				
			} else if (type instanceof Element) {
				Element element = (Element) type;
//				if (ElementUtil.isTransient(element))
//					continue;
				buf.put(generateXSDSchemaElement(namespace, element));
			}
		}
		return buf.get();
	}

	
	protected String generateXSDSchemaForEnumerationValue(Namespace namespace, Enumeration enumeration, boolean generateLabelVersion) {
		String enumerationName = NameUtil.capName(enumeration.getName());
		if (generateLabelVersion)
			enumerationName += "Name";
			
		Buf buf = new Buf();
		buf.putLine1("");
		buf.putLine1("<!--");
		buf.putLine2(enumerationName+" enumeration");
		buf.putLine2(generateCharacterSequence("*", enumerationName.length()+12));
		buf.putLine1("-->");
		buf.putLine1("");
		buf.putLine1("<xs:simpleType name=\""+enumerationName+"\">");
		buf.putLine1("	<xs:restriction base=\"xs:string\">");
		
		List<Literal> literals = enumeration.getLiterals();
		Iterator<Literal> iterator = literals.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			String enumerationValue = null; 
			if (generateLabelVersion && !StringUtils.isEmpty(literal.getLabel()))
				enumerationValue = literal.getLabel();
			else enumerationValue = literal.getName();
			
			buf.putLine1("		<xs:enumeration value=\""+enumerationValue+"\" />");
		}
		buf.putLine1("	</xs:restriction>");
		buf.putLine1("</xs:simpleType>");
		return buf.get();
	}

	protected String generateXSDSchemaElement(Namespace namespace, Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String prefixPart = namespace.getPrefix() + ":";
		
		Buf buf = new Buf();
		buf.putLine1("");
		buf.putLine1("<!--");
		buf.putLine2(elementName+" element");
		buf.putLine2(generateCharacterSequence("*", elementName.length()+8));
		buf.putLine1("-->");
		buf.putLine1("");
		buf.putLine1("<xs:element name=\""+elementNameUncapped+"\" type=\""+prefixPart+elementName+"\" />");
		buf.putLine1("<xs:complexType name=\""+elementName+"\">");
		buf.putLine1("	<xs:sequence>");
		
//		if (elementName.toLowerCase().contains("event"))
//			System.out.println();
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isInverse(field))
				continue;

			//String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldTypeUri = ModelLayerHelper.getFieldTypeUri(field);
			String fieldTypeLocalPart = ModelLayerHelper.getFieldTypeLocalPart(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldType = ModelLayerHelper.getFieldType(field);

			String xsdElementName = fieldNameUncapped;
			String xsdElementType = fieldClassName;
			if (!StringUtils.isEmpty(field.getKey())) {
				xsdElementType = "common:Map";

			} else if (fieldTypeUri.equals("http://www.w3.org/2001/XMLSchema")) {
				if (fieldType.endsWith("date")) {
					xsdElementType = "xs:date";
				} else if (fieldType.endsWith("dateTime")) {
					xsdElementType = "xs:dateTime";
				} else if (fieldType.endsWith("time")) {
					xsdElementType = "xs:time";
				} else {
					xsdElementType = "xs:" + fieldTypeLocalPart;
					//xsdElementType = "xs:" + NameUtil.uncapName(fieldClassName);
				}
				
			} else {
				Namespace fieldNamespace = context.getNamespaceByUri(fieldTypeUri);
				xsdElementType = fieldNamespace.getPrefix() + ":" + fieldClassName;
			}
			
//			if (ClassUtil.isJavaPrimitiveType(fieldClassName))
//				xsdElementType = "xs:"+NameUtil.uncapName(fieldClassName);
//			if (ClassUtil.isJavaDefaultType(fieldClassName))
//				prefixPart = "xs:";
			
			Integer minOccurs = field.getMinOccurs();
			Integer maxOccurs = field.getMaxOccurs();
			boolean isRequired = field.getRequired();
			boolean isNillible = field.getNullable();
			String defaultValue = field.getDefault();
			
			buf.put3("");
			buf.put("<xs:element");
			buf.put(" name=\""+xsdElementName+"\"");
			buf.put(" type=\""+xsdElementType+"\"");
			
			if (!isRequired && minOccurs == 0)
				buf.put(" minOccurs=\"0\"");
			else if (minOccurs > 1)
				buf.put(" minOccurs=\""+minOccurs+"\"");
			
			if (maxOccurs > 1)
				buf.put(" maxOccurs=\""+maxOccurs+"\"");
			else if (maxOccurs == -1 && (FieldUtil.isList(field) || FieldUtil.isSet(field)))
				buf.put(" maxOccurs=\"unbounded\"");

			if (!StringUtils.isEmpty(defaultValue))
				buf.put(" default=\""+defaultValue+"\"");
			if (isNillible)
				buf.put(" nillable=\""+isNillible+"\"");
			buf.put(" />");
			buf.putLine("");
		}
		
		buf.putLine1("	</xs:sequence>");
		buf.putLine1("</xs:complexType>");
		return buf.get();
	}

	public static String generateCharacterSequence(String characterSet, int length) {
		Buf buf = new Buf();
		for (int i = 0; i < length; i++)
			buf.put(characterSet);
		return buf.get();
	}

	protected String generateXSDFileClose() {
		Buf buf = new Buf();
		buf.put("</xs:schema>\n");
		return buf.get();
	}

}
