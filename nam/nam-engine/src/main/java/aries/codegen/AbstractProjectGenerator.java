package aries.codegen;

import java.util.Set;

import nam.model.Module;
import nam.model.Project;

import org.aries.util.FileUtil;


public abstract class AbstractProjectGenerator extends AbstractFileGenerator {

	protected abstract Set<Module> getModules(Project project) throws Exception;
	
	protected abstract void generateProjectConfiguration(Project project, Module module) throws Exception;
	
	protected abstract void generateProjectFiles(Project project, Module module) throws Exception;
	
	
	public void generate(Project project, Module module) throws Exception {
		context.setModule(module);
		assureProjectFoldersExist(project, module);
		generateProjectConfiguration(project, module);
		generateProjectFiles(project, module);
	}
	
	protected void assureProjectFoldersExist(Project project, Module module) throws Exception {
		//String targetPath = context.getTargetWorkspace() + "/" + context.getTargetHome();
		String projectName = context.getProjectName();
		String targetPath = context.getTargetPath(projectName);
		
		FileUtil.assureDirectoryExists(targetPath);
		FileUtil.assureDirectoryExists(targetPath + "/.settings");
		FileUtil.assureDirectoryExists(targetPath + "/src/main/java");
		FileUtil.assureDirectoryExists(targetPath + "/src/main/resources");
		FileUtil.assureDirectoryExists(targetPath + "/src/test/java");
		FileUtil.assureDirectoryExists(targetPath + "/src/test/resources");
	}


//	public void generateProject() throws Exception {
//		String sourcePath = TEMPLATE_WORKSPACE+"/"+context.getTemplateHome();
//		String targetPath = TARGET_HOME+"/"+context.getModule().getArtifactId();
//		List<Pattern> excludes = new ArrayList<Pattern>();
//		excludes.add(Pattern.compile("target"));
//		excludes.add(Pattern.compile("[,\\S]+.java"));
//		excludes.add(Pattern.compile("[,\\S]+.class"));
//		GenerateUtil.generateFiles(sourcePath, targetPath, excludes);
//	}
	
}
