package nam.model.src.main.resources.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Information;
import nam.model.Literal;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

import org.apache.cxf.common.util.StringUtils;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;

import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ModelXSDBuilder extends AbstractDataLayerFileBuilder {

	private String lastPackage;
	
	private String unitNamespace;
	
	private Set<String> importedNamespaces;

	private List<String> entityClassNames;
	
	
	public ModelXSDBuilder(GenerationContext context) {
		super(context);
	}

	protected boolean isExtendsAbstractParent(Element element) {
		//String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		//String concreteEntityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		return context.getParentElementByType(element.getType()) != null;
	}

	public List<ModelFile> buildFiles(List<Namespace> namespaces) throws Exception {
		return buildFiles(false, namespaces);
	}

	public List<ModelFile> buildFiles(boolean isTest, List<Namespace> namespaces) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		NamespaceUtil.sortNamespaces(namespaces);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelFiles.add(buildFile(isTest, namespace));
		}
		return modelFiles;
	}

	public List<ModelFile> buildFiles(Information information) throws Exception {
		return buildFiles(false, information);
	}
	
	public List<ModelFile> buildFiles(boolean isTest, Information information) throws Exception {
		//context.buildParentElementMap(information);
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		//buf.put(generateInformationBlockOpen());
		List<Namespace> namespaces = information.getNamespaces();
		NamespaceUtil.sortNamespaces(namespaces);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelFiles.add(buildFile(isTest, namespace));
		}
		//buf.put(generateInformationBlockClose());
		return modelFiles;
	}

	protected ModelFile buildFile(boolean isTest, Namespace namespace) throws Exception {
		Buf buf = new Buf();
		buf.put(generateSchemaOpen(namespace));
		buf.put(generateSchemaImports(namespace));
		buf.put(generateSchemaElements(namespace));
		//buf.put(generateSchemaAttributes(namespace));
		//buf.put(generateNamespaceProperties(namespace));
		buf.put(generateSchemaClose());

		String filePath = new String(namespace.getFilePath());
		if (filePath.endsWith(".aries"))
			filePath = filePath.replace(".aries", ".xsd");
		//String filePath = NamespaceUtil.getXSDFileName(namespace);
		//ModelFile modelFile = createMainResourcesFile("model", filePath);

		Project project = context.getProject();
		//String targetProject = project.getName();
		//List<Module> modules = ProjectUtil.getAllModules(project);
		//if (modules.size() == 1)
		//	targetProject = project.getName() + "/project/" + modules.get(0).getArtifactId();
		
		/* This is used to generate directly into the place thats gets copied from.. */
		String cacheLocation = context.getCacheLocation();
		String targetPath = context.getTargetPath(project.getName());
		//filePath = filePath.substring(cacheLocation.length() + 1);
		//filePath = FileUtil.normalizePath(cacheLocation + "/" + filePath);
		
		String projectName = project.getName();
		String workspaceLocation = context.getWorkspaceLocation();

		filePath = filePath.replace(cacheLocation, "");
		if (filePath.startsWith("\\model")) {
			filePath = filePath.substring(7);
			filePath = "\\schema\\" + filePath;
		}
		filePath = filePath.replace(cacheLocation, "");
		//filePath = filePath.replace(workspaceLocation + "/" + projectName, "");
		filePath = FileUtil.normalizePath(targetPath + "/src/main/resources" + filePath);
		//filePath = FileUtil.normalizePath(workspaceLocation + "/" + projectName + filePath);
		//String targetPath = context.getTargetWorkspace() + "/" + projectName + "/" + filePath;
		//targetPath = FileUtil.normalizePath(targetPath);
		
		String parentDirectory = FileUtil.getDirectory(filePath);
		String fileName = FileUtil.getFileName(filePath);
		
		ModelFile modelFile = new ModelFile();
		modelFile.setTargetPath(parentDirectory);
		modelFile.setTargetFolder(parentDirectory);
		modelFile.setTargetFile(fileName);
		modelFile.setTextContent(buf.get());
		return modelFile;
	}

	protected String generateSchemaOpen(Namespace namespace) {
		String elementFormDefault = "qualified";
		String attributeFormDefault = "qualified";
		String prefix = namespace.getPrefix();
		String uri = namespace.getUri();
		
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		buf.putLine("");
		buf.putLine("<xs:schema");
		buf.putLine("	version=\"1.0\"");
		buf.putLine("	jaxb:version=\"2.0\"");
		buf.putLine("	elementFormDefault=\""+elementFormDefault+"\"");
		buf.putLine("	attributeFormDefault=\""+attributeFormDefault+"\"");
		buf.putLine("	xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
		buf.putLine("	xmlns:jaxb=\"http://java.sun.com/xml/ns/jaxb\"");
		buf.putLine("	xmlns:xjc=\"http://java.sun.com/xml/ns/jaxb/xjc\"");
		
		List<Namespace> importedNamespaces = namespace.getImports();
		NamespaceUtil.sortNamespaces(importedNamespaces);
		Iterator<Namespace> iterator = importedNamespaces.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = iterator.next();
			String importedPrefix = importedNamespace.getPrefix();
			String importedUri = importedNamespace.getUri();
			buf.putLine("	xmlns:"+importedPrefix+"=\""+importedUri+"\"");
		}
		buf.putLine("	xmlns:"+prefix+"=\""+uri+"\"");
		buf.putLine("	targetNamespace=\""+uri+"\">");
		return buf.get();
	}

	protected String generateSchemaImports(Namespace namespace) {
		Buf buf = new Buf();
		List<Namespace> importedNamespaces = NamespaceUtil.getImports(namespace);
		NamespaceUtil.sortNamespaces(importedNamespaces);
		Iterator<Namespace> iterator = importedNamespaces.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = (Namespace) iterator.next();
			buf.put(generateSchemaImport(importedNamespace));
		}
		return buf.get();
	}
	
	protected String generateSchemaImport(Namespace namespace) {
		Buf buf = new Buf();
		String uri = namespace.getUri();
		String filePath = namespace.getFilePath();
		if (filePath.startsWith("/"))
			filePath = ".." + filePath;
		else filePath = "../" + filePath;
		buf.putLine1("");
		buf.putLine1("<xs:import namespace=\""+uri+"\" schemaLocation=\""+filePath+"\" />");
		return buf.get();
	}

	protected String generateSchemaElements(Namespace namespace) {
		Buf buf = new Buf();
		List<Element> elements = NamespaceUtil.getElements(namespace);
		List<Enumeration> enumerations = NamespaceUtil.getEnumerations(namespace);
		List<Type> types = new ArrayList<Type>();
		types.addAll(elements);
		types.addAll(enumerations);
		TypeUtil.sortByQualifiedName(types);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Element) {
				buf.put(generateElementXSD(namespace, (Element) type));
			} else if (type instanceof Enumeration) {
				buf.put(generateEnumerationXSD(namespace, (Enumeration) type, false));
				buf.put(generateEnumerationXSD(namespace, (Enumeration) type, true));
			}
		}
		return buf.get();
	}
	
