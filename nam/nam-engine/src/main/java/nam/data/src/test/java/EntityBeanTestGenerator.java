package nam.data.src.test.java;

import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a JPA Entity Bean from an Aries Element or Aries Namespace;
 * 
 * Model generation properties:
 * <ul>
 * <li>generateJavadoc</li>
 * <li>overridePackageNameFromXSD</li>
 * <li>generateTableAnnotation</li>
 * <li>generateLowercaseTableAndColumnNames</li>
 * <li>addUnderscoresInTableAndColumnNames</li>
 * <li>generateFieldLevelJPAAnnotations</li>
 * <li>wrapEnumWithDataType</li>
 * </ul>
 * 
 * @author tfisher
 */
public class EntityBeanTestGenerator extends AbstractBeanGenerator {

	public EntityBeanTestGenerator(GenerationContext context) {
		this.context = context;
	}
	
}
