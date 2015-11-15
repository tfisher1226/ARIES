package aries.codegen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import aries.generation.engine.GenerationContext;


public abstract class AbstractGenerator {

	protected Log log = LogFactory.getLog(getClass());
	
	public static String TEMPLATE_WORKSPACE = "c:/workspace/ARIES2";

	//public static String TEMPLATE_HOME = TEMPLATE_WORKSPACE+"/nam/tokens";

	public static String TARGET_HOME = TEMPLATE_WORKSPACE+"/nam-generated";
	
	public static String TOKEN_HOME = "tokens";

	public static String NL = "\n";
	//public static String NL = System.getProperty("line.separator");

	public static String SEP = System.getProperty("file.separator");

	public static String SP = " ";
	
	public static String TAB = "\t";
	
	public static String SEMICOLON = ";";

	
	protected GenerationContext context;

//	protected boolean generatingTests;
	
	
	public GenerationContext getContext() {
		return context;
	}

	public void setContext(GenerationContext context) {
		this.context = context;
	}

//	public boolean isGeneratingTests() {
//		return generatingTests;
//	}
//
//	public void setGeneratingTests(boolean generatingTests) {
//		this.generatingTests = generatingTests;
//	}

}
