package aries.generation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import nam.model.Annotation;
import nam.model.Channel;
import nam.model.Element;
import nam.model.Fault;
import nam.model.Field;
import nam.model.HashType;
import nam.model.Id;
import nam.model.Reference;
import nam.model.Service;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.common.Map;
import org.aries.common.MapEntry;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.ModelFieldUtil;
import aries.generation.engine.GenerationContext;


public class AnnotationUtil {

	public static GenerationContext context;
	
	private static String NL = "\n";
	private static String TAB = "\t";

	public static ModelAnnotation createOverrideAnnotation() {
		ModelAnnotation annotation = createAnnotation("Override");
		return annotation;
	}

	public static ModelAnnotation createSuppressSerialWarning() {
		return createSuppressWarning("serial");
	}

	public static ModelAnnotation createSuppressWarning(String... types) {
		ModelAnnotation annotation = createAnnotation("SuppressWarnings");
		if (types.length == 1) {
			addPart(annotation, "\""+types[0]+"\"");
		} else {
			StringBuffer buf = new StringBuffer();
			buf.append("{");
			for (int i=0; i < types.length; i++) {
				String type = types[i];
				if (i > 0)
					buf.append(", ");
				buf.append("\""+type+"\"");				
			}
			buf.append("}");
			addPart(annotation, buf.toString());
		}
		return annotation;
	}

	public static ModelAnnotation createInjectAnnotation() {
		ModelAnnotation annotation = createAnnotation("Inject");
		return annotation;
	}

	public static ModelAnnotation createNewAnnotation(String className) {
		ModelAnnotation annotation = createAnnotation("New");
		addPart(annotation, className+".class");
		return annotation;
	}

	
	/*
	 * JSF annotations
	 * ---------------
	 */

	public static ModelAnnotation createFacesConverterAnnotation(String elementName, String className) {
		ModelAnnotation annotation = createAnnotation("FacesConverter");
		addPartQuoted(annotation, "value", elementName+"Converter");
		addPart(annotation, "forClass", className+".class");
		return annotation;
	}
	
	

	/*
	 * SEAM annotations
	 * ----------------
	 */

//	public static ModelAnnotation createCreateAnnotation() {
//		ModelAnnotation annotation = createAnnotation("Create");
//		return annotation;
//	}
//	
//	public static ModelAnnotation createAutoCreateAnnotation() {
//		ModelAnnotation annotation = createAnnotation("AutoCreate");
//		return annotation;
//	}
//
//	public static ModelAnnotation createBeginAnnotation() {
//		return createBeginAnnotation(false);
//	}
//	
//	public static ModelAnnotation createBeginAnnotation(boolean join) {
//		ModelAnnotation annotation = createAnnotation("Begin");
//		if (join)
//			addPart(annotation, "join", join);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createFactoryAnnotation(String value) {
//		ModelAnnotation annotation = createAnnotation("Factory");
//		//instanceName = NameUtil.getClassName(instanceName);
//		//instanceName = NameUtil.uncapName(instanceName);
//		addPartQuoted(annotation, "value", value);
//		//System.out.println(instanceName);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createNameAnnotation(String instanceName) {
//		ModelAnnotation annotation = createAnnotation("Name");
//		//instanceName = NameUtil.getClassName(instanceName);
//		//instanceName = NameUtil.uncapName(instanceName);
//		addPartQuoted(annotation, instanceName);
//		//System.out.println(instanceName);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createRoleAnnotation(String instanceName, ScopeType scopeType) {
//		ModelAnnotation annotation = createAnnotation("Role");
//		addPartQuoted(annotation, "name", instanceName);
//		addPart(annotation, "scope", "ScopeType."+scopeType.toString());
//		return annotation;
//	}
//
//	public static ModelAnnotation createRoleAnnotation2(String instanceName, ScopeType scopeType) {
//		ModelAnnotation annotation = createAnnotation("org.jboss.seam.annotations.Role");
//		addPartQuoted(annotation, "name", instanceName);
//		addPart(annotation, "scope", "ScopeType."+scopeType.toString());
//		return annotation;
//	}
//
//	public static ModelAnnotation createScopeAnnotation(ScopeType scopeType) {
//		ModelAnnotation annotation = createAnnotation("Scope");
//		addPart(annotation, "ScopeType."+scopeType.toString());
//		return annotation;
//	}
//
//	public static ModelAnnotation createInAnnotation(boolean required) {
//		ModelAnnotation annotation = createAnnotation("In");
//		addPart(annotation, "required", required);
//		return annotation;
//	}
//
//	public static ModelAnnotation createInAnnotation(boolean required, String value) {
//		ModelAnnotation annotation = createAnnotation("In");
//		addPartQuoted(annotation, "value", value);
//		addPart(annotation, "required", required);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createInAnnotationUnquoted(boolean required, String value) {
//		ModelAnnotation annotation = createAnnotation("In");
//		addPart(annotation, "value", value);
//		addPart(annotation, "required", required);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createOutAnnotation(boolean required) {
//		ModelAnnotation annotation = createAnnotation("Out");
//		addPart(annotation, "required", required);
//		return annotation;
//	}
//
//	public static ModelAnnotation createTransactionalAnnotation() {
//		ModelAnnotation annotation = createAnnotation("Transactional");
//		return annotation;
//	}
//
//	public static ModelAnnotation createBypassInterceptorsAnnotation() {
//		ModelAnnotation annotation = createAnnotation("BypassInterceptors");
//		return annotation;
//	}
//
//	public static ModelAnnotation createEnactAnnotation(ModelClass modelClass, Field field) {
//		String name = NameUtil.capName(field.getEnact());
//		ModelAnnotation annotation = AnnotationUtil.createAnnotation(name);
//		if (name.equals("UserPassword")) {
//			if (field.getHash() == null)
//				field.setHash(HashType.MD_5);
//			AnnotationUtil.addPartQuoted(annotation, "hash", field.getHash().value());
//		}
//		modelClass.addImportedClass("org.jboss.seam.annotations.security.management."+name);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createObserverAnnotation(String eventName) {
//		ModelAnnotation annotation = createAnnotation("Observer");
//		addPartQuoted(annotation, eventName);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createRestrictAnnotation(String elExpression) {
//		ModelAnnotation annotation = createAnnotation("Restrict");
//		addPartQuoted(annotation, elExpression);
//		return annotation;
//	}
//	
//	public static ModelAnnotation createConverterAnnotation() {
//		ModelAnnotation annotation = createAnnotation("Converter");
//		return annotation;
//	}
//	
//	public static ModelAnnotation createValidatorAnnotation() {
//		ModelAnnotation annotation = createAnnotation("Validator");
//		return annotation;
//	}
	
	
	/*
	 * JMX annotations
	 * ---------------
	 */

