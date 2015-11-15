package nam.service.src.main.resources;

import nam.model.util.ModuleUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class RuntimeDefaultsXMLFileBuilder extends AbstractFileBuilder {

	public RuntimeDefaultsXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public ModelFile buildFile() throws Exception {
		String fileNamePrefix = ModuleUtil.getFileNamePrefix(context.getModule());
		String targetFile = NameUtil.uncapName(fileNamePrefix) + "-defaults.xml";
		ModelFile modelFile = createMainResourcesFile(targetFile);
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put(buildXmlOpen());
		buf.put(buildXMLContent());
		buf.put(buildXmlClose());
		return buf.get();
	}

	protected String buildXmlOpen() {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		buf.putLine("<checkpoints");
		buf.putLine("	xmlns=\"http://www.aries.org/validate/0.0.1\"");
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://www.aries.org/validate/0.0.1 file:/workspace/ARIES2/aries-validate/src/main/resources/schema/aries-validate-0.0.1.xsd\">");
		return buf.get();
	}

	protected String buildXMLContent() {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine("");
		return buf.get();
	}

	protected String buildXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</checkpoints>");
		return buf.get();
	}

}
