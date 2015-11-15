package aries.codegen.persistence;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Field;
import nam.model.Module;
import nam.model.util.ElementUtil;

import org.apache.commons.lang.ClassUtils;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanGenerator;
import aries.codegen.util.Buf;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class EntityMapperGenerator extends AbstractBeanGenerator {

	public void generate() throws Exception {
		//TODO
	}

	public void generate(Element element) throws Exception {
		//Element element = context.getElement();
		ModelClass modelClass = new ModelClass();
		modelClass.setName(element.getType());
		modelClass.setType(element.getType());
		modelClass.setClassName(ClassUtils.getShortClassName(element.getType()+"Mapper"));
		modelClass.setPackageName(context.getModule().getGroupId()+".map");
		initializeClass(modelClass);
		generateClass(modelClass);
	}
	
	public void initializeClass(ModelClass modelClass) throws Exception {
		initializeClassAnnotations(modelClass);
		initializeImportedClasses(modelClass);
		//initializeInstanceFields(modelClass);
		//initializeInstanceMethods(modelClass);
		initializeMapperObjectAttributes(modelClass);
		modelClass.addInstanceOperation(createToModelOperation());
		modelClass.addInstanceOperation(createToEntityOperation());
		modelClass.addInstanceOperation(createToModelListOperation());
		modelClass.addInstanceOperation(createToEntityListOperation());
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		//SEAM classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
		//SEAM classAnnotations.add(AnnotationUtil.createNameAnnotation(context.getElement().getType()));
		//SEAM classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.STATELESS));
	}
	
	protected void initializeImportedClasses(ModelClass modelClass) {
		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.List");
		//SEAM modelClass.addImportedClass("org.jboss.seam.ScopeType");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
		Element element = context.getElement();
		Module module = null; //TODO context.getModule(ModuleType.DATA);
		String packageName = module.getGroupId();
		String className = NameUtil.getClassName(element.getType());
		modelClass.addImportedClass(packageName+".model."+className);
		modelClass.addImportedClass(packageName+".entity."+className+"Entity");
	}
	
	protected void initializeMapperObjectAttributes(ModelClass modelClass) {
		Element element = context.getElement();
		Module module = context.getModule();
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String type = field.getType();
			String className = NameUtil.getClassName(type);
			if (type.startsWith(module.getGroupId())) {
				String mapperName = className+"Mapper";
				String attributeName = NameUtil.uncapName(mapperName);
				ModelAttribute modelAttribute = new ModelAttribute();
				modelAttribute.setName(attributeName);
				modelAttribute.setClassName(mapperName);
				modelAttribute.setPackageName(module.getGroupId()+".map");
				modelAttribute.setModifiers(Modifier.PROTECTED);
				modelAttribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
				//modelAttribute.addAnnotation(AnnotationUtil.createInAnnotation(true));
				modelClass.addImportedClass("org.jboss.seam.annotations.In");
				modelClass.addInstanceAttribute(modelAttribute);
			}
		}
	}

	protected ModelAttribute createMapperObjectAttribute() {
		ModelAttribute modelAttribute = new ModelAttribute();
		return modelAttribute;
	}

	protected ModelOperation createToModelOperation() {
		Element element = context.getElement();
		Module module = context.getModule();
		String typeName = NameUtil.getClassName(element.getType());
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC);
		operation.setName("toModel");
		operation.addParameter(createParameter(typeName+"Entity", "entity"));

		Buf buf = new Buf();
		buf.putLine2(typeName+" model = new "+typeName+"();");
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String name = field.getName();
			String type = field.getType();
			String className = NameUtil.getClassName(type);
			String attributeName = NameUtil.capName(name);
			if (type.startsWith(module.getGroupId())) {
				String mapperName = NameUtil.uncapName(className)+"Mapper";
				String sourceCode = "model.set"+attributeName+"("+mapperName+".toModel(entity.get"+attributeName+"()));";
				buf.putLine2(sourceCode);
			} else {
				String sourceCode = "entity.set"+attributeName+"(entity.get"+attributeName+"());";
				buf.putLine2(sourceCode);
			}
		}
		
		buf.putLine2("return model;");
		operation.setResultType(typeName);
		operation.addInitialSource(buf.get());
		return operation;
	}

	protected ModelOperation createToEntityOperation() {
		Element element = context.getElement();
		Module module = context.getModule();
		String typeName = NameUtil.getClassName(element.getType());
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC);
		operation.setName("toEntity");
		operation.addParameter(createParameter(typeName, "model"));

		Buf buf = new Buf();
		buf.putLine2(typeName+"Entity entity = new "+typeName+"Entity();");
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String name = field.getName();
			String type = field.getType();
			String className = NameUtil.getClassName(type);
			String attributeName = NameUtil.capName(name);
			if (type.startsWith(module.getGroupId())) {
				String mapperName = NameUtil.uncapName(className)+"Mapper";
				String sourceCode = "entity.set"+attributeName+"("+mapperName+".toEntity(model.get"+attributeName+"()));";
				buf.putLine2(sourceCode);
			} else {
				String sourceCode = "entity.set"+attributeName+"(model.get"+attributeName+"());";
				buf.putLine2(sourceCode);
			}
		}

		buf.putLine2("return entity;");
		operation.setResultType(typeName+"Entity");
		operation.addInitialSource(buf.get());
		return operation;
	}
	
	protected ModelOperation createToModelListOperation() {
		Element element = context.getElement();
		String typeName = NameUtil.getClassName(element.getType());
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC);
		operation.setName("toModelList");
		Buf buf = new Buf();
		operation.addParameter(createParameter("List<"+typeName+"Entity>", "entityList"));
		buf.putLine2("List<"+typeName+"> modelList = new ArrayList<"+typeName+">();");
		buf.putLine2("for ("+typeName+"Entity entityObject : entityList) {");
		buf.putLine2(TAB+typeName+" modelObject = toModel(entityObject);");
		buf.putLine2(TAB+"modelList.add(modelObject);");
		buf.putLine2("}");
		buf.putLine2("return modelList;");
		operation.setResultType("List<"+typeName+">");
		operation.addInitialSource(buf.get());
		return operation;
	}
	
	protected ModelOperation createToEntityListOperation() {
		Element element = context.getElement();
		String typeName = NameUtil.getClassName(element.getType());
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC);
		operation.setName("toEntityList");
		operation.addParameter(createParameter("List<"+typeName+">", "modelList"));
		Buf buf = new Buf();
		buf.putLine2("List<"+typeName+"Entity> entityList = new ArrayList<"+typeName+"Entity>();");
		buf.putLine2("for ("+typeName+" modelObject : modelList) {");
		buf.putLine2(TAB+typeName+"Entity entityObject = toEntity(modelObject);");
		buf.putLine2(TAB+"entityList.add(entityObject);");
		buf.putLine2("}");
		buf.putLine2("return entityList;");
		operation.setResultType("List<"+typeName+"Entity>");
		operation.addInitialSource(buf.get());
		return operation;
	}

}
