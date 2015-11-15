package nam.data.src.main.java;

import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


public class QueryBeanGenerator extends AbstractBeanGenerator {

	private QueryBeanProvider provider;
	
	
	public QueryBeanGenerator(GenerationContext context) {
		this.context = context;
		initialize();
	}

	protected void initialize() {
		provider = new QueryBeanProvider(context);
	}
	
}
