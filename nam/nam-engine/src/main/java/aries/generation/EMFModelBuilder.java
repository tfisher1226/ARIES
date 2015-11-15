package aries.generation;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.model.util.TypeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.ClassUtil;
import org.aries.util.ExceptionUtil;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;
import org.aries.util.TypeMap;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.adapters.AdapterRegistry;
import org.eclipse.bpel.model.adapters.BasicBPELAdapterFactory;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.bpel.model.resource.BPELResourceFactoryImpl;
import org.eclipse.bpel.model.resource.BPELResourceSetImpl;
import org.eclipse.bpel.model.util.ImportResolver;
import org.eclipse.bpel.model.util.ImportResolverRegistry;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.eclipse.wst.wsdl.internal.util.WSDLAdapterFactory;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;

import aries.generation.engine.GenerationContext;


public class EMFModelBuilder {

	private static Log log = LogFactory.getLog(EMFModelBuilder.class);

	private GenerationContext context;

	private AriesModelHelper ariesModelHelper;

	private AriesModelParser ariesModelParser;

	
	public EMFModelBuilder(GenerationContext context) {
		this.context = context;
		// register globally the Ecore Resource Factory to the ".ecore" extension
		// weird that we need to do this, but well...
		//Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
	}

	public void setAriesModelHelper(AriesModelHelper ariesModelHelper) {
		this.ariesModelHelper = ariesModelHelper;
	}
	
	public void setAriesModelParser(AriesModelParser ariesModelParser) {
		this.ariesModelParser = ariesModelParser;
	}

	protected ResourceSet createResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		//BasicExtendedMetaData extendedMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
		//ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(resourceSet.getPackageRegistry());
		//resourceSet.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
		//resourceSet.getPackageRegistry().put(org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage.eINSTANCE.getNsURI(), org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage.eINSTANCE);
		//resourceSet.getPackageRegistry().put(XSDPackage.eNS_URI, XSDPackage.eINSTANCE);
		return resourceSet;
	}

	/*
	 * ARIES Processing
	 * ----------------
	 */

	protected AriesEcoreBuilder createARIESEcoreBuilder() {
		ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
		AriesEcoreBuilder ariesEcoreBuilder = new AriesEcoreBuilder(context);
		ariesEcoreBuilder.setAriesModelHelper(ariesModelHelper);
		ariesEcoreBuilder.setAriesModelParser(ariesModelParser);
		ariesEcoreBuilder.setExtendedMetaData(extendedMetaData);
		ariesEcoreBuilder.setValidate(true);
		return ariesEcoreBuilder;
	}
	
	public ResourceSet buildEPackagesFromARIES(List<URL> xmlSchemaURLs) throws Exception {
		AriesEcoreBuilder ariesEcoreBuilder = createARIESEcoreBuilder();
		ResourceSet resourceSet = createResourceSet();
		List<URI> xmlSchemaURIs = new ArrayList<URI>();
		Iterator<URL> iterator = xmlSchemaURLs.iterator();
		while (iterator.hasNext()) {
			URL schemaURL = (URL) iterator.next();
			URI schemaURI = URI.createURI(schemaURL.toURI().toString());
			xmlSchemaURIs.add(schemaURI);
		}
		try {
			buildEPackagesFromARIES(resourceSet, ariesEcoreBuilder, xmlSchemaURIs);
//			buildEResourcesFromARIES(resourceSet, xmlSchemaURIs);
			return resourceSet;
			
		} catch (Exception e) {
			String fileSet = xmlSchemaURIs.toString();
			String errorMessage = "Problem with file set: "+fileSet;
			Exception rootCause = ExceptionUtil.getRootCause(e);
			String rootCauseMessage = rootCause.getMessage();
			if (rootCauseMessage != null)
				errorMessage += ": "+rootCauseMessage;
			log.error(errorMessage);
			throw e;
		}
	}
	
	//TODO do appropriate thing with diagnostics
	public void buildEPackagesFromARIES(ResourceSet resourceSet, AriesEcoreBuilder ariesEcoreBuilder, List<URI> xmlSchemaURIs) throws Exception {
		List<?> eCorePackages = ariesEcoreBuilder.buildEPackages(xmlSchemaURIs);
		List<AriesDiagnostic> diagnostics = ariesEcoreBuilder.getDiagnostics();
		//Map targetNamespaceToEPackageMap = ariesEcoreBuilder.getTargetNamespaceToEPackageMap();
		//Map componentToEModelElementMap = ariesEcoreBuilder.getAriesComponentToEModelElementMap();
		Iterator<?> iterator = eCorePackages.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (object instanceof EPackage) {
				EPackage ePackage = (EPackage) object;
				String nsURI = ePackage.getNsURI();
				resourceSet.getPackageRegistry().put(nsURI, ePackage);
				//ExtendedMetaData extendedMetaData = (ExtendedMetaData) resourceSet.getLoadOptions().get(XMLResource.OPTION_EXTENDED_META_DATA);
				//EStructuralFeature rootNodeFeature = extendedMetaData.demandFeature(nsURI, "rootNode", true);
			} else {
				System.out.println("ERROR: Unexpected result: "+object);
				continue;
			}
		}
	}

