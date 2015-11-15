package nam.data.src.main.java;

import nam.model.Unit;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


public class DAOBeanGenerator extends AbstractBeanGenerator {

//	private Repository repository;
	
	private Unit unit;
	
	
	public DAOBeanGenerator(GenerationContext context) {
		this.context = context;
	}
	
//	public void setRepository(Repository repository) {
//		this.repository = repository;
//	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	
//	/*
//	 * Operation creation methods
//	 * --------------------------
//	 */
//	
//	protected ModelOperation createGetEntityOperation(Entity entity) {
//		String entityTypeName = entity.getType();
//		String entityClassName = TypeUtil.getClassName(entityTypeName);
//		ModelOperation operation = new ModelOperation();
//		operation.setName("get"+entityClassName);
//		operation.setModifiers(Modifier.PUBLIC);
//		Element element = context.findElementByType(entity.getType());
//		Field field = context.findFieldByName(element, "id");
//		Assert.notNull(field, "Field 'Id' not found: "+entity.getType());
//		String fieldClassName = TypeUtil.getClassName(field.getType());
//		operation.addParameter(createParameter(fieldClassName, "id"));
//		operation.setResultType(entityClassName+"Entity");
//		Buf buf = new Buf();
//		buf.put2("return null;"+NL);
//		operation.addInitialSource(buf.get());
//		return operation;
//	}
//
//	protected ModelOperation createGetEntityListOperation(Entity entity) {
//		String entityTypeName = entity.getType();
//		String entityClassName = TypeUtil.getClassName(entityTypeName);
//		ModelOperation operation = new ModelOperation();
//		operation.setName("get"+entityClassName);
//		operation.setModifiers(Modifier.PUBLIC);
//		Element element = context.findElementByType(entityTypeName);
//		Field field = context.findFieldByName(element, "id");
//		Assert.notNull(field, "Field 'Id' not found: "+entityTypeName);
//		String fieldClassName = TypeUtil.getClassName(field.getType());
//		operation.addParameter(createParameter(fieldClassName, "id"));
//		operation.setResultType(entityClassName+"Entity");
//		Buf buf = new Buf();
//		buf.put2("return null;"+NL);
//		operation.addInitialSource(buf.get());
//		return operation;
//	}
//	
//	protected ModelOperation createSaveEntityOperation(Entity entity) {
//		return null;
//	}

}
