package nam.service.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Field;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelField;


public class DataUnitProvider extends AbstractBeanProvider {
	
	protected ModelClass currentClass;


	public DataUnitProvider(GenerationContext context) {
		super(context);
	}

	public void setCurrentClass(ModelClass modelClass) {
		this.currentClass = modelClass;
	}

	public String generateSource_Constructor(Unit unit) {
		Buf buf = new Buf();
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			buf.put(generateSource_Constructor(unit, element));
			
		}
		return buf.get();
	}
	
	public String generateSource_Constructor(Unit unit, Element element) {
		Buf buf = new Buf();
		List<Field> fields = ElementUtil.getFields(element);
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
		Buf buf = new Buf();
		String structure = modelField.getStructure();
		if (structure.equals("item")) {
			
		} else if (structure.equals("list") || structure.equals("set")) {
			
		} else if (structure.equals("map")) {
			String fieldType = modelField.getType();
			String fieldTypeLocalPart = TypeUtil.getLocalPart(fieldType);
			buf.putLine2("return getElement("+fieldTypeLocalPart+"Key);");
		}
		return buf.get();
	}
	
	public String generateSource_GetElements(ModelField modelField) {
		Buf buf = new Buf();
		String structure = modelField.getStructure();
		if (structure.equals("map")) {
			String fieldType = modelField.getType();
			String fieldTypeLocalPart = TypeUtil.getLocalPart(fieldType);
			buf.putLine2("return getElements("+fieldTypeLocalPart+"Keys);");
		}
		return buf.get();
	}

	public String generateSource_GetAllElements(ModelField modelField) {
		Buf buf = new Buf();
		String structure = modelField.getStructure();
		if (structure.equals("list")) {
			
		} else if (structure.equals("set")) {
			
		} else if (structure.equals("map")) {
			buf.putLine2("return getElements();");
		}
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
		String fieldKeyTypeLocalPart = TypeUtil.getLocalPart(modelField.getKeyType());
		
		Buf buf = new Buf();
		buf.putLine2("putElement("+fieldKeyTypeLocalPart+", "+fieldTypeLocalPart+"Map);");
		return buf.get();
	}
	
	public String generateSource_AddElements(ModelField modelField) {
		String fieldNameCapped = NameUtil.capName(modelField.getName());
		String fieldNameUncapped = NameUtil.uncapName(modelField.getName());
		String fieldClassName = TypeUtil.getClassName(modelField.getType());
		String fieldTypeLocalPart = TypeUtil.getLocalPart(modelField.getType());
		
		Buf buf = new Buf();
		buf.putLine2("putElements("+fieldTypeLocalPart+"Map);");
		return buf.get();
	}
	
	public String generateSource_SetElements(ModelField modelField) {
		String fieldName = modelField.getName();
		String fieldType = modelField.getType();
		String fieldTypeLocalPart = TypeUtil.getLocalPart(fieldType);
		
		Buf buf = new Buf();
		buf.putLine2("putElements("+fieldTypeLocalPart+"Map);");
		return buf.get();
	}
	
	public String generateSource_PutElement(ModelField modelField) {
		String fieldType = modelField.getType();
		String fieldTypeLocalPart = TypeUtil.getLocalPart(fieldType);
		
		Buf buf = new Buf();
		buf.putLine2("putElement("+fieldTypeLocalPart+"Key, "+fieldTypeLocalPart+");");
		return buf.get();
	}
	
	public String generateSource_PutElements(ModelField modelField) {
		String fieldName = modelField.getName();
		String fieldType = modelField.getType();
		String fieldTypeLocalPart = TypeUtil.getLocalPart(fieldType);
		
		Buf buf = new Buf();
		buf.putLine2("putElements("+fieldTypeLocalPart+"Map);");
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