//	public void buildEResourcesFromARIES(ResourceSet resourceSet, List<URI> xmlSchemaURIs) throws Exception {
//		Iterator<URI> iterator = xmlSchemaURIs.iterator();
//		while (iterator.hasNext()) {
//			URI uri = iterator.next();
//			
//			emfResourceSet.getResources();
//			List<Service> services = ariesModelHelper.builder.buildServices(projectResources);
//			emfServiceMap = ServiceUtil.createMap(services);
//		}
//	}
	
	
	/*
	 * XSD Processing
	 * --------------
	 */

	protected XSDEcoreBuilder createXSDEcoreBuilder() {
		BasicExtendedMetaData extendedMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
		XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder(extendedMetaData);
		xsdEcoreBuilder.setValidate(true);
		return xsdEcoreBuilder;
	}

	public ResourceSet buildFromXSD(String xmlSchemaFile) {
		XSDEcoreBuilder xsdEcoreBuilder = createXSDEcoreBuilder();
		ResourceSet resourceSet = createResourceSet();
		URI xmlSchemaURI = URI.createFileURI(xmlSchemaFile);
		List<URI> xmlSchemaURIs = new ArrayList<URI>();
		xmlSchemaURIs.add(xmlSchemaURI);
		buildFromXSD(resourceSet, xsdEcoreBuilder, xmlSchemaURIs);
		return resourceSet;
	}

	public ResourceSet buildFromXSD(URL xmlSchemaURL) throws Exception {
		List<URL> xmlSchemaURLs = new ArrayList<URL>(); 
		xmlSchemaURLs.add(xmlSchemaURL);
		ResourceSet resourceSet = buildFromXSD(xmlSchemaURLs);
		return resourceSet;
	}
	
	public ResourceSet buildFromXSD(List<URL> xmlSchemaURLs) throws Exception {
		XSDEcoreBuilder xsdEcoreBuilder = createXSDEcoreBuilder();
		ResourceSet resourceSet = createResourceSet();
		List<URI> xmlSchemaURIs = new ArrayList<URI>();
		Iterator<URL> iterator = xmlSchemaURLs.iterator();
		while (iterator.hasNext()) {
			URL schemaURL = (URL) iterator.next();
			URI schemaURI = URI.createURI(schemaURL.toURI().toString());
			xmlSchemaURIs.add(schemaURI);
		}
		try {
			buildFromXSD(resourceSet, xsdEcoreBuilder, xmlSchemaURIs);
			return resourceSet;
		} catch (Exception e) {
			Exception rootCause = ExceptionUtil.getRootCause(e);
			System.out.println(rootCause.getMessage());
			String message = xmlSchemaURIs.toString();
			throw new FileNotFoundException(message);
		}
	}

	public void buildFromXSD(ResourceSet resourceSet, List<URI> xmlSchemaURIs) {
		XSDEcoreBuilder xsdEcoreBuilder = createXSDEcoreBuilder();
		buildFromXSD(resourceSet, xsdEcoreBuilder, xmlSchemaURIs);
	}
	
	public void buildFromXSD(ResourceSet resourceSet, XSDEcoreBuilder xsdEcoreBuilder, List<URI> xmlSchemaURIs) {
		Collection<?> eCorePackages = xsdEcoreBuilder.generate(xmlSchemaURIs);
		List diagnostics = xsdEcoreBuilder.getDiagnostics();
		//List diagnostics = xsdEcoreBuilder.getDiagnostics();
		//Map targetNamespaceToEPackageMap = xsdEcoreBuilder.getTargetNamespaceToEPackageMap();
		//Map xsdComponentToEModelElementMap = xsdEcoreBuilder.getXSDComponentToEModelElementMap();
		Iterator<?> iterator = eCorePackages.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (object instanceof EPackage) {
				EPackage ePackage = (EPackage) object;
				String nsURI = ePackage.getNsURI();
				resourceSet.getPackageRegistry().put(nsURI, ePackage);
				//ExtendedMetaData extendedMetaData = (ExtendedMetaData) resourceSet.getLoadOptions().get(XMLResource.OPTION_EXTENDED_META_DATA);
				//EStructuralFeature rootNodeFeature = extendedMetaData.demandFeature(nsURI, "rootNode", true);
			} else if (object instanceof ArrayList) {
				//this is acceptable (but unused)
				continue;
			} else {
				System.out.println("ERROR: Unexpected result: "+object);
			}
		}
	}

	public void buildFromXSD(ResourceSet resourceSet, XSDSchema xsdSchema) throws Exception {
		XSDEcoreBuilder xsdEcoreBuilder = createXSDEcoreBuilder();
		buildFromXSD(resourceSet, xsdEcoreBuilder, xsdSchema);
	}

	public void buildFromXSD(ResourceSet resourceSet, XSDEcoreBuilder xsdEcoreBuilder, XSDSchema xsdSchema) throws Exception {
		String schemaLocation = xsdSchema.getSchemaLocation();
		String targetNamespace = xsdSchema.getTargetNamespace();
		
		if (targetNamespace == null) {
			EList<?> contents = xsdSchema.getContents();
			Iterator<?> iterator = contents.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof XSDImport) {
					XSDImport xsdImport = (XSDImport) object;
					xsdSchema = xsdImport.getResolvedSchema();
					schemaLocation = xsdSchema.getSchemaLocation();
					targetNamespace = xsdSchema.getTargetNamespace();
					//TODO assuming for now only one imported schema
					break;
				}
			}
		}
		
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName(ProjectLevelHelper.getPackageName(targetNamespace));
		ePackage.setNsURI(targetNamespace);

		Map<?, ?> namespaceMap = xsdSchema.getQNamePrefixToNamespaceMap();
		Iterator<?> prefixIterator = namespaceMap.keySet().iterator();
		while (prefixIterator.hasNext()) {
			String prefix = (String) prefixIterator.next();
			String namespace = (String) namespaceMap.get(prefix);
			if (namespace.equals(targetNamespace)) {
				ePackage.setNsPrefix(prefix);
				break;
			}
		}
		
		//JAXBSessionCache.INSTANCE.addSchema(schemaLocation);
		
		xsdEcoreBuilder.generate(xsdSchema);
		EList<?> contents = xsdSchema.getContents();
		Iterator<?> iterator = contents.iterator();
		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			if (object instanceof XSDSimpleTypeDefinition) {
				EDataType eDataType = xsdEcoreBuilder.getEDataType((XSDSimpleTypeDefinition) object);
				ePackage.getEClassifiers().add(eDataType);
				
			} else if (object instanceof XSDComplexTypeDefinition) {
				EClass eClass = xsdEcoreBuilder.getEClass((XSDComplexTypeDefinition) object);
				ePackage.getEClassifiers().add(eClass);

			} else if (object instanceof XSDElementDeclaration) {
				XSDElementDeclaration xsdElementDefinition = (XSDElementDeclaration) object;
				XSDTypeDefinition xsdTypeDefinition = xsdElementDefinition.getTypeDefinition();
				//EStructuralFeature eStructuralFeature = xsdEcoreBuilder.getEStructuralFeature(xsdElementDefinition);
				//xsdTypeDefinition.setName(TypeUtil.getFilteredElementName(xsdElementDefinition.getName()));
				EClassifier eClassifier = xsdEcoreBuilder.getEClassifier(xsdTypeDefinition);
				eClassifier.setName(org.aries.util.TypeUtil.getFilteredElementName(eClassifier.getName()));
				ePackage.getEClassifiers().add(eClassifier);
				
				String namespace = xsdTypeDefinition.getTargetNamespace();
				String packageName = ProjectLevelHelper.getPackageName(namespace);
				String classId = packageName + ":" + NameUtil.uncapName(eClassifier.getName());
				String className = packageName + "." + eClassifier.getName();
				
				try {
					Class<?> classObject = ClassUtil.loadClass(className);
					TypeMap.INSTANCE.addType(classId, namespace, classObject);
				} catch (ClassNotFoundException e) {
					//ignore for now
				}
			}
			//String className = TypeUtil.getTypeFromEClass(eClass);
			//String packageName = NameUtil.getPackageNameFromEPackage(ePackage);
			//String typeName = TypeUtil.getTypeFromEPackageAndEClass(ePackage, eClass);
			//TypeMap.INSTANCE.addType(typeName, schemaNamespace, eClass.getInstanceClass());
		}

		resourceSet.getPackageRegistry().put(targetNamespace, ePackage);

