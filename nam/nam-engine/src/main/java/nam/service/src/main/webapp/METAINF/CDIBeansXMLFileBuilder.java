package nam.service.src.main.webapp.METAINF;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class CDIBeansXMLFileBuilder extends AbstractFileBuilder {

	public CDIBeansXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false, "beans.xml");
	}

	public ModelFile buildForTest() throws Exception {
		return buildFile(true, "beans.xml");
	}

	public ModelFile buildFile(boolean isTest, String fileName) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "META-INF", fileName);
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put(buildXmlOpen());
		buf.put(buildXmlContent());
		buf.put(buildXmlClose());
		return buf.get();
	}
	
	protected String buildXmlOpen() {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		buf.putLine("<beans"); 
		buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/javaee\""); 
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\">");
		return buf.get();
	}

	protected String buildXmlContent() {
		Buf buf = new Buf();
		buf.putLine("");
		return buf.get();
	}

	protected String buildXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</beans>");
		return buf.get();
	}

}