//	protected String getEntityElement(Namespace namespace) {
//		Buf buf = new Buf();
//		List<Element> elements = NamespaceUtil.getElements(namespace);
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			buf.put(generateElementXSD(namespace, element));
//		}
//		return buf.get();
//	}
	
	protected String generateEnumerationXSD(Namespace namespace, Enumeration enumeration, boolean generateLabels) {
		String enumerationName = NameUtil.capName(enumeration.getName());
		if (generateLabels)
			enumerationName += "Name";
		
		Buf buf = new Buf();
		buf.putLine1("");
		buf.put(generateTypeNameComment(enumerationName+" enumeration"));
		buf.putLine1("");

		buf.putLine1("<xs:simpleType name=\""+enumerationName+"\">");
		buf.putLine1("	<xs:restriction base=\"xs:string\">");
		List<Literal> literals = enumeration.getLiterals();
		Iterator<Literal> iterator = literals.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			String name = literal.getName().toUpperCase();
			if (generateLabels) {
				String label = literal.getLabel();
				if (label != null)
					name = label;
			}
			buf.putLine1("		<xs:enumeration value=\""+name+"\" />");
		}
		buf.putLine1("	</xs:restriction>");
		buf.putLine1("</xs:simpleType>");
		return buf.get();
	}
	
	protected String generateElementXSD(Namespace namespace, Element element) {
		String namespaceName = namespace.getName();
		String namespacePrefix = namespace.getPrefix();
		String elementName = NameUtil.capName(element.getName());
		String elementNameUncapped = NameUtil.uncapName(element.getName());
		
		Buf buf = new Buf();
		buf.putLine1("");
		buf.put(generateTypeNameComment(elementName+" element"));
		buf.putLine1("");
		
		buf.putLine1("<xs:element name=\""+elementNameUncapped+"\" type=\""+namespacePrefix+":"+elementName+"\" />");
		buf.putLine1("<xs:complexType name=\""+elementName+"\">");
		buf.putLine1("	<xs:sequence>");
		
		//buf.putLine1("		<xs:element name=\"id\" type=\"xs:long\" minOccurs=\"0\" />");

		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isInverse(field))
				continue;
			
