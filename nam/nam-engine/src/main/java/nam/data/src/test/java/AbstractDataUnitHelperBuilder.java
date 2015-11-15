package nam.data.src.test.java;

import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;


public abstract class AbstractDataUnitHelperBuilder extends AbstractBeanBuilder {

	public AbstractDataUnitHelperBuilder(GenerationContext context) {
		super(context);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass) {
		modelClass.addClassAnnotation(AnnotationUtil.createStatefulAnnotation());
		modelClass.addClassAnnotation(AnnotationUtil.createConcurrencyManagementAnnotation());
		modelClass.addClassAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
	}

	protected void initializeImportedClasses(ModelClass modelClass) {
		modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
		modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
		modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRES_NEW");
		modelClass.addStaticImportedClass("org.junit.Assert.fail");
	}
		
}
