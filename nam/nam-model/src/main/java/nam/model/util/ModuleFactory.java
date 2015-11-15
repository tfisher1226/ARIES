package nam.model.util;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleLevel;
import nam.model.ModuleType;
import nam.model.Project;


public class ModuleFactory {

	public static Module createPomModule() throws Exception {
		return createModule(ModuleType.POM);
	}

	public static Module createEarModule() throws Exception {
		return createModule(ModuleType.EAR);
	}

	public static Module createViewModule() throws Exception {
		return createModule(ModuleType.VIEW);
	}

	public static Module createServiceModule() throws Exception {
		return createModule(ModuleType.SERVICE);
	}

	public static Module createClientModule() throws Exception {
		return createModule(ModuleType.CLIENT);
	}

	public static Module createDataModule() throws Exception {
		return createModule(ModuleType.DATA);
	}

	public static Module createModelModule() throws Exception {
		return createModule(ModuleType.MODEL);
	}

	public static Module createTestModule() throws Exception {
		return createModule(ModuleType.TEST);
	}

	public static Module createModule(Project project, Application application, ModuleType moduleType) throws Exception {
		Module module = createModule(moduleType);
		initializeModule(project, application, module);
		return module;
	}

	public static Module createModule(Application application, ModuleType moduleType) throws Exception {
		Module module = createModule(moduleType);
		initializeModule(application, module);
		return module;
	}

	public static Module createModule(ModuleType moduleType) {
		Module module = new Module();
		module.setType(moduleType);
		module.setLevel(ModuleLevel.APPLICATION_LEVEL);
		return module;
	}

	public static void initializeModule(Project project, Application application, Module module) throws Exception {
		initializeModule(application, module);
	}

	public static void initializeModule(Application application, Module module) throws Exception {
		if (module.getGroupId() == null)
			module.setGroupId(application.getGroupId());
		if (module.getArtifactId() == null)
			module.setArtifactId(getDefaultArtifactId(application, module.getType()));
		if (module.getNamespace() == null)
			module.setNamespace(application.getNamespace());
		//List<Module> modules = ApplicationUtil.getModules(application);
		//modules.add(module);
	}

	public static String getDefaultArtifactId(Project project, ModuleType moduleType) {
		return getDefaultArtifactId(project.getName().toLowerCase(), moduleType);
	}

	public static String getDefaultArtifactId(Application application, ModuleType moduleType) {
		return getDefaultArtifactId(application.getArtifactId(), moduleType);
	}

	public static String getDefaultArtifactId(String baseName, ModuleType moduleType) {
		switch (moduleType) {
		case POM: return baseName;
		case MODEL: return baseName+"-model";
		case CLIENT: return baseName+"-client";
		case SERVICE: return baseName+"-service";
		case DATA: return baseName+"-data";
		case VIEW: return baseName+"-view";
		case TEST: return baseName+"-test";
		case WAR: return baseName+"-war";
		case EAR: return baseName+"-ear";
		default: throw new IllegalArgumentException("Unrecognized ModuleType: "+moduleType);
		}
	}
	
}
