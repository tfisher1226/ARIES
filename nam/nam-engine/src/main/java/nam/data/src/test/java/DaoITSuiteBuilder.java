package nam.data.src.test.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Namespace;
import nam.model.Unit;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;


public class DaoITSuiteBuilder extends AbstractBeanBuilder {

	public DaoITSuiteBuilder(GenerationContext context) {
		super(context);
	}

	protected void initialize() {
	}
	
	public ModelClass buildClass(Unit unit) throws Exception {
		return buildClass(unit, unit.getNamespace());
	}
	
	protected ModelClass buildClass(Unit unit, Namespace namespace) throws Exception {
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String className = NameUtil.capName(unit.getName()) + "DaoITSuite";
		String beanName = NameUtil.uncapName(className);

		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		//modelClass.setType(daoType);
		initializeClass(modelClass, unit, namespace);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Unit unit, Namespace namespace) throws Exception {
		//List<Element> elements = UnitUtil.getFunctionalElementsBasedOnName(unit);
		List<Element> elements = UnitUtil.getElements(unit);
		initializeImportedClasses(modelClass, elements);
		initializeClassAnnotations(modelClass, elements);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, List<Element> elements) {
		modelClass.addImportedClass("org.junit.runner.RunWith");
		modelClass.addImportedClass("org.junit.runners.Suite");
	}

	protected void initializeClassAnnotations(ModelClass modelClass, List<Element> elements) throws Exception {
		modelClass.addClassAnnotation(AnnotationUtil.createJUnitSuiteRunWithAnnotation());
		modelClass.addClassAnnotation(AnnotationUtil.createJUnitSuiteClassesAnnotation(createElementClassListForSuiteAnnotation(elements)));
	}

	public List<String> createElementClassListForSuiteAnnotation(List<Element> elements) {
		List<String> elementClasses = new ArrayList<String>();
		Iterator<Element> iterator = elements.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Element element = iterator.next();
			String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
			elementClasses.add(daoInterfaceName+"IT.class");
		}
		return elementClasses;
	}
	
}
