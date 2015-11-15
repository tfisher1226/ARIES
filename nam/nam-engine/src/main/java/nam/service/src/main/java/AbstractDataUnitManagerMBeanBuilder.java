package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.List;

import nam.model.Module;
import nam.model.Project;
import nam.model.Unit;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;


/**
 * Builds a DataUnit Manager {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * <h3>Properties</h3>
 * The following properties can be used to configure execution of this builder: 
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * <h3>Dependencies</h3>
 * Execution of this builder must come after the following builders: 
 * <ul>
 * <li>ProcessClassBuilder</li>
 * </ul>
 * 
 * @author tfisher
 */
public abstract class AbstractDataUnitManagerMBeanBuilder extends AbstractDataUnitManagerBuilder {

	public AbstractDataUnitManagerMBeanBuilder(GenerationContext context) {
		super(context);
	}
	
	protected void initializeImportedClasses(ModelInterface modelInterface) throws Exception {
		modelInterface.addImportedClass("java.util.Collection");
		modelInterface.addImportedClass("java.util.List");
		modelInterface.addImportedClass("javax.management.MXBean");
	}

	protected void initializeInterfaceAnnotations(ModelInterface modelInterface) throws Exception {
		List<ModelAnnotation> classAnnotations = modelInterface.getInterfaceAnnotations();
		classAnnotations.add(AnnotationUtil.createMXBeanAnnotation());
	}

	protected void initializeInstanceFields(ModelInterface modelInterface, String suffix) throws Exception {
		modelInterface.addInstanceAttribute(createMBeanNameConstant(suffix));
	}

	protected ModelAttribute createMBeanNameConstant(String suffix) {
		Project project = context.getProject();
		String domain = project.getDomain();
		if (context != null && !context.getPropertyAsBoolean("appendProjectToGroupId"))
			domain += "." + project.getName();
		//domain = domain.replace("-", ".");
		
		String name = NameUtil.capName(modelUnit.getName());
		if (name.endsWith("MBean"))
			name = name.replace("MBean", "");
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		attribute.setClassName(String.class.getName());
		attribute.setName("MBEAN_NAME");
		attribute.setDefault("\""+domain+"."+suffix+":name="+name+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
}
