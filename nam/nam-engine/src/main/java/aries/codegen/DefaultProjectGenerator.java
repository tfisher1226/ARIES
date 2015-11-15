package aries.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import aries.codegen.util.GenerateUtil;
import aries.generation.engine.GenerationContext;


public class DefaultProjectGenerator extends AbstractGenerator {

	public void generate(GenerationContext generationContext) throws Exception {
		this.context = generationContext;
		String sourcePath = TEMPLATE_WORKSPACE+"/"+generationContext.getTemplateHome();
		String targetPath = TARGET_HOME+"/"+context.getModule().getArtifactId();
		List<Pattern> excludes = new ArrayList<Pattern>();
		excludes.add(Pattern.compile("target"));
		excludes.add(Pattern.compile("[,\\S]+.java"));
		excludes.add(Pattern.compile("[,\\S]+.class"));
		GenerateUtil.generateFiles(sourcePath, targetPath, excludes);
	}
	
}
