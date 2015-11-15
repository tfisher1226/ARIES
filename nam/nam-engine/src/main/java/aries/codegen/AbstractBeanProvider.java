package aries.codegen;

import nam.model.Element;
import nam.model.Field;
import nam.model.Reference;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;

import org.aries.util.ClassUtil;

import aries.generation.engine.GenerationContext;



public abstract class AbstractBeanProvider implements BeanProvider {
	
	protected GenerationContext context;

	
	public AbstractBeanProvider(GenerationContext context) {
		this.context = context;
	}
	
	
	public boolean isImportRequiredForField(Element element, Field field) {
		if (field instanceof Reference == false)
			return false;
		if (FieldUtil.isInverse(field))
			return false;
		String fieldType = field.getType();
		String className = TypeUtil.getClassName(fieldType);
		if (ClassUtil.isJavaPrimitiveType(className))
			return false;
		return true;
	}
	
}