//			if (elementNameUncapped.equals("preferences"))
//				System.out.println();
			
			String structure = field.getStructure();
			Element fieldElement = context.getElementByType(field.getType());
			Enumeration fieldEnumeration = context.getEnumerationByType(field.getType());
			
			if (structure.equals("map")) {
				String name = field.getName();
				String fieldNamespacePrefix = "common";
				String fieldNamespaceLocalPart = "Map";

				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("<xs:element name=\""+name+"\"");
				stringBuffer.append(" type=\""+fieldNamespacePrefix+":"+fieldNamespaceLocalPart+"\"");
				
				if (FieldUtil.isRequired(field))
					stringBuffer.append(" minOccurs=\"1\"");
				else stringBuffer.append(" minOccurs=\"0\"");

				if (FieldUtil.isNillable(field))
					stringBuffer.append(" nillable=\"true\"");
				stringBuffer.append(" />");
				buf.putLine4(stringBuffer.toString());
				
			} else if (fieldElement != null) {
				String name = field.getName();
				String type = fieldElement.getType();
				String fieldNamespaceUri = TypeUtil.getNamespace(type);
				Namespace fieldNamespace = context.getNamespaceByUri(fieldNamespaceUri);
				String fieldNamespacePrefix = fieldNamespace.getPrefix();
				String fieldNamespaceLocalPart = NameUtil.capName(TypeUtil.getLocalPart(type));
				
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("<xs:element name=\""+name+"\"");
				stringBuffer.append(" type=\""+fieldNamespacePrefix+":"+fieldNamespaceLocalPart+"\"");
				
				if (FieldUtil.isRequired(field))
					stringBuffer.append(" minOccurs=\"1\"");
				else stringBuffer.append(" minOccurs=\"0\"");
				if (!structure.equals("item"))
					stringBuffer.append(" maxOccurs=\"unbounded\"");

				if (FieldUtil.isNillable(field))
					stringBuffer.append(" nillable=\"true\"");
				stringBuffer.append(" />");
				buf.putLine4(stringBuffer.toString());
				
			} else if (fieldEnumeration != null) {
				String name = field.getName();
				String type = fieldEnumeration.getType();
				String fieldNamespaceUri = TypeUtil.getNamespace(type);
				Namespace fieldNamespace = context.getNamespaceByUri(fieldNamespaceUri);
				String fieldNamespacePrefix = fieldNamespace.getPrefix();
				String fieldNamespaceLocalPart = NameUtil.capName(TypeUtil.getLocalPart(type));
				
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("<xs:element name=\""+name+"\"");
				stringBuffer.append(" type=\""+fieldNamespacePrefix+":"+fieldNamespaceLocalPart+"\"");

				if (FieldUtil.isRequired(field))
					stringBuffer.append(" minOccurs=\"1\"");
				else stringBuffer.append(" minOccurs=\"0\"");
				if (!structure.equals("item"))
					stringBuffer.append(" maxOccurs=\"unbounded\"");

				if (FieldUtil.isNillable(field))
					stringBuffer.append(" nillable=\"true\"");
				stringBuffer.append(" />");
				buf.putLine4(stringBuffer.toString());
				
			} else {
				String name = field.getName();
				String type = field.getType();
				String localPart = TypeUtil.getLocalPart(type);
				
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("<xs:element name=\""+name+"\"");
				stringBuffer.append(" type=\"xs:"+localPart+"\"");
				
				if (FieldUtil.isRequired(field))
					stringBuffer.append(" minOccurs=\"1\"");
				else stringBuffer.append(" minOccurs=\"0\"");
				if (!structure.equals("item"))
					stringBuffer.append(" maxOccurs=\"unbounded\"");
				
				String defaultValue = field.getDefault();
				if (!StringUtils.isEmpty(defaultValue))
					stringBuffer.append(" default=\""+defaultValue+"\"");
				
				if (FieldUtil.isNillable(field))
					stringBuffer.append(" nillable=\"true\"");
				stringBuffer.append(" />");
				buf.putLine4(stringBuffer.toString());
			}
		}

