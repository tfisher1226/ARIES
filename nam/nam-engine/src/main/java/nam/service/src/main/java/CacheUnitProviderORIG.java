package nam.service.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Cache;
import nam.model.Field;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelField;


public class CacheUnitProviderORIG extends AbstractBeanProvider {
	
	protected ModelClass currentClass;


	public CacheUnitProviderORIG(GenerationContext context) {
		super(context);
	}

	public void setCurrentClass(ModelClass modelClass) {
		this.currentClass = modelClass;
	}

	public String generateSource_Constructor(Cache cache) {
		Buf buf = new Buf();
		List<Field> fields = ElementUtil.getFields(cache);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String className = TypeUtil.getClassName(field.getType());
			String structure = field.getStructure();
			if (structure.equals("item")) {
				//buf.putLine2(field.getName()+" = new "+className+"();");
			} else if (structure.equals("list")) {
				buf.putLine2(field.getName()+" = new ArrayList<"+className+">();");
			} else if (structure.equals("set")) {
				buf.putLine2(field.getName()+" = new HashSet<"+className+">();");
			} else if (structure.equals("map")) {
				String keyClassName = TypeUtil.getClassName(field.getKey());
				buf.putLine2(field.getName()+" = new HashMap<"+keyClassName+", "+className+">();");
			}
		}
		return buf.get();
	}
	
	public String generateSource_GetElement(ModelField modelField) {
		String fieldNameCapped = NameUtil.capName(modelField.getName());
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldTypeLocalPart = TypeUtil.getLocalPart(modelField.getType());
		
		Buf buf = new Buf();
		//buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		
		String structure = modelField.getStructure();
		if (structure.equals("item")) {
			buf.putLine2("return "+fieldNameUncapped+";");
			
		} else if (structure.equals("list") || structure.equals("set")) {
			buf.putLine2("return "+fieldNameUncapped+".get("+fieldTypeLocalPart+"Index);");
			
		} else if (structure.equals("map")) {
			String fieldKeyType = modelField.getKeyType();
			String fieldKeyTypeLocalPart = TypeUtil.getLocalPart(fieldKeyType);
			buf.putLine2("return "+fieldNameUncapped+".get("+fieldKeyTypeLocalPart+"Key);");
		}
		
		//buf.putLine2("}");
		return buf.get();
	}
	
	public String generateSource_GetElements(ModelField modelField) {
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldName = NameUtil.uncapName(fieldClassName);
		
		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		String structure = modelField.getStructure();
		if (structure.equals("map")) {
			buf.putLine3("List<"+fieldClassName+"> list = new ArrayList<"+fieldClassName+">();");
			buf.putLine3("Iterator<"+fieldClassName+"> iterator = "+fieldNameUncapped+".values().iterator();");
			buf.putLine3("while (iterator.hasNext()) {");
			buf.putLine3("	"+fieldClassName+" "+fieldName+" = iterator.next();");
			buf.putLine3("	list.add("+fieldName+");");
			buf.putLine3("}");
			buf.putLine3("return list;");
		}
		buf.putLine2("}");
		return buf.get();
	}

	public String generateSource_GetAllElements(ModelField modelField) {
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		
		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		String structure = modelField.getStructure();
		if (structure.equals("list")) {
			buf.putLine2("	return new ArrayList<"+fieldClassName+">("+fieldNameUncapped+");");
			
		} else if (structure.equals("set")) {
			buf.putLine2("	return new HashSet<"+fieldClassName+">("+fieldNameUncapped+");");
			
		} else if (structure.equals("map")) {
			String fieldKeyClassName = TypeUtil.getClassName(modelField.getKeyType());
			buf.putLine2("	return new HashMap<"+fieldKeyClassName+", "+fieldClassName+">("+fieldNameUncapped+");");
		}
		buf.putLine2("}");
		return buf.get();
	}

	public String generateSource_SetElement(ModelField modelField) {
		String fieldNameCapped = NameUtil.capName(modelField.getName());
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldTypeLocalPart = TypeUtil.getLocalPart(modelField.getType());
		
		Buf buf = new Buf();
		//buf.putLine2("synchronized (this."+fieldNameUncapped+") {");
		buf.putLine2("this."+fieldNameUncapped+" = "+fieldTypeLocalPart+";");
		//buf.putLine2("}");
		return buf.get();
	}
	
	public String generateSource_AddElement(ModelField modelField) {
		String fieldNameCapped = NameUtil.capName(modelField.getName());
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldTypeLocalPart = TypeUtil.getLocalPart(modelField.getType());
		
		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		buf.putLine2("	"+fieldNameUncapped+".add("+fieldTypeLocalPart+");");
		buf.putLine2("}");
		return buf.get();
	}
	
	public String generateSource_AddElements(ModelField modelField) {
		String fieldNameCapped = NameUtil.capName(modelField.getName());
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldTypeLocalPart = TypeUtil.getLocalPart(modelField.getType());
		
		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		buf.putLine2("	"+fieldNameUncapped+".addAll("+fieldTypeLocalPart+");");
		buf.putLine2("}");
		return buf.get();
	}
	
	public String generateSource_PutElement(ModelField modelField) {
		String fieldName = modelField.getName();
		String fieldType = modelField.getType();
		String fieldKeyType = modelField.getKeyType();

		String fieldNameCapped = NameUtil.capName(fieldName);
		String fieldNameUncapped = NameUtil.uncapName(fieldName);
		String fieldClassName = TypeUtil.getClassName(fieldType);
		String fieldPackageName = TypeUtil.getPackageName(fieldType);
		String fieldTypeLocalPart = TypeUtil.getLocalPart(fieldType);
		String fieldKeyClassName = TypeUtil.getClassName(fieldKeyType);
		String fieldKeyPackageName = TypeUtil.getPackageName(fieldKeyType);
		String fieldKeyTypeLocalPart = TypeUtil.getLocalPart(fieldKeyType);
		
		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		buf.putLine2("	"+fieldNameUncapped+".put("+fieldKeyTypeLocalPart+", "+fieldTypeLocalPart+");");
		buf.putLine2("}");
		return buf.get();
	}
	
	public String generateSource_PutElements(ModelField modelField) {
		String fieldName = modelField.getName();
		String fieldType = modelField.getType();
		String fieldKeyType = modelField.getKeyType();

		String fieldNameCapped = NameUtil.capName(fieldName);
		String fieldNameUncapped = NameUtil.uncapName(fieldName);
		String fieldClassName = TypeUtil.getClassName(fieldType);
		String fieldPackageName = TypeUtil.getPackageName(fieldType);
		String fieldTypeLocalPart = TypeUtil.getLocalPart(fieldType);
		String fieldKeyClassName = TypeUtil.getClassName(fieldKeyType);
		String fieldKeyPackageName = TypeUtil.getPackageName(fieldKeyType);
		String fieldKeyTypeLocalPart = TypeUtil.getLocalPart(fieldKeyType);
		
		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		buf.putLine2("	"+fieldNameUncapped+".putAll("+fieldTypeLocalPart+"Map);");
		buf.putLine2("}");
		return buf.get();
	}
	
	public String generateSource_RemoveElement(ModelField modelField) {
		String fieldNameCapped = NameUtil.capName(modelField.getName());
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldTypeLocalPart = TypeUtil.getLocalPart(modelField.getType());
		
		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		buf.putLine2("	"+fieldNameUncapped+".remove("+fieldTypeLocalPart+");");
		buf.putLine2("}");
		return buf.get();
	}
	
	public String generateSource_RemoveElements(ModelField modelField) {
		String fieldNameCapped = NameUtil.capName(modelField.getName());
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldTypeLocalPart = TypeUtil.getLocalPart(modelField.getType());
		String parameterName = fieldTypeLocalPart;
		
		String structure = modelField.getStructure();
		if (structure.equals("list")) {
			parameterName = fieldTypeLocalPart + "List";
			
		} else if (structure.equals("set")) {
			parameterName = fieldTypeLocalPart + "Set";
			
		} else if (structure.equals("map")) {
			parameterName = fieldTypeLocalPart + "Map";
		}

		Buf buf = new Buf();
		buf.putLine2("synchronized ("+fieldNameUncapped+") {");
		buf.putLine2("	"+fieldNameUncapped+".removeAll("+parameterName+");");
		buf.putLine2("}");
		return buf.get();
	}

}
