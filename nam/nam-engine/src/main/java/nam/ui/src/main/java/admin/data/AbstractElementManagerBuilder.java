package nam.ui.src.main.java.admin.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.model.Element;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Type;
import nam.model.src.main.java.AbstractElementClassBuilder;
import nam.model.util.ProjectUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


public abstract class AbstractElementManagerBuilder extends AbstractElementClassBuilder {

	public AbstractElementManagerBuilder(GenerationContext context) {
		super(context);
	}

	protected String getPackageName(Type type) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		//if (packageName.startsWith("admin"))
		//	System.out.println();
		return packageName;
	}

	public List<ModelClass> buildClasses(Project project, Module module) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Set<Information> informationSets = new LinkedHashSet<Information>();
		informationSets.addAll(ProjectUtil.getInformationBlocksFromModules(project));
		Information moduleInformation = module.getInformation();
		if (moduleInformation != null)
			informationSets.add(moduleInformation);
		Iterator<Information> iterator = informationSets.iterator();
		while (iterator.hasNext()) {
			Information information = iterator.next();
			modelClasses.addAll(buildClasses(project, information));
		}
//		//modelClasses.addAll(buildClasses(project, module.getInformation()));
//		Persistence persistenceBlock = module.getPersistence();
//		if (persistenceBlock != null)
//			modelClasses.addAll(buildClasses(project, persistenceBlock));
		return modelClasses;
	}
	
	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		//super.initializeClass(modelClass, element);
		initializeImportedClasses(modelClass);
		initializeStaticFields(modelClass);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeStaticFields(ModelClass modelClass) throws Exception {
		modelClass.addStaticAttribute(createStaticLoggerAttribute(modelClass.getClassName()));
		modelClass.addStaticAttribute(createStaticPropertyPrefixLoggerAttribute());
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Element element) throws Exception {
	}

}
