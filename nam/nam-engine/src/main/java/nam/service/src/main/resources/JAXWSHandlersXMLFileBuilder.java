package nam.service.src.main.resources;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JAXWSHandlersXMLFileBuilder extends AbstractFileBuilder {

	public JAXWSHandlersXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildOneWayFile() throws Exception {
		return buildFile("jaxws-handlers-service-oneway.xml");
	}
	
	public ModelFile buildTwoWayFile() throws Exception {
		return buildFile("jaxws-handlers-service-twoway.xml");
	}
	
	public ModelFile buildFile(String fileName) throws Exception {
		ModelFile modelFile = createMainResourcesFile(fileName);
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put(buildComponentsXmlOpen());
		buf.put(buildComponentsXMLContent());
		buf.put(buildComponentsXmlClose());
		return buf.get();
	}
	
	protected String buildComponentsXmlOpen() {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		buf.putLine("<handler-chains");
		buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/javaee\"");
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee\">");
		return buf.get();
	}

	protected String buildComponentsXMLContent() {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine("	<handler-chain>");
		buf.putLine("		<handler>");
		buf.putLine("			<handler-name>TransactionHandler</handler-name>");
		buf.putLine("			<handler-class>common.tx.handler.service.JaxwsTransactionInterceptor</handler-class>");
		buf.putLine("		</handler>");
		buf.putLine("");
		buf.putLine("		<handler>");
		buf.putLine("			<handler-name>ContextHandler</handler-name>");
		buf.putLine("			<handler-class>common.tx.handler.service.JaxWSHeaderContextProcessor</handler-class>");
		buf.putLine("		</handler>");
		buf.putLine("");
		buf.putLine("		<!--");
		buf.putLine("		<handler>");
		buf.putLine("			<handler-name>SeamHandler</handler-name>");
		buf.putLine("			<handler-class>org.aries.service.jaxws.JaxwsSeamContextHandler</handler-class>");
		buf.putLine("		</handler>");
		buf.putLine("		-->");
		buf.putLine("");
		buf.putLine("		<!--");
		buf.putLine("		<handler>");
		buf.putLine("			<handler-name>Instance Identifier Handler</handler-name>");
		buf.putLine("			<handler-class>org.aries.tx.client.handler.InstanceIdentifierHandler</handler-class>");
		buf.putLine("		</handler>");
		buf.putLine("		-->");
		buf.putLine("");
		buf.putLine("		<!--");
		buf.putLine("		<handler>");
		buf.putLine("			<handler-name>WSSEHeaderHandler</handler-name>");
		buf.putLine("			<handler-class>org.aries.service.jaxws.JaxwsSeamContextHandler</handler-class>");
		buf.putLine("		</handler>");
		buf.putLine("		-->");
		buf.putLine("");
		buf.putLine("		<!--");
		buf.putLine("		<handler>");
		buf.putLine("			<handler-name>WSAHeaderHandler</handler-name>");
		buf.putLine("			<handler-class>org.aries.service.jaxws.WSAHeaderHandler</handler-class>");
		buf.putLine("		</handler>");
		buf.putLine("		-->");
		buf.putLine("	</handler-chain>");
		return buf.get();
	}

	protected String buildComponentsXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</handler-chains>");
		return buf.get();
	}

}
