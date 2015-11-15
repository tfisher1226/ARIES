package org.aries;

import nam.model.Project;
import aries.generation.engine.GeneratorEngine;


public class AriesXMLGenerator {
	
	private GeneratorEngine engine;

	
	public AriesXMLGenerator(GeneratorEngine engine) {
		this.engine = engine;
	}

	public String generate(Project project) throws Exception {
       	String xml = engine.saveProjectToXML(project);
		return xml;
	}

}
