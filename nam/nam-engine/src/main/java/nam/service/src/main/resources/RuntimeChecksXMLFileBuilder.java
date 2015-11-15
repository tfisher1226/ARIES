package nam.service.src.main.resources;

import java.util.Iterator;
import java.util.List;

import nam.model.util.ModuleUtil;

import org.aries.util.NameUtil;
import org.aries.validate.Checkpoint;
import org.aries.validate.Checkpoints;
import org.aries.validate.Condition;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class RuntimeChecksXMLFileBuilder extends AbstractFileBuilder {

	public RuntimeChecksXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public ModelFile buildFile() throws Exception {
		String fileNamePrefix = ModuleUtil.getFileNamePrefix(context.getModule());
		String targetFile = NameUtil.uncapName(fileNamePrefix) + "-checks.xml";
		ModelFile modelFile = createMainResourcesFile(targetFile);
		modelFile.setTextContent(getFileContent(false));
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
		buf.putLine("<checkpoints");
		buf.putLine("	xmlns=\"http://www.aries.org/validate\"");
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://www.aries.org/validate file:/workspace/ARIES2/aries-validate/src/main/resources/schema/aries-validate-1.0.xsd\">");
		return buf.get();
	}

	protected String buildXmlContent() {
		Buf buf = new Buf();
		buf.putLine("");
		Checkpoints checkpoints = context.getModule().getCheckpoints();
		if (checkpoints != null) {
			Iterator<Checkpoint> iterator = checkpoints.getCheckpoints().iterator();
			while (iterator.hasNext()) {
				Checkpoint checkpoint = iterator.next();
				buf.put(buildXmlCheckpoint(checkpoint));
			}
		}
		buf.putLine("");
		return buf.get();
	}

	protected String buildXmlCheckpoint(Checkpoint checkpoint) {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine("	<checkpoint>");
		buf.putLine("		<name>"+checkpoint.getName()+"</name>");
		List<Condition> conditions = checkpoint.getConditions();
		Iterator<Condition> iterator = conditions.iterator();
		while (iterator.hasNext()) {
			Condition condition = iterator.next();
			buf.putLine("		<condition>");
			buf.putLine("			<type>"+condition.getType()+"</type>");
			if (condition.getExpression() != null)
				buf.putLine("			<expression>"+condition.getExpression()+"</expression>");
			buf.putLine("			<exception>"+condition.getException()+"</exception>");
			buf.putLine("			<message>"+condition.getMessage()+"</message>");
			buf.putLine("		</condition>");
		}
		buf.putLine("	</checkpoint>");
		buf.putLine("");
		return buf.get();
	}

	protected String buildXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</checkpoints>");
		return buf.get();
	}

}
