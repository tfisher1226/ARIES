package nam.ear.src.main.application.METAINF;

import nam.model.Application;
import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JBossAppXMLGenerator extends AbstractFileGenerator {

	public JBossAppXMLGenerator(GenerationContext context) {
		this.context = context;
	}

	public void generateFile(ModelFile ModelFile) throws Exception {
		generate(ModelFile, false);
	}

	public void generate(ModelFile ModelFile, boolean isTest) throws Exception {
		setTargetFile("jboss-app.xml");
		setTargetFolder("src/main/application/META-INF");
		setTargetPath(context.getTargetPath() + "/" + targetFolder);

		//Project project = context.getProject();
		Application application = context.getApplication();
		
		Buf buf = new Buf();
		if (ModelFile.getVersion().equals("5")) {
			buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buf.putLine();
			buf.putLine("<!DOCTYPE jboss-app PUBLIC \"-//JBoss//DTD Java EE Application 5.0//EN\" \"http://www.jboss.org/j2ee/dtd/jboss-app_5_0.dtd\">");
			buf.putLine();
			buf.putLine("<jboss-app>");
			buf.putLine("	<module-order>strict</module-order>");
			buf.putLine("	<loader-repository>");
			buf.putLine("		"+application.getGroupId()+":app="+application.getGroupId()+"-loader");
			buf.putLine("		<loader-repository-config>java2ParentDelegation=false</loader-repository-config>");
			buf.putLine("	</loader-repository>");
			buf.putLine("</jboss-app>");
		}
		generateFile(buf.get());
	}

}

