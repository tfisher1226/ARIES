package nam.data.src.main.java;

import nam.model.Unit;
import aries.codegen.AbstractBeanGenerator;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;


public class ImporterBeanGenerator extends AbstractBeanGenerator {

	private Unit unit;
	
	
	public ImporterBeanGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/*
	 * Interface Javadoc generation
	 * ----------------------------
	 */
	
	protected String generateJavadocContent(ModelInterface modelInterface) {
		String className = modelInterface.getClassName();
		Buf buf = new Buf();
		buf.putLine1(" * Provides mapping between {@link "+className+"Entity} to {@link "+className+"}.");
		return buf.get();
	}

	/*
	 * Class Javadoc generation
	 * ------------------------
	 */
	
	protected String generateJavadocContent(ModelClass modelClass) {
		String className = modelClass.getClassName();
		Buf buf = new Buf();
		buf.putLine(" * Provides mapping between {@link "+className+"Entity} to {@link "+className+"}.");
		return buf.get();
	}
	
}
