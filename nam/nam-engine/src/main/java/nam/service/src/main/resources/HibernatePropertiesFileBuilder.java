package nam.service.src.main.resources;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class HibernatePropertiesFileBuilder extends AbstractFileBuilder {

	public HibernatePropertiesFileBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}

	public ModelFile buildForTest() throws Exception {
		return buildFile(true);
	}
	
	public ModelFile buildFile(boolean isTest) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "hibernate.properties");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.putLine("hibernate.connection.username=root");
		buf.putLine("hibernate.connection.password=");
		buf.putLine("hibernate.connection.autocommit=false");
		buf.putLine("#hibernate.connection.release_mode=auto");
		buf.putLine("#hibernate.jndi.url=jnp://localhost:1099");
		buf.putLine("#hibernate.jndi.class=org.jnp.interfaces.NamingContextFactory");
		return buf.get();
	}

}
