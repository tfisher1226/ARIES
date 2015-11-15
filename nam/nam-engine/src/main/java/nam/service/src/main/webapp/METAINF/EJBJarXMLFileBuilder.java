package nam.service.src.main.webapp.METAINF;

import nam.model.BeanType;
import nam.model.Module;
import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class EJBJarXMLFileBuilder extends AbstractFileBuilder {

	public EJBJarXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public ModelFile buildFile() throws Exception {
		ModelFile modelFile = createMainResourcesFile("META-INF", "ejb-jar.xml");
		modelFile.setTextContent(getXhtmlContent(false));
		return modelFile;
	}
	
	@SuppressWarnings("incomplete-switch")
	public String getXhtmlContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		buf.putLine("<ejb-jar"); 
		buf.putLine("	version=\"3.1\"");
		buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/javaee\""); 
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_1.xsd\">");
		buf.putLine("");
		
		Module module = context.getModule();
		buf.putLine("	<display-name>"+module.getArtifactId()+"</display-name>");
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			buf.putLine("");
			buf.putLine("	<interceptors/>");
			break;
		case SEAM:
			buf.putLine("");
			buf.putLine("	<interceptors>");
			buf.putLine("		<interceptor>");
			buf.putLine("			<interceptor-class>org.jboss.seam.ejb.SeamInterceptor</interceptor-class>");
			buf.putLine("		</interceptor>");
			buf.putLine("	</interceptors>");
			break;
		}
		
		buf.putLine("");
		buf.putLine("	<assembly-descriptor>");
		buf.putLine("		<interceptor-binding>");
		buf.putLine("			<ejb-name>*</ejb-name>");
		buf.putLine("			<interceptor-class>org.jboss.seam.ejb.SeamInterceptor</interceptor-class>");
		buf.putLine("		</interceptor-binding>");
		buf.putLine("");
		buf.putLine(" 		<!--"); 
		buf.putLine("			Interceptor to support the handling of Transactional context.");
		buf.putLine("			This will apply to all methods for all beans in deployment.");
		buf.putLine("		-->");
		buf.putLine("		<interceptor-binding>");
		buf.putLine("			<ejb-name>*</ejb-name>");
		buf.putLine("			<interceptor-class>common.tx.handler.service.EjbTransactionInterceptor</interceptor-class>");
		buf.putLine("		</interceptor-binding>");
		buf.putLine("	</assembly-descriptor>");
		buf.putLine("</ejb-jar>");
		return buf.get();
	}

}
