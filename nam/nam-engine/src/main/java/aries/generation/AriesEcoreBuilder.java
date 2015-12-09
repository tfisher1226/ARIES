package aries.generation;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Applications;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Grouping;
import nam.model.Import;
import nam.model.Information;
import nam.model.Literal;
import nam.model.Messaging;
import nam.model.Modules;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Services;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.ApplicationUtil;
import nam.model.util.FieldUtil;
import nam.model.util.InformationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;
import nam.model.util.ViewUtil;
import nam.ui.View;

import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import aries.generation.engine.GenerationContext;


public class AriesEcoreBuilder {

	private GenerationContext context;
	
	private AriesModelHelper ariesModelHelper;

	private AriesModelParser ariesModelParser;

	private ExtendedMetaData extendedMetaData;
	
	private EcoreFactory ecoreFactory;

	private List<AriesDiagnostic> diagnostics;
	
	private boolean validate;

	
	public AriesEcoreBuilder() {
		initialize();
	}
	
	public AriesEcoreBuilder(GenerationContext context) {
		this.context = context;
		initialize();
	}
	
	public void setAriesModelHelper(AriesModelHelper ariesModelHelper) {
		this.ariesModelHelper = ariesModelHelper;
	}

	public void setAriesModelParser(AriesModelParser ariesModelParser) {
		this.ariesModelParser = ariesModelParser;
	}

	protected void initialize() {
		diagnostics = new ArrayList<AriesDiagnostic>();
	}

	public ExtendedMetaData getExtendedMetaData() {
		return extendedMetaData;
	}

	public void setExtendedMetaData(ExtendedMetaData extendedMetaData) {
		this.extendedMetaData = extendedMetaData;
	}

	public List<AriesDiagnostic> getDiagnostics() {
		return diagnostics;
	}

	public boolean getValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public List<EPackage> buildEPackages(List<URI> xmlSchemaURIs) throws Exception {
		List<EPackage> ePackages = new ArrayList<EPackage>();
		Iterator<URI> iterator = xmlSchemaURIs.iterator();
		while (iterator.hasNext()) {
			URI uri = iterator.next();
			String fileName = toFileString(uri);
			fileName = FileUtil.normalizePath(fileName);
			ePackages.addAll(buildEPackages(fileName));
		}
		return ePackages;
	}
	
	protected String toFileString(URI uri) {
		if (!uri.isFile()) 
			return null;

		StringBuffer result = new StringBuffer();
		char separator = File.separatorChar;

		if (uri.hasAuthority()) {
			//result.append(separator);
			//result.append(separator);
			result.append(uri.authority());

			if (uri.hasDevice()) 
				result.append(separator);
		}

		if (uri.hasDevice()) 
			result.append(uri.device());
		if (uri.hasAbsolutePath()) 
			result.append(separator);

		String[] segments = uri.segments();
		for (int i = 0, len = segments.length; i < len; i++) {
			if (i != 0) result.append(separator);
			result.append(segments[i]);
		}

		String decodedResult = URI.decode(result.toString());
		return decodedResult;
	}
	
	public Collection<EPackage> buildEPackages(String fileName) throws Exception {
		Serializable object = ariesModelParser.unmarshalFromFileSystem(fileName);
		String directory = NameUtil.getParentDirectoryName(fileName);
		
		if (object instanceof Project)
			return new ArrayList<EPackage>();
		else if (object instanceof Messaging)
			return new ArrayList<EPackage>();
		else if (object instanceof Information)
			return generateEcoreFromInformationModel(directory, fileName, (Information) object);
		else if (object instanceof Persistence)
			return generateEcoreFromPersistenceModel(directory, fileName, (Persistence) object);
		else if (object instanceof Applications)
			return generateEcoreFromApplicationsModel(directory, fileName, (Applications) object);
		else if (object instanceof Modules)
			return generateEcoreFromModulesModel(directory, fileName, (Modules) object);
		else if (object instanceof Services)
			return generateEcoreFromServicesModel(directory, fileName, (Services) object);
		else if (object instanceof View)
			return generateEcoreFromViewModel(directory, fileName, (View) object);
		throw new Exception("Unrecognized object: "+object.getClass());
	}