	public static ModelAnnotation createMXBeanAnnotation() {
		ModelAnnotation annotation = createAnnotation("MXBean");
		return annotation;
	}
	
	
	/*
	 * JPA annotations
	 * ---------------
	 */

	public static ModelAnnotation createPersistenceContextAnnotation() {
		return createPersistenceContextAnnotation(null);
	}
	
	public static ModelAnnotation createPersistenceContextAnnotation(String unitName) {
		return createPersistenceContextAnnotation(unitName, null);
	}
	
	public static ModelAnnotation createPersistenceContextAnnotation(String unitName, String type) {
		ModelAnnotation annotation = createAnnotation("PersistenceContext");
		if (unitName != null)
			addPartQuoted(annotation, "unitName", unitName);
		if (type != null)
			addPart(annotation, "type", type);
		return annotation;
	}
	
	public static ModelAnnotation createMappedSuperclassAnnotation() {
		ModelAnnotation annotation = createAnnotation("MappedSuperclass");
		return annotation;
	}
	
	public static ModelAnnotation createEntityAnnotation(ModelClass modelClass) {
		ModelAnnotation annotation = createAnnotation("Entity");
		String elementName = NameUtil.trimFromEnd(modelClass.getClassName(), "Entity");
		addPartQuoted(annotation, "name", elementName);
		modelClass.addImportedClass("javax.persistence.Entity");
		return annotation;
	}
	
	public static ModelAnnotation createTableAnnotation(ModelClass modelClass) {
		ModelAnnotation annotation = createAnnotation("Table");
		String className = NameUtil.toUnderscoredLowercase(modelClass.getClassName());
		String elementName = NameUtil.trimFromEnd(className, "_entity");
		addPartQuoted(annotation, "name", elementName);
		modelClass.addImportedClass("javax.persistence.Table");
		
		//TODO are we going to allow references to be unique too?
		StringBuffer buf = new StringBuffer();
		Set<ModelAttribute> instanceAttributes = modelClass.getInstanceAttributes();
		Iterator<ModelAttribute> iterator = instanceAttributes.iterator();
		for (int i=0; iterator.hasNext();) {
			ModelAttribute modelAttribute = iterator.next();
			if (modelAttribute.isUnique()) {
				String fieldName = NameUtil.toUnderscoredLowercase(modelAttribute.getName());
				if (i > 0)
					buf.append(", ");
				buf.append(fieldName);
				i++;
			}
		}
		String uniqueColumnNames = buf.toString();
		if (!StringUtils.isEmpty(uniqueColumnNames)) {
			addPart(annotation, "uniqueConstraints", "@UniqueConstraint(columnNames = \""+uniqueColumnNames +"\")");
			modelClass.addImportedClass("javax.persistence.UniqueConstraint");
		}
		return annotation;
	}
	
	public static ModelAnnotation createIdAnnotation() {
		ModelAnnotation annotation = createAnnotation("Id");
		return annotation;
	}

	public static ModelAnnotation createGeneratedValueAnnotation() {
		ModelAnnotation annotation = createAnnotation("GeneratedValue");
		addPart(annotation, "strategy", "GenerationType.IDENTITY");
		return annotation;
	}

	public static ModelAnnotation createGeneratedValueAnnotation(Id id, String elementName) {
		//@GeneratedValue(strategy = GenerationType.TABLE, generator = "organizationIdStore")
		ModelAnnotation annotation = createAnnotation("GeneratedValue");
		elementName = NameUtil.uncapName(elementName);
		String idStoreName = elementName+"IdStore";
		addPart(annotation, "strategy", "GenerationType.TABLE");
		addPartQuoted(annotation, "generator", idStoreName);
		return annotation;
	}

	public static ModelAnnotation createTableGeneratorAnnotation(Id id, String elementName) {
		ModelAnnotation annotation = createAnnotation("TableGenerator");
		elementName = NameUtil.uncapName(elementName);
		String idStoreName = elementName+"IdStore";
		String idStoreNameUnderscored = NameUtil.splitStringUsingUnderscores(idStoreName);
		addPartQuoted(annotation, "name", idStoreName);
		addPartQuoted(annotation, "table", idStoreNameUnderscored);
		addPartQuoted(annotation, "pkColumnName", "name");
		addPartQuoted(annotation, "pkColumnValue", elementName+"."+id.getColumn());
		addPartQuoted(annotation, "valueColumnName", "value");
		addPart(annotation, "initialValue", id.getInitialValue());
		addPart(annotation, "allocationSize", id.getAllocationSize());
		return annotation;
	}

	public static ModelAnnotation createColumnAnnotation(Field field) {
		ModelAnnotation annotation = createAnnotation("Column");
		String column = field.getColumn();
		if (StringUtils.isEmpty(column))
			column = field.getName();
		column = NameUtil.toUnderscoredLowercase(column);		
		addPartQuoted(annotation, "name", column);
		if (field.getRequired())
			addPart(annotation, "nullable = false");
		if (!field.getChangeable())
			addPart(annotation, "updatable = false");
		if (field.getUnique())
			addPart(annotation, "unique = true");
		return annotation;
	}
	