//		buf.putLine1("		<xs:element name=\"enabled\" type=\"xs:boolean\" minOccurs=\"0\" default=\"true\" />");
//		buf.putLine1("		<xs:element name=\"firstNames\" type=\"xs:string\" minOccurs=\"0\" />");
//		buf.putLine1("		<xs:element name=\"lastNames\" type=\"xs:string\" minOccurs=\"0\" />");
//		buf.putLine1("		<xs:element name=\"userNames\" type=\"xs:string\" minOccurs=\"0\" />");
//		buf.putLine1("		<xs:element name=\"emailAddresses\" type=\"common:EmailAddress\" minOccurs=\"0\" nillable=\"true\" />");
//		buf.putLine1("		<xs:element name=\"streetAddresses\" type=\"common:StreetAddress\" minOccurs=\"0\" nillable=\"true\" />");
//		buf.putLine1("		<xs:element name=\"roles\" type=\"sgiusa:Role\" minOccurs=\"0\" maxOccurs=\"unbounded\" nillable=\"true\" />");
//		buf.putLine1("		<xs:element name=\"creationDateStart\" type=\"xs:dateTime\" minOccurs=\"0\" />");
//		buf.putLine1("		<xs:element name=\"creationDateEnd\" type=\"xs:dateTime\" minOccurs=\"0\" />");
//		buf.putLine1("		<xs:element name=\"lastUpdateStart\" type=\"xs:dateTime\" minOccurs=\"0\" />");
//		buf.putLine1("		<xs:element name=\"lastUpdateEnd\" type=\"xs:dateTime\" minOccurs=\"0\" />");

		buf.putLine1("	</xs:sequence>");
		buf.putLine1("</xs:complexType>");
		return buf.get();
	}
	
	private String generateTypeNameComment(String name) {
		Buf buf = new Buf();
		buf.putLine1("<!--");
		buf.putLine2(name);
		buf.put2("");
		int nameLength = name.length();
		for (int i=0; i < nameLength; i++)
			buf.put("*");
		buf.putLine("");
		buf.putLine1("-->");
		return buf.get();
	}

//	protected String generateNamespaceProperties(Namespace namespace) {
//		Buf buf = new Buf();
//		buf.putLine();
//		buf.putLine1("<properties>");
//		Properties properties = namespace.getProperties();
//		Iterator<Property> iterator = properties.getProperties().iterator();
//		while (iterator.hasNext()) {
//			Property property = iterator.next();
//			String name = property.getName();
//			String value = property.getValue();
//			buf.putLine3("<property name=\""+name+"\" value=\""+value+"\"/>");
//		}
//		buf.putLine1("</properties>");
//		return buf.get();
//	}
	
	protected String generateSchemaClose() {
		Buf buf = new Buf();
		buf.putLine("</xs:schema>");
		return buf.get();
	}
	
	protected String generateInformationBlockClose() {
		Buf buf = new Buf();
		return buf.get();
	}
	

}
