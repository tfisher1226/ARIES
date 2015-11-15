package nam.ui.src.main.webapp.admin.data;

import nam.model.Element;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.ui.src.main.webapp.admin.AbstractCompositionXHTMLBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public abstract class AbstractDataElementXHTMLBuilder extends AbstractCompositionXHTMLBuilder {

	protected abstract ModelFile buildFile(Namespace namespace, Element element) throws Exception;


	public AbstractDataElementXHTMLBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public void build(Project project, Module module) {
		super.build(project, module);
	}

	protected String generateCompositionXhtmlHeader() {
		Buf buf = new Buf();
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns=\"http://www.w3.org/1999/xhtml\"");
		buf.putLine("	xmlns:h=\"http://xmlns.jcp.org/jsf/html\"");
		buf.putLine("	xmlns:f=\"http://xmlns.jcp.org/jsf/core\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");	
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
		buf.putLine("	xmlns:a4j=\"http://richfaces.org/a4j\"");
		buf.putLine("	xmlns:rich=\"http://richfaces.org/rich\"");
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\">");
		return buf.get();
	}
	
}
