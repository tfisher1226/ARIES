package nam.client.src.main.resources.METAINF;

import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class BeansXMLBuilder extends AbstractDataLayerFileBuilder {

	public BeansXMLBuilder(GenerationContext context) {
		super(context);
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}
	
	public ModelFile buildFile(boolean isTest) throws Exception {
		ModelFile modelFile = createMainResourcesFile("META-INF", "beans.xml");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put(generateXmlOpen(isTest));
		buf.put(generateXMLContent(isTest));
		buf.put(generateXmlClose());
		return buf.get();
	}

	protected String generateXmlOpen(boolean isTest) {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine();
		buf.putLine("<beans"); 
		buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/javaee\""); 
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://docs.jboss.org/cdi/beans_1_0.xsd\">");
		return buf.get();
	}

	protected String generateXMLContent(boolean isTest) {
		Buf buf = new Buf();
		//buf.putLine2("");
		return buf.get();
	}

	protected String generateXmlClose() {
		Buf buf = new Buf();
		buf.putLine();
		buf.putLine("</beans>");
		return buf.get();
	}

}
