package nam.service.src.main.resources;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class SeamComponentsXMLFileBuilder extends AbstractFileBuilder {

	private static final int SEAM_VERSION_2 = 2;

	private static final int SEAM_VERSION_3 = 3;

	private int seamVersion = SEAM_VERSION_2;
	
	
	public SeamComponentsXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}

	/*
	 * TOKENS:
	 * template1
	 * template1.org
	 */

	/*
	 * components.xml:
	 * +version
	 * +namespace
	 * +schemaLocation
	 * +imports
	 */
	
	/*
	 * Authentication settings:
	 * authentication-method (bean.method)
	 * remember-me (boolean)
	 */
	
	/*
	 * Identity-store settings:
	 * user-class (classname)
	 * role-class (classname)
	 */
	
	/*
	 * Component specifications:
	 * service component proxies
	 */

	/*
	 * Transaction specifications:
	 */

	/*
	 * Event specifications:
	 */
	
	/*
	 * Cache-control-filter settings:
	 */

	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}

	public ModelFile buildForTest() throws Exception {
		return buildFile(true);
	}
	
	public ModelFile buildFile(boolean isTest) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "components.xml");
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put(generateComponentsXmlOpen());
		buf.put(generateComponentsXMLContent());
		buf.put(generateComponentsXmlClose());
		return buf.get();
	}

	protected String generateComponentsXmlOpen() {
		Buf buf = new Buf();
		switch (seamVersion) {
		case SEAM_VERSION_2:
			buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buf.putLine();
			buf.putLine("<components");
			buf.putLine("	xmlns=\"http://jboss.com/products/seam/components\"");
			buf.putLine("	xmlns:core=\"http://jboss.com/products/seam/core\"");
			buf.putLine("	xmlns:security=\"http://jboss.com/products/seam/security\"");
			buf.putLine("	xmlns:persistence=\"http://jboss.com/products/seam/persistence\"");
			buf.putLine("	xmlns:transaction=\"http://jboss.com/products/seam/transaction\"");
			buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			buf.putLine("	xsi:schemaLocation=");
			buf.putLine("		\"http://jboss.com/products/seam/core http://jboss.com/products/seam/core-2.2.xsd"); 
			buf.putLine("		 http://jboss.com/products/seam/security http://jboss.com/products/seam/security-2.2.xsd");
			buf.putLine("		 http://jboss.com/products/seam/persistence http://jboss.com/products/seam/persistence-2.2.xsd"); 
			buf.putLine("		 http://jboss.com/products/seam/transaction http://jboss.com/products/seam/transaction-2.2.xsd"); 
			buf.putLine("		 http://jboss.com/products/seam/components http://jboss.com/products/seam/components-2.2.xsd\">");
			break;
		case SEAM_VERSION_3:
			//not yet
			break;
		}
		return buf.get();
	}

	protected String generateComponentsXMLContent() {
		Buf buf = new Buf();
		buf.putLine("");
		buf.putLine("	<core:manager");
		buf.putLine("		conversation-timeout=\"120000\"");
		buf.putLine("		concurrent-request-timeout=\"500\"");
		buf.putLine("		conversation-id-parameter=\"cid\" />");
		
		if (context.isOptionEnabledEJB()) {
			buf.putLine("");
			buf.putLine("	<core:init"); 
			buf.putLine("		debug=\"false\""); 
			buf.putLine("		jndi-pattern=\"deploy/#{ejbName}/local\"");
			buf.putLine("		transaction-management-enabled=\"true\" />");	
			buf.putLine("");
			buf.putLine("	<transaction:ejb-transaction />");
		}
		return buf.get();
	}

	protected String generateComponentsXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</components>");
		return buf.get();
	}

}
