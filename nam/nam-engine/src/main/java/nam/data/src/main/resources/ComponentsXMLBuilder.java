package nam.data.src.main.resources;

import nam.model.Persistence;
import nam.model.Unit;
import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


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


public class ComponentsXMLBuilder extends AbstractDataLayerFileBuilder {

	private static final int SEAM_VERSION_2 = 2;

	private static final int SEAM_VERSION_3 = 3;

	private int seamVersion = 2;

	
	public ComponentsXMLBuilder(GenerationContext context) {
		super(context);
	}

	public ModelFile buildFile(boolean isTest, Persistence persistence) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, "components.xml");
		modelFile.setTextContent(getFileContent(isTest, persistence));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest, Persistence persistence) throws Exception {
		Buf buf = new Buf();
		buf.put(generateComponentsXmlOpen(isTest));
		buf.put(generateComponentsXMLContent(isTest));
		buf.put(generateComponentsXmlClose());
		return buf.get();
	}

	protected String generateComponentsXmlOpen(boolean isTest) {
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

	protected String generateComponentsXMLContent(boolean isTest) {
		Buf buf = new Buf();
		buf.putLine();
		buf.putLine("	<core:manager");
		buf.putLine("		conversation-timeout=\"120000\"");
		buf.putLine("		concurrent-request-timeout=\"500\"");
		buf.putLine("		conversation-id-parameter=\"conversationId\"");
		buf.putLine("		parent-conversation-id-parameter=\"parentConversationId\"");
		buf.putLine("		default-flush-mode=\"MANUAL\" />");
		
		//if (isTest) {
			buf.putLine("");
			buf.putLine("	<core:init");
			buf.putLine("		debug=\"false\"");
			buf.putLine("		jndi-pattern=\"deploy/#{ejbName}/local\"");
			buf.putLine("		transaction-management-enabled=\"true\" />");
			buf.putLine("");
			buf.putLine("	<transaction:ejb-transaction />");
		//}
		return buf.get();
	}

	protected String generateComponentsXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</components>");
		return buf.get();
	}

}