	//generate unidirectional OneToOne or ManyToOne association using a foreign key mapping
	public static ModelAnnotation createJoinColumnAnnotation(Element element, Reference reference) {
		ModelAnnotation annotation = createAnnotation("JoinColumn");
		String column = reference.getColumn();
		if (StringUtils.isEmpty(column))
			column = reference.getName();
		column = NameUtil.toUnderscoredLowercase(column) + "_id";
		addPartQuoted(annotation, "name", column);
		if (!reference.getBinding().equals("id"))
			addPartQuoted(annotation, "referencedColumnName", NameUtil.splitStringUsingUnderscores(reference.getBinding()));
		if (reference.getRequired())
			addPart(annotation, "nullable = false");
		if (!reference.getChangeable())
			addPart(annotation, "updatable = false");
		if (reference.getUnique())
			addPart(annotation, "unique = true");
		return annotation;
	}

	
//	@JoinTable(name = "user_role", 
//			joinColumns = @JoinColumn(name = "user_id"), 
//			inverseJoinColumns = @JoinColumn(name = "role_id"))
	
	//generate unidirectional OneToOne or ManyToOne association using a foreign key mapping
	public static ModelAnnotation createJoinTableAnnotationCase2(ModelClass modelClass, Element element, Reference reference) {
		ModelAnnotation annotation = createAnnotation("JoinTable");
		String columnName = reference.getColumn();
		if (StringUtils.isEmpty(columnName))
			columnName = reference.getName();
		String fieldName = NameUtil.toUnderscoredLowercase(columnName);
//		if (fieldName.toLowerCase().endsWith("roles"))
//			System.out.println();
		if (!reference.getStructure().equals("item"))
			fieldName = NameUtil.toSingular(fieldName);
		fieldName = NameUtil.toUnderscoredLowercase(fieldName);
		//String localPart = TypeUtil.getLocalPart(field.getType());
		String modelClassName = modelClass.getClassName();
		if (modelClassName.startsWith("Abstract"))
			modelClassName = modelClassName.substring(8);
		String className = TypeUtil.getClassName(element.getType());
		String elementName = NameUtil.uncapName(className);
		//String className = NameUtil.toUnderscoredLowercase(modelClassName);
		//String entityName = NameUtil.trimFromEnd(className, "_entity");
		elementName = NameUtil.toUnderscoredLowercase(elementName);
		String joinColumnName = NameUtil.toUnderscoredLowercase(elementName) + "_id";
		String inverseJoinColumnName = NameUtil.toUnderscoredLowercase(fieldName) + "_id";
		//String joinColumnName = modelClass.getName().toLowerCase() + "_id";
		//String inverseJoinColumnName = TypeUtil.getLocalPart(item.getType()).toLowerCase() + "_id";
		columnName = elementName + "_" + fieldName;
		addPartQuoted(annotation, "name", columnName);
		addPart(annotation, "joinColumns", "@JoinColumn(name = \""+joinColumnName+"\")");
		addPart(annotation, "inverseJoinColumns", "@JoinColumn(name = \""+inverseJoinColumnName+"\")");
		if (FieldUtil.isUnique(reference))
			addPart(annotation, "uniqueConstraints", "@UniqueConstraint(columnNames = \""+joinColumnName+"\")");
		return annotation;
	}

	public static ModelAnnotation createForeignKeyAnnotation(ModelClass modelClass, Element element, Reference reference) {
		return createForeignKeyAnnotation(modelClass, element, reference, false);
	}
	
	public static ModelAnnotation createForeignKeyAnnotation(ModelClass modelClass, Element element, Reference reference, boolean needInverseFk) {
		ModelAnnotation annotation = createAnnotation("ForeignKey");
		//String modelClassName = modelClass.getClassName();
		//if (modelClassName.startsWith("Abstract"))
		//	modelClassName = modelClassName.substring(8);
		String className = TypeUtil.getClassName(element.getType());
		String elementName = NameUtil.uncapName(className);
		String elementPart = NameUtil.toUnderscoredLowercase(elementName);
		//String className = NameUtil.toUnderscoredLowercase(modelClassName);
		String fieldName = NameUtil.toUnderscoredLowercase(reference.getName());
		if (!reference.getStructure().equals("item"))
			fieldName = NameUtil.toSingular(fieldName);
		String entityName = NameUtil.trimFromEnd(elementPart, "_entity");
		String tableRef = entityName+"_"+fieldName;
		if (tableRef.startsWith("abstract_"))
			tableRef = tableRef.substring(9);
		String name = tableRef + "_fk";
		//String name = tableRef + "_" + entityName + "_fk";
		//String inverseName = tableRef + "_" + fieldName + "_fk";
		addPartQuoted(annotation, "name", name);
		if (needInverseFk) {
			String inverseName = tableRef + "_inverse_fk";
			addPartQuoted(annotation, "inverseName", inverseName);
		}
		return annotation;
	}
	
	public static ModelAnnotation createOnDeleteAnnotation(ModelClass modelClass, Element element, Reference reference) {
		ModelAnnotation annotation = createAnnotation("OnDelete");
		addPart(annotation, "action", "CASCADE");
		return annotation;
	}
	
	public static ModelAnnotation createTemporalDateAnnotation() {
		ModelAnnotation annotation = createAnnotation("Temporal");
		addPart(annotation, "TemporalType.DATE");
		return annotation;
	}

	public static ModelAnnotation createTemporalTimeAnnotation() {
		ModelAnnotation annotation = createAnnotation("Temporal");
		addPart(annotation, "TemporalType.TIME");
		return annotation;
	}

	public static ModelAnnotation createTemporalTimestampAnnotation() {
		ModelAnnotation annotation = createAnnotation("Temporal");
		addPart(annotation, "TemporalType.TIMESTAMP");
		return annotation;
	}
	
	public static ModelAnnotation createOneToOneAnnotation(Reference reference) {
		ModelAnnotation annotation = createAnnotation("OneToOne");
		addCascadeAnnotationPart(annotation, reference);
		if (FieldUtil.isInverse(reference)) {
			String mappedBy = reference.getMappedBy();
			if (!StringUtils.isEmpty(mappedBy)) {
				addPartQuoted(annotation, "mappedBy", mappedBy);
			}
		} else if (FieldUtil.isContained(reference) || FieldUtil.isCascadeDelete(reference)) {
			addPart(annotation, "orphanRemoval", true);
		}
		//TODO We need to understand this better
		//if (FieldUtil.isRequired(reference)) {
		//	addPart(annotation, "optional", false);
		//}
		return annotation;
	}

