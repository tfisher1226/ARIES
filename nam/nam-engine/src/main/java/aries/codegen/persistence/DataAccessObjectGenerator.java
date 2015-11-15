package aries.codegen.persistence;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Entity;
import nam.model.Field;
import nam.model.Unit;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanGenerator;
import aries.codegen.util.Buf;
import aries.codegen.util.GenerateUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;


public class DataAccessObjectGenerator extends AbstractBeanGenerator {

//	private Repository repository;
	
	private Unit unit;
	
	
	public DataAccessObjectGenerator(GenerationContext context) {
		this.context = context;
	}
	
//	public void setRepository(Repository repository) {
//		this.repository = repository;
//	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public void generate() throws Exception {
		generateAccessObjectInterface();
		generateAccessObjectInstance();
	}


	/*
	 * Access object interface generation
	 * ----------------------------------
	 */
	
	public void generateAccessObjectInterface() throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setClassName(NameUtil.capName(unit.getName())+"Dao");
		modelInterface.setPackageName(context.getModule().getGroupId()+".dao");
		initializeInterface(modelInterface);
		generateInterface(modelInterface);
	}

	protected void initializeInterface(ModelInterface modelInterface) throws Exception {
		//initializeInterfaceAnnotations(modelInterface);
		initializeImportedClasses(modelInterface);
//		initializeInterfaceMethods(modelInterface);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface) throws Exception {
	}

//	protected void initializeInterfaceMethods(ModelInterface modelInterface) throws Exception {
//		List<Entity> entities = UnitUtil.getEntities(unit);
//		Iterator<Entity> iterator = entities.iterator();
//		while (iterator.hasNext()) {
//			Entity entity = iterator.next();
//			initializeInterfaceMethods(modelInterface, entity);
//		}
//	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Entity entity) throws Exception {
		modelInterface.addInstanceOperation(createGetEntityOperation(entity));
		//modelInterface.addInterfaceOperation(createGetEntityListOperation(entity));
		//modelInterface.addInterfaceOperation(createSaveEntityOperation(entity));
		String packageName = context.getModule().getGroupId()+".entity";
		modelInterface.addImportedClass(packageName+"."+entity.getType()+"Entity");
	}
	
	protected void initializeInterfaceMethods(ModelInterface modelInterface, List<Entity> entities) throws Exception {
		Iterator<Entity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			modelInterface.addInstanceOperation(createGetEntityOperation(entity));
			//modelInterface.addInterfaceOperation(createGetEntityListOperation(entity));
			//modelInterface.addInterfaceOperation(createSaveEntityOperation(entity));
			String packageName = context.getModule().getGroupId()+".model";
			modelInterface.addImportedClass(packageName+"."+entity.getType());
		}
	}
	

	/*
	 * Access object instance generation
	 * ---------------------------------
	 */
	
	public void generateAccessObjectInstance() throws Exception {
		ModelClass modelClass = new ModelClass();
		modelClass.setName(unit.getName());
		String packageName = context.getModule().getGroupId()+".dao";
		String interfaceName = NameUtil.capName(unit.getName())+"Dao";
		modelClass.setPackageName(packageName);
		modelClass.setClassName(interfaceName+"Impl");
		modelClass.setType(TypeUtil.getTypeFromPackageAndClass(packageName, interfaceName+"Impl"));
		modelClass.addImplementedInterface(packageName+"."+interfaceName);
		initializeClass(modelClass);
		generateClass(modelClass);
	}

	public void initializeClass(ModelClass modelClass) throws Exception {
		initializeClassAnnotations(modelClass);
		initializeImportedClasses(modelClass);
		//initializeInstanceFields(modelClass);
//		initializeInstanceMethods(modelClass);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		//SEAM classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
		//SEAM classAnnotations.add(AnnotationUtil.createNameAnnotation(modelClass.getName()));
		//SEAM classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.STATELESS));
	}
	
	protected void initializeImportedClasses(ModelClass modelClass) {
		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.List");
		//SEAM modelClass.addImportedClass("org.jboss.seam.ScopeType");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
	}
	
//	protected void initializeInstanceMethods(ModelClass modelClass) throws Exception {
//		List<Entity> entities = UnitUtil.getEntities(unit);
//		initializeInstanceMethods(modelClass, entities);
//	}

	protected void initializeInstanceMethods(ModelClass modelClass, List<Entity> entities) throws Exception {
		Iterator<Entity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			initializeInstanceMethods(modelClass, entity);
		}
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Entity entity) throws Exception {
		modelClass.addInstanceOperation(createGetEntityOperation(entity));
		//modelInterface.addInterfaceOperation(createGetEntityListOperation(entity));
		//modelInterface.addInterfaceOperation(createSaveEntityOperation(entity));
		String packageName = context.getModule().getGroupId()+".entity";
		modelClass.addImportedClass(packageName+"."+entity.getType()+"Entity");
	}

	
	/*
	 * Operation creation methods
	 * --------------------------
	 */
	
	protected ModelOperation createGetEntityOperation(Entity entity) {
		String entityTypeName = entity.getType();
		String entityClassName = TypeUtil.getClassName(entityTypeName);
		ModelOperation operation = new ModelOperation();
		operation.setName("get"+entityClassName);
		operation.setModifiers(Modifier.PUBLIC);
		Element element = context.findElementByType(entityTypeName);
		Field field = context.findFieldByName(element, "id");
		Assert.notNull(field, "Field 'Id' not found: "+entity.getType());
		String fieldClassName = TypeUtil.getClassName(field.getType());
		//operation.addParameter(createParameter(fieldClassName, "id"));
		operation.setResultType(entityClassName+"Entity");
		Buf buf = new Buf();
		buf.put2("return null;"+NL);
		operation.addInitialSource(buf.get());
		return operation;
	}

	protected ModelOperation createGetEntityListOperation(Entity entity) {
		String entityTypeName = entity.getType();
		String entityClassName = TypeUtil.getClassName(entityTypeName);
		ModelOperation operation = new ModelOperation();
		operation.setName("get"+entityClassName);
		operation.setModifiers(Modifier.PUBLIC);
		Element element = context.findElementByType(entityTypeName);
		Field field = context.findFieldByName(element, "id");
		Assert.notNull(field, "Field 'Id' not found: "+entityTypeName);
		String fieldClassName = TypeUtil.getClassName(field.getType());
		//operation.addParameter(createParameter(fieldClassName, "id"));
		operation.setResultType(entityClassName+"Entity");
		Buf buf = new Buf();
		buf.put2("return null;"+NL);
		operation.addInitialSource(buf.get());
		return operation;
	}
	
	protected ModelOperation createSaveEntityOperation(Entity entity) {
		return null;
	}

}
