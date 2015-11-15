package nam.data;

import java.lang.reflect.Modifier;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Service;
import nam.model.Unit;

import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelReference;


public class DataLayerFactory {

	public static GenerationContext context;
	
	public static ModelAttribute createRepositoryAttribute(Service service, Unit unit) {
		return createRepositoryAttribute(service.getNamespace(), unit);
	}
	
	public static ModelAttribute createRepositoryAttribute(Process process, Unit unit) {
		return createRepositoryAttribute(process.getNamespace(), unit);
	}
	
	public static ModelAttribute createRepositoryAttribute(String namespace, Unit unit) {
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String unitNameCapped = NameUtil.capName(unit.getName());
		String unitNameUncapped = NameUtil.uncapName(unit.getName());

		String className = unitNameCapped+"Repository";
		String name = unitNameUncapped+"Repository";
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PROTECTED);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(name);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
			break;
		case SEAM:
			attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
			//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(elementQualifiedName, 2);
			//attribute.addAnnotation(AnnotationUtil.createInAnnotation(true, contextPrefix+"."+name));
		}
		
		attribute.addImportedClass(packageName+"."+className);
		return attribute;
	}

	public static ModelReference createEntityManagerReference(Element element) {
		ModelReference reference = new ModelReference();
		reference.setModifiers(Modifier.PROTECTED);
		reference.setPackageName("javax.persistence");
		reference.setClassName("EntityManager");
		reference.setName("entityManager");
		reference.setStructure("item");
		reference.setGenerateGetter(false);
		reference.setGenerateSetter(false);
		return reference;
	}
	
}
