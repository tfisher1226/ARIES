package nam.ui.src.main.webapp.WEBINF;

import aries.codegen.AbstractFileBuilder;
import aries.generation.model.ModelFile;


public class WebXMLBuilder extends AbstractFileBuilder {

	/*
	 * TOKENS:
	 * template1
	 * template1.org
	 */
	
	/*
	 * web.xml:
	 * +version
	 * +namespace
	 * +schemaLocation
	 * +imports
	 */
	
	/*
	 * +display-name
	 * +session-timeout
	 * +servlet(s)
	 * +listener(s)
	 */
	
	/*
	 * MODULES:
	 * +Seam
	 * +JSF
	 * +Facelets
	 * +Richfaces
	 * +JBoss
	 */
	
//	public ModelFile buildFile() throws Exception {
//		ModelFile modelFile = new ModelFile();
//		modelFile.setTargetFile("components.xml");
//		modelFile.setTargetFolder("src/main/resources");
//		modelFile.setTargetPath(context.getTargetPath() + "/" + modelFile.getTargetFolder());
//		modelFile.setTextContent(getFileContent(false));
//		return modelFile;
//	}
	
}