	protected List<EPackage> generateEcoreFromInformationModel(String directory, String filePath, Information model) throws Exception {
		List<EPackage> ePackages = new ArrayList<EPackage>();
		ariesModelHelper.validateAndAssure(model);
		//Map<String, Namespace> namespacesByUri = context.getNamespacesByUri();
		Collection<Namespace> namespaces = InformationUtil.getNamespaces(model);
		NamespaceUtil.setFilePath(namespaces, filePath);
		ePackages.addAll(generateEPackages(namespaces));
		assureEPackages(ePackages);
		validateEPackages(ePackages);
		return ePackages;
	}

	protected Collection<EPackage> generateEcoreFromPersistenceModel(String directory, String filePath, Persistence model) throws Exception {
		List<Import> importedFiles = PersistenceUtil.getImports(model);
		Iterator<Import> iterator = importedFiles.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			//String runtimeLocation = context.getRuntimeLocation();
			String importFilePath = importedFile.getFile();
			if (importedFile.getDir() != null)
				importFilePath = importedFile.getDir() + "/" + importedFile.getFile();
			Collection<Namespace> namespaces = context.getEngine().createNamespaces(importFilePath);
			//ariesModelHelper.assureNamespacesIncluded(namespaces, importedFile.get);
			NamespaceUtil.setFilePath(namespaces, filePath);
			//NamespaceUtil.setProperty(namespaces, "imported");
			//TODO nothing more for now
		}
		List<EPackage> ePackages = new ArrayList<EPackage>();
		Collection<Unit> units = PersistenceUtil.getUnits(model);
		Iterator<Unit> iterator2 = units.iterator();
		while (iterator2.hasNext()) {
			Unit unit = iterator2.next();
			//generateEcoreFromPersistenceModel(unit);
			//TODO nothing more for now
		}
		//ePackages.addAll(generateEPackages(model.getNamespaces()));
		return ePackages;
	}

	protected Collection<EPackage> generateEcoreFromApplicationsModel(String directory, String fileName, Applications model) throws Exception {
		List<Import> importedFiles = ApplicationUtil.getImports(model);
		return generateEcoreFromImportedFiles(directory, importedFiles);
	}
	
	protected Collection<EPackage> generateEcoreFromModulesModel(String directory, String fileName, Modules model) throws Exception {
		Set<Import> importedFiles = ModuleUtil.getImports(model);
		return generateEcoreFromImportedFiles(directory, importedFiles);
	}
	
	protected Collection<EPackage> generateEcoreFromServicesModel(String directory, String fileName, Services model) throws Exception {
		List<Import> importedFiles = ServicesUtil.getImports(model);
		return generateEcoreFromImportedFiles(directory, importedFiles);
	}
	
	protected Collection<EPackage> generateEcoreFromViewModel(String directory, String fileName, View model) throws Exception {
		List<Import> importedFiles = ViewUtil.getImports(model);
		return generateEcoreFromImportedFiles(directory, importedFiles);
	}

	protected Collection<EPackage> generateEcoreFromImportedFiles(String directory, Collection<Import> importedFiles) throws Exception {
		List<Namespace> importedNamespaces = new ArrayList<Namespace>();
		Iterator<Import> iterator = importedFiles.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			String fileName = importedFile.getFile();
			
			String runtimeFilePath = context.getFilePath(context.getCacheLocation() + "/model", null, fileName);
			//String runtimeFilePath = context.getRuntimeFilePath(ariesFileName);
			String runtimeFileFolder = NameUtil.getParentDirectoryName(runtimeFilePath);
			context.setRuntimeFileContext(runtimeFileFolder);
			
			//String fileContext = context.getRuntimeFileContext();
			//String filePath = context.getRuntimeFilePath(fileContext, fileName);
			
			List<Namespace> namespaces = context.getEngine().createNamespaces(runtimeFilePath);
			//NamespaceUtil.setProperty(namespaces, "imported");
			importedNamespaces.addAll(namespaces);
			//TODO nothing more for now
		}
		Collection<EPackage> ePackages = generateEPackages(importedNamespaces);
		return ePackages;
	}
	
	public Collection<EPackage> generateEPackages(Collection<Namespace> namespaces) throws Exception {
		List<EPackage> ePackages = new ArrayList<EPackage>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			ePackages.add(generateEPackage(namespace));
		}
		return ePackages;
	}

	public EPackage generateEPackage(Namespace namespace) throws Exception {
		ecoreFactory = new EcoreFactoryImpl();
		String namespaceUri = namespace.getUri();
		String packageName = NameUtil.getPackageNameFromNamespace(namespaceUri);
		EPackageImpl ePackage = (EPackageImpl) EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName(packageName);
		ePackage.setNsURI(namespaceUri);
		generateEClassifiers(ePackage, namespace);
		generateEStructuralFeatures(ePackage, namespace);
		return ePackage;
	}

	protected void generateEClassifiers(EPackageImpl ePackage, Namespace namespace) {
		String packageName = NameUtil.getPackageNameFromNamespace(namespace.getUri());
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			String className = TypeUtil.getClassName(type.getType());
			String javaName = packageName+"."+className;
			String typeName = type.getName();

//			if (javaName.contains("LeadershipRole"))
//				System.out.println();
			
			if (type instanceof Element) {
				//Element element = (Element) type;
				EClass eClass = ecoreFactory.createEClass();
				eClass.setName(typeName);
				eClass.setInstanceClassName(javaName);
				//eElement.setInstanceTypeName(className);
				eClass.setAbstract(false);
				eClass.setInterface(false);
				ePackage.getEClassifiers().add(eClass);
				//createAttributesAndReferences(element, eElement);
//				if (context.getElementByJavaTypeCache().containsKey(javaName))
//					System.out.println();
				context.getElementByJavaTypeCache().put(javaName, eClass);
				context.getElementByQNameCache().put(TypeUtil.getTypeFromEPackageAndEClass(ePackage, eClass), eClass);
				//ariesToEmfMap.put(element, eClass);
			
			} else if (type instanceof Enumeration) {
				//Enumeration enumeration = (Enumeration) type;
				EEnum eEnum = ecoreFactory.createEEnum();
				eEnum.setName(typeName);
				eEnum.setInstanceClassName(javaName);
				//eEnum.setInstanceTypeName(className);
				eEnum.setSerializable(true);
				ePackage.getEClassifiers().add(eEnum);
				//createAttributesAndReferences(enumeration, eEnum);
//				if (javaName.endsWith("RoleType"))
//					System.out.println();
//				if (context.getEnumerationCache().containsKey(javaName))
//					System.out.println();
				context.getEnumerationCache().put(javaName, eEnum);

			} else if (type instanceof Type) {
				EDataType eDataType = ecoreFactory.createEDataType();
				eDataType.setName(typeName);
				eDataType.setInstanceClassName(javaName);
				//eDataType.setInstanceTypeName(className);
				eDataType.setSerializable(true);
				ePackage.getEClassifiers().add(eDataType);
//				if (context.getDatatypeCache().containsKey(javaName))
//					System.out.println();
				context.getDatatypeCache().put(javaName, eDataType);
				//ariesToEmfMap.put(type, eDataType);
			}
		}
	}
	

	private void generateEStructuralFeatures(EPackageImpl ePackage, Namespace namespace) throws Exception {
		String packageName = NameUtil.getPackageNameFromNamespace(namespace.getUri());
		List<Type> types = NamespaceUtil.getTypes(namespace);
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			String typeName = type.getType();
			String className = TypeUtil.getClassName(typeName);
			String javaName = packageName+"."+className;

//			if (javaName.contains("LeadershipRole"))
//				System.out.println();
			
			if (type instanceof Element) {
				Element element = (Element) type;
				EClass eClass = context.getElementByJavaTypeCache().get(javaName);
				createAttributesAndReferences(eClass, element);
			
			} else if (type instanceof Enumeration) {
				Enumeration enumeration = (Enumeration) type;
//				if (javaName.endsWith("RoleType"))
//					System.out.println();
				EEnum eEnum = context.getEnumerationCache().get(javaName);
				createEEnumLiterals(eEnum, enumeration);

			} else if (type instanceof Type) {
				EDataType eDataType = context.getDatatypeCache().get(javaName);
				//createAttributesAndReferences(type, eDataType);
				//TODO nothing here for now
			}
		}
	}
	

	protected void createAttributesAndReferences(EClass eClass, Element element) throws Exception {
		List<Serializable> fields = element.getAttributesAndReferencesAndGroups();
		Iterator<Serializable> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Attribute) {
				EAttribute eAttribute = createEAttribute((Attribute) object);
				eClass.getEStructuralFeatures().add(eAttribute);
			} else if (object instanceof Reference) {
				EReference eReference = createEReference((Reference) object);
				eClass.getEStructuralFeatures().add(eReference);
			} else if (object instanceof Grouping) {
				Grouping grouping = (Grouping) object;
				createAttributesAndReferences(eClass, grouping.getFields());
			}
		}
	}
	
	protected void createAttributesAndReferences(EClass eClass, List<Field> fields) throws Exception {
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Attribute) {
				EAttribute eAttribute = createEAttribute((Attribute) object);
				eClass.getEStructuralFeatures().add(eAttribute);
			} else if (object instanceof Reference) {
				EReference eReference = createEReference((Reference) object);
				eClass.getEStructuralFeatures().add(eReference);
			}
		}
	}

	protected EAttribute createEAttribute(Attribute attribute) throws Exception {
		EAttribute eAttribute = ecoreFactory.createEAttribute();
		
		//establish type
		EClassifier eClassifier = getEClassifier(attribute.getType());
		EGenericType eGenericType = getEGenericType(eClassifier);
		eAttribute.setEType(eClassifier);
		eAttribute.setEGenericType(eGenericType);
		
		//initialize bounds
		eAttribute.setLowerBound(attribute.getMinOccurs());
		eAttribute.setUpperBound(attribute.getMaxOccurs());

		//initialize properties
		eAttribute.setName(attribute.getName());
		eAttribute.setID(attribute.getId());
		eAttribute.setChangeable(FieldUtil.isChangeable(attribute));
		eAttribute.setDerived(FieldUtil.isChangeable(attribute));
		eAttribute.setOrdered(FieldUtil.isOrdered(attribute));
		eAttribute.setTransient(FieldUtil.isTransient(attribute));
		eAttribute.setUnique(FieldUtil.isUnique(attribute));
		eAttribute.setUnsettable(FieldUtil.isUnsettable(attribute));
		eAttribute.setVolatile(FieldUtil.isVolatile(attribute));

		//other attribute properties
		eAttribute.setDefaultValueLiteral(attribute.getDefault());
		//eAttribute.setDefaultValue(attribute.getDefault());
		return eAttribute;
	}

	protected EReference createEReference(Reference reference) throws Exception {
		EReference eReference = ecoreFactory.createEReference();
		
		//using default literal as placeholder for type - which is establish in subsequent pass
		eReference.setDefaultValueLiteral(reference.getType());
		
		//establish type
		//EClassifier eClassifier = getEClassifier(reference.getType());
		//EGenericType eGenericType = getEGenericType(eClassifier);
		//eReference.setEType(eClassifier);
		//eReference.setEGenericType(eGenericType);
		
		//initialize bounds
		eReference.setLowerBound(reference.getMinOccurs());
		eReference.setUpperBound(reference.getMaxOccurs());

		//initialize properties
		eReference.setName(reference.getName());
		eReference.setChangeable(FieldUtil.isChangeable(reference));
		eReference.setDerived(FieldUtil.isChangeable(reference));
		eReference.setOrdered(FieldUtil.isOrdered(reference));
		eReference.setTransient(FieldUtil.isTransient(reference));
		eReference.setUnique(FieldUtil.isUnique(reference));
		eReference.setUnsettable(FieldUtil.isUnsettable(reference));
		eReference.setVolatile(FieldUtil.isVolatile(reference));
		
		eReference.setContainment(true);
		eReference.setEOpposite(null);

		//other attribute properties
		//eReference.setDefaultValueLiteral(reference.getDefault());
		//eReference.setDefaultValue(reference.getDefault());
		return eReference;
	}

	protected EClassifier getEClassifier(String typeName) throws Exception {
		EClassifier eClassifier = null;
		String javaName = TypeUtil.getJavaName(typeName);
		String className = TypeUtil.getClassName(typeName);

//		if (javaName.equals("org.sgiusa.model.RoleType"))
//			System.out.println();
		
//		if (javaName.contains("[]")) {
//			//javaName = javaName.replace("[]", "");
//			className = className.replace("[]", "");
//		}
		
		Class<?> instanceClass = null;
		if (ClassUtil.isJavaPrimitiveType(className)) {
			instanceClass = ClassUtil.getClassForJavaPrimitiveType(className);
			
		//TODO handle these special types in a much better way - externalize!
		} else if (CodeGenUtil.isJavaDefaultType(className) || javaName.equals("java.util.Date") || javaName.equals("java.io.Serializable")) {
			instanceClass = ClassUtil.loadClass(javaName);
		}
		
		if (instanceClass != null) {
			eClassifier = ecoreFactory.createEDataType();
			eClassifier.setName(className);
			eClassifier.setInstanceClass(instanceClass);
			eClassifier.setInstanceClassName(javaName);
			eClassifier.setInstanceTypeName(javaName);

		} else {
			EClass eClass = context.getElementByJavaTypeCache().get(javaName);
			EEnum eEnum = context.getEnumerationCache().get(javaName);
			EDataType eDataType = context.getDatatypeCache().get(javaName);
			if (eClass != null) {
				eClassifier = eClass; 
			} else if (eEnum != null) {
				eClassifier = eEnum; 
			} else if (eDataType != null) {
				eClassifier = eDataType; 
			}
		}
		
//		if (!javaName.startsWith("java."))
//			System.out.println();
//		if (eClassifier == null)
//			System.out.println();
		Assert.notNull(eClassifier, "Class not found: "+javaName);
		return eClassifier;
	}

	protected EGenericType getEGenericType(EClassifier eClassifier) throws Exception {
		EGenericType eGenericType = ecoreFactory.createEGenericType();
		eGenericType.setEClassifier(eClassifier);
		return eGenericType;
	}
	
	protected void createEEnumLiterals(EEnum eEnum, Enumeration enumeration) throws Exception {
		List<Literal> literals = enumeration.getLiterals();
		Iterator<Literal> iterator = literals.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			EEnumLiteral eEnumLiteral = ecoreFactory.createEEnumLiteral();
			eEnumLiteral.setName(literal.getName());
			eEnumLiteral.setValue(literal.getValue());
			eEnumLiteral.setLiteral(literal.getLabel());
			//EEnumLiteral literal = eEnum.getEEnumLiteral(0);
			eEnum.getELiterals().add(eEnumLiteral);
		}
	}


	protected void assureEPackages(List<EPackage> ePackages) throws Exception {
		Iterator<EPackage> iterator = ePackages.iterator();
		while (iterator.hasNext()) {
			EPackage ePackage = iterator.next();
			assureEPackage(ePackage);
		}
	}

	protected void assureEPackage(EPackage ePackage) throws Exception {
		EList<EClassifier> eClassifiers = ePackage.getEClassifiers();		
		Iterator<EClassifier> iterator = eClassifiers.iterator();
		while (iterator.hasNext()) {
			EClassifier eClassifier = iterator.next();
			if (eClassifier instanceof EClass)
				assureEClass((EClass) eClassifier);
		}
	}

	protected void assureEClass(EClass eClass) throws Exception {
		EList<EStructuralFeature> eStructuralFeatures = eClass.getEStructuralFeatures();
		Iterator<EStructuralFeature> iterator = eStructuralFeatures.iterator();
		while (iterator.hasNext()) {
			EStructuralFeature eStructuralFeature = iterator.next();
			
			if (eStructuralFeature instanceof EReference) {
				EReference eReference = (EReference) eStructuralFeature;
				
				//establish type
				String typeName = eReference.getDefaultValueLiteral();
				String localPart = TypeUtil.getLocalPart(typeName);
				if (localPart.contains(".")) {
					//String packageName = NameUtil.getPackageName(typeName);
					//String className = NameUtil.getClassName(typeName);
					typeName = TypeUtil.getTypeFromJavaType(localPart);
				}
				
				EClassifier eClassifier = getEClassifier(typeName);
				EGenericType eGenericType = getEGenericType(eClassifier);
				eReference.setEType(eClassifier);
				eReference.setEGenericType(eGenericType);
				
				//we assume (for now) no default literal supported for references
				eReference.setDefaultValueLiteral(null);
			}
		}
	}

	protected void validateEPackages(List<EPackage> ePackages) throws Exception {
		
	}

}