//		Iterator<?> iterator = eCorePackages.iterator();
//		while (iterator.hasNext()) {
//			Object object = iterator.next();
//			if (object instanceof EPackage) {
//				EPackage ePackage = (EPackage) object;
//				String nsURI = ePackage.getNsURI();
//				resourceSet.getPackageRegistry().put(nsURI, ePackage);
//				
//				//ExtendedMetaData extendedMetaData = (ExtendedMetaData) resourceSet.getLoadOptions().get(XMLResource.OPTION_EXTENDED_META_DATA);
//				//EStructuralFeature rootNodeFeature = extendedMetaData.demandFeature(nsURI, "rootNode", true);
//			}
//		}
	}

	/*
	 * WSDL Processing
	 * ---------------
	 */

//	public ResourceSet buildFromWSDL(String schemaFile) {
//		URI schemaURI = URI.createFileURI(schemaFile);
//		ResourceSet resourceSet = buildFromWSDL(schemaURI);
//		return resourceSet;
//	}

//	public ResourceSet buildFromWSDL(URL xmlSchemaURL) throws URISyntaxException {
//		URI schemaFileURI = URI.createURI(xmlSchemaURL.toURI().toString());
//		ResourceSet resourceSet = buildFromWSDL(schemaFileURI);
//		return resourceSet;
//	}

	public ResourceSet buildFromWSDL(URL xmlSchemaURL) throws URISyntaxException {
		List<URL> xmlSchemaURLs = new ArrayList<URL>(); 
		xmlSchemaURLs.add(xmlSchemaURL);
		ResourceSet resourceSet = buildFromWSDL(xmlSchemaURLs);
		return resourceSet;
	}
	
	public ResourceSet buildFromWSDL(List<URL> xmlSchemaURLs) throws URISyntaxException {
		//XSDEcoreBuilder xsdEcoreBuilder = createXSDEcoreBuilder();
		ResourceSet resourceSet = createResourceSet();

		// Register the basic elements of the specification
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "xml", new XMLResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "xsd", new XSDResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "wsdl", new WSDLResourceFactoryImpl());
		//resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMLResourceFactoryImpl());
		//resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("wsdl", new WSDLResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(XSDPackage.eNS_URI, XSDPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(WSDLPackage.eNS_URI, WSDLPackage.eINSTANCE);

		List<URI> xmlSchemaURIs = new ArrayList<URI>();
		Iterator<URL> iterator = xmlSchemaURLs.iterator();
		while (iterator.hasNext()) {
			URL schemaURL = (URL) iterator.next();
			URI schemaURI = URI.createURI(schemaURL.toURI().toString());
			xmlSchemaURIs.add(schemaURI);
		}
		//buildNamespacesFromWSDL(resourceSet, xmlSchemaURIs);
		buildServicesFromWSDL(resourceSet, xmlSchemaURIs);
		return resourceSet;
	}
	
	public void buildNamespacesFromWSDL(ResourceSet resourceSet, List<URI> xmlSchemaURIs) {
		XSDEcoreBuilder xsdEcoreBuilder = createXSDEcoreBuilder();
		buildFromXSD(resourceSet, xsdEcoreBuilder, xmlSchemaURIs);
	}
	
	public void buildNamespacesFromWSDL(ResourceSet resourceSet, XSDEcoreBuilder xsdEcoreBuilder, List<URI> xmlSchemaURIs) {
		Collection<?> eCorePackages = xsdEcoreBuilder.generate(xmlSchemaURIs);
		Iterator<?> iterator = eCorePackages.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (object instanceof EPackage) {
				EPackage ePackage = (EPackage) object;
				String nsURI = ePackage.getNsURI();
				resourceSet.getPackageRegistry().put(nsURI, ePackage);
				
				//ExtendedMetaData extendedMetaData = (ExtendedMetaData) resourceSet.getLoadOptions().get(XMLResource.OPTION_EXTENDED_META_DATA);
				//EStructuralFeature rootNodeFeature = extendedMetaData.demandFeature(nsURI, "rootNode", true);
			}
		}
	}

	public void buildServicesFromWSDL(ResourceSet resourceSet, List<URI> xmlSchemaURIs) {
		Iterator<URI> iterator = xmlSchemaURIs.iterator();
		while (iterator.hasNext()) {
			URI xmlSchemaUri  = iterator.next();
			buildServicesFromWSDL(resourceSet, xmlSchemaUri);
		}
	}

	public ResourceSet buildServicesFromWSDL(ResourceSet resourceSet, URI xmlSchemaUri) {
		//WSDLResourceImpl wsdlMainResource = (WSDLResourceImpl) resourceSet.createResource(URI.createURI("*.wsdl"));
		//Resource resource = resourceSet.createResource(wsdlSchemaURI);
		//Registry packageRegistry = resourceSet.getPackageRegistry();
		
		// Register the required extensions
		// None here
		
		// Load the main document
		Resource wsdlResource = resourceSet.getResource(xmlSchemaUri, true);
		Definition definition = (Definition) wsdlResource.getContents().iterator().next();
		buildServicesFromWSDL(resourceSet, definition);
		return resourceSet;
	}
	
	public ResourceSet buildServicesFromWSDL(ResourceSet resourceSet, Definition definition) {
		// Add the initial definition
		Set<Definition> definitions = new HashSet<Definition>();
		definitions.add(definition);

		// Process the imports
		Map<?, ?> imports = definition.getImports();
		processImports(imports, definitions);
		return resourceSet;
	}
	
	/**
	 * Finds the definitions from imports and processes them recursively.
	 * @param importMap a map of imports (see {@link Definition#getImports()})
	 * @param definitions a list of definitions, found from import declarations
	 */
	private static void processImports(Map<?, ?> importMap, Collection<Definition> definitions) {
		for (Object object: importMap.values()) {

			// Case "java.util.list"
			if (object instanceof List<?>) {
				for (Object oo : ((List<?>) object)) {
					Definition d = ((org.eclipse.wst.wsdl.Import) oo).getEDefinition();
					if (d != null && ! definitions.contains(d)) {
						definitions.add(d);
						processImports(d.getImports(), definitions);
					}
				}
			}

			// Case "org.eclipse.wst.Definition"
			else if (object instanceof Definition) {
				Definition d = (Definition) object;
				if (!definitions.contains(d)) {
					definitions.add(d);
					processImports(d.getImports(), definitions);
				}
			}
		}
	}

	
	/*
	 * BPEL Processing
	 * ---------------
	 */

	public ResourceSet buildFromBPEL(URL xmlSchemaURL) throws Exception {
		List<URL> xmlSchemaURLs = new ArrayList<URL>(); 
		xmlSchemaURLs.add(xmlSchemaURL);
		ResourceSet resourceSet = buildFromBPEL(xmlSchemaURLs);
		return resourceSet;
	}
	
	public ResourceSet buildFromBPEL(List<URL> xmlSchemaURLs) throws Exception {
		//XSDEcoreBuilder xsdEcoreBuilder = createXSDEcoreBuilder();
		//ResourceSet resourceSet = createResourceSet();
		BPELResourceSetImpl resourceSet = new BPELResourceSetImpl();

		//NOTE this is to satisfy runtime of server from maven...
		EPackage.Registry.INSTANCE.put(WSDLPackage.eNS_URI, WSDLPackage.eINSTANCE);

//		System.out.println("***"+resourceSet.getPackageRegistry());
//		System.out.println("***"+EPackage.Registry.INSTANCE);
//		WSDLPackage theWSDLPackage = (WSDLPackage)EPackage.Registry.INSTANCE.getEPackage(WSDLPackage.eNS_URI);
//		System.out.println("***"+theWSDLPackage);

//		System.out.println("***"+EPackage.Registry.INSTANCE.getEPackage(WSDLPackage.eNS_URI));
//		System.out.println("***"+EPackage.Registry.INSTANCE.getEPackage(PartnerlinktypePackage.eNS_URI));
//		System.out.println("***"+PartnerlinktypePackage.eINSTANCE);

		WSDLAdapterFactory wsdlAdapterFactory = new WSDLAdapterFactory();
		AdapterRegistry.INSTANCE.registerAdapterFactory(WSDLPackage.eINSTANCE, wsdlAdapterFactory);
		AdapterRegistry.INSTANCE.registerAdapterFactory(BPELPackage.eINSTANCE, BasicBPELAdapterFactory.INSTANCE);
		
		// Register the basic elements of the specification
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "xml", new XMLResourceFactoryImpl());
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "xsd", new XSDResourceFactoryImpl());
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "wsdl", new WSDLResourceFactoryImpl());
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "bpel", new BPELResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put( "xml", new XMLResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put( "xsd", new XSDResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put( "wsdl", new WSDLResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put( "bpel", new BPELResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(XSDPackage.eNS_URI, XSDPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(WSDLPackage.eNS_URI, WSDLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(BPELPackage.eNS_URI, BPELPackage.eINSTANCE);

		List<URI> xmlSchemaURIs = new ArrayList<URI>();
		Iterator<URL> iterator = xmlSchemaURLs.iterator();
		while (iterator.hasNext()) {
			URL schemaURL = iterator.next();
			URI schemaURI = URI.createURI(schemaURL.toURI().toString());
			xmlSchemaURIs.add(schemaURI);
		}
		//buildNamespacesFromWSDL(resourceSet, xmlSchemaURIs);
		buildFromBPEL(resourceSet, xmlSchemaURIs);
		return resourceSet;
	}

	public void buildFromBPEL(ResourceSet resourceSet, List<URI> xmlSchemaURIs) throws Exception {
		Iterator<URI> iterator = xmlSchemaURIs.iterator();
		while (iterator.hasNext()) {
			URI xmlSchemaUri  = iterator.next();
			BPELResource bpelResource = buildFromBPEL(resourceSet, xmlSchemaUri);
			resourceSet.getResources().add(bpelResource);
			buildFromBPEL(resourceSet, bpelResource);
		}
	}

	public BPELResource buildFromBPEL(ResourceSet resourceSet, URI xmlSchemaUri) throws Exception {
		// Load the document and recursively load all specified imports
		BPELResource bpelResource = (BPELResource) resourceSet.getResource(xmlSchemaUri, true);
		return bpelResource;

//		// Load the main document
//		BPELResource bpelResource = (BPELResource) resourceSet.createResource(xmlSchemaUri);
//		bpelResource.getContents();
//
//		System.out.println(xmlSchemaUri.hasAbsolutePath());
//		File file = new File(xmlSchemaUri.toFileString());
//		InputStream inputStream = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
//		
//		BPELReader reader = new BPELReader();
//		reader.read(bpelResource, inputStream);
		
//		// Add the initial definition
//		Set<Definition> definitions = new HashSet<Definition>();
//		definitions.add(definition);
//
//		// Process the imports
//		Map<?, ?> imports = definition.getImports();
//		processImports(imports, definitions);
//		return resourceSet;
	}

	public void buildFromBPEL(ResourceSet resourceSet, BPELResource bpelResource) throws Exception {
		Process process = bpelResource.getProcess();
		EList<org.eclipse.bpel.model.Import> imports = process.getImports();
		Iterator<org.eclipse.bpel.model.Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			org.eclipse.bpel.model.Import importObject = iterator.next();
			ImportResolver[] importResolvers = ImportResolverRegistry.INSTANCE.getResolvers(importObject.getImportType());
			for (ImportResolver importResolver : importResolvers) {
				URI bpelResourceUri = bpelResource.getURI();
				String fileString = bpelResourceUri.toFileString();
				String directory = FileUtil.getDirectory(fileString);
				String location = directory + java.io.File.separator + importObject.getLocation();
				importObject.setLocation("file://"+location);

				List<Object> schemaObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_SCHEMA);
				List<Object> definitionObjects = importResolver.resolve(importObject, ImportResolver.RESOLVE_DEFINITION);
				buildFromImportedXSD(resourceSet, schemaObjects);
//				buildFromImportedWSDL(resourceSet, definitionObjects);
			}
		}
	}

	public void buildFromImportedXSD(ResourceSet resourceSet, List<Object> schemaObjects) throws Exception {
		Iterator<Object> iterator = schemaObjects.iterator();
		while (iterator.hasNext()) {
			XSDSchema xsdSchema = (XSDSchema) iterator.next();
			buildFromXSD(resourceSet, xsdSchema);
		}
	}

//	public void buildFromImportedWSDL(ResourceSet resourceSet, List<Object> definitionObjects) throws Exception {
//		Iterator<Object> iterator = definitionObjects.iterator();
//		while (iterator.hasNext()) {
//			Definition definition = (Definition) iterator.next();
//			
//			resourceSet.getReso().put(schemaNamespace, ePackage);
//			buildFromWSDL(resourceSet, definition);
//		}
//	}
	
}
