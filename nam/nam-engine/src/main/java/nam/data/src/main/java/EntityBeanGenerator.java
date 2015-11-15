package nam.data.src.main.java;

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
public class EntityBeanGenerator extends AbstractBeanGenerator {

	public EntityBeanGenerator(GenerationContext context) {
		this.context = context;
	}


//	//force to primitive boolean for EntityBean's
//	protected String getFieldType(ModelField modelField) {
//		String className = NameUtil.getSimpleName(modelField.getClassName());
//		String keyClassName = NameUtil.getSimpleName(modelField.getKeyClassName());
//		if (className.equals("Boolean"))
//			className = "boolean";
//		if (keyClassName != null && keyClassName.equals("Boolean"))
//			keyClassName = "boolean";
//		return FieldUtil.getFieldType(modelField.getStructure(), className, keyClassName);
//	}
	
}