	public static ModelAnnotation createOneToManyAnnotation(Reference reference) {
		ModelAnnotation annotation = createAnnotation("OneToMany");
		//if (field instanceof Reference) {
			//Reference reference = (Reference) field;
			String mappedBy = reference.getMappedBy();
			if (!StringUtils.isEmpty(mappedBy)) {
				addPartQuoted(annotation, "mappedBy", mappedBy);
			}
		//}
		addCascadeAnnotationPart(annotation, reference);
		addPart(annotation, "orphanRemoval", "true");
		return annotation;
	}

	public static ModelAnnotation createManyToOneAnnotation(Reference reference) {
		ModelAnnotation annotation = createAnnotation("ManyToOne");
		addCascadeAnnotationPart(annotation, reference);
		//addPart(annotation, "targetEntity", className+".class");
		return annotation;
	}

	public static ModelAnnotation createManyToManyAnnotation(Reference reference) {
		ModelAnnotation annotation = createAnnotation("ManyToMany");
		addCascadeAnnotationPart(annotation, reference);
		//addPart(annotation, "targetEntity", className+".class");
		return annotation;
	}

	/**
	 * Provides cascade portion of specified JPA multiplicity annotation.
	 * Enforces default of ALL if/when nothing else is specified.
	 */
	public static void addCascadeAnnotationPart(ModelAnnotation annotation, Reference reference) {
		StringBuffer buf = new StringBuffer();
		String cascade = reference.getCascade();
		if (StringUtils.isEmpty(cascade)) {
			cascade = "all";
		}
		
		StringTokenizer tokenizer = new StringTokenizer(cascade, ",");
		boolean multipleTypes = tokenizer.countTokens() > 1;
		if (multipleTypes)
			buf.append("cascade = {");
		else buf.append("cascade = ");
		for (int i=0; tokenizer.hasMoreTokens(); i++) {
			String token = tokenizer.nextToken().trim();
			String cascadeType = token.toLowerCase();
			if (i > 0)
				buf.append(", ");
			if (cascadeType.equals("all")) {
				buf.append("CascadeType.ALL");
			} else if (cascadeType.equals("refresh")) {
				buf.append("CascadeType.REFRESH");
			} else if (cascadeType.equals("merge")) {
				buf.append("CascadeType.MERGE");
			} else if (cascadeType.equals("remove")) {
				buf.append("CascadeType.REMOVE");
			} else if (cascadeType.equals("persist")) {
				buf.append("CascadeType.PERSIST");
			} else if (cascadeType.equals("detach")) {
				buf.append("CascadeType.DETACH");
			} else {
				throw new RuntimeException("Unrecognized CascadeType: "+token);
			}
		}
		if (multipleTypes)
			buf.append("}");
		if (!StringUtils.isEmpty(buf.toString()))
			addPart(annotation, buf.toString());
	}
	
	public static ModelAnnotation createElementCollectionAnnotation(ModelClass modelClass, ModelField modelField) {
		ModelAnnotation annotation = createAnnotation("ElementCollection");
		modelClass.addImportedClass("javax.persistence.ElementCollection");
		//modelClass.addStaticImportedClass("javax.persistence.FetchType.EAGER");
		addPart(annotation, "targetClass", modelField.getClassName()+".class");
		//addPart(annotation, "fetch", "EAGER");
		return annotation;
	}

	public static ModelAnnotation createCollectionTableAnnotation(ModelClass modelClass, ModelField modelField) {
		//@CollectionTable(name = "user_permission_activity_group", joinColumns = @JoinColumn(name = "permission_id"))
		ModelAnnotation annotation = createAnnotation("CollectionTable");
		modelClass.addImportedClass("javax.persistence.CollectionTable");
		String elementClassName = NameUtil.splitStringUsingUnderscores(modelClass.getClassName());
		String elementName = NameUtil.trimFromEnd(elementClassName, "_entity");
		String fieldName = NameUtil.toUnderscoredLowercase(modelField.getName());
		if (!modelField.getStructure().equals("item"))
			fieldName = NameUtil.toSingular(fieldName);
				String tableName = elementName+"_"+fieldName;
		addPartQuoted(annotation, "name", tableName);
		String joinColumnName = elementName + "_id";
		addPart(annotation, "joinColumns", "@JoinColumn(name = \""+joinColumnName+"\")");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		//String inverseJoinColumnName = fieldType + "_id";
		//if (!CodeGenUtil.isJavaDefaultType(modelField.getClassName()))
		//	addPart(annotation, "inverseJoinColumns", "@JoinColumn(name = \""+inverseJoinColumnName+"\")");
		if (modelField.isUnique()) {
			addPart(annotation, "uniqueConstraints", "@UniqueConstraint(columnNames = \""+joinColumnName+"\")");
			modelClass.addImportedClass("javax.persistence.UniqueConstraint");
		}
		return annotation;
	}

	public static ModelAnnotation createEnumeratedAnnotation(String className) {
		Assert.isTrue(CodeGenUtil.isJavaDefaultType(className));
		ModelAnnotation annotation = createAnnotation("Enumerated");
		if (className.endsWith("String"))
			addPart(annotation, "EnumType.STRING");
		else addPart(annotation, "EnumType.ORDINAL");
		return annotation;
	}

	public static ModelAnnotation createCacheConcurrencyAnnotation(ModelClass modelClass) {
		ModelAnnotation annotation = createAnnotation("Cache");
		addPart(annotation, "usage = READ_WRITE");
		modelClass.addImportedClass("org.hibernate.annotations.Cache");
		//modelClass.addImportedClass("org.hibernate.annotations.CacheConcurrencyStrategy");
		modelClass.addStaticImportedClass("org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE");
		return annotation;
	}

	
	/*
	 * JAXB annotations
	 * ----------------
	 */

	public static ModelAnnotation createXmlFieldAccessorType() {
		ModelAnnotation annotation = new ModelAnnotation();
		annotation.setLabel("XmlAccessorType");
		addPart(annotation, "XmlAccessType.FIELD");
		return annotation;
	}

