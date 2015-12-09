package aries.codegen;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Component;
import nam.model.Input;
import nam.model.Output;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.GenerateUtil;
import aries.codegen.util.MethodUtil;
import aries.codegen.util.ModelFieldUtil;
import aries.codegen.util.TokenUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelEnum;
import aries.generation.model.ModelField;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelLiteral;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelPackage;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;
import aries.generation.model.ModelUnit;

public abstract class AbstractBeanGenerator extends AbstractFileGenerator implements BeanGenerator {

	// public abstract void generate() throws Exception;

	// protected boolean generateJavadoc = false;

	public AbstractBeanGenerator() {
	}

	public AbstractBeanGenerator(GenerationContext context) {
		this.context = context;
	}

	/*
	 * Package generation ------------------
	 */

	public void generatePackages(Collection<ModelPackage> modelPackages) throws Exception {
		Iterator<ModelPackage> iterator = modelPackages.iterator();
		while (iterator.hasNext()) {
			ModelPackage modelPackage = iterator.next();
			generatePackage(modelPackage);
		}
	}

	public void generatePackage(ModelPackage modelPackage) throws Exception {
		generateClasses(modelPackage.getClasses());
		generateEnums(modelPackage.getEnums());
	}

	/*
	 * Interface generation --------------------
	 */

	public void generateInterfaces(Collection<ModelInterface> modelInterfaces) throws Exception {
		Iterator<ModelInterface> iterator = modelInterfaces.iterator();
		while (iterator.hasNext()) {
			ModelInterface modelInterface = iterator.next();
			generateInterface(modelInterface);
		}
	}

	public void generateInterface(ModelInterface modelInterface) throws Exception {
		generateInterface(modelInterface, false);
	}

	public void generateTest(ModelInterface modelInterface) throws Exception {
		generateInterface(modelInterface, true);
	}

	public void generateInterface(ModelInterface modelInterface, boolean isTest) throws Exception {
		String properInterfaceName = NameUtil.getCompilationUnitName(modelInterface.getClassName());
		String modulePackagePath = GenerateUtil.convertPackageToPath(modelInterface.getPackageName());
		modulePackagePath = modulePackagePath.replace("-", "_");
		String projectName = context.getProject().getName();
		String targetParent = !isTest ? "main" : "test";

		setTargetFile(properInterfaceName + ".java");
		setTargetFolder("src/" + targetParent + "/java" + modulePackagePath);
		setTargetPath(context.getTargetPath(projectName) + "/" + targetFolder);

		Buf buf = new Buf();
		buf.put(generatePackageDeclaration(modelInterface));
		buf.put(generateStaticImportedPackages(modelInterface));
		buf.put(generateImportedPackages(modelInterface));
		// buf.putLine();

		if (context.isEnabled("generateJavadoc"))
			buf.put(generateJavadoc(modelInterface));
		buf.put(generateInterfaceDeclaration(modelInterface));
		buf.put(generateInterfaceFields(modelInterface));
		buf.put(generateInterfaceMethods(modelInterface));
		buf.put("}");
		buf.put(NL);
		generateFile(buf.get());
	}

	/*
	 * Class generation ----------------
	 */

	public void generateClasses(Collection<ModelClass> modelClasses) throws Exception {
		generateClasses(modelClasses, false);
	}

	public void generateTests(Collection<ModelClass> testClasses) throws Exception {
		generateClasses(testClasses, true);
	}

	public void generateClasses(Collection<ModelClass> modelClasses, boolean isTest) throws Exception {
		Iterator<ModelClass> iterator = modelClasses.iterator();
		while (iterator.hasNext()) {
			ModelClass modelClass = iterator.next();
			generateClass(modelClass, isTest);
		}
	}

	public void generateClass(ModelClass modelClass) throws Exception {
		generateClass(modelClass, false);
	}

	public void generateTest(ModelClass modelClass) throws Exception {
		generateClass(modelClass, true);
	}

	public void generateClass(ModelClass modelClass, boolean isTest) throws Exception {
		String properClassName = NameUtil.getCompilationUnitName(modelClass.getClassName());
		String modulePackagePath = GenerateUtil.convertPackageToPath(modelClass.getPackageName());
		modulePackagePath = modulePackagePath.replace("-", "_");
		String projectName = context.getProject().getName();
		String targetParent = !isTest ? "main" : "test";

		setTargetFile(properClassName + ".java");
		setTargetFolder("src/" + targetParent + "/java" + modulePackagePath);
		setTargetPath(context.getTargetPath(projectName) + "/" + targetFolder);

		Buf buf = new Buf();
		buf.put(generatePackageDeclaration(modelClass));
		buf.put(generateStaticImportedPackages(modelClass));
		buf.put(generateImportedPackages(modelClass));
		buf.putLine();

		// context.setProperty("generateJavadocXmlFragment");
		if (context.isEnabled("generateJavadoc"))
			buf.put(generateJavadoc(modelClass));
		buf.put(generateClassDeclaration(modelClass));
		// context.unsetProperty("generateJavadocXmlFragment");

		boolean isEnum = modelClass instanceof ModelEnum;
		if (isEnum) {
			ModelEnum modelEnum = (ModelEnum) modelClass;
			buf.put(generateEnumLiterals(modelEnum));
		}

		buf.put(generateStaticFields(modelClass));
		buf.put(generateStaticMethods(modelClass));
		buf.put(generateInstanceFields(modelClass));
		buf.put(generateConstructors(modelClass));
		buf.put(generateAccessorMethods(modelClass));
		buf.put(generateInstanceMethods(modelClass));
		// buf.put(generateHashCodeMethod(modelClass));
		// buf.put(generateEqualsMethod(modelClass));
		buf.put("}");
		buf.put(NL);
		generateFile(buf.get());

		List<ModelClass> supportingClasses = modelClass.getSupportingClasses();
		Iterator<ModelClass> iterator = supportingClasses.iterator();
		while (iterator.hasNext()) {
			ModelClass supportingClass = iterator.next();
			generateClass(supportingClass, isTest);
		}
	}

	public void generateEnums(Collection<ModelEnum> modelEnums) throws Exception {
		Iterator<ModelEnum> iterator = modelEnums.iterator();
		while (iterator.hasNext()) {
			ModelEnum modelEnum = iterator.next();
			generateEnum(modelEnum);
		}
	}

	public void generateEnum(ModelEnum modelEnum) throws Exception {
		String modulePackagePath = GenerateUtil.convertPackageToPath(modelEnum.getPackageName());
		modulePackagePath = modulePackagePath.replace("-", "_");
		String projectName = context.getProject().getName();
		String targetParent = "main";

		setTargetFile(modelEnum.getClassName() + ".java");
		setTargetFolder("src/" + targetParent + "/java" + modulePackagePath);
		setTargetPath(context.getTargetPath(projectName) + "/" + targetFolder);

		Buf buf = new Buf();
		buf.put(generatePackageDeclaration(modelEnum));
		buf.put(generateImportedPackages(modelEnum));

		// context.setProperty("generateJavadocXmlFragment");
		if (context.isEnabled("generateJavadoc"))
			buf.put(generateJavadoc(modelEnum));
		buf.put(generateClassDeclaration(modelEnum));
		// context.unsetProperty("generateJavadocXmlFragment");

		buf.put(generateEnumLiterals(modelEnum));
		buf.put(generateStaticFields(modelEnum));
		buf.put(generateStaticMethods(modelEnum));
		buf.put(generateInstanceFields(modelEnum));
		buf.put(generateConstructors(modelEnum));
		buf.put(generateAccessorMethods(modelEnum));
		buf.put(generateInstanceMethods(modelEnum));
		buf.put("}");
		buf.put(NL);
		generateFile(buf.get());
	}

