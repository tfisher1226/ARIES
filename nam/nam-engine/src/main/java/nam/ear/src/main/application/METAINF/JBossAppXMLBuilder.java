package nam.ear.src.main.application.METAINF;

import aries.codegen.AbstractFileBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class JBossAppXMLBuilder extends AbstractFileBuilder {

	/*
	 * TOKENS:
	 * template1
	 * template1.org
	 */

	/*
	 * jboss-web.xml:
	 * +version
	 * +namespace
	 * +schemaLocation
	 * +imports
	 */

	/*
	 * +context-root
	 * +loader-repository
	 * +security-domain(s)
	 */

	public JBossAppXMLBuilder(GenerationContext context) {
		this.context = context;
	}
	
	public ModelFile buildFile() {
		ModelFile ModelFile = new ModelFile();
		ModelFile.setVersion("5");
		return ModelFile;
	}
	
}
