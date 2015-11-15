package aries.codegen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import aries.generation.engine.GenerationContext;


public abstract class AbstractBuilder implements Builder {

	public static String NL = System.getProperty("line.separator");

	public static String SEP = System.getProperty("file.separator");

	protected Log log = LogFactory.getLog(getClass());
	
	protected GenerationContext context;

	
	public AbstractBuilder() {
		//do nothing
	}
	
	public AbstractBuilder(GenerationContext context) {
		setContext(context);
	}
	
	public GenerationContext getContext() {
		return context;
	}

	public void setContext(GenerationContext context) {
		this.context = context;
	}
	
}