	public static ModelAnnotation createXmlType(ModelClass modelClass) {
		ModelAnnotation annotation = createAnnotation("XmlType");
		String xmlTypeName = NameUtil.capName(modelClass.getClassName()); 
		addPartQuoted(annotation, "name", xmlTypeName);
		addPartQuoted(annotation, "namespace", modelClass.getNamespace());
		Set<ModelField> fields = modelClass.getInstanceFields();
		if (fields.size() > 0)
			addPart(annotation, "propOrder", createXmlTypePropOrderDetail(modelClass, fields));
		return annotation;
	}

	protected static String createXmlTypePropOrderDetail(ModelClass modelClass, Set<ModelField> fields) {
		Iterator<ModelField> iterator = fields.iterator();
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		buf.append(NL);
		for (int i=0; iterator.hasNext(); i++) {
			ModelField modelField = iterator.next();
			if (ModelFieldUtil.isInverse(modelField))
				continue;
			if (modelField.getName().equals("ref"))
				continue;
			
			if (modelField instanceof ModelReference) {
				if (modelField.getType() == null)
					throw new RuntimeException("CODE PROBLEM");
				Element fieldElement = context.getElementByType(modelField.getType());
				if (fieldElement != null) {
					boolean isTwoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(fieldElement);
					boolean isInverse = ModelFieldUtil.isInverse(modelField);
					if (isInverse && isTwoWaySelfReferencing == false)
						continue;
				}
			}

			if (i > 0) {
				buf.append(",");
				buf.append(NL);
			}
			buf.append(TAB);
			buf.append("\""+modelField.getUncappedName()+"\"");
		}
		buf.append(NL);
		buf.append("}");
		return buf.toString();
	}
	
	public static ModelAnnotation createXmlEnum(ModelEnum modelEnum) {
		ModelAnnotation annotation = createAnnotation("XmlEnum");
		return annotation;
	}
	
	public static ModelAnnotation createXmlEnumValue(ModelLiteral modelLiteral) {
		ModelAnnotation annotation = createAnnotation("XmlEnumValue");
		addPartQuoted(annotation, modelLiteral.getText());
		return annotation;
	}
	
	public static ModelAnnotation createXmlRegistry(ModelClass modelClass) {
		ModelAnnotation annotation = createAnnotation("XmlRegistry");
		return annotation;
	}
	
	public static ModelAnnotation createXmlRootElement(ModelClass modelClass) {
		ModelAnnotation annotation = createAnnotation("XmlRootElement");
		String xmlElementName = NameUtil.uncapName(modelClass.getClassName()); 
		addPartQuoted(annotation, "name", xmlElementName);
		addPartQuoted(annotation, "namespace", modelClass.getNamespace());
		return annotation;
	}
	
	//TODO several options should be available here...
	public static ModelAnnotation createXmlAttribute(ModelClass modelClass, ModelAttribute modelAttribute) {
		ModelAnnotation annotation = createAnnotation("XmlAttribute");
		String name = NameUtil.splitStringUsingDashes(modelAttribute.getName());
		addPartQuoted(annotation, "name", name);
		//TODO For now - take out namespace part
		//TODO Find out why this causes problems in Elements which extend from other Elements
		//addPartQuoted(annotation, "namespace", modelClass.getNamespace());
		if (modelAttribute.isRequired())
			addPart(annotation, "required", "true");
		return annotation;
	}

	//TODO several options should be available here...
	//TODO String name = NameUtil.splitStringUsingDashes(modelField.getName());
	public static ModelAnnotation createXmlElement(ModelClass modelClass, ModelField modelField) {
		ModelAnnotation annotation = createAnnotation("XmlElement");
		addPartQuoted(annotation, "name", modelField.getName());
		addPartQuoted(annotation, "namespace", modelClass.getNamespace());
		String qualifiedName = modelField.getQualifiedName();
		if (qualifiedName.equals("java.lang.Boolean") || qualifiedName.equals("java.util.Date"))
			addPart(annotation, "type", "String.class");
		
		if (modelField.getDefault() != null)
			addPartQuoted(annotation, "defaultValue", modelField.getDefault().toString());
		
		if (modelField.isRequired())
			addPart(annotation, "required", "true");
		if (modelField.isNullable())
			addPart(annotation, "nillable", "true");
		return annotation;
	}

