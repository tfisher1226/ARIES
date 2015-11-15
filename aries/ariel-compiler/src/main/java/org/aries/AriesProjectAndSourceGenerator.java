package org.aries;

import nam.model.Project;
import aries.generation.engine.GeneratorEngine;


public class AriesProjectAndSourceGenerator {

	private GeneratorEngine engine;

	
	public AriesProjectAndSourceGenerator(GeneratorEngine engine) {
		this.engine = engine;
	}

	public void generate(Project project) throws Exception {
       	engine.setInputProject(project);
       	engine.generate();
	}

}