	protected String generateEnumLiterals(ModelEnum modelEnum) throws Exception {
		Buf buf = new Buf();
		buf.put1(NL);
		Set<ModelLiteral> literals = modelEnum.getLiterals();
		Iterator<ModelLiteral> iterator = literals.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			ModelLiteral modelLiteral = iterator.next();
			String name = modelLiteral.getName();
			String text = modelLiteral.getText();

			if (!StringUtils.isEmpty(text)) {
				buf.put(generateFieldAnnotations(modelLiteral));
				buf.put1(name + "(\"" + text + "\")");
			} else {
				buf.put1(name);
			}

			if (i + 1 < literals.size())
				buf.put(",");
			else
				buf.put(";");
			buf.put(NL);
			buf.put1(NL);
		}
		return buf.get();
	}

	public String toGetMethod(ModelField modelField) {
		return "get" + modelField.getCappedName();
	}

	// /*
	// * Class initialization
	// * --------------------
	// */
	//
	// public void initializeClass(ModelClass modelClass) throws Exception {
	// //createClassAnnotations(modelClass);
	// initializeStaticFields(modelClass);
	// initializeStaticMethods(modelClass);
	// initializeInstanceFields(modelClass);
	// initializeInstanceMethods(modelClass);
	// initializeClassAnnotations(modelClass);
	// initializeClassConstructors(modelClass);
	// }
	//
	// // protected void createClassAnnotations(ModelClass modelClass) {
	// // }
	//
	// protected void initializeClassAnnotations(ModelClass modelClass) throws
	// Exception {
	// List<ModelAnnotation> classAnnotations =
	// AnnotationUtil.createModelAnnotations(context.getElement().getAnnotations());
	// modelClass.setClassAnnotations(classAnnotations);
	// }
	//
	// protected void initializeClassConstructors(ModelClass modelClass) throws
	// Exception {
	// ModelConstructor constructor = new ModelConstructor();
	// constructor.setModifiers(Modifier.PUBLIC);
	// modelClass.addConstructor(constructor);
	// }
	//
	// protected void initializeStaticFields(ModelClass modelClass) {
	// modelClass.getImplementedInterfaces().contains("java.io.Serializable");
	// ModelAttribute staticAttribute = new ModelAttribute();
	// staticAttribute.setModifiers(Modifier.PUBLIC+Modifier.FINAL+Modifier.STATIC);
	// staticAttribute.setClassName("long");
	// staticAttribute.setName("serialVersionUID");
	// staticAttribute.setValue(1L);
	// modelClass.addStaticAttribute(staticAttribute );
	// }
	//
	// protected void initializeStaticMethods(ModelClass modelClass) {
	// }
	//
	// protected void initializeInstanceFields(ModelClass modelClass) throws
	// Exception {
	// List<Field> fields = ElementUtil.getFields(context.getElement());
	// Iterator<Field> iterator = fields.iterator();
	// while (iterator.hasNext()) {
	// Field field = iterator.next();
	// if (field instanceof Attribute)
	// initializeInstanceField(modelClass, (Attribute) field);
	// else if (field instanceof Reference)
	// initializeInstanceField(modelClass, (Reference) field);
	// }
	// }
	//
	// protected void initializeInstanceField(ModelClass modelClass, Attribute
	// attribute) throws Exception {
	// ModelAttribute modelAttribute = new ModelAttribute();
	// modelAttribute.setKeyEnabled(attribute.getUnique());
	// modelAttribute.setModifiers(Modifier.PRIVATE);
	// initializeInstanceFieldName(modelClass, modelAttribute, attribute);
	// initializeInstanceFieldType(modelClass, modelAttribute, attribute);
	// createFieldAnnotations(modelAttribute, attribute);
	// initializeFieldAnnotations(modelClass, modelAttribute, attribute);
	// initializeImportedClasses(modelClass, modelAttribute);
	// modelClass.addInstanceAttribute(modelAttribute);
	// if (!CodeGenUtil.isJavaDefaultType(attribute.getType()))
	// modelClass.addImportedClass(modelAttribute.getPackageName()+"."+modelAttribute.getClassName());
	// if (attribute.getType().toLowerCase().equals("division"))
	// System.out.println();
	// }
	//
	// protected void initializeInstanceField(ModelClass modelClass, Reference
	// reference) throws Exception {
	// ModelReference modelReference = new ModelReference();
	// modelReference.setKeyEnabled(reference.getUnique());
	// modelReference.setModifiers(Modifier.PRIVATE);
	// initializeInstanceFieldName(modelClass, modelReference, reference);
	// initializeInstanceFieldType(modelClass, modelReference, reference);
	// createFieldAnnotations(modelReference, reference);
	// initializeFieldAnnotations(modelClass, modelReference, reference);
	// initializeImportedClasses(modelClass, modelReference);
	// modelClass.addInstanceReference(modelReference);
	// if (!CodeGenUtil.isJavaDefaultType(reference.getType()))
	// modelClass.addImportedClass(modelReference.getPackageName()+"."+modelReference.getClassName());
	// }
	//
	// protected void initializeInstanceFieldName(ModelClass modelClass,
	// ModelField modelField, Field field) throws Exception {
	// modelField.setName(field.getName());
	// }
	//
	// protected void initializeInstanceFieldType(ModelClass modelClass,
	// ModelField modelField, Field field) throws Exception {
	// initializeInstanceFieldType(modelClass, modelField, field.getType());
	// }
	//
	// protected void initializeInstanceFieldType(ModelClass modelClass,
	// ModelField modelField, String fieldType) throws Exception {
	// String javaClass = ClassUtil.convertTypeToJavaClass(fieldType);
	// modelField.setPackageName(NameUtil.getPackageName(javaClass));
	// modelField.setClassName(NameUtil.getClassName(javaClass));
	// }
	//
	// protected void initializeImportedClasses(ModelClass modelClass,
	// ModelField modelField) throws Exception {
	// String packageName = modelField.getPackageName();
	// if (StringUtils.isEmpty(packageName))
	// return;
	// if (packageName.startsWith(context.getModule().getGroupId()))
	// return;
	// if (!packageName.equals("java.lang")) {
	// String className = modelField.getClassName();
	// modelClass.addImportedClass(packageName+"."+className);
	// }
	// }
	//
	// protected void initializeInstanceMethods(ModelClass modelClass) throws
	// Exception {
	// //nothing by default
	// }
	//
	// protected void createFieldAnnotations(ModelField modelField, Field field)
	// {
	// List<ModelAnnotation> fieldAnnotations =
	// AnnotationUtil.createModelAnnotations(field.getAnnotations());
	// modelField.setAnnotations(fieldAnnotations);
	// }
	//
	// protected void initializeFieldAnnotations(ModelClass modelClass,
	// ModelAttribute modelAttribute, Attribute attribute) throws Exception {
	// //nothing by default
	// }
	//
	// protected void initializeFieldAnnotations(ModelClass modelClass,
	// ModelReference modelReference, Reference reference) throws Exception {
	// //nothing by default
	// }

	/*
	 * Package name generation -----------------------
	 */

	public String generatePackageDeclaration(ModelClass modelClass) throws Exception {
		return generatePackageDeclaration(modelClass.getPackageName());
	}

	public String generatePackageDeclaration(ModelInterface modelInterface) throws Exception {
		return generatePackageDeclaration(modelInterface.getPackageName());
	}

	public String generatePackageDeclaration(String packageName) throws Exception {
		Buf buf = new Buf();
		if (packageName.length() > 0) {
			buf.put("package");
			buf.put(SP);
			buf.put(packageName);
			buf.put(SEMICOLON);
			buf.put(NL);
			return buf.get();
		}
		return "";
	}

	/*
	 * Import list generation ----------------------
	 */

	public String generateImportedPackages(ModelClass modelClass) throws Exception {
		return generateImportedPackages(modelClass.getPackageName(), modelClass.getImportedClasses());
	}

	public String generateImportedPackages(ModelInterface modelInterface) throws Exception {
		return generateImportedPackages(modelInterface.getPackageName(), modelInterface.getImportedClasses());
	}

	public String generateStaticImportedPackages(ModelClass modelClass) throws Exception {
		return generateImportedPackages(modelClass.getPackageName(), modelClass.getStaticImportedClasses(), true);
	}

	public String generateStaticImportedPackages(ModelInterface modelInterface) throws Exception {
		return generateImportedPackages(modelInterface.getPackageName(), modelInterface.getStaticImportedClasses(), true);
	}

	public String generateImportedPackages(String packageName, Collection<String> importedClasses) throws Exception {
		return generateImportedPackages(packageName, importedClasses, false);
	}

	public String generateImportedPackages(String packageName, Collection<String> importedClasses, boolean isStatic) throws Exception {
		Buf buf = new Buf();
		String lastPrefix = null;
		Iterator<String> iterator = importedClasses.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			String fullyQualifiedClassName = iterator.next();
			String parentPackage = NameUtil.getParentPath(fullyQualifiedClassName, ".");
			if (parentPackage.equals(packageName))
				continue;

			String prefix = NameUtil.getQualifiedContextNamePrefix(fullyQualifiedClassName);
			if (!prefix.equals(lastPrefix))
				buf.put(NL);
			lastPrefix = prefix;
			buf.put("import");
			buf.put(SP);
			if (isStatic) {
				buf.put("static");
				buf.put(SP);
			}
			buf.put(fullyQualifiedClassName);
			buf.put(SEMICOLON);
			buf.put(NL);
		}
		if (!isStatic)
			buf.put(NL);
		return buf.get();
	}

	/*
	 * Interface Javadoc generation ----------------------------
	 */

	protected String generateJavadoc(ModelInterface modelInterface) {
		String className = modelInterface.getClassName();
		String typeName = modelInterface.getType();

		Buf buf = new Buf();
		buf.putLine("/**");

		if (!context.isEnabled("generateJavadocXmlFragment") || StringUtils.isEmpty(typeName)) {
			buf.putLine(" *<p>");
			buf.putLine(" * Java interface class for <code>" + className + "</code>.");
			String javadocContent = generateJavadocContent(modelInterface);
			if (javadocContent != null) {
				buf.putLine(" *");
				buf.put(javadocContent);
				buf.putLine(" *");
			}

		} else {
			buf.putLine(" *<p>");
			buf.putLine(" * Java interface class for <code>" + className + "</code> complex type.");
			buf.putLine(" *");
			buf.putLine(" * The class represents <code>" + typeName + "</code> global type.");
			buf.putLine(" *");
			String javadocContent = generateJavadocContent(modelInterface);
			if (javadocContent != null) {
				buf.put(javadocContent);
				buf.putLine(" *");
			}

			buf.putLine(" * The following schema fragment specifies the expected content contained within this class.");
			buf.putLine(" *");

			buf.putLine(" * <pre>");
			Iterator<ModelOperation> iterator = modelInterface.getInstanceOperations().iterator();
			while (iterator.hasNext()) {
				ModelOperation modelOperation = iterator.next();
				// TODO something here...
			}
			buf.putLine(" * </pre>");
		}

		String author = context.getAuthor();
		if (author != null) {
			buf.putLine(" *");
			buf.putLine(" * @author " + author);
		}

		String version = modelInterface.getVersion();
		if (version != null) {
			buf.putLine(" *");
			buf.putLine(" * @version " + version);
		}

		buf.putLine(" * </p>");
		buf.putLine(" */");
		return buf.get();
	}


	/*
	 * Class Javadoc generation 
	 * ------------------------
	 */
	
	protected String generateJavadoc(ModelClass modelClass) {
		String typeName = modelClass.getType();
		boolean generateJavadocXmlFragment = !context.isEnabled("generateJavadocXmlFragment") || StringUtils.isEmpty(typeName);
		return generateJavadoc(modelClass, generateJavadocXmlFragment);
	}

	protected String generateJavadoc(ModelClass modelClass, boolean generateJavadocXmlFragment) {
		String className = modelClass.getClassName();
		String typeName = modelClass.getType();

		Buf buf = new Buf();
		buf.putLine("/**");
		
		List<String> javadoc = modelClass.getJavadoc();
		if (!context.isEnabled("generateJavadocXmlFragment") && javadoc != null && !javadoc.isEmpty()) {
			String javadocContent = generateJavadocContent(modelClass);
			if (javadocContent != null) {
				buf.put(javadocContent);
			}
			
		} else if (!context.isEnabled("generateJavadocXmlFragment") || StringUtils.isEmpty(typeName)) {
			buf.putLine(" * <p>");
			buf.putLine(" * Java implementation class for <code>" + className + "</code>.");
			String javadocContent = generateJavadocContent(modelClass);
			if (javadocContent != null) {
				buf.putLine(" *");
				buf.put(javadocContent);
			}
			buf.putLine(" * </p>");

		} else {
			buf.putLine(" * <p>");
			buf.putLine(" * Java implementation class for <code>" + className + "</code> complex type.");
			buf.putLine(" *");
			buf.putLine(" * The class represents <code>" + typeName + "</code> global type.");
			buf.putLine(" *");
			String javadocContent = generateJavadocContent(modelClass);
			if (javadocContent != null) {
				buf.put(javadocContent);
				buf.putLine(" *");
			}

			buf.putLine(" * The following schema fragment specifies the expected content contained within this class.");
			buf.putLine(" *");

			buf.putLine(" * <pre>");
			buf.putLine(" * &lt;complexType name=\"" + className + "\">");
			buf.putLine(" *   &lt;complexContent>");
			buf.putLine(" *     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">");
			buf.putLine(" *       &lt;sequence>");

			Iterator<ModelField> iterator = modelClass.getInstanceFields().iterator();
			while (iterator.hasNext()) {
				ModelField modelField = iterator.next();
				if (modelField.isRequired())
					buf.putLine(" *         &lt;element name=\"" + modelField.getName() + "\" type=\"" + modelField.getType() + "\" />");
				else
					buf.putLine(" *         &lt;element name=\"" + modelField.getName() + "\" type=\"" + modelField.getType() + "\" minOccurs=\"0\" />");
			}
			buf.putLine(" *       &lt;/sequence>");
			buf.putLine(" *     &lt;/restriction>");
			buf.putLine(" *   &lt;/complexContent>");
			buf.putLine(" * &lt;/complexType>");
			buf.putLine(" * </pre>");
			buf.putLine(" * </p>");
		}

		String author = context.getAuthor();
		if (author != null && !author.equals("generated")) {
			buf.putLine(" *");
			buf.putLine(" * @author " + author);
		}

		String version = modelClass.getVersion();
		if (version != null) {
			buf.putLine(" *");
			buf.putLine(" * @version " + version);
		}

		buf.putLine(" */");
		return buf.get();
	}

//	protected String generateJavadocContent(ModelClass modelClass) {
//		return modelClass.getJavadoc();
//	}

	public String generateJavadoc(ModelUnit modelUnit, ModelOperation modelOperation) {
		Buf buf = new Buf();
		buf.putLine1("/**");
		List<String> javadoc = modelOperation.getJavadoc();
		if (javadoc == null || javadoc.isEmpty()) {
			buf.putLine1(" * Constructs an instance of <code>" + modelUnit.getClassName() + "</code>.");
		} else {
			Iterator<String> iterator = javadoc.iterator();
			while (iterator.hasNext()) {
				String text = iterator.next();
				buf.putLine1(" * " + text);
			}
		}
		buf.putLine1(" */");
		return buf.get();
	}
	
	public String generateJavadoc(ModelUnit modelUnit) {
		Buf buf = new Buf();
		buf.putLine1("/**");
		buf.put(generateJavadocContent(modelUnit));
		buf.putLine1(" */");
		return buf.get();
	}
	
	public String generateJavadocContent(ModelUnit modelUnit) {
		Buf buf = new Buf();
		List<String> javadoc = modelUnit.getJavadoc();
		if (javadoc != null && !javadoc.isEmpty()) {
			Iterator<String> iterator = javadoc.iterator();
			while (iterator.hasNext()) {
				String text = iterator.next();
				buf.putLine(" * " + text);
			}
		}
		return buf.get();
	}

	protected String getFieldNameJavadoc(ModelField modelField) {
		return getNameJavadoc(modelField, modelField.getName());
	}
	
	protected String getClassNameJavadoc(ModelField modelField) {
		return getNameJavadoc(modelField, modelField.getClassName());
	}
	
	protected String getNameJavadoc(ModelField modelField, String name) {
		String cappedName = NameUtil.capName(name);
		String fieldNameJavadoc = null;
		if (modelField instanceof ModelAttribute)
			fieldNameJavadoc = "'<em>" + cappedName + "</em>'";
		else if (modelField instanceof ModelReference)
			fieldNameJavadoc = "'{@link " + cappedName + "}'";
		return fieldNameJavadoc;
	}

	
	/*
	 * Class declaration generation 
	 * ----------------------------
	 */

	public String generateClassDeclaration(ModelClass modelClass) throws Exception {
		Buf buf = new Buf();
		buf.put(generateClassAnnotations(modelClass));
		buf.put(generateClassSignature(modelClass));
		return buf.get();
	}

	public String generateClassSignature(ModelClass modelClass) throws Exception {
		boolean isEnum = modelClass instanceof ModelEnum;
		String declarationType = isEnum ? "enum" : "class";

		Buf buf = new Buf();
		buf.put("public");
		if (modelClass.isAbstract()) {
			buf.put(SP);
			buf.put("abstract");
		}
		buf.put(SP);
		buf.put(declarationType);
		buf.put(SP);
		buf.put(modelClass.getClassName());
		String superClassName = modelClass.getParentClassName();
		if (superClassName != null) {
			buf.put(SP);
			buf.put("extends");
			buf.put(SP);
			buf.put(NameUtil.getClassName(superClassName));
		}
		buf.put(SP);
		// add implemented interfaces
		Set<String> interfaces = modelClass.getImplementedInterfaces();
		if (interfaces.size() > 0) {
			buf.put("implements");
			buf.put(SP);
			Iterator<String> iterator = interfaces.iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				String interfaceName = iterator.next();
				buf.put(interfaceName);
				//buf.put(NameUtil.getClassName(interfaceName));
				if (i + 1 < interfaces.size())
					buf.put(",");
				buf.put(SP);
			}
		}
		buf.put("{");
		buf.put(NL);
		return buf.get();
	}

	/*
	 * Interface declaration generation --------------------------------
	 */

	public String generateInterfaceDeclaration(ModelInterface modelInterface) throws Exception {
		Buf buf = new Buf();
		buf.putLine();
		buf.put(generateInterfaceAnnotations(modelInterface));
		buf.put(generateInterfaceName(modelInterface));
		return buf.get();
	}

	public String generateInterfaceName(ModelInterface modelInterface) throws Exception {
		Buf buf = new Buf();
		buf.put("public interface ");
		buf.put(modelInterface.getClassName());
		buf.put(SP);
		List<String> extendedInterfaces = modelInterface.getExtendedInterfaces();
		if (extendedInterfaces.size() > 0) {
			Iterator<String> iterator = extendedInterfaces.iterator();
			buf.put("extends ");
			while (iterator.hasNext()) {
				String name = iterator.next();
				buf.put(NameUtil.getClassName(name));
				buf.put(SP);
			}
		}
		buf.put("{");
		buf.put(NL);
		return buf.get();
	}

	/*
	 * Field generation ----------------
	 */

	public String generateStaticFields(ModelClass modelClass) throws Exception {
		Set<ModelField> modelFields = modelClass.getStaticFields();
		return generateFields(modelFields);
	}

	public String generateInstanceFields(ModelClass modelClass) throws Exception {
		Set<ModelField> modelFields = modelClass.getInstanceFields();
		return generateFields(modelFields);
	}

	// public String generateInstanceFields(ModelEnum modelEnum) throws
	// Exception {
	// Set<ModelLiteral> modelLiterals = modelEnum.getLiterals();
	// return generateFields(modelLiterals);
	// }

	public String generateInterfaceFields(ModelInterface modelInterface) throws Exception {
		Set<? extends ModelField> modelFields = modelInterface.getInstanceAttributes();
		return generateFields(modelFields);
	}

	public String generateFields(Set<? extends ModelField> modelFields) throws Exception {
		Buf buf = new Buf();
		if (modelFields.size() > 0) {
			Iterator<? extends ModelField> iterator = modelFields.iterator();
			while (iterator.hasNext()) {
				ModelField modelField = iterator.next();
				String text = generateField(modelField);
				buf.put(text);
			}
			// TODO make this extra NL configurable
			// if (modelFields.size() > 0)
			// buf.put(NL);
		}
		return buf.get();
	}

	public String generateField(ModelField modelField) throws Exception {
		Buf buf = new Buf();
		buf.put1(NL);
		buf.put(generateFieldAnnotations(modelField));
		buf.put(generateModifiers(modelField));
		buf.put(SP);
		buf.put(getFieldType(modelField));
		buf.put(SP);
		buf.put(getFieldName(modelField));
		
//		if (modelField.getValue() == null) {
//			//String fieldName = getFieldName(modelField);
//			String fieldType = getFieldType(modelField);
//			if (modelField.getStructure().equals("list"))
//				modelField.setValue("new Array" + fieldType + "()");
//			if (modelField.getStructure().equals("set"))
//				modelField.setValue("new Hash" + fieldType + "()");
//			if (modelField.getStructure().equals("map"))
//				modelField.setValue("new Hash" + fieldType + "()");
//		}
		
		Object defaultValue = modelField.getDefault();
		if (defaultValue != null) {
			buf.put(SP);
			buf.put("=");
			buf.put(SP);
			Object value = defaultValue;
			String type = modelField.getType();
			if (type == null)
				type = modelField.getClassName();
			if (context.getEnumerationByType(type) != null) {
				buf.put(modelField.getClassName() + "." + (String) defaultValue);
			} else if (value instanceof String) {
				buf.put((String) defaultValue);
			} else if (value instanceof Long) {
				buf.put(defaultValue.toString() + "L");
			} else {
				buf.put(defaultValue.toString());
			}
		}
		buf.put(SEMICOLON);
		buf.put(NL);
		return buf.get();
	}

	public String generateStaticMethods(ModelClass modelClass) throws Exception {
		String methods = generateInstanceMethods(modelClass, modelClass.getStaticOperations(), false);
		return methods;
	}

	public String generateInterfaceMethods(ModelInterface modelInterface) throws Exception {
		String methods = generateInstanceMethods(modelInterface, modelInterface.getInstanceOperations(), true);
		return methods;
	}

	public String generateInstanceMethods(ModelClass modelClass) throws Exception {
		String methods = generateInstanceMethods(modelClass, modelClass.getInstanceOperations(), false);
		return methods;
	}

	public String generateInstanceMethods(ModelUnit modelUnit, Collection<ModelOperation> modelOperations, boolean interfaceOnly) throws Exception {
		Buf buf = new Buf();
		if (modelOperations.size() > 0)
			buf.put1(NL);
		Iterator<ModelOperation> iterator = modelOperations.iterator();
		while (iterator.hasNext()) {
			ModelOperation modelOperation = iterator.next();
			buf.put(generateInstanceMethod(modelUnit, modelOperation, interfaceOnly));
		}
		return buf.get();
	}

	public String generateInstanceMethod(ModelUnit modelUnit, ModelOperation modelOperation, boolean interfaceOnly) throws Exception {
		Buf buf = new Buf();
		if (context.isEnabled("generateJavadoc"))
			buf.put(generateJavadoc(modelUnit, modelOperation));
		// if (!interfaceOnly)
		buf.put(generateOperationAnnotations(modelOperation));
		buf.put1(getVisibilityModifier(modelOperation));
		buf.put(SP);
		if (Modifier.isStatic(modelOperation.getModifiers())) {
			buf.put("static");
			buf.put(SP);
		}
		String returnType = modelOperation.getResultType();
		if (returnType == null)
			returnType = "void";
		buf.put(NameUtil.getClassName(returnType));
		buf.put(SP);
		buf.put(TokenUtil.getSimpleName(modelOperation.getName()));
		List<ModelParameter> parameters = modelOperation.getParameters();
		// List<String> parameterTypes = modelOperation.getParameterTypes();
		// List<String> parameterNames = modelOperation.getParameterNames();
		if (parameters.size() > 0) {
			buf.put("(");
			for (int i = 0; i < parameters.size(); i++) {
				ModelParameter modelParameter = parameters.get(i);
				String construct = modelParameter.getConstruct();
				String className = modelParameter.getClassName();

				String typeName = className;
				Iterator<ModelAnnotation> iterator = modelParameter.getAnnotations().iterator();
				while (iterator.hasNext()) {
					ModelAnnotation annotation = iterator.next();
					buf.put(generateAnnotation(annotation, false));
					buf.put(" ");
				}
				if (modelParameter.isFinal())
					buf.put("final ");
				if (construct != null) {
					if (construct.equalsIgnoreCase("list"))
						typeName = "List<" + className + ">";
					else if (construct.equalsIgnoreCase("set"))
						typeName = "Set<" + className + ">";
					else if (construct.equalsIgnoreCase("map"))
						typeName = "Map<" + modelParameter.getKeyClassName() + ", " + className + ">";
					else if (construct.equalsIgnoreCase("collection"))
						typeName = "Collection<" + className + ">";
				}
				if (i > 0)
					buf.put("," + SP);
				buf.put(typeName);
				buf.put(SP);
				buf.put(modelParameter.getName());
			}
			buf.put(")");
		} else
			buf.put("()");

		if (modelOperation.getExceptions().size() > 0) {
			List<String> exceptions = modelOperation.getExceptions();
			Iterator<String> exceptionIterator = exceptions.iterator();
			buf.put(SP);
			buf.put("throws");
			for (int i = 0; exceptionIterator.hasNext(); i++) {
				String exception = exceptionIterator.next();
				if (i > 0)
					buf.put("," + SP);
				else
					buf.put(SP);
				buf.put(exception);
			}
		}

		if (interfaceOnly) {
			buf.put(SEMICOLON);
		} else {
			buf.put(SP);
			buf.put("{");
			buf.put(NL);
			buf.put(generateSource(modelOperation));
			buf.put1("}");
		}
		buf.put(NL);
		buf.put1(NL);
		return buf.get();
	}

	/*
	 * Annotation generation ---------------------
	 */

	public String generateClassAnnotations(ModelClass modelClass) throws Exception {
		return generateAnnotations(modelClass.getClassAnnotations());
	}

	public String generateInterfaceAnnotations(ModelInterface modelInterface) throws Exception {
		return generateAnnotations(modelInterface.getInterfaceAnnotations());
	}

	public String generateFieldAnnotations(ModelField modelField) throws Exception {
		return generateAnnotations(modelField.getAnnotations(), 1);
	}

	public String generateFieldAnnotations(ModelLiteral modelLiteral) throws Exception {
		return generateAnnotations(modelLiteral.getAnnotations(), 1);
	}

	public String generateOperationAnnotations(ModelOperation modelOperation) throws Exception {
		if (modelOperation == null)
			System.out.println();
		return generateAnnotations(modelOperation.getAnnotations(), 1);
	}

	public String generateAnnotations(List<ModelAnnotation> annotations) throws Exception {
		return generateAnnotations(annotations, 0);
	}

	public String generateAnnotations(List<ModelAnnotation> annotations, int indent) throws Exception {
		Buf buf = new Buf();
		Iterator<ModelAnnotation> iterator = annotations.iterator();
		while (iterator.hasNext()) {
			ModelAnnotation modelAnnotation = iterator.next();
			for (int i = 0; i < indent; i++)
				buf.put(TAB);
			buf.put(generateAnnotation(modelAnnotation));
		}
		return buf.get();
	}

	public String generateAnnotation(ModelAnnotation annotation) throws Exception {
		return generateAnnotation(annotation, true);
	}
	
	public String generateAnnotation(ModelAnnotation annotation, boolean insertLineBreaks) throws Exception {
		String label = annotation.getLabel();
		Buf buf = new Buf();
		if (!label.startsWith("//"))
			buf.put("@");
		buf.put(label);
		Map<Object, Object> parts = annotation.getParts();
		if (parts.size() > 0) {
			buf.put("(");
			Iterator<Object> keys = parts.keySet().iterator();
			for (int i = 0; keys.hasNext(); i++) {
				if (i > 0) {
					buf.put(", ");
					if (annotation.isFormatted()) {
						if (insertLineBreaks) {
							buf.putLine("");
							buf.put3("");
						} else buf.put(" ");
					}
				}
				Object key = keys.next();
				if (key instanceof String) {
					buf.put(key.toString());
					Object value = parts.get(key);
					if (value != null && value instanceof String) {
						String valueString = (String) value;
						if (!StringUtils.isEmpty(valueString)) {
							buf.put(" = ");
							// buf.put("\"");
							buf.put(valueString);
							// buf.put("\"");
						}
					} else if (value != null) {
						buf.put(" = ");
						buf.put(value.toString());
					}
				} else {
					buf.put(key.toString());
				}
			}
			buf.put(")");
		}
		if (insertLineBreaks)
			buf.put(NL);
		return buf.get();
	}

	public String generateModifiers(ModelField modelField) throws Exception {
		Buf buf = new Buf();
		int modifiers = modelField.getModifiers();
		buf.put1(getVisibilityModifier(modifiers));
		if (Modifier.isStatic(modifiers))
			buf.put(SP + "static");
		if (Modifier.isFinal(modifiers))
			buf.put(SP + "final");
		if (Modifier.isAbstract(modifiers))
			buf.put(SP + "abstract");
		return buf.get();
	}

	/*
	 * Constructor generation ----------------------
	 */

	public String generateConstructors(ModelClass modelClass) throws Exception {
		Buf buf = new Buf();
		// TODO make this extra NL configurable
		if (modelClass.getStaticFields().size() > 0 || modelClass.getInstanceFields().size() > 0)
			buf.put1(NL);
		Set<ModelConstructor> constructors = modelClass.getInstanceConstructors();
		if (constructors.size() > 0) {
			buf.put1(NL);
			Iterator<ModelConstructor> iterator = constructors.iterator();
			while (iterator.hasNext()) {
				ModelConstructor constructor = iterator.next();
				buf.put(generateConstructor(modelClass, constructor));
				buf.put1(NL);
			}
		}
		return buf.get();
	}

	public String generateConstructor(ModelClass modelClass, ModelConstructor modelConstructor) throws Exception {
		Buf buf = new Buf();
		// buf.put(NL);
		if (context.isEnabled("generateJavadoc"))
			buf.put(generateJavadoc(modelClass, modelConstructor));
		buf.put(TAB);
		int modifiers = modelConstructor.getModifiers();
		if (modifiers > 0) {
			buf.put(getConstructorModifiers(modifiers));
			buf.put(SP);
		}
		
		String className = modelClass.getClassName();
		int indexOfGeneric = className.indexOf("<");
		if (indexOfGeneric != -1)
			className = className.substring(0, indexOfGeneric);
		buf.put(TokenUtil.getSimpleName(className));
		
		List<ModelParameter> parameters = modelConstructor.getParameters();
		if (parameters.size() > 0) {
			buf.put("(");
			for (int i = 0; i < parameters.size(); i++) {
				ModelParameter modelParameter = parameters.get(i);
				if (i > 0)
					buf.put(", ");
				if (modelParameter.isFinal())
					buf.put("final ");
				buf.put(modelParameter.getClassName());
				buf.put(SP);
				if (modelParameter.getName() != null)
					buf.put(modelParameter.getName());
				else
					buf.put("arg" + i);
			}
			buf.put(")");
		} else
			buf.put("()");
		
		if (modelConstructor.getExceptions().size() > 0) {
			List<String> exceptions = modelConstructor.getExceptions();
			Iterator<String> exceptionIterator = exceptions.iterator();
			buf.put(SP);
			buf.put("throws");
			for (int i = 0; exceptionIterator.hasNext(); i++) {
				String exception = exceptionIterator.next();
				if (i > 0)
					buf.put("," + SP);
				else
					buf.put(SP);
				buf.put(exception);
			}
		}
		
		buf.put(SP);
		buf.put("{");
		buf.put(NL);
		buf.put(generateSource(modelConstructor));
		buf.put1("}");
		buf.put(NL);
		return buf.get();
	}


	/*
	 * Source generation 
	 * -----------------
	 */

	public String generateSource(ModelOperation modelOperation) {
		Buf buf = new Buf();

		List<String> initialSource = modelOperation.getInitialSource();
		List<String> completionSource = modelOperation.getCompletionSource();

		if (initialSource.size() > 0) {
			Iterator<String> iterator = initialSource.iterator();
			while (iterator.hasNext()) {
				String sourceLine = iterator.next();
				buf.put(sourceLine);
			}
		}

		if (completionSource.size() > 0) {
			Iterator<String> iterator2 = completionSource.iterator();
			while (iterator2.hasNext()) {
				String sourceLine = iterator2.next();
				buf.put(sourceLine);
			}
		}

		if (buf.length() == 0)
			buf.putLine2("//nothing for now");
		return buf.get();
	}

	/*
	 * Accessor generation -------------------
	 */

	public String generateAccessorMethods(ModelClass modelClass) throws Exception {
		Buf buf = new Buf();
		buf.put(generateAccessorMethods(modelClass.getStaticFields()));
		buf.put(generateAccessorMethods(modelClass.getInstanceFields()));
		return buf.get();
	}

	public String generateAccessorGetMethods(ModelClass modelClass) throws Exception {
		Buf buf = new Buf();
		buf.put(generateAccessorGetMethods(modelClass.getStaticFields()));
		buf.put(generateAccessorGetMethods(modelClass.getInstanceFields()));
		return buf.get();
	}

	public String generateAccessorSetMethods(ModelClass modelClass) throws Exception {
		Buf buf = new Buf();
		buf.put(generateAccessorSetMethods(modelClass.getStaticFields()));
		buf.put(generateAccessorSetMethods(modelClass.getInstanceFields()));
		return buf.get();
	}

	public String generateAccessorMethods(Set<ModelField> instanceFields) throws Exception {
		Buf buf = new Buf();
		Iterator<ModelField> iterator = instanceFields.iterator();
		while (iterator.hasNext()) {
			ModelField modelField = iterator.next();
			String fieldType = ModelFieldUtil.getFieldType(modelField);
			
			if (fieldType.toLowerCase().equals("boolean") && !ModelFieldUtil.isCollection(modelField))
				buf.put(generateAccessorIsMethod(modelField));
			
			if (modelField.getGenerateGetter())
				buf.put(generateAccessorGetMethod(modelField));
			
			if (modelField.getGenerateSetter())
				buf.put(generateAccessorSetMethod(modelField));
			
			if (modelField.getGenerateUnsetMethod())
				buf.put(generateAccessorUnsetMethod(modelField));
			
			if (ModelFieldUtil.isList(modelField) || ModelFieldUtil.isSet(modelField)) {
				if (modelField.getGenerateAddMethod()) {
					buf.put(generateAccessorAddItemMethod(modelField));
					buf.put(generateAccessorAddItemsMethod(modelField));
				}
				if (modelField.getGenerateRemoveMethod()) {
					buf.put(generateAccessorRemoveItemMethod(modelField));
					buf.put(generateAccessorRemoveItemsMethod(modelField));
				}
				if (modelField.getGenerateClearMethod()) {
					buf.put(generateAccessorClearMethod(modelField));
				}
			}
			
			if (ModelFieldUtil.isMap(modelField)) {
				if (modelField.getGenerateAddMethod()) {
					buf.put(generateAccessorPutItemMethod(modelField));
					buf.put(generateAccessorPutItemsMethod(modelField));
				}
				if (modelField.getGenerateRemoveMethod()) {
					buf.put(generateAccessorRemoveItemMethod(modelField));
					buf.put(generateAccessorRemoveItemsMethod(modelField));
				}
				if (modelField.getGenerateClearMethod()) {
					buf.put(generateAccessorClearMethod(modelField));
				}
			}
		}
		return buf.get();
	}

	public String generateAccessorGetMethods(Set<ModelField> instanceFields) throws Exception {
		Buf buf = new Buf();
		Iterator<ModelField> iterator = instanceFields.iterator();
		while (iterator.hasNext()) {
			ModelField modelField = iterator.next();
			String fieldType = ModelFieldUtil.getFieldType(modelField);
			if (fieldType.toLowerCase().equals("boolean") && !ModelFieldUtil.isCollection(modelField))
				buf.put(generateAccessorIsMethod(modelField));
			buf.put(generateAccessorGetMethod(modelField));
		}
		return buf.get();
	}

	public String generateAccessorSetMethods(Set<ModelField> instanceFields) throws Exception {
		Buf buf = new Buf();
		Iterator<ModelField> iterator = instanceFields.iterator();
		while (iterator.hasNext()) {
			ModelField modelField = iterator.next();
			buf.put(generateAccessorSetMethod(modelField));
		}
		return buf.get();
	}

	public String generateAccessorIsMethod(ModelField modelField) throws Exception {
		Assert.isTrue(modelField.getClassName().toLowerCase().equals("boolean"), "Input field must be of type boolean");
		Buf buf = new Buf();
		if (modelField.getGenerateGetter()) {
			String getterMethod = generateGetterMethod(modelField, true);
			buf.put1(NL);
			buf.put(getterMethod);
			buf.put(NL);
		}
		return buf.get();
	}

	public String generateAccessorGetMethod(ModelField modelField) throws Exception {
		Buf buf = new Buf();
		if (modelField.getGenerateGetter()) {
			String getterMethod = generateGetterMethod(modelField, false);
			buf.put1(NL);
			buf.put(getterMethod);
			buf.put(NL);
		}
		return buf.get();
	}

	public String generateGetterMethod(ModelField modelField) throws Exception {
		return generateGetterMethod(modelField, true);
	}

	public String generateGetterMethod(ModelField modelField, boolean enforceBooleanVersion) throws Exception {
		Buf buf = new Buf();

		if (context.isEnabled("generateJavadoc")) {
			buf.putLine1("/**");
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			String classNameJavadoc = getClassNameJavadoc(modelField);
			buf.putLine1(" * Returns the current value of "+fieldNameJavadoc+" " + modelField.getRepresentation() + ".");
			if (modelField.getMultiplicity() == -1)
				buf.putLine1(" * The " + modelField.getStructure() + " contents are of type "+classNameJavadoc+".");
			buf.putLine1(" * @return The value of "+fieldNameJavadoc+" " + modelField.getRepresentation() + ".");
			buf.putLine1(" */");
		}

		int indent = 2;
		String fieldName = modelField.getName();
		String fieldType = getFieldType(modelField);
		buf.putLine1("public " + fieldType + " " + MethodUtil.toGetMethod(modelField, enforceBooleanVersion) + "() {");
		if (modelField.getMultiplicity() == -1) {
			if (modelField.isSynchronizationEnabled()) {
				buf.putLine2("synchronized (" + fieldName + ") {");
				indent++;
			}
			if (!modelField.isSynchronizationEnabled()) {
				buf.putLine(indent, "if (" + fieldName + " == null)");
				if (modelField.getStructure().equals("list"))
					buf.putLine(indent, "	"+fieldName + " = new Array" + fieldType + "();");
				if (modelField.getStructure().equals("set"))
					buf.putLine(indent, "	"+fieldName + " = new Hash" + fieldType + "();");
				if (modelField.getStructure().equals("map"))
					buf.putLine(indent, "	"+fieldName + " = new Hash" + fieldType + "();");
			}
			buf.putLine(indent, "return "+fieldName+";");
			if (modelField.isSynchronizationEnabled())
				buf.putLine2("}");
		} else {
			if (fieldType.equals("Boolean") && modelField.isSynchronizationEnabled())
				buf.putLine2("return "+fieldName+" != null && "+fieldName+";");
			else buf.putLine2("return "+fieldName+";");
		}
		buf.put1("}");
		return buf.get();
	}

	public String generateAccessorSetMethod(ModelField modelField) throws Exception {
		Buf buf = new Buf();
		if (modelField.getGenerateSetter()) {
			String setterMethod = generateSetterMethod(modelField);
			buf.put1(NL);
			buf.put(setterMethod);
			buf.put(NL);
		}
		return buf.get();
	}

	public String generateSetterMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		String className = modelField.getClassName();
		String fieldNameCapped = NameUtil.capName(fieldName);
		String fieldNameUncapped = NameUtil.uncapName(fieldName);
		//String classNameUncapped = NameUtil.uncapName(className);
		String fieldStructure = modelField.getStructure();
		//String paramName = ModelFieldUtil.getStructuredName(modelField);
		String paramName = fieldNameUncapped;
		
		//if (paramName.equals("bookOrdersEntity"))
		//	System.out.println();
		
		Buf buf = new Buf();
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			buf.put1(" * Allows assignment of the "+fieldNameJavadoc+" " + modelField.getRepresentation() + "." + NL);
			buf.put1(" * @param value The new value of the "+fieldNameJavadoc+" " + modelField.getRepresentation() + "." + NL);
			buf.put1(" */" + NL);
		}
		buf.put1("public");
		buf.put(SP);
		buf.put("void");
		buf.put(SP);
		buf.put(MethodUtil.toSetMethod(modelField));
		buf.put("(");
		if (modelField.getMultiplicity() == -1) {
			if (fieldStructure.equals("list")) {
				buf.put("Collection<"+className+">");
			} else if (fieldStructure.equals("set")) {
				buf.put("Collection<"+className+">");
			} else if (fieldStructure.equals("collection")) {
				buf.put("Collection<"+className+">");
			} else buf.put(getFieldType(modelField));
		} else buf.put(getFieldType(modelField));
		buf.put(SP);
		buf.put(paramName);
		buf.put(")");
		buf.put(SP);
		buf.put("{");
		buf.put(NL);
		
		if (modelField.getMultiplicity() == -1) {
			//buf.putLine2("if ("+paramName+" != null) {");
			int indent = 2;

			buf.putLine(indent, "if ("+paramName+" == null) {");
			buf.putLine(indent, "	this."+fieldName+" = null;");
			buf.putLine(indent, "} else {");

			if (modelField.isSynchronizationEnabled()) {
				buf.putLine(indent, "synchronized (this."+fieldName+") {");
				indent++;
			}
			
			if (fieldStructure.equals("list")) {
				buf.putLine(indent+1, "this."+fieldName+" = new ArrayList<"+className+">();");
			} else if (fieldStructure.equals("set")) {
				buf.putLine(indent+1, "this."+fieldName+" = new HashSet<"+className+">();");
			} else if (fieldStructure.equals("map")) {
				String keyClassName = modelField.getKeyClassName();
				buf.putLine(indent+1, "this."+fieldName+" = new HashMap<"+keyClassName+", "+className+">();");
			}
			
			if (modelField.getGenerateAddMethod())
				buf.putLine(indent+1, "addTo"+fieldNameCapped+"("+paramName+");");
			else {
				if (fieldStructure.equals("map"))
					buf.putLine(indent+1, "this."+fieldName+".putAll("+paramName+");");
				else buf.putLine(indent+1, "this."+fieldName+".addAll("+paramName+");");
			}
			
			buf.putLine(indent, "}");
			if (modelField.isSynchronizationEnabled())
				buf.putLine2("}");
			
		} else {
			int indent = 2;
			if (fieldName.equals(paramName))
				buf.putLine(indent, "this."+fieldName+" = "+paramName+";");
			else buf.putLine(indent, "this."+fieldName+" = "+paramName+";");
		}
		
		buf.put1("}");
		return buf.get();
	}

	public String generateAccessorUnsetMethod(ModelField modelField) throws Exception {
		Buf buf = new Buf();
		if (modelField.getGenerateUnsetMethod()) {
			String unsetMethod = generateUnsetMethod(modelField);
			buf.put1(NL);
			buf.put(unsetMethod);
			buf.put(NL);
		}
		return buf.get();
	}

	public String generateUnsetMethod(ModelField modelField) throws Exception {
		Buf buf = new Buf();
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			buf.put1(" * Allows clearing of the "+fieldNameJavadoc+" " + modelField.getRepresentation() + "." + NL);
			buf.put1(" */" + NL);
		}
		buf.put1("public");
		buf.put(SP);
		buf.put("void");
		buf.put(SP);
		buf.put(MethodUtil.toUnsetMethod(modelField));
		buf.put("(");
		buf.put(getFieldType(modelField));
		buf.put(SP);
		buf.put(getFieldName(modelField));
		buf.put(")");
		buf.put(SP);
		buf.put("{");
		buf.put(NL);
		buf.put2("this.");
		buf.put(getFieldName(modelField));
		buf.put(SP);
		buf.put("=");
		buf.put(SP);
		buf.put(getFieldName(modelField));
		buf.put(SEMICOLON);
		buf.put(NL);
		buf.put1("}");
		return buf.get();
	}

	public String generateAccessorAddItemMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		//String fieldType = getFieldType(modelField);
		String fieldStructure = modelField.getStructure();
		String fieldClassName = modelField.getClassName();
		String fieldKeyClassName = modelField.getKeyClassName();
		String paramName = ModelFieldUtil.getParameterName(modelField);
		
		//if (ModelFieldUtil.isCollection(modelField))
		//	fieldType = ModelFieldUtil.getFieldType(modelField, "collection");
		//if (ModelFieldUtil.isMap(modelField))
		//	fieldType = ModelFieldUtil.getFieldType(modelField, "map");
		String addMethod = MethodUtil.toAddMethod(modelField);
		// boolean isCollection = ModelFieldUtil.isCollection(modelField);

		Buf buf = new Buf();
		buf.putLine("");
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			//String cappedName = modelField.getCappedName();
			//String qualifiedName = modelField.getQualifiedName();
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			String representation = modelField.getRepresentation();
			buf.put1(" * Allows addition of a new element to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" * @param value The new value to add to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" */" + NL);
		}
		if (fieldStructure.equals("map")) {
			buf.putLine1("public void "+addMethod+"("+fieldKeyClassName+" key, "+fieldClassName+" "+paramName+") {");
			buf.putLine1("	if ("+paramName+" != null ) {");
			buf.putLine1("		synchronized (this."+fieldName+") {");
			buf.putLine1("			this."+fieldName+".put(key, "+paramName+");");
			buf.putLine1("		}");
			buf.putLine1("	}");
			buf.putLine1("}");
		} else {
			buf.putLine1("public void "+addMethod+"("+fieldClassName+" "+paramName+") {");
			buf.putLine1("	if ("+paramName+" != null ) {");
			buf.putLine1("		synchronized (this."+fieldName+") {");
			buf.putLine1("			this."+fieldName+".add("+paramName+");");
			buf.putLine1("		}");
			buf.putLine1("	}");
			buf.putLine1("}");
		}
		return buf.get();
	}

	public String generateAccessorAddItemsMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		String fieldType = getFieldType(modelField);
		//String fieldStructure = modelField.getStructure();
		//String fieldClassName = modelField.getClassName();
		//String classNameUncapped = NameUtil.uncapName(fieldClassName);
		String paramName = ModelFieldUtil.getParameterName(modelField) + "Collection";
		
		if (ModelFieldUtil.isCollection(modelField))
			fieldType = ModelFieldUtil.getFieldType(modelField, "collection");
		if (ModelFieldUtil.isMap(modelField))
			fieldType = ModelFieldUtil.getFieldType(modelField, "map");
		String addMethod = MethodUtil.toAddMethod(modelField);
		// boolean isCollection = ModelFieldUtil.isCollection(modelField);
		
		Buf buf = new Buf();
		buf.putLine("");
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			//String cappedName = modelField.getCappedName();
			//String qualifiedName = modelField.getQualifiedName();
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			String representation = modelField.getRepresentation();
			buf.put1(" * Allows addition of a new element to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" * @param value The new value to add to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" */" + NL);
		}
		buf.putLine1("public void " + addMethod + "(" + fieldType + " " + paramName + ") {");
		buf.putLine1("	if ("+paramName+" != null && !"+paramName+".isEmpty()) {");
		buf.putLine1("		synchronized (this." + fieldName + ") {");
		buf.putLine1("			this." + fieldName + ".addAll(" + paramName + ");");
		buf.putLine1("		}");
		buf.putLine1("	}");
		buf.putLine1("}");
		return buf.get();
	}

	public String generateAccessorPutItemMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		//String fieldType = getFieldType(modelField);
		//String fieldStructure = modelField.getStructure();
		String fieldClassName = modelField.getClassName();
		String fieldKeyClassName = modelField.getKeyClassName();
		String fieldNameCapped = NameUtil.capName(fieldName);
		//String fieldClassNameUncapped = NameUtil.uncapName(fieldClassName);
		String paramNameBase = ModelFieldUtil.getParameterName(modelField);
		String keyParamName = paramNameBase + "Key";
		String valueParamName = paramNameBase + "Value";
		
		//if (ModelFieldUtil.isCollection(modelField))
		//	fieldType = ModelFieldUtil.getFieldType(modelField, "collection");
		//if (ModelFieldUtil.isMap(modelField))
		//	fieldType = ModelFieldUtil.getFieldType(modelField, "map");
		//String addMethod = MethodUtil.toAddMethod(modelField);
		// boolean isCollection = ModelFieldUtil.isCollection(modelField);

		Buf buf = new Buf();
		buf.putLine("");
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			//String cappedName = modelField.getCappedName();
			//String qualifiedName = modelField.getQualifiedName();
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			String representation = modelField.getRepresentation();
			buf.put1(" * Allows addition of a new element to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" * @param value The new value to add to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" */" + NL);
		}
		buf.putLine1("public void addTo"+fieldNameCapped+"("+fieldKeyClassName+" "+keyParamName+", "+fieldClassName+" "+valueParamName+") {");
		buf.putLine1("	if ("+keyParamName+" != null) {");
		buf.putLine1("		synchronized (this."+fieldName+") {");
		buf.putLine1("			this."+fieldName+".put("+keyParamName+", "+valueParamName+");");
		buf.putLine1("		}");
		buf.putLine1("	}");
		buf.putLine1("}");
		return buf.get();
	}
	
	public String generateAccessorPutItemsMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		String fieldType = ModelFieldUtil.getFieldType(modelField, "map");
		//String fieldStructure = modelField.getStructure();
		//String fieldClassName = modelField.getClassName();
		String addMethod = MethodUtil.toAddMethod(modelField);
		// boolean isCollection = ModelFieldUtil.isCollection(modelField);
		//String fieldClassNameUncapped = NameUtil.uncapName(fieldClassName);
		String paramName = ModelFieldUtil.getParameterName(modelField) + "Map";
		
		Buf buf = new Buf();
		buf.putLine("");
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			//String cappedName = modelField.getCappedName();
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			//String qualifiedName = modelField.getQualifiedName();
			String representation = modelField.getRepresentation();
			buf.put1(" * Allows addition of a new element to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" * @param value The new value to add to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" */" + NL);
		}
		buf.putLine1("public void " + addMethod + "(" + fieldType + " " + paramName + ") {");
		buf.putLine1("	if ("+paramName+" != null && !"+paramName+".isEmpty()) {");
		buf.putLine1("		synchronized (this." + fieldName + ") {");
		buf.putLine1("			this." + fieldName + ".putAll(" + paramName + ");");
		buf.putLine1("		}");
		buf.putLine1("	}");
		buf.putLine1("}");
		return buf.get();
	}

	public String generateAccessorRemoveItemMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		//String fieldType = getFieldType(modelField);
		String fieldStructure = modelField.getStructure();
		String fieldClassName = modelField.getClassName();
		String fieldKeyClassName = modelField.getKeyClassName();
		String paramName = ModelFieldUtil.getParameterName(modelField);
		
		//if (ModelFieldUtil.isCollection(modelField))
		//	fieldType = ModelFieldUtil.getFieldType(modelField, "collection");
		//if (ModelFieldUtil.isMap(modelField))
		//	fieldType = ModelFieldUtil.getFieldType(modelField, "map");
		String removeMethod = MethodUtil.toRemoveMethod(modelField);
		// boolean isCollection = ModelFieldUtil.isCollection(modelField);

		Buf buf = new Buf();
		buf.putLine("");
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			//String cappedName = modelField.getCappedName();
			//String qualifiedName = modelField.getQualifiedName();
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			String representation = modelField.getRepresentation();
			buf.put1(" * Allows removal of an existing element of the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" * @param value The value to remove to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" */" + NL);
		}
		if (fieldStructure.equals("map"))
			buf.putLine1("public void " + removeMethod + "(" + fieldKeyClassName + " "+paramName+"Key) {");
		else buf.putLine1("public void " + removeMethod + "(" + fieldClassName + " "+paramName+") {");

		buf.putLine2("if ("+paramName+" != null ) {");
		buf.putLine2("	synchronized (this." + fieldName + ") {");
		if (fieldStructure.equals("map"))
			buf.putLine2("		this." + fieldName + ".remove("+paramName+"Key);");
		else buf.putLine2("		this." + fieldName + ".remove("+paramName+");");
		buf.putLine2("	}");
		buf.putLine2("}");
		buf.putLine1("}");
		return buf.get();
	}
	
	public String generateAccessorRemoveItemsMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		String fieldType = getFieldType(modelField);
		//String fieldStructure = modelField.getStructure();
		//String fieldClassName = modelField.getClassName();
		//String fieldKeyClassName = modelField.getKeyClassName();
		//String classNameUncapped = NameUtil.uncapName(fieldClassName);
		//String paramName = classNameUncapped + "Collection";
		String paramName = ModelFieldUtil.getParameterName(modelField) + "Collection";
		
		if (ModelFieldUtil.isCollection(modelField))
			fieldType = ModelFieldUtil.getFieldType(modelField, "collection");
		if (ModelFieldUtil.isMap(modelField))
			fieldType = ModelFieldUtil.getFieldType(modelField, "map");
		String removeMethod = MethodUtil.toRemoveMethod(modelField);
		// boolean isCollection = ModelFieldUtil.isCollection(modelField);
		
		Buf buf = new Buf();
		buf.putLine("");
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			String cappedName = modelField.getCappedName();
			//String qualifiedName = modelField.getQualifiedName();
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			String representation = modelField.getRepresentation();
			buf.put1(" * Allows removal of an existing element of the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" * @param value The value to remove to the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" */" + NL);
		}
		buf.putLine1("public void " + removeMethod + "(" + fieldType + " " + paramName + ") {");
		if (!ModelFieldUtil.isMap(modelField)) {
			buf.putLine2("if ("+paramName+" != null ) {");
			buf.putLine2("	synchronized (this." + fieldName + ") {");
			buf.putLine2("		this." + fieldName + ".removeAll(" + paramName + ");");
			buf.putLine2("	}");
			buf.putLine2("}");
		} else {
			String keyClassName = ModelFieldUtil.getFieldKeyClassName(modelField);
			buf.putLine2("if ("+paramName+" != null && !"+paramName+".isEmpty()) {");
			buf.putLine2("	synchronized (this." + fieldName + ") {");
			buf.putLine2("		Set<" + keyClassName + "> keySet = " + paramName + ".keySet();");
			buf.putLine2("		Iterator<" + keyClassName + "> iterator = keySet.iterator();");
			buf.putLine2("		while (iterator.hasNext()) {");
			buf.putLine2("			" + keyClassName + " key = iterator.next();");
			buf.putLine2("			this." + fieldName + ".remove(key);");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("}");
		}
		buf.putLine1("}");
		return buf.get();
	}

	public String generateAccessorClearMethod(ModelField modelField) throws Exception {
		String fieldName = modelField.getName();
		// String fieldType = getFieldType(modelField);
		String clearMethod = MethodUtil.toClearMethod(modelField);
		boolean isCollection = ModelFieldUtil.isCollection(modelField);

		Buf buf = new Buf();
		buf.putLine("");
		if (context.isEnabled("generateJavadoc")) {
			buf.put1("/**" + NL);
			//String cappedName = modelField.getCappedName();
			String fieldNameJavadoc = getFieldNameJavadoc(modelField);
			String representation = modelField.getRepresentation();
			buf.put1(" * Allows removal of all existing elements of the "+fieldNameJavadoc+" " + representation + "." + NL);
			buf.put1(" */" + NL);
		}
		buf.putLine1("public void " + clearMethod + "() {");
		buf.putLine1("	synchronized (" + fieldName + ") {");
		if (isCollection)
			buf.putLine2("	" + fieldName + ".clear();");
		else
			buf.putLine2("	" + fieldName + ".set(null);");
		buf.putLine1("	}");
		buf.putLine1("}");
		return buf.get();
	}

	protected String getFieldType(ModelField modelField) {
		return ModelFieldUtil.getFieldType(modelField);
	}

	protected String getFieldName(ModelField modelField) {
		return ModelFieldUtil.getFieldName(modelField);
	}

	
	/*
	 * Operation generation --------------------
	 */

	/*
	 * Javadoc generation ------------------
	 */

	public String generateClassJavadoc(ModelClass modelClass) throws Exception {
		return generateJavadoc(modelClass.getAuthor(), modelClass.getVersion());
	}

	public String generateInterfaceJavadoc(ModelInterface modelInterface) throws Exception {
		return generateJavadoc(modelInterface.getAuthor(), modelInterface.getVersion());
	}

	public String generateJavadoc(String author, String version) throws Exception {
		Buf buf = new Buf();
		buf.put1(NL);
		buf.put("/**" + NL);
		buf.put(" * Generated by Nam." + NL);
		// put(" * Autogenerated at <f:formatNow pattern=\"MM/dd/yyyy HH:mm\"/>"+".");
		buf.put(" *" + NL);
		if (author != null) {
			buf.put(" * @author " + author);
			buf.put(NL);
		}
		if (version != null) {
			buf.put(" * @version " + version);
			buf.put(NL);
		}
		buf.put(" */");
		buf.put(NL);
		return buf.get();
	}

	// public void generateClass(ModelClass modelClass) throws Exception {
	// //setSourceFile("GenericElement.java");
	// //setSourceFolder("src/main/java/template1");
	// //setSourcePath(TEMPLATE_WORKSPACE+"/"+context.getTemplateHome()+"/"+sourceFolder);
	// setTargetFile(modelClass.getClassName()+".java");
	// setTargetFolder("src/main/java/"+GenerateUtil.convertPackageToPath(modelClass.getPackageName()));
	// setTargetPath(context.getTargetPath() + "/" + targetFolder);
	//
	// FilterSet filterSet = new MyFilterSet();
	// filterSet.addFilter("/*@package-declaration@*/",
	// generatePackageDeclaration(modelClass));
	// filterSet.addFilter("/*@imported-packages@*/",
	// generateImportedPackages(modelClass));
	// filterSet.addFilter("/*@class-declaration@*/",
	// generateClassDeclaration(modelClass));
	// filterSet.addFilter("/*@static-fields@*/",
	// generateStaticFields(modelClass));
	// filterSet.addFilter("/*@static-methods@*/",
	// generateStaticMethods(modelClass));
	// filterSet.addFilter("/*@instance-fields@*/",
	// generateInstanceFields(modelClass));
	// filterSet.addFilter("/*@constructors@*/",
	// generateConstructors(modelClass));
	// filterSet.addFilter("/*@field-accessors@*/",
	// generateAccessorMethods(modelClass));
	// filterSet.addFilter("/*@instance-methods@*/",
	// generateInstanceMethods(modelClass));
	// //filterSet.addFilter("/*@hashcode-method@*/",
	// generateHashCodeMethod(modelClass));
	// //filterSet.addFilter("/*@equals-method@*/",
	// generateEqualsMethod(modelClass));
	// filterSet.addFilter("/*@end-class@*/", "}");
	// generateFile(filterSet);
	// }

	/*
	 * Component Helper methods ------------------------
	 */

	public Component findComponent(Input input) {
		return findComponent(input.getType(), input.getName(), false);
	}

	public Component findComponent(Input input, boolean required) {
		return findComponent(input.getType(), input.getName(), required);
	}

	public Component findComponent(Output output) {
		return findComponent(output.getType(), output.getName(), false);
	}

	public Component findComponent(Output result, boolean required) {
		return findComponent(result.getType(), result.getName(), required);
	}

	public Component findComponent(String type, String name, boolean required) {
		Component element = null;
		if (!StringUtils.isEmpty(name)) {
			element = context.getComponentByName(name);
			if (element == null)
				element = context.getComponentByType(type);
			if (required)
				Assert.notNull(element, "Component not found for name: " + name);
		} else {
			element = context.getComponentByType(type);
			if (required)
				Assert.notNull(element, "Component not found for type: " + type);
		}
		return element;
	}

	public Component findComponentByName(String name) {
		return findComponentByName(name, true);
	}

	public Component findComponentByName(String name, boolean required) {
		Assert.notEmpty(name, "Name must be specified");
		Component element = context.getComponentByName(name);
		if (required)
			Assert.notNull(element, "Component not found for name: " + name);
		return element;
	}

	public Component findComponentByType(String type, boolean required) {
		Assert.notEmpty(type, "Type must be specified");
		Component element = context.getComponentByType(type);
		if (required)
			Assert.notNull(element, "Component not found for type: " + type);
		return element;
	}

	public String findComponentType(Input input) {
		String elementType = null;
		Component element = findComponent(input);
		if (element != null)
			elementType = element.getType();
		if (elementType == null)
			elementType = input.getType();
		elementType = NameUtil.getClassName(elementType);
		return elementType;
	}

	public String findComponentName(Input input) {
		String elementName = null;
		Component element = findComponent(input);
		if (element != null)
			elementName = element.getName();
		if (elementName == null)
			elementName = input.getName();
		if (elementName == null)
			elementName = NameUtil.uncapName(input.getType());
		return elementName;
	}

	public String findComponentType(Output output) {
		String elementType = null;
		Component element = findComponent(output);
		if (element != null)
			elementType = element.getType();
		if (elementType == null)
			elementType = output.getType();
		elementType = NameUtil.getClassName(elementType);
		return elementType;
	}

	/*
	 * Utility methods ---------------
	 */

	public static String getConstructorModifiers(int modifiers) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(getVisibilityModifier(modifiers));
		if (Modifier.isStatic(modifiers))
			buf.append(SP + "static");
		if (Modifier.isFinal(modifiers))
			buf.append(SP + "final");
		if (Modifier.isAbstract(modifiers))
			buf.append(SP + "abstract");
		return buf.toString();
	}

	public static String getVisibilityModifier(ModelAttribute modelAttribute) throws Exception {
		return getVisibilityModifier(modelAttribute.getModifiers());
	}

	public static String getVisibilityModifier(ModelOperation modelOperation) throws Exception {
		return getVisibilityModifier(modelOperation.getModifiers());
	}

	public static String getVisibilityModifier(int modifiers) throws Exception {
		if (Modifier.isPublic(modifiers))
			return "public";
		if (Modifier.isPrivate(modifiers))
			return "private";
		if (Modifier.isProtected(modifiers))
			return "protected";
		return "";
	}

	protected ModelParameter createParameter(String type, String name) {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setClassName(NameUtil.getClassName(type));
		modelParameter.setPackageName(NameUtil.getPackageName(type));
		modelParameter.setName(name);
		return modelParameter;
	}

}
