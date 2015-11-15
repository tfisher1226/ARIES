package nam.client.src.main.java;

import java.lang.reflect.Modifier;

import nam.client.ClientLayerHelper;
import nam.model.Service;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;


/**
 * Builds a EJB Service Client (i.e. client-side) Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientClassEJBBuilder extends AbstractManagementBeanBuilder {

	private ClientClassJMSProvider provider;

	
	public ClientClassEJBBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		provider = new ClientClassJMSProvider(context);
		initialize(provider);
	}
	
	public ModelClass build(Service service) throws Exception {
		String namespace = ClientLayerHelper.getNamespace(service);
		String interfaceName = ClientLayerHelper.getClientInterfaceName(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		String className = ClientLayerHelper.getClientInterfaceName(service) + "ProxyForEJB";
		String rootName = ClientLayerHelper.getRootName(service);
		String beanName = NameUtil.capName(className);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setNamespace(namespace);
		modelClass.addImplementedInterface("Proxy<"+interfaceName+">");
		modelClass.addImportedClass("org.aries.bean.Proxy");
		modelClass.setParentClassName("EJBProxy");
		modelClass.addImportedClass("org.aries.tx.service.ejb.EJBProxy");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeInstanceFields(modelClass, service);
		initializeInstanceConstructor(modelClass, service);
		initializeInstanceOperations(modelClass, service);
		//initializeImportedClasses(modelClass, service);
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceAttribute(createServiceAttribute_ServiceInterceptor(service));
	}

	public ModelAttribute createServiceAttribute_ServiceInterceptor(Service service) {
		String className = ClientLayerHelper.getClientInterfaceName(service) + "Interceptor";
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected void initializeInstanceConstructor(ModelClass modelClass, Service service) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addParameter(CodeUtil.createParameter_ServiceId());
		modelConstructor.addParameter(CodeUtil.createParameter_HostName());
		modelConstructor.addParameter(CodeUtil.createParameter_PortNumber());
		
		Buf buf = new Buf();
		buf.putLine2("super(serviceId, host, port);");
		buf.putLine2("createDelegate();");
		modelConstructor.addInitialSource(buf.get());
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		//if (provider.messageLayerRequested)
		createModelOperation_CreateDelegate(modelClass, service);
		createModelOperation_GetDelegate(modelClass, service);
	}

	protected void createModelOperation_CreateDelegate(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createDelegate");
		modelOperation.setModifiers(Modifier.PROTECTED);
		
		Buf buf = new Buf();
		String interceptorPrefix = NameUtil.uncapName(service.getInterfaceName());
		buf.putLine2(interceptorPrefix+"Interceptor = new "+service.getInterfaceName()+"Interceptor();");
		buf.putLine2(interceptorPrefix+"Interceptor.setProxy(this);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createModelOperation_GetDelegate(ModelClass modelClass, Service service) {
		String interfaceName = service.getInterfaceName();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDelegate");
		modelOperation.setResultType(interfaceName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		String serviceName = NameUtil.uncapName(interfaceName);
		buf.putLine2("return "+serviceName+"Interceptor;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
}


