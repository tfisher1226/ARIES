package nam.ui.src.main.java;

import nam.ProjectLevelHelper;
import nam.model.Module;

import org.apache.tools.ant.types.FilterSet;
import org.aries.util.NameUtil;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;


public class UtilClassGenerator extends AbstractFileGenerator {

	public UtilClassGenerator(GenerationContext context) {
		this.context = context;
	}

	public void generate() throws Exception {
		generateFile("src/main/java", "AdminHelper.java");
		generateFile("src/main/java", "AdminManager.java");
		generateFile("src/main/java", "PermissionResolver.java");
		generateFile("src/main/java", "SecurityGuard.java");
		generateFile("src/main/java", "SecurityManager.java");
		//generateFile("src/main/java", "ServletListener.java");
		generateFile("src/main/java", "TracingListener.java");
	}
	
	public void generateFile(String folderName, String fileName) throws Exception {
		String projectName = context.getProjectName();
		Module module = context.getModule();
		//Application application = context.getApplication();
		String packageName = ProjectLevelHelper.getPackageName(module.getNamespace());
		String packagePath = packageName.replace(".", "/");

		setSourceFile(fileName);
		setSourceFolder(NameUtil.normalizePath(folderName+"/template1_package/ui"));
		setTargetFile(fileName);
		setTargetFolder(NameUtil.normalizePath(folderName+"/"+packagePath+"/ui"));

		FilterSet filterSet = new MyFilterSet();
		filterSet.addFilter("template1_package", packageName);
		filterSet.addFilter("template1_property_name_prefix", projectName);
		generateFile(filterSet);
	}

}
