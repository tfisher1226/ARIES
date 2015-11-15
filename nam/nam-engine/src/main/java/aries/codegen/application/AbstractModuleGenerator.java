package aries.codegen.application;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractFileGenerator;

public abstract class AbstractModuleGenerator extends AbstractFileGenerator {

	public abstract void generate(Project project, Module module)
			throws Exception;

	public abstract void initialize(Project project, Module module)
			throws Exception;

	public void execute(Project project, Module module) throws Exception {
		initialize(project, module);
		generate(project, module);
	}

}
