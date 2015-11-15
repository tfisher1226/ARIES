package nam.ear;

import java.util.Iterator;
import java.util.Set;

import nam.ear.src.main.application.METAINF.ApplicationXMLBuilder;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ProjectUtil;
import aries.codegen.application.AbstractModuleBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class EARModuleBuilder extends AbstractModuleBuilder {

	private EARModuleHelper earModuleHelper;
	private ApplicationXMLBuilder applicationXMLBuilder;
	//private JBossAppXMLBuilder jbossAppXMLBuilder;

	
	public EARModuleBuilder(GenerationContext context) {
		earModuleHelper = new EARModuleHelper(context);
		applicationXMLBuilder = new ApplicationXMLBuilder(context);
		//jbossAppXMLBuilder = new JBossAppXMLBuilder(context);
		this.context = context;
	}
	
	public void initialize(Project project) throws Exception {
		Set<Module> modules = ProjectUtil.getEARModules(project);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			initialize(project, module);
		}
	}
	
	public void initialize(Project project, Module module) throws Exception {
		earModuleHelper.initialize(project, module);
		context.initialize(project, module);
	}

	public ModelFile buildApplicationXML() throws Exception {
		ModelFile ModelFile = applicationXMLBuilder.buildFile();
		return ModelFile;
	}

//	public ModelFile buildJBossAppXML() throws Exception {
//		ModelFile ModelFile = jbossAppXMLBuilder.buildFile();
//		return ModelFile;
//	}

}
