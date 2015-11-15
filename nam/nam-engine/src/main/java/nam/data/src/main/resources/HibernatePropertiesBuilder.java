package nam.data.src.main.resources;

import nam.model.Persistence;
import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class HibernatePropertiesBuilder extends AbstractDataLayerFileBuilder {

	public HibernatePropertiesBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelFile buildFile(boolean isTest, Persistence persistence) throws Exception {
		ModelFile modelFile = createMainResourcesFile("hibernate.properties");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		if (isTest) {
			buf.putLine("hibernate.connection.username=root");
			buf.putLine("hibernate.connection.password=");
			buf.putLine("hibernate.connection.autocommit=false");
			buf.putLine("#hibernate.connection.release_mode=auto");
			buf.putLine("#hibernate.jndi.url=jnp://localhost:1099");
			buf.putLine("#hibernate.jndi.class=org.jnp.interfaces.NamingContextFactory");
		}
		return buf.get();
	}

}
