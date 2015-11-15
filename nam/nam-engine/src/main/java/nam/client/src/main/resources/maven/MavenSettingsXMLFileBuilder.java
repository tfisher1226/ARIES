package nam.client.src.main.resources.maven;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class MavenSettingsXMLFileBuilder extends AbstractFileBuilder {

	public MavenSettingsXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false, "settings.xml");
	}
	
	public ModelFile buildForTest() throws Exception {
		return buildFile(true, "settings.xml");
	}
	
	public ModelFile buildFile(boolean isTest, String fileName) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "maven", fileName);
		modelFile.setTextContent(getFileContent(isTest));
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
		buf.putLine("<settings"); 
		buf.putLine("	xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\"");
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">");
		return buf.get();
	}

	protected String buildXMLContent() {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine("	<localRepository>c:/Users/tfisher/.m2/repository</localRepository>");
		buf.putLine("");
		buf.putLine("	<mirrors>");
		buf.putLine("		<mirror>");
		buf.putLine("			<!--This sends everything else to /public -->");
		buf.putLine("			<id>nexus</id>");
		buf.putLine("			<mirrorOf>*</mirrorOf>");
		buf.putLine("			<url>http://localhost:8081/nexus/content/groups/public</url>");
		buf.putLine("		</mirror>");
		buf.putLine("	</mirrors>");
		buf.putLine("");
		buf.putLine("	<profiles>");
		buf.putLine("		<profile>");
		buf.putLine("			<id>nexus</id>");
		buf.putLine("			<!--Enable snapshots for the built in central repo to direct -->");
		buf.putLine("			<!--all requests to nexus via the mirror -->");
		buf.putLine("			<repositories>");
		buf.putLine("				<repository>");
		buf.putLine("					<id>central</id>");
		buf.putLine("					<url>http://central</url>");
		buf.putLine("					<releases>");
		buf.putLine("						<enabled>true</enabled>");
		buf.putLine("					</releases>");
		buf.putLine("					<snapshots>");
		buf.putLine("						<enabled>true</enabled>");
		buf.putLine("					</snapshots>");
		buf.putLine("				</repository>");
		buf.putLine("			</repositories>");
		buf.putLine("");
		buf.putLine("			<pluginRepositories>");
		buf.putLine("				<pluginRepository>");
		buf.putLine("					<id>central</id>");
		buf.putLine("					<url>http://central</url>");
		buf.putLine("					<releases>");
		buf.putLine("						<enabled>true</enabled>");
		buf.putLine("					</releases>");
		buf.putLine("					<snapshots>");
		buf.putLine("						<enabled>true</enabled>");
		buf.putLine("					</snapshots>");
		buf.putLine("				</pluginRepository>");
		buf.putLine("			</pluginRepositories>");
		buf.putLine("		</profile>");
		buf.putLine("	</profiles>");
		buf.putLine("");
		buf.putLine("	<activeProfiles>");
		buf.putLine("		<activeProfile>nexus</activeProfile>");
		buf.putLine("	</activeProfiles>");
		buf.putLine("");
		return buf.get();
	}

	protected String buildXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</settings>");
		return buf.get();
	}

}