	//TODO add option for this:
	//TODO String name = NameUtil.splitStringUsingDashes(modelField.getName());
	public static ModelAnnotation createXmlElements(ModelClass modelClass, ModelReference modelReference) {
		ModelAnnotation annotation = createAnnotation("XmlElements");

		Buf buf = new Buf();
		buf.putLine("{");
		List<String> acceptedTypes = modelReference.getAcceptedTypes();
		Iterator<String> iterator = acceptedTypes.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String acceptedType = iterator.next();
			String acceptedTypeUncapped = NameUtil.uncapName(acceptedType);
			String acceptedTypeCapped = NameUtil.capName(acceptedType);

			ModelAnnotation annotation2 = createAnnotation("XmlElement");
			addPartQuoted(annotation2, "name", acceptedTypeUncapped);
			addPartQuoted(annotation2, "namespace", modelClass.getNamespace());
			
			String qualifiedName = modelReference.getQualifiedName();
			if (qualifiedName.equals("java.lang.Boolean") || qualifiedName.equals("java.util.Date"))
				addPart(annotation2, "type", "String.class");
			else addPart(annotation2, "type", acceptedTypeCapped+".class");

			if (modelReference.isRequired())
				addPart(annotation2, "required", "true");
			if (modelReference.isNullable())
				addPart(annotation2, "nillable", "true");

			buf.put2("@XmlElement("+annotation2.getPartsAsString()+")");
			if (i < acceptedTypes.size() - 1)
				buf.put(",");
			buf.putLine("");
		}
		buf.put1("}");
		if (!StringUtils.isEmpty(buf.toString()))
			addPart(annotation, buf.toString());
		return annotation;
	}

	public static ModelAnnotation createXmlJavaTypeAdapter(String adapterClassName) {
		ModelAnnotation annotation = createAnnotation("XmlJavaTypeAdapter");
		addPart(annotation, adapterClassName);
		return annotation;
	}

	public static ModelAnnotation createXmlSchemaType(String schemaTypeName) {
		ModelAnnotation annotation = createAnnotation("XmlSchemaType");
		addPartQuoted(annotation, "name", schemaTypeName);
		return annotation;
	}

	public static ModelAnnotation createXmlTransient() {
		ModelAnnotation annotation = createAnnotation("XmlTransient");
		return annotation;
	}
	
	
	/*
	 * JAXWS annotations
	 * -----------------
	 */

	public static ModelAnnotation createWebServiceAnnotation(Service service) {
		return createWebServiceAnnotation(service, false);
	}

	public static ModelAnnotation createWebServiceInterfaceAnnotation(Service service) {
		return createWebServiceAnnotation(service, true);
	}

	public static ModelAnnotation createWebServiceAnnotation(Service service, boolean isInterface) {
		ModelAnnotation annotation = createAnnotation("WebService");
		String name = service.getName();
		addPartQuoted(annotation, "name", name);
		if (!isInterface) {
			addPartQuoted(annotation, "serviceName", name+"Service");
			//addPartQuoted(annotation, "portName", name+"Port");
			addPartQuoted(annotation, "portName", name);
		}
		addPartQuoted(annotation, "targetNamespace", service.getNamespace());
		return annotation;
	}

	public static ModelAnnotation createWebServiceClientAnnotation(Service service) {
		ModelAnnotation annotation = createAnnotation("WebServiceClient");
		String name = service.getName();
		addPartQuoted(annotation, "name", name);
		//addPartQuoted(annotation, "wsdlLocation", service.getWsdlLocation());
		addPartQuoted(annotation, "targetNamespace", service.getNamespace());
		return annotation;
	}

	public static ModelAnnotation createWebEndpointAnnotation(String endpointName) {
		ModelAnnotation annotation = createAnnotation("WebEndpoint");
		addPartQuoted(annotation, "name", endpointName);
		return annotation;
	}

	public static ModelAnnotation createOnewayAnnotation() {
		ModelAnnotation annotation = createAnnotation("Oneway");
		return annotation;
	}

	public static ModelAnnotation createWebMethodAnnotation() {
		return createWebMethodAnnotation(null);
	}
	
	public static ModelAnnotation createWebMethodAnnotation(String operationName) {
		ModelAnnotation annotation = createAnnotation("WebMethod");
		if (operationName != null)
			addPartQuoted(annotation, "operationName", operationName);
		return annotation;
	}

	public static ModelAnnotation createWebParamAnnotation() {
		ModelAnnotation annotation = createAnnotation("WebParam");
		return annotation;
	}

	public static ModelAnnotation createWebResultAnnotation() {
		return createWebResultAnnotation(null);
	}
	
	public static ModelAnnotation createWebResultAnnotation(String resultName) {
		ModelAnnotation annotation = createAnnotation("WebResult");
		if (resultName != null)
			addPartQuoted(annotation, "name", resultName);
		return annotation;
	}

	public static ModelAnnotation createWebFaultAnnotation(Fault fault) {
		ModelAnnotation annotation = createAnnotation("WebFault");
		addPartQuoted(annotation, "name", fault.getName());
		addPartQuoted(annotation, "targetNamespace", TypeUtil.getNamespace(fault.getType()));
		return annotation;
	}
	
	public static ModelAnnotation createSOAPBindingRPCStyleAnnotation() {
		ModelAnnotation annotation = createAnnotation("SOAPBinding");
		addPart(annotation, "style", "SOAPBinding.Style.RPC");
		return annotation;
	}

	public static ModelAnnotation createHandlerChainAnnotation(String fileName) {
		ModelAnnotation annotation = createAnnotation("HandlerChain");
		addPartQuoted(annotation, "file", fileName);
		return annotation;
	}

	
	/*
	 * CDI annotations
	 * ---------------
	 */
	
	public static ModelAnnotation createObservesAnnotation() {
		ModelAnnotation annotation = createAnnotation("Observes");
		return annotation;
	}
	
	public static ModelAnnotation createProducesAnnotation() {
		ModelAnnotation annotation = createAnnotation("Produces");
		return annotation;
	}
	
	public static ModelAnnotation createNamedAnnotation(String name) {
		ModelAnnotation annotation = createAnnotation("Named");
		addPartQuoted(annotation, name);
		return annotation;
	}
	
	public static ModelAnnotation createApplicationScopedAnnotation() {
		ModelAnnotation annotation = createAnnotation("ApplicationScoped");
		return annotation;
	}
	
	public static ModelAnnotation createRequestScopedAnnotation() {
		ModelAnnotation annotation = createAnnotation("RequestScoped");
		return annotation;
	}
	
	public static ModelAnnotation createSessionScopedAnnotation() {
		ModelAnnotation annotation = createAnnotation("SessionScoped");
		return annotation;
	}
	
	public static ModelAnnotation createViewScopedAnnotation() {
		ModelAnnotation annotation = createAnnotation("ViewScoped");
		return annotation;
	}
	

	
	/*
	 * EJB annotations
	 * ---------------
	 */

	public static ModelAnnotation createStartupAnnotation() {
		ModelAnnotation annotation = createAnnotation("Startup");
		return annotation;
	}
	
	public static ModelAnnotation createSingletonAnnotation() {
		ModelAnnotation annotation = createAnnotation("Singleton");
		return annotation;
	}
	
	public static ModelAnnotation createRemoteAnnotation(Service service) {
		ModelAnnotation annotation = createAnnotation("Remote");
		String name = service.getInterfaceName();
		addPart(annotation, name+".class");
		return annotation;
	}
	
	public static ModelAnnotation createStatelessAnnotation() {
		return createStatelessAnnotation(null);
	}
	
	public static ModelAnnotation createStatelessAnnotation(Service service) {
		ModelAnnotation annotation = createAnnotation("Stateless");
		if (service != null) {
			String name = service.getInterfaceName();
			addPartQuoted(annotation, "name", name);
		}
		return annotation;
	}

	public static ModelAnnotation createStatefulAnnotation() {
		return createStatefulAnnotation(null);
	}
	
	public static ModelAnnotation createStatefulAnnotation(Service service) {
		ModelAnnotation annotation = createAnnotation("Stateful");
		if (service != null) {
			String name = service.getInterfaceName();
			addPartQuoted(annotation, "name", name);
		}
		return annotation;
	}

	public static ModelAnnotation createLocalAnnotation() {
		ModelAnnotation annotation = createAnnotation("Local");
		return annotation;
	}

	public static ModelAnnotation createLocalAnnotation(Service service) {
		return createLocalAnnotation(service.getInterfaceName());
	}
	
	public static ModelAnnotation createLocalAnnotation(String className) {
		ModelAnnotation annotation = createAnnotation("Local");
		if (className != null) {
			addPart(annotation, className + ".class");
		}
		return annotation;
	}

	public static ModelAnnotation createLocalBeanAnnotation() {
		ModelAnnotation annotation = createAnnotation("LocalBean");
		return annotation;
	}
	
	public static ModelAnnotation createConcurrencyManagementAnnotation() {
		ModelAnnotation annotation = createAnnotation("ConcurrencyManagement");
		addPart(annotation, "BEAN");
		return annotation;
	}
	
	public static ModelAnnotation createPostConstructAnnotation() {
		ModelAnnotation annotation = createAnnotation("PostConstruct");
		return annotation;
	}
	
	public static ModelAnnotation createPreDestroyAnnotation() {
		ModelAnnotation annotation = createAnnotation("PreDestroy");
		return annotation;
	}
	
	public static ModelAnnotation createTransactionManagementAnnotation() {
		ModelAnnotation annotation = createAnnotation("TransactionManagement");
		addPart(annotation, "CONTAINER");
		return annotation;
	}

	public static ModelAnnotation createTransactionAttributeAnnotation() {
		return createTransactionAttributeAnnotation("REQUIRED");
	}
	
	public static ModelAnnotation createTransactionAttributeAnnotation(String attribute) {
		ModelAnnotation annotation = createAnnotation("TransactionAttribute");
		addPart(annotation, attribute);
		return annotation;
	}
	
	public static ModelAnnotation createMessageDrivenQueueBasedAnnotation(Service service, Channel channel) {
		ModelAnnotation annotation = createAnnotation("MessageDriven");
		String channelNameUncapped = NameUtil.uncapName(channel.getName());
		String channelNameCapped = NameUtil.capName(channel.getName());
		String listenerName = channelNameUncapped + service.getInterfaceName() + "Listener";
		
		String clientId = channelNameUncapped + "." + service.getDomain() + "." + service.getName();
		if (channel.getName().endsWith(service.getDomain()) ||
			channel.getName().equalsIgnoreCase(service.getDomain()))
			clientId = service.getDomain() + "." + service.getName();
		String destinationNameBase = MessagingUtil.getDestinationName(service, channel);
		String destinationName = "java:/queue/" + destinationNameBase;
		String destinationType = "javax.jms.Queue";
		boolean durable = true;
		int maxSession = 2;
		int reconnectAttempts = -1;

//		if (destinationNameBase.equals("inventory_bookshop2_supplier_book_orders_manager_queue"))
//			System.out.println();
		
		addPartQuoted(annotation, "name", listenerName);
		Buf activationConfig = new Buf();
		activationConfig.putLine("{");
		activationConfig.putLine2("@ActivationConfigProperty(propertyName = \"clientId\", propertyValue = \""+clientId+"\"),");
		activationConfig.putLine2("@ActivationConfigProperty(propertyName = \"destination\", propertyValue = \""+destinationName+"\"),");
		activationConfig.putLine2("@ActivationConfigProperty(propertyName = \"destinationType\", propertyValue = \""+destinationType+"\"),");
		if (durable)
			activationConfig.putLine2("@ActivationConfigProperty(propertyName = \"subscriptionDurability\", propertyValue = \"Durable\"),");
		activationConfig.putLine2("@ActivationConfigProperty(propertyName = \"reconnectAttempts\", propertyValue = \""+reconnectAttempts+"\"),");
		activationConfig.putLine2("@ActivationConfigProperty(propertyName = \"maxSession\", propertyValue = \""+maxSession+"\")");
		activationConfig.put("}");
		addPart(annotation, "activationConfig", activationConfig.get());
		return annotation;
	}
	
	public static ModelAnnotation createInterceptorsAnnotation(String className) {
		ModelAnnotation annotation = createAnnotation("Interceptors");
		addPart(annotation, className);
		return annotation;
	}

	public static ModelAnnotation createAsynchronousAnnotation() {
		ModelAnnotation annotation = createAnnotation("Asynchronous");
		return annotation;
	}
	
	public static ModelAnnotation createAccessTimeoutAnnotation(int ms) {
		ModelAnnotation annotation = createAnnotation("AccessTimeout");
		addPart(annotation, "value", ms);
		return annotation;
	}
	
	public static ModelAnnotation createApplicationExceptionAnnotation(Fault fault) {
		ModelAnnotation annotation = createAnnotation("ApplicationException");
		addPart(annotation, "rollback", "true");
		return annotation;
	}
	
	

	/*
	 * JUNIT annotations
	 * -----------------
	 */

	public static ModelAnnotation createJUnitSuiteRunWithAnnotation() {
		ModelAnnotation annotation = createJUnitSuiteRunWithAnnotation("Suite");
		return annotation;
	}
	
	public static ModelAnnotation createJUnitSuiteRunWithAnnotation(String className) {
		ModelAnnotation annotation = createAnnotation("RunWith");
		addPart(annotation, className+".class");
		return annotation;
	}

	public static ModelAnnotation createJUnitSuiteClassesAnnotation(List<String> elementClasses) {
		ModelAnnotation annotation = createAnnotation("Suite.SuiteClasses");
		Buf buf = new Buf();
		buf.putLine("{");
		int size = elementClasses.size();
		Iterator<String> iterator = elementClasses.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String elementClass = iterator.next();
			buf.put1(elementClass);
			if (i + 1 < size)
				buf.putLine(",");
			else buf.putLine("");
		}
		buf.put("}");
		addPart(annotation, buf.get());
		return annotation;
	}

	public static ModelAnnotation createMockitoJUnitRunnerAnnotation() {
		ModelAnnotation annotation = createAnnotation("RunWith");
		addPart(annotation, "MockitoJUnitRunner.class");
		return annotation;
	}

	public static ModelAnnotation createJUnitBeforeAnnotation() {
		ModelAnnotation annotation = createAnnotation("Before");
		return annotation;
	}

	public static ModelAnnotation createJUnitBeforeClassAnnotation() {
		ModelAnnotation annotation = createAnnotation("BeforeClass");
		return annotation;
	}

	public static ModelAnnotation createJUnitAfterAnnotation() {
		ModelAnnotation annotation = createAnnotation("After");
		return annotation;
	}

	public static ModelAnnotation createJUnitAfterClassAnnotation() {
		ModelAnnotation annotation = createAnnotation("AfterClass");
		return annotation;
	}

	public static ModelAnnotation createJUnitTestAnnotation() {
		return createJUnitTestAnnotation(null);
	}

	public static ModelAnnotation createJUnitTestAnnotation(String expectedException) {
		ModelAnnotation annotation = createAnnotation("Test");
		if (expectedException != null)
			addPart(annotation, "expected", expectedException);
		return annotation;
	}

	public static ModelAnnotation createJUnitIgnoreAnnotation() {
		ModelAnnotation annotation = createAnnotation("Ignore");
		return annotation;
	}
	
	public static ModelAnnotation createJUnitIgnoreAnnotationCommented() {
		ModelAnnotation annotation = createAnnotation("//@Ignore");
		return annotation;
	}
	
	
	/*
	 * Arquillian annotations
	 * ----------------------
	 */

	public static ModelAnnotation createArquillianRunAsClientAnnotation() {
		ModelAnnotation annotation = createAnnotation("RunAsClient");
		return annotation;
	}

	public static ModelAnnotation createArquillianDeploymentAnnotation(String name, int order) {
		ModelAnnotation annotation = createAnnotation("Deployment");
		addPartQuoted(annotation, "name", name);
		addPart(annotation, "order", order);
		return annotation;
	}

	public static ModelAnnotation createArquillianTargetsContainerAnnotation(String name) {
		ModelAnnotation annotation = createAnnotation("TargetsContainer");
		addPartQuoted(annotation, name);
		return annotation;
	}

	public static ModelAnnotation createArquillianInSequenceAnnotation(int order) {
		ModelAnnotation annotation = createAnnotation("InSequence");
		addPart(annotation, "value", order);
		return annotation;
	}

	
	/*
	 * Arquillian annotations
	 * ----------------------
	 */

	public static ModelAnnotation createByteManRuleAnnotation(String targetClass, String targetMethod, String targetLocation, String action, int index) {
		ModelAnnotation annotation = createAnnotation("BytemanRule");
		addPartQuoted(annotation, "name", "rule" + index);
		addPartQuoted(annotation, "targetClass", targetClass);
		addPartQuoted(annotation, "targetMethod", targetMethod);
		addPartQuoted(annotation, "targetLocation", targetLocation);
		addPartQuoted(annotation, "action", action);
		annotation.setFormatted(true);
		return annotation;
	}

	
	/*
	 * Suppress Warning Annotation methods
	 * -----------------------------------
	 */

	public static ModelAnnotation createSuppressWarningsAnnotation(String type) {
		ModelAnnotation annotation = createAnnotation("SuppressWarnings");
		addPart(annotation, "\""+type+"\"");
		return annotation;
	}
	
	/*
	 * Helper methods
	 * --------------
	 */
	
	public static List<ModelAnnotation> createModelAnnotations(List<Annotation> annotations) {
		List<ModelAnnotation> modelAnnotations = new ArrayList<ModelAnnotation>();
		Iterator<Annotation> iterator = annotations.iterator();
		while (iterator.hasNext()) {
			Annotation annotation = (Annotation) iterator.next();
			ModelAnnotation modelAnnotation = createModelAnnotation(annotation);
			modelAnnotations.add(modelAnnotation);
		}
		return modelAnnotations;
	}

	public static ModelAnnotation createModelAnnotation(Annotation annotation) {
		ModelAnnotation modelAnnotation = new ModelAnnotation();
		modelAnnotation.setLabel(annotation.getSource());
		Map parts = annotation.getDetails();
		List<MapEntry> entries = parts.getEntries();
		Iterator<MapEntry> iterator = entries.iterator();
		while (iterator.hasNext()) {
			MapEntry entry = iterator.next();
			addPart(modelAnnotation, entry);
		}
		return modelAnnotation;
	}
	
	private static void addPart(ModelAnnotation annotation, MapEntry entry) {
		addPart(annotation, entry.getKey(), entry.getValue());
	}

	public static void addPart(ModelAnnotation annotation, Object part) {
		addPart(annotation, part, null);
	}

	public static void addPartQuoted(ModelAnnotation annotation, Object part) {
		addPart(annotation,  "\""+part+"\"", null);
	}

	public static void addPart(ModelAnnotation annotation, Object key, Object value) {
		annotation.addPart(key, value);
	}

	public static void addPartQuoted(ModelAnnotation annotation, Object key, String value) {
		annotation.addPart(key, "\""+value+"\"");
	}

	public static ModelAnnotation createAnnotation(String label) {
		ModelAnnotation annotation = new ModelAnnotation();
		annotation.setLabel(label);
		return annotation;
	}

	public static ModelAnnotation createAnnotation(String label, Object part) {
		ModelAnnotation annotation = createAnnotation(label);
		addPart(annotation, part);
		return annotation;
	}
	
	public static ModelAnnotation createAnnotation(String label, Object part, String value) {
		ModelAnnotation annotation = createAnnotation(label);
		addPart(annotation, part, value);
		return annotation;
	}
	
	public static ModelAnnotation createQuotedAnnotation(String label, Object part) {
		ModelAnnotation annotation = createAnnotation(label);
		addPartQuoted(annotation, part);
		return annotation;
	}
	
	public static ModelAnnotation createQuotedAnnotation(String label, Object part, String value) {
		ModelAnnotation annotation = createAnnotation(label);
		addPartQuoted(annotation, part, value);
		return annotation;
	}

}
