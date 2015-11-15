package nam.service.src.main.resources;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JAXWSPropertiesFileBuilder extends AbstractFileBuilder {

	public JAXWSPropertiesFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		ModelFile modelFile = createMainResourcesFile("jaxws.properties");
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("org.jboss.wsf.spi.deployment.DeploymentModelFactory=org.jboss.wsf.framework.deployment.ArchiveDeploymentModelFactory");
		return buf.get();
	}
	
}
